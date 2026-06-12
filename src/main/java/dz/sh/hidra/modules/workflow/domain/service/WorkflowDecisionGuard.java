/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDecisionGuard
 * @CreatedOn   : 2026-06-11
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
