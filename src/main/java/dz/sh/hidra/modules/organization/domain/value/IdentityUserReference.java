/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityUserReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Neutral identity user reference value object.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents a neutral reference from an employee to a future identity user.
 *
 * <p>Business role:
 * This value object captures a neutral reference from an employee to a future identity user inside the organization bounded context.
 *
 * <p>Architecture role:
 * This is a domain value object and must not depend on Spring, JPA, REST DTO validation, identity,
 * topology, or platform infrastructure.
 *
 * <p>Validation:
 * Identity user references are trimmed and must be 2 to 120 characters long. This value object intentionally stores a neutral identifier and does not import identity domain classes.
 *
 * <p>Usage:
 * Use this type instead of raw strings when organization domain logic requires this concept.
 *
 * @param value validated value, for example <code>usr_550e8400-e29b-41d4-a716-446655440000</code>
 */
public record IdentityUserReference(String value) implements ValueObject {

    public IdentityUserReference {
        value = normalize(value);
    }

    /**
     * Creates the value object from raw input.
     *
     * @param value raw input value
     * @return validated value object
     */
    public static IdentityUserReference of(String value) {
        return new IdentityUserReference(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("IdentityUserReference must not be null or blank.");
        }

        String normalized = value.trim();
        

        if (normalized.length() < 2 || normalized.length() > 120) {
            throw new InvalidValueObjectException("IdentityUserReference length must be between 2 and 120 characters.");
        }

        return normalized;
    }
}
