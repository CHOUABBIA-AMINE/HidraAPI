/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationBoundaryViolationException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.exception
 *
 * @Description : Notification boundary violation exception.
 *
 */
package dz.sh.hidra.modules.notification.domain.exception;

/**
 * Raised when notification attempts to own source business state or provider secrets.
 */
public class NotificationBoundaryViolationException extends NotificationDomainException {

    private static final long serialVersionUID = -7501115113254135280L;

	public NotificationBoundaryViolationException(String message) {
        super(message);
    }
}
