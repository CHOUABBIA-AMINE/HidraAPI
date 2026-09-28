/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSegment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Segment between two topology nodes or points.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;
public record PipelineSegment(
        String id,
        String pipelineId,
        String code,
        PipelineSegmentType segmentType,
        String fromNodeId,
        String toNodeId,
        BigDecimal startKilometerPoint,
        BigDecimal endKilometerPoint,
        BigDecimal lengthKm,
        FlowDirection flowDirection,
        TopologyStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public PipelineSegment {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTopologyValueException("PipelineSegment id must not be blank.");
        }
        // HRA-051 required: pipelineId
        if (pipelineId == null || pipelineId.isBlank()) {
            throw new InvalidTopologyValueException("PipelineSegment pipeline id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidTopologyValueException("PipelineSegment code must not be blank.");
        }
        // HRA-051 required: segmentType
        if (segmentType == null) {
            throw new InvalidTopologyValueException("PipelineSegment segment type must not be null.");
        }
        // HRA-051 required: fromNodeId
        if (fromNodeId == null || fromNodeId.isBlank()) {
            throw new InvalidTopologyValueException("PipelineSegment from node id must not be blank.");
        }
        // HRA-051 required: toNodeId
        if (toNodeId == null || toNodeId.isBlank()) {
            throw new InvalidTopologyValueException("PipelineSegment to node id must not be blank.");
        }
        // HRA-051 required: flowDirection
        if (flowDirection == null) {
            throw new InvalidTopologyValueException("PipelineSegment flow direction must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidTopologyValueException("PipelineSegment status must not be null.");
        }

        id = normalize(id);
        pipelineId = normalize(pipelineId);
        code = normalize(code);
        fromNodeId = normalize(fromNodeId);
        toNodeId = normalize(toNodeId);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
