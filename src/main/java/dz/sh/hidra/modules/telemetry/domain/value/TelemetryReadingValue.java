/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingValue
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Validated telemetry reading value object.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import java.math.BigDecimal;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Captures a telemetry reading value with exactly one supported value kind.
 *
 * @param numericValue numeric value, optional
 * @param textValue text value, optional
 * @param booleanValue boolean value, optional
 */
public record TelemetryReadingValue(BigDecimal numericValue, String textValue, Boolean booleanValue) implements ValueObject {

    public TelemetryReadingValue {
        textValue = normalizeText(textValue);
        int populatedValues = 0;

        if (numericValue != null) {
            populatedValues++;
        }
        if (textValue != null) {
            populatedValues++;
        }
        if (booleanValue != null) {
            populatedValues++;
        }

        if (populatedValues != 1) {
            throw new InvalidValueObjectException("TelemetryReadingValue requires exactly one of numericValue, textValue, or booleanValue.");
        }
    }

    public static TelemetryReadingValue numeric(BigDecimal value) {
        return new TelemetryReadingValue(value, null, null);
    }

    public static TelemetryReadingValue text(String value) {
        return new TelemetryReadingValue(null, value, null);
    }

    public static TelemetryReadingValue bool(boolean value) {
        return new TelemetryReadingValue(null, null, value);
    }

    public boolean isNumeric() {
        return numericValue != null;
    }

    private static String normalizeText(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();

        if (normalized.length() > 500) {
            throw new InvalidValueObjectException("TelemetryReadingValue text length must not exceed 500 characters.");
        }

        return normalized;
    }
}
