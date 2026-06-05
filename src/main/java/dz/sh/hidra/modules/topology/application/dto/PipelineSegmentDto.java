/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSegmentDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Application DTO representing a pipeline segment.
 *
 */
package dz.sh.hidra.modules.topology.application.dto;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Represents pipeline segment data returned by topology use cases.
 *
 * <p>Business role:
 * This DTO describes a physical pipe segment between two topology nodes.
 *
 * <p>Architecture role:
 * This application DTO is framework-independent and does not expose persistence entities or REST
 * response models.
 *
 * <p>Validation:
 * This DTO is an output projection. Segment validation is enforced by topology domain models,
 * policies, and services.
 *
 * <p>Usage:
 * Use this DTO as an application output and map it to REST responses in the API layer later.
 *
 * @param pipelineSegmentId pipeline segment identifier
 * @param pipelineId parent pipeline identifier
 * @param code business code
 * @param name display name
 * @param fromNodeId from-node identifier
 * @param toNodeId to-node identifier
 * @param lengthKm segment length in kilometers
 * @param diameterInches segment diameter in inches
 * @param status lifecycle status
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
public record PipelineSegmentDto(
        String pipelineSegmentId,
        String pipelineId,
        String code,
        String name,
        String fromNodeId,
        String toNodeId,
        BigDecimal lengthKm,
        BigDecimal diameterInches,
        String status,
        Instant createdAt,
        Instant updatedAt) {
}
