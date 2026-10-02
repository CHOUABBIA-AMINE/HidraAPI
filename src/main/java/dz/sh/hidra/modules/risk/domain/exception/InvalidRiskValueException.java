/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidRiskValueException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.exception
 *
 * @Description : Invalid risk value exception.
 *
 */
package dz.sh.hidra.modules.risk.domain.exception;

/**
 * Raised when a risk value is invalid.
 */
public class InvalidRiskValueException extends RiskDomainException {

    private static final long serialVersionUID = 5429448887627035824L;

	public InvalidRiskValueException(String message) {
        super(message);
    }
}
