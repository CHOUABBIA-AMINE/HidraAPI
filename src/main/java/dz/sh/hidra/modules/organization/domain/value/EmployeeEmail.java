/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeEmail
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Validated employee email value object.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import java.util.regex.Pattern;
import java.util.Locale;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents the professional email address of an employee.
 *
 * <p>Business role:
 * This value object captures the professional email address of an employee inside the organization bounded context.
 *
 * <p>Architecture role:
 * This is a domain value object and must not depend on Spring, JPA, REST DTO validation, identity,
 * topology, or platform infrastructure.
 *
 * <p>Validation:
 * Email addresses are trimmed, lower-cased, must be 5 to 120 characters long, and must follow a lightweight email format.
 *
 * <p>Usage:
 * Use this type instead of raw strings when organization domain logic requires this concept.
 *
 * @param value validated value, for example <code>abir.medjerab@example.com</code>
 */
public record EmployeeEmail(String value) implements ValueObject {

    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);

    public EmployeeEmail {
        value = normalize(value);
    }

    /**
     * Creates the value object from raw input.
     *
     * @param value raw input value
     * @return validated value object
     */
    public static EmployeeEmail of(String value) {
        return new EmployeeEmail(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("EmployeeEmail must not be null or blank.");
        }

        String normalized = value.trim();
        normalized = normalized.toLowerCase(Locale.ROOT);

        if (normalized.length() < 5 || normalized.length() > 120) {
            throw new InvalidValueObjectException("EmployeeEmail length must be between 5 and 120 characters.");
        }

        if (!ALLOWED_PATTERN.matcher(normalized).matches()) {
            throw new InvalidValueObjectException("EmployeeEmail must be a valid email address.");
        }

        return normalized;
    }
}
