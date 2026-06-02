/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeFullName
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Validated employee full name value object.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents the full name of an operational employee.
 *
 * <p>Business role:
 * This value object captures the full name of an operational employee inside the organization bounded context.
 *
 * <p>Architecture role:
 * This is a domain value object and must not depend on Spring, JPA, REST DTO validation, identity,
 * topology, or platform infrastructure.
 *
 * <p>Validation:
 * Full names are trimmed and must be 3 to 160 characters long.
 *
 * <p>Usage:
 * Use this type instead of raw strings when organization domain logic requires this concept.
 *
 * @param value validated value, for example <code>Abir MEDJERAB</code>
 */
public record EmployeeFullName(String value) implements ValueObject {

    public EmployeeFullName {
        value = normalize(value);
    }

    /**
     * Creates the value object from raw input.
     *
     * @param value raw input value
     * @return validated value object
     */
    public static EmployeeFullName of(String value) {
        return new EmployeeFullName(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("EmployeeFullName must not be null or blank.");
        }

        String normalized = value.trim();
        

        if (normalized.length() < 3 || normalized.length() > 160) {
            throw new InvalidValueObjectException("EmployeeFullName length must be between 3 and 160 characters.");
        }

        return normalized;
    }
}
