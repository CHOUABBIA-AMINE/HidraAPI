/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateOrganizationUnitRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.request
 *
 * @Description : REST request to create organization unit.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.request;

import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

/**
 * REST request to create organization unit.
 */
public record CreateOrganizationUnitRequest(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String unitTypeId,
        String parentUnitId,
        OrganizationUnitStatus status,
        @NotNull Instant validFrom
) {
}
