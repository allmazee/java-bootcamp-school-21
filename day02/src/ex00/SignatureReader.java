import java.io.*;
import java.util.*;

public class SignatureReader {
    private final int BUFFER_SIZE = 8;
    private final String UNDEFINED_STR = "UNDEFINED";
    private final String RESULT_FILE = "result.txt";
    private final String SIGNATURES_FILE = "signatures.txt";
    private Map<String, byte[]> signatures = new HashMap<>();

    SignatureReader() {
        loadSignatures();
    }

    public void runSignatureReader() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("->");
            String line = scanner.nextLine();
            if (line.equals("42")) {
                System.exit(0);
            }
            String type = getFileType(line);
            if (type.equals(UNDEFINED_STR)) {
                System.out.println(type);
            } else {
                writeToFile(type);
                System.out.println("PROCESSED");
            }
        }
    }

    private String getFileType(String filePath) {
        byte[] hexes = readSignature(filePath);
        if (hexes.length == 0) {
            return UNDEFINED_STR;
        }
        for (Map.Entry<String, byte[]> entry : signatures.entrySet()) {
            if (startsWith(hexes, entry.getValue())) {
                return entry.getKey();
            }
        }
        return UNDEFINED_STR;
    }

    private byte[] readSignature(String filePath) {
        byte[] bytes = new byte[BUFFER_SIZE];
        try (FileInputStream fileInputStream = new FileInputStream(filePath)){
            int input = fileInputStream.read(bytes, 0, bytes.length);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return bytes;
    }

    private boolean startsWith(byte[] fileBytes, byte[] signature) {
        for (int i = 0; i < signature.length; i++) {
            if (signature[i] != fileBytes[i]) {
                return false;
            }
        }
        return true;
    }

    private void writeToFile(String type) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(RESULT_FILE, true)) {
            if (!type.equals(UNDEFINED_STR)) {
                fileOutputStream.write(type.getBytes());
                fileOutputStream.write('\n');
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadSignatures() {
        try(FileInputStream fileInputStream = new FileInputStream(SIGNATURES_FILE)) {
            StringBuilder hexes = new StringBuilder();
            int input;
            while ((input = fileInputStream.read()) != -1) {
                if (input == '\n') {
                    putToMap(hexes.toString());
                    hexes.setLength(0);
                } else if (input != '\r') {
                    hexes.append((char) input);
                }
                if (hexes.length() == 0) {
                    putToMap(hexes.toString());
                }
            }
        } catch (IOException exception) {
            System.out.print(exception.getMessage());
        }
    }

    private void putToMap(String line) {
        String[] parts = line.split(", ");
        if (parts.length == 2) {
            String type = parts[0];
            String hexes = parts[1].replace(" ", "");
            byte[] bytes = strToByteArray(hexes);
            signatures.put(type, bytes);
        }
    }

    private byte[] strToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        if (true) {
            for (int i = 0; i < len - 1; i += 2) {
                data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                        + Character.digit(s.charAt(i+1), 16));
            }
        }
        return data;
    }
}
