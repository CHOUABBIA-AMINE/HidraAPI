/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitTypeResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.response
 *
 * @Description : REST response exposing an organization unit type catalog entry with trilingual labels.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.response;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response exposing an organization unit type catalog entry with trilingual labels.
 *
 * <p>Business role:
 * Gives clients stable organization unit type reference data while treating Arabic, French, and
 * English labels as first-class API fields.
 *
 * <p>Architecture role:
 * This response is an API-boundary projection. Domain references keep only id and code, while this
 * response exposes display labels resolved from catalog data.
 */
@Schema(name = "OrganizationUnitTypeResponse", description = "Organization unit type catalog entry with trilingual labels.")
public record OrganizationUnitTypeResponse(
        @Schema(description = "Organization unit type catalog entry identifier.", example = "organization-out-station")
        String id,

        @Schema(description = "Stable language-neutral organization unit type code.", example = "STATION")
        String code,

        @Schema(description = "Arabic organization unit type name.", example = "محطة")
        String nameAr,

        @Schema(description = "French organization unit type name.", example = "Station")
        String nameFr,

        @Schema(description = "English organization unit type name.", example = "Station")
        String nameEn,

        @Schema(description = "Arabic organization unit type description.", example = "محطة كوحدة تنظيمية للأفراد والمسؤوليات.")
        String descriptionAr,

        @Schema(description = "French organization unit type description.", example = "Station comme unité organisationnelle pour les personnes et responsabilités.")
        String descriptionFr,

        @Schema(description = "English organization unit type description.", example = "Station as an organization unit for people and responsibility.")
        String descriptionEn,

        @Schema(description = "Catalog lifecycle status.", example = "ACTIVE")
        String status,

        @Schema(description = "Display ordering value.", example = "80")
        int sortOrder,

        @Schema(description = "Whether this organization unit type is system-defined.", example = "true")
        boolean systemDefined,

        @Schema(description = "Creation instant.", type = "string", format = "date-time")
        Instant createdAt,

        @Schema(description = "Last update instant.", type = "string", format = "date-time")
        Instant updatedAt) {

    public String label() {
        if (nameEn != null && !nameEn.isBlank()) {
            return nameEn;
        }
        if (nameFr != null && !nameFr.isBlank()) {
            return nameFr;
        }
        if (nameAr != null && !nameAr.isBlank()) {
            return nameAr;
        }
        return code;
    }

    public String locale() {
        return "en";
    }
}
