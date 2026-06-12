/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateMaintenanceWorkOrderRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.api.rest.request
 *
 * @Description : REST request to create maintenance work order.
 *
 */
package dz.sh.hidra.modules.assets.api.rest.request;

import java.time.Instant;

/**
 * REST request to create maintenance work order.
 */
public record CreateMaintenanceWorkOrderRequest(
        String workOrderNumber,
        String maintainableAssetId,
        String maintenancePlanId,
        String sourceRecommendationId,
        String workOrderTypeId,
        String priorityId,
        String title,
        String description,
        String assignedOrganizationUnitId,
        String assignedActorId,
        Instant plannedStartAt,
        Instant plannedEndAt,
        String workflowInstanceId,
        String createdByActorId
) {
}
