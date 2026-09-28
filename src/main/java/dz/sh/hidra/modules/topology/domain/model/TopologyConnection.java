/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConnection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Directed or undirected graph connection between nodes.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;
public record TopologyConnection(
        String id,
        String code,
        String fromNodeId,
        String toNodeId,
        ConnectionType connectionType,
        FlowDirection flowDirection,
        String pipelineSegmentId,
        BigDecimal nominalCapacity,
        String capacityUnitCode,
        TopologyStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public TopologyConnection {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTopologyValueException("TopologyConnection id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidTopologyValueException("TopologyConnection code must not be blank.");
        }
        // HRA-051 required: fromNodeId
        if (fromNodeId == null || fromNodeId.isBlank()) {
            throw new InvalidTopologyValueException("TopologyConnection from node id must not be blank.");
        }
        // HRA-051 required: toNodeId
        if (toNodeId == null || toNodeId.isBlank()) {
            throw new InvalidTopologyValueException("TopologyConnection to node id must not be blank.");
        }
        // HRA-051 required: connectionType
        if (connectionType == null) {
            throw new InvalidTopologyValueException("TopologyConnection connection type must not be null.");
        }
        // HRA-051 required: flowDirection
        if (flowDirection == null) {
            throw new InvalidTopologyValueException("TopologyConnection flow direction must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidTopologyValueException("TopologyConnection status must not be null.");
        }

        id = normalize(id);
        code = normalize(code);
        fromNodeId = normalize(fromNodeId);
        toNodeId = normalize(toNodeId);
        pipelineSegmentId = normalize(pipelineSegmentId);
        capacityUnitCode = normalize(capacityUnitCode);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
