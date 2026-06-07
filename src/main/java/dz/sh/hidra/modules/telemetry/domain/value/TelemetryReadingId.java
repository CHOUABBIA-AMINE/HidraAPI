/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Telemetry reading identifier.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import java.util.UUID;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Telemetry reading identifier.
 *
 * <p>Business role:
 * Provides a stable identity for telemetry domain objects without exposing persistence details.
 *
 * <p>Architecture role:
 * Pure telemetry domain value object. It must not depend on Spring, JPA, REST, topology,
 * organization, identity, flow, risk, analytics, workflow, reporting, or notification modules.
 *
 * @param value stable identifier value
 */
public record TelemetryReadingId(String value) implements ValueObject {

    public TelemetryReadingId {
        value = normalize(value);
    }

    public static TelemetryReadingId of(String value) {
        return new TelemetryReadingId(value);
    }

    public static TelemetryReadingId newId() {
        return new TelemetryReadingId(UUID.randomUUID().toString());
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("TelemetryReadingId must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 80) {
            throw new InvalidValueObjectException("TelemetryReadingId length must not exceed 80 characters.");
        }

        return normalized;
    }
}
