/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceWorkOrder
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Maintenance work order.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
import dz.sh.hidra.modules.assets.domain.value.*;
import java.time.Instant;

    /**
     * Maintenance work order.
     *
         * @param id id
     * @param workOrderNumber workOrderNumber
     * @param maintainableAssetId maintainableAssetId
     * @param maintenancePlanId maintenancePlanId
     * @param sourceRecommendationId sourceRecommendationId
     * @param workOrderTypeId workOrderTypeId
     * @param priorityId priorityId
     * @param status status
     * @param title title
     * @param description description
     * @param assignedOrganizationUnitId assignedOrganizationUnitId
     * @param assignedActorId assignedActorId
     * @param plannedStartAt plannedStartAt
     * @param plannedEndAt plannedEndAt
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param workflowInstanceId workflowInstanceId
     * @param createdByActorId createdByActorId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record MaintenanceWorkOrder(
            String id,
        String workOrderNumber,
        String maintainableAssetId,
        String maintenancePlanId,
        String sourceRecommendationId,
        String workOrderTypeId,
        String priorityId,
        MaintenanceWorkOrderStatus status,
        String title,
        String description,
        String assignedOrganizationUnitId,
        String assignedActorId,
        Instant plannedStartAt,
        Instant plannedEndAt,
        Instant startedAt,
        Instant completedAt,
        String workflowInstanceId,
        String createdByActorId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public MaintenanceWorkOrder {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("MaintenanceWorkOrder id must not be blank.");
        }
        // HRA-051 required: workOrderNumber
        if (workOrderNumber == null || workOrderNumber.isBlank()) {
            throw new InvalidAssetsValueException("MaintenanceWorkOrder work order number must not be blank.");
        }
        // HRA-051 required: maintainableAssetId
        if (maintainableAssetId == null || maintainableAssetId.isBlank()) {
            throw new InvalidAssetsValueException("MaintenanceWorkOrder maintainable asset id must not be blank.");
        }
        // HRA-051 required: workOrderTypeId
        if (workOrderTypeId == null || workOrderTypeId.isBlank()) {
            throw new InvalidAssetsValueException("MaintenanceWorkOrder work order type id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidAssetsValueException("MaintenanceWorkOrder status must not be null.");
        }

        id = normalize(id);
        workOrderNumber = normalize(workOrderNumber);
        maintainableAssetId = normalize(maintainableAssetId);
        maintenancePlanId = normalize(maintenancePlanId);
        sourceRecommendationId = normalize(sourceRecommendationId);
        workOrderTypeId = normalize(workOrderTypeId);
        priorityId = normalize(priorityId);
        title = normalize(title);
        description = normalize(description);
        assignedOrganizationUnitId = normalize(assignedOrganizationUnitId);
        assignedActorId = normalize(assignedActorId);
        workflowInstanceId = normalize(workflowInstanceId);
        createdByActorId = normalize(createdByActorId);
        }
        public boolean openLifecycle() {
            return status != MaintenanceWorkOrderStatus.CLOSED
                    && status != MaintenanceWorkOrderStatus.CANCELLED
                    && status != MaintenanceWorkOrderStatus.REJECTED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
