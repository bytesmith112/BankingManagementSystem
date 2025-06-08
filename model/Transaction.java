package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Represents a transaction (deposit or withdrawal)
public class Transaction {
    private String accountNumber;
    private String type; // "Deposit" or "Withdrawal"
    private double amount;
    private LocalDateTime timestamp;

    // Constructor
    public Transaction(String accountNumber, String type, double amount) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // To String method for file storage
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return accountNumber + "," + type + "," + amount + "," + timestamp.format(formatter);
    }

    // Static method to create Transaction from string
    public static Transaction fromString(String line) {
        String[] parts = line.split(",");
        Transaction transaction = new Transaction(parts[0], parts[1], Double.parseDouble(parts[2]));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        transaction.timestamp = LocalDateTime.parse(parts[3], formatter);
        return transaction;
    }
}