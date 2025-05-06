package ex02;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        //Анализ аргументa командной строки для подсчета
        int size=0, count=0;
        if (args.length != 2 || !args[0].startsWith("--arraySize=") || !args[1].startsWith("--threadsCount=")) {
            System.out.println("Usage: java Program --arraySize=<size> --threadsCount=<count>");
            System.exit(-1);   
            //return;
        }
        String arraySize = args[0].split("=")[1];
        String threadsCount = args[1].split("=")[1]; 
            try {
             size = Integer.parseInt(arraySize);
             count = Integer.parseInt(threadsCount);
            } catch (NumberFormatException e) {
                System.out.println("Invalid arraySize/threadsCount value.");
                System.exit(-1);
            }

        
        if (size > 2000000 || count > size || count < 1) {
            System.out.println("Invalid input. Ensure array size is <= 2,000,000 and threads count <= array size.");
            return;
        }

        Calculate calculate = new Calculate(size, count);
        calculate.run();
    }
}