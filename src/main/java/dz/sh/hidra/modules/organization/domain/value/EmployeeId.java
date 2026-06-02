/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Stable value object identifier for an employee.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import java.util.UUID;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents the stable identifier of an employee.
 *
 * <p>Business role:
 * This value object uniquely identifies an employee inside the organization bounded context.
 *
 * <p>Architecture role:
 * This is a domain value object and must remain independent from Spring, JPA, REST, identity,
 * topology, and platform infrastructure.
 *
 * <p>Validation:
 * The identifier rejects null and blank values. Generated identifiers use the <code>emp_</code>
 * prefix followed by a random UUID.
 *
 * <p>Usage:
 * Use this type in organization domain and application code instead of raw strings.
 *
 * @param value stable identifier value, for example <code>emp_550e8400-e29b-41d4-a716-446655440000</code>
 */
public record EmployeeId(String value) implements ValueObject {

    private static final String PREFIX = "emp_";

    public EmployeeId {
        value = normalize(value);
    }

    /**
     * Creates a new generated identifier.
     *
     * @return generated identifier
     */
    public static EmployeeId newId() {
        return new EmployeeId(PREFIX + UUID.randomUUID());
    }

    /**
     * Creates an identifier from an existing persisted value.
     *
     * @param value existing identifier value
     * @return validated identifier
     */
    public static EmployeeId of(String value) {
        return new EmployeeId(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("EmployeeId must not be null or blank.");
        }
        return value.trim();
    }
}
