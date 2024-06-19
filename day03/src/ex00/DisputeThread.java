public class DisputeThread extends Thread {
    private String message;
    private int count;

    DisputeThread(String message, int count) {
        this.message = message;
        this.count = count;
    }

    public void run() {
            for (int i = 0; i < count; i++) {
                System.out.println(message);
            }
    }
}
