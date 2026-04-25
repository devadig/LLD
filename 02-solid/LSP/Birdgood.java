// ============================================================
// ✅ GOOD: Bird hierarchy — LSP COMPLIANT
// ============================================================
// Instead of putting fly() in the base Bird class (not all
// birds fly), we separate CAPABILITIES into interfaces.
// Each bird picks ONLY the capabilities it actually has.
// ============================================================

// ---- Base: things ALL birds can do ----
abstract class Bird {
    protected String name;

    public Bird(String name) { this.name = name; }
    public String getName() { return name; }

    public void eat() {
        System.out.println("   " + name + " is eating.");
    }
}

// ---- Capability interfaces: birds opt-in ----
interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

// ---- Sparrow: can fly, can't swim ----
class Sparrow extends Bird implements Flyable {

    public Sparrow(String name) { super(name); }

    @Override
    public void fly() {
        System.out.println("   " + name + " is flying through the sky!");
    }
}

// ---- Penguin: can swim, can't fly ----
// No fly() method. No UnsupportedOperationException. Clean!
class Penguin extends Bird implements Swimmable {

    public Penguin(String name) { super(name); }

    @Override
    public void swim() {
        System.out.println("   " + name + " is swimming gracefully!");
    }
}

// ---- Duck: can fly AND swim ----
class Duck extends Bird implements Flyable, Swimmable {

    public Duck(String name) { super(name); }

    @Override
    public void fly() {
        System.out.println("   " + name + " is flying over the pond!");
    }

    @Override
    public void swim() {
        System.out.println("   " + name + " is paddling in the water!");
    }
}
