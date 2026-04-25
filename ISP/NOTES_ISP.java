// ============================================================
//  INTERFACE SEGREGATION PRINCIPLE — Interview Cheat Sheet
// ============================================================
//
//  Q: What is ISP?
//  A: No class should be FORCED to implement methods it
//     doesn't use. Prefer many small, focused interfaces
//     over one large, fat interface.
//
//  Q: WHY do we need it?
//  A:
//     1. ELIMINATES DEAD CODE
//        → Fat interface: Chef has serve(), wash(), billing()
//          that all throw UnsupportedOperationException.
//        → Split interfaces: Chef only has cook(). Clean.
//        → No empty methods, no lying about capabilities.
//
//     2. COMPILE-TIME SAFETY (most important!)
//        → Fat interface: manager.prepareOrder(cashier) COMPILES
//          but CRASHES at runtime.
//        → Split interfaces: manager.prepareOrder(cashier)
//          WON'T COMPILE because Cashier doesn't implement
//          Cookable. Compiler catches the bug before it runs!
//        → Moves errors from RUNTIME to COMPILE-TIME.
//
//     3. REDUCES COUPLING
//        → If Cookable interface changes (add a parameter),
//          ONLY classes that implement Cookable are affected.
//        → With fat interface, ALL workers would need to change
//          even if the change is only relevant to cooking.
//
//     4. EASIER TO IMPLEMENT & TEST
//        → Implementing 1-2 methods is simple.
//        → Implementing 10 methods (8 are irrelevant) is painful.
//        → Mock a small interface in tests: easy.
//        → Mock a fat interface in tests: write 10 stub methods.
//
//  Q: How do you IDENTIFY an ISP violation?
//  A: Look for these signs:
//
//     1. METHODS THAT THROW UnsupportedOperationException
//        → "I implement this because I'm forced to, but I
//          can't actually do it."
//
//     2. EMPTY METHOD BODIES
//        → @Override void serve() { } ← does nothing
//
//     3. INTERFACE WITH MANY UNRELATED METHODS
//        → cook() + serve() + wash() + billing() + clean()
//        → If no single class needs ALL of them, it's too fat.
//
//     4. CLASSES IMPLEMENTING INTERFACE BUT USING ONLY 20-30%
//        → Chef implements 5 methods but only 1 is real.
//
//  Q: ISP vs SRP — what's the difference?
//  A:
//     SRP → for CLASSES: each class should have one reason to change
//     ISP → for INTERFACES: each interface should serve one client
//
//     SRP asks: "Does this CLASS do too many things?"
//     ISP asks: "Does this INTERFACE force too many things?"
//
//     They're the same IDEA applied at different levels.
//     SRP = don't put unrelated things in one class.
//     ISP = don't put unrelated things in one interface.
//
//  Q: How granular should interfaces be?
//  A: There's a balance. Don't go to the extreme of one
//     method per interface ALWAYS. Group methods that are
//     COHESIVE — methods that are always used together.
//
//     TOO FAT:  RestaurantWorker (5 unrelated methods)
//     JUST RIGHT: Cookable, Servable, Billable (focused roles)
//     TOO THIN: Startable + Stoppable + Pausable + Resumable
//               when they're ALWAYS used together → just Runnable
//
//     Rule of thumb: If implementing class A needs methods
//     X and Y together but never X without Y, keep them in
//     one interface.
//
//  Q: ISP in the real world (Java/Spring)?
//  A: 
//     → java.util.Collection vs List vs Set vs Queue
//       → Not one giant DataStructure interface!
//     → Spring: CrudRepository vs PagingAndSortingRepository
//       → Need only CRUD? Implement CrudRepository.
//       → Need paging too? Extend PagingAndSortingRepository.
//     → Servlet API: HttpServletRequest has getParameter(),
//       getHeader(), etc. But ReadableRequest could be smaller
//       for handlers that only read parameters.
//
//  Q: How does ISP connect to LSP?
//  A: ISP PREVENTS LSP violations!
//     → Fat Bird interface with fly() → Penguin overrides
//       fly() to throw exception → LSP violated!
//     → Split into Flyable + Swimmable → Penguin implements
//       only Swimmable → no broken contract → LSP satisfied!
//     → Fix the interface (ISP) → fix the substitution (LSP).
//
// ============================================================
