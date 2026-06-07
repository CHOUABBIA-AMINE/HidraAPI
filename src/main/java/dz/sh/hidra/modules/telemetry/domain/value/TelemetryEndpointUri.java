/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryEndpointUri
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Endpoint URI value object for telemetry sources.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Endpoint URI value object for telemetry sources.
 *
 * @param value raw value
 */
public record TelemetryEndpointUri(String value) implements ValueObject {

    public TelemetryEndpointUri {
        value = normalize(value);
    }

    public static TelemetryEndpointUri of(String value) {
        return new TelemetryEndpointUri(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("TelemetryEndpointUri must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 500) {
            throw new InvalidValueObjectException("TelemetryEndpointUri length must not exceed 500 characters.");
        }

        return normalized;
    }
}
