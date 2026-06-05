/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : REST response representing a topology pipeline system.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing a topology pipeline system.
 *
 * <p>Business role:
 * Returns a physical hydrocarbon transportation system that groups pipelines.
 *
 * <p>Architecture role:
 * This is a REST output contract mapped from PipelineSystemDto through TopologyRestMapper.
 *
 * <p>Validation:
 * Pipeline system validity is enforced by topology domain models, value objects, policies, and
 * services before this response is produced.
 *
 * @param pipelineSystemId pipeline system identifier
 * @param code pipeline system business code
 * @param name pipeline system display name
 * @param description optional description
 * @param productType hydrocarbon product type
 * @param status lifecycle status
 * @param operationalOwnerReference optional neutral operational owner reference
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
@Schema(name = "PipelineSystemResponse", description = "REST response representing a topology pipeline system.")
public record PipelineSystemResponse(
        @Schema(description = "Pipeline system identifier.", example = "ps_550e8400-e29b-41d4-a716-446655440000")
        String pipelineSystemId,

        @Schema(description = "Pipeline system business code.", example = "GZ1")
        String code,

        @Schema(description = "Pipeline system display name.", example = "Gas Pipeline System GZ1")
        String name,

        @Schema(description = "Optional pipeline system description.", example = "Main gas transportation system.")
        String description,

        @Schema(description = "Hydrocarbon product type.", example = "GAS", allowableValues = {"GAS", "CRUDE_OIL", "CONDENSATE", "LPG", "REFINED_PRODUCT", "MULTIPHASE", "UNKNOWN"})
        String productType,

        @Schema(description = "Pipeline system lifecycle status.", example = "ACTIVE", allowableValues = {"PLANNED", "ACTIVE", "INACTIVE", "UNDER_MAINTENANCE", "RETIRED", "DECOMMISSIONED"})
        String status,

        @Schema(description = "Optional neutral operational owner reference.")
        OperationalOwnerReferenceResponse operationalOwnerReference,

        @Schema(description = "Creation instant.", type = "string", format = "date-time")
        Instant createdAt,

        @Schema(description = "Last update instant.", type = "string", format = "date-time")
        Instant updatedAt) {
}
