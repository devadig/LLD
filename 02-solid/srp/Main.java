// ============================================================
// DEMO: Single Responsibility Principle
// ============================================================

public class Main {
    public static void main(String[] args) {

        System.out.println("================================================");
        System.out.println("  SOLID #1 — Single Responsibility Principle");
        System.out.println("================================================\n");

        // ============================================================
        // ❌ THE BAD WAY: One class does everything
        // ============================================================
        System.out.println("❌ BAD: One god-class doing everything:");
        System.out.println("─────────────────────────────────────────\n");

        InvoiceBad badInvoice = new InvoiceBad("INV-001", "Rahul", 18);
        badInvoice.addItem("Keyboard", 2500);
        badInvoice.addItem("Mouse", 800);

        // All four operations live in ONE class — tightly coupled
        System.out.println("Total: ₹" + badInvoice.calculateTotal());
        badInvoice.saveToDatabase();     // DB code inside Invoice!
        badInvoice.generatePDF();        // PDF code inside Invoice!
        badInvoice.sendEmail("rahul@mail.com"); // Email code inside Invoice!

        System.out.println("\n" + "=".repeat(50) + "\n");

        // ============================================================
        // ✅ THE GOOD WAY: Each class has ONE responsibility
        // ============================================================
        System.out.println("✅ GOOD: Each class handles ONE thing:");
        System.out.println("─────────────────────────────────────────\n");

        // 1. Invoice — ONLY business logic
        Invoice invoice = new Invoice("INV-002", "Priya", 18);
        invoice.addItem("Monitor", 15000);
        invoice.addItem("Webcam", 3500);
        invoice.addItem("USB Hub", 1200);
        System.out.println("Created: " + invoice);
        System.out.println("Subtotal: ₹" + invoice.calculateSubtotal());
        System.out.println("Tax: ₹" + invoice.calculateTax());
        System.out.println("Total: ₹" + invoice.calculateTotal());
        System.out.println();

        // 2. Repository — ONLY persistence
        InvoiceRepository repository = new InvoiceRepository();
        repository.save(invoice);

        // 3. PDF Generator — ONLY presentation
        InvoicePDFGenerator pdfGenerator = new InvoicePDFGenerator();
        String pdfPath = pdfGenerator.generate(invoice);

        // 4. Notification Service — ONLY notifications
        InvoiceNotificationService notifier = new InvoiceNotificationService();
        notifier.sendEmail(invoice, "priya@mail.com");
        notifier.sendSMS(invoice, "+91-9876543210");

        // ============================================================
        // WHY THIS IS BETTER:
        // ============================================================
        System.out.println("=".repeat(50));
        System.out.println("  WHY THIS IS BETTER:");
        System.out.println("=".repeat(50));
        System.out.println();
        System.out.println("  SCENARIO 1: DBA says 'Switch to MongoDB'");
        System.out.println("  → Edit ONLY InvoiceRepository.java");
        System.out.println("  → Invoice, PDF, Email — UNTOUCHED");
        System.out.println();
        System.out.println("  SCENARIO 2: Designer says 'New PDF layout'");
        System.out.println("  → Edit ONLY InvoicePDFGenerator.java");
        System.out.println("  → Invoice, DB, Email — UNTOUCHED");
        System.out.println();
        System.out.println("  SCENARIO 3: PM says 'Add GST + CESS'");
        System.out.println("  → Edit ONLY Invoice.java");
        System.out.println("  → DB, PDF, Email — UNTOUCHED");
        System.out.println();
        System.out.println("  SCENARIO 4: Ops says 'Switch to AWS SES'");
        System.out.println("  → Edit ONLY InvoiceNotificationService.java");
        System.out.println("  → Invoice, DB, PDF — UNTOUCHED");
        System.out.println();
        System.out.println("  Each change is ISOLATED. No accidental breakage.");
        System.out.println("  Each class can be tested INDEPENDENTLY.");
        System.out.println("  Each class can be reused in other contexts.");
        System.out.println("=".repeat(50));
    }
}
