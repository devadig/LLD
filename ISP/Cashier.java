// ============================================================
// ✅ GOOD: Cashier implements ONLY Billable
// ============================================================

public class Cashier implements Billable {

    private String name;
    private double totalCollected;

    public Cashier(String name) {
        this.name = name;
        this.totalCollected = 0;
    }

    @Override
    public void manageBilling(double amount) {
        totalCollected += amount;
        System.out.println("   💵 Cashier " + name + " collected ₹" + amount +
                " (Total today: ₹" + totalCollected + ")");
    }

    public String getName() { return name; }
}
