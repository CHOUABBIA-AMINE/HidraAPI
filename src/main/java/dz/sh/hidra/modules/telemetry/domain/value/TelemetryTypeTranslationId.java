/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryTypeTranslationId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Telemetry type translation identifier.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import java.util.UUID;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Telemetry type translation identifier.
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
public record TelemetryTypeTranslationId(String value) implements ValueObject {

    public TelemetryTypeTranslationId {
        value = normalize(value);
    }

    public static TelemetryTypeTranslationId of(String value) {
        return new TelemetryTypeTranslationId(value);
    }

    public static TelemetryTypeTranslationId newId() {
        return new TelemetryTypeTranslationId(UUID.randomUUID().toString());
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("TelemetryTypeTranslationId must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 80) {
            throw new InvalidValueObjectException("TelemetryTypeTranslationId length must not exceed 80 characters.");
        }

        return normalized;
    }
}
