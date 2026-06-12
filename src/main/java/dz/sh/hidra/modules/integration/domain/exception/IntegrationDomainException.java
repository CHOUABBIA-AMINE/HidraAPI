/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationDomainException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.exception
 *
 * @Description : Base integration domain exception.
 *
 */
package dz.sh.hidra.modules.integration.domain.exception;

/**
 * Base exception for integration domain failures.
 */
public class IntegrationDomainException extends RuntimeException {

    private static final long serialVersionUID = 7968481656321926397L;

	public IntegrationDomainException(String message) {
        super(requireMessage(message));
    }

    public IntegrationDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Integration exception message must not be null or blank.");
        }
        return message.trim();
    }
}
