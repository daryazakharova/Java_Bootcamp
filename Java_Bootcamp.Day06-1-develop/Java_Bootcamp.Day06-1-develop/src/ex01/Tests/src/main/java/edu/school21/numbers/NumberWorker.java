package edu.school21.numbers;

public class NumberWorker {

    public boolean isPrime(int number) {
        if (number < 2) {
            throw new IllegalNumberException("Number must be greater than 1");
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int digitsSum(int number) {
        int sum = 0;
        number = Math.abs(number); // Handle negative numbers
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }
}
