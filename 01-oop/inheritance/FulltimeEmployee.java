// ============================================================
// CHILD 1: Full-Time Employee
// ============================================================
// Gets name, id, department, checkIn(), checkOut() for FREE
// from Employee. Only defines what's UNIQUE to full-time:
// → monthly salary + bonus
// ============================================================

public class FullTimeEmployee extends Employee {

    private double monthlySalary;
    private double bonusPercentage;

    public FullTimeEmployee(String name, String id, String dept,
                            double monthlySalary, double bonusPercentage) {
        super(name, id, dept);  // Call parent constructor
        this.monthlySalary = monthlySalary;
        this.bonusPercentage = bonusPercentage;
    }

    @Override
    public double calculateSalary() {
        double annual = monthlySalary * 12;
        double bonus = annual * (bonusPercentage / 100);
        System.out.println("   💰 [FULL-TIME] Base: ₹" + annual +
                           " + Bonus(" + bonusPercentage + "%): ₹" + bonus);
        return annual + bonus;
    }
}
