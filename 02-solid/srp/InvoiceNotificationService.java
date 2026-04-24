// ============================================================
// ✅ GOOD: InvoiceNotificationService — ONLY notifications
// ============================================================
// This class has ONE reason to change: notification mechanism.
// Switch from SendGrid to AWS SES? Edit here.
// Add Slack notifications? Edit here.
// Business logic, DB, PDF — all untouched.
// ============================================================

public class InvoiceNotificationService {

    public void sendEmail(Invoice invoice, String recipientEmail) {
        System.out.println("   📧 [EMAIL] Preparing email...");
        System.out.println("   📧 [EMAIL] To: " + recipientEmail);
        System.out.println("   📧 [EMAIL] Subject: Invoice #" + invoice.getInvoiceId() +
                " — ₹" + invoice.calculateTotal());
        System.out.println("   📧 [EMAIL] Body: Dear " + invoice.getCustomerName() +
                ", please find your invoice attached.");
        System.out.println("   📧 [EMAIL] ✅ Email sent successfully.\n");
    }

    public void sendSMS(Invoice invoice, String phoneNumber) {
        System.out.println("   💬 [SMS] Sending to: " + phoneNumber);
        System.out.println("   💬 [SMS] Message: Invoice #" + invoice.getInvoiceId() +
                " for ₹" + invoice.calculateTotal() + " has been generated.");
        System.out.println("   💬 [SMS] ✅ SMS sent.\n");
    }
}
