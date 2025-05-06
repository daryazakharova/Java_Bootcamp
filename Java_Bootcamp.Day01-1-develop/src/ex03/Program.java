package ex03;

import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User user1 = new User("Alice", 5000);
        User user2 = new User("Mike", 1000);

        TransactionsLinkedList listOfTransactionUser1 = user1.getTransactions();

        Transaction tr1 = new Transaction(user1, user2, Category.OUTCOME, -300);
        Transaction tr2 = new Transaction(user1, user2, Category.INCOME, 500);
        Transaction tr3 = new Transaction(user1, user2, Category.INCOME, 200);
        Transaction tr4 = new Transaction(user1, user2, Category.OUTCOME, -100);

        listOfTransactionUser1.addTransaction(tr1);
        listOfTransactionUser1.addTransaction(tr2);
        listOfTransactionUser1.addTransaction(tr3);
        listOfTransactionUser1.addTransaction(tr4);

        System.out.println("User Alice performed  " + user1.getTransactions().getSize() + " transactions.");
        System.out.println("Let's convert the list into an array and print it ");
        Transaction[] transactionsArray = user1.getTransactions().toArray();
        System.out.println("Transactions for user " + user1.getUserName() + ":");
        for (Transaction transaction : transactionsArray) {
            System.out.println(transaction);
        }

        System.out.println("The 3 transaction was deleted:");
        listOfTransactionUser1.removeTransactionById(tr3.getId());
        for (Transaction transaction : user1.getTransactions().toArray()) {
            System.out.println(transaction);
        }

        System.out.println("Deleting a non-existent transaction:");
        try {
            listOfTransactionUser1.removeTransactionById(UUID.randomUUID().toString());
        } catch (TransactionNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}