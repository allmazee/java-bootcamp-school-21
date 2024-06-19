import java.util.ArrayList;
import java.util.Random;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        ThreadManager manager = new ThreadManager(args);
        manager.init();
        ArrayList<Integer> numbers = manager.getNumbers();
        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        System.out.println(numbers);
        System.out.println("Sum: " + sum);
        manager.calculate();
    }
}
