/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryTypeCatalogId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Telemetry type catalog entry identifier.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import java.util.UUID;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Telemetry type catalog entry identifier.
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
public record TelemetryTypeCatalogId(String value) implements ValueObject {

    public TelemetryTypeCatalogId {
        value = normalize(value);
    }

    public static TelemetryTypeCatalogId of(String value) {
        return new TelemetryTypeCatalogId(value);
    }

    public static TelemetryTypeCatalogId newId() {
        return new TelemetryTypeCatalogId(UUID.randomUUID().toString());
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("TelemetryTypeCatalogId must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 80) {
            throw new InvalidValueObjectException("TelemetryTypeCatalogId length must not exceed 80 characters.");
        }

        return normalized;
    }
}
