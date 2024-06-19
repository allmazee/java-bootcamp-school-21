import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileManager {
    private String pathString;
    private File absolutePath;

    FileManager(){}

    public void run(String currentFolder) throws IOException {
        if (!currentFolder.startsWith("--current-folder=")) {
            System.err.println("Illegal arguments");
            System.exit(-1);
        }
        String[] args = currentFolder.split("--current-folder=");
        if (args.length != 2) {
            System.err.println("Illegal arguments");
            System.exit(-1);
        }
        Path startPath = Paths.get(args[1]);
        if (!startPath.isAbsolute() || !Files.exists(startPath)) {
            System.out.println("Wrong initial directory");
            System.exit(-1);
        }
        pathString = args[1];
        absolutePath = startPath.toFile();
        System.out.println(pathString);
        Scanner scanner = new Scanner(System.in);
        String inputLine = "";
        while (!inputLine.equals("exit")) {
            inputLine = scanner.nextLine();
            handleCommand(inputLine);
        }

    }

    private void handleCommand(String inputLine) throws IOException {
        if (inputLine.equals("ls")) {
            listFiles();
        } else if (inputLine.startsWith("mv ")) {
            move(inputLine);
        } else if (inputLine.startsWith("cd")) {
            changeDirectory(inputLine);
        } else {
            if (!inputLine.equals("exit")) {
                System.out.println("Unknown command");
            }
        }
    }

    private void listFiles() {
        File[] files = absolutePath.listFiles();
        if (files == null) {
            System.out.println("Cannot access directory");
            return;
        }
        for(File file : files) {
            long size = 0;
            if (file.isDirectory()) {
                size = getSize(file);
            } else {
                size = file.length();
            }
            System.out.println(file.getName() + " " + size / 1024 + " KB");
        }
    }

    private long getSize(File directory) {
        long size = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    size += getSize(file);
                } else {
                    size += file.length();
                }
            }
        }
        return size;
    }

    private void move(String inputLine) {
        String[] args = inputLine.split(" ");
        if (args.length > 3) {
            System.out.println("mv: too many arguments");
        } else if (args.length < 3) {
            System.out.println("mv: missing destination file operand");
        } else {
            Path currentPath = Paths.get(pathString);
            Path source = currentPath.resolve(Paths.get(args[1]));
            Path destination = currentPath.resolve(Paths.get(args[2]));
            if (!Files.exists(source)) {
                System.out.println("mv: cannot stat " + source.getFileName() + ": No such file or directory");
                return;
            }
            if (Files.isDirectory(destination)) {
                destination = destination.resolve(source.getFileName());
            }
            try {
                Files.move(source, destination);
            } catch (IOException e) {
                System.out.println("mv: " + e.getMessage());
            }
        }
    }

    private void changeDirectory(String inputLine) {
        String[] args = inputLine.split("cd ");
        if (args.length > 2) {
            System.out.println("cd: too many arguments");
        } else if (args.length == 1) {
            if (!args[0].equals("cd")) {
                System.out.println("Command " + args[0] + " not found");
            }
            Path root = absolutePath.toPath().getRoot();
            absolutePath = root.toFile();
            pathString = absolutePath.toString();
            System.out.println(pathString);
        } else {
            Path path = Paths.get(args[1]);
            if (path.isAbsolute()) {
                absolutePath = new File(args[1]);
                pathString = args[1];
            } else {
                String tmpPathStr = pathString + "/" + args[1];
                path = Paths.get(tmpPathStr).normalize();
                if(!path.toFile().exists()) {
                    System.out.println("No such file or directory");
                    return;
                }
                absolutePath = path.toFile();
                pathString = absolutePath.toString();
            }
            System.out.println(pathString);
        }
    }
}
