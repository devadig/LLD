// ============================================================
//  SOLID PRINCIPLES — Complete Interview Guide
// ============================================================
//
//  These 5 principles work TOGETHER to create maintainable code.
//  Learn the WHY, not just the WHAT.
//
// ============================================================


// ============================================================
//  S — SINGLE RESPONSIBILITY PRINCIPLE (SRP)
// ============================================================
//
//  RULE: A class should have only ONE REASON TO CHANGE.
//
//  MEANING: One class = one responsibility = one actor/stakeholder
//
//  WHY IT MATTERS:
//  → Isolated changes (change DB logic without touching business logic)
//  → Independent testing (test Invoice without needing a database)
//  → Team scalability (different devs work on different classes, no conflicts)
//
//  HOW TO IDENTIFY VIOLATION:
//  1. Can't describe the class in one sentence WITHOUT "and"
//     ❌ "Invoice calculates totals AND saves to DB AND generates PDF"
//  2. Changing one feature risks breaking another
//  3. Class imports unrelated libraries (SMTP + JDBC + PDF library)
//
//  COMMON MISCONCEPTION:
//  "A class should do only one thing" ← TOO VAGUE
//  Invoice has calculateSubtotal(), calculateTax(), calculateTotal()
//  → That's 3 methods but ONE responsibility (business logic)
//
//  THE FIX:
//  ❌ Invoice.java (500 lines: business + DB + PDF + email)
//  ✅ Invoice.java           → business logic only
//     InvoiceRepository.java  → persistence only
//     InvoicePDFGenerator.java → presentation only
//     InvoiceNotifier.java    → notification only
//
//  INTERVIEW ANSWER:
//  "A class should have one reason to change. This means one
//  stakeholder whose needs drive modifications. For example,
//  Invoice handles business logic. InvoiceRepository handles
//  persistence. If the DBA changes the schema, only
//  InvoiceRepository changes — Invoice stays untouched."


// ============================================================
//  O — OPEN/CLOSED PRINCIPLE (OCP)
// ============================================================
//
//  RULE: Software should be OPEN for extension, CLOSED for modification.
//
//  MEANING: Add new features by ADDING code, not EDITING existing code.
//
//  WHY IT MATTERS:
//  → Protects working code (existing code is tested, deployed, working)
//  → Reduces regression risk (new discount can't break old ones)
//  → Enables plug-and-play (new discount = new class file, plug it in)
//
//  THE PROBLEM (violates OCP):
//  double applyDiscount(String type, double price) {
//      if (type.equals("FLAT")) return price - 500;
//      else if (type.equals("PERCENTAGE")) return price * 0.9;
//      else if (type.equals("BOGO")) return price / 2;
//      // Every new discount = EDIT this method forever
//  }
//
//  THE FIX (follows OCP):
//  interface DiscountStrategy { double apply(double price); }
//  class FlatDiscount implements DiscountStrategy { }
//  class PercentageDiscount implements DiscountStrategy { }
//  // New discount? New class. Zero edits to existing code.
//
//  HOW TO ACHIEVE OCP:
//  → Strategy Pattern (what we built)
//  → Template Method Pattern
//  → Decorator Pattern
//  → Factory Pattern
//  All design patterns exist to achieve OCP in specific situations!
//
//  INTERVIEW ANSWER:
//  "When requirements change, I add new code instead of editing
//  existing tested code. For example, a discount system with
//  a DiscountStrategy interface. New discount type? Create a new
//  class implementing DiscountStrategy. The checkout code never
//  changes. Files modified: 0. Files created: 1. Risk: zero."


