/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : RiskAssessmentWorkflowAuthorityContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.contract.workflow
 * @Description : Risk-owned admission for protected Workflow target operations and finalization.
 */
package dz.sh.hidra.modules.risk.application.contract.workflow;

import java.util.function.Supplier;
import java.time.Instant;
public interface RiskAssessmentWorkflowAuthorityContract {
    record ReviewEvidence(String taskId,Instant actedAt) { }
    void captureReview(String assessmentId,String taskId,Instant actedAt);
    ReviewEvidence reviewEvidence(String assessmentId);
    boolean governed(String assessmentId);
    void requireStart(String assessmentId);
    void requireProtectedRead(String assessmentId);
    void requireReview(String assessmentId,String actorId);
    boolean finalizationArmed(String assessmentId);
    <T> T withFinalization(String assessmentId,Supplier<T> action);
}
