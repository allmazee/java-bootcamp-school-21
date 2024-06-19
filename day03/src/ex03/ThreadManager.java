import java.util.List;

public class ThreadManager {
    private final ArgumentsReader argumentsReader;
    private final UrlsReader urlsReader;
    private final ThreadGenerator generator;

    ThreadManager(String[] args) {
        this.argumentsReader = new ArgumentsReader(args);
        this.urlsReader = new UrlsReader();
        this.generator = new ThreadGenerator();
    }

    public void download() {
        argumentsReader.read();
        urlsReader.read();
        int threadsCount = argumentsReader.getThreadsCount();
        List<String> urls = urlsReader.getUrls();
        generator.setThreadsCount(threadsCount);
        generator.setUrls(urls);
        generator.generate();
    }
}
