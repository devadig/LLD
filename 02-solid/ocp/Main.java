// ============================================================
// DEMO: Open/Closed Principle
// ============================================================

public class Main {
    public static void main(String[] args) {

        System.out.println("================================================");
        System.out.println("  SOLID #2 — Open/Closed Principle");
        System.out.println("================================================\n");

        // ============================================================
        // ❌ THE BAD WAY: if-else chain grows forever
        // ============================================================
        System.out.println("❌ BAD: if-else chain (violates OCP):");
        System.out.println("─────────────────────────────────────────\n");

        DiscountCalculatorBad bad = new DiscountCalculatorBad();
        bad.applyDiscount("FLAT", 5000, 500);
        bad.applyDiscount("PERCENTAGE", 5000, 20);
        bad.applyDiscount("BUY1GET1", 5000, 0);
        bad.applyDiscount("SEASONAL", 5000, 0);

        System.out.println("\n   Problem: Every new discount type requires");
        System.out.println("   editing this method. It NEVER stops growing.\n");
        System.out.println("=".repeat(50) + "\n");

        // ============================================================
        // ✅ THE GOOD WAY: OCP with Strategy Pattern
        // ============================================================
        System.out.println("✅ GOOD: Strategy pattern (follows OCP):");
        System.out.println("─────────────────────────────────────────\n");

        CheckoutService checkout = new CheckoutService();

        // ---- Original discounts ----
        checkout.checkout("Mechanical Keyboard", 5000, new FlatDiscount(500));
        checkout.checkout("Gaming Mouse", 3000, new PercentageDiscount(20));

        // ---- Added LATER — no edits to CheckoutService! ----
        checkout.checkout("Headphones (x2)", 4000, new BuyOneGetOneFree());

        // ---- Added EVEN LATER — still no edits! ----
        // "20% off, max ₹300" — like a Swiggy/Zomato coupon
        checkout.checkout("Monitor", 15000, new CappedPercentageDiscount(20, 300));

        // ---- A big order with capped discount ----
        checkout.checkout("Laptop", 75000, new CappedPercentageDiscount(15, 5000));

        // ============================================================
        // THE KEY INSIGHT: Look at CheckoutService.java
        // ============================================================
        System.out.println("=".repeat(50));
        System.out.println("  THE KEY INSIGHT:");
        System.out.println("=".repeat(50));
        System.out.println();
        System.out.println("  CheckoutService.checkout() was written ONCE.");
        System.out.println("  It handles Flat, Percentage, BOGO, Capped —");
        System.out.println("  and ANY future discount we haven't invented yet.");
        System.out.println();
        System.out.println("  To add a new discount type:");
        System.out.println("  1. Create a new class implementing DiscountStrategy");
        System.out.println("  2. Done.");
        System.out.println();
        System.out.println("  Files modified: 0");
        System.out.println("  Files created: 1");
        System.out.println("  Risk of breaking existing code: ZERO");
        System.out.println();
        System.out.println("  This is Open/Closed Principle in action.");
        System.out.println("=".repeat(50));
        System.out.println();

        // ============================================================
        // BONUS: OCP enables runtime flexibility
        // ============================================================
        System.out.println("BONUS: Discounts can be chosen at RUNTIME:\n");

        // Imagine these come from a database or config file
        DiscountStrategy[] availableDiscounts = {
            new FlatDiscount(200),
            new PercentageDiscount(10),
            new BuyOneGetOneFree(),
            new CappedPercentageDiscount(25, 1000)
        };

        String product = "Wireless Earbuds";
        double price = 4000;

        System.out.println("Available discounts for " + product + " (₹" + price + "):\n");
        for (int i = 0; i < availableDiscounts.length; i++) {
            System.out.println("  Option " + (i + 1) + ": " + availableDiscounts[i].getDescription());
            double finalPrice = availableDiscounts[i].apply(price);
            System.out.println("  → You pay: ₹" + finalPrice + "\n");
        }
    }
}
