import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Program {
    public static void main(String[] args) {
        Object lock = new Object();
        int count = getCount(args);
        Thread eggThread = new Thread(new Producer(count, lock));
        Thread henThread = new Thread(new Consumer(count, lock));
        henThread.start();
        eggThread.start();
    }

    public static int getCount(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--count=")) {
            System.out.println("Invalid arguments");
            System.exit(-1);
        }
        String[] tokens = args[0].split("--count=");
        if (tokens.length != 2) {
            System.out.println("Invalid arguments");
            System.exit(-1);
        }
        int count = 0;
        try {
            count = Integer.parseInt(tokens[1]);
        }
        catch (NumberFormatException e) {
            System.out.println("NumberFormatException: " + e.getMessage());
            System.exit(-1);
        }
        return count;
    }

}
