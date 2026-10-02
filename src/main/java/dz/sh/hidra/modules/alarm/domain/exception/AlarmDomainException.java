/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmDomainException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.exception
 *
 * @Description : Base alarm domain exception.
 *
 */
package dz.sh.hidra.modules.alarm.domain.exception;

/**
 * Base exception for alarm domain failures.
 */
public class AlarmDomainException extends RuntimeException {

    private static final long serialVersionUID = -2024539983786008757L;

	public AlarmDomainException(String message) {
        super(requireMessage(message));
    }

    public AlarmDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Alarm exception message must not be null or blank.");
        }
        return message.trim();
    }
}
