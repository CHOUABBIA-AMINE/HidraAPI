/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidNotificationValueException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.exception
 *
 * @Description : Invalid notification value exception.
 *
 */
package dz.sh.hidra.modules.notification.domain.exception;

/**
 * Raised when a notification value is invalid.
 */
public class InvalidNotificationValueException extends NotificationDomainException {

    private static final long serialVersionUID = 4057514066074932008L;

	public InvalidNotificationValueException(String message) {
        super(message);
    }
}
