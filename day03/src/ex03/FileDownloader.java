import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class FileDownloader implements Runnable{
    private final List<String> urls;
    private final String fileName;
    private final int index;

    FileDownloader(List<String> urls, int index) {
        this.urls = urls;
        this.fileName = "file_";
        this.index = index;
    }

    @Override
    public void run() {
        String url = urls.get(index);
        String type = getType(url);
        try (InputStream inputStream = new URL(url).openStream()) {
            System.out.println(Thread.currentThread().getName()
                    + " start download file number " + index);
            Files.copy(inputStream, Paths.get(fileName + index + type), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Failed to download file number " + index + ": " + e.getMessage());
        }
        System.out.println(Thread.currentThread().getName()
                + " finish download file number " + index);
    }

    private String getType(String url) {
        String[] tokens = url.split("\\.");
        return "." + tokens[tokens.length - 1];
    }
}
