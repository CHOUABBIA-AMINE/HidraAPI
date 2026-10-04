/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingValueValidator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.service
 *
 * @Description : Enforces the authoritative raw-reading value-shape policy.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.service;

import dz.sh.hidra.modules.telemetry.domain.exception.TelemetryReadingValidationException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.value.ReadingState;
import java.math.BigDecimal;

/**
 * Enforces exactly one typed value, except null-valued rejected/quarantined evidence.
 */
public class TelemetryReadingValueValidator {

    public void validate(TelemetryReading reading) {
        if (reading == null) {
            throw new TelemetryReadingValidationException("Telemetry reading must not be null.");
        }
        validateValueShape(
                reading.state(),
                reading.numericValue(),
                reading.textValue(),
                reading.booleanValue()
        );
    }

    public static void validateValueShape(
            ReadingState state,
            BigDecimal numericValue,
            String textValue,
            Boolean booleanValue
    ) {
        int count = 0;
        if (numericValue != null) {
            count++;
        }
        if (textValue != null && !textValue.isBlank()) {
            count++;
        }
        if (booleanValue != null) {
            count++;
        }

        if (count > 1) {
            throw new TelemetryReadingValidationException(
                    "Telemetry reading must not contain multiple value shapes."
            );
        }

        boolean nullValueAllowed = state == ReadingState.REJECTED
                || state == ReadingState.QUARANTINED;
        if (count == 0 && !nullValueAllowed) {
            throw new TelemetryReadingValidationException(
                    "Telemetry reading must contain exactly one value shape unless rejected or quarantined."
            );
        }
    }
}
