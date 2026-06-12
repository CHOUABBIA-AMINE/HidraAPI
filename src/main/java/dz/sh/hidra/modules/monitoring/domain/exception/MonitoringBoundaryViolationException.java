/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringBoundaryViolationException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.exception
 *
 * @Description : Monitoring boundary violation exception.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.exception;

/**
 * Raised when monitoring tries to own a forbidden external lifecycle.
 */
public class MonitoringBoundaryViolationException extends MonitoringDomainException {

    private static final long serialVersionUID = -4074752096836800065L;

	public MonitoringBoundaryViolationException(String message) {
        super(message);
    }
}
