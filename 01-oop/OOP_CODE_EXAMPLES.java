// ============================================================
//  OOP FUNDAMENTALS — Code Examples (Abstract/Essential)
// ============================================================
//  One focused example per pillar showing ONLY the core concept.
//  Study the STRUCTURE, not implementation details.
// ============================================================


// ============================================================
//  ENCAPSULATION — "Bundle data + methods, restrict access"
// ============================================================

// ❌ BAD: Public fields — no protection
class BankAccountBad {
    public double balance;  // Anyone can access directly
    public String name;
}

// Usage:
BankAccountBad acc = new BankAccountBad();
acc.balance = -5000;  // 💀 Negative balance! No one stopped it.
acc.name = "";        // 💀 Empty name! No validation.

// ✅ GOOD: Private fields + controlled methods
class BankAccount {
    // STEP 1: Hide the data
    private double balance;
    private String accountId;
    
    // STEP 2: Provide controlled access
    public BankAccount(String id, double initialDeposit) {
        if (initialDeposit < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative");
        }
        this.accountId = id;
        this.balance = initialDeposit;
    }
    
    // Read-only access (getter, no setter!)
    public double getBalance() {
        return balance;
    }
    
    // NOT a dumb setter — business operation with RULES
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        balance += amount;
    }
    
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        balance -= amount;
    }
}

// Usage:
BankAccount acc = new BankAccount("ACC-001", 5000);
// acc.balance = -5000;  // ❌ Won't compile — balance is private
acc.withdraw(50000);     // ❌ Throws exception — withdraw() enforces rules
acc.deposit(3000);       // ✅ Works — proper validation

// KEY POINTS:
// → Fields are PRIVATE — direct access blocked
// → Access via PUBLIC methods — with validation
// → Expose BEHAVIOR (deposit/withdraw) not data (setBalance)


// ============================================================
//  ABSTRACTION — "Define WHAT, hide HOW"
// ============================================================

// ❌ BAD: Without abstraction — if-else chain
class NotificationServiceBad {
    void send(String type, String recipient, String message) {
        if (type.equals("EMAIL")) {
            // SMTP logic here...
        } else if (type.equals("SMS")) {
            // Twilio logic here...
        } else if (type.equals("PUSH")) {
            // Firebase logic here...
        }
        // Every new type = edit this method
    }
}

// ✅ GOOD: With abstraction — interface + implementations
abstract class NotificationService {
    
    // TEMPLATE METHOD — shared behavior
    public final void send(String recipient, String message) {
        // Step 1: Validate (shared logic)
        if (recipient == null || recipient.isEmpty()) {
            throw new IllegalArgumentException("Recipient cannot be empty");
        }
        
        // Step 2: Actually deliver — THIS is abstracted
        // Subclass decides HOW
        deliverNotification(recipient, message);
        
        // Step 3: Log success (shared logic)
        System.out.println("Notification sent successfully");
    }
    
    // ABSTRACT METHOD — the "WHAT"
    // Each type implements HOW
    protected abstract void deliverNotification(String recipient, String message);
}

class EmailNotification extends NotificationService {
    @Override
    protected void deliverNotification(String recipient, String message) {
        // Email-specific logic: SMTP, HTML formatting, etc.
        System.out.println("[EMAIL] Sending to " + recipient);
    }
}

class SmsNotification extends NotificationService {
    @Override
    protected void deliverNotification(String recipient, String message) {
        // SMS-specific logic: Twilio API, 160 char limit, etc.
        System.out.println("[SMS] Sending to " + recipient);
    }
}

class PushNotification extends NotificationService {
    @Override
    protected void deliverNotification(String recipient, String message) {
        // Push-specific logic: Firebase, payload building, etc.
        System.out.println("[PUSH] Sending to " + recipient);
    }
}

// Usage — same method, different implementations
void sendAlert(NotificationService service, String recipient, String msg) {
    service.send(recipient, msg);  // Don't know or care which type
}

