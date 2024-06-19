import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UrlsReader {
    private final String fileName;
    private final List<String> urls;

    UrlsReader() {
        fileName = "files_urls.txt";
        urls = new ArrayList<String>();
    }

    public List<String> getUrls() {
        return urls;
    }

    public void read() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                addUrl(line);
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            System.exit(-1);
        }
    }

    private void addUrl(String line) {
        String[] tokens = line.split(" ");
        urls.add(tokens[1]);
    }
}


