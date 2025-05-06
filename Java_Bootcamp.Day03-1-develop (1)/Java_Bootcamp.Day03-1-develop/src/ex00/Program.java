package ex00;

public class Program {
    public static void main(String[] args) {
        // Анализ аргументa командной строки для подсчета
        int count = 50; 
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
        EggThread eggThread = new EggThread(count);
        HenThread henThread = new HenThread(count);

        eggThread.start();
        henThread.start();

        // Ожидание пока оба потока завершатся
        try {
            eggThread.join();
            henThread.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }

        // Печать "Human" 
        for (int i = 0; i < count; i++) {
            System.out.println("Human");
        }
    }
}
}