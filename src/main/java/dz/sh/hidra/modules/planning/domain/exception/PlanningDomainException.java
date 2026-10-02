/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningDomainException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.exception
 *
 * @Description : Base planning domain exception.
 *
 */
package dz.sh.hidra.modules.planning.domain.exception;

/**
 * Base exception for planning domain failures.
 */
public class PlanningDomainException extends RuntimeException {

    private static final long serialVersionUID = 4284099864577352752L;

	public PlanningDomainException(String message) {
        super(requireMessage(message));
    }

    public PlanningDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Planning exception message must not be null or blank.");
        }
        return message.trim();
    }
}
