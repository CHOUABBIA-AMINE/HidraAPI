/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : GovernedRiskAssessmentPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.out
 * @Description : Risk-owned immutable governance ledger persistence port.
 */
package dz.sh.hidra.modules.risk.application.port.out;

import dz.sh.hidra.modules.risk.domain.model.GovernedRiskAssessment;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
public interface GovernedRiskAssessmentPort {
    record Scope(String assessmentId,String companyPartyId,String facilityId) { }
    record Submission(String assessmentId,String cellId,String matrixId,String matrixVersion,
                      BigDecimal score,String ratingId,String alarmId,String alarmCode,
                      Instant alarmRaisedAt,String digestFormat,String digest,Instant submittedAt) { }
    record Receipt(String assessmentId,String instanceId,String reviewTaskId,String reviewActionId,
                   String reviewerId,Instant reviewedAt,String approvalTaskId,String approvalActionId,
                   String approverId,Instant approvedAt,String auditReceiptId,String digest) { }
    Optional<Scope> scope(String assessmentId);
    void enroll(Scope scope);
    void freeze(Submission submission);
    Optional<Submission> submission(String assessmentId);
    void finalizeApproved(Receipt receipt);
    Optional<GovernedRiskAssessment> projection(String assessmentId);
}
