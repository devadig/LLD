// ============================================================
//  CREATIONAL DESIGN PATTERNS — Complete Interview Guide
// ============================================================
//
//  These 5 patterns solve ONE problem: How to create objects
//  in a way that's flexible, testable, and decoupled.
//
//  They all follow DIP: depend on abstractions, not concrete classes.
//
// ============================================================


// ============================================================
//  OVERVIEW — What Are Creational Patterns?
// ============================================================
//
//  THE CORE PROBLEM:
//  Using `new ConcreteClass()` everywhere:
//  → Violates DIP (tight coupling to concrete classes)
//  → Violates OCP (adding new types requires editing existing code)
//  → Hard to test (can't inject mocks)
//  → Object creation logic scattered across codebase
//
//  THE SOLUTION:
//  Creational patterns separate "WHAT you create" from "HOW you create it"
//  → Centralize creation logic
//  → Work with abstractions (interfaces/abstract classes)
//  → Easy to extend with new types
//  → Easy to test (inject different implementations)
//
//  THE 5 PATTERNS:
//  1. SINGLETON      — Only one instance exists
//  2. FACTORY METHOD — Subclasses decide which class to instantiate
//  3. ABSTRACT FACTORY — Families of related objects
//  4. BUILDER        — Step-by-step construction of complex objects
//  5. PROTOTYPE      — Clone existing objects


// ============================================================
//  1. SINGLETON PATTERN
// ============================================================
//
//  PROBLEM: You need exactly ONE instance of a class shared globally.
//
//  WHY IT MATTERS:
//  → Resource control (one DB pool, not 100)
//  → Consistent state (one config manager, not multiple with different settings)
//  → Global access point (everyone gets the same instance)
//
//  REAL-WORLD EXAMPLES:
//  → Database connection pool
//  → Configuration manager
//  → Logger
//  → Cache manager
//  → Thread pool
//
//  HOW IT WORKS:
//  1. Private constructor (no one can call `new`)
//  2. Private static instance variable
//  3. Public static getInstance() method
//
//  THREE IMPLEMENTATIONS:
//
//  EAGER INITIALIZATION (Thread-safe by default):
//  → Instance created at class loading
//  → Pro: Simple, thread-safe
//  → Con: Created even if never used
//
//  LAZY INITIALIZATION (Double-checked locking):
//  → Instance created only when first needed
//  → Pro: Saves resources if never used
//  → Con: Requires volatile + synchronized (more complex)
//
//  BILL PUGH (Best for most cases):
//  → Uses inner static class
//  → Pro: Lazy + thread-safe + no synchronization overhead
//  → Con: Slightly less intuitive
//
//  WHEN TO USE:
//  ✅ Database connection pools
//  ✅ Configuration managers
//  ✅ Logging systems
//  ✅ Cache managers
//  ✅ Thread pools
//
//  WHEN NOT TO USE:
//  ❌ Business objects (User, Order, Product)
//  ❌ Entities/DTOs
//  ❌ Anything that needs multiple instances
//
//  PITFALL — VIOLATES DIP:
//  DatabaseConnection db = DatabaseConnection.getInstance();
//  → This appears in 50 classes → tight coupling
//  
//  BETTER: Inject via interface
//  interface ConnectionPool { }
//  class DatabaseConnection implements ConnectionPool { }
//  
//  class OrderService {
//      private ConnectionPool pool;
//      public OrderService(ConnectionPool pool) { this.pool = pool; }
//  }
//  → Now OrderService doesn't know it's a Singleton
//
//  CONNECTION TO SOLID:
//  → SRP: Debatable violation (manages lifecycle + business logic)
//  → DIP: Often violated if used with getInstance() directly
//  → Better: Treat as implementation detail, inject via interface
//
//  INTERVIEW TIP:
//  "Singleton ensures only one instance exists. For example, a database
//  connection pool. I'd use Bill Pugh implementation for lazy thread-safe
//  creation. But I'd inject it via interface to avoid tight coupling."


