/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListFacilitiesQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.query
 *
 * @Description : Application query for listing physical facilities.
 *
 */
package dz.sh.hidra.modules.topology.application.query;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.modules.topology.domain.value.FacilityType;
import dz.sh.hidra.modules.topology.domain.value.ProductType;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Carries input required to list physical facilities.
 *
 * <p>Business role:
 * This query lists topology resources with optional filters.
 *
 * <p>Architecture role:
 * This is an application query. It is framework-independent and must not depend on API, persistence,
 * Spring, JPA, identity, organization implementation, measurement, flow, risk, workflow, or
 * infrastructure code.
 *
 * <p>Validation:
 * Page request is mandatory. Search text is optional and normalized when present.
 *
 * <p>Usage:
 * Use this query from list use cases.
 *
 * @param searchText optional search text filter
 * @param facilityType optional facility type filter
 * @param productType optional product type filter
 * @param status optional status filter
 * @param pageRequest pagination request
 */
public record ListFacilitiesQuery(
        String searchText,
        FacilityType facilityType,
        ProductType productType,
        TopologyStatus status,
        PageRequest pageRequest) implements Query {

    public ListFacilitiesQuery {
        searchText = normalizeOptional(searchText, "Search text", 120);
        Objects.requireNonNull(pageRequest, "Page request must not be null.");

    }

    /**
     * Creates an unfiltered list query.
     *
     * @param pageRequest pagination request
     * @return list query
     */
    public static ListFacilitiesQuery all(PageRequest pageRequest) {
        return new ListFacilitiesQuery(null, null, null, null, pageRequest);
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
