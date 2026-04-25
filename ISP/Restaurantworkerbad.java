// ============================================================
// ❌ BAD: FAT interface VIOLATING ISP
// ============================================================
// One interface forces ALL workers to implement ALL methods.
// Chef doesn't serve. Waiter doesn't cook. Cashier doesn't wash.
// But they're ALL forced to implement everything.
// ============================================================

public interface RestaurantWorkerBad {

    void cook(String dish);
    void serve(int tableNumber);
    void washDishes();
    void manageBilling(double amount);
    void cleanFloor();
}
