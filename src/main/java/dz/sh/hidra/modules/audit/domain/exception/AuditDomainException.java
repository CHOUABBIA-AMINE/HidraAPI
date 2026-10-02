/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditDomainException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.exception
 *
 * @Description : Base audit domain exception.
 *
 */
package dz.sh.hidra.modules.audit.domain.exception;

/**
 * Base exception for audit domain failures.
 */
public class AuditDomainException extends RuntimeException {

    private static final long serialVersionUID = -475356164866309165L;

	public AuditDomainException(String message) {
        super(requireMessage(message));
    }

    public AuditDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Audit exception message must not be null or blank.");
        }
        return message.trim();
    }
}
