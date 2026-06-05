/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Stable value object identifier for a physical facility.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.util.UUID;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents the stable identifier of a physical facility.
 *
 * <p>Business role:
 * This value object uniquely identifies a physical facility inside the topology bounded context.
 *
 * <p>Architecture role:
 * This is a pure topology domain value object. It must not depend on Spring, JPA, REST, identity,
 * organization, platform, measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * The identifier rejects null and blank values. Generated identifiers use the <code>fac_</code>
 * prefix followed by a random UUID.
 *
 * <p>Usage:
 * Use this type in topology domain and application code instead of raw strings.
 *
 * @param value stable identifier value, for example <code>fac_550e8400-e29b-41d4-a716-446655440000</code>
 */
public record FacilityId(String value) implements ValueObject {

    private static final String PREFIX = "fac_";

    public FacilityId {
        value = normalize(value);
    }

    /**
     * Creates a new generated identifier.
     *
     * @return generated identifier
     */
    public static FacilityId newId() {
        return new FacilityId(PREFIX + UUID.randomUUID());
    }

    /**
     * Creates an identifier from an existing persisted value.
     *
     * @param value existing identifier value
     * @return validated identifier
     */
    public static FacilityId of(String value) {
        return new FacilityId(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("FacilityId must not be null or blank.");
        }
        return value.trim();
    }
}
