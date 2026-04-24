// ============================================================
// ✅ GOOD: Invoice — ONLY business logic
// ============================================================
// This class has ONE reason to change: business rules.
// Tax calculation changes? Edit here.
// Discount logic changes? Edit here.
// Nothing else lives here. Clean.
// ============================================================

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Invoice {

    private String invoiceId;
    private String customerName;
    private List<String> itemNames = new ArrayList<>();
    private List<Double> itemPrices = new ArrayList<>();
    private double taxRate;

    public Invoice(String invoiceId, String customerName, double taxRate) {
        if (invoiceId == null || invoiceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invoice ID cannot be empty");
        }
        this.invoiceId = invoiceId;
        this.customerName = customerName;
        this.taxRate = taxRate;
    }

    public void addItem(String name, double price) {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
        itemNames.add(name);
        itemPrices.add(price);
    }

    public double calculateSubtotal() {
        double sum = 0;
        for (double price : itemPrices) sum += price;
        return sum;
    }

    public double calculateTax() {
        return calculateSubtotal() * (taxRate / 100);
    }

    public double calculateTotal() {
        return calculateSubtotal() + calculateTax();
    }

    // Read-only access for other classes (persistence, PDF, etc.)
    public String getInvoiceId() { return invoiceId; }
    public String getCustomerName() { return customerName; }
    public List<String> getItemNames() { return Collections.unmodifiableList(itemNames); }
    public List<Double> getItemPrices() { return Collections.unmodifiableList(itemPrices); }
    public double getTaxRate() { return taxRate; }
    public int getItemCount() { return itemNames.size(); }

    @Override
    public String toString() {
        return "Invoice{id='" + invoiceId + "', customer='" + customerName +
               "', total=₹" + calculateTotal() + "}";
    }
}
