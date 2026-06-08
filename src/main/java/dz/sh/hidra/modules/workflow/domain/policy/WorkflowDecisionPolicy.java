/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDecisionPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.policy
 *
 * @Description : Domain policy for workflow decisions.
 *
 */
package dz.sh.hidra.modules.workflow.domain.policy;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCommentText;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowReasonReference;

/**
 * Domain policy for workflow task decisions.
 *
 * <p>Business role:
 * Verifies whether task decisions are allowed and whether required reason/comment information is
 * present.
 */
public final class WorkflowDecisionPolicy {

    /**
     * Ensures a task can receive a decision.
     *
     * @param task workflow task
     * @param decision workflow decision
     * @param reason decision reason
     * @param comment decision comment
     */
    public void requireDecisionCanBeApplied(
            WorkflowTask task,
            WorkflowDecision decision,
            WorkflowReasonReference reason,
            WorkflowCommentText comment) {

        Objects.requireNonNull(task, "Workflow task must not be null.");
        Objects.requireNonNull(decision, "Workflow decision must not be null.");

        if (task.status().isClosed()) {
            throw new BusinessRuleViolationException("Closed workflow task cannot receive a decision.");
        }

        if (decision.requiresReason() && reason == null) {
            throw new BusinessRuleViolationException("Workflow decision requires a reason.");
        }

        if (WorkflowDecision.REQUEST_CORRECTION.equals(decision) && comment == null) {
            throw new BusinessRuleViolationException("Requesting correction requires a comment.");
        }

        if (WorkflowDecision.COMMENT.equals(decision) && comment == null) {
            throw new BusinessRuleViolationException("Workflow comment decision requires comment text.");
        }
    }

    /**
     * Ensures approval can be applied.
     *
     * @param task workflow task
     */
    public void requireApprovalAllowed(WorkflowTask task) {
        requireDecisionCanBeApplied(task, WorkflowDecision.APPROVE, null, null);
    }

    /**
     * Ensures rejection can be applied.
     *
     * @param task workflow task
     * @param reason rejection reason
     */
    public void requireRejectionAllowed(
            WorkflowTask task,
            WorkflowReasonReference reason) {

        requireDecisionCanBeApplied(task, WorkflowDecision.REJECT, reason, null);
    }
}
