// ============================================================
//  SINGLE RESPONSIBILITY PRINCIPLE — Interview Cheat Sheet
// ============================================================
//
//  Q: What is SRP?
//  A: A class should have only ONE REASON TO CHANGE.
//     This means one class = one responsibility = one actor
//     (team/stakeholder) whose needs drive changes.
//
//  Q: WHY do we need it?
//  A:
//     1. ISOLATED CHANGES
//        → Change DB logic? Only InvoiceRepository changes.
//        → No risk of accidentally breaking business logic.
//
//     2. INDEPENDENT TESTING
//        → Test Invoice math without needing a DB.
//        → Test PDF generation without sending emails.
//        → Each class can be unit tested in isolation.
//
//     3. REUSABILITY
//        → InvoicePDFGenerator can be used by other modules.
//        → InvoiceNotificationService can send any notification.
//        → When logic is trapped in a god-class, you can't reuse
//          just one part — you drag everything along.
//
//     4. TEAM SCALABILITY
//        → Developer A works on Invoice (business logic).
//        → Developer B works on InvoiceRepository (DB).
//        → No merge conflicts. No stepping on each other.
//
//  Q: Common MISCONCEPTION about SRP?
//  A: "A class should do only one thing."
//     → Too vague! Invoice has addItem(), calculateSubtotal(),
//       calculateTax(), calculateTotal() — that's 4 methods.
//       But they ALL serve the same responsibility: business logic.
//     → The correct test: "How many REASONS (actors/stakeholders)
//       can cause this class to change?"
//     → If the answer is 1 → SRP is satisfied.
//
//  Q: How do you IDENTIFY an SRP violation?
//  A: Ask yourself these questions:
//     1. Can I describe the class in one sentence WITHOUT "and"?
//        → "Invoice calculates totals AND saves to DB AND generates PDF"
//        → Multiple "AND"s = multiple responsibilities
//
//     2. If I change one feature, do I risk breaking another?
//        → Changed email template, but broke tax calculation?
//        → That's an SRP violation.
//
//     3. Does this class import too many unrelated libraries?
//        → SMTP library + JDBC driver + PDF library = red flag
//
//  Q: Isn't this creating too many classes?
//  A: This is the MOST common pushback. The answer:
//     → Yes, more classes. But each class is SMALL, FOCUSED,
//       and EASY TO UNDERSTAND.
//     → A 500-line god-class is harder to maintain than 5
//       well-named 100-line classes.
//     → You navigate by CLASS NAMES, not by scrolling through
//       a massive file looking for "where's the email part?"
//     → In professional codebases, small focused classes are
//       the standard, not the exception.
//
//  Q: Real-world analogy?
//  A: Restaurant kitchen.
//     → One chef doesn't cook, serve, wash dishes, AND bill.
//     → Cook cooks. Waiter serves. Dishwasher washes.
//       Cashier bills.
//     → If the dishwasher is sick, cooking doesn't stop.
//     → Each role is independent and replaceable.
//
//  Q: How does SRP connect to other SOLID principles?
//  A: SRP is the FOUNDATION:
//     → Open/Closed: If a class does one thing, extending it
//       (not modifying) becomes natural.
//     → Interface Segregation: SRP for interfaces — don't
//       force a class to implement methods it doesn't need.
//     → Dependency Inversion: SRP classes are easier to
//       abstract behind interfaces.
//
// ============================================================
