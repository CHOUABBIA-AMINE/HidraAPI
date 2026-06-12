/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePlanningPeriodRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.request
 *
 * @Description : REST request to create planning period.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.request;

import java.time.Instant;

/**
 * REST request to create planning period.
 */
public record CreatePlanningPeriodRequest(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String periodTypeId,
        Instant periodStart,
        Instant periodEnd,
        String timeZone,
        String createdByActorId
) {
}
