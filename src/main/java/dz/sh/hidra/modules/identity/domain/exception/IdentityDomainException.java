/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityDomainException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.exception
 *
 * @Description : Defines the base identity domain exception.
 *
 */
package dz.sh.hidra.modules.identity.domain.exception;

/**
 * Base exception for identity domain failures.
 */
public class IdentityDomainException extends RuntimeException {

    public IdentityDomainException(String message) {
        super(requireMessage(message));
    }

    public IdentityDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Identity exception message must not be null or blank.");
        }
        return message.trim();
    }
}
