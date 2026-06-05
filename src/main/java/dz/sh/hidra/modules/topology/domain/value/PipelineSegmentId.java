/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSegmentId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Stable value object identifier for a pipeline segment.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.util.UUID;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents the stable identifier of a pipeline segment.
 *
 * <p>Business role:
 * This value object uniquely identifies a pipeline segment inside the topology bounded context.
 *
 * <p>Architecture role:
 * This is a pure topology domain value object. It must not depend on Spring, JPA, REST, identity,
 * organization, platform, measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * The identifier rejects null and blank values. Generated identifiers use the <code>seg_</code>
 * prefix followed by a random UUID.
 *
 * <p>Usage:
 * Use this type in topology domain and application code instead of raw strings.
 *
 * @param value stable identifier value, for example <code>seg_550e8400-e29b-41d4-a716-446655440000</code>
 */
public record PipelineSegmentId(String value) implements ValueObject {

    private static final String PREFIX = "seg_";

    public PipelineSegmentId {
        value = normalize(value);
    }

    /**
     * Creates a new generated identifier.
     *
     * @return generated identifier
     */
    public static PipelineSegmentId newId() {
        return new PipelineSegmentId(PREFIX + UUID.randomUUID());
    }

    /**
     * Creates an identifier from an existing persisted value.
     *
     * @param value existing identifier value
     * @return validated identifier
     */
    public static PipelineSegmentId of(String value) {
        return new PipelineSegmentId(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("PipelineSegmentId must not be null or blank.");
        }
        return value.trim();
    }
}
