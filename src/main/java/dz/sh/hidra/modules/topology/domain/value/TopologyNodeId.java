/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyNodeId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Stable value object identifier for a topology node.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.util.UUID;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents the stable identifier of a topology node.
 *
 * <p>Business role:
 * This value object uniquely identifies a topology node inside the topology bounded context.
 *
 * <p>Architecture role:
 * This is a pure topology domain value object. It must not depend on Spring, JPA, REST, identity,
 * organization, platform, measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * The identifier rejects null and blank values. Generated identifiers use the <code>node_</code>
 * prefix followed by a random UUID.
 *
 * <p>Usage:
 * Use this type in topology domain and application code instead of raw strings.
 *
 * @param value stable identifier value, for example <code>node_550e8400-e29b-41d4-a716-446655440000</code>
 */
public record TopologyNodeId(String value) implements ValueObject {

    private static final String PREFIX = "node_";

    public TopologyNodeId {
        value = normalize(value);
    }

    /**
     * Creates a new generated identifier.
     *
     * @return generated identifier
     */
    public static TopologyNodeId newId() {
        return new TopologyNodeId(PREFIX + UUID.randomUUID());
    }

    /**
     * Creates an identifier from an existing persisted value.
     *
     * @param value existing identifier value
     * @return validated identifier
     */
    public static TopologyNodeId of(String value) {
        return new TopologyNodeId(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("TopologyNodeId must not be null or blank.");
        }
        return value.trim();
    }
}
