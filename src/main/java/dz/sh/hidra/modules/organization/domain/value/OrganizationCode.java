/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationCode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Normalized organization code.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;

import java.util.Locale;

/**
 * Normalized organization code.
 *
 * @param value normalized code value
 */
public record OrganizationCode(String value) {

    public OrganizationCode {
        if (value == null || value.isBlank()) {
            throw new InvalidOrganizationValueException("Organization code must not be null or blank.");
        }
        value = value.trim().toUpperCase(Locale.ROOT);
    }

    public static OrganizationCode of(String value) {
        return new OrganizationCode(value);
    }
}
