import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        if (num <= 1) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        int count = 1;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i != 0) {
                count++;
            } else {
                System.out.println("false " + count);
                System.exit(0);
            }
        }
        System.out.println("true " + count);
    }
}
