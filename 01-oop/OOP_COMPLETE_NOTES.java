// ============================================================
//  OOP FUNDAMENTALS — Complete Interview Guide
// ============================================================
//
//  The 4 pillars of Object-Oriented Programming.
//  These are the FOUNDATION — SOLID builds on top of these.
//
// ============================================================


// ============================================================
//  ENCAPSULATION — "Protect your object's internals"
// ============================================================
//
//  DEFINITION: Bundling data (fields) and methods that operate on
//              that data into a single unit (class), and RESTRICTING
//              direct access to the internals.
//
//  MEANING: Hide the data. Provide controlled access via methods.
//
//  WHY IT MATTERS:
//  1. PROTECT INVARIANTS
//     → BankAccount balance should never go negative
//     → If balance is public, anyone can set it to -5000
//     → With encapsulation, withdraw() enforces the rule
//
//  2. HIDE IMPLEMENTATION DETAILS
//     → Tomorrow you store balance in paisa (1/100 rupee) internally
//     → Just change getBalance() to return balance/100
//     → If balance was public, 500 files might break
//
//  3. CONTROLLED ACCESS
//     → You decide: read-only? write-only? read-write?
//     → getBalance() exists but NO setBalance() → read-only
//     → deposit()/withdraw() are business methods, not dumb setters
//
//  HOW TO ACHIEVE:
//  → Make fields PRIVATE
//  → Provide PUBLIC methods with validation/rules
//  → Expose BEHAVIOR (deposit, withdraw) not data (setBalance)
//
//  COMMON MISCONCEPTION:
//  "Encapsulation = getters + setters" ← WRONG!
//  
//  class Account {
//      private double balance;
//      public void setBalance(double b) { balance = b; }  // ❌ NO PROTECTION
//  }
//  This is NOT encapsulation — it's just public with extra steps.
//  
//  True encapsulation:
//  → No setBalance(). Instead: deposit(), withdraw() with rules.
//  → getBalance() for read-only access.
//
//  REAL-WORLD ANALOGY:
//  ATM Machine
//  → You can't open it and grab cash (private fields)
//  → You use the screen/buttons (public methods)
//  → The machine enforces rules: PIN check, daily limit, sufficient
//    balance (validation in methods)
//
//  INTERVIEW ANSWER:
//  "Encapsulation bundles data and methods together while restricting
//  direct access to internals. For example, BankAccount has private
//  balance and public deposit()/withdraw() methods that enforce rules.
//  This protects invariants, hides implementation details, and gives
//  controlled access. It's NOT just getters/setters — those without
//  validation provide no real protection."