// ============================================================
//  2. FACTORY METHOD PATTERN
// ============================================================
//
//  PROBLEM: Creating objects with `new` couples code to concrete classes.
//           Every new type requires editing existing code (violates OCP).
//
//  WHY IT MATTERS:
//  → Eliminates if-else chains
//  → New type = new subclass (zero edits to existing code)
//  → Easy to test (inject different factory)
//  → Separates creation from usage
//
//  REAL-WORLD EXAMPLES:
//  → Document readers (PDF, Word, Excel)
//  → Notification services (Email, SMS, Push)
//  → Payment processors (UPI, Card, NetBanking)
//  → UI frameworks (different button styles per platform)
//
//  HOW IT WORKS:
//  1. Product interface (what we're creating)
//  2. Concrete products (EmailNotification, SmsNotification)
//  3. Creator abstract class with:
//     → Template method (business logic)
//     → Abstract factory method (returns Product)
//  4. Concrete creators (each overrides factory method)
//
//  THE KEY DIFFERENCE FROM SIMPLE FACTORY:
//  
//  SIMPLE FACTORY (not GoF pattern):
//  class NotificationFactory {
//      static Notification create(String type) {
//          if (type.equals("EMAIL")) return new Email();
//          // Still has if-else chain → violates OCP
//      }
//  }
//  
//  FACTORY METHOD (GoF pattern):
//  abstract class NotificationService {
//      void notify(String msg) {
//          Notification n = createNotification();  // Factory method
//          n.send(msg);
//      }
//      protected abstract Notification createNotification();
//  }
//  
//  class EmailService extends NotificationService {
//      protected Notification createNotification() {
//          return new EmailNotification();
//      }
//  }
//  → No if-else, new type = new subclass
//
//  WHEN TO USE:
//  ✅ Framework code (users extend your classes)
//  ✅ Plugin systems
//  ✅ Multiple variants that need different setup
//  ✅ Types will grow over time
//
//  WHEN NOT TO USE:
//  ❌ Closed set of 3-5 types that won't grow (use Simple Factory)
//  ❌ No shared logic between types (just use interface)
//  ❌ Over-engineering simple creation
//
//  CONNECTION TO SOLID:
//  ✅ SRP: Creator handles business logic, subclasses handle creation
//  ✅ OCP: New type = new subclass, zero edits
//  ✅ DIP: Depends on Product interface, not concrete classes
//  ✅ LSP: All creators are substitutable
//
//  INTERVIEW TIP:
//  "Factory Method lets subclasses decide which class to instantiate.
//  For example, NotificationService has abstract createNotification().
//  EmailService returns EmailNotification, SmsService returns SmsNotification.
//  New WhatsApp type? New WhatsAppService class. Existing code untouched.
//  This follows OCP — open for extension, closed for modification."


