/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalPlanSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.dto
 *
 * @Description : Operational plan summary DTO.
 *
 */
package dz.sh.hidra.modules.planning.application.dto;

import dz.sh.hidra.modules.planning.domain.value.OperationalPlanStatus;

/**
 * Operational plan summary DTO.
 */
public record OperationalPlanSummaryDto(
        String id,
        String periodId,
        String code,
        String nameFr,
        String topologyScopeType,
        String topologyScopeId,
        OperationalPlanStatus status,
        String approvedRevisionId
) {
}
