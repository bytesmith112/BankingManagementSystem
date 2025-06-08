package service;

import dao.AccountDAO;
import dao.TransactionDAO;
import model.Account;
import model.Transaction;
import util.InputValidator;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

// Service layer for business logic
public class AccountService {
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;
    private static final double INTEREST_RATE = 0.03; // 3% annual interest

    public AccountService() {
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    // Create a new account with validation
    public String createAccount(String accountNumber, String holderName, double balance, String accountType, double overdraftLimit) throws IOException {
        if (!InputValidator.isValidAccountNumber(accountNumber)) {
            return "Invalid account number! Must be 5 digits.";
        }
        if (!InputValidator.isValidName(holderName)) {
            return "Invalid holder name! Use letters and spaces only.";
        }
        if (!InputValidator.isValidBalance(balance)) {
            return "Invalid balance! Must be non-negative.";
        }
        if (!InputValidator.isValidAccountType(accountType)) {
            return "Invalid account type! Must be 'Savings' or 'Current'.";
        }
        if (!InputValidator.isValidOverdraftLimit(overdraftLimit)) {
            return "Invalid overdraft limit! Must be non-negative.";
        }

        if (accountDAO.readAccount(accountNumber) != null) {
            return "Account already exists!";
        }

        Account account = new Account(accountNumber, holderName, balance, accountType, overdraftLimit);
        accountDAO.createAccount(account);
        return "Account created successfully!";
    }

    // Read an account by account number
    public Account readAccount(String accountNumber) throws IOException {
        if (!InputValidator.isValidAccountNumber(accountNumber)) {
            throw new IllegalArgumentException("Invalid account number! Must be 5 digits.");
        }
        Account account = accountDAO.readAccount(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account not found!");
        }
        return account;
    }

    // Search accounts by holder name
    public List<Account> searchAccountsByName(String holderName) throws IOException {
        if (!InputValidator.isValidName(holderName)) {
            throw new IllegalArgumentException("Invalid holder name! Use letters and spaces only.");
        }
        List<Account> accounts = accountDAO.getAllAccounts();
        return accounts.stream()
                .filter(account -> account.getHolderName().toLowerCase().contains(holderName.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Update an account
    public String updateAccount(String accountNumber, String newHolderName, double newBalance, String newAccountType, double newOverdraftLimit) throws IOException {
        if (!InputValidator.isValidName(newHolderName)) {
            return "Invalid holder name! Use letters and spaces only.";
        }
        if (!InputValidator.isValidBalance(newBalance)) {
            return "Invalid balance! Must be non-negative.";
        }
        if (!InputValidator.isValidAccountType(newAccountType)) {
            return "Invalid account type! Must be 'Savings' or 'Current'.";
        }
        if (!InputValidator.isValidOverdraftLimit(newOverdraftLimit)) {
            return "Invalid overdraft limit! Must be non-negative.";
        }

        Account account = accountDAO.readAccount(accountNumber);
        if (account == null) {
            return "Account not found!";
        }

        account.setHolderName(newHolderName);
        account.setBalance(newBalance);
        account.setAccountType(newAccountType);
        account.setOverdraftLimit(newAccountType.equals("Current") ? newOverdraftLimit : 0);
        accountDAO.updateAccount(account);
        return "Account updated successfully!";
    }

    // Delete an account
    public String deleteAccount(String accountNumber) throws IOException {
        if (!InputValidator.isValidAccountNumber(accountNumber)) {
            return "Invalid account number! Must be 5 digits.";
        }

        if (accountDAO.deleteAccount(accountNumber)) {
            return "Account deleted successfully!";
        }
        return "Account not found!";
    }

    // Get all accounts
    public List<Account> getAllAccounts() throws IOException {
        return accountDAO.getAllAccounts();
    }

    // Calculate and add interest (for Savings accounts only)
    public String calculateInterest(String accountNumber) throws IOException {
        Account account = readAccount(accountNumber);
        if (account == null) {
            return "Account not found!";
        }
        if (!account.getAccountType().equalsIgnoreCase("Savings")) {
            return "Interest calculation is only available for Savings accounts!";
        }

        double interest = account.getBalance() * INTEREST_RATE;
        account.setBalance(account.getBalance() + interest);
        accountDAO.updateAccount(account);
        return String.format("Interest of %.2f added! New balance: %.2f", interest, account.getBalance());
    }

    // Deposit money into an account
    public String deposit(String accountNumber, double amount) throws IOException {
        if (!InputValidator.isValidAmount(amount)) {
            return "Invalid amount! Must be positive.";
        }

        Account account = readAccount(accountNumber);
        account.setBalance(account.getBalance() + amount);
        accountDAO.updateAccount(account);

        Transaction transaction = new Transaction(accountNumber, "Deposit", amount);
        transactionDAO.logTransaction(transaction);
        return String.format("Deposited %.2f successfully! New balance: %.2f", amount, account.getBalance());
    }

    // Withdraw money from an account
    public String withdraw(String accountNumber, double amount) throws IOException {
        if (!InputValidator.isValidAmount(amount)) {
            return "Invalid amount! Must be positive.";
        }

        Account account = readAccount(accountNumber);
        double availableBalance = account.getBalance();
        if (account.getAccountType().equalsIgnoreCase("Current")) {
            availableBalance += account.getOverdraftLimit();
        }

        if (amount > availableBalance) {
            return "Insufficient funds! Available balance (including overdraft): " + availableBalance;
        }

        account.setBalance(account.getBalance() - amount);
        accountDAO.updateAccount(account);

        Transaction transaction = new Transaction(accountNumber, "Withdrawal", amount);
        transactionDAO.logTransaction(transaction);
        return String.format("Withdrew %.2f successfully! New balance: %.2f", amount, account.getBalance());
    }

    // Get transaction history
    public List<Transaction> getTransactionHistory(String accountNumber) throws IOException {
        if (!InputValidator.isValidAccountNumber(accountNumber)) {
            throw new IllegalArgumentException("Invalid account number! Must be 5 digits.");
        }
        return transactionDAO.getTransactions(accountNumber);
    }
}