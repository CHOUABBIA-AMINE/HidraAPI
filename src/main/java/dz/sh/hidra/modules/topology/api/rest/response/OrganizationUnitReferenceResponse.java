/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitReferenceResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : REST response representing a neutral organization unit reference.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing a neutral organization unit reference.
 *
 * <p>Business role:
 * Returns the organization unit that owns or operates a physical topology asset without making
 * topology own employees, positions, assignments, or reporting lines.
 *
 * <p>Architecture role:
 * This response exposes neutral scalar fields only and must not expose organization implementation
 * classes.
 *
 * <p>Validation:
 * Reference validity is enforced by topology domain value objects before this response is produced.
 *
 * @param referenceType neutral reference type
 * @param referenceId organization unit identifier
 * @param referenceCode organization unit code
 * @param referenceName organization unit name
 */
@Schema(name = "OrganizationUnitReferenceResponse", description = "REST response representing a neutral organization unit reference.")
public record OrganizationUnitReferenceResponse(
        @Schema(description = "Reference type.", example = "ORGANIZATION_UNIT")
        String referenceType,

        @Schema(description = "Organization unit identifier.", example = "ou_550e8400-e29b-41d4-a716-446655440000")
        String referenceId,

        @Schema(description = "Organization unit business code.", example = "TRC-OPS-EAST-CS-01")
        String referenceCode,

        @Schema(description = "Organization unit display name.", example = "Compression Station East 01")
        String referenceName) {
}
