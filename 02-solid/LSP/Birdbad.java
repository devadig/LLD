// ============================================================
// ❌ BAD: Bird hierarchy VIOLATING LSP
// ============================================================
// Bird has fly(). Penguin extends Bird but CAN'T fly.
// Any code doing bird.fly() crashes with Penguin.
// ============================================================

public class BirdBad {

    protected String name;

    public BirdBad(String name) {
        this.name = name;
    }

    public void eat() {
        System.out.println("   " + name + " is eating.");
    }

    // Not ALL birds can fly — but we put it in the base class!
    public void fly() {
        System.out.println("   " + name + " is flying high!");
    }
}
