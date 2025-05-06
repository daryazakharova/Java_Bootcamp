package ex00;

public class User {
    private int identifier;
    private String userName;
    private double balance;

    public User(int identifier, String userName, double balance) {
        this.identifier = identifier;
        this.userName = userName;
        setBalance(balance);
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
            System.err.println("Insufficient balance for this transaction.");
            System.exit(-1);
        }
        this.balance += amount;
    }

    @Override
    public String toString() {
        return "Id=" + identifier +
                ", Name=" + userName +
                ", Balance=" + balance;
    }
}