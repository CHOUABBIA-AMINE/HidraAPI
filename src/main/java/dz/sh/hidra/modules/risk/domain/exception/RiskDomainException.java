/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskDomainException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.exception
 *
 * @Description : Base risk domain exception.
 *
 */
package dz.sh.hidra.modules.risk.domain.exception;

/**
 * Base exception for risk domain failures.
 */
public class RiskDomainException extends RuntimeException {

    private static final long serialVersionUID = 35430148247320237L;

	public RiskDomainException(String message) {
        super(requireMessage(message));
    }

    public RiskDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Risk exception message must not be null or blank.");
        }
        return message.trim();
    }
}
