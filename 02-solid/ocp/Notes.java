// ============================================================
//  OPEN/CLOSED PRINCIPLE — Interview Cheat Sheet
// ============================================================
//
//  Q: What is OCP?
//  A: Software entities (classes, modules, functions) should be
//     OPEN for extension but CLOSED for modification.
//     → Open for extension: You CAN add new behavior.
//     → Closed for modification: You DON'T edit existing code.
//
//  Q: WHY do we need it?
//  A:
//     1. PROTECT WORKING CODE
//        → Existing code is tested, deployed, working.
//        → Editing it risks introducing new bugs.
//        → OCP says: don't touch what works. Add new code instead.
//
//     2. REDUCE REGRESSION RISK
//        → In a team, dev A adds "loyalty discount" and
//          accidentally breaks "percentage discount" because
//          they edited the same if-else block.
//        → With OCP, each discount is a separate class.
//          Dev A's new file can't break dev B's existing file.
//
//     3. ENABLE PLUG-AND-PLAY
//        → New discount type = new class file. Plug it in.
//        → No rebuild of existing modules.
//        → Works like USB — plug in a new device, the port
//          (interface) doesn't change.
//
//  Q: How do you ACHIEVE OCP?
//  A: Through ABSTRACTION. The key techniques:
//
//     1. STRATEGY PATTERN (most common)
//        → Define an interface (DiscountStrategy)
//        → Each variant is a class implementing it
//        → Consumer depends on interface, not concrete classes
//        → This is exactly what we built!
//
//     2. TEMPLATE METHOD PATTERN
//        → Abstract class defines the skeleton
//        → Subclasses fill in the specific steps
//
//     3. DECORATOR PATTERN
//        → Wrap existing behavior with new behavior
//        → Without modifying the original class
//
//     All three achieve the same goal: extend without modify.
//
//  Q: How is OCP different from SRP?
//  A:
//     SRP → "Split responsibilities so each class has one
//            reason to change."
//     OCP → "When change IS needed, add new code instead
//            of editing existing code."
//
//     SRP asks: "Does this class do too much?"
//     OCP asks: "When requirements change, do I have to
//               edit existing code?"
//
//     They complement each other:
//     → SRP makes classes focused (easy to understand).
//     → OCP makes the system extensible (easy to grow).
//
//  Q: OCP and Design Patterns connection? ⭐ IMPORTANT
//  A: OCP is the REASON most design patterns exist!
//
//     Strategy Pattern → OCP for algorithms/behaviors
//     Observer Pattern → OCP for event handling
//     Decorator Pattern → OCP for adding features
//     Factory Pattern → OCP for object creation
//     Template Method → OCP for process steps
//
//     When you learn patterns in Phase 3, you'll see that
//     almost every pattern is just a clever way to achieve
//     OCP in a specific situation.
//
//  Q: Common MISTAKE with OCP?
//  A: Over-engineering. Not every class needs to be extensible
//     from day one. Apply OCP where you KNOW or EXPECT change.
//
//     YAGNI (You Ain't Gonna Need It) + OCP:
//     → First time: just write the code.
//     → Second time (similar change): refactor for OCP.
//     → Third time: you'll be glad you did.
//
//     Don't create interfaces for everything "just in case."
//     Apply OCP where change is LIKELY or ALREADY happening.
//
//  Q: Real-world analogy?
//  A: Power outlets (plug points).
//     → The socket (interface) is CLOSED — it doesn't change.
//     → But you can plug in ANY device (extension) — phone
//       charger, laptop, TV, iron — all work without
//       modifying the socket.
//     → New device invented? It just needs the right plug.
//       The socket stays unchanged.
//
// ============================================================
