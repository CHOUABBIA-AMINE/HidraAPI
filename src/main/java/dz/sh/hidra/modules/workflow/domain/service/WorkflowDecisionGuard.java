/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDecisionGuard
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.service
 *
 * @Description : Guards workflow decision requirements.
 *
 */
package dz.sh.hidra.modules.workflow.domain.service;

import dz.sh.hidra.modules.workflow.domain.exception.WorkflowBoundaryViolationException;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;

/**
 * Guards workflow decision requirements.
 */
public class WorkflowDecisionGuard {

    /**
     * One structural predicate for executable configured transitions and advisory previews.
     * A delegation row or allowDelegation flag is not an authority grant.
     */
    public boolean supportedConfiguredTransition(WorkflowDecision decision, String transitionDefinitionId,
            String fromStepId, String toStepId, String condition, String callback,
            String instanceDefinitionId, String currentStepId,
            String sourceStepDefinitionId, String targetStepDefinitionId) {
        return decision != null && decision != WorkflowDecision.COMMENT
                && decision != WorkflowDecision.DELEGATE
                && instanceDefinitionId != null
                && instanceDefinitionId.equals(transitionDefinitionId)
                && instanceDefinitionId.equals(sourceStepDefinitionId)
                && instanceDefinitionId.equals(targetStepDefinitionId)
                && currentStepId != null && currentStepId.equals(fromStepId)
                && toStepId != null && !toStepId.equals(fromStepId)
                && (condition == null || condition.isBlank())
                && (callback == null || callback.isBlank());
    }

    public void ensureSupportedConfiguredTransition(WorkflowDecision decision, String transitionDefinitionId,
            String fromStepId, String toStepId, String condition, String callback,
            String instanceDefinitionId, String currentStepId,
            String sourceStepDefinitionId, String targetStepDefinitionId) {
        if (!supportedConfiguredTransition(decision, transitionDefinitionId, fromStepId, toStepId,
                condition, callback, instanceDefinitionId, currentStepId,
                sourceStepDefinitionId, targetStepDefinitionId)) {
            throw new WorkflowBoundaryViolationException("Configured Workflow transition is not executable.");
        }
    }

    public void ensureReasonAndCommentRules(WorkflowDecision decision, String reasonId, String commentText) {
        if (decision == null) {
            throw new WorkflowBoundaryViolationException("Workflow decision must not be null.");
        }
        boolean reasonRequired = decision == WorkflowDecision.REJECT
                || decision == WorkflowDecision.REQUEST_CORRECTION
                || decision == WorkflowDecision.RETURN
                || decision == WorkflowDecision.DELEGATE
                || decision == WorkflowDecision.ESCALATE
                || decision == WorkflowDecision.CANCEL;
        if (reasonRequired && (reasonId == null || reasonId.isBlank())) {
            throw new WorkflowBoundaryViolationException("Workflow decision requires a reason.");
        }
        if (decision == WorkflowDecision.REQUEST_CORRECTION && (commentText == null || commentText.isBlank())) {
            throw new WorkflowBoundaryViolationException("Correction request requires a comment.");
        }
    }
}
