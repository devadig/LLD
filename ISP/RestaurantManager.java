// ============================================================
// ✅ RestaurantManager — Works with SPECIFIC interfaces
// ============================================================
// The manager doesn't accept "RestaurantWorker" (fat interface).
// It accepts the EXACT capability it needs for each task.
// This is type-safe: you physically CANNOT pass a Chef to
// a method that needs a Servable. Compiler catches it!
// ============================================================

import java.util.List;

public class RestaurantManager {

    // Only accepts things that CAN cook
    public void prepareOrder(Cookable cook, String dish) {
        System.out.println("\n📋 Order received: " + dish);
        cook.cook(dish);
    }

    // Only accepts things that CAN serve
    public void assignTable(Servable server, int tableNumber) {
        System.out.println("\n📋 Assigning table " + tableNumber);
        server.serve(tableNumber);
    }

    // Only accepts things that CAN handle billing
    public void processPayment(Billable biller, double amount) {
        System.out.println("\n📋 Processing payment: ₹" + amount);
        biller.manageBilling(amount);
    }

    // Only accepts things that CAN clean
    public void assignCleaning(Cleanable cleaner) {
        System.out.println("\n📋 Assigning cleaning duty");
        cleaner.cleanFloor();
    }

    // Can work with a LIST of cleaners — doesn't care if they're
    // Waiters, KitchenHelpers, or any future Cleanable type
    public void endOfDayCleanup(List<Cleanable> cleaners) {
        System.out.println("\n📋 End of day — everyone cleans!");
        for (Cleanable cleaner : cleaners) {
            cleaner.cleanFloor();
        }
    }
}
