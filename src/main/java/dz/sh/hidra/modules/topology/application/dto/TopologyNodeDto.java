/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyNodeDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Application DTO representing a topology node.
 *
 */
package dz.sh.hidra.modules.topology.application.dto;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Represents topology node data returned by topology use cases.
 *
 * <p>Business role:
 * This DTO describes a physical graph vertex such as a facility inlet/outlet, internal facility
 * node, pipeline junction, valve point, injection point, extraction point, purge point, vent point,
 * drain point, metering point, sampling point, scraper point, receipt point, or delivery point.
 *
 * <p>Architecture role:
 * This application DTO is framework-independent and does not expose persistence entities or REST
 * response models.
 *
 * <p>Validation:
 * This DTO is an output projection. Node validation is enforced by topology domain objects.
 *
 * <p>Usage:
 * Use this DTO as an application output and map it to REST responses in the API layer later.
 *
 * @param topologyNodeId topology node identifier
 * @param code business code
 * @param name display name
 * @param nodeType node type
 * @param facilityId optional facility identifier
 * @param pipelineAppurtenanceId optional pipeline appurtenance identifier
 * @param coordinate optional geographical coordinate
 * @param elevationMeters optional elevation in meters
 * @param status lifecycle status
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
public record TopologyNodeDto(
        String topologyNodeId,
        String code,
        String name,
        String nodeType,
        String facilityId,
        String pipelineAppurtenanceId,
        GeoCoordinateDto coordinate,
        BigDecimal elevationMeters,
        String status,
        Instant createdAt,
        Instant updatedAt) {
}
