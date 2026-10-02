/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalPlanResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.response
 *
 * @Description : REST response for operational plan.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.response;

import dz.sh.hidra.modules.planning.domain.value.OperationalPlanStatus;

/**
 * REST response for operational plan.
 */
public record OperationalPlanResponse(
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
