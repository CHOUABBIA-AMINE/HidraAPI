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
import dz.sh.hidra.modules.topology.domain.value.FacilityTypeReference;
import dz.sh.hidra.modules.topology.domain.value.ProductType;
import dz.sh.hidra.modules.topology.domain.value.ProductTypeReference;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/** Carries input required to list physical facilities. */
public record ListFacilitiesQuery(
        String searchText,
        FacilityTypeReference facilityType,
        ProductTypeReference productType,
        TopologyStatus status,
        PageRequest pageRequest) implements Query {

    public ListFacilitiesQuery {
        searchText = normalizeOptional(searchText, "Search text", 120);
        Objects.requireNonNull(pageRequest, "Page request must not be null.");
    }

    @Deprecated(forRemoval = true)
    public ListFacilitiesQuery(
            String searchText,
            FacilityType facilityType,
            ProductType productType,
            TopologyStatus status,
            PageRequest pageRequest) {

        this(
                searchText,
                facilityType == null ? null : FacilityTypeReference.from(facilityType),
                productType == null ? null : ProductTypeReference.from(productType),
                status,
                pageRequest);
    }

    @Deprecated(forRemoval = true)
    public ListFacilitiesQuery(
            String searchText,
            FacilityTypeReference facilityType,
            ProductType productType,
            TopologyStatus status,
            PageRequest pageRequest) {

        this(
                searchText,
                facilityType,
                productType == null ? null : ProductTypeReference.from(productType),
                status,
                pageRequest);
    }

    @Deprecated(forRemoval = true)
    public ListFacilitiesQuery(
            String searchText,
            FacilityType facilityType,
            ProductTypeReference productType,
            TopologyStatus status,
            PageRequest pageRequest) {

        this(
                searchText,
                facilityType == null ? null : FacilityTypeReference.from(facilityType),
                productType,
                status,
                pageRequest);
    }

    public static ListFacilitiesQuery all(PageRequest pageRequest) {
        return new ListFacilitiesQuery(null, (FacilityTypeReference) null, (ProductTypeReference) null, null, pageRequest);
    }

    private static String normalizeOptional(String value, String label, int maxLength) {
        if (value == null || value.isBlank()) { return null; }
        String normalized = value.trim();
        if (normalized.length() > maxLength) { throw new IllegalArgumentException(label + " must not exceed " + maxLength + " characters."); }
        return normalized;
    }
}
