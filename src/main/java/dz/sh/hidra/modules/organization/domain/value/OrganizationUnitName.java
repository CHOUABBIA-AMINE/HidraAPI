/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitName
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Validated organization unit name value object.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents the display name of an organization unit.
 *
 * <p>Business role:
 * This value object captures the display name of an organization unit inside the organization bounded context.
 *
 * <p>Architecture role:
 * This is a domain value object and must not depend on Spring, JPA, REST DTO validation, identity,
 * topology, or platform infrastructure.
 *
 * <p>Validation:
 * Organization unit names are trimmed and must be 2 to 160 characters long.
 *
 * <p>Usage:
 * Use this type instead of raw strings when organization domain logic requires this concept.
 *
 * @param value validated value, for example <code>Compression Station East 01</code>
 */
public record OrganizationUnitName(String value) implements ValueObject {

    public OrganizationUnitName {
        value = normalize(value);
    }

    /**
     * Creates the value object from raw input.
     *
     * @param value raw input value
     * @return validated value object
     */
    public static OrganizationUnitName of(String value) {
        return new OrganizationUnitName(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("OrganizationUnitName must not be null or blank.");
        }

        String normalized = value.trim();
        

        if (normalized.length() < 2 || normalized.length() > 160) {
            throw new InvalidValueObjectException("OrganizationUnitName length must be between 2 and 160 characters.");
        }

        return normalized;
    }
}
