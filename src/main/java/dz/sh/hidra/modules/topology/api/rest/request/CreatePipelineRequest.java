/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePipelineRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : REST request body for creating a topology pipeline.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request body used to create a physical pipeline.
 *
 * <p>Business role:
 * Creates a physical pipeline under a pipeline system.
 *
 * <p>Architecture role:
 * This REST input contract accepts a stable product type catalog code. The API mapper resolves it to
 * a catalog reference before calling the application layer.
 *
 * <p>Validation:
 * Pipeline system id, code, name, product type code, nominal diameter, and design length are
 * required.
 *
 * @param pipelineSystemId parent pipeline system identifier
 * @param code pipeline business code
 * @param name pipeline display name
 * @param description optional business description
 * @param productTypeCode language-neutral product type catalog code
 * @param nominalDiameterInches nominal diameter in inches
 * @param designLengthKm design length in kilometers
 */
@Schema(name = "CreatePipelineRequest", description = "Request body for creating a topology pipeline.")
public record CreatePipelineRequest(
        @Schema(description = "Parent pipeline system identifier.", example = "ps_550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String pipelineSystemId,

        @Schema(description = "Pipeline business code.", example = "GZ1-LINE-A", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String code,

        @Schema(description = "Pipeline display name.", example = "GZ1 Main Line A", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 160)
        String name,

        @Schema(description = "Optional pipeline description.", example = "Main transportation line.")
        @Size(max = 500)
        String description,

        @Schema(description = "Language-neutral product type catalog code.", example = "GAS", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        @Pattern(regexp = "[A-Za-z0-9][A-Za-z0-9_.-]{1,79}")
        String productTypeCode,

        @Schema(description = "Nominal diameter in inches.", example = "42.000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal nominalDiameterInches,

        @Schema(description = "Design length in kilometers.", example = "512.300", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal designLengthKm) {
}
