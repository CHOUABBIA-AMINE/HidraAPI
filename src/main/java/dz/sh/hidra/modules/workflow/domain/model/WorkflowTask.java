/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTask
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Actionable workflow task.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import dz.sh.hidra.modules.workflow.domain.value.*;
import java.time.Instant;

    /**
     * Actionable workflow task.
     *
         * @param id id
     * @param instanceId instanceId
     * @param stepId stepId
     * @param status status
     * @param assignedActorId assignedActorId
     * @param assignedActorUsernameSnapshot assignedActorUsernameSnapshot
     * @param assignedActorDisplayNameSnapshot assignedActorDisplayNameSnapshot
     * @param assignedOrganizationUnitId assignedOrganizationUnitId
     * @param assignedOrganizationUnitNameSnapshot assignedOrganizationUnitNameSnapshot
     * @param assignedRoleCodeSnapshot assignedRoleCodeSnapshot
     * @param priorityId priorityId
     * @param dueAt dueAt
     * @param claimedByActorId claimedByActorId
     * @param claimedAt claimedAt
     * @param completedByActorId completedByActorId
     * @param completedAt completedAt
     * @param assignmentModeId assignmentModeId
     * @param taskLabelSnapshot taskLabelSnapshot
     * @param slaStatus slaStatus
     * @param escalatedAt escalatedAt
     * @param delegatedAt delegatedAt
     * @param expiresAt expiresAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record WorkflowTask(
            String id,
        String instanceId,
        String stepId,
        WorkflowTaskStatus status,
        String assignedActorId,
        String assignedActorUsernameSnapshot,
        String assignedActorDisplayNameSnapshot,
        String assignedOrganizationUnitId,
        String assignedOrganizationUnitNameSnapshot,
        String assignedRoleCodeSnapshot,
        String priorityId,
        Instant dueAt,
        String claimedByActorId,
        Instant claimedAt,
        String completedByActorId,
        Instant completedAt,
        String assignmentModeId,
        String taskLabelSnapshot,
        WorkflowSlaStatus slaStatus,
        Instant escalatedAt,
        Instant delegatedAt,
        Instant expiresAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public WorkflowTask {
        id = normalize(id);
        instanceId = normalize(instanceId);
        stepId = normalize(stepId);
        assignedActorId = normalize(assignedActorId);
        assignedActorUsernameSnapshot = normalize(assignedActorUsernameSnapshot);
        assignedActorDisplayNameSnapshot = normalize(assignedActorDisplayNameSnapshot);
        assignedOrganizationUnitId = normalize(assignedOrganizationUnitId);
        assignedOrganizationUnitNameSnapshot = normalize(assignedOrganizationUnitNameSnapshot);
        assignedRoleCodeSnapshot = normalize(assignedRoleCodeSnapshot);
        priorityId = normalize(priorityId);
        claimedByActorId = normalize(claimedByActorId);
        completedByActorId = normalize(completedByActorId);
        assignmentModeId = normalize(assignmentModeId);
        taskLabelSnapshot = normalize(taskLabelSnapshot);
        }
        public boolean openTask() {
            return status == WorkflowTaskStatus.OPEN
                    || status == WorkflowTaskStatus.CLAIMED
                    || status == WorkflowTaskStatus.IN_REVIEW;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
