// ============================================================
// ❌ BAD: Chef forced to implement irrelevant methods
// ============================================================
// A Chef cooks. That's it. But this fat interface forces the
// Chef to implement serve(), washDishes(), manageBilling(),
// and cleanFloor() — all with empty bodies or exceptions.
//
// Every empty method here is a CODE SMELL screaming
// "this interface is too fat!"
// ============================================================

public class ChefBad implements RestaurantWorkerBad {

    private String name;

    public ChefBad(String name) {
        this.name = name;
    }

    @Override
    public void cook(String dish) {
        // ✅ This is what a Chef ACTUALLY does
        System.out.println("   👨‍🍳 Chef " + name + " is cooking: " + dish);
    }

    @Override
    public void serve(int tableNumber) {
        // ❌ Chef doesn't serve — but FORCED to implement
        throw new UnsupportedOperationException("Chef doesn't serve tables!");
    }

    @Override
    public void washDishes() {
        // ❌ Chef doesn't wash — but FORCED to implement
        throw new UnsupportedOperationException("Chef doesn't wash dishes!");
    }

    @Override
    public void manageBilling(double amount) {
        // ❌ Chef doesn't handle billing — but FORCED to implement
        throw new UnsupportedOperationException("Chef doesn't handle billing!");
    }

    @Override
    public void cleanFloor() {
        // ❌ Chef doesn't clean floors — but FORCED to implement
        throw new UnsupportedOperationException("Chef doesn't clean floors!");
    }
}
