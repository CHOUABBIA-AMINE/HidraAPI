/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.response
 *
 * @Description : REST response for organization unit.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.response;

import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;

/**
 * REST response for organization unit.
 */
public record OrganizationUnitResponse(
        String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String unitTypeId,
        String parentUnitId,
        OrganizationUnitStatus status
) {
}