// ============================================================
//  ABSTRACTION — "Show WHAT, hide HOW"
// ============================================================
//
//  DEFINITION: Showing only essential features and hiding complex
//              implementation details.
//
//  MEANING: Define WHAT something does, hide HOW it does it internally.
//
//  WHY IT MATTERS:
//  1. SIMPLICITY FOR THE CALLER
//     → sendNotification(user, message) works for Email, SMS, Push
//     → Caller doesn't deal with SMTP config, Twilio keys, Firebase
//     → Like driving a car — you steer, don't rewire the mechanism
//
//  2. EXTENSIBILITY WITHOUT MODIFICATION
//     → New notification type (WhatsApp)? Create new class.
//     → Zero changes to existing working code
//     → No if-else chains growing forever
//
//  3. LOOSE COUPLING
//     → Business logic depends on ABSTRACTION (NotificationService)
//     → Not on concrete classes (EmailNotifier, SmsNotifier)
//     → Easy to swap, easy to test (pass MockNotifier)
//
//  HOW TO ACHIEVE:
//  → Abstract classes (shared behavior + abstract methods)
//  → Interfaces (pure contracts)
//  → Caller depends on abstraction, not implementation
//
//  ABSTRACT CLASS vs INTERFACE:
//  
//  ABSTRACT CLASS                    INTERFACE
//  → Has shared code + abstract      → Pure contract (Java 8+ has default)
//    methods                         → Multiple inheritance allowed
//  → Single inheritance only         → "can-do" capability
//  → "is-a" with shared logic        → Use when unrelated classes share
//  → Use when subclasses share         a behavior
//    common behavior
//  
//  WHEN TO USE WHICH:
//  → Shared logic exists? → Abstract class
//  → Just a contract? → Interface
//  → Often use BOTH: Interface → Abstract Class → Concrete Class
//
//  REAL-WORLD ANALOGY:
//  TV Remote
//  → You press "Volume Up" (abstraction — the WHAT)
//  → Don't know if it uses infrared, Bluetooth, or WiFi (the HOW)
//  → Manufacturer can change IR to Bluetooth — you still just press
//    the same button
//
//  HOW ABSTRACTION ≠ ENCAPSULATION:
//  
//  ENCAPSULATION               ABSTRACTION
//  → Hides DATA                → Hides IMPLEMENTATION
//  → private fields +          → abstract class / interface
//    controlled methods
//  → "Don't touch my           → "Don't worry about how I do it,
//     internals directly"         just tell me what to do"
//  → Works at FIELD level      → Works at CLASS/METHOD level
//  → About PROTECTION          → About SIMPLIFICATION
//
//  INTERVIEW ANSWER:
//  "Abstraction shows what an object does while hiding how it does it.
//  For example, NotificationService defines send() as an abstract
//  method. EmailNotification, SmsNotification each implement send()
//  differently, but the caller just calls send() — doesn't know or
//  care about SMTP vs Twilio. New type? New class. Caller unchanged.
//  This simplifies usage, enables extension, and reduces coupling."


// ============================================================
//  INHERITANCE — "Reuse code, establish hierarchy"
// ============================================================
//
//  DEFINITION: Mechanism where a child class acquires fields and
//              methods of a parent class, enabling code reuse.
//
//  MEANING: Share common behavior in parent. Children inherit it and
//           specialize what's different.
//
//  WHY IT MATTERS:
//  1. CODE REUSE (DRY — Don't Repeat Yourself)
//     → Common fields (name, id) and behavior (checkIn, checkOut)
//       written ONCE in Employee parent
//     → 10 employee types? checkIn logic exists in 1 place
//     → Fix a bug → fixed for all subtypes automatically
//
//  2. ESTABLISH TYPE HIERARCHY
//     → FullTimeEmployee IS-A Employee
//     → Enables polymorphism: processPayroll(Employee e) works
//       with ANY employee type
//
//  THE BIG WARNING — "Favor Composition over Inheritance":
//  Don't use inheritance just for code reuse. Use it ONLY for
//  genuine "is-a" relationships where ALL parent behavior makes
//  sense for the child.
//
//  BAD EXAMPLE:
//  Employee has checkIn(). FreelanceEmployee extends Employee.
//  But freelancers don't check in! Now you override checkIn() to
//  do nothing or throw exception. That's a sign inheritance is wrong.
//
//  GOOD ALTERNATIVE (Composition):
//  → Attendable interface (checkIn, checkOut)
//  → Payable interface (calculateSalary)
//  → FullTimeEmployee implements BOTH
//  → FreelanceEmployee implements only Payable
//  → Each class picks its capabilities. No unwanted baggage.
//
//  TYPES OF INHERITANCE (Java):
//  → Single: A extends B
//  → Multilevel: A extends B, B extends C
//  → Hierarchical: B extends A, C extends A
//  → ❌ Multiple class inheritance NOT allowed (Diamond Problem)
//    Use interfaces instead
//
//  DIAMOND PROBLEM:
//  If class C extends both A and B, and both have doSomething(),
//  which version does C inherit? Java avoids this by disallowing
//  multiple class inheritance. Interfaces solve it — if two
//  interfaces have the same default method, the implementing class
//  MUST override it explicitly.
//
//  WHEN TO USE INHERITANCE vs INTERFACE vs COMPOSITION:
//  
//  INHERITANCE (extends)
//  → True "is-a" relationship
//  → Child needs ALL parent behavior
//  → Want to share implementation
//  → Example: ArrayList IS-A AbstractList
//
//  INTERFACE (implements)
//  → "can-do" / capability contract
//  → Unrelated classes share a behavior
//  → Example: List and Set implement Collection
//
//  COMPOSITION (has-a)
//  → One class USES another class
//  → More flexible, less coupling
//  → Example: Car HAS-A Engine (Car doesn't extend Engine!)
//
//  REAL-WORLD ANALOGY:
//  Vehicle hierarchy
//  → Car, Bike, Truck all IS-A Vehicle (share: start, stop, fuel)
//  → Each adds what's unique (Car has 4 wheels, Bike has 2)
//  → Bad: FlyingCar extends Car AND extends Plane
//    (can't do in Java — use interfaces: Drivable + Flyable)
//
//  INTERVIEW ANSWER:
//  "Inheritance lets a child class acquire parent's fields and methods,
//  enabling code reuse and establishing a type hierarchy. For example,
//  Employee has name, id, checkIn(). FullTimeEmployee and ContractEmployee
//  inherit these and only define what's different — calculateSalary().
//  This eliminates duplication. But use it carefully — only when child
//  truly needs ALL parent behavior. Otherwise use composition or
//  interfaces. The rule is 'favor composition over inheritance'."