// ============================================================
//  3. ABSTRACT FACTORY PATTERN
// ============================================================
//
//  PROBLEM: Need to create FAMILIES of related objects that must work together.
//           Want to ensure they're compatible (no mixing Windows button with Mac checkbox).
//
//  WHY IT MATTERS:
//  → Guarantees consistency (all objects from same family)
//  → Easy to swap families (Windows → Mac, just change factory)
//  → No accidental mixing (WindowsButton + MacCheckbox impossible)
//  → Decoupling (client doesn't know concrete classes)
//
//  REAL-WORLD EXAMPLES:
//  → Cross-platform UI (Windows/Mac/Linux themes)
//  → Database access (MySQL/PostgreSQL/Oracle families)
//  → Document formats (PDF/Word readers+writers+formatters)
//  → Game themes (Fantasy/SciFi characters+weapons+maps)
//
//  HOW IT WORKS:
//  1. Product interfaces (Button, Checkbox, Textbox)
//  2. Concrete products for each family:
//     → WindowsButton, WindowsCheckbox, WindowsTextbox
//     → MacButton, MacCheckbox, MacTextbox
//  3. Abstract factory interface (createButton, createCheckbox, createTextbox)
//  4. Concrete factories (WindowsFactory, MacFactory)
//  5. Client uses ONE factory to create ALL objects
//
//  FACTORY METHOD vs ABSTRACT FACTORY:
//  
//  FACTORY METHOD:
//  → Creates ONE product type
//  → Example: NotificationService.createNotification()
//  → Multiple variants of single product
//  
//  ABSTRACT FACTORY:
//  → Creates FAMILIES of products
//  → Example: GUIFactory.createButton() + createCheckbox() + createTextbox()
//  → Multiple related products that must match
//
//  MEMORY TRICK:
//  → Factory Method = ONE product, many variants
//  → Abstract Factory = MANY products, must be compatible
//
//  WHEN TO USE:
//  ✅ Cross-platform applications
//  ✅ Multiple database vendors
//  ✅ Theming systems
//  ✅ Document format families
//  
//  WHEN NOT TO USE:
//  ❌ Products are unrelated
//  ❌ Only one product type needed
//  ❌ No compatibility requirement
//
//  CONNECTION TO SOLID:
//  ✅ OCP: Add new family (LinuxFactory) without editing existing code
//  ✅ DIP: Client depends on GUIFactory interface
//  ✅ SRP: Each factory creates one family
//  ✅ LSP: All factories are substitutable
//
//  INTERVIEW TIP:
//  "Abstract Factory creates families of related objects. For example,
//  WindowsFactory creates WindowsButton + WindowsCheckbox + WindowsTextbox
//  (all matching). MacFactory creates Mac versions (all matching).
//  Client gets ONE factory and uses it to create everything — guaranteed
//  no mixing. Switch OS? Change factory. Client code unchanged."


// ============================================================
//  4. BUILDER PATTERN
// ============================================================
//
//  PROBLEM: Creating complex objects with many optional parameters.
//           Constructors with 10+ params are unreadable and error-prone.
//
//  WHY IT MATTERS:
//  → Eliminates telescoping constructors
//  → Self-documenting code (readable method chains)
//  → Immutability (object can't change after build())
//  → Validation before object creation
//  → Flexible (skip optional params)
//
//  REAL-WORLD EXAMPLES:
//  → Pizza with toppings
//  → HTTP request with headers/body/timeout
//  → Custom laptop configuration
//  → Database query with filters/sorting/pagination
//
//  HOW IT WORKS:
//  1. Product class with final fields (immutable)
//  2. Private constructor (only Builder can call it)
//  3. Static inner Builder class with:
//     → Required params in constructor
//     → Optional params with default values
//     → Fluent setters (return `this` for chaining)
//     → build() method (creates Product)
//
//  TELESCOPING CONSTRUCTOR PROBLEM:
//  Pizza(String size)
//  Pizza(String size, boolean cheese)
//  Pizza(String size, boolean cheese, boolean pepperoni)
//  Pizza(String size, boolean cheese, boolean pepperoni, boolean mushrooms)
//  // 20 more constructors...
//  
//  new Pizza("Large", true, false, true, false, true);
//  // What's the third parameter? No idea!
//
//  BUILDER SOLUTION:
//  new Pizza.Builder("Large")
//      .cheese()
//      .mushrooms()
//      .extraCheese()
//      .build();
//  // Self-documenting! Clear what each param is.
//
//  IMMUTABILITY:
//  → Builder is mutable (during construction)
//  → Product is immutable (after build())
//  → Thread-safe once built
//  → No setters on Product
//
//  VALIDATION:
//  public Pizza build() {
//      if (size == null) throw new IllegalStateException("Size required");
//      if (extraCheese && !cheese) throw new IllegalStateException(...);
//      return new Pizza(this);
//  }
//
//  WHEN TO USE:
//  ✅ 4+ constructor parameters (especially optional)
//  ✅ Need immutability
//  ✅ Step-by-step construction
//  ✅ Many combinations of parameters
//
//  WHEN NOT TO USE:
//  ❌ Simple objects (2-3 params → use constructor)
//  ❌ All fields required (no optional params)
//  ❌ Object needs to be mutable
//
//  DIRECTOR PATTERN (OPTIONAL):
//  Pre-configured builders for common scenarios:
//  
//  class PizzaDirector {
//      static Pizza buildMargherita() {
//          return new Pizza.Builder("Medium").cheese().build();
//      }
//      static Pizza buildSupreme() {
//          return new Pizza.Builder("Large").cheese().pepperoni()
//              .mushrooms().olives().build();
//      }
//  }
//
//  CONNECTION TO SOLID:
//  ✅ SRP: Builder handles construction, Product handles behavior
//  ✅ OCP: Add new optional params without breaking existing code
//  ✅ Immutability prevents bugs
//
//  INTERVIEW TIP:
//  "Builder constructs complex objects step-by-step. For example, Pizza
//  with optional toppings. Instead of constructors with 10 params, I use
//  fluent methods: new Pizza.Builder('Large').cheese().pepperoni().build().
//  This gives readability, immutability, and validation. The product has
//  final fields — can't change after build() — making it thread-safe."


