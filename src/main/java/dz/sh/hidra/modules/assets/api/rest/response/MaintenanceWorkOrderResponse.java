/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceWorkOrderResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.api.rest.response
 *
 * @Description : REST response for maintenance work order.
 *
 */
package dz.sh.hidra.modules.assets.api.rest.response;

import dz.sh.hidra.modules.assets.domain.value.MaintenanceWorkOrderStatus;

import java.time.Instant;

/**
 * REST response for maintenance work order.
 */
public record MaintenanceWorkOrderResponse(
        String id,
        String workOrderNumber,
        String maintainableAssetId,
        String sourceRecommendationId,
        String workOrderTypeId,
        MaintenanceWorkOrderStatus status,
        String title,
        Instant plannedStartAt,
        Instant completedAt
) {
}
