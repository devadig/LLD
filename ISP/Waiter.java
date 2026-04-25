// ============================================================
// ✅ GOOD: Waiter implements Servable + Cleanable
// ============================================================
// A waiter serves tables and sometimes cleans the floor.
// Two roles that genuinely belong to a waiter.
// No cook(), no wash(), no billing() — because a waiter
// doesn't do those. No dead code!
// ============================================================

public class Waiter implements Servable, Cleanable {

    private String name;

    public Waiter(String name) {
        this.name = name;
    }

    @Override
    public void serve(int tableNumber) {
        System.out.println("   🍽️ Waiter " + name + " is serving table " + tableNumber);
    }

    @Override
    public void cleanFloor() {
        System.out.println("   🧹 Waiter " + name + " is cleaning the floor");
    }

    public String getName() { return name; }
}
