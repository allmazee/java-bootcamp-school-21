public class Producer implements Runnable{
    private final int count;
    private final Object lock;

    Producer(int count, Object lock) {
        this.count = count;
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.println("Egg");
            synchronized (lock) {
                lock.notify();
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    System.exit(-1);
                }
            }
        }
    }
}
