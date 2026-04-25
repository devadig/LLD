// ============================================================
// DEMO: Liskov Substitution Principle
// ============================================================

public class Main {
    public static void main(String[] args) {

        System.out.println("================================================");
        System.out.println("  SOLID #3 — Liskov Substitution Principle");
        System.out.println("================================================\n");

        // ============================================================
        // ❌ VIOLATION 1: Rectangle-Square Problem
        // ============================================================
        System.out.println("❌ VIOLATION 1: Rectangle-Square Problem");
        System.out.println("─────────────────────────────────────────\n");

        // This method was written for Rectangles
        // It expects: set width → only width changes
        System.out.println("Testing with Rectangle:");
        RectangleBad rect = new RectangleBad(10, 5);
        testRectangle(rect);  // ✅ Works as expected

        System.out.println("Testing with Square (substituted in):");
        RectangleBad square = new SquareBad(10);  // LSP says this should work
        testRectangle(square);  // ❌ WRONG area! Unexpected behavior!

        System.out.println();

        // ============================================================
        // ❌ VIOLATION 2: Bird-Penguin Problem
        // ============================================================
        System.out.println("❌ VIOLATION 2: Bird-Penguin Problem");
        System.out.println("─────────────────────────────────────────\n");

        BirdBad sparrow = new BirdBad("Sparrow");
        makeBirdFly(sparrow);  // ✅ Works fine

        BirdBad penguin = new PenguinBad("Emperor Penguin");
        System.out.println("Substituting Penguin where Bird is expected:");
        try {
            makeBirdFly(penguin);  // 💀 CRASHES!
        } catch (UnsupportedOperationException e) {
            System.out.println("   💀 CRASHED: " + e.getMessage());
        }

        System.out.println("\n" + "=".repeat(50) + "\n");

        // ============================================================
        // ✅ FIX 1: Shape interface (Rectangle & Square are siblings)
        // ============================================================
        System.out.println("✅ FIX 1: Shape interface (no broken inheritance)");
        System.out.println("─────────────────────────────────────────\n");

        Shape[] shapes = {
            new RectangleGood(10, 5),
            new SquareGood(7),
            new RectangleGood(3, 8),
            new SquareGood(4)
        };

        // This loop works PERFECTLY with both types. No surprises.
        for (Shape shape : shapes) {
            System.out.println("   " + shape.describe() + " → Area: " + shape.getArea());
        }

        // Each shape has its OWN API:
        System.out.println("\n   Rectangle uses setWidth/setHeight (independent):");
        RectangleGood r = new RectangleGood(10, 5);
        r.setWidth(20);
        System.out.println("   After setWidth(20): " + r.describe());

        System.out.println("\n   Square uses setSide (single value, no confusion):");
        SquareGood s = new SquareGood(10);
        s.setSide(20);
        System.out.println("   After setSide(20): " + s.describe());

        System.out.println();

        // ============================================================
        // ✅ FIX 2: Capability interfaces (Flyable, Swimmable)
        // ============================================================
        System.out.println("✅ FIX 2: Capability interfaces");
        System.out.println("─────────────────────────────────────────\n");

        // All birds can eat — this works with ANY bird
        Bird[] allBirds = { new Sparrow("Jack"), new Penguin("Happy Feet"), new Duck("Donald") };
        System.out.println("All birds eat (base behavior — always safe):");
        for (Bird bird : allBirds) {
            bird.eat();
        }

        System.out.println();

        // Only flyable birds are asked to fly — type-safe!
        Flyable[] flyingBirds = { new Sparrow("Tweety"), new Duck("Daffy") };
        System.out.println("Only Flyable birds fly (no Penguin here!):");
        for (Flyable flyer : flyingBirds) {
            flyer.fly();  // ✅ Always safe — everything in this array CAN fly
        }

        System.out.println();

        // Only swimmable birds are asked to swim
        Swimmable[] swimmingBirds = { new Penguin("Skipper"), new Duck("Daffy") };
        System.out.println("Only Swimmable birds swim:");
        for (Swimmable swimmer : swimmingBirds) {
            swimmer.swim();  // ✅ Always safe
        }

        System.out.println();

        // Duck can do both!
        System.out.println("Duck can do BOTH (implements Flyable + Swimmable):");
        Duck duck = new Duck("Darkwing");
        duck.eat();
        duck.fly();
        duck.swim();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("  LSP SUMMARY:");
        System.out.println("=".repeat(50));
        System.out.println();
        System.out.println("  The test: Can you SUBSTITUTE a child for its parent");
        System.out.println("  and have everything STILL WORK correctly?");
        System.out.println();
        System.out.println("  ❌ Square for Rectangle → setWidth breaks (area wrong)");
        System.out.println("  ❌ Penguin for Bird → fly() crashes");
        System.out.println("  ✅ Any Shape → getArea() always works");
        System.out.println("  ✅ Any Flyable → fly() always works");
        System.out.println("  ✅ Any Swimmable → swim() always works");
        System.out.println("=".repeat(50));
    }

    // ---- Helper: Tests a "rectangle" by setting width ----
    static void testRectangle(RectangleBad rect) {
        rect.setWidth(20);
        rect.setHeight(5);
        int expectedArea = 20 * 5;  // 100 — the NATURAL expectation
        int actualArea = rect.getArea();
        System.out.println("   Set width=20, height=5");
        System.out.println("   Expected area: " + expectedArea);
        System.out.println("   Actual area:   " + actualArea);
        System.out.println("   " + (expectedArea == actualArea ? "✅ CORRECT" : "❌ WRONG! LSP VIOLATED") + "\n");
    }

    // ---- Helper: Makes a bird fly ----
    static void makeBirdFly(BirdBad bird) {
        System.out.println("   Asking " + bird.name + " to fly...");
        bird.fly();
    }
}
