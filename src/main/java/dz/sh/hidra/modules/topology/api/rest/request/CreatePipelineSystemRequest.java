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
 * This is a REST input contract. Controllers map it to CreatePipelineSystemCommand through
 * TopologyRestMapper.
 *
 * <p>Validation:
 * Code, name, and product type are required. Description and operational owner reference are
 * optional. Product type must match the topology domain enum.
 *
 * @param code pipeline system business code
 * @param name pipeline system display name
 * @param description optional business description
 * @param productType hydrocarbon product type
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

        @Schema(description = "Hydrocarbon product type.", example = "GAS", allowableValues = {"GAS", "CRUDE_OIL", "CONDENSATE", "LPG", "REFINED_PRODUCT", "MULTIPHASE", "UNKNOWN"}, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Pattern(regexp = "GAS|CRUDE_OIL|CONDENSATE|LPG|REFINED_PRODUCT|MULTIPHASE|UNKNOWN")
        String productType,

        @Schema(description = "Optional neutral operational owner reference.")
        @Valid
        OperationalOwnerReferenceRequest operationalOwnerReference) {
}
