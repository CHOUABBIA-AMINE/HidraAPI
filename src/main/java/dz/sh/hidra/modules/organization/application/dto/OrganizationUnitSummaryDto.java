/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.dto
 *
 * @Description : Organization unit summary DTO.
 *
 */
package dz.sh.hidra.modules.organization.application.dto;

import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;

/**
 * Organization unit summary DTO.
 */
public record OrganizationUnitSummaryDto(
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
