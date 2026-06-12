/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowEscalationRule
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Escalation rule configured for a workflow step.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import java.time.Instant;

    /**
     * Escalation rule configured for a workflow step.
     *
         * @param id id
     * @param definitionId definitionId
     * @param stepId stepId
     * @param afterDurationSeconds afterDurationSeconds
     * @param escalateToActorId escalateToActorId
     * @param escalateToActorUsernameSnapshot escalateToActorUsernameSnapshot
     * @param escalateToActorDisplayNameSnapshot escalateToActorDisplayNameSnapshot
     * @param escalateToActorRoleCodeSnapshot escalateToActorRoleCodeSnapshot
     * @param escalateToOrganizationUnitId escalateToOrganizationUnitId
     * @param escalateToOrganizationUnitNameSnapshot escalateToOrganizationUnitNameSnapshot
     * @param escalateToOrganizationRoleCodeSnapshot escalateToOrganizationRoleCodeSnapshot
     * @param escalationReasonId escalationReasonId
     * @param repeatable repeatable
     * @param maxRepeatCount maxRepeatCount
     * @param escalationLevel escalationLevel
     * @param businessHoursCalendarId businessHoursCalendarId
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record WorkflowEscalationRule(
            String id,
        String definitionId,
        String stepId,
        int afterDurationSeconds,
        String escalateToActorId,
        String escalateToActorUsernameSnapshot,
        String escalateToActorDisplayNameSnapshot,
        String escalateToActorRoleCodeSnapshot,
        String escalateToOrganizationUnitId,
        String escalateToOrganizationUnitNameSnapshot,
        String escalateToOrganizationRoleCodeSnapshot,
        String escalationReasonId,
        boolean repeatable,
        Integer maxRepeatCount,
        Integer escalationLevel,
        String businessHoursCalendarId,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public WorkflowEscalationRule {
        id = normalize(id);
        definitionId = normalize(definitionId);
        stepId = normalize(stepId);
        escalateToActorId = normalize(escalateToActorId);
        escalateToActorUsernameSnapshot = normalize(escalateToActorUsernameSnapshot);
        escalateToActorDisplayNameSnapshot = normalize(escalateToActorDisplayNameSnapshot);
        escalateToActorRoleCodeSnapshot = normalize(escalateToActorRoleCodeSnapshot);
        escalateToOrganizationUnitId = normalize(escalateToOrganizationUnitId);
        escalateToOrganizationUnitNameSnapshot = normalize(escalateToOrganizationUnitNameSnapshot);
        escalateToOrganizationRoleCodeSnapshot = normalize(escalateToOrganizationRoleCodeSnapshot);
        escalationReasonId = normalize(escalationReasonId);
        businessHoursCalendarId = normalize(businessHoursCalendarId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
