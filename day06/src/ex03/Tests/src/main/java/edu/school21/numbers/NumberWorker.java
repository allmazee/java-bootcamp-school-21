package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int number) {
        if (number < 2) {
            throw new IllegalNumberException("IllegalNumberException: Number is less than 2");
        }
        for (int i = 2; i * i <= number; i++) {
            if (number % i != 0) {
                continue;
            }
            return false;
        }
        return true;
    }

    public int digitsSum(int number) {
        int sum = 0;
        number = Math.abs(number);
        if (number == -2147483648) {
            return 47;
        }
        while (number / 10 != 0) {
            sum += number % 10;
            number /= 10;
        }
        sum += number;
        return sum;
    }
}