// ============================================================
//  POLYMORPHISM — "Many forms"
// ============================================================
//
//  DEFINITION: The ability to use a single interface to represent
//              different underlying types. Same method call, different
//              behavior based on the actual object.
//
//  MEANING: Write code once that works with current AND future types.
//
//  WHY IT MATTERS:
//  1. ELIMINATE IF-ELSE / INSTANCEOF CHAINS
//     → Without: if UPI do this, else if Card do that...
//     → With: method.processPayment() — JVM picks the right one
//     → Zero conditionals
//
//  2. WRITE CODE THAT WORKS WITH FUTURE TYPES
//     → checkout(PaymentMethod m) works with UPI today and Crypto
//       next year — without changing a line
//     → "Open for extension, closed for modification" (OCP!)
//
//  3. COLLECTIONS OF MIXED TYPES
//     → PaymentMethod[] can hold UPI, Card, NetBanking objects
//     → Loop through them uniformly. Each behaves correctly
//
//  TWO TYPES OF POLYMORPHISM:
//  
//  COMPILE-TIME (Static) — METHOD OVERLOADING
//  → Same method name, different parameters
//  → Compiler picks based on arguments
//  → Example: print(int), print(String), print(double)
//  → Also called: early binding, static dispatch
//  
//  RUNTIME (Dynamic) — METHOD OVERRIDING ⭐ (more important)
//  → Child overrides parent's method
//  → JVM picks based on ACTUAL object type at runtime
//  → Example:
//      PaymentMethod m = new UpiPayment();
//      m.processPayment(100);  // Calls UPI's version, not interface
//  → Also called: late binding, dynamic dispatch
//
//  HOW RUNTIME POLYMORPHISM WORKS (vtable):
//  → Every class has a vtable (virtual method table) with method pointers
//  → When you call m.processPayment(), JVM looks at the ACTUAL object's
//    vtable (not reference type) to find which method to execute
//  → This is "dynamic dispatch"
//
//  RULES FOR METHOD OVERRIDING:
//  → Same method signature (name + params)
//  → Return type: same or covariant (subtype)
//  → Access modifier: same or MORE visible (not less)
//  → Cannot override: final, static, or private methods
//  → Use @Override annotation (catches mistakes)
//
//  COVARIANT RETURN TYPE:
//  Parent: Animal getAnimal()
//  Child:  Dog getAnimal()    // Dog IS-A Animal — valid!
//
//  METHOD OVERLOADING vs OVERRIDING:
//  
//  OVERLOADING                     OVERRIDING
//  → Same class                   → Parent-child
//  → Different parameters         → Same parameters
//  → Compile-time                 → Runtime
//  → "More options for same       → "Different behavior for
//     action" (convenience)          same action" (specialization)
//  → Example: println(int),       → Example: each PaymentMethod
//    println(String)                has its own processPayment()
//
//  REAL-WORLD ANALOGY:
//  Coffee machine buttons
//  → Press "Espresso" → machine makes espresso
//  → Press "Latte" → machine makes latte
//  → Same interface (button press), different behavior
//  → Machine type (basic vs premium) changes HOW it makes coffee,
//    but the button press (interface) stays the same
//
//  HOW ALL 4 PILLARS CONNECT:
//  
//  ENCAPSULATION → protects each class's internals
//       ↓
//  ABSTRACTION → defines WHAT each class must do (contract)
//       ↓
//  INHERITANCE → lets children reuse parent's code
//       ↓
//  POLYMORPHISM → lets you treat them all uniformly
//
//  Together: clean, extensible, maintainable code where adding
//  new types doesn't break existing code.
//
//  INTERVIEW ANSWER:
//  "Polymorphism means many forms — the same method call behaves
//  differently based on the actual object. For example, 
//  checkout(PaymentMethod m) calls m.processPayment(). If m is UPI,
//  it calls UPI's version. If m is CreditCard, it calls Card's version.
//  The JVM decides at runtime. This eliminates if-else chains, works
//  with future types, and enables uniform processing. There's also
//  compile-time polymorphism (overloading) where the compiler picks
//  based on parameters, but runtime polymorphism is more powerful."


