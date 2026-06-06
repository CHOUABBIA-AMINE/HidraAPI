/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : REST response representing a topology pipeline with trilingual labels.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import java.math.BigDecimal;
import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing a topology pipeline.
 */
@Schema(name = "PipelineResponse", description = "REST response representing a topology pipeline with trilingual labels.")
public record PipelineResponse(
        @Schema(description = "Pipeline identifier.", example = "pipe_550e8400-e29b-41d4-a716-446655440000")
        String pipelineId,

        @Schema(description = "Parent pipeline system identifier.", example = "ps_550e8400-e29b-41d4-a716-446655440000")
        String pipelineSystemId,

        @Schema(description = "Pipeline business code.", example = "GZ1-LINE-A")
        String code,

        @Schema(description = "Arabic pipeline display name.", example = "خط الغاز الرئيسي أ")
        String nameAr,

        @Schema(description = "French pipeline display name.", example = "Ligne principale gaz A")
        String nameFr,

        @Schema(description = "English pipeline display name.", example = "GZ1 Main Line A")
        String nameEn,

        @Schema(description = "Arabic pipeline description.", example = "خط نقل رئيسي للغاز.")
        String descriptionAr,

        @Schema(description = "French pipeline description.", example = "Ligne principale de transport de gaz.")
        String descriptionFr,

        @Schema(description = "English pipeline description.", example = "Main gas transportation line.")
        String descriptionEn,

        @Schema(description = "Localized hydrocarbon product type catalog reference.")
        TopologyTypeReferenceResponse productType,

        @Schema(description = "Nominal diameter in inches.", example = "42.000")
        BigDecimal nominalDiameterInches,

        @Schema(description = "Design length in kilometers.", example = "512.300")
        BigDecimal designLengthKm,

        @Schema(description = "Pipeline lifecycle status.", example = "ACTIVE", allowableValues = {"PLANNED", "ACTIVE", "INACTIVE", "UNDER_MAINTENANCE", "RETIRED", "DECOMMISSIONED"})
        String status,

        @Schema(description = "Creation instant.", type = "string", format = "date-time", example = "2026-06-06T12:00:00Z")
        Instant createdAt,

        @Schema(description = "Last update instant.", type = "string", format = "date-time", example = "2026-06-06T12:00:00Z")
        Instant updatedAt) {
}
