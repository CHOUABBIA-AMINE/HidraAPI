/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidTelemetryValueException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.exception
 *
 * @Description : Invalid telemetry value exception.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.exception;

/**
 * Raised when a telemetry value is invalid.
 */
public class InvalidTelemetryValueException extends TelemetryDomainException {

    private static final long serialVersionUID = -9216850861528388154L;

	public InvalidTelemetryValueException(String message) {
        super(message);
    }
}
