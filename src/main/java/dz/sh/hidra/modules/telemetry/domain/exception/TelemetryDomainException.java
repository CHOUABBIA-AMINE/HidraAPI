/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryDomainException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.exception
 *
 * @Description : Base telemetry domain exception.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.exception;

/**
 * Base exception for telemetry domain failures.
 */
public class TelemetryDomainException extends RuntimeException {

    private static final long serialVersionUID = 4379191940360359748L;

	public TelemetryDomainException(String message) {
        super(requireMessage(message));
    }

    public TelemetryDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Telemetry exception message must not be null or blank.");
        }
        return message.trim();
    }
}
