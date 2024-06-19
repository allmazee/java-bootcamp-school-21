import java.util.ArrayList;

public class ThreadGenerator {
    private int threadsCount;
    private ArrayList<Integer> numbers;
    private int result;

    ThreadGenerator() {}

    public int getResult() {
        return result;
    }

    public void setThreadsCount(int threadsCount) {
        if (threadsCount < 1) {
            System.out.println("Threads count can not be less than 1");
            System.exit(-1);
        }
        this.threadsCount = Math.min(threadsCount, numbers.size());
    }

    public void setNumbers(ArrayList<Integer> numbers) {
        this.numbers = numbers;
    }

    public void generate() throws InterruptedException {
        int numbersCount = numbers.size();
        int numbersInThread = numbersCount / threadsCount;
        int remainder = Math.abs(numbersCount - threadsCount * numbersInThread);
        int numbersInLast = numbersInThread + remainder;
        Thread[] threads = new Thread[threadsCount];
        SumCalculator[] calculators = new SumCalculator[threadsCount];
        int offset = 0;
        int length = numbersInThread;
        for (int i = 0; i < threadsCount; i++) {
            if (i == threadsCount - 1) {
                length = numbersInLast;
            }
            calculators[i] = new SumCalculator(numbers, offset, length);
            threads[i] = new Thread(calculators[i]);
            threads[i].start();
            offset += length;
        }
        for (Thread thread: threads) {
            thread.join();
        }
        for (int i = 0; i < threadsCount; i++) {
            result += calculators[i].getResult();
        }
    }
}