// ============================================================
//  L — LISKOV SUBSTITUTION PRINCIPLE (LSP)
// ============================================================
//
//  RULE: If S is a subtype of T, objects of type T can be replaced
//        with objects of type S without breaking the program.
//
//  MEANING: Children must HONOR the parent's contract. No surprises.
//
//  WHY IT MATTERS:
//  → Trust in polymorphism (substitution must be safe)
//  → Catches bad inheritance hierarchies
//  → Prevents runtime surprises
//
//  RED FLAGS (LSP violations):
//  1. THROWING EXCEPTIONS in overridden methods
//     fly() { throw new UnsupportedOperationException(); }
//  2. EMPTY/NO-OP overrides
//     checkIn() { /* do nothing */ }
//  3. INSTANCEOF checks before calling
//     if (bird instanceof Penguin) skip fly();
//
//  CLASSIC EXAMPLE — Rectangle-Square:
//  ❌ Square extends Rectangle
//     rect.setWidth(20); rect.setHeight(5);
//     Expected area: 100
//     With Square: area = 25 (height override made it 5×5)
//     LSP VIOLATED — wrong result!
//
//  ✅ Rectangle and Square both implement Shape interface
//     Each has its own clean API
//     No broken contract
//
//  CLASSIC EXAMPLE — Bird-Penguin:
//  ❌ Penguin extends Bird (Bird has fly())
//     bird.fly() → crashes if bird is a Penguin
//
//  ✅ Flyable interface (only flying birds implement it)
//     Swimmable interface (only swimming birds implement it)
//     Penguin implements Swimmable, not Flyable
//     No crash possible
//
//  THE FIX: Use INTERFACES for capabilities
//  Don't force children to inherit methods they can't fulfill.
//
//  INTERVIEW ANSWER:
//  "If I substitute a child for its parent, the program should
//  still work correctly. For example, if Penguin extends Bird
//  but overrides fly() to throw an exception, that's an LSP
//  violation. The fix is capability interfaces — Flyable for
//  flying birds, Swimmable for swimming birds. Penguin only
//  implements Swimmable, so there's no broken contract."


// ============================================================
//  I — INTERFACE SEGREGATION PRINCIPLE (ISP)
// ============================================================
//
//  RULE: No class should be forced to implement methods it doesn't use.
//
//  MEANING: Prefer many small interfaces over one fat interface.
//
//  WHY IT MATTERS:
//  1. ELIMINATES DEAD CODE
//     No empty methods, no throwing UnsupportedOperationException
//  2. COMPILE-TIME SAFETY (most important!)
//     manager.prepareOrder(cashier) won't compile
//     (Cashier doesn't implement Cookable)
//  3. REDUCES COUPLING
//     Change to Cookable only affects classes that cook
//  4. EASIER TO TEST
//     Mock 1 method vs mock 10 methods
//
//  THE PROBLEM (violates ISP):
//  interface RestaurantWorker {
//      void cook(); void serve(); void wash(); void billing();
//  }
//  class Chef implements RestaurantWorker {
//      void cook() { ... }  // ✅ actual work
//      void serve() { throw ... }  // ❌ forced to implement
//      void wash() { throw ... }   // ❌ forced to implement
//      void billing() { throw ... } // ❌ forced to implement
//  }
//
//  THE FIX (follows ISP):
//  interface Cookable { void cook(); }
//  interface Servable { void serve(); }
//  interface Billable { void billing(); }
//  
//  class Chef implements Cookable { }  // Only cook()
//  class Waiter implements Servable { }  // Only serve()
//  class KitchenHelper implements Cookable, Washable { }  // Mix & match!
//
//  HOW ISP PREVENTS LSP VIOLATIONS:
//  Fat Bird interface with fly() → Penguin violates LSP
//  Split into Flyable + Swimmable → LSP satisfied
//  Fix the interface (ISP) → fix substitution (LSP)
//
//  INTERVIEW ANSWER:
//  "Don't force classes to implement methods they don't use.
//  For example, a fat RestaurantWorker interface forces Chef
//  to implement serve(), wash(), billing() — all throwing
//  exceptions. With ISP, I split it: Cookable, Servable,
//  Billable. Chef only implements Cookable. Benefit: if I pass
//  a Cashier to prepareOrder(Cookable c), it won't compile —
//  compile-time safety instead of runtime crash."


