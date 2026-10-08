/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentApprovalContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.contract.risk
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.workflow.application.contract.risk;

import java.time.Instant;
public interface RiskAssessmentApprovalContract {
    record Request(String assessmentId, String instanceId, String taskId, String transitionId,
            String reviewActionId, Instant expectedTaskUpdatedAt, String reasonId,
            String decisionNote, String commentText, String correlationId) {}
    record Approval(String instanceId, String taskId, String actionId, String reviewActionId,
            String reviewerId, String reviewerDisplayName, String approverId,
            String approverUsername, String approverDisplayName, Instant approvedAt) {}
    Approval approve(Request request);
}
