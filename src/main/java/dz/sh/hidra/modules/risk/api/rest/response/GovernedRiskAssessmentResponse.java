/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : GovernedRiskAssessmentResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Record
 * @Layer       : API
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.api.rest.response
 * @Description : Unique typed REST representation of scoped Risk results and immutable decisions.
 */
package dz.sh.hidra.modules.risk.api.rest.response;

import dz.sh.hidra.modules.risk.domain.model.GovernedRiskAssessment;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
@Schema(name="T22GovernedRiskAssessmentResult")
public record GovernedRiskAssessmentResponse(
        String assessmentId,String companyPartyId,String facilityId,String status,
        BigDecimal inherentScore,String inherentRatingId,String inherentCellId,
        String inherentMatrixId,String inherentMatrixVersion,
        String alarmId,String alarmCodeSnapshot,Instant alarmRaisedAt,
        String submissionFormat,String submissionDigest,Instant submittedAt,
        List<DecisionHistory> approvedDecisionHistory) {
    @Schema(name="T22RiskApprovalDecisionHistory")
    public record DecisionHistory(String workflowInstanceId,String reviewTaskId,
            String reviewActionId,String reviewerId,Instant reviewedAt,
            String approvalTaskId,String approvalActionId,String approverId,
            Instant approvedAt,String auditReceiptId,String submissionDigest) {
        public static DecisionHistory from(GovernedRiskAssessment.Decision d) {
            return new DecisionHistory(d.workflowInstanceId(),d.reviewTaskId(),
                    d.reviewActionId(),d.reviewerId(),d.reviewedAt(),d.approvalTaskId(),
                    d.approvalActionId(),d.approverId(),d.approvedAt(),d.auditReceiptId(),
                    d.submissionDigest());
        }
    }
    public static GovernedRiskAssessmentResponse from(GovernedRiskAssessment a) {
        return new GovernedRiskAssessmentResponse(a.assessmentId(),a.companyPartyId(),
                a.facilityId(),a.status(),a.inherentScore(),a.inherentRatingId(),
                a.inherentCellId(),a.inherentMatrixId(),a.inherentMatrixVersion(),
                a.alarmId(),a.alarmCodeSnapshot(),a.alarmRaisedAt(),
                a.submissionFormat(),a.submissionDigest(),a.submittedAt(),
                a.decisions().stream().map(DecisionHistory::from).toList());
    }
}
