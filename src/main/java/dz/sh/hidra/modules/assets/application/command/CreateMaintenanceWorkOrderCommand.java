/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateMaintenanceWorkOrderCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.command
 *
 * @Description : Command to create maintenance work order.
 *
 */
package dz.sh.hidra.modules.assets.application.command;

import java.time.Instant;

/**
 * Command to create maintenance work order.
 */
public record CreateMaintenanceWorkOrderCommand(
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
