/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalOwnerReferenceRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : REST request body fragment for neutral operational owner references.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request fragment carrying a neutral operational owner reference.
 *
 * <p>Business role:
 * Links a topology asset to its operational owner without coupling topology to organization
 * implementation classes.
 *
 * <p>Architecture role:
 * This is a REST input contract. It must not import organization, identity, measurement, flow, risk,
 * workflow, or infrastructure implementation classes.
 *
 * <p>Validation:
 * Owner type, id, code, and name are required when this fragment is provided.
 *
 * @param ownerType neutral owner type
 * @param ownerId owner identifier
 * @param ownerCode owner business code
 * @param ownerName owner display name
 */
@Schema(name = "OperationalOwnerReferenceRequest", description = "Neutral operational owner reference request fragment.")
public record OperationalOwnerReferenceRequest(
        @Schema(description = "Owner type.", example = "ORGANIZATION_UNIT", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String ownerType,

        @Schema(description = "Owner identifier.", example = "ou_550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 120)
        String ownerId,

        @Schema(description = "Owner business code.", example = "TRC-OPS-EAST", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 120)
        String ownerCode,

        @Schema(description = "Owner display name.", example = "Operational East Region", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 160)
        String ownerName) {
}
