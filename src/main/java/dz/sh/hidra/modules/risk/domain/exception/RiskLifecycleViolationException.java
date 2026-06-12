/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskLifecycleViolationException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.exception
 *
 * @Description : Risk lifecycle violation exception.
 *
 */
package dz.sh.hidra.modules.risk.domain.exception;

/**
 * Raised when a risk lifecycle transition is invalid.
 */
public class RiskLifecycleViolationException extends RiskDomainException {

    private static final long serialVersionUID = 5088550664548066910L;

	public RiskLifecycleViolationException(String message) {
        super(message);
    }
}
