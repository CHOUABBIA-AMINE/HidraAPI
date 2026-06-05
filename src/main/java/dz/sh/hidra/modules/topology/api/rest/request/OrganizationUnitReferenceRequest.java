/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitReferenceRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : REST request body fragment for neutral organization unit references.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request fragment carrying a neutral organization unit reference.
 *
 * <p>Business role:
 * Links a physical topology facility to an operational organization unit without making topology own
 * employees, positions, assignments, or reporting lines.
 *
 * <p>Architecture role:
 * This is a REST input contract. It must not import organization implementation classes.
 *
 * <p>Validation:
 * Reference type, id, code, and name are required when this fragment is provided.
 *
 * @param referenceType neutral reference type
 * @param referenceId organization unit identifier
 * @param referenceCode organization unit code
 * @param referenceName organization unit name
 */
@Schema(name = "OrganizationUnitReferenceRequest", description = "Neutral organization unit reference request fragment.")
public record OrganizationUnitReferenceRequest(
        @Schema(description = "Reference type.", example = "ORGANIZATION_UNIT", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 80)
        String referenceType,

        @Schema(description = "Organization unit identifier.", example = "ou_550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 120)
        String referenceId,

        @Schema(description = "Organization unit business code.", example = "TRC-OPS-EAST-CS-01", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 120)
        String referenceCode,

        @Schema(description = "Organization unit display name.", example = "Compression Station East 01", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 160)
        String referenceName) {
}
