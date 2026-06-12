/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowAssignment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Explicit assignment for a workflow task.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import dz.sh.hidra.modules.workflow.domain.value.*;
import java.time.Instant;

    /**
     * Explicit assignment for a workflow task.
     *
         * @param id id
     * @param taskId taskId
     * @param actorId actorId
     * @param actorUsernameSnapshot actorUsernameSnapshot
     * @param actorDisplayNameSnapshot actorDisplayNameSnapshot
     * @param roleCodeSnapshot roleCodeSnapshot
     * @param organizationUnitId organizationUnitId
     * @param organizationUnitNameSnapshot organizationUnitNameSnapshot
     * @param status status
     * @param assignedAt assignedAt
     * @param updatedAt updatedAt
     */
    public record WorkflowAssignment(
            String id,
        String taskId,
        String actorId,
        String actorUsernameSnapshot,
        String actorDisplayNameSnapshot,
        String roleCodeSnapshot,
        String organizationUnitId,
        String organizationUnitNameSnapshot,
        WorkflowAssignmentStatus status,
        Instant assignedAt,
        Instant updatedAt
    ) {

        public WorkflowAssignment {
        id = normalize(id);
        taskId = normalize(taskId);
        actorId = normalize(actorId);
        actorUsernameSnapshot = normalize(actorUsernameSnapshot);
        actorDisplayNameSnapshot = normalize(actorDisplayNameSnapshot);
        roleCodeSnapshot = normalize(roleCodeSnapshot);
        organizationUnitId = normalize(organizationUnitId);
        organizationUnitNameSnapshot = normalize(organizationUnitNameSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
