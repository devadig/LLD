// ============================================================
// DEMO: Abstraction in action
// ============================================================
// Notice how the sendAlert() method works with ANY notification
// type — it doesn't know or care whether it's Email, SMS, or Push.
// That's abstraction.
// ============================================================

public class Main {

    // ---- THIS is the beauty of abstraction ----
    // This method accepts the ABSTRACT type (NotificationService).
    // It works with Email today, SMS tomorrow, Push next week,
    // and WhatsApp next month — WITHOUT changing a single line here.
    public static void sendAlert(NotificationService service,
                                 String recipient,
                                 String message) {
        service.send(recipient, message);
    }

    public static void main(String[] args) {

        System.out.println("================================================");
        System.out.println("  ABSTRACTION DEMO — Notification System");
        System.out.println("================================================\n");

        // ---- Same method, different behaviors ----
        // The caller (this main method) doesn't know HOW notifications
        // are sent. It just says "send" and the right thing happens.

        System.out.println("--- Sending via Email ---");
        sendAlert(new EmailNotification(),
                  "rahul@example.com",
                  "Your order #1234 has been shipped!");

        System.out.println("--- Sending via SMS ---");
        sendAlert(new SmsNotification(),
                  "+91-9876543210",
                  "OTP for login: 482910");

        System.out.println("--- Sending via Push ---");
        sendAlert(new PushNotification(),
                  "device-token-abc123",
                  "You have a new message from Priya!");

        // ---- Validation still works (inherited from abstract class) ----
        System.out.println("--- Trying with empty recipient ---");
        try {
            sendAlert(new EmailNotification(), "", "Hello");
        } catch (IllegalArgumentException e) {
            System.out.println("🚫 BLOCKED: " + e.getMessage() + "\n");
        }

        // ============================================================
        // KEY INSIGHT: Look at the sendAlert() method above.
        //
        // If tomorrow your PM says "Add WhatsApp notifications",
        // you:
        //   1. Create a WhatsAppNotification class extending
        //      NotificationService
        //   2. Implement deliverNotification()
        //   3. Done. Call sendAlert(new WhatsAppNotification(), ...)
        //
        // You DON'T touch sendAlert().
        // You DON'T touch EmailNotification.
        // You DON'T touch SmsNotification.
        // You DON'T add any if-else chain.
        //
        // THAT is abstraction + extensibility.
        // ============================================================
    }
}
