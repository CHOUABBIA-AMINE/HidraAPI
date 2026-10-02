/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowStepAssignmentRule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Reusable assignment policy for a workflow step.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import java.time.Instant;

    /**
     * Reusable assignment policy for a workflow step.
     *
         * @param id id
     * @param definitionId definitionId
     * @param stepId stepId
     * @param assignmentModeId assignmentModeId
     * @param actorId actorId
     * @param roleCode roleCode
     * @param organizationUnitId organizationUnitId
     * @param organizationRoleCode organizationRoleCode
     * @param targetOwnerMode targetOwnerMode
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record WorkflowStepAssignmentRule(
            String id,
        String definitionId,
        String stepId,
        String assignmentModeId,
        String actorId,
        String roleCode,
        String organizationUnitId,
        String organizationRoleCode,
        String targetOwnerMode,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public WorkflowStepAssignmentRule {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowStepAssignmentRule id must not be blank.");
        }
        // HRA-051 required: definitionId
        if (definitionId == null || definitionId.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowStepAssignmentRule definition id must not be blank.");
        }
        // HRA-051 required: stepId
        if (stepId == null || stepId.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowStepAssignmentRule step id must not be blank.");
        }
        // HRA-051 required: assignmentModeId
        if (assignmentModeId == null || assignmentModeId.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowStepAssignmentRule assignment mode id must not be blank.");
        }

        id = normalize(id);
        definitionId = normalize(definitionId);
        stepId = normalize(stepId);
        assignmentModeId = normalize(assignmentModeId);
        actorId = normalize(actorId);
        roleCode = normalize(roleCode);
        organizationUnitId = normalize(organizationUnitId);
        organizationRoleCode = normalize(organizationRoleCode);
        targetOwnerMode = normalize(targetOwnerMode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
