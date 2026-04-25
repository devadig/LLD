// ============================================================
// ✅ GOOD: KitchenHelper implements Cookable + Washable + Cleanable
// ============================================================
// A junior kitchen worker who helps cook, washes dishes,
// and cleans the floor. Three roles — all genuine.
// Still no serve() or billing() because that's not their job.
// 
// THIS is the power of ISP: mix and match capabilities!
// ============================================================

public class KitchenHelper implements Cookable, Washable, Cleanable {

    private String name;

    public KitchenHelper(String name) {
        this.name = name;
    }

    @Override
    public void cook(String dish) {
        System.out.println("   🍳 Helper " + name + " is assisting with: " + dish);
    }

    @Override
    public void washDishes() {
        System.out.println("   🧽 Helper " + name + " is washing dishes");
    }

    @Override
    public void cleanFloor() {
        System.out.println("   🧹 Helper " + name + " is mopping the kitchen floor");
    }

    public String getName() { return name; }
}
