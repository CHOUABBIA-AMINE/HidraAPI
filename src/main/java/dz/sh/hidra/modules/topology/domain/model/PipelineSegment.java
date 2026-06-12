/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSegment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
        id = normalize(id);
        pipelineId = normalize(pipelineId);
        code = normalize(code);
        fromNodeId = normalize(fromNodeId);
        toNodeId = normalize(toNodeId);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
