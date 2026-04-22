// ============================================================
// ABSTRACTION — "Define WHAT, hide HOW"
// ============================================================
// We use an abstract class here because:
// → It defines a CONTRACT: every notification must have send()
// → It provides SHARED logic: logging, validation (template method)
// → Subclasses only implement the part that VARIES: the actual sending
// ============================================================

public abstract class NotificationService {

    // This is the ABSTRACT method — the "WHAT"
    // Every notification type MUST implement this.
    // But the CALLER never cares about the details inside.
    protected abstract void deliverNotification(String recipient, String message);

    // This is the TEMPLATE — shared behavior that ALL notifications follow.
    // Notice: this method is NOT abstract. It's concrete. It uses the
    // abstract method internally. This is a very common pattern.
    public final void send(String recipient, String message) {
        // Step 1: Validate (shared logic — no need to repeat in every subclass)
        if (recipient == null || recipient.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipient cannot be empty");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }

        // Step 2: Log (shared logic)
        System.out.println("📤 Preparing to send notification to: " + recipient);

        // Step 3: Actually deliver — THIS is where abstraction kicks in.
        // We don't know HOW it's delivered. Email? SMS? Push? Pigeon?
        // We don't care. The subclass handles it.
        deliverNotification(recipient, message);

        // Step 4: Log success (shared logic)
        System.out.println("✅ Notification sent successfully!\n");
    }
}
