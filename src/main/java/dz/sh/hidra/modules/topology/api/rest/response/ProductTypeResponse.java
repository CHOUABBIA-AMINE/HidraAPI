/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductTypeResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : REST response exposing a product type catalog entry with trilingual labels.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response exposing a product type catalog entry with trilingual labels.
 *
 * <p>Business role:
 * Gives clients stable product type reference data while treating Arabic, French, and English labels
 * as first-class API fields.
 *
 * <p>Architecture role:
 * This response is an API-boundary projection. Domain references keep only id and code, while this
 * response exposes display labels resolved from catalog data.
 */
@Schema(name = "ProductTypeResponse", description = "Product type catalog entry with trilingual labels.")
public record ProductTypeResponse(
        @Schema(description = "Product type catalog entry identifier.", example = "topology-pt-gas")
        String id,

        @Schema(description = "Stable language-neutral product type code.", example = "GAS")
        String code,

        @Schema(description = "Arabic product type name.", example = "غاز")
        String nameAr,

        @Schema(description = "French product type name.", example = "Gaz")
        String nameFr,

        @Schema(description = "English product type name.", example = "Gas")
        String nameEn,

        @Schema(description = "Arabic product type description.", example = "منتج غازي هيدروكربوني.")
        String descriptionAr,

        @Schema(description = "French product type description.", example = "Produit gazeux hydrocarbure.")
        String descriptionFr,

        @Schema(description = "English product type description.", example = "Hydrocarbon gas product.")
        String descriptionEn,

        @Schema(description = "Catalog lifecycle status.", example = "ACTIVE")
        String status,

        @Schema(description = "Display ordering value.", example = "10")
        int sortOrder,

        @Schema(description = "Whether this product type is system-defined.", example = "true")
        boolean systemDefined,

        @Schema(description = "Creation instant.", type = "string", format = "date-time")
        Instant createdAt,

        @Schema(description = "Last update instant.", type = "string", format = "date-time")
        Instant updatedAt) {
}
