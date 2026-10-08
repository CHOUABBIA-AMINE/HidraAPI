/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentAuditContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.contract.risk
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.audit.application.contract.risk;

import java.time.Instant;
public interface RiskAssessmentAuditContract {
    record ApprovalEvidence(String assessmentId, String assessmentNumber, String actorId,
            String actorUsername, String actorDisplayName, String reviewerId,
            String workflowInstanceId, String workflowTaskId, String workflowActionId,
            String reviewActionId, Instant occurredAt) {}
    String appendApproved(ApprovalEvidence evidence);
}
