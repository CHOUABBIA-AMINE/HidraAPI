/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyBoundaryViolationException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.exception
 *
 * @Description : Custody boundary violation exception.
 *
 */
package dz.sh.hidra.modules.custody.domain.exception;

/**
 * Raised when custody attempts to own telemetry, planning, finance, ERP, or topology state.
 */
public class CustodyBoundaryViolationException extends CustodyDomainException {

    private static final long serialVersionUID = -2384795501620858331L;

	public CustodyBoundaryViolationException(String message) {
        super(message);
    }
}
