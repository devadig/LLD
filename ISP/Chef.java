// ============================================================
// ✅ GOOD: Chef implements ONLY Cookable
// ============================================================
// No empty methods. No exceptions. No dead code.
// Chef cooks. Period. Clean and honest.
// ============================================================

public class Chef implements Cookable {

    private String name;

    public Chef(String name) {
        this.name = name;
    }

    @Override
    public void cook(String dish) {
        System.out.println("   👨‍🍳 Chef " + name + " is cooking: " + dish);
    }

    public String getName() { return name; }
}
