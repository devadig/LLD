// ============================================================
//  POLYMORPHISM — Interview Cheat Sheet
// ============================================================
//
//  Q: What is Polymorphism?
//  A: "Many forms" — the ability to use a single interface to
//     represent different underlying types. The same method
//     call behaves differently based on the actual object.
//
//  Q: WHY do we need it?
//  A: Three reasons:
//
//     1. ELIMINATE IF-ELSE / INSTANCEOF CHAINS
//        → Without it: if UPI do this, else if Card do that...
//        → With it: method.processPayment() — JVM picks the
//          right one. Zero conditionals.
//
//     2. WRITE CODE THAT WORKS WITH FUTURE TYPES
//        → checkout(PaymentMethod m) works with UPI today and
//          CryptoPayment next year — without changing a line.
//        → Your code is "open for extension, closed for
//          modification" (this is the Open/Closed SOLID principle!)
//
//     3. COLLECTIONS OF MIXED TYPES
//        → PaymentMethod[] can hold UPI, Card, NetBanking objects.
//        → Loop through them uniformly. Each behaves correctly.
//
//  Q: Two types of Polymorphism?
//  A:
//     COMPILE-TIME (Static) — METHOD OVERLOADING
//     → Same method name, different parameters
//     → Compiler picks the right version based on arguments
//     → Example: createOrder(amt), createOrder(amt, desc)
//     → Also called: early binding, static dispatch
//
//     RUNTIME (Dynamic) — METHOD OVERRIDING ⭐ (more important)
//     → Child overrides parent's method
//     → JVM picks the right version AT RUNTIME based on actual
//       object type, not the reference type
//     → Example:
//         PaymentMethod m = new UpiPayment();  // ref: PaymentMethod
//         m.processPayment(100);  // calls UPI's version, not interface's
//     → Also called: late binding, dynamic dispatch
//
//  Q: How does runtime polymorphism work internally?
//  A: Through a mechanism called VIRTUAL METHOD TABLE (vtable).
//     → Every class has a vtable — a table of method pointers.
//     → When you call m.processPayment(), the JVM looks at the
//       ACTUAL object's vtable (not the reference type) to find
//       which method to execute.
//     → This is why it's called "dynamic dispatch."
//
//  Q: Can you overload AND override in the same class?
//  A: Yes! They're independent concepts.
//     → Overloading: same class, same name, different params
//     → Overriding: child class, same name, same params as parent
//
//  Q: Rules for method overriding?
//  A: - Same method signature (name + params)
//     - Return type must be same or covariant (subtype)
//     - Access modifier can be same or MORE visible (not less)
//     - Cannot override final, static, or private methods
//     - Use @Override annotation (not required, but good practice
//       — compiler catches mistakes)
//
//  Q: What is covariant return type?
//  A: Child can return a SUBTYPE of parent's return type.
//       Parent: Animal getAnimal()
//       Child:  Dog getAnimal()    // Dog IS-A Animal — valid!
//
//  Q: Method overloading vs overriding — how to remember?
//  A:
//     OVERLOADING                    OVERRIDING
//     → Same class                  → Parent-child
//     → Different parameters        → Same parameters
//     → Compile-time                → Runtime
//     → "More options for same      → "Different behavior for
//        action" (convenience)         same action" (specialization)
//     → Example: println(int),      → Example: each PaymentMethod
//       println(String)               has its own processPayment()
//
//  Q: Can polymorphism work with abstract classes AND interfaces?
//  A: Yes, both! The pattern is the same:
//     → Declare variable as parent type / interface type
//     → Assign child / implementation object
//     → Call methods — JVM dispatches to actual type
//
//  ============================================================
//  HOW ALL 4 OOP PILLARS CONNECT:
//  ============================================================
//
//  ENCAPSULATION → protects each class's internals
//       ↓
//  ABSTRACTION → defines WHAT each class must do (contract)
//       ↓
//  INHERITANCE → lets children reuse parent's code
//       ↓
//  POLYMORPHISM → lets you treat them all uniformly
//
//  Together they enable: clean, extensible, maintainable code
//  where adding new types doesn't break existing code.
//  ============================================================
