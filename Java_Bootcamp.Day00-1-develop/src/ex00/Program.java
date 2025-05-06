package ex00;

public class Program {

    public static void main(String[] args) {
        int number = 479598;
        int sumOfDigits = 0;
        while (number > 0) {
            int digit = number % 10;
            sumOfDigits += digit;
            number /= 10;
        }

        System.out.println("The sum of digits is: " + sumOfDigits);
    }
}