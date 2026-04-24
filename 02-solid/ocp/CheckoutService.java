// ============================================================
// ✅ CheckoutService — CLOSED for modification
// ============================================================
// This class works with ANY DiscountStrategy.
// When new discount types are added, this class does NOT change.
// It is CLOSED for modification, OPEN for extension
// (via new DiscountStrategy implementations).
//
// THIS is the class that OCP protects. It's tested, deployed,
// working in production. We never need to touch it again.
// ============================================================

public class CheckoutService {

    // This method accepts the INTERFACE.
    // It doesn't know (or care) if it's Flat, Percentage, BOGO,
    // Capped, Loyalty, Seasonal, or any future discount.
    public double checkout(String itemName, double price, DiscountStrategy discount) {

        System.out.println("🛒 Checkout: " + itemName + " — ₹" + price);
        System.out.println("   Applying: " + discount.getDescription());

        double finalPrice = discount.apply(price);

        System.out.println("   ✅ Final Price: ₹" + finalPrice);
        System.out.println();
        return finalPrice;
    }

    // Overloaded version: no discount
    public double checkout(String itemName, double price) {
        System.out.println("🛒 Checkout: " + itemName + " — ₹" + price + " (no discount)");
        System.out.println("   ✅ Final Price: ₹" + price);
        System.out.println();
        return price;
    }
}
