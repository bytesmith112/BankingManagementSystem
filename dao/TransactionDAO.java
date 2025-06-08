package dao;

import model.Transaction;
import util.FileUtil;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

// Data Access Object for Transaction operations
public class TransactionDAO {
    // Log a transaction
    public void logTransaction(Transaction transaction) throws IOException {
        FileUtil.writeTransaction(transaction);
    }

    // Get transaction history for an account
    public List<Transaction> getTransactions(String accountNumber) throws IOException {
        List<Transaction> transactions = FileUtil.readTransactions();
        return transactions.stream()
                .filter(t -> t.getAccountNumber().equals(accountNumber))
                .collect(Collectors.toList());
    }
}