// ============================================================
// IMPLEMENTATION 2: Credit Card Payment
// ============================================================

public class CreditCardPayment implements PaymentMethod {

    private String cardNumber;
    private double creditLimit;
    private double usedCredit;

    public CreditCardPayment(String cardNumber, double creditLimit) {
        this.cardNumber = cardNumber;
        this.creditLimit = creditLimit;
        this.usedCredit = 0;
    }

    private String getMaskedCard() {
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }

    @Override
    public void validate(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (usedCredit + amount > creditLimit) {
            throw new IllegalArgumentException(
                "Credit limit exceeded! Available: ₹" + (creditLimit - usedCredit));
        }
    }

    @Override
    public boolean processPayment(double amount) {
        validate(amount);
        System.out.println("   💳 [CARD] Charging card " + getMaskedCard());
        System.out.println("   💳 [CARD] Contacting bank for authorization...");
        System.out.println("   💳 [CARD] OTP verified. ₹" + amount + " charged.");
        System.out.println("   💳 [CARD] Remaining credit: ₹" + (creditLimit - usedCredit - amount));
        usedCredit += amount;
        return true;
    }

    @Override
    public String getMethodName() {
        return "Credit Card (" + getMaskedCard() + ")";
    }
}
