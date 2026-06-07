/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryExternalReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : External reference for telemetry source systems.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * External reference for telemetry source systems.
 *
 * @param value raw value
 */
public record TelemetryExternalReference(String value) implements ValueObject {

    public TelemetryExternalReference {
        value = normalize(value);
    }

    public static TelemetryExternalReference of(String value) {
        return new TelemetryExternalReference(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("TelemetryExternalReference must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 200) {
            throw new InvalidValueObjectException("TelemetryExternalReference length must not exceed 200 characters.");
        }

        return normalized;
    }
}
