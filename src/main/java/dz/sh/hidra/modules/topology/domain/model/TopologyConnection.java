/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConnection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
        id = normalize(id);
        code = normalize(code);
        fromNodeId = normalize(fromNodeId);
        toNodeId = normalize(toNodeId);
        pipelineSegmentId = normalize(pipelineSegmentId);
        capacityUnitCode = normalize(capacityUnitCode);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
