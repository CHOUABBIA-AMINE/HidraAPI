/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitCode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Validated organization unit code value object.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import java.util.regex.Pattern;
import java.util.Locale;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents the unique business code of an organization unit.
 *
 * <p>Business role:
 * This value object captures the unique business code of an organization unit inside the organization bounded context.
 *
 * <p>Architecture role:
 * This is a domain value object and must not depend on Spring, JPA, REST DTO validation, identity,
 * topology, or platform infrastructure.
 *
 * <p>Validation:
 * Organization unit codes are trimmed, upper-cased, must be 2 to 80 characters long, and may contain uppercase letters, digits, underscores, or hyphens.
 *
 * <p>Usage:
 * Use this type instead of raw strings when organization domain logic requires this concept.
 *
 * @param value validated value, for example <code>CS_EAST_01</code>
 */
public record OrganizationUnitCode(String value) implements ValueObject {

    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[A-Z0-9][A-Z0-9_-]*$");

    public OrganizationUnitCode {
        value = normalize(value);
    }

    /**
     * Creates the value object from raw input.
     *
     * @param value raw input value
     * @return validated value object
     */
    public static OrganizationUnitCode of(String value) {
        return new OrganizationUnitCode(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("OrganizationUnitCode must not be null or blank.");
        }

        String normalized = value.trim();
        normalized = normalized.toUpperCase(Locale.ROOT);

        if (normalized.length() < 2 || normalized.length() > 80) {
            throw new InvalidValueObjectException("OrganizationUnitCode length must be between 2 and 80 characters.");
        }

        if (!ALLOWED_PATTERN.matcher(normalized).matches()) {
            throw new InvalidValueObjectException("OrganizationUnitCode must contain only uppercase letters, digits, underscores, or hyphens and must start with a letter or digit.");
        }

        return normalized;
    }
}
