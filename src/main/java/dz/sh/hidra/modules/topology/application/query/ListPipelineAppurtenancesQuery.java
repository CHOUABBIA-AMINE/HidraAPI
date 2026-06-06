/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListPipelineAppurtenancesQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.query
 *
 * @Description : Application query for listing pipeline appurtenances.
 *
 */
package dz.sh.hidra.modules.topology.application.query;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceType;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceTypeReference;
import dz.sh.hidra.modules.topology.domain.value.PipelineId;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;
import dz.sh.hidra.modules.topology.domain.value.ValveType;
import dz.sh.hidra.modules.topology.domain.value.ValveTypeReference;

/** Carries input required to list pipeline appurtenances. */
public record ListPipelineAppurtenancesQuery(
        String searchText,
        PipelineId pipelineId,
        PipelineAppurtenanceTypeReference appurtenanceType,
        ValveTypeReference valveType,
        TopologyStatus status,
        PageRequest pageRequest) implements Query {

    public ListPipelineAppurtenancesQuery {
        searchText = normalizeOptional(searchText, "Search text", 120);
        Objects.requireNonNull(pageRequest, "Page request must not be null.");
        if (appurtenanceType != null && !appurtenanceType.isValve() && valveType != null) {
            throw new IllegalArgumentException("Valve type filter can only be used with VALVE appurtenance type.");
        }
    }

    @Deprecated(forRemoval = true)
    public ListPipelineAppurtenancesQuery(
            String searchText,
            PipelineId pipelineId,
            PipelineAppurtenanceType appurtenanceType,
            ValveType valveType,
            TopologyStatus status,
            PageRequest pageRequest) {

        this(
                searchText,
                pipelineId,
                appurtenanceType == null ? null : PipelineAppurtenanceTypeReference.from(appurtenanceType),
                ValveTypeReference.from(valveType),
                status,
                pageRequest);
    }

    public static ListPipelineAppurtenancesQuery all(PageRequest pageRequest) {
        return new ListPipelineAppurtenancesQuery(null, null, (PipelineAppurtenanceTypeReference) null, null, null, pageRequest);
    }

    private static String normalizeOptional(String value, String label, int maxLength) {
        if (value == null || value.isBlank()) { return null; }
        String normalized = value.trim();
        if (normalized.length() > maxLength) { throw new IllegalArgumentException(label + " must not exceed " + maxLength + " characters."); }
        return normalized;
    }
}
