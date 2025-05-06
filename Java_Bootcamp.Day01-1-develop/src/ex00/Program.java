package ex00;

import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User user1 = new User(1, "Mike", 1000);
        User user2 = new User(2, "John", 500);
        System.out.println("User 1: " + user1);
        System.out.println("User 2: " + user2);

        Transaction transaction1 = new Transaction(user1, user2, Category.OUTCOME, -200);
        System.out.println(transaction1);
        System.out.println("Updated User 1 Balance: " + user1.getBalance());
        System.out.println("Updated User 2 Balance: " + user2.getBalance());

        Transaction transaction2 = new Transaction(user1, user2, Category.INCOME, 500);
        System.out.println(transaction2);
        System.out.println("Updated User 1 Balance: " + user1.getBalance());
        System.out.println("Updated User 2 Balance: " + user2.getBalance());

        Transaction transaction3 = new Transaction(user1, user2, Category.OUTCOME, -1500);
        //User user3 = new User(3, "Mike", -1000);
    }
}