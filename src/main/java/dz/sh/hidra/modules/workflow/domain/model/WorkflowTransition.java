/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTransition
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Allowed transition between workflow steps.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import dz.sh.hidra.modules.workflow.domain.value.*;
import java.time.Instant;

    /**
     * Allowed transition between workflow steps.
     *
         * @param id id
     * @param definitionId definitionId
     * @param fromStepId fromStepId
     * @param toStepId toStepId
     * @param decision decision
     * @param reasonRequired reasonRequired
     * @param commentRequired commentRequired
     * @param conditionExpression conditionExpression
     * @param requiredPermissionCode requiredPermissionCode
     * @param targetModuleCallback targetModuleCallback
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record WorkflowTransition(
            String id,
        String definitionId,
        String fromStepId,
        String toStepId,
        WorkflowDecision decision,
        boolean reasonRequired,
        boolean commentRequired,
        String conditionExpression,
        String requiredPermissionCode,
        String targetModuleCallback,
        Instant createdAt,
        Instant updatedAt
    ) {

        public WorkflowTransition {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowTransition id must not be blank.");
        }
        // HRA-051 required: definitionId
        if (definitionId == null || definitionId.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowTransition definition id must not be blank.");
        }
        // HRA-051 required: fromStepId
        if (fromStepId == null || fromStepId.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowTransition from step id must not be blank.");
        }
        // HRA-051 required: toStepId
        if (toStepId == null || toStepId.isBlank()) {
            throw new InvalidWorkflowValueException("WorkflowTransition to step id must not be blank.");
        }
        // HRA-051 required: decision
        if (decision == null) {
            throw new InvalidWorkflowValueException("WorkflowTransition decision must not be null.");
        }

        id = normalize(id);
        definitionId = normalize(definitionId);
        fromStepId = normalize(fromStepId);
        toStepId = normalize(toStepId);
        conditionExpression = normalize(conditionExpression);
        requiredPermissionCode = normalize(requiredPermissionCode);
        targetModuleCallback = normalize(targetModuleCallback);
        if(fromStepId.equals(toStepId))
            throw new InvalidWorkflowValueException("Workflow transition requires distinct source and destination steps.");
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
