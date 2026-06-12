/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationId
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Stable organization module identifier.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;

import java.util.UUID;

/**
 * Stable organization module identifier.
 *
 * @param value identifier value
 */
public record OrganizationId(String value) {

    public OrganizationId {
        value = requireText(value, "Organization ID must not be null or blank.");
    }

    public static OrganizationId of(String value) {
        return new OrganizationId(value);
    }

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
