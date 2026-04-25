// ============================================================
// ✅ GOOD: Segregated interfaces — each focused on ONE role
// ============================================================
// Instead of ONE fat interface with 5 methods, we have
// FIVE small interfaces with 1 method each.
// Each worker implements ONLY what it actually does.
// ============================================================

// ---- Small, focused interfaces ----

interface Cookable {
    void cook(String dish);
}

interface Servable {
    void serve(int tableNumber);
}

interface Washable {
    void washDishes();
}

interface Billable {
    void manageBilling(double amount);
}

interface Cleanable {
    void cleanFloor();
}
