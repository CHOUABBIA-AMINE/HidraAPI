/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentDomainException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.exception
 *
 * @Description : Base incident domain exception.
 *
 */
package dz.sh.hidra.modules.incident.domain.exception;

/**
 * Base exception for incident domain failures.
 */
public class IncidentDomainException extends RuntimeException {

    private static final long serialVersionUID = -31654504039837024L;

	public IncidentDomainException(String message) {
        super(requireMessage(message));
    }

    public IncidentDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Incident exception message must not be null or blank.");
        }
        return message.trim();
    }
}
