/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Application DTO representing a pipeline.
 *
 */
package dz.sh.hidra.modules.topology.application.dto;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Represents pipeline data returned by topology use cases.
 *
 * <p>Business role:
 * This DTO describes a physical pipeline belonging to a pipeline system.
 *
 * <p>Architecture role:
 * This application DTO is framework-independent. It does not expose persistence entities and does
 * not use API, Swagger, JPA, Spring, identity, organization implementation, measurement, flow, risk,
 * workflow, or infrastructure types.
 *
 * <p>Validation:
 * This DTO is an output projection. Pipeline validation is enforced by topology domain objects.
 *
 * <p>Usage:
 * Use this DTO as an application output and map it to REST responses in the API layer later.
 *
 * @param pipelineId pipeline identifier
 * @param pipelineSystemId parent pipeline system identifier
 * @param code business code
 * @param name display name
 * @param description optional description
 * @param productType hydrocarbon product type
 * @param nominalDiameterInches nominal diameter in inches
 * @param designLengthKm design length in kilometers
 * @param status lifecycle status
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
public record PipelineDto(
        String pipelineId,
        String pipelineSystemId,
        String code,
        String name,
        String description,
        String productType,
        BigDecimal nominalDiameterInches,
        BigDecimal designLengthKm,
        String status,
        Instant createdAt,
        Instant updatedAt) {
}
