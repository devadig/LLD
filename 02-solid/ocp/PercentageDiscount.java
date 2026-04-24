// ============================================================
// STRATEGY 2: Percentage Discount (% off)
// ============================================================

public class PercentageDiscount implements DiscountStrategy {

    private double percentage;

    public PercentageDiscount(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be 0-100");
        }
        this.percentage = percentage;
    }

    @Override
    public double apply(double originalPrice) {
        double discount = originalPrice * (percentage / 100);
        double discounted = originalPrice - discount;
        System.out.println("   💰 [PERCENTAGE] ₹" + originalPrice + " - " +
                percentage + "% (₹" + discount + ") = ₹" + discounted);
        return discounted;
    }

    @Override
    public String getDescription() {
        return percentage + "% off";
    }
}
