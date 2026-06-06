/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListTopologyNodesQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.query
 *
 * @Description : Application query for listing topology nodes.
 *
 */
package dz.sh.hidra.modules.topology.application.query;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.modules.topology.domain.value.FacilityId;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.domain.value.NodeTypeReference;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceId;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/** Carries input required to list topology nodes. */
public record ListTopologyNodesQuery(
        String searchText,
        NodeTypeReference nodeType,
        FacilityId facilityId,
        PipelineAppurtenanceId pipelineAppurtenanceId,
        TopologyStatus status,
        PageRequest pageRequest) implements Query {

    public ListTopologyNodesQuery {
        searchText = normalizeOptional(searchText, "Search text", 120);
        Objects.requireNonNull(pageRequest, "Page request must not be null.");
    }

    @Deprecated(forRemoval = true)
    public ListTopologyNodesQuery(
            String searchText,
            NodeType nodeType,
            FacilityId facilityId,
            PipelineAppurtenanceId pipelineAppurtenanceId,
            TopologyStatus status,
            PageRequest pageRequest) {

        this(searchText, nodeType == null ? null : NodeTypeReference.from(nodeType), facilityId, pipelineAppurtenanceId, status, pageRequest);
    }

    public static ListTopologyNodesQuery all(PageRequest pageRequest) {
        return new ListTopologyNodesQuery(null, (NodeTypeReference) null, null, null, null, pageRequest);
    }

    private static String normalizeOptional(String value, String label, int maxLength) {
        if (value == null || value.isBlank()) { return null; }
        String normalized = value.trim();
        if (normalized.length() > maxLength) { throw new IllegalArgumentException(label + " must not exceed " + maxLength + " characters."); }
        return normalized;
    }
}
