/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityBoundaryViolationException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.exception
 *
 * @Description : Integrity boundary violation exception.
 *
 */
package dz.sh.hidra.modules.integrity.domain.exception;

/**
 * Raised when integrity attempts to own a foreign lifecycle.
 */
public class IntegrityBoundaryViolationException extends IntegrityDomainException {

    private static final long serialVersionUID = 5295725578498886307L;

	public IntegrityBoundaryViolationException(String message) {
        super(message);
    }
}
