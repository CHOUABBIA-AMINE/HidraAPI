/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : GovernedRiskAssessment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 * @Description : Immutable governed Risk projection with source-frozen submission and approved decision evidence.
 */
package dz.sh.hidra.modules.risk.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public record GovernedRiskAssessment(
        String assessmentId, String companyPartyId, String facilityId, String status,
        String assessorId, BigDecimal inherentScore, String inherentRatingId,
        String inherentCellId, String inherentMatrixId, String inherentMatrixVersion,
        String alarmId, String alarmCodeSnapshot, Instant alarmRaisedAt,
        String submissionFormat, String submissionDigest, Instant submittedAt,
        List<Decision> decisions) {
    public GovernedRiskAssessment {
        Objects.requireNonNull(assessmentId);
        Objects.requireNonNull(companyPartyId);
        Objects.requireNonNull(facilityId);
        decisions = decisions == null ? List.of() : List.copyOf(decisions);
    }
    public record Decision(String workflowInstanceId, String reviewTaskId,
            String reviewActionId, String reviewerId, Instant reviewedAt,
            String approvalTaskId, String approvalActionId, String approverId,
            Instant approvedAt, String auditReceiptId, String submissionDigest) {
        public Decision {
            Objects.requireNonNull(workflowInstanceId);
            Objects.requireNonNull(reviewActionId);
            Objects.requireNonNull(approvalActionId);
            Objects.requireNonNull(auditReceiptId);
            Objects.requireNonNull(submissionDigest);
        }
    }
}
