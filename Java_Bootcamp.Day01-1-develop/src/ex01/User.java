package ex01;

public class User {
    private int identifier;
    private String userName;
    private double balance;

    public User(String userName, double balance) {
        this.userName = userName;
        setBalance(balance);
        this.identifier = UserIdsGenerator.getInstance().generateId();
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

    @Override
    public String toString() {
        return "Id=" + identifier +
                ", Name=" + userName +
                ", Balance=" + balance;
    }
}