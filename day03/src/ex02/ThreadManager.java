import java.util.ArrayList;

public class ThreadManager {
    private final ArgumentsReader reader;
    private final ArrayGenerator arrayGenerator;
    private final ThreadGenerator threadGenerator;
    ThreadManager(String[] args) {
        reader = new ArgumentsReader(args);
        arrayGenerator = new ArrayGenerator();
        threadGenerator = new ThreadGenerator();
    }

    public ArrayList<Integer> getNumbers() {
        return arrayGenerator.getNumbers();
    }

    public void init() {
        reader.read();
        arrayGenerator.setArraySize(reader.getArraySize());
        arrayGenerator.generate();
        threadGenerator.setThreadsCount(reader.getThreadsCount());
        threadGenerator.setNumbers(arrayGenerator.getNumbers());
    }
    public void calculate() {
        try {
            threadGenerator.generate();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        System.out.println("Sum by threads: " + threadGenerator.getResult());
    }
}