// ============================================================
//  5. PROTOTYPE PATTERN
// ============================================================
//
//  PROBLEM: Object creation is expensive (DB fetch, file I/O, complex setup).
//           Need variations of similar objects.
//           Don't know concrete class at runtime (working with interface).
//
//  WHY IT MATTERS:
//  → Fast creation (clone existing vs create from scratch)
//  → Works with interface types (don't need to know concrete class)
//  → Easy variations (clone + tweak)
//  → Avoid subclassing for every variation
//
//  REAL-WORLD EXAMPLES:
//  → Game characters (spawn 100 enemies by cloning prototype)
//  → Shape editors (duplicate shapes)
//  → Database config (clone expensive config, modify slightly)
//  → Document templates (clone template, fill in data)
//
//  HOW IT WORKS:
//  1. Prototype interface with clone() method
//  2. Concrete classes implement clone()
//  3. Client calls clone() to get a copy
//  4. Modify copy as needed (original unchanged)
//
//  PROTOTYPE REGISTRY (COMMON PATTERN):
//  Store common prototypes in a map:
//  
//  class ShapeRegistry {
//      Map<String, Shape> prototypes;
//      
//      ShapeRegistry() {
//          prototypes.put("red-circle", new Circle(0, 0, 10, "red"));
//          prototypes.put("blue-square", new Square(0, 0, 20, "blue"));
//      }
//      
//      Shape getShape(String key) {
//          return prototypes.get(key).clone();
//      }
//  }
//  
//  Shape s1 = registry.getShape("red-circle");  // Clone
//  Shape s2 = registry.getShape("red-circle");  // Another clone
//
//  SHALLOW vs DEEP COPY:
//  
//  SHALLOW COPY (dangerous with reference types):
//  → Copies field values
//  → If field is reference, both objects point to SAME object
//  → Modify copy → original affected too!
//  
//  DEEP COPY (safe):
//  → Recursively copies everything
//  → Copy and original are completely independent
//  
//  public Employee clone() {
//      Employee copy = new Employee();
//      copy.name = this.name;  // String immutable, safe
//      copy.address = this.address.clone();  // Deep copy Address
//      return copy;
//  }
//
//  WHEN TO USE:
//  ✅ Object creation is expensive
//  ✅ Need variations of similar objects
//  ✅ Working with interface types at runtime
//  ✅ Avoid subclassing for every variation
//
//  WHEN NOT TO USE:
//  ❌ Simple objects (just use `new`)
//  ❌ No expensive setup
//  ❌ No variations needed
//
//  BUILDER vs PROTOTYPE:
//  
//  BUILDER:
//  → Complex construction with many optional params
//  → Example: Car with 15 optional features
//  → Build from scratch with custom config
//  
//  PROTOTYPE:
//  → Quick variations by cloning
//  → Example: Spawn 100 enemies (clone 1 prototype)
//  → Clone existing + tweak
//
//  CONNECTION TO SOLID:
//  ✅ OCP: Add new prototypes without changing code
//  ✅ DIP: Depends on Shape interface
//  ✅ LSP: All clones are valid substitutes
//
//  INTERVIEW TIP:
//  "Prototype clones existing objects instead of creating from scratch.
//  For example, spawning game enemies. Creating one Warrior is expensive
//  (load assets, initialize AI). I create one prototype, then clone it
//  100 times — instant. Each clone is independent. Works even when I
//  only know it's a GameCharacter interface, not the concrete class."


