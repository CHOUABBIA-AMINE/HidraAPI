/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitTypeResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.response
 *
 * @Description : Localized REST response for an organization unit type catalog reference.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Localized REST response for an organization unit type catalog reference.
 *
 * @param id organization unit type catalog identifier
 * @param code stable language-neutral type code
 * @param label localized label
 * @param locale resolved locale
 */
@Schema(name = "OrganizationUnitTypeResponse", description = "Localized organization unit type reference.")
public record OrganizationUnitTypeResponse(
        @Schema(description = "Organization unit type catalog identifier.", example = "organization-out-station")
        String id,
        @Schema(description = "Stable language-neutral organization unit type code.", example = "STATION")
        String code,
        @Schema(description = "Localized organization unit type label.", example = "Station")
        String label,
        @Schema(description = "Resolved locale.", example = "en")
        String locale) {
}
