/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseBoundaryViolationException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.exception
 *
 * @Description : HSE boundary violation exception.
 *
 */
package dz.sh.hidra.modules.hse.domain.exception;

/**
 * Raised when HSE attempts to own a foreign lifecycle.
 */
public class HseBoundaryViolationException extends HseDomainException {

    private static final long serialVersionUID = -8840810635076135044L;

	public HseBoundaryViolationException(String message) {
        super(message);
    }
}
