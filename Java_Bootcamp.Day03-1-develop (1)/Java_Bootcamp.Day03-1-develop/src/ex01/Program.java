package ex01;

public class Program {
    private static final Object lock = new Object();
    private static int count = 50; 

    public static void main(String[] args) {
        //Анализ аргументa командной строки для подсчета
        if (args.length != 1 || !args[0].startsWith("--count=")) {
            System.out.println("Usage: java Program --count=50");
        } else {
            String countStr = args[0].substring("--count=".length());
            try {
                count = Integer.parseInt(countStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid count value. Using default: 50");
                System.exit(-1);
            }
        

        // Создание потоков Egg and Hen
        Thread eggThread = new Thread(new EggRunnable());
        Thread henThread = new Thread(new HenRunnable());

        eggThread.start();
        henThread.start();

        // Ожидание пока оба потока завершатся
        try {
            eggThread.join();
            henThread.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }
    }
    }
    static class EggRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < count; i++) {
                synchronized (lock) {
                    System.out.println("Egg");
                    lock.notify();                          // Сообщить hen thread
                    try {
                        if (i < count - 1) {                // Не ждaть после последнего  egg
                            lock.wait();                    // Подождaть hen
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    static class HenRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < count; i++) {
                synchronized (lock) {
                    try {
                        if (i > 0) {                       // Ждaть первого egg
                            lock.wait();                   // Подождaть пока появится egg
                        }
                        System.out.println("Hen");
                        lock.notify();                    // Сообщите об этом egg 
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}