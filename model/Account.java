package model;

// Represents a bank account with type (Savings/Current)
public class Account {
    private String accountNumber;
    private String holderName;
    private double balance;
    private String accountType; // "Savings" or "Current"
    private double overdraftLimit; // Applicable for Current accounts

    // Constructor
    public Account(String accountNumber, String holderName, double balance, String accountType, double overdraftLimit) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.accountType = accountType;
        this.overdraftLimit = accountType.equals("Current") ? overdraftLimit : 0;
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    // To String method for file storage
    @Override
    public String toString() {
        return accountNumber + "," + holderName + "," + balance + "," + accountType + "," + overdraftLimit;
    }

    // Static method to create Account from string
    public static Account fromString(String line) {
        String[] parts = line.split(",");
        return new Account(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3], Double.parseDouble(parts[4]));
    }
}