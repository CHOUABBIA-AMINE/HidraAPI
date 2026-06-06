/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyTypeReferenceResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : REST response representing a localized topology type catalog reference.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing a localized topology type catalog reference.
 *
 * <p>Business role:
 * Exposes one controlled-vocabulary topology type using its stable identifier, language-neutral code,
 * and localized display label.
 *
 * <p>Architecture role:
 * This API contract hides catalog persistence and domain reference internals from REST clients.
 *
 * <p>Validation:
 * The identifier and label are resolved through topology catalog application use cases before the
 * response is returned.
 *
 * @param id catalog entry identifier
 * @param code language-neutral catalog code
 * @param label localized display label
 * @param locale locale used to resolve the label
 */
@Schema(name = "TopologyTypeReferenceResponse", description = "Localized topology type catalog reference.")
public record TopologyTypeReferenceResponse(
        @Schema(description = "Catalog entry identifier.", example = "topology-ft-compression-station")
        String id,

        @Schema(description = "Language-neutral catalog code.", example = "COMPRESSION_STATION")
        String code,

        @Schema(description = "Localized display label.", example = "Station de compression")
        String label,

        @Schema(description = "Locale used for the label.", example = "fr")
        String locale) {
}
