/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditBoundaryViolationException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.exception
 *
 * @Description : Audit boundary violation exception.
 *
 */
package dz.sh.hidra.modules.audit.domain.exception;

/**
 * Raised when audit attempts to own foreign business state or mutable behavior.
 */
public class AuditBoundaryViolationException extends AuditDomainException {

    private static final long serialVersionUID = -5590728954969758239L;

	public AuditBoundaryViolationException(String message) {
        super(message);
    }
}
