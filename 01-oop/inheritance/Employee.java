// ============================================================
// INHERITANCE — "Reuse shared behavior, specialize what varies"
// ============================================================
// The parent class holds COMMON fields and behavior.
// Children inherit everything and only add/override what's
// DIFFERENT for them.
// ============================================================

public abstract class Employee {

    // ---- Shared fields (common to ALL employees) ----
    private String name;
    private String employeeId;
    private String department;

    public Employee(String name, String employeeId, String department) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
        this.employeeId = employeeId;
        this.department = department;
    }

    // ---- Shared behavior (same for ALL employees) ----
    // Written ONCE here, inherited by every subclass.
    // Fix a bug here → fixed everywhere.
    public void checkIn() {
        System.out.println("   ✅ " + name + " (" + employeeId + ") checked in at " +
                java.time.LocalTime.now().withNano(0));
    }

    public void checkOut() {
        System.out.println("   👋 " + name + " (" + employeeId + ") checked out at " +
                java.time.LocalTime.now().withNano(0));
    }

    // ---- Abstract method: each type calculates salary DIFFERENTLY ----
    // This is where SPECIALIZATION happens.
    public abstract double calculateSalary();

    // ---- Getters ----
    public String getName() { return name; }
    public String getEmployeeId() { return employeeId; }
    public String getDepartment() { return department; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", id='" + employeeId + '\'' +
                ", dept='" + department + '\'' +
                '}';
    }
}
