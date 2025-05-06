package ex01;
import java.io.*;
import java.util.*;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Program <input1.txt> <input2.txt>");
            return;
        }

        Words words = new Words(args[0], args[1]);
        try {
            words.run();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
