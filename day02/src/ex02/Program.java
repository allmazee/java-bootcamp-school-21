import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Program {
    public static void main(String[] args) throws IOException {
        FileManager manager = new FileManager();
        manager.run(args[0]);
    }
}
