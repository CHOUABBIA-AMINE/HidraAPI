/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePipelineSystemRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : REST request body for creating a topology pipeline system.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request body used to create a topology pipeline system.
 *
 * <p>Business role:
 * Creates a physical hydrocarbon transportation system that groups pipelines.
 *
 * <p>Architecture role:
 * This REST input contract accepts stable catalog type codes. The API mapper resolves those codes to
 * topology catalog entries before creating application commands.
 *
 * <p>Validation:
 * Code, name, and product type code are required. Description and operational owner reference are
 * optional.
 *
 * @param code pipeline system business code
 * @param name pipeline system display name
 * @param description optional business description
 * @param productTypeCode language-neutral product type catalog code
 * @param operationalOwnerReference optional neutral operational owner reference
 */
@Schema(name = "CreatePipelineSystemRequest", description = "Request body for creating a topology pipeline system.")
public record CreatePipelineSystemRequest(
        @Schema(description = "Pipeline system business code.", example = "GZ1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String code,

        @Schema(description = "Pipeline system display name.", example = "Gas Pipeline System GZ1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 160)
        String name,

        @Schema(description = "Optional pipeline system description.", example = "Main gas transportation system.")
        @Size(max = 500)
        String description,

        @Schema(description = "Language-neutral product type catalog code.", example = "GAS", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        @Pattern(regexp = "[A-Za-z0-9][A-Za-z0-9_.-]{1,79}")
        String productTypeCode,

        @Schema(description = "Optional neutral operational owner reference.")
        @Valid
        OperationalOwnerReferenceRequest operationalOwnerReference) {
}
