import java.util.ArrayList;

public class SumCalculator implements Runnable{
    private ArrayList<Integer> numbers;
    private int offset;
    private int length;
    private int result;

    SumCalculator(ArrayList<Integer> numbers, int offset, int length) {
        this.numbers = numbers;
        this.offset = offset;
        this.length = length;
    }

    public int getResult() {
        return result;
    }

    @Override
    public void run() {
        result = 0;
        for (int i = offset; i < offset + length; i++) {
            result += numbers.get(i);
        }
        try {
            int first = numbers.get(offset);
            int last = numbers.get(offset + length-1);
            System.out.println(Thread.currentThread().getName() + ": from "
                    + first + " to " + last + " sum is " + result);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

    }
}
