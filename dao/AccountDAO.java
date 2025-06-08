package dao;

import model.Account;
import util.FileUtil;
import java.io.IOException;
import java.util.List;

// Data Access Object for Account CRUD operations
public class AccountDAO {
    // Create a new account
    public void createAccount(Account account) throws IOException {
        List<Account> accounts = FileUtil.readAccounts();
        accounts.add(account);
        FileUtil.writeAccounts(accounts);
    }

    // Read an account by account number
    public Account readAccount(String accountNumber) throws IOException {
        List<Account> accounts = FileUtil.readAccounts();
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    // Update an existing account
    public boolean updateAccount(Account updatedAccount) throws IOException {
        List<Account> accounts = FileUtil.readAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(updatedAccount.getAccountNumber())) {
                accounts.set(i, updatedAccount);
                FileUtil.writeAccounts(accounts);
                return true;
            }
        }
        return false;
    }

    // Delete an account by account number
    public boolean deleteAccount(String accountNumber) throws IOException {
        List<Account> accounts = FileUtil.readAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(accountNumber)) {
                accounts.remove(i);
                FileUtil.writeAccounts(accounts);
                return true;
            }
        }
        return false;
    }

    // Get all accounts
    public List<Account> getAllAccounts() throws IOException {
        return FileUtil.readAccounts();
    }
}