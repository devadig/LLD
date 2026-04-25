// ============================================================
//  CREATIONAL DESIGN PATTERNS — Code Examples
// ============================================================
//  One focused example per pattern showing ONLY the core structure.
//  Study the PATTERN, not implementation details.
// ============================================================


// ============================================================
//  1. SINGLETON — Only one instance exists
// ============================================================

// ❌ BAD: Anyone can create multiple instances
class DatabaseConnectionBad {
    // ... connection logic
}
DatabaseConnectionBad conn1 = new DatabaseConnectionBad();
DatabaseConnectionBad conn2 = new DatabaseConnectionBad();  // Wasteful!

// ✅ GOOD: Bill Pugh Singleton (best for most cases)
class DatabaseConnection {
    
    // Private constructor — no one can call `new`
    private DatabaseConnection() {
        // Expensive initialization
    }
    
    // Inner static class — loaded only when getInstance() called
    private static class Holder {
        private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    }
    
    // Public access point
    public static DatabaseConnection getInstance() {
        return Holder.INSTANCE;
    }
    
    public void executeQuery(String query) { /* ... */ }
}

// Usage:
DatabaseConnection db1 = DatabaseConnection.getInstance();
DatabaseConnection db2 = DatabaseConnection.getInstance();
// db1 == db2 → TRUE (same instance)

// KEY POINTS:
// → Private constructor prevents direct instantiation
// → Lazy loading (created only when first needed)
// → Thread-safe (no synchronization overhead)
// → Only ONE instance exists across entire application


// ============================================================
//  2. FACTORY METHOD — Subclasses decide what to create
// ============================================================

// ❌ BAD: if-else chain couples code to concrete classes
class NotificationServiceBad {
    void notify(String type, String message) {
        Notification notif;
        if (type.equals("EMAIL")) {
            notif = new EmailNotification();  // ❌ Tight coupling
        } else if (type.equals("SMS")) {
            notif = new SmsNotification();    // ❌ Tight coupling
        }
        notif.send(message);
    }
}

// ✅ GOOD: Factory Method pattern
// STEP 1: Product interface
interface Notification {
    void send(String message);
}

// STEP 2: Concrete products
class EmailNotification implements Notification {
    public void send(String message) {
        System.out.println("[EMAIL] " + message);
    }
}

class SmsNotification implements Notification {
    public void send(String message) {
        System.out.println("[SMS] " + message);
    }
}

// STEP 3: Creator (abstract class with factory method)
abstract class NotificationService {
    
    // Template method — shared business logic
    public final void notifyUser(String message) {
        Notification notif = createNotification();  // Factory method call
        notif.send(message);
        logNotification();  // Shared logic
    }
    
    // FACTORY METHOD — subclasses implement this
    protected abstract Notification createNotification();
    
    private void logNotification() {
        System.out.println("Logged");
    }
}

// STEP 4: Concrete creators
class EmailNotificationService extends NotificationService {
    @Override
    protected Notification createNotification() {
        return new EmailNotification();
    }
}

class SmsNotificationService extends NotificationService {
    @Override
    protected Notification createNotification() {
        return new SmsNotification();
    }
}

// Usage:
NotificationService service = new EmailNotificationService();
service.notifyUser("Order shipped!");  // Uses EmailNotification

// Add new type? Just create new classes:
class PushNotification implements Notification { /* ... */ }
class PushNotificationService extends NotificationService {
    protected Notification createNotification() {
        return new PushNotification();
    }
}
// ✅ Zero edits to existing code (OCP!)

// KEY POINTS:
// → No if-else chain (eliminated)
// → New type = new subclass (OCP)
// → Subclass decides which concrete class to create
// → Depends on interface, not concrete classes (DIP)


// ============================================================
//  3. ABSTRACT FACTORY — Families of related objects
// ============================================================

// ❌ BAD: Can accidentally mix incompatible objects
Button btn = new WindowsButton();
Checkbox chk = new MacCheckbox();  // 💀 Different families! UI broken

// ✅ GOOD: Abstract Factory ensures compatibility

// STEP 1: Product interfaces
interface Button {
    void render();
}

interface Checkbox {
    void render();
}

// STEP 2: Concrete products for Windows family
class WindowsButton implements Button {
    public void render() { System.out.println("Windows button"); }
}

class WindowsCheckbox implements Checkbox {
    public void render() { System.out.println("Windows checkbox"); }
}

// STEP 3: Concrete products for Mac family
class MacButton implements Button {
    public void render() { System.out.println("Mac button"); }
}

class MacCheckbox implements Checkbox {
    public void render() { System.out.println("Mac checkbox"); }
}

// STEP 4: Abstract Factory interface
interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// STEP 5: Concrete factories (one per family)
class WindowsFactory implements GUIFactory {
    public Button createButton() {
        return new WindowsButton();
    }
    
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}

class MacFactory implements GUIFactory {
    public Button createButton() {
        return new MacButton();
    }
    
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}

// STEP 6: Client (platform-independent!)
class Application {
    private Button button;
    private Checkbox checkbox;
    
    public Application(GUIFactory factory) {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }
    
    public void render() {
        button.render();
        checkbox.render();
    }
}

// Usage:
GUIFactory factory;
if (os.contains("Windows")) {
    factory = new WindowsFactory();
} else {
    factory = new MacFactory();
}

Application app = new Application(factory);
app.render();
// ✅ Guaranteed: All UI components from same family!

// KEY POINTS:
// → ONE factory creates ALL related objects
// → Ensures compatibility (no mixing Windows + Mac)
// → Easy to swap (change factory, everything switches)
// → Client doesn't know concrete classes


// ============================================================
//  4. BUILDER — Step-by-step construction of complex objects
// ============================================================

// ❌ BAD: Telescoping constructors
class PizzaBad {
    Pizza(String size) { }
    Pizza(String size, boolean cheese) { }
    Pizza(String size, boolean cheese, boolean pepperoni) { }
    // ... 20 more constructors
}
// Usage:
Pizza p = new Pizza("Large", true, false, true, false);
// What does the third 'false' mean? 🤷

// ✅ GOOD: Builder pattern
class Pizza {
    // Final fields (immutable)
    private final String size;
    private final boolean cheese;
    private final boolean pepperoni;
    private final boolean mushrooms;
    
    // Private constructor
    private Pizza(PizzaBuilder builder) {
        this.size = builder.size;
        this.cheese = builder.cheese;
        this.pepperoni = builder.pepperoni;
        this.mushrooms = builder.mushrooms;
    }
    
    // Static inner Builder class
    public static class PizzaBuilder {
        // Required
        private final String size;
        
        // Optional (with defaults)
        private boolean cheese = false;
        private boolean pepperoni = false;
        private boolean mushrooms = false;
        
        public PizzaBuilder(String size) {
            this.size = size;
        }
        
        // Fluent setters (return 'this' for chaining)
        public PizzaBuilder cheese() {
            this.cheese = true;
            return this;
        }
        
        public PizzaBuilder pepperoni() {
            this.pepperoni = true;
            return this;
        }
        
        public PizzaBuilder mushrooms() {
            this.mushrooms = true;
            return this;
        }
        
        // Build method
        public Pizza build() {
            return new Pizza(this);
        }
    }
}

// Usage — Readable and flexible!
Pizza margherita = new Pizza.PizzaBuilder("Medium")
    .cheese()
    .build();

Pizza supreme = new Pizza.PizzaBuilder("Large")
    .cheese()
    .pepperoni()
    .mushrooms()
    .build();

// KEY POINTS:
// → Eliminates telescoping constructors
// → Self-documenting (clear what each param is)
// → Immutable (final fields, no setters)
// → Flexible (skip optional params)
// → Thread-safe once built


// ============================================================
//  5. PROTOTYPE — Clone existing objects
// ============================================================

// ❌ BAD: Manual copying is tedious and error-prone
Shape original = fetchExpensiveShape();  // Expensive DB fetch
Shape copy = new Shape();
copy.setX(original.getX());
copy.setY(original.getY());
copy.setColor(original.getColor());
// ... 20 more fields
// Miss one? Silent bug!

// ✅ GOOD: Prototype pattern
// STEP 1: Prototype interface
interface Shape {
    Shape clone();
    void draw();
}

