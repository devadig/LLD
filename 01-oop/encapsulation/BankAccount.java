// ============================================================
// ENCAPSULATION — "Protect your object's internals"
// ============================================================
// WHY? → Without encapsulation, anyone can put your object in
//         an invalid state. Encapsulation gives YOU control
//         over what happens to your data.
// ============================================================

public class BankAccount {

    // ---- STEP 1: Make fields PRIVATE ----
    // No one outside this class can directly touch these.
    private String ownerName;
    private double balance;
    private String accountId;

    // ---- STEP 2: Control object creation via Constructor ----
    // You decide what's REQUIRED to create a valid account.
    public BankAccount(String ownerName, String accountId, double initialDeposit) {
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner name cannot be empty");
        }
        if (initialDeposit < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative");
        }
        this.ownerName = ownerName;
        this.accountId = accountId;
        this.balance = initialDeposit;
    }

    // ---- STEP 3: Expose CONTROLLED access via methods ----

    // READ-ONLY access to balance (getter, no setter!)
    public double getBalance() {
        return this.balance;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public String getAccountId() {
        return this.accountId;
    }

    // CONTROLLED modification — not a simple setter, but a
    // business operation with RULES built in.
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance += amount;
        System.out.println("✅ Deposited ₹" + amount + " | New Balance: ₹" + this.balance);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > this.balance) {
            throw new IllegalArgumentException("Insufficient funds! Balance: ₹" + this.balance);
        }
        this.balance -= amount;
        System.out.println("✅ Withdrew ₹" + amount + " | New Balance: ₹" + this.balance);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "owner='" + ownerName + '\'' +
                ", accountId='" + accountId + '\'' +
                ", balance=₹" + balance +
                '}';
    }
}
