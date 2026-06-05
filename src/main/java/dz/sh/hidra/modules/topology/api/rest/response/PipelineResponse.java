/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : REST response representing a topology pipeline.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import java.math.BigDecimal;
import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing a topology pipeline.
 *
 * <p>Business role:
 * Returns a physical pipeline belonging to a pipeline system.
 *
 * <p>Architecture role:
 * This is a REST output contract mapped from PipelineDto through TopologyRestMapper.
 *
 * <p>Validation:
 * Pipeline validity is enforced by topology domain models, value objects, policies, and services
 * before this response is produced.
 *
 * @param pipelineId pipeline identifier
 * @param pipelineSystemId parent pipeline system identifier
 * @param code pipeline business code
 * @param name pipeline display name
 * @param description optional description
 * @param productType hydrocarbon product type
 * @param nominalDiameterInches nominal diameter in inches
 * @param designLengthKm design length in kilometers
 * @param status lifecycle status
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
@Schema(name = "PipelineResponse", description = "REST response representing a topology pipeline.")
public record PipelineResponse(
        @Schema(description = "Pipeline identifier.", example = "pipe_550e8400-e29b-41d4-a716-446655440000")
        String pipelineId,

        @Schema(description = "Parent pipeline system identifier.", example = "ps_550e8400-e29b-41d4-a716-446655440000")
        String pipelineSystemId,

        @Schema(description = "Pipeline business code.", example = "GZ1-LINE-A")
        String code,

        @Schema(description = "Pipeline display name.", example = "GZ1 Main Line A")
        String name,

        @Schema(description = "Optional pipeline description.", example = "Main transportation line.")
        String description,

        @Schema(description = "Hydrocarbon product type.", example = "GAS", allowableValues = {"GAS", "CRUDE_OIL", "CONDENSATE", "LPG", "REFINED_PRODUCT", "MULTIPHASE", "UNKNOWN"})
        String productType,

        @Schema(description = "Nominal diameter in inches.", example = "42.000")
        BigDecimal nominalDiameterInches,

        @Schema(description = "Design length in kilometers.", example = "512.300")
        BigDecimal designLengthKm,

        @Schema(description = "Pipeline lifecycle status.", example = "ACTIVE", allowableValues = {"PLANNED", "ACTIVE", "INACTIVE", "UNDER_MAINTENANCE", "RETIRED", "DECOMMISSIONED"})
        String status,

        @Schema(description = "Creation instant.", type = "string", format = "date-time")
        Instant createdAt,

        @Schema(description = "Last update instant.", type = "string", format = "date-time")
        Instant updatedAt) {
}
