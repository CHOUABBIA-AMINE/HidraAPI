/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningPeriodResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.response
 *
 * @Description : REST response for planning period.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.response;

import dz.sh.hidra.modules.planning.domain.value.PlanningPeriodStatus;

import java.time.Instant;

/**
 * REST response for planning period.
 */
public record PlanningPeriodResponse(
        String id,
        String code,
        String nameFr,
        Instant periodStart,
        Instant periodEnd,
        String timeZone,
        PlanningPeriodStatus status
) {
}
