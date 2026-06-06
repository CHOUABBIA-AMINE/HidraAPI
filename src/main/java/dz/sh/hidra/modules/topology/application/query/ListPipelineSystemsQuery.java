/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListPipelineSystemsQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.query
 *
 * @Description : Application query for listing pipeline systems.
 *
 */
package dz.sh.hidra.modules.topology.application.query;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.modules.topology.domain.value.ProductType;
import dz.sh.hidra.modules.topology.domain.value.ProductTypeReference;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/** Carries input required to list pipeline systems. */
public record ListPipelineSystemsQuery(
        String searchText,
        ProductTypeReference productType,
        TopologyStatus status,
        PageRequest pageRequest) implements Query {

    public ListPipelineSystemsQuery {
        searchText = normalizeOptional(searchText, "Search text", 120);
        Objects.requireNonNull(pageRequest, "Page request must not be null.");
    }

    @Deprecated(forRemoval = true)
    public ListPipelineSystemsQuery(
            String searchText,
            ProductType productType,
            TopologyStatus status,
            PageRequest pageRequest) {

        this(searchText, productType == null ? null : ProductTypeReference.from(productType), status, pageRequest);
    }

    public static ListPipelineSystemsQuery all(PageRequest pageRequest) {
        return new ListPipelineSystemsQuery(null, (ProductTypeReference) null, null, pageRequest);
    }

    private static String normalizeOptional(String value, String label, int maxLength) {
        if (value == null || value.isBlank()) { return null; }
        String normalized = value.trim();
        if (normalized.length() > maxLength) { throw new IllegalArgumentException(label + " must not exceed " + maxLength + " characters."); }
        return normalized;
    }
}
