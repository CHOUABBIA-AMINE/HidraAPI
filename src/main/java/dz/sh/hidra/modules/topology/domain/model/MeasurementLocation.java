/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MeasurementLocation
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Topology anchor where telemetry/custody/monitoring measurements are associated.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;
public record MeasurementLocation(
        String id,
        String code,
        MeasurementLocationType measurementLocationType,
        String pipelineId,
        String pipelineSegmentId,
        String facilityId,
        String nodeId,
        String equipmentId,
        BigDecimal kilometerPoint,
        String description,
        TopologyStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public MeasurementLocation {
        id = normalize(id);
        code = normalize(code);
        pipelineId = normalize(pipelineId);
        pipelineSegmentId = normalize(pipelineSegmentId);
        facilityId = normalize(facilityId);
        nodeId = normalize(nodeId);
        equipmentId = normalize(equipmentId);
        description = normalize(description);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
