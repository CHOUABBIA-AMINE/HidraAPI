/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryName
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Validated telemetry display name value object.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * User-facing telemetry display name for sources, devices, points, and catalog translations.
 *
 * @param value validated display name
 */
public record TelemetryName(String value) implements ValueObject {

    public TelemetryName {
        value = normalize(value);
    }

    public static TelemetryName of(String value) {
        return new TelemetryName(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("TelemetryName must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() < 2 || normalized.length() > 160) {
            throw new InvalidValueObjectException("TelemetryName length must be between 2 and 160 characters.");
        }

        return normalized;
    }
}
