/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListOrganizationUnitsQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.query
 *
 * @Description : Application query for listing organization units.
 *
 */
package dz.sh.hidra.modules.organization.application.query;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitTypeReference;

/**
 * Carries input required to list or search organization units.
 *
 * @param searchText optional search text for unit code or name
 * @param type optional organization unit type catalog reference filter
 * @param status optional organization unit status filter
 * @param parentId optional parent organization unit filter
 * @param pageRequest pagination request
 */
public record ListOrganizationUnitsQuery(
        String searchText,
        OrganizationUnitTypeReference type,
        OrganizationUnitStatus status,
        OrganizationUnitId parentId,
        PageRequest pageRequest) implements Query {

    public ListOrganizationUnitsQuery {
        searchText = normalizeOptional(searchText, "Search text", 120);
        Objects.requireNonNull(pageRequest, "Page request must not be null.");
    }

    public static ListOrganizationUnitsQuery all(PageRequest pageRequest) {
        return new ListOrganizationUnitsQuery(null, (OrganizationUnitTypeReference) null, null, null, pageRequest);
    }

    private static String normalizeOptional(String value, String label, int maxLength) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();
        if (normalized.length() > maxLength) {
            throw new IllegalArgumentException(label + " must not exceed " + maxLength + " characters.");
        }
        return normalized;
    }
}