// ============================================================
//  HOW OOP CONNECTS TO SOLID
// ============================================================
//
//  OOP provides the TOOLS:
//  → Classes, interfaces, inheritance, polymorphism
//
//  SOLID provides the RULES for using those tools well:
//  → SRP: Each class should encapsulate ONE responsibility
//  → OCP: Use abstraction (interfaces/abstract classes) to extend
//  → LSP: Inheritance must be used correctly (honor contracts)
//  → ISP: Interfaces should be focused (not fat)
//  → DIP: Depend on abstractions, use polymorphism
//
//  Example showing all 4 OOP pillars:
//  
//  abstract class PaymentMethod {              // Abstraction
//      private String id;                       // Encapsulation (private)
//      
//      protected abstract boolean process();    // Abstraction
//      
//      public String getId() { return id; }     // Encapsulation (controlled access)
//  }
//  
//  class CreditCard extends PaymentMethod {     // Inheritance
//      @Override
//      protected boolean process() { }          // Polymorphism
//  }
//  
//  void checkout(PaymentMethod m) {             // Polymorphism
//      m.process();  // Works with CreditCard, UPI, anything
//  }


// ============================================================
//  QUICK REFERENCE TABLE
// ============================================================
//
//  | Pillar       | Question                        | Key Mechanism          |
//  |--------------|---------------------------------|------------------------|
//  | Encapsulation| How do I protect my data?       | private + public methods|
//  | Abstraction  | How do I hide complexity?       | abstract class/interface|
//  | Inheritance  | How do I reuse code?            | extends / implements   |
//  | Polymorphism | How do I write flexible code?   | method overriding      |


// ============================================================
//  FINAL TIPS FOR INTERVIEWS
// ============================================================
//
//  1. Always give CONCRETE EXAMPLES
//     Don't just define. Show BankAccount, PaymentMethod, Employee.
//
//  2. Know the DIFFERENCES
//     Encapsulation vs Abstraction (most asked!)
//     Overloading vs Overriding
//     Abstract class vs Interface
//     Inheritance vs Composition
//
//  3. Explain the WHY
//     "Encapsulation protects invariants so balance can't go negative"
//     Not just "Encapsulation hides data"
//
//  4. Connect to REAL CODE
//     → Java Collections (ArrayList IS-A List)
//     → toString(), equals(), hashCode() (overriding)
//     → Comparable, Serializable (interfaces)
//
//  5. Show you understand TRADEOFFS
//     → Inheritance gives reuse but tight coupling
//     → Composition is flexible but more boilerplate
//     → Know when to use each
