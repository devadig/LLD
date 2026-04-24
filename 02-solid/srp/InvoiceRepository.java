// ============================================================
// ✅ GOOD: InvoiceRepository — ONLY persistence
// ============================================================
// This class has ONE reason to change: database/storage changes.
// Switch from MySQL to PostgreSQL? Edit here.
// Change table schema? Edit here.
// Business logic stays untouched.
// ============================================================

public class InvoiceRepository {

    // Tomorrow this could be a real JPA repository, a MongoDB
    // client, or a file-based storage. The Invoice class
    // doesn't know or care.

    public void save(Invoice invoice) {
        System.out.println("   💾 [DB] Connecting to database...");
        System.out.println("   💾 [DB] INSERT INTO invoices (id, customer, total)");
        System.out.println("   💾 [DB]   VALUES ('" + invoice.getInvoiceId() + "', '" +
                invoice.getCustomerName() + "', " + invoice.calculateTotal() + ")");

        // Save line items
        for (int i = 0; i < invoice.getItemCount(); i++) {
            System.out.println("   💾 [DB] INSERT INTO invoice_items (invoice_id, name, price)");
            System.out.println("   💾 [DB]   VALUES ('" + invoice.getInvoiceId() + "', '" +
                    invoice.getItemNames().get(i) + "', " + invoice.getItemPrices().get(i) + ")");
        }
        System.out.println("   💾 [DB] ✅ Invoice saved successfully.\n");
    }

    public Invoice findById(String invoiceId) {
        System.out.println("   💾 [DB] SELECT * FROM invoices WHERE id = '" + invoiceId + "'");
        // In real code, this would return data from DB
        return null;
    }
}
