/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyDomainException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.exception
 *
 * @Description : Base custody domain exception.
 *
 */
package dz.sh.hidra.modules.custody.domain.exception;

/**
 * Base exception for custody domain failures.
 */
public class CustodyDomainException extends RuntimeException {

    private static final long serialVersionUID = 1404920451539034817L;

	public CustodyDomainException(String message) {
        super(requireMessage(message));
    }

    public CustodyDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Custody exception message must not be null or blank.");
        }
        return message.trim();
    }
}
