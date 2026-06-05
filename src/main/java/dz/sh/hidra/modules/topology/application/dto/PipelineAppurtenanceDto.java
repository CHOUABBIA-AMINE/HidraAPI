/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAppurtenanceDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Application DTO representing a pipeline appurtenance.
 *
 */
package dz.sh.hidra.modules.topology.application.dto;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Represents pipeline appurtenance data returned by topology use cases.
 *
 * <p>Business role:
 * This DTO describes a point asset installed along a pipeline such as a valve, injection point,
 * extraction point, purge point, vent point, drain point, sampling point, metering point, scraper
 * launcher, scraper receiver, hot tap, bypass point, or connection point.
 *
 * <p>Architecture role:
 * This application DTO is framework-independent. It does not model operations, permits, telemetry,
 * hydraulic calculations, risk, maintenance, workflow, or persistence details.
 *
 * <p>Validation:
 * This DTO is an output projection. Appurtenance validation is enforced by topology domain models,
 * policies, and services.
 *
 * <p>Usage:
 * Use this DTO as an application output and map it to REST responses in the API layer later.
 *
 * @param pipelineAppurtenanceId appurtenance identifier
 * @param pipelineId parent pipeline identifier
 * @param nodeId topology node identifier
 * @param code business code
 * @param name display name
 * @param appurtenanceType appurtenance type
 * @param valveType optional valve type
 * @param pipelineKilometerPoint KP/PK/chainage
 * @param status lifecycle status
 * @param coordinate optional geographical coordinate
 * @param description optional description
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
public record PipelineAppurtenanceDto(
        String pipelineAppurtenanceId,
        String pipelineId,
        String nodeId,
        String code,
        String name,
        String appurtenanceType,
        String valveType,
        BigDecimal pipelineKilometerPoint,
        String status,
        GeoCoordinateDto coordinate,
        String description,
        Instant createdAt,
        Instant updatedAt) {
}
