/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationCode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Canonical normalization and validation policy for stable Organization business codes.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;

import java.util.Locale;

/**
 * Canonical stable business-code value for Organization reference and master data.
 *
 * <p>Business role: provides one normalization rule for language-neutral codes used by
 * administrative geography, organization units and types, positions, and shifts.</p>
 *
 * <p>Architecture role: domain value object reused by domain constructors and active
 * application commands. Persistence/API string contracts remain unchanged where
 * replacing their public type would create unnecessary compatibility churn.</p>
 *
 * <p>Validation: codes must be nonblank; leading/trailing whitespace is removed and
 * letters are normalized to upper case using {@link Locale#ROOT}. No additional
 * character-set restriction is invented because existing authoritative code sets may
 * legitimately use digits, dashes, underscores, dots, colons, or other stable
 * separators.</p>
 *
 * <p>Usage: use for stable language-neutral Organization business codes only. Do not
 * use it for employee numbers, postal codes, database IDs, topology owner codes, or
 * free-form textual identifiers.</p>
 *
 * @param value normalized nonblank business code
 */
public record OrganizationCode(String value) {

    public OrganizationCode {
        if (value == null || value.isBlank()) {
            throw new InvalidOrganizationValueException(
                    "Organization code must not be null or blank."
            );
        }
        value = value.trim().toUpperCase(Locale.ROOT);
    }

    public static OrganizationCode of(String value) {
        return new OrganizationCode(value);
    }
}
