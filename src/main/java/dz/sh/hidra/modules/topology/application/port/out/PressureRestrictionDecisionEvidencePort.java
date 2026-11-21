/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PressureRestrictionDecisionEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Defines exact authority, Workflow decision, and Audit evidence requested by Topology.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.application.port.in.PressureRestrictionDecisionUseCase.DecisionReference;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.Scope;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureWorkflowCandidate;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface PressureRestrictionDecisionEvidencePort {
    enum Operation { SUBMIT, REVIEW, PUBLISH, EVIDENCE_READ }

    record Approval(UUID candidateId, String locator, String instanceId, String taskId, String actionId,
                    String submitterId, String reviewerId, Instant actedAt) { }

    String requireCurrent(Scope scope, Operation operation);

    /** Revalidate an already recorded reviewer's current eligibility in the target scope. */
    boolean reviewerEligible(Scope scope, String reviewerId);

    Optional<Approval> finalApproval(QualifiedPressureWorkflowCandidate candidate, DecisionReference reference);

    /** Append with the same transaction and physical owner publication connection. */
    String appendAudit(QualifiedPressureWorkflowCandidate candidate, Approval approval, Instant recordedAt);

    /** Recheck the exact bound Audit receipt for an already authorized evidence read. */
    boolean auditMatches(QualifiedPressureWorkflowCandidate candidate, Approval approval, String auditEventId);
}