sendAlert(new EmailNotification(), "user@example.com", "Alert!");
sendAlert(new SmsNotification(), "+91-9876543210", "Alert!");
sendAlert(new PushNotification(), "device-token-123", "Alert!");

// KEY POINTS:
// → Abstract class defines the TEMPLATE (shared steps)
// → Abstract method defines the CONTRACT (what must be implemented)
// → Concrete classes implement the DETAILS (how to do it)
// → Caller works with abstraction — doesn't see implementation


// ============================================================
//  INHERITANCE — "Reuse shared behavior, specialize differences"
// ============================================================

// ❌ BAD: Code duplication without inheritance
class FullTimeEmployeeBad {
    String name, id;
    void checkIn() { /* same logic */ }
    void checkOut() { /* same logic */ }
    double calculateSalary() { /* different */ }
}

class ContractEmployeeBad {
    String name, id;          // Duplicated
    void checkIn() { /* same logic */ }   // Duplicated
    void checkOut() { /* same logic */ }  // Duplicated
    double calculateSalary() { /* different */ }
}
// Add InternEmployee? Copy-paste again. Bug in checkIn()? Fix in 3 places.

// ✅ GOOD: Inheritance — shared code in parent
abstract class Employee {
    // SHARED fields — inherited by all children
    private String name;
    private String employeeId;
    
    public Employee(String name, String employeeId) {
        this.name = name;
        this.employeeId = employeeId;
    }
    
    // SHARED behavior — inherited by all children
    // Written ONCE, used by all
    public void checkIn() {
        System.out.println(name + " checked in");
    }
    
    public void checkOut() {
        System.out.println(name + " checked out");
    }
    
    // ABSTRACT method — each type does it differently
    public abstract double calculateSalary();
    
    // Getters
    public String getName() { return name; }
    public String getId() { return employeeId; }
}

class FullTimeEmployee extends Employee {
    private double monthlySalary;
    private double bonusPercentage;
    
    public FullTimeEmployee(String name, String id, double salary, double bonus) {
        super(name, id);  // Call parent constructor
        this.monthlySalary = salary;
        this.bonusPercentage = bonus;
    }
    
    @Override
    public double calculateSalary() {
        double annual = monthlySalary * 12;
        double bonus = annual * (bonusPercentage / 100);
        return annual + bonus;
    }
}

class ContractEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;
    
    public ContractEmployee(String name, String id, double rate, int hours) {
        super(name, id);
        this.hourlyRate = rate;
        this.hoursWorked = hours;
    }
    
    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }
}

// Usage:
Employee fullTime = new FullTimeEmployee("Rahul", "FT-001", 80000, 15);
Employee contract = new ContractEmployee("Priya", "CT-001", 500, 160);

fullTime.checkIn();   // ✅ Inherited from Employee
fullTime.calculateSalary();  // ✅ FullTime's own implementation

// KEY POINTS:
// → Parent holds COMMON fields and behavior
// → Children inherit everything and add UNIQUE parts
// → Fix checkIn() bug → fixed in ONE place (parent)
// → calculateSalary() varies → each child implements it


// ============================================================
//  POLYMORPHISM — "Same interface, many forms"
// ============================================================

// Example showing BOTH types of polymorphism

// --- COMPILE-TIME POLYMORPHISM (Overloading) ---
class Calculator {
    // Same method name, different parameters
    // Compiler picks which to call based on arguments
    
    int add(int a, int b) {
        return a + b;
    }
    
    double add(double a, double b) {
        return a + b;
    }
    
    int add(int a, int b, int c) {
        return a + b + c;
    }
}

Calculator calc = new Calculator();
calc.add(5, 10);        // Calls add(int, int)
calc.add(5.5, 10.5);    // Calls add(double, double)
calc.add(5, 10, 15);    // Calls add(int, int, int)
// Decided at COMPILE time based on arguments


// --- RUNTIME POLYMORPHISM (Overriding) ⭐ ---
interface PaymentMethod {
    boolean processPayment(double amount);
    String getMethodName();
}

