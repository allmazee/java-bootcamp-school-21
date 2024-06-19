import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadGenerator {
    private int threadsCount;
    private List<String> urls;

    ThreadGenerator() {}

    ThreadGenerator(int threadsCount, List<String> urls) {
        if (threadsCount < 1) {
            System.out.println("Threads count can not be less than 1");
            System.exit(-1);
        }
        this.threadsCount = threadsCount;
        this.urls = urls;
    }

    public void setThreadsCount(int threadsCount) {
        this.threadsCount = threadsCount;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public void generate() {
        ExecutorService service = Executors.newFixedThreadPool(threadsCount);
        for (int i = 1; i <= urls.size(); i++) {
            service.submit(new FileDownloader(urls, i));
        }
        service.shutdown();
    }


}
