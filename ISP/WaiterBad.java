// ============================================================
// ❌ BAD: Waiter forced to implement irrelevant methods
// ============================================================

public class WaiterBad implements RestaurantWorkerBad {

    private String name;

    public WaiterBad(String name) {
        this.name = name;
    }

    @Override
    public void cook(String dish) {
        // ❌ Waiter doesn't cook
        throw new UnsupportedOperationException("Waiter doesn't cook!");
    }

    @Override
    public void serve(int tableNumber) {
        // ✅ This is what a Waiter ACTUALLY does
        System.out.println("   🍽️ Waiter " + name + " is serving table " + tableNumber);
    }

    @Override
    public void washDishes() {
        // ❌ Waiter doesn't wash dishes
        throw new UnsupportedOperationException("Waiter doesn't wash dishes!");
    }

    @Override
    public void manageBilling(double amount) {
        // ❌ Waiter doesn't handle billing
        throw new UnsupportedOperationException("Waiter doesn't handle billing!");
    }

    @Override
    public void cleanFloor() {
        // ❌ Waiter doesn't clean floors
        throw new UnsupportedOperationException("Waiter doesn't clean floors!");
    }
}
