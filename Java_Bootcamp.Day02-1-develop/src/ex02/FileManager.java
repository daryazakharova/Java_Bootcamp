package ex02;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class FileManager {
    private Path currentPath;

    public FileManager(String startPath) {
        this.currentPath = Paths.get(startPath);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.print(currentPath + "\n-> ");
            command = scanner.nextLine().trim();

            if (command.equals("exit")) {
                break;
            }

            String[] parts = command.split(" ");
            switch (parts[0]) {
                case "ls":
                    listFiles();
                    break;
                case "cd":
                    if (parts.length > 1) {
                        changeDirectory(parts[1]);
                    } else {
                        System.out.println("cd: missing argument");
                    }
                    break;
                case "mv":
                    if (parts.length > 2) {
                        moveFile(parts[1], parts[2]);
                    } else {
                        System.out.println("mv: missing arguments");
                    }
                    break;
                default:
                    System.out.println("Unknown command: " + parts[0]);
            }
        }
        scanner.close();
    }

    private void listFiles() {
        File folder = currentPath.toFile();
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                long sizeInKB = file.length() / 1024;
                System.out.println(file.getName() + " " + sizeInKB + " KB");
            }
        } else {
            System.out.println("The directory is empty or an error occurred.");
        }
    }

    private void changeDirectory(String folderName) {
        Path newPath = currentPath.resolve(folderName);
        if (Files.isDirectory(newPath)) {
            currentPath = newPath;
        } else {
            System.out.println("No such directory: " + folderName);
        }
    }

    private void moveFile(String what, String where) {
        Path source = currentPath.resolve(what);
        Path target;

        if (Files.exists(source)) {
            if (Files.isDirectory(currentPath.resolve(where))) {
                target = currentPath.resolve(where).resolve(source.getFileName());
            } else {
                target = currentPath.resolve(where);
            }

            try {
                Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("mv: " + e.getMessage());
            }
        } else {
            System.out.println("mv: " + what + ": No such file");
        }
    }
}