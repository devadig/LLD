// ============================================================
// DEMO: Inheritance — The Good and The Bad
// ============================================================

public class Main {

    // Works with ANY Employee — polymorphism + inheritance together
    public static void processPayroll(Employee emp) {
        System.out.println("Processing: " + emp);
        emp.checkIn();
        double salary = emp.calculateSalary();
        emp.checkOut();
        System.out.println("   📊 Total Compensation: ₹" + salary);
        System.out.println();
    }

    public static void main(String[] args) {

        System.out.println("================================================");
        System.out.println("  INHERITANCE DEMO — Employee HR System");
        System.out.println("================================================\n");

        // ---- THE GOOD: Shared behavior, specialized calculation ----

        Employee fullTime = new FullTimeEmployee(
            "Rahul", "FT-001", "Engineering", 80000, 15);

        Employee contractor = new ContractEmployee(
            "Priya", "CT-001", "Design", 500, 160);

        Employee intern = new InternEmployee(
            "Arjun", "IN-001", "Engineering", 25000, 6);

        // Same method handles all three — thanks to inheritance
        processPayroll(fullTime);
        processPayroll(contractor);
        processPayroll(intern);

        // ============================================================
        // THE BAD: When inheritance goes WRONG
        // ============================================================
        // 
        // Imagine your PM says: "We also have Freelancers, but they
        // DON'T check in or check out — they work from anywhere."
        //
        // Problem: If FreelanceEmployee extends Employee, it INHERITS
        // checkIn() and checkOut() which don't make sense for them!
        //
        // You'd have to override them to do nothing:
        //
        //   @Override
        //   public void checkIn() {
        //       // do nothing — freelancers don't check in
        //   }
        //
        // This is a CODE SMELL. It means the inheritance hierarchy
        // is wrong. The child is inheriting behavior it doesn't want.
        //
        // ============================================================
        // THE FIX: Composition over Inheritance
        // ============================================================
        //
        // Instead of forcing checkIn/checkOut into the parent:
        //
        //   interface Attendable {
        //       void checkIn();
        //       void checkOut();
        //   }
        //
        //   interface Payable {
        //       double calculateSalary();
        //   }
        //
        //   class FullTimeEmployee implements Attendable, Payable { ... }
        //   class FreelanceEmployee implements Payable { ... }
        //       // ↑ No Attendable = No checkIn/checkOut. Clean!
        //
        // This way, each class picks ONLY the capabilities it needs.
        // No unwanted baggage.
        //
        // RULE OF THUMB:
        //   → Use inheritance for genuine "is-a" relationships
        //     where ALL parent behavior applies to the child.
        //   → Use interfaces/composition when you want to mix
        //     and match capabilities.
        // ============================================================

        System.out.println("================================================");
        System.out.println("  KEY LESSON:");
        System.out.println("  Inheritance = powerful but creates coupling.");
        System.out.println("  Ask: Does EVERY child truly need EVERY");
        System.out.println("  behavior from the parent?");
        System.out.println("  If not → use composition/interfaces instead.");
        System.out.println("================================================");
    }
}
