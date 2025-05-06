package ex00;

import java.util.UUID;

enum Category {
    OUTCOME,
    INCOME
}

public class Transaction {
    private String id;
    private User recipient;
    private User sender;
    private Category category;
    private double amount;


    public Transaction(User sender, User recipient, Category category, double amount) {
        this.id = UUID.randomUUID().toString(); // Generate a unique UUID
        this.sender = sender;
        this.recipient = recipient;
        this.category = category;
        this.amount = amount;

        if (category == Category.OUTCOME && amount < 0) {
            sender.adjustBalance(amount);
            recipient.adjustBalance(-amount);
        } else if (category == Category.INCOME && amount > 0) {
            recipient.adjustBalance(-amount);
            sender.adjustBalance(amount);
        } else {
            System.err.println("Invalid amount for this transaction type");
            System.exit(-1);
        }
    }

    public String getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public Category getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction ID: " + id +
                ", \nSender: " + sender.getUserName() +
                ", \nRecipient: " + recipient.getUserName() +
                ", \nCategory: " + category +
                ", \nAmount: " + amount;
    }
}