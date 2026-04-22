// ============================================================
// CONCRETE IMPLEMENTATION 1: Email
// ============================================================
// This class knows HOW to send an email.
// But the outside world only sees "send a notification".
// ============================================================

public class EmailNotification extends NotificationService {

    @Override
    protected void deliverNotification(String recipient, String message) {
        // Imagine this connects to an SMTP server, builds HTML, etc.
        // All that complexity is HIDDEN here.
        System.out.println("   📧 [EMAIL] Connecting to SMTP server...");
        System.out.println("   📧 [EMAIL] Building HTML template...");
        System.out.println("   📧 [EMAIL] Sending email to: " + recipient);
        System.out.println("   📧 [EMAIL] Content: " + message);
    }
}
