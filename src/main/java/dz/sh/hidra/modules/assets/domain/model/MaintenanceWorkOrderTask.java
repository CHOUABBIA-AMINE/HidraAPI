/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceWorkOrderTask
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Work-order task.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
import dz.sh.hidra.modules.assets.domain.value.*;
import java.time.Instant;

    /**
     * Work-order task.
     *
         * @param id id
     * @param workOrderId workOrderId
     * @param taskTemplateId taskTemplateId
     * @param taskNumber taskNumber
     * @param taskTypeId taskTypeId
     * @param description description
     * @param status status
     * @param sequenceNumber sequenceNumber
     * @param assignedActorId assignedActorId
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param resultSummary resultSummary
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record MaintenanceWorkOrderTask(
            String id,
        String workOrderId,
        String taskTemplateId,
        String taskNumber,
        String taskTypeId,
        String description,
        MaintenanceTaskStatus status,
        Integer sequenceNumber,
        String assignedActorId,
        Instant startedAt,
        Instant completedAt,
        String resultSummary,
        Instant createdAt,
        Instant updatedAt
    ) {

        public MaintenanceWorkOrderTask {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("MaintenanceWorkOrderTask id must not be blank.");
        }
        // HRA-051 required: workOrderId
        if (workOrderId == null || workOrderId.isBlank()) {
            throw new InvalidAssetsValueException("MaintenanceWorkOrderTask work order id must not be blank.");
        }
        // HRA-051 required: taskNumber
        if (taskNumber == null || taskNumber.isBlank()) {
            throw new InvalidAssetsValueException("MaintenanceWorkOrderTask task number must not be blank.");
        }
        // HRA-051 required: taskTypeId
        if (taskTypeId == null || taskTypeId.isBlank()) {
            throw new InvalidAssetsValueException("MaintenanceWorkOrderTask task type id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidAssetsValueException("MaintenanceWorkOrderTask status must not be null.");
        }
        // HRA-051 required: sequenceNumber
        if (sequenceNumber == null) {
            throw new InvalidAssetsValueException("MaintenanceWorkOrderTask sequence number must not be null.");
        }

        id = normalize(id);
        workOrderId = normalize(workOrderId);
        taskTemplateId = normalize(taskTemplateId);
        taskNumber = normalize(taskNumber);
        taskTypeId = normalize(taskTypeId);
        description = normalize(description);
        assignedActorId = normalize(assignedActorId);
        resultSummary = normalize(resultSummary);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
