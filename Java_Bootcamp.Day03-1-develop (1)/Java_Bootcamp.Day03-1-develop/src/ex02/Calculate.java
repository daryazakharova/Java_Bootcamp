package ex02;

import java.util.Random;

public class Calculate {
    private int[] array;
    private final int arraySize;
    private final int countOfThreads;

    public Calculate(int arraySize, int countOfThreads) {
        this.arraySize = arraySize;
        this.countOfThreads = countOfThreads;
        this.array = new int[arraySize];
    }

    public void run() throws InterruptedException {
        generateArray();
        int standardSum = calculateStandardSum();
        System.out.println("Sum: " + standardSum);
        int threadSum = calculateSumWithThreads();
        System.out.println("Sum by threads: " + threadSum);
    }

    private void generateArray() {
        Random random = new Random();
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(1001); 
        }
    }

    private int calculateStandardSum() {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    private int calculateSumWithThreads() throws InterruptedException {   //исключение, если поток прерывается во время ожидания.
        int period = arraySize / countOfThreads;
        int remainder = arraySize % countOfThreads;
        Thread[] threads = new Thread[countOfThreads];
        SumThread[] sumThreads = new SumThread[countOfThreads];
        int start = 0;
        int totalSum = 0;

        for (int i = 0; i < countOfThreads; i++) {
            int end = start + period + (i == countOfThreads - 1 ? remainder : 0);
            sumThreads[i] = new SumThread(array, start, end);
            threads[i] = new Thread(sumThreads[i]);
            threads[i].start();
            start = end;
        }

        for (int i = 0; i < countOfThreads; i++) {
            threads[i].join();
            totalSum += sumThreads[i].getSum();
            System.out.println("Thread " + (i + 1) + ": from " + (i * period) + " to " + (i * period + (i == countOfThreads - 1 ? remainder : period - 1)) + " sum is " + sumThreads[i].getSum());
        }

        return totalSum;
    }
}