/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitTypeTranslation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Localized translation for an organization unit type catalog entry.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import java.util.Locale;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Localized translation for an organization unit type catalog entry.
 *
 * @param locale translation locale such as en, fr, or ar
 * @param name localized name
 * @param description optional localized description
 */
public record OrganizationUnitTypeTranslation(
        String locale,
        String name,
        String description) implements ValueObject {

    public OrganizationUnitTypeTranslation {
        locale = normalizeLocale(locale);
        name = normalizeRequired(name, "Organization unit type translation name", 160);
        description = normalizeOptional(description, "Organization unit type translation description", 500);
    }

    private static String normalizeLocale(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("Organization unit type translation locale must not be blank.");
        }
        String normalized = value.trim().toLowerCase(Locale.ROOT);
        if (normalized.length() > 10) {
            throw new InvalidValueObjectException("Organization unit type translation locale must not exceed 10 characters.");
        }
        return normalized;
    }

    private static String normalizeRequired(String value, String label, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(label + " must not be blank.");
        }
        String normalized = value.trim();
        if (normalized.length() > maxLength) {
            throw new InvalidValueObjectException(label + " must not exceed " + maxLength + " characters.");
        }
        return normalized;
    }

    private static String normalizeOptional(String value, String label, int maxLength) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String normalized = value.trim();
        if (normalized.length() > maxLength) {
            throw new InvalidValueObjectException(label + " must not exceed " + maxLength + " characters.");
        }
        return normalized;
    }
}
