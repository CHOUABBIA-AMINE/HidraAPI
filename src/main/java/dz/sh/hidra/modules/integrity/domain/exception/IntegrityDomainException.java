/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityDomainException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.exception
 *
 * @Description : Base integrity domain exception.
 *
 */
package dz.sh.hidra.modules.integrity.domain.exception;

/**
 * Base exception for integrity domain failures.
 */
public class IntegrityDomainException extends RuntimeException {

    private static final long serialVersionUID = 6578316437351170896L;

	public IntegrityDomainException(String message) {
        super(requireMessage(message));
    }

    public IntegrityDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Integrity exception message must not be null or blank.");
        }
        return message.trim();
    }
}