class UpiPayment implements PaymentMethod {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("[UPI] Processing ₹" + amount);
        return true;
    }
    
    @Override
    public String getMethodName() {
        return "UPI";
    }
}

class CreditCardPayment implements PaymentMethod {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("[CARD] Processing ₹" + amount);
        return true;
    }
    
    @Override
    public String getMethodName() {
        return "Credit Card";
    }
}

class NetBankingPayment implements PaymentMethod {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("[NETBANK] Processing ₹" + amount);
        return true;
    }
    
    @Override
    public String getMethodName() {
        return "Net Banking";
    }
}

// THE POWER: Single method works with ALL payment types
class PaymentProcessor {
    // Accepts the INTERFACE — doesn't know concrete type
    void checkout(PaymentMethod method, double amount) {
        System.out.println("Processing via " + method.getMethodName());
        method.processPayment(amount);  // JVM picks the right version AT RUNTIME
        System.out.println("Payment complete!");
    }
}

// Usage:
PaymentProcessor processor = new PaymentProcessor();

PaymentMethod upi = new UpiPayment();
PaymentMethod card = new CreditCardPayment();
PaymentMethod netBank = new NetBankingPayment();

// SAME method call → different behavior
processor.checkout(upi, 1500);      // Calls UPI's processPayment()
processor.checkout(card, 25000);    // Calls Card's processPayment()
processor.checkout(netBank, 8000);  // Calls NetBank's processPayment()

// Works with COLLECTIONS too
PaymentMethod[] methods = { new UpiPayment(), new CreditCardPayment(), new NetBankingPayment() };
for (PaymentMethod method : methods) {
    processor.checkout(method, 1000);  // Each behaves correctly
}

// KEY POINTS:
// → Reference type: PaymentMethod (interface)
// → Actual object: UpiPayment, CreditCard, etc.
// → JVM looks at ACTUAL object to decide which method to call
// → No if-else, no instanceof — clean polymorphic code
// → Add new payment type? Just implement PaymentMethod. Checkout unchanged.


// ============================================================
//  ALL 4 PILLARS WORKING TOGETHER — Complete Example
// ============================================================

// A notification system showing all 4 pillars:

// ENCAPSULATION — Each class protects its data
abstract class Notification {
    private String id;           // PRIVATE — protected
    private String recipient;    // PRIVATE — protected
    
    public Notification(String id, String recipient) {
        this.id = id;
        this.recipient = recipient;
    }
    
    protected String getRecipient() { return recipient; }  // Controlled access
}

// ABSTRACTION — Define WHAT, subclasses define HOW
abstract class Notification {
    // Template method — shared steps
    public final void send(String message) {
        validate();
        deliver(message);  // Abstract — subclass defines HOW
        log();
    }
    
    protected abstract void deliver(String message);  // WHAT
    
    private void validate() { /* shared validation */ }
    private void log() { /* shared logging */ }
}

// INHERITANCE — Reuse parent's code
class EmailNotification extends Notification {
    @Override
    protected void deliver(String message) {
        // Email-specific delivery
    }
}

class SmsNotification extends Notification {
    @Override
    protected void deliver(String message) {
        // SMS-specific delivery
    }
}

// POLYMORPHISM — Treat uniformly
void sendBulkNotifications(Notification[] notifications, String message) {
    for (Notification notif : notifications) {
        notif.send(message);  // Works with Email, SMS, Push, anything
    }
}


// ============================================================
//  KEY TAKEAWAYS
// ============================================================
//
//  ENCAPSULATION → private fields + public methods with validation
//  ABSTRACTION   → abstract class/interface to hide implementation
//  INHERITANCE   → extends to reuse parent's code
//  POLYMORPHISM  → method overriding for flexible, extensible code
//
//  They work together:
//  → Encapsulation protects data
//  → Abstraction simplifies usage
//  → Inheritance eliminates duplication
//  → Polymorphism enables uniform processing
//
//  Remember: SOLID builds on these 4 pillars!
