package ex01;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //System.out.print("-> ");
        int number = in.nextInt();
        in.close();

        if (number < 2) {
            System.out.println("IllegalArgument");
            System.exit(-1);
        } else if (number == 2) {
            System.out.println("true 1");
        } else {
            int i;
            for (i = 2; i <= Math.sqrt(number); i++) {
                if (number % i != 0) continue;
                System.out.println("false " + --i);
                System.exit(0);
            }
            System.out.println("true " + --i);
        }
    }
}