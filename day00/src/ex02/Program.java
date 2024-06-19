import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = 0
        int count = 0;
        while (num != 42) {
            num = scanner.nextInt();
            if (isPrime(sumOfDigits(num))) count++;
        }
        System.out.println("Count of coffee-request – " + count);
    }
    
    static int sumOfDigits(int num) {
        int res = 0;
        while (num > 0) {
            res += (num % 10);
            num /= 10;
        }
        return res;
    }

    static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        boolean result = true;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                result = false;
                break;
            }
        }
        return result;
    }
}
