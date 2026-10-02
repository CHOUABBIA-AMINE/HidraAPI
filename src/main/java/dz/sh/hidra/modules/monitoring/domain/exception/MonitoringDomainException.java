/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringDomainException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.exception
 *
 * @Description : Base monitoring domain exception.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.exception;

/**
 * Base exception for monitoring domain failures.
 */
public class MonitoringDomainException extends RuntimeException {

    private static final long serialVersionUID = 9209170324373097063L;

	public MonitoringDomainException(String message) {
        super(requireMessage(message));
    }

    public MonitoringDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Monitoring exception message must not be null or blank.");
        }
        return message.trim();
    }
}
