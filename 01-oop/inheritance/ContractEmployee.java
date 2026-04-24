// ============================================================
// CHILD 2: Contract Employee
// ============================================================
// Same shared behavior (checkIn, checkOut) — inherited.
// Unique: paid by the HOUR, salary depends on hours worked.
// ============================================================

public class ContractEmployee extends Employee {

    private double hourlyRate;
    private int hoursWorked;

    public ContractEmployee(String name, String id, String dept,
                            double hourlyRate, int hoursWorked) {
        super(name, id, dept);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        double total = hourlyRate * hoursWorked;
        System.out.println("   💰 [CONTRACT] ₹" + hourlyRate + "/hr × " +
                           hoursWorked + " hrs = ₹" + total);
        return total;
    }
}
