// ============================================================
// ✅ GOOD: DiscountStrategy — The contract
// ============================================================
// This interface is the KEY to OCP.
// Any new discount type implements this interface.
// Existing code that USES this interface never changes.
// ============================================================

public interface DiscountStrategy {

    // Calculate the discounted price
    double apply(double originalPrice);

    // Human-readable name (for logging/display)
    String getDescription();
}
