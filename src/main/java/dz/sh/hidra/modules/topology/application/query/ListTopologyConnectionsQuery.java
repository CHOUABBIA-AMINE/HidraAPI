/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListTopologyConnectionsQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.query
 *
 * @Description : Application query for listing topology connections.
 *
 */
package dz.sh.hidra.modules.topology.application.query;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.modules.topology.domain.value.ConnectionType;
import dz.sh.hidra.modules.topology.domain.value.ConnectionTypeReference;
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/** Carries input required to list topology connections. */
public record ListTopologyConnectionsQuery(
        String searchText,
        TopologyNodeId fromNodeId,
        TopologyNodeId toNodeId,
        ConnectionTypeReference connectionType,
        TopologyAssetType linkedAssetType,
        TopologyStatus status,
        PageRequest pageRequest) implements Query {

    public ListTopologyConnectionsQuery {
        searchText = normalizeOptional(searchText, "Search text", 120);
        Objects.requireNonNull(pageRequest, "Page request must not be null.");
    }

    @Deprecated(forRemoval = true)
    public ListTopologyConnectionsQuery(
            String searchText,
            TopologyNodeId fromNodeId,
            TopologyNodeId toNodeId,
            ConnectionType connectionType,
            TopologyAssetType linkedAssetType,
            TopologyStatus status,
            PageRequest pageRequest) {

        this(searchText, fromNodeId, toNodeId, connectionType == null ? null : ConnectionTypeReference.from(connectionType), linkedAssetType, status, pageRequest);
    }

    public static ListTopologyConnectionsQuery all(PageRequest pageRequest) {
        return new ListTopologyConnectionsQuery(null, null, null, (ConnectionTypeReference) null, null, null, pageRequest);
    }

    private static String normalizeOptional(String value, String label, int maxLength) {
        if (value == null || value.isBlank()) { return null; }
        String normalized = value.trim();
        if (normalized.length() > maxLength) { throw new IllegalArgumentException(label + " must not exceed " + maxLength + " characters."); }
        return normalized;
    }
}
