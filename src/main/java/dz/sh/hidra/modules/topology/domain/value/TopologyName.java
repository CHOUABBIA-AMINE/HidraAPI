/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyName
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Validated topology display name value object.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents a human-readable name for a topology asset.
 *
 * <p>Business role:
 * This value object captures the display or business name of a physical topology asset.
 *
 * <p>Architecture role:
 * This is a pure topology domain value object and must not depend on Spring, JPA, REST, identity,
 * organization, platform, measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * Names are trimmed and must be 2 to 160 characters long.
 *
 * <p>Usage:
 * Use this type instead of raw strings whenever topology domain logic requires a name.
 *
 * @param value validated name, for example <code>Compression Station East 01</code>
 */
public record TopologyName(String value) implements ValueObject {

    public TopologyName {
        value = normalize(value);
    }

    /**
     * Creates a topology name from raw input.
     *
     * @param value raw name
     * @return validated topology name
     */
    public static TopologyName of(String value) {
        return new TopologyName(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("TopologyName must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() < 2 || normalized.length() > 160) {
            throw new InvalidValueObjectException("TopologyName length must be between 2 and 160 characters.");
        }

        return normalized;
    }
}
