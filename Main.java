// Source code is decompiled from a .class file using FernFlower decompiler.
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import model.Account;
import model.Transaction;
import service.AccountService;

public class Main {
   private static final AccountService accountService = new AccountService();
   private static final Scanner scanner;

   public Main() {
   }

   public static void main(String[] var0) {
      while(true) {
         displayMenu();
         int var1 = getUserChoice();

         try {
            switch (var1) {
               case 1:
                  createAccount();
                  break;
               case 2:
                  viewAccount();
                  break;
               case 3:
                  updateAccount();
                  break;
               case 4:
                  deleteAccount();
                  break;
               case 5:
                  viewAllAccounts();
                  break;
               case 6:
                  calculateInterest();
                  break;
               case 7:
                  deposit();
                  break;
               case 8:
                  withdraw();
                  break;
               case 9:
                  viewTransactionHistory();
                  break;
               case 10:
                  searchAccountsByName();
                  break;
               case 11:
                  System.out.println("Exiting...");
                  return;
               default:
                  System.out.println("Invalid choice! Please try again.");
            }
         } catch (Exception var3) {
            System.out.println("Error: " + var3.getMessage());
         }
      }
   }

   private static void displayMenu() {
      System.out.println("\n=== Banking Management System ===");
      System.out.println("1. Create Account");
      System.out.println("2. View Account");
      System.out.println("3. Update Account");
      System.out.println("4. Delete Account");
      System.out.println("5. View All Accounts");
      System.out.println("6. Calculate Interest (Savings Accounts)");
      System.out.println("7. Deposit Money");
      System.out.println("8. Withdraw Money");
      System.out.println("9. View Transaction History");
      System.out.println("10. Search Accounts by Holder Name");
      System.out.println("11. Exit");
      System.out.print("Enter your choice: ");
   }

   private static int getUserChoice() {
      try {
         return Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException var1) {
         return -1;
      }
   }

   private static void createAccount() throws Exception {
      System.out.print("Enter account number (5 digits): ");
      String var0 = scanner.nextLine();
      System.out.print("Enter holder name: ");
      String var1 = scanner.nextLine();
      System.out.print("Enter initial balance: ");
      double var2 = Double.parseDouble(scanner.nextLine());
      System.out.print("Enter account type (Savings/Current): ");
      String var4 = scanner.nextLine();
      double var5 = 0.0;
      if (var4.equalsIgnoreCase("Current")) {
         System.out.print("Enter overdraft limit: ");
         var5 = Double.parseDouble(scanner.nextLine());
      }

      String var7 = accountService.createAccount(var0, var1, var2, var4, var5);
      System.out.println(var7);
   }

   private static void viewAccount() throws Exception {
      System.out.print("Enter account number: ");
      String var0 = scanner.nextLine();
      Account var1 = accountService.readAccount(var0);
      System.out.println("Account Details: " + String.valueOf(var1));
   }

   private static void updateAccount() throws Exception {
      System.out.print("Enter account number: ");
      String var0 = scanner.nextLine();
      System.out.print("Enter new holder name: ");
      String var1 = scanner.nextLine();
      System.out.print("Enter new balance: ");
      double var2 = Double.parseDouble(scanner.nextLine());
      System.out.print("Enter new account type (Savings/Current): ");
      String var4 = scanner.nextLine();
      double var5 = 0.0;
      if (var4.equalsIgnoreCase("Current")) {
         System.out.print("Enter new overdraft limit: ");
         var5 = Double.parseDouble(scanner.nextLine());
      }

      String var7 = accountService.updateAccount(var0, var1, var2, var4, var5);
      System.out.println(var7);
   }

   private static void deleteAccount() throws Exception {
      System.out.print("Enter account number: ");
      String var0 = scanner.nextLine();
      String var1 = accountService.deleteAccount(var0);
      System.out.println(var1);
   }

   private static void viewAllAccounts() throws Exception {
      @SuppressWarnings("rawtypes")
      List var0 = accountService.getAllAccounts();
      if (var0.isEmpty()) {
         System.out.println("No accounts found!");
      } else {
         System.out.println("All Accounts:");
         Iterator var1 = var0.iterator();

         while(var1.hasNext()) {
            Account var2 = (Account)var1.next();
            System.out.println(var2);
         }
      }

   }

   private static void calculateInterest() throws Exception {
      System.out.print("Enter account number: ");
      String var0 = scanner.nextLine();
      String var1 = accountService.calculateInterest(var0);
      System.out.println(var1);
   }

   private static void deposit() throws Exception {
      System.out.print("Enter account number: ");
      String var0 = scanner.nextLine();
      System.out.print("Enter amount to deposit: ");
      double var1 = Double.parseDouble(scanner.nextLine());
      String var3 = accountService.deposit(var0, var1);
      System.out.println(var3);
   }

   private static void withdraw() throws Exception {
      System.out.print("Enter account number: ");
      String var0 = scanner.nextLine();
      System.out.print("Enter amount to withdraw: ");
      double var1 = Double.parseDouble(scanner.nextLine());
      String var3 = accountService.withdraw(var0, var1);
      System.out.println(var3);
   }

   private static void viewTransactionHistory() throws Exception {
      System.out.print("Enter account number: ");
      String var0 = scanner.nextLine();
      List var1 = accountService.getTransactionHistory(var0);
      if (var1.isEmpty()) {
         System.out.println("No transactions found for this account!");
      } else {
         System.out.println("Transaction History:");
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            Transaction var3 = (Transaction)var2.next();
            System.out.println(var3);
         }
      }

   }

   private static void searchAccountsByName() throws Exception {
      System.out.print("Enter holder name to search: ");
      String var0 = scanner.nextLine();
      List var1 = accountService.searchAccountsByName(var0);
      if (var1.isEmpty()) {
         System.out.println("No accounts found with that name!");
      } else {
         System.out.println("Matching Accounts:");
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            Account var3 = (Account)var2.next();
            System.out.println(var3);
         }
      }

   }

   static {
      scanner = new Scanner(System.in);
   }
}
