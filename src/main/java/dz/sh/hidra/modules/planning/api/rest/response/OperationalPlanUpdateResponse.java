/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalPlanUpdateResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.response
 *
 * @Description : REST response for a concurrency-protected operational-plan metadata update.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.response;

import java.time.Instant;

/**
 * Returns the persisted mutable metadata and refreshed concurrency token.
 */
public record OperationalPlanUpdateResponse(
        String id,
        String nameAr,
        String nameFr,
        String nameEn,
        String responsibleOrganizationUnitId,
        Instant updatedAt
) {
}
