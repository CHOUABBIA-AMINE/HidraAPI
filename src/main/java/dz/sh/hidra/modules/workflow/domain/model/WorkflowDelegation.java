/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDelegation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Delegation of a workflow task.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import dz.sh.hidra.modules.workflow.domain.value.*;
import java.time.Instant;

    /**
     * Delegation of a workflow task.
     *
         * @param id id
     * @param taskId taskId
     * @param fromActorId fromActorId
     * @param fromActorUsernameSnapshot fromActorUsernameSnapshot
     * @param fromActorDisplayNameSnapshot fromActorDisplayNameSnapshot
     * @param fromActorRoleCodeSnapshot fromActorRoleCodeSnapshot
     * @param toActorId toActorId
     * @param toActorUsernameSnapshot toActorUsernameSnapshot
     * @param toActorDisplayNameSnapshot toActorDisplayNameSnapshot
     * @param toActorRoleCodeSnapshot toActorRoleCodeSnapshot
     * @param toOrganizationUnitId toOrganizationUnitId
     * @param toOrganizationUnitNameSnapshot toOrganizationUnitNameSnapshot
     * @param toOrganizationRoleCodeSnapshot toOrganizationRoleCodeSnapshot
     * @param reasonId reasonId
     * @param delegationStatus delegationStatus
     * @param delegatedAt delegatedAt
     * @param acceptedAt acceptedAt
     * @param validUntil validUntil
     * @param delegationDepth delegationDepth
     */
    public record WorkflowDelegation(
            String id,
        String taskId,
        String fromActorId,
        String fromActorUsernameSnapshot,
        String fromActorDisplayNameSnapshot,
        String fromActorRoleCodeSnapshot,
        String toActorId,
        String toActorUsernameSnapshot,
        String toActorDisplayNameSnapshot,
        String toActorRoleCodeSnapshot,
        String toOrganizationUnitId,
        String toOrganizationUnitNameSnapshot,
        String toOrganizationRoleCodeSnapshot,
        String reasonId,
        WorkflowDelegationStatus delegationStatus,
        Instant delegatedAt,
        Instant acceptedAt,
        Instant validUntil,
        Integer delegationDepth
    ) {

        public WorkflowDelegation {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowDelegation id must not be blank.");
        }
        // HRA-051 required: taskId
        if (taskId == null || taskId.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowDelegation task id must not be blank.");
        }
        // HRA-051 required: fromActorId
        if (fromActorId == null || fromActorId.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowDelegation from actor id must not be blank.");
        }
        // HRA-051 required: reasonId
        if (reasonId == null || reasonId.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowDelegation reason id must not be blank.");
        }
        // HRA-051 required: delegationStatus
        if (delegationStatus == null) {
            throw new InvalidWorkflowValueException("WorkflowDelegation delegation status must not be null.");
        }
        // HRA-051 required: delegatedAt
        if (delegatedAt == null) {
            throw new InvalidWorkflowValueException("WorkflowDelegation delegated at must not be null.");
        }

        id = normalize(id);
        taskId = normalize(taskId);
        fromActorId = normalize(fromActorId);
        fromActorUsernameSnapshot = normalize(fromActorUsernameSnapshot);
        fromActorDisplayNameSnapshot = normalize(fromActorDisplayNameSnapshot);
        fromActorRoleCodeSnapshot = normalize(fromActorRoleCodeSnapshot);
        toActorId = normalize(toActorId);
        toActorUsernameSnapshot = normalize(toActorUsernameSnapshot);
        toActorDisplayNameSnapshot = normalize(toActorDisplayNameSnapshot);
        toActorRoleCodeSnapshot = normalize(toActorRoleCodeSnapshot);
        toOrganizationUnitId = normalize(toOrganizationUnitId);
        toOrganizationUnitNameSnapshot = normalize(toOrganizationUnitNameSnapshot);
        toOrganizationRoleCodeSnapshot = normalize(toOrganizationRoleCodeSnapshot);
        reasonId = normalize(reasonId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
