/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseDomainException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.exception
 *
 * @Description : Base HSE domain exception.
 *
 */
package dz.sh.hidra.modules.hse.domain.exception;

/**
 * Base exception for HSE domain failures.
 */
public class HseDomainException extends RuntimeException {

    private static final long serialVersionUID = 3394571332064692554L;

	public HseDomainException(String message) {
        super(requireMessage(message));
    }

    public HseDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("HSE exception message must not be null or blank.");
        }
        return message.trim();
    }
}
