/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationDomainException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.exception
 *
 * @Description : Base notification domain exception.
 *
 */
package dz.sh.hidra.modules.notification.domain.exception;

/**
 * Base exception for notification domain failures.
 */
public class NotificationDomainException extends RuntimeException {

    private static final long serialVersionUID = 1688264312538450941L;

	public NotificationDomainException(String message) {
        super(requireMessage(message));
    }

    public NotificationDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Notification exception message must not be null or blank.");
        }
        return message.trim();
    }
}
