// ============================================================
// DEMO: See encapsulation in action
// ============================================================

public class Main {
    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("  ENCAPSULATION DEMO — BankAccount");
        System.out.println("========================================\n");

        // ---- VALID object creation ----
        BankAccount acc = new BankAccount("Rahul", "ACC-1001", 5000);
        System.out.println("Created: " + acc);
        System.out.println();

        // ---- Controlled operations ----
        acc.deposit(3000);
        acc.withdraw(2000);
        System.out.println("After transactions: " + acc);
        System.out.println();

        // ---- ENCAPSULATION PREVENTS BAD THINGS ----

        // ❌ Can't do this anymore:
        // acc.balance = -5000;        // COMPILE ERROR — balance is private
        // acc.ownerName = "";          // COMPILE ERROR — ownerName is private

        // ❌ Can't withdraw more than balance:
        System.out.println("--- Trying to withdraw ₹50000 (more than balance) ---");
        try {
            acc.withdraw(50000);
        } catch (IllegalArgumentException e) {
            System.out.println("🚫 BLOCKED: " + e.getMessage());
        }

        // ❌ Can't deposit negative amount:
        System.out.println("\n--- Trying to deposit -1000 ---");
        try {
            acc.deposit(-1000);
        } catch (IllegalArgumentException e) {
            System.out.println("🚫 BLOCKED: " + e.getMessage());
        }

        // ❌ Can't create account with empty name:
        System.out.println("\n--- Trying to create account with empty name ---");
        try {
            BankAccount bad = new BankAccount("", "ACC-9999", 1000);
        } catch (IllegalArgumentException e) {
            System.out.println("🚫 BLOCKED: " + e.getMessage());
        }

        // ✅ Read-only access works fine
        System.out.println("\n--- Read-only access ---");
        System.out.println("Owner: " + acc.getOwnerName());
        System.out.println("Balance: ₹" + acc.getBalance());
        System.out.println("Account: " + acc.getAccountId());
    }
}
