/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingValueValidator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.service
 *
 * @Description : Validates telemetry reading value shape.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.service;

import dz.sh.hidra.modules.telemetry.domain.exception.TelemetryReadingValidationException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;

/**
 * Validates telemetry reading value shape.
 */
public class TelemetryReadingValueValidator {

    public void validate(TelemetryReading reading) {
        if (reading == null) {
            throw new TelemetryReadingValidationException("Telemetry reading must not be null.");
        }
        if (!reading.hasExactlyOneValue()) {
            throw new TelemetryReadingValidationException("Telemetry reading must contain exactly one value shape.");
        }
    }
}
