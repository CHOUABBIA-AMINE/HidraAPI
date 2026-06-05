/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalOwnerReferenceResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : REST response representing a neutral operational owner reference.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing a neutral operational owner reference.
 *
 * <p>Business role:
 * Returns the operational owner of a physical topology asset without coupling topology to
 * organization implementation classes.
 *
 * <p>Architecture role:
 * This response exposes neutral scalar fields only and must not expose organization implementation
 * classes.
 *
 * <p>Validation:
 * Owner reference validity is enforced by topology domain value objects before this response is
 * produced.
 *
 * @param ownerType neutral owner type
 * @param ownerId owner identifier
 * @param ownerCode owner business code
 * @param ownerName owner display name
 */
@Schema(name = "OperationalOwnerReferenceResponse", description = "REST response representing a neutral operational owner reference.")
public record OperationalOwnerReferenceResponse(
        @Schema(description = "Owner type.", example = "ORGANIZATION_UNIT")
        String ownerType,

        @Schema(description = "Owner identifier.", example = "ou_550e8400-e29b-41d4-a716-446655440000")
        String ownerId,

        @Schema(description = "Owner business code.", example = "TRC-OPS-EAST")
        String ownerCode,

        @Schema(description = "Owner display name.", example = "Operational East Region")
        String ownerName) {
}