// ============================================================
//  D — DEPENDENCY INVERSION PRINCIPLE (DIP)
// ============================================================
//
//  RULE: High-level modules should depend on ABSTRACTIONS,
//        not on low-level concrete classes.
//
//  MEANING: Depend on interfaces. Inject dependencies from outside.
//
//  WHY IT MATTERS:
//  → DECOUPLING (swap MySQL → PostgreSQL, OrderService unchanged)
//  → TESTABILITY (pass MockDatabase, test without real DB)
//  → FLEXIBILITY (dev: InMemory, test: Mock, prod: MySQL)
//  → PARALLEL DEVELOPMENT (teams work on interface contract)
//
//  THE PROBLEM (violates DIP):
//  class OrderService {
//      private MySQLDatabase db = new MySQLDatabase();  // ❌ tight coupling
//      private EmailNotifier email = new EmailNotifier(); // ❌ tight coupling
//  }
//  → Can't swap MySQL to Postgres without editing OrderService
//  → Can't test without real MySQL running
//
//  THE FIX (follows DIP):
//  interface Database { void save(...); }
//  interface Notifier { void send(...); }
//  
//  class OrderService {
//      private final Database db;
//      private final Notifier notifier;
//      
//      // CONSTRUCTOR INJECTION — dependencies come from OUTSIDE
//      public OrderService(Database db, Notifier n) {
//          this.db = db;
//          this.notifier = n;
//      }
//  }
//  
//  // Usage:
//  Database mysql = new MySQLDatabase();
//  Notifier email = new EmailNotifier();
//  OrderService service = new OrderService(mysql, email);
//  
//  // Swap to PostgreSQL + SMS? Just change what you pass:
//  Database postgres = new PostgreSQLDatabase();
//  Notifier sms = new SmsNotifier();
//  OrderService service2 = new OrderService(postgres, sms);
//  // OrderService code: UNTOUCHED
//
//  DIP vs DI:
//  DIP = PRINCIPLE (depend on abstractions)
//  DI = TECHNIQUE (inject dependencies)
//  DIP is the WHY. DI is the HOW.
//
//  CONNECTION TO SPRING:
//  Spring's @Autowired / @Component / @Service are all DIP!
//  
//  @Service
//  public class OrderService {
//      private final Database db;
//      
//      @Autowired
//      public OrderService(Database db) { this.db = db; }
//  }
//  
//  @Component
//  public class MySQLDatabase implements Database { }
//  
//  Spring automatically creates MySQLDatabase and injects it.
//  Want to swap to Postgres? Change @Component. OrderService: untouched.
//  DIP is the FOUNDATION of Spring's IoC container!
//
//  INTERVIEW ANSWER:
//  "High-level modules depend on abstractions, not concrete classes.
//  OrderService depends on Database interface, not MySQLDatabase.
//  Dependencies are injected via constructor. This gives decoupling
//  — I can swap MySQL to Postgres without touching OrderService —
//  and testability — I can pass MockDatabase in tests. This is
//  the foundation of Spring's dependency injection."


// ============================================================
//  HOW ALL 5 PRINCIPLES CONNECT
// ============================================================
//
//  SRP → Makes classes focused
//    ↓
//  OCP → Easier to extend focused classes
//    ↓
//  LSP → Extension only works if substitution is safe
//    ↓
//  ISP → Safe substitution requires right-sized interfaces
//    ↓
//  DIP → Right interfaces enable dependency injection
//
//  They're not 5 isolated rules. They're a SYSTEM.
//
//  EXAMPLE: Discount system shows all 5:
//  → SRP: CheckoutService only handles checkout
//  → OCP: New discount = new class, zero edits
//  → LSP: Any DiscountStrategy works in checkout()
//  → ISP: DiscountStrategy is small (only apply())
//  → DIP: CheckoutService depends on DiscountStrategy interface


// ============================================================
//  QUICK REFERENCE TABLE
// ============================================================
//
//  | Principle | Question                          | Red Flag                    |
//  |-----------|-----------------------------------|-----------------------------|
//  | SRP       | Does this class do too much?      | Can't describe without "and"|
//  | OCP       | Can I extend without modifying?   | Growing if-else chain       |
//  | LSP       | Can I substitute safely?          | Throwing exceptions         |
//  | ISP       | Does interface force too much?    | Empty method bodies         |
//  | DIP       | Do I depend on abstractions?      | new ConcreteClass() inside  |


// ============================================================
//  FINAL TIPS FOR INTERVIEWS
// ============================================================
//
//  1. Always give a CONCRETE EXAMPLE
//     Don't just recite definitions. Walk through Invoice/Discount/Bird.
//
//  2. Explain the PROBLEM first
//     Show what breaks WITHOUT the principle.
//     Then show how the principle FIXES it.
//
//  3. Connect to REAL FRAMEWORKS
//     SRP → Spring separates @Service, @Repository, @Controller
//     OCP → Strategy pattern in Collections.sort()
//     LSP → Java's Collection hierarchy
//     ISP → Java's List vs Set vs Queue (not one DataStructure)
//     DIP → Spring's @Autowired dependency injection
//
//  4. Know how they RELATE
//     "ISP prevents LSP violations"
//     "DIP enables OCP at the system level"
//     "SRP makes OCP easier to achieve"
//
//  5. Don't over-engineer
//     Not every class needs an interface (DTOs don't)
//     Apply principles where change is LIKELY
