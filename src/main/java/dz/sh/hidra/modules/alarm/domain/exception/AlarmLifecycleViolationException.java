/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmLifecycleViolationException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.exception
 *
 * @Description : Alarm lifecycle violation exception.
 *
 */
package dz.sh.hidra.modules.alarm.domain.exception;

/**
 * Raised when an alarm lifecycle transition is invalid.
 */
public class AlarmLifecycleViolationException extends AlarmDomainException {

    private static final long serialVersionUID = 4661184858614717490L;

	public AlarmLifecycleViolationException(String message) {
        super(message);
    }
}
