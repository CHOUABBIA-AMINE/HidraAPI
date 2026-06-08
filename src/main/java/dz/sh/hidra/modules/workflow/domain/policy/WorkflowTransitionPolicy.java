/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTransitionPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.policy
 *
 * @Description : Domain policy for workflow transitions.
 *
 */
package dz.sh.hidra.modules.workflow.domain.policy;

import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTransition;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCommentText;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowReasonReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowStepId;

/**
 * Domain policy for workflow transitions.
 *
 * <p>Business role:
 * Verifies transition ownership, current step, decision, reason, and comment requirements.
 */
public final class WorkflowTransitionPolicy {

    /**
     * Ensures a transition belongs to a workflow definition.
     *
     * @param definition workflow definition
     * @param transition workflow transition
     */
    public void requireTransitionBelongsToDefinition(
            WorkflowDefinition definition,
            WorkflowTransition transition) {

        Objects.requireNonNull(definition, "Workflow definition must not be null.");
        Objects.requireNonNull(transition, "Workflow transition must not be null.");

        if (!definition.id().equals(transition.definitionId())) {
            throw new BusinessRuleViolationException("Workflow transition does not belong to the supplied definition.");
        }
    }

    /**
     * Ensures a transition can be applied from the current step for a decision.
     *
     * @param definition workflow definition
     * @param currentStepId current step identifier
     * @param decision requested decision
     * @param reason decision reason
     * @param comment decision comment
     * @return matching transition
     */
    public WorkflowTransition requireApplicableTransition(
            WorkflowDefinition definition,
            WorkflowStepId currentStepId,
            WorkflowDecision decision,
            WorkflowReasonReference reason,
            WorkflowCommentText comment) {

        Objects.requireNonNull(definition, "Workflow definition must not be null.");
        Objects.requireNonNull(currentStepId, "Current workflow step id must not be null.");
        Objects.requireNonNull(decision, "Workflow decision must not be null.");

        Optional<WorkflowTransition> transition = definition.transitions().stream()
                .filter(candidate -> currentStepId.equals(candidate.fromStepId()))
                .filter(candidate -> decision.equals(candidate.decision()))
                .findFirst();

        if (transition.isEmpty()) {
            throw new BusinessRuleViolationException("No workflow transition exists for the current step and decision.");
        }

        requireTransitionRequirementsSatisfied(transition.get(), reason, comment);

        return transition.get();
    }

    /**
     * Ensures a transition reason/comment requirements are satisfied.
     *
     * @param transition workflow transition
     * @param reason reason reference
     * @param comment comment value
     */
    public void requireTransitionRequirementsSatisfied(
            WorkflowTransition transition,
            WorkflowReasonReference reason,
            WorkflowCommentText comment) {

        Objects.requireNonNull(transition, "Workflow transition must not be null.");

        if (transition.reasonRequired() && reason == null) {
            throw new BusinessRuleViolationException("Workflow transition requires a decision reason.");
        }

        if (transition.commentRequired() && comment == null) {
            throw new BusinessRuleViolationException("Workflow transition requires a decision comment.");
        }
    }
}
