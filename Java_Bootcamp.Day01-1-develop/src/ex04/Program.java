package ex04;

public class Program {
    public static void main(String[] args) {
        TransactionsService facade = new TransactionsService();

        User user1 = new User("Alice", 10000);
        User user2 = new User("Mike", 15000);

        facade.addUser(user1);
        facade.addUser(user2);

        System.out.println("Users before transactions:");
        System.out.println(facade.usersList.toString());

        facade.executeTransaction(user1.getIdentifier(), user2.getIdentifier(), 3000);
        facade.executeTransaction(user1.getIdentifier(), user2.getIdentifier(), 150);
        facade.executeTransaction(user2.getIdentifier(), user1.getIdentifier(), 4000);

        System.out.println("\nUsers after 3 transactions:");
        System.out.println(facade.usersList.toString());

        System.out.println("\nInformation about " + user1.getUserName() + "'s transactions:");
        Transaction[] transactionsArr1 = facade.getTransactionsList(user1.getIdentifier());
        for (Transaction item : transactionsArr1) {
            System.out.println(item.toString());
        }

        System.out.println("\nInformation about " + user2.getUserName() + "'s transactions:");
        Transaction[] transactionsArr2 = facade.getTransactionsList(user2.getIdentifier());
        for (Transaction item : transactionsArr2) {
            System.out.println(item.toString());
        }


        System.out.println("\nRemove one " + user1.getUserName() + "'s transaction.");
        facade.removeTransaction(transactionsArr1[1].getId(), user1.getIdentifier());

        System.out.println("Information about " + user1.getUserName() + "'s transactions:");
        transactionsArr1 = facade.getTransactionsList(user1.getIdentifier());
        for (Transaction item : transactionsArr1) {
            System.out.println(item.toString());
        }

        System.out.println("\nInformation about " + user2.getUserName() + "'s transactions:");
        transactionsArr2 = facade.getTransactionsList(user2.getIdentifier());
        for (Transaction item : transactionsArr2) {
            System.out.println(item.toString());
        }

        System.out.println("\nInformation about unpaired operations:");
        Transaction[] invalid = facade.checkValidityOfTransactions();
        for (Transaction item : invalid) {
            System.out.println(item.toString());
        }

        System.out.println("Exceptions:");
        try {
            facade.executeTransaction(user1.getIdentifier(), user2.getIdentifier(), 100000);
        } catch (IllegalTransactionException e) {
            System.err.println(e.getMessage());
        }

        User user = new User("John", 100);
        try {
            facade.getUserBalance(user);
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}