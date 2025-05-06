package ex02;

import java.util.Scanner;

public class Program {

    public static int sumOfDigits(int number) {
        int sumOfDigits = 0;
        while (number > 0) {
            int digit = number % 10;
            sumOfDigits += digit;
            number /= 10;
        }
        return sumOfDigits;
    }

    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        } else if (number == 2) {
            return true;
        } else {
            int i;
            for (i = 2; i <= Math.sqrt(number); i++) {
                if (number % i != 0) continue;
                return false;
            }
            return true;
        }
    }

    public static void main(String[] args) {
        int number;
        int coffeeRequests = 0;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            number = in.nextInt();
            if (number == 42) {
                break;
            }
            if (number == 0 || number == 1) continue;
            if (isPrime(sumOfDigits(number))) coffeeRequests++;
        }

        System.out.println("Count of coffee-request - " + coffeeRequests);
        in.close();
    }
}