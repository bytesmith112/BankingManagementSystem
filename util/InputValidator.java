package util;

// Utility class for input validation
public class InputValidator {
    // Validate account number (must be 5 digits)
    public static boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && accountNumber.matches("\\d{5}");
    }

    // Validate holder name (letters and spaces only)
    public static boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-Z\\s]+") && !name.trim().isEmpty();
    }

    // Validate balance (non-negative)
    public static boolean isValidBalance(double balance) {
        return balance >= 0;
    }

    // Validate amount for transactions (positive)
    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    // Validate account type (Savings or Current)
    public static boolean isValidAccountType(String accountType) {
        return accountType != null && (accountType.equalsIgnoreCase("Savings") || accountType.equalsIgnoreCase("Current"));
    }

    // Validate overdraft limit (non-negative)
    public static boolean isValidOverdraftLimit(double overdraftLimit) {
        return overdraftLimit >= 0;
    }
}