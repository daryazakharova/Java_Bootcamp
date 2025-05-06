package ex05;

public class User {
    private int identifier;
    private String userName;
    private double balance;
    private TransactionsLinkedList transactions;

    public User(String userName, double balance) {
        this.userName = userName;
        setBalance(balance);
        this.identifier = UserIdsGenerator.getInstance().generateId();
        this.transactions = new TransactionsLinkedList();
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getUserName() {
        return userName;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            System.err.println("IllegalArgument balance");
            System.exit(-1);
        }
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void adjustBalance(double amount) {
        if (this.balance + amount < 0) {
            throw new IllegalTransactionException("Insufficient balance for this transaction.");
        }
        this.balance += amount;
    }

    public TransactionsLinkedList getTransactionsList() {
        return transactions;
    }

    @Override
    public String toString() {
        return "Id=" + identifier +
                ", Name=" + userName +
                ", Balance=" + balance;
    }
}