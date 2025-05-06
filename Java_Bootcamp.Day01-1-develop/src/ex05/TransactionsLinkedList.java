package ex05;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private class Node {
        Transaction transaction;
        Node next;

        Node(Transaction transaction) {
            this.transaction = transaction;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public TransactionsLinkedList() {
        head = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        Node newNode = new Node(transaction);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    @Override
    public void removeTransactionById(String id) {
        Node current = head;
        Node previous = null;

        while (current != null) {
            if (current.transaction.getId().equals(id)) {
                if (previous == null) {
                    head = current.next; // Remove head
                } else {
                    previous.next = current.next; // Bypass the current node
                }
                size--;
                return;
            }
            previous = current;
            current = current.next;
        }
        throw new TransactionNotFoundException("Transaction with ID " + id + " not found.");
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactions = new Transaction[size];
        Node current = head;
        int index = 0;

        while (current != null) {
            transactions[index++] = current.transaction;
            current = current.next;
        }
        return transactions;
    }
}