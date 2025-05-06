package ex00;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        String signatureFilePath = "./signatures.txt";
        Map<String, String[]> signaturesMap = new HashMap<>();

        // Чтение текста из файла
        try (FileInputStream fis = new FileInputStream(signatureFilePath);
             Scanner scanner = new Scanner(fis)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    String format = parts[0].trim();
                    String[] signatureBytes = parts[1].trim().split(" ");
                    signaturesMap.put(format, signatureBytes);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Signatures file not found: " + e.getMessage());
            return;
        } catch (IOException e) {
            System.out.println("Error reading signatures file: " + e.getMessage());
            return;
        }

        // Чтение пути к файлам и запись результата.
        try (Scanner inputScanner = new Scanner(System.in);
             BufferedWriter writer = new BufferedWriter(new FileWriter("./result.txt", true))) {
            String filePath;

            while (true) {
                System.out.print("-> ");
                filePath = inputScanner.nextLine().trim();
                if (filePath.equals("42")) {
                    break; 
                }

                File file = new File(filePath);
                if (!file.exists()) {
                    System.out.println("File does not exist: " + filePath);
                    continue; 
                }

                // Чтение первых нескольких байтов файла
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    byte[] buffer = new byte[8]; // Read up to 8 bytes
                    int bytesRead = fileInputStream.read(buffer);
                    if (bytesRead == -1) {
                        System.out.println("File is empty: " + filePath);
                        continue; 
                    }

                    // Преобразование байтов в шестнадцатеричную строку
                    StringBuilder hexString = new StringBuilder();
                    for (int i = 0; i < bytesRead; i++) {
                        hexString.append(String.format("%02X", buffer[i] & 0xFF)).append(" ");
                    }

                    // Проверка совпадение подписей
                    boolean found = false;
                    for (Map.Entry<String, String[]> entry : signaturesMap.entrySet()) {
                        String format = entry.getKey();
                        String[] signature = entry.getValue();
                        String signatureHex = String.join(" ", signature);

                        if (hexString.toString().startsWith(signatureHex)) {
                            writer.write(format + "\n");
                            found = true;
                            System.out.println("PROCESSED");
                            break; 
                        }
                    }

                    if (!found) {
                        System.out.println("UNDEFINED");
                    }
                } catch (IOException e) {
                    System.out.println("Error reading file: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to result file: " + e.getMessage());
        }
    }
}
