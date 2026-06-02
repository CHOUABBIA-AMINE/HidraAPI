/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListEmployeesQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.query
 *
 * @Description : Application query for listing employees.
 *
 */
package dz.sh.hidra.modules.organization.application.query;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.modules.organization.domain.value.EmploymentStatus;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;

/**
 * Carries input required to list or search employees.
 *
 * <p>Business role:
 * This query lists operational employees and can optionally filter by text, employment status, or
 * assigned organization unit.
 *
 * <p>Architecture role:
 * This is an application query. It is framework-independent and must not depend on API, persistence,
 * Spring, JPA, identity, topology, platform, or infrastructure code.
 *
 * <p>Validation:
 * Page request is mandatory. Search text is optional and normalized when present. Status and
 * organization unit filters are optional.
 *
 * <p>Usage:
 * Use this query from list employee use cases.
 *
 * @param searchText optional search text for employee number, name, or email
 * @param status optional employment status filter
 * @param organizationUnitId optional organization unit filter
 * @param pageRequest pagination request
 */
public record ListEmployeesQuery(
        String searchText,
        EmploymentStatus status,
        OrganizationUnitId organizationUnitId,
        PageRequest pageRequest) implements Query {

    public ListEmployeesQuery {
        searchText = normalizeOptional(searchText, "Search text", 120);
        Objects.requireNonNull(pageRequest, "Page request must not be null.");
    }

    /**
     * Creates an unfiltered employee list query.
     *
     * @param pageRequest pagination request
     * @return list employees query
     */
    public static ListEmployeesQuery all(PageRequest pageRequest) {
        return new ListEmployeesQuery(null, null, null, pageRequest);
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
