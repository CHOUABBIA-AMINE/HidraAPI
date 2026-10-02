/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidAuditValueException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.exception
 *
 * @Description : Invalid audit value exception.
 *
 */
package dz.sh.hidra.modules.audit.domain.exception;

/**
 * Raised when an audit value is invalid.
 */
public class InvalidAuditValueException extends AuditDomainException {

    private static final long serialVersionUID = -8738184987121483160L;

	public InvalidAuditValueException(String message) {
        super(message);
    }
}