// STEP 2: Concrete prototypes
class Circle implements Shape {
    private int x, y;
    private int radius;
    private String color;
    
    public Circle(int x, int y, int radius, String color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }
    
    // Clone method
    @Override
    public Shape clone() {
        return new Circle(this.x, this.y, this.radius, this.color);
    }
    
    @Override
    public void draw() {
        System.out.println("Circle at (" + x + "," + y + ")");
    }
    
    public void setX(int x) { this.x = x; }
}

// STEP 3: Prototype Registry (optional but common)
class ShapeRegistry {
    private Map<String, Shape> prototypes = new HashMap<>();
    
    public ShapeRegistry() {
        // Pre-load common shapes
        prototypes.put("red-circle", new Circle(0, 0, 10, "red"));
        prototypes.put("blue-circle", new Circle(0, 0, 10, "blue"));
    }
    
    public Shape getShape(String key) {
        Shape prototype = prototypes.get(key);
        return prototype != null ? prototype.clone() : null;
    }
}

// Usage:
ShapeRegistry registry = new ShapeRegistry();

// Get clones (fast!)
Shape circle1 = registry.getShape("red-circle");
Shape circle2 = registry.getShape("red-circle");

// Modify copies (original unchanged)
((Circle) circle1).setX(100);
((Circle) circle2).setX(200);

circle1.draw();  // Circle at (100,0)
circle2.draw();  // Circle at (200,0)
// Original in registry still at (0,0)

// KEY POINTS:
// → Clone existing vs create from scratch (faster)
// → Works with interface types (don't need concrete class)
// → Easy variations (clone + tweak)
// → Each clone is independent


// ============================================================
//  HOW PATTERNS WORK TOGETHER
// ============================================================

// Example: Payment system using multiple patterns

// 1. FACTORY METHOD for payment types
abstract class PaymentService {
    void processPayment(double amount) {
        PaymentMethod method = createPaymentMethod();  // Factory method
        method.process(amount);
    }
    protected abstract PaymentMethod createPaymentMethod();
}

class UpiPaymentService extends PaymentService {
    protected PaymentMethod createPaymentMethod() {
        return new UpiPayment();
    }
}

// 2. BUILDER for complex payment config
class PaymentConfig {
    private final String merchantId;
    private final String apiKey;
    private final int timeout;
    
    private PaymentConfig(Builder b) { /* ... */ }
    
    public static class Builder {
        private String merchantId;
        private String apiKey;
        private int timeout = 30000;
        
        public Builder merchantId(String id) { this.merchantId = id; return this; }
        public Builder apiKey(String key) { this.apiKey = key; return this; }
        public Builder timeout(int t) { this.timeout = t; return this; }
        public PaymentConfig build() { return new PaymentConfig(this); }
    }
}

PaymentConfig config = new PaymentConfig.Builder()
    .merchantId("MERCH-123")
    .apiKey("abc123")
    .timeout(5000)
    .build();

// 3. SINGLETON for config manager
class PaymentConfigManager {
    private static class Holder {
        private static final PaymentConfigManager INSTANCE = new PaymentConfigManager();
    }
    
    public static PaymentConfigManager getInstance() {
        return Holder.INSTANCE;
    }
}


// ============================================================
//  QUICK REFERENCE
// ============================================================

// WHEN TO USE WHICH PATTERN:

// Need exactly ONE instance?
// → SINGLETON

// Creating objects couples your code?
// → FACTORY METHOD (one product type, many variants)

// Need compatible families of objects?
// → ABSTRACT FACTORY (many product types, must match)

// Constructor has 4+ parameters (especially optional)?
// → BUILDER

// Object creation is expensive? Need variations?
// → PROTOTYPE


// ============================================================
//  KEY TAKEAWAYS
// ============================================================

// ALL creational patterns:
// → Separate creation from usage
// → Depend on abstractions (DIP)
// → Enable extension without modification (OCP)
// → Make testing easier (inject mocks)

// SINGLETON → One instance
// FACTORY METHOD → Subclasses decide type
// ABSTRACT FACTORY → Compatible families
// BUILDER → Step-by-step construction
// PROTOTYPE → Clone + tweak
