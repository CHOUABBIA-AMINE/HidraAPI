/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceWorkOrderTask
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
