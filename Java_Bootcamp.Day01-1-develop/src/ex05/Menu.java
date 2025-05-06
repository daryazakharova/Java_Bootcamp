package ex05;

import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private final TransactionsService transactionsService;
    private final boolean isDevMode;

    public Menu(TransactionsService transactionsService, boolean isDevMode) {
        this.transactionsService = transactionsService;
        this.isDevMode = isDevMode;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("-> ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    addUser(scanner);
                    break;
                case 2:
                    viewUserBalances(scanner);
                    break;
                case 3:
                    performTransfer(scanner);
                    break;
                case 4:
                    viewTransactionsForUser(scanner);
                    break;
                case 5:
                    if (isDevMode) {
                        removeTransferById(scanner);
                    } else {
                        System.out.println("This option is only available in dev mode.");
                    }
                    break;
                case 6:
                    if (isDevMode) {
                        checkTransferValidity(scanner);
                    } else {
                        System.out.println("This option is only available in dev mode.");
                    }
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println("---------------------------------------------------------");
        }
    }

    private void printMenu() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        if (isDevMode) {
            System.out.println("5. DEV – remove a transfer by ID");
            System.out.println("6. DEV – check transfer validity");
        }
        System.out.println("7. Finish execution");
    }

    private void addUser(Scanner scanner) {
        System.out.print("Enter a user name and a balance: ");
        String[] input = scanner.nextLine().split(" ");
        try {
            String name = input[0];
            int balance = Integer.parseInt(input[1]);
            User user = new User(name, balance);
            transactionsService.addUser(user);
            System.out.println("User  with id = " + user.getIdentifier() + " is added");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void viewUserBalances(Scanner scanner) {
        System.out.print("Enter a user ID: ");
        String userId = scanner.nextLine().trim();
        try {
            int id = Integer.parseInt(userId);
            double balance = transactionsService.getUserBalance(id);
            System.out.println(transactionsService.usersList.getUserById(id).getUserName() + " : " + balance);
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private void performTransfer(Scanner scanner) {
        System.out.print("Enter a sender ID, a recipient ID, and a transfer amount: ");
        String[] input = scanner.nextLine().split(" ");
        try {
            int senderId = Integer.parseInt(input[0]);
            int recipientId = Integer.parseInt(input[1]);
            int amount = Integer.parseInt(input[2]);
            transactionsService.executeTransaction(senderId, recipientId, amount);
            System.out.println("The transfer is completed");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void viewTransactionsForUser(Scanner scanner) {
        System.out.println("Enter a user ID");
        String input = scanner.nextLine().trim();
        try {
            int id = Integer.parseInt(input);
            Transaction[] transactions = transactionsService.getTransactionsList(id);
            if (transactions == null) {
                throw new RuntimeException("User with ID = " + id + " hasn't any transactions");
            }
            for (Transaction item : transactions) {
                String category = (item.getCategory() == Category.INCOME) ?
                        "From " :
                        "To ";
                User user = (item.getCategory() == Category.INCOME) ?
                        item.getSender() :
                        item.getRecipient();
                System.out.println(category + user.getUserName() + "(id = " + user.getIdentifier() + ") " +
                        item.getAmount() + " with id = " + item.getId());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void removeTransferById(Scanner scanner) {
        System.out.print("Enter a user ID and a transfer ID: ");
        String input = scanner.nextLine().trim();
        try {
            String[] inputArr = input.split(" ");
            if (inputArr.length != 2) {
                throw new RuntimeException("Invalid data. Try again");
            }
            int userId = Integer.parseInt(inputArr[0]);
            String transactionId = inputArr[1];
            Transaction transaction = hgetTransaction(transactionsService.getTransactionsList(userId), transactionId);
            transactionsService.removeTransaction(transactionId, userId);
            User user = (transaction.getCategory() == Category.INCOME) ?
                    transaction.getSender() :
                    transaction.getRecipient();
            String category = (transaction.getCategory() == Category.INCOME) ?
                    "From " :
                    "To ";
            System.out.println("Transfer " + category + " " + user.getUserName() +
                    "(id = " + user.getIdentifier() + ") " + transaction.getAmount() + " removed");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private Transaction hgetTransaction(Transaction[] transactions, String transactionId) {
        if (transactions == null) {
            throw new RuntimeException("Transaction with id = " + transactionId + " not found");
        }
        for (Transaction item : transactions) {
            if (item.getId().equals(transactionId)) {
                return item;
            }
        }
        return null;
    }

    private User getUserHolder(Transaction transaction) {
        UsersArrayList listUsers = transactionsService.usersList;

        for (int i = 0; i < listUsers.getNumberOfUsers(); i++) {
            Transaction[] listTrans = listUsers.getUserByIndex(i).getTransactionsList().toArray();
            for (int j = 0; listTrans != null && j < listTrans.length; j++) {
                if (listTrans[j].getId().equals(transaction.getId())) {
                    return listUsers.getUserByIndex(i);
                }
            }
        }
        return null;
    }

    private void checkTransferValidity(Scanner scanner) {
        System.out.println("Check results:");
        Transaction[] transactions = transactionsService.checkValidityOfTransactions();
        if (transactions != null) {
            for (Transaction item : transactions) {
                User userHolder = getUserHolder(item);
                User userSender = (item.getCategory() == Category.INCOME) ?
                        item.getSender() :
                        item.getRecipient();
                String transactionId = item.getId();
                double amount = item.getAmount();
                System.out.println(userHolder.getUserName() + "(id = " + userHolder.getIdentifier() +
                        ") has an unacknowledged transfer id = " + transactionId + " from " +
                        userSender.getUserName() + "(id = " + userSender.getIdentifier() +
                        ") for " + amount);
            }
        }
        System.out.println("There are no unpaired transactions");
    }
}

