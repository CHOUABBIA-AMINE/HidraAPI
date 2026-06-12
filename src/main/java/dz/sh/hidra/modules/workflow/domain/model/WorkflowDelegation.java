/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDelegation
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
