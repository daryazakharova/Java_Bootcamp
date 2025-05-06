package ex05;

public class Program {
    public static void main(String[] args) {
        boolean isDevMode = false;

        if (args.length > 0 && args[0].equals("--profile=dev")) {
            isDevMode = true;
        }

        TransactionsService transactionsService = new TransactionsService();

        Menu menu = new Menu(transactionsService, isDevMode);
        menu.run();
    }
}