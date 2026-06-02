/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignmentId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Stable value object identifier for an employee assignment.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import java.util.UUID;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents the stable identifier of an employee assignment.
 *
 * <p>Business role:
 * This value object uniquely identifies an employee assignment inside the organization bounded context.
 *
 * <p>Architecture role:
 * This is a domain value object and must remain independent from Spring, JPA, REST, identity,
 * topology, and platform infrastructure.
 *
 * <p>Validation:
 * The identifier rejects null and blank values. Generated identifiers use the <code>asg_</code>
 * prefix followed by a random UUID.
 *
 * <p>Usage:
 * Use this type in organization domain and application code instead of raw strings.
 *
 * @param value stable identifier value, for example <code>asg_550e8400-e29b-41d4-a716-446655440000</code>
 */
public record AssignmentId(String value) implements ValueObject {

    private static final String PREFIX = "asg_";

    public AssignmentId {
        value = normalize(value);
    }

    /**
     * Creates a new generated identifier.
     *
     * @return generated identifier
     */
    public static AssignmentId newId() {
        return new AssignmentId(PREFIX + UUID.randomUUID());
    }

    /**
     * Creates an identifier from an existing persisted value.
     *
     * @param value existing identifier value
     * @return validated identifier
     */
    public static AssignmentId of(String value) {
        return new AssignmentId(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("AssignmentId must not be null or blank.");
        }
        return value.trim();
    }
}
