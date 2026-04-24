// ============================================================
// IMPLEMENTATION 1: UPI Payment
// ============================================================

public class UpiPayment implements PaymentMethod {

    private String upiId;
    private double dailyLimit;
    private double spentToday;

    public UpiPayment(String upiId, double dailyLimit) {
        this.upiId = upiId;
        this.dailyLimit = dailyLimit;
        this.spentToday = 0;
    }

    @Override
    public void validate(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (amount > 100000) throw new IllegalArgumentException("UPI limit: ₹1,00,000 per txn");
        if (spentToday + amount > dailyLimit) {
            throw new IllegalArgumentException("Daily UPI limit (₹" + dailyLimit + ") exceeded");
        }
    }

    @Override
    public boolean processPayment(double amount) {
        validate(amount);
        System.out.println("   📱 [UPI] Sending request to " + upiId);
        System.out.println("   📱 [UPI] Waiting for PIN authentication...");
        System.out.println("   📱 [UPI] Payment of ₹" + amount + " completed via UPI");
        spentToday += amount;
        return true;
    }

    @Override
    public String getMethodName() {
        return "UPI (" + upiId + ")";
    }
}
