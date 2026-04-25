// ============================================================
//  LISKOV SUBSTITUTION PRINCIPLE — Interview Cheat Sheet
// ============================================================
//
//  Q: What is LSP?
//  A: If class B extends class A, then everywhere you use A,
//     you should be able to substitute B WITHOUT the program
//     breaking, producing wrong results, or throwing unexpected
//     exceptions.
//
//     In short: children must HONOR the parent's contract.
//
//  Q: WHY do we need it?
//  A:
//     1. TRUST IN POLYMORPHISM
//        → Polymorphism only works if substitution is safe.
//        → If process(Shape s) works for Rectangle but breaks
//          for Square, polymorphism is useless.
//        → LSP guarantees that subtyping is SAFE.
//
//     2. CATCH BAD INHERITANCE HIERARCHIES
//        → "Is-a" in the REAL WORLD doesn't always mean
//          "is-a" in CODE.
//        → Square IS-A Rectangle (math) but NOT in code
//          (because setWidth behavior changes).
//        → Penguin IS-A Bird (biology) but NOT in code
//          (because fly() breaks).
//        → LSP is the test that catches these mistakes.
//
//     3. PREVENT RUNTIME SURPRISES
//        → Without LSP: code works in testing (Rectangle),
//          crashes in production (Square passed in).
//        → With LSP: any subtype is guaranteed safe.
//
//  Q: How do you DETECT an LSP violation? ⭐
//  A: Look for these RED FLAGS:
//
//     1. THROWING EXCEPTIONS in overridden methods
//        → fly() throws UnsupportedOperationException
//        → Parent said "I can fly." Child says "I crash."
//
//     2. EMPTY/NO-OP overrides
//        → checkIn() { /* do nothing */ }
//        → Parent said "I check in." Child silently ignores.
//
//     3. INSTANCEOF checks before calling a method
//        → if (bird instanceof Penguin) skip fly();
//        → If you need to check the type, the hierarchy is wrong.
//
//     4. STRENGTHENING PRECONDITIONS
//        → Parent: withdraw(any amount up to balance)
//        → Child: withdraw(only amounts > ₹100)
//        → Child is MORE restrictive — breaks parent's promise.
//
//     5. WEAKENING POSTCONDITIONS
//        → Parent: getBalance() always returns >= 0
//        → Child: getBalance() might return negative
//        → Child delivers LESS than parent promised.
//
//  Q: How do you FIX an LSP violation?
//  A: Two main approaches:
//
//     1. USE INTERFACES FOR CAPABILITIES
//        → Don't put fly() in Bird base class.
//        → Create Flyable interface. Only flying birds implement it.
//        → Penguin doesn't implement Flyable → no crash possible.
//
//     2. MAKE THEM SIBLINGS, NOT PARENT-CHILD
//        → Don't make Square extend Rectangle.
//        → Both implement Shape interface.
//        → Each has its own clean API (setSide vs setWidth/setHeight).
//
//  Q: Rectangle-Square problem — explain it? ⭐ VERY COMMON
//  A: (Walk through our example)
//     → Rectangle has setWidth() and setHeight() — independent.
//     → Square overrides both to keep sides equal.
//     → Code that sets width=20, height=5 expects area=100.
//     → With Square, height override makes it 5×5=25. WRONG!
//     → FIX: Both implement Shape interface separately.
//
//  Q: LSP and "Design by Contract"?
//  A: LSP is closely related to Bertrand Meyer's DbC:
//     → Preconditions: Child can WEAKEN (accept more), not strengthen
//     → Postconditions: Child can STRENGTHEN (guarantee more), not weaken
//     → Invariants: Child must maintain parent's invariants
//
//     Think of it as: the child can be MORE generous (accept
//     more inputs, give stronger guarantees) but NEVER more
//     restrictive.
//
//  Q: How does LSP connect to other SOLID principles?
//  A:
//     → ISP (Interface Segregation): LSP violations often happen
//       because interfaces are too FAT. Splitting them (ISP)
//       prevents LSP violations.
//       Bird interface with fly() → split into Flyable + Swimmable
//       → Penguin only implements Swimmable → no LSP issue.
//
//     → OCP: LSP enables OCP. If substitution is safe, you can
//       extend the system with new subtypes without modifying
//       existing code.
//
//  Q: Quick LSP test for any hierarchy?
//  A: Ask: "If I pass this child to ALL existing code that
//     uses the parent, will ANYTHING break, crash, give wrong
//     results, or need an instanceof check?"
//     → If YES → LSP violation → fix the hierarchy.
//     → If NO → LSP satisfied → safe to use.
//
// ============================================================
