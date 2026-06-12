/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingValidationException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.exception
 *
 * @Description : Invalid telemetry reading exception.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.exception;

/**
 * Raised when a telemetry reading violates value-shape or timestamp rules.
 */
public class TelemetryReadingValidationException extends TelemetryDomainException {

    private static final long serialVersionUID = 358394225877874205L;

	public TelemetryReadingValidationException(String message) {
        super(message);
    }
}
