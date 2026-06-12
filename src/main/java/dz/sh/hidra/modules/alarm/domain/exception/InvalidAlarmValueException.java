/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidAlarmValueException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.exception
 *
 * @Description : Invalid alarm value exception.
 *
 */
package dz.sh.hidra.modules.alarm.domain.exception;

/**
 * Raised when an alarm value is invalid.
 */
public class InvalidAlarmValueException extends AlarmDomainException {

    private static final long serialVersionUID = 7499968262837927300L;

	public InvalidAlarmValueException(String message) {
        super(message);
    }
}
