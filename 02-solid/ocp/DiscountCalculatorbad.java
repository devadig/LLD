// ============================================================
// ❌ BAD: Discount system VIOLATING Open/Closed Principle
// ============================================================
// Every new discount type = edit this class.
// This method is NEVER "done" — it keeps growing.
// Each edit risks breaking existing discount logic.
// ============================================================

public class DiscountCalculatorBad {

    public double applyDiscount(String discountType, double originalPrice, double value) {

        if (discountType.equals("FLAT")) {
            // Flat ₹ off
            double discounted = originalPrice - value;
            System.out.println("   [FLAT] ₹" + originalPrice + " - ₹" + value +
                    " = ₹" + discounted);
            return Math.max(discounted, 0);
        }
        else if (discountType.equals("PERCENTAGE")) {
            // Percentage off
            double discount = originalPrice * (value / 100);
            double discounted = originalPrice - discount;
            System.out.println("   [PERCENTAGE] ₹" + originalPrice + " - " +
                    value + "% = ₹" + discounted);
            return discounted;
        }
        // Week 2: PM says "Add buy-one-get-one" → EDIT this method
        else if (discountType.equals("BUY1GET1")) {
            double discounted = originalPrice / 2;
            System.out.println("   [BUY1GET1] ₹" + originalPrice +
                    " / 2 = ₹" + discounted);
            return discounted;
        }
        // Week 3: PM says "Add seasonal discount" → EDIT again
        else if (discountType.equals("SEASONAL")) {
            double discount = originalPrice * 0.3;  // 30% off
            double discounted = originalPrice - discount;
            System.out.println("   [SEASONAL] ₹" + originalPrice +
                    " - 30% = ₹" + discounted);
            return discounted;
        }
        // Week 4: PM says "Add loyalty discount"
        // Week 5: PM says "Add coupon discount"
        // Week 6: PM says "Add first-purchase discount"
        // ... This NEVER ends. And every edit risks breaking
        //     existing discounts that were working fine.

        System.out.println("   [UNKNOWN] No discount applied.");
        return originalPrice;
    }
}
