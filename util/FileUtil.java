package util;

import model.Account;
import model.Transaction;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Utility class for file operations
public class FileUtil {
    private static final String ACCOUNT_FILE_PATH = "accounts.txt";
    private static final String TRANSACTION_FILE_PATH = "transactions.txt";

    // Read all accounts from the file
    public static List<Account> readAccounts() throws IOException {
        List<Account> accounts = new ArrayList<>();
        File file = new File(ACCOUNT_FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
            return accounts;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    accounts.add(Account.fromString(line));
                }
            }
        }
        return accounts;
    }

    // Write all accounts to the file
    public static void writeAccounts(List<Account> accounts) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNT_FILE_PATH))) {
            for (Account account : accounts) {
                writer.write(account.toString());
                writer.newLine();
            }
        }
    }

    // Read all transactions from the file
    public static List<Transaction> readTransactions() throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(TRANSACTION_FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
            return transactions;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    transactions.add(Transaction.fromString(line));
                }
            }
        }
        return transactions;
    }

    // Write a transaction to the file (append mode)
    public static void writeTransaction(Transaction transaction) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTION_FILE_PATH, true))) {
            writer.write(transaction.toString());
            writer.newLine();
        }
    }
}