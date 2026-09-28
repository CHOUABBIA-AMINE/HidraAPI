/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Organization-owned policy for validating and generating String/UUID identifiers.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;

import java.util.UUID;

/**
 * Organization-owned policy for validating and generating String/UUID identifiers.
 *
 * <p>Business role: provides one normalization/validation rule for textual identifiers
 * created by the Organization module.</p>
 *
 * <p>Architecture role: this value object is intentionally a boundary policy rather than
 * the mandatory component type of every Organization domain record. Existing records keep
 * their identifier components as {@link String} to avoid broad compatibility churn, while
 * creation/write paths may use {@link #newId()} and {@link #of(String)} to enforce the
 * canonical Organization String-ID rule.</p>
 *
 * <p>Scope: this policy applies only to String/UUID-style identifiers owned by Organization.
 * It does not apply to {@code OperationalScope.id}, whose generated registry identity is a
 * positive {@link Long} by design.</p>
 *
 * <p>Validation: values are trimmed and null/blank identifiers are rejected.</p>
 *
 * @param value validated textual identifier value
 */
public record OrganizationId(String value) {

    public OrganizationId {
        value = requireText(value, "Organization ID must not be null or blank.");
    }

    /**
     * Validates and normalizes an existing Organization-owned String identifier.
     *
     * @param value identifier text
     * @return validated Organization identifier
     */
    public static OrganizationId of(String value) {
        return new OrganizationId(value);
    }

    /**
     * Creates a new Organization-owned UUID identifier represented as canonical text.
     *
     * @return newly generated Organization identifier
     */
    public static OrganizationId newId() {
        return new OrganizationId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidOrganizationValueException(message);
        }
        return value.trim();
    }
}
