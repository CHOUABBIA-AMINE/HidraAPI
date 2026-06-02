/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListPositionsQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.query
 *
 * @Description : Application query for listing organization positions.
 *
 */
package dz.sh.hidra.modules.organization.application.query;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.query.Query;

/**
 * Carries input required to list or search organization positions.
 *
 * <p>Business role:
 * This query lists operational positions/functions such as Station Team Leader, Station Boss,
 * Region Director, Gas Flux Director, or Department Chief. These are not identity roles.
 *
 * <p>Architecture role:
 * This is an application query. It must not depend on API, persistence, identity, topology,
 * platform, Spring, JPA, or infrastructure code.
 *
 * <p>Validation:
 * Page request is mandatory. Search text is optional and normalized. Active-only is a simple
 * application filter flag.
 *
 * <p>Usage:
 * Use this query from position listing use cases.
 *
 * @param searchText optional search text for position code or title
 * @param activeOnly whether only active positions should be returned
 * @param pageRequest pagination request
 */
public record ListPositionsQuery(
        String searchText,
        boolean activeOnly,
        PageRequest pageRequest) implements Query {

    public ListPositionsQuery {
        searchText = normalizeOptional(searchText, "Search text", 120);
        Objects.requireNonNull(pageRequest, "Page request must not be null.");
    }

    /**
     * Creates a query for active positions only.
     *
     * @param pageRequest pagination request
     * @return list positions query
     */
    public static ListPositionsQuery active(PageRequest pageRequest) {
        return new ListPositionsQuery(null, true, pageRequest);
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
