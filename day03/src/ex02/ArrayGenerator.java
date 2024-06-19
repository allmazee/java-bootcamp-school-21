import java.util.ArrayList;
import java.util.Random;

public class ArrayGenerator {
    private int arraySize;
    private ArrayList<Integer> numbers;

    ArrayGenerator() {
        this.numbers = new ArrayList<>(arraySize);
    }

    public ArrayList<Integer> getNumbers() {
        return numbers;
    }

    public void setArraySize(int arraySize) {
        int maxElements = 2000000;
        this.arraySize = Math.min(arraySize, maxElements);
    }

    public void generate() {
        Random random = new Random();
        int maxModuloValue = 1000;
        for (int i = 0; i < arraySize; i++) {
            Integer r = random.nextInt() % maxModuloValue;
            numbers.add(r);
        }
    }
}
