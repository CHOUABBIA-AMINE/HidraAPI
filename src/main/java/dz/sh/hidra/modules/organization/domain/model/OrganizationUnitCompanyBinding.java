/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitCompanyBinding
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Preserves an immutable explicit company and unit binding revision.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import java.time.Instant;

/**
 * Organization-owned reference fact. Party eligibility and current-pointer selection
 * belong to the runtime owners; this value makes no legal-employer assertion.
 */
public record OrganizationUnitCompanyBinding(
        String id,
        long revision,
        String organizationUnitId,
        String companyPartyId,
        Instant recordedAt
) {
    public OrganizationUnitCompanyBinding {
        id = requireText(id, "Binding ID is required.");
        organizationUnitId = requireText(organizationUnitId, "Binding organization-unit ID is required.");
        companyPartyId = requireText(companyPartyId, "Binding company Party ID is required.");
        if (revision <= 0) {
            throw new InvalidOrganizationValueException("Binding revision must be positive.");
        }
        if (recordedAt == null) {
            throw new InvalidOrganizationValueException("Binding recorded time is required.");
        }
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidOrganizationValueException(message);
        }
        return value.trim();
    }
}
