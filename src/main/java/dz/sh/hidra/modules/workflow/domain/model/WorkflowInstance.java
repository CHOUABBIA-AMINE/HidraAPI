/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstance
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Running workflow instance over a neutral target.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import dz.sh.hidra.modules.workflow.domain.value.*;
import java.time.Instant;

    /**
     * Running workflow instance over a neutral target.
     *
         * @param id id
     * @param definitionId definitionId
     * @param definitionVersion definitionVersion
     * @param workflowPurposeId workflowPurposeId
     * @param targetModule targetModule
     * @param targetTypeId targetTypeId
     * @param targetId targetId
     * @param targetCodeSnapshot targetCodeSnapshot
     * @param targetLabelSnapshot targetLabelSnapshot
     * @param status status
     * @param currentStepId currentStepId
     * @param startedByActorId startedByActorId
     * @param startedByUsernameSnapshot startedByUsernameSnapshot
     * @param startedByDisplayNameSnapshot startedByDisplayNameSnapshot
     * @param startedByRoleCodeSnapshot startedByRoleCodeSnapshot
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param cancelledAt cancelledAt
     * @param correlationId correlationId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record WorkflowInstance(
            String id,
        String definitionId,
        int definitionVersion,
        String workflowPurposeId,
        String targetModule,
        String targetTypeId,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot,
        WorkflowInstanceStatus status,
        String currentStepId,
        String startedByActorId,
        String startedByUsernameSnapshot,
        String startedByDisplayNameSnapshot,
        String startedByRoleCodeSnapshot,
        Instant startedAt,
        Instant completedAt,
        Instant cancelledAt,
        String correlationId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public WorkflowInstance {
        id = normalize(id);
        definitionId = normalize(definitionId);
        workflowPurposeId = normalize(workflowPurposeId);
        targetModule = normalize(targetModule);
        targetTypeId = normalize(targetTypeId);
        targetId = normalize(targetId);
        targetCodeSnapshot = normalize(targetCodeSnapshot);
        targetLabelSnapshot = normalize(targetLabelSnapshot);
        currentStepId = normalize(currentStepId);
        startedByActorId = normalize(startedByActorId);
        startedByUsernameSnapshot = normalize(startedByUsernameSnapshot);
        startedByDisplayNameSnapshot = normalize(startedByDisplayNameSnapshot);
        startedByRoleCodeSnapshot = normalize(startedByRoleCodeSnapshot);
        correlationId = normalize(correlationId);
        }
        public boolean nonTerminal() {
            return status == WorkflowInstanceStatus.DRAFT
                    || status == WorkflowInstanceStatus.STARTED
                    || status == WorkflowInstanceStatus.IN_PROGRESS
                    || status == WorkflowInstanceStatus.WAITING;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
