package ex02;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

 public class Program{
     public static void main(String[] args) {
        if (args.length != 2 || !args[0].equals("--current-folder")) {
            System.out.println("Usage: java FileManager --current-folder PATH");
            return;
        }

        String startPath = args[1];
        FileManager fileManager = new FileManager(startPath);
        fileManager.start();
    }
 }
