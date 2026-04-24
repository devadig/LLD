// ============================================================
// ✅ GOOD: InvoicePDFGenerator — ONLY presentation
// ============================================================
// This class has ONE reason to change: PDF format/layout.
// Add a company logo? Edit here.
// Change font? Edit here.
// Business logic and DB stay untouched.
// ============================================================

public class InvoicePDFGenerator {

    public String generate(Invoice invoice) {
        System.out.println("   📄 [PDF] Creating PDF for invoice: " + invoice.getInvoiceId());
        System.out.println("   📄 [PDF] ┌─────────────────────────────────────┐");
        System.out.println("   📄 [PDF] │  INVOICE: " + invoice.getInvoiceId());
        System.out.println("   📄 [PDF] │  Customer: " + invoice.getCustomerName());
        System.out.println("   📄 [PDF] │─────────────────────────────────────│");

        for (int i = 0; i < invoice.getItemCount(); i++) {
            System.out.printf("   📄 [PDF] │  %-20s  ₹%10.2f │%n",
                    invoice.getItemNames().get(i),
                    invoice.getItemPrices().get(i));
        }

        System.out.println("   📄 [PDF] │─────────────────────────────────────│");
        System.out.printf("   📄 [PDF] │  Subtotal:              ₹%10.2f │%n", invoice.calculateSubtotal());
        System.out.printf("   📄 [PDF] │  Tax (%.0f%%):              ₹%10.2f │%n",
                invoice.getTaxRate(), invoice.calculateTax());
        System.out.printf("   📄 [PDF] │  TOTAL:                 ₹%10.2f │%n", invoice.calculateTotal());
        System.out.println("   📄 [PDF] └─────────────────────────────────────┘");

        String filePath = invoice.getInvoiceId() + ".pdf";
        System.out.println("   📄 [PDF] ✅ Saved as: " + filePath + "\n");
        return filePath;
    }
}
