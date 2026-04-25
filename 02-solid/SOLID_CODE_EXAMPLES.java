// ============================================================
//  SOLID PRINCIPLES — Code Examples (Abstract/Essential)
// ============================================================
//  One focused example per principle showing ONLY the core concept.
//  Study the STRUCTURE, not implementation details.
// ============================================================


// ============================================================
//  S — SINGLE RESPONSIBILITY PRINCIPLE
// ============================================================
//  Problem: One class doing too many things

// ❌ BAD: Invoice handles business + database + PDF + email
class InvoiceBad {
    double calculateTotal() { /* business logic */ }
    void saveToDatabase() { /* persistence */ }
    void generatePDF() { /* presentation */ }
    void sendEmail() { /* notification */ }
    // ONE class, FOUR reasons to change. DBA, designer, business, ops can all break this.
}

// ✅ GOOD: Each class has ONE responsibility
class Invoice {
    double calculateTotal() { /* business logic ONLY */ }
}

class InvoiceRepository {
    void save(Invoice inv) { /* persistence ONLY */ }
}

class InvoicePDFGenerator {
    void generate(Invoice inv) { /* presentation ONLY */ }
}

class InvoiceNotifier {
    void sendEmail(Invoice inv) { /* notification ONLY */ }
}
// Change DB? Only InvoiceRepository changes.
// Change PDF? Only InvoicePDFGenerator changes.
// Each change is ISOLATED.


// ============================================================
//  O — OPEN/CLOSED PRINCIPLE
// ============================================================
//  Problem: Every new feature requires editing existing code

// ❌ BAD: Growing if-else chain
class DiscountCalculatorBad {
    double apply(String type, double price) {
        if (type.equals("FLAT")) return price - 500;
        else if (type.equals("PERCENTAGE")) return price * 0.9;
        else if (type.equals("BOGO")) return price / 2;
        // Every new discount = EDIT this method. Never stops growing.
    }
}

// ✅ GOOD: Interface + implementations (Strategy Pattern)
interface DiscountStrategy {
    double apply(double price);
}

class FlatDiscount implements DiscountStrategy {
    private double amount;
    public double apply(double price) { return price - amount; }
}

class PercentageDiscount implements DiscountStrategy {
    private double percent;
    public double apply(double price) { return price * (1 - percent/100); }
}

class BuyOneGetOne implements DiscountStrategy {
    public double apply(double price) { return price / 2; }
}

class CheckoutService {
    // Works with ANY DiscountStrategy — existing or future
    double checkout(double price, DiscountStrategy discount) {
        return discount.apply(price);
    }
}
// New discount? Create new class. CheckoutService NEVER changes.
// OPEN for extension (new class), CLOSED for modification (no edits).


// ============================================================
//  L — LISKOV SUBSTITUTION PRINCIPLE
// ============================================================
//  Problem: Child breaks parent's contract

// ❌ BAD: Square extends Rectangle but breaks setWidth/setHeight contract
class RectangleBad {
    int width, height;
    void setWidth(int w) { width = w; }
    void setHeight(int h) { height = h; }
    int getArea() { return width * height; }
}

class SquareBad extends RectangleBad {
    @Override
    void setWidth(int w) { 
        width = w; 
        height = w;  // ⚠️ SURPRISE! Caller expected only width to change
    }
    @Override
    void setHeight(int h) { 
        width = h;   // ⚠️ SURPRISE! Caller expected only height to change
        height = h; 
    }
}

// Test:
void testRectangle(RectangleBad rect) {
    rect.setWidth(20);
    rect.setHeight(5);
    assert rect.getArea() == 100;  // Expects 20 × 5 = 100
}
// testRectangle(new RectangleBad()) → ✅ passes (100)
// testRectangle(new SquareBad())    → ❌ fails (25, because 5 × 5)
// LSP VIOLATED: Can't substitute Square for Rectangle

// ✅ GOOD: Both implement Shape, no inheritance
interface Shape {
    int getArea();
}

class Rectangle implements Shape {
    private int width, height;
    void setWidth(int w) { width = w; }
    void setHeight(int h) { height = h; }
    public int getArea() { return width * height; }
}

class Square implements Shape {
    private int side;
    void setSide(int s) { side = s; }
    public int getArea() { return side * side; }
}
// Each has its own clean API. No broken contracts.

// Another example: Bird hierarchy
// ❌ BAD:
class BirdBad {
    void fly() { /* flies */ }
}
class PenguinBad extends BirdBad {
    @Override
    void fly() { throw new UnsupportedOperationException(); }
    // LSP VIOLATED: bird.fly() crashes if bird is Penguin
}

// ✅ GOOD: Capability interfaces
interface Flyable { void fly(); }
interface Swimmable { void swim(); }

class Sparrow implements Flyable {
    public void fly() { /* actually flies */ }
}

class Penguin implements Swimmable {
    public void swim() { /* actually swims */ }
    // No fly() method → can't crash
}
// LSP satisfied: Any Flyable can fly. Any Swimmable can swim.


// ============================================================
//  I — INTERFACE SEGREGATION PRINCIPLE
// ============================================================
//  Problem: Fat interface forces classes to implement methods they don't use

// ❌ BAD: One fat interface
interface RestaurantWorkerBad {
    void cook();
    void serve();
    void washDishes();
    void manageBilling();
}

