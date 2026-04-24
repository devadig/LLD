// ============================================================
// STRATEGY 1: Flat Discount (₹ off)
// ============================================================

public class FlatDiscount implements DiscountStrategy {

    private double flatAmount;

    public FlatDiscount(double flatAmount) {
        if (flatAmount < 0) throw new IllegalArgumentException("Discount cannot be negative");
        this.flatAmount = flatAmount;
    }

    @Override
    public double apply(double originalPrice) {
        double discounted = Math.max(originalPrice - flatAmount, 0);
        System.out.println("   💰 [FLAT] ₹" + originalPrice + " - ₹" + flatAmount +
                " = ₹" + discounted);
        return discounted;
    }

    @Override
    public String getDescription() {
        return "Flat ₹" + flatAmount + " off";
    }
}
