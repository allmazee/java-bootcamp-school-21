import java.util.Objects;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{
    private final int count;
    private final Object lock;

    Consumer(int count, Object lock) {
        this.count = count;
        this.lock = lock;
    }

    @Override
     public void run() {
        for (int i = 0; i < count; i++) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    System.exit(-1);
                }
                System.out.println("Hen");
                lock.notify();
            }
        }
    }
}
