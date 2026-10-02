/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowStateHistory
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Append-only workflow state transition history.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import java.time.Instant;

    /**
     * Append-only workflow state transition history.
     *
         * @param id id
     * @param instanceId instanceId
     * @param taskId taskId
     * @param fromStepId fromStepId
     * @param toStepId toStepId
     * @param fromStatus fromStatus
     * @param toStatus toStatus
     * @param actorId actorId
     * @param actorUsernameSnapshot actorUsernameSnapshot
     * @param actorDisplayNameSnapshot actorDisplayNameSnapshot
     * @param actorRoleCodeSnapshot actorRoleCodeSnapshot
     * @param actionId actionId
     * @param reasonId reasonId
     * @param correlationId correlationId
     * @param changedAt changedAt
     */
    public record WorkflowStateHistory(
            String id,
        String instanceId,
        String taskId,
        String fromStepId,
        String toStepId,
        String fromStatus,
        String toStatus,
        String actorId,
        String actorUsernameSnapshot,
        String actorDisplayNameSnapshot,
        String actorRoleCodeSnapshot,
        String actionId,
        String reasonId,
        String correlationId,
        Instant changedAt
    ) {

        public WorkflowStateHistory {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowStateHistory id must not be blank.");
        }
        // HRA-051 required: instanceId
        if (instanceId == null || instanceId.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowStateHistory instance id must not be blank.");
        }
        // HRA-051 required: actorId
        if (actorId == null || actorId.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowStateHistory actor id must not be blank.");
        }
        // HRA-051 required: changedAt
        if (changedAt == null) {
            throw new InvalidWorkflowValueException("WorkflowStateHistory changed at must not be null.");
        }

        id = normalize(id);
        instanceId = normalize(instanceId);
        taskId = normalize(taskId);
        fromStepId = normalize(fromStepId);
        toStepId = normalize(toStepId);
        fromStatus = normalize(fromStatus);
        toStatus = normalize(toStatus);
        actorId = normalize(actorId);
        actorUsernameSnapshot = normalize(actorUsernameSnapshot);
        actorDisplayNameSnapshot = normalize(actorDisplayNameSnapshot);
        actorRoleCodeSnapshot = normalize(actorRoleCodeSnapshot);
        actionId = normalize(actionId);
        reasonId = normalize(reasonId);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
