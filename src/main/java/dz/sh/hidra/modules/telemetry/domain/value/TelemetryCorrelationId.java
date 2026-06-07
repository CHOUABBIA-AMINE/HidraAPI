/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCorrelationId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Correlation identifier for telemetry ingestion tracing.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Correlation identifier for telemetry ingestion tracing.
 *
 * @param value raw value
 */
public record TelemetryCorrelationId(String value) implements ValueObject {

    public TelemetryCorrelationId {
        value = normalize(value);
    }

    public static TelemetryCorrelationId of(String value) {
        return new TelemetryCorrelationId(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("TelemetryCorrelationId must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 120) {
            throw new InvalidValueObjectException("TelemetryCorrelationId length must not exceed 120 characters.");
        }

        return normalized;
    }
}