class ChefBad implements RestaurantWorkerBad {
    public void cook() { /* actual work */ }
    public void serve() { throw new UnsupportedOperationException(); }
    public void washDishes() { throw new UnsupportedOperationException(); }
    public void manageBilling() { throw new UnsupportedOperationException(); }
    // Forced to implement 3 methods Chef doesn't do!
}

// ✅ GOOD: Small, focused interfaces
interface Cookable { void cook(); }
interface Servable { void serve(); }
interface Washable { void washDishes(); }
interface Billable { void manageBilling(); }

class Chef implements Cookable {
    public void cook() { /* only this */ }
}

class Waiter implements Servable, Cleanable {
    public void serve() { /* ... */ }
    public void clean() { /* ... */ }
    // Pick only what you need
}

class RestaurantManager {
    void prepareOrder(Cookable cook) {
        cook.cook();
    }
    void assignTable(Servable server) {
        server.serve();
    }
}
// manager.prepareOrder(cashier) → WON'T COMPILE (Cashier isn't Cookable)
// Compile-time safety! With fat interface, it would compile but crash at runtime.


// ============================================================
//  D — DEPENDENCY INVERSION PRINCIPLE
// ============================================================
//  Problem: High-level depends on low-level concrete classes

// ❌ BAD: OrderService creates its dependencies
class OrderServiceBad {
    private MySQLDatabase db = new MySQLDatabase();  // ❌ tight coupling
    private EmailNotifier notifier = new EmailNotifier();  // ❌ tight coupling
    
    void placeOrder(Order order) {
        db.save(order);
        notifier.send(order);
    }
}
// Can't swap MySQL → Postgres without editing OrderService
// Can't test without real MySQL database

// ✅ GOOD: OrderService depends on abstractions, dependencies injected
interface Database {
    void save(Order order);
}

interface Notifier {
    void send(Order order);
}

class MySQLDatabase implements Database {
    public void save(Order order) { /* MySQL-specific */ }
}

class PostgreSQLDatabase implements Database {
    public void save(Order order) { /* Postgres-specific */ }
}

class EmailNotifier implements Notifier {
    public void send(Order order) { /* email-specific */ }
}

class SmsNotifier implements Notifier {
    public void send(Order order) { /* SMS-specific */ }
}

class OrderService {
    private final Database db;
    private final Notifier notifier;
    
    // CONSTRUCTOR INJECTION — dependencies come from OUTSIDE
    public OrderService(Database db, Notifier notifier) {
        this.db = db;
        this.notifier = notifier;
    }
    
    void placeOrder(Order order) {
        db.save(order);       // Doesn't know if MySQL, Postgres, Mock
        notifier.send(order); // Doesn't know if Email, SMS, Push
    }
}

// Usage — swap implementations without changing OrderService:
Database mysql = new MySQLDatabase();
Notifier email = new EmailNotifier();
OrderService service1 = new OrderService(mysql, email);

Database postgres = new PostgreSQLDatabase();
Notifier sms = new SmsNotifier();
OrderService service2 = new OrderService(postgres, sms);  // SAME OrderService class!

// Testing — easy with mocks:
class MockDatabase implements Database {
    boolean saveCalled = false;
    public void save(Order o) { saveCalled = true; }
}
MockDatabase mockDb = new MockDatabase();
MockNotifier mockNotif = new MockNotifier();
OrderService testService = new OrderService(mockDb, mockNotif);
testService.placeOrder(order);
assert mockDb.saveCalled;  // No real database needed!


// ============================================================
//  HOW PRINCIPLES WORK TOGETHER — Complete Example
// ============================================================

// A payment system showing ALL 5 principles:

// S — Each class has one responsibility
class PaymentService { }      // Business logic only
class PaymentRepository { }   // Persistence only
class PaymentNotifier { }     // Notification only

// O — New payment method = new class, not editing existing code
interface PaymentMethod {
    boolean process(double amount);
}
class CreditCard implements PaymentMethod { }
class UPI implements PaymentMethod { }
class NetBanking implements PaymentMethod { }
// New: class Crypto implements PaymentMethod { }  ← just add, don't edit

// L — Any PaymentMethod can substitute in processPayment()
void processPayment(PaymentMethod method, double amt) {
    method.process(amt);  // Works with CreditCard, UPI, NetBanking, Crypto
}

// I — Small focused interface (only process()), not fat interface
interface PaymentMethod {
    boolean process(double amount);  // Just this, not validate() + refund() + dispute() + ...
}

// D — PaymentService depends on interface, not concrete class
class PaymentService {
    private final PaymentMethod method;
    
    public PaymentService(PaymentMethod method) {  // Injected
        this.method = method;
    }
}


// ============================================================
//  KEY TAKEAWAYS
// ============================================================
//
//  SRP → Split responsibilities into separate classes
//  OCP → Use interfaces/abstraction to add features via new classes
//  LSP → Children must honor parent contracts (use interfaces for capabilities)
//  ISP → Split fat interfaces into small focused ones
//  DIP → Depend on interfaces, inject dependencies
//
//  They all connect:
//  SRP gives focused classes → OCP extends them → LSP ensures safe substitution
//  → ISP provides right interfaces → DIP injects them
