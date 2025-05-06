package ex03;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Program {
    public static void main(String[] args) {
        String filePath = "files_urls.txt";
        List<String> links = new ArrayList<>();

        // Чтение URL-адресов из файла
        try (Scanner scanner = new Scanner(new FileInputStream(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(" ");
                if (split.length > 1) {
                    links.add(split[1]);               // Добавление URL в список
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: " + filePath + " not found.");
            return;
        }

        CountDownLatch latch = new CountDownLatch(links.size());
        int threadCount = parseThreadCount(args);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        // Отправка задач на загрузку в executor service
        for (int i = 0; i < links.size(); i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                try {
                    String[] numberThread = Thread.currentThread().getName().split("-");
                    System.out.println("Thread-" + numberThread[3] + " start download file number " + (index + 1));
                    downloadFile(links.get(index), index + 1);
                    System.out.println("Thread-" + numberThread[3] + " finish download file number " + (index + 1));
                } catch (IOException e) {
                    System.err.println("Error downloading file number " + (index + 1) + ": " + e.getMessage());
                } finally {
                    latch.countDown();                // Уменьшить счетчик
                }
                }
            });
        }

        // Ожидание завершения всех загрузок
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();      // Восстановить прерванное состояние
            System.err.println("Download interrupted.");
        } finally {
            executorService.shutdown();             // Завершить работу executor service
        }

        System.out.println("All downloads completed.");
    }

    // Метод загрузки файла по URL-адресу
    public static void downloadFile(String link, int fileIndex) throws IOException {
        String[] split = link.split("\\.");
        String name = fileIndex + "." + split[split.length - 1];
        Path pathFile = Paths.get(name);           // Сохранить в текущем каталоге
        URL url = new URL(link);
        try {
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "chrome-extension");

            try (InputStream in = connection.getInputStream()) {
                Files.copy(in, pathFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new IOException("Failed to download from " + link + ": " + e.getMessage());
        }
    }

    // Метод для анализа количества потоков из аргументов командной строки
    public static int parseThreadCount(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--threadsCount=")) {
            System.out.println("Usage: java Program --threadsCount=<number>");
            System.exit(1);
        }
        String[] parts = args[0].split("=");
        try {
            int threadCount = Integer.parseInt(parts[1]);
            if (threadCount <= 0 ) {
                throw new NumberFormatException();
            }
            return threadCount;
        } catch (NumberFormatException e) {
            System.out.println("Invalid thread count. Please provide a positive integer.");
            System.exit(1);
            return 0; 
        }
    }
}
