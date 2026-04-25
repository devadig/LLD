// ============================================================
// DEMO: Interface Segregation Principle
// ============================================================

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        System.out.println("================================================");
        System.out.println("  SOLID #4 — Interface Segregation Principle");
        System.out.println("================================================\n");

        // ============================================================
        // ❌ THE BAD WAY: Fat interface
        // ============================================================
        System.out.println("❌ BAD: Fat interface forces irrelevant implementations");
        System.out.println("─────────────────────────────────────────\n");

        ChefBad badChef = new ChefBad("Gordon");
        badChef.cook("Pasta Carbonara");  // ✅ Works

        System.out.println("\n   Calling methods Chef was FORCED to implement:");
        try {
            badChef.serve(5);  // 💀 Crashes
        } catch (UnsupportedOperationException e) {
            System.out.println("   💀 serve() → " + e.getMessage());
        }
        try {
            badChef.washDishes();  // 💀 Crashes
        } catch (UnsupportedOperationException e) {
            System.out.println("   💀 washDishes() → " + e.getMessage());
        }
        try {
            badChef.manageBilling(500);  // 💀 Crashes
        } catch (UnsupportedOperationException e) {
            System.out.println("   💀 manageBilling() → " + e.getMessage());
        }

        WaiterBad badWaiter = new WaiterBad("James");
        System.out.println();
        badWaiter.serve(3);  // ✅ Works
        try {
            badWaiter.cook("Biryani");  // 💀 Crashes
        } catch (UnsupportedOperationException e) {
            System.out.println("   💀 cook() → " + e.getMessage());
        }

        System.out.println("\n   Problem: 4 out of 5 methods throw exceptions.");
        System.out.println("   The interface FORCED classes to lie about their");
        System.out.println("   capabilities. Every unused method is a landmine.\n");

        System.out.println("=".repeat(50) + "\n");

        // ============================================================
        // ✅ THE GOOD WAY: Segregated interfaces
        // ============================================================
        System.out.println("✅ GOOD: Segregated interfaces — each class picks its roles");
        System.out.println("─────────────────────────────────────────\n");

        // Create workers with ONLY the capabilities they actually have
        Chef chef = new Chef("Gordon");
        Waiter waiter = new Waiter("James");
        Cashier cashier = new Cashier("Anita");
        KitchenHelper helper = new KitchenHelper("Ravi");

        RestaurantManager manager = new RestaurantManager();

        // ---- Each worker used for what they ACTUALLY do ----
        manager.prepareOrder(chef, "Butter Chicken");
        manager.prepareOrder(helper, "Naan (assisting)");

        manager.assignTable(waiter, 7);

        manager.processPayment(cashier, 1850);

        manager.assignCleaning(waiter);   // Waiter CAN clean
        manager.assignCleaning(helper);   // Helper CAN clean too

        // ---- COMPILE-TIME SAFETY: Wrong combos are IMPOSSIBLE ----
        System.out.println("\n" + "=".repeat(50));
        System.out.println("  COMPILE-TIME SAFETY:");
        System.out.println("=".repeat(50) + "\n");

        System.out.println("  These would NOT compile (caught at compile time):");
        System.out.println();
        System.out.println("  manager.prepareOrder(cashier, \"Pasta\");");
        System.out.println("  → ❌ Cashier doesn't implement Cookable");
        System.out.println();
        System.out.println("  manager.assignTable(chef, 5);");
        System.out.println("  → ❌ Chef doesn't implement Servable");
        System.out.println();
        System.out.println("  manager.processPayment(waiter, 500);");
        System.out.println("  → ❌ Waiter doesn't implement Billable");
        System.out.println();
        System.out.println("  With the fat interface, all of these would COMPILE");
        System.out.println("  but CRASH at runtime. With ISP, the compiler");
        System.out.println("  catches mistakes BEFORE the code even runs!");

        // ---- Polymorphism still works perfectly ----
        System.out.println();
        manager.endOfDayCleanup(Arrays.asList(waiter, helper));

        // ============================================================
        // HOW ISP CONNECTS TO OTHER PRINCIPLES
        // ============================================================
        System.out.println("\n" + "=".repeat(50));
        System.out.println("  HOW ISP CONNECTS:");
        System.out.println("=".repeat(50));
        System.out.println();
        System.out.println("  ISP → prevents LSP violations");
        System.out.println("    Fat Bird interface with fly() → Penguin violates LSP");
        System.out.println("    Split into Flyable/Swimmable → LSP satisfied");
        System.out.println();
        System.out.println("  ISP → enables OCP");
        System.out.println("    New role (Deliverable)? Create new interface.");
        System.out.println("    DeliveryBoy implements Deliverable. Done.");
        System.out.println("    No existing interface or class changes.");
        System.out.println();
        System.out.println("  ISP → supports SRP");
        System.out.println("    Small interfaces = small, focused implementations");
        System.out.println("    Each class handles only its actual responsibilities.");
        System.out.println("=".repeat(50));
    }
}
