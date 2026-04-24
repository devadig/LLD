// ============================================================
// COMPILE-TIME POLYMORPHISM (Method Overloading)
// ============================================================
// Same method name "createOrder", but different parameter lists.
// The COMPILER decides which version to call based on the
// arguments you pass. Decided at COMPILE time, not runtime.
// ============================================================

public class PaymentProcessor {

    // VERSION 1: Basic order — just amount
    public void createOrder(double amount) {
        System.out.println("📦 Order created: ₹" + amount + " (no discount, no description)");
    }

    // VERSION 2: Order with description
    public void createOrder(double amount, String description) {
        System.out.println("📦 Order created: ₹" + amount + " — " + description);
    }

    // VERSION 3: Order with description + discount
    public void createOrder(double amount, String description, double discountPercent) {
        double discounted = amount - (amount * discountPercent / 100);
        System.out.println("📦 Order created: ₹" + amount +
                " — " + description +
                " (Discount: " + discountPercent + "%, Final: ₹" + discounted + ")");
    }

    // ---- THE REAL POWER: Runtime polymorphism ----
    // This method accepts the INTERFACE type.
    // It doesn't know if it's UPI, Card, or NetBanking.
    // The JVM picks the right processPayment() AT RUNTIME.
    public void checkout(PaymentMethod method, double amount) {
        System.out.println("\n💵 Checking out ₹" + amount + " via " + method.getMethodName());
        System.out.println("   Validating...");

        try {
            boolean success = method.processPayment(amount);
            if (success) {
                System.out.println("   🎉 Transaction successful!\n");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("   🚫 Transaction failed: " + e.getMessage() + "\n");
        }
    }
}
