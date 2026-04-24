// ============================================================
// IMPLEMENTATION 3: Net Banking Payment
// ============================================================

public class NetBankingPayment implements PaymentMethod {

    private String bankName;
    private double accountBalance;

    public NetBankingPayment(String bankName, double accountBalance) {
        this.bankName = bankName;
        this.accountBalance = accountBalance;
    }

    @Override
    public void validate(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (amount > accountBalance) {
            throw new IllegalArgumentException(
                "Insufficient balance in " + bankName + "! Available: ₹" + accountBalance);
        }
    }

    @Override
    public boolean processPayment(double amount) {
        validate(amount);
        System.out.println("   🏦 [NETBANK] Redirecting to " + bankName + " portal...");
        System.out.println("   🏦 [NETBANK] User authenticated via bank login");
        System.out.println("   🏦 [NETBANK] ₹" + amount + " debited from " + bankName);
        System.out.println("   🏦 [NETBANK] New balance: ₹" + (accountBalance - amount));
        accountBalance -= amount;
        return true;
    }

    @Override
    public String getMethodName() {
        return "Net Banking (" + bankName + ")";
    }
}