// ============================================================
//  QUICK COMPARISON TABLE
// ============================================================
//
//  | Pattern          | Problem                           | When to Use                     |
//  |------------------|-----------------------------------|---------------------------------|
//  | Singleton        | Need exactly ONE instance         | DB pool, logger, config         |
//  | Factory Method   | Creating objects couples code     | Extensible variants             |
//  | Abstract Factory | Need compatible family of objects | Cross-platform, themes          |
//  | Builder          | Too many constructor params       | Complex objects, immutability   |
//  | Prototype        | Expensive object creation         | Clone + tweak, spawn many       |


// ============================================================
//  HOW CREATIONAL PATTERNS CONNECT TO SOLID
// ============================================================
//
//  ALL creational patterns support SOLID:
//
//  SRP → Each pattern separates creation from usage
//  OCP → Add new types without editing existing code
//  LSP → All implementations are substitutable
//  DIP → Depend on abstractions (interfaces), inject dependencies
//
//  SPECIFIC CONNECTIONS:
//  
//  Singleton + DIP:
//  ❌ BAD: OrderService calls DatabaseConnection.getInstance() directly
//  ✅ GOOD: OrderService depends on ConnectionPool interface, injected
//  
//  Factory Method + OCP:
//  ❌ BAD: if-else chain for each type
//  ✅ GOOD: New type = new subclass, zero edits
//  
//  Abstract Factory + LSP:
//  ✅ All factories (Windows, Mac, Linux) are substitutable
//  
//  Builder + Immutability:
//  ✅ Final fields prevent bugs, enable thread-safety
//  
//  Prototype + DIP:
//  ✅ Works with Shape interface, not concrete classes


// ============================================================
//  INTERVIEW STRATEGY
// ============================================================
//
//  FOR EACH PATTERN, EXPLAIN:
//  1. THE PROBLEM (what breaks without it)
//  2. THE SOLUTION (how the pattern fixes it)
//  3. REAL EXAMPLE (Pizza, Notification, GUI)
//  4. WHEN TO USE (specific scenarios)
//  5. CONNECTION TO SOLID (especially OCP and DIP)
//
//  COMMON QUESTIONS:
//  
//  "When would you use Factory Method vs Abstract Factory?"
//  → Factory Method: ONE product, many variants (Notification types)
//  → Abstract Factory: MANY products, must match (Windows UI family)
//  
//  "When would you use Builder vs Prototype?"
//  → Builder: Complex construction with optional params
//  → Prototype: Clone existing + tweak for variations
//  
//  "How does Singleton violate SOLID?"
//  → Violates DIP if accessed via getInstance() everywhere
//  → Fix: Inject via interface
//  
//  "Why use Prototype when you can just copy manually?"
//  → Works with interface types (don't know concrete class)
//  → Centralized cloning logic
//  → Deep copy handled correctly
//
//  ALWAYS CONNECT TO REAL CODE:
//  → Java: StringBuilder (Builder pattern)
//  → Spring: Singleton beans
//  → Collections.sort() uses Strategy (Factory Method variation)


// ============================================================
//  FINAL TIPS
// ============================================================
//
//  1. Know the DIFFERENCES
//     Simple Factory vs Factory Method (most confused!)
//     Factory Method vs Abstract Factory
//     Builder vs Prototype
//
//  2. Give CONCRETE EXAMPLES
//     Don't just define. Show Pizza, Notification, Shape.
//
//  3. Explain the WHY
//     "Singleton prevents resource waste by ensuring one instance"
//     Not just "Singleton restricts instantiation"
//
//  4. Show you understand TRADEOFFS
//     → Singleton gives global access but tight coupling
//     → Factory Method follows OCP but more classes
//     → Know when NOT to use each pattern
//
//  5. Connect to FRAMEWORKS
//     → Spring beans are Singletons
//     → ORMs use Abstract Factory (different DB families)
//     → StringBuilder is Builder pattern
