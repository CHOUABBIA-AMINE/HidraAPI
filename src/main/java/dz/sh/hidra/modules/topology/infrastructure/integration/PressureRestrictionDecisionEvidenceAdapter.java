/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PressureRestrictionDecisionEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.integration
 *
 * @Description : Connects exact pressure restriction decisions to Identity, Workflow and Audit owner contracts.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.integration;

import dz.sh.hidra.modules.audit.application.contract.topology.PressureRestrictionAuditEvidenceContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyPressureRestrictionAuthorizationContract;
import dz.sh.hidra.modules.topology.application.port.in.PressureRestrictionDecisionUseCase.DecisionReference;
import dz.sh.hidra.modules.topology.application.port.out.PressureRestrictionDecisionEvidencePort;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.Scope;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureWorkflowCandidate;
import dz.sh.hidra.modules.workflow.application.contract.target.RevisionApprovalEvidenceContract;
import java.time.Instant;
import java.util.Optional;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public class PressureRestrictionDecisionEvidenceAdapter implements PressureRestrictionDecisionEvidencePort {
    private final TopologyPressureRestrictionAuthorizationContract identity;
    private final ObjectProvider<RevisionApprovalEvidenceContract> workflow;
    private final ObjectProvider<PressureRestrictionAuditEvidenceContract> audit;

    public PressureRestrictionDecisionEvidenceAdapter(TopologyPressureRestrictionAuthorizationContract identity,
            ObjectProvider<RevisionApprovalEvidenceContract> workflow,
            ObjectProvider<PressureRestrictionAuditEvidenceContract> audit) {
        this.identity = identity;
        this.workflow = workflow;
        this.audit = audit;
    }

    @Override
    public String requireCurrent(Scope scope, Operation operation) {
        return identity.current(target(scope), TopologyPressureRestrictionAuthorizationContract.Operation.valueOf(operation.name()))
                .orElseThrow(() -> new AccessDeniedException("Restriction authority unavailable"));
    }

    @Override
    public boolean reviewerEligible(Scope scope, String reviewerId) {
        return identity.reviewerEligible(target(scope), reviewerId);
    }

    @Override
    public Optional<Approval> finalApproval(QualifiedPressureWorkflowCandidate candidate, DecisionReference reference) {
        return workflow.getObject().resolvePressure(new RevisionApprovalEvidenceContract.PressureRequest(
                candidate.id(), reference.instanceId(), reference.taskId(), reference.actionId(),
                candidate.submittedBy(), candidate.createdAt(), Instant.now()))
                .map(evidence -> new Approval(candidate.id(), candidate.workflowLocator(), evidence.instanceId(),
                        evidence.taskId(), evidence.actionId(), candidate.submittedBy(), evidence.actorId(), evidence.actedAt()));
    }

    @Override
    public String appendAudit(QualifiedPressureWorkflowCandidate candidate, Approval approval, Instant recordedAt) {
        return audit.getObject().appendPressureWitness(witness(candidate, approval));
    }

    @Override
    public boolean auditMatches(QualifiedPressureWorkflowCandidate candidate, Approval approval, String eventId) {
        return audit.getObject().matchesPressureWitness(eventId, witness(candidate, approval));
    }

    private static TopologyPressureRestrictionAuthorizationContract.Target target(Scope scope) {
        return new TopologyPressureRestrictionAuthorizationContract.Target(scope.companyPartyId(),
                scope.pipelineId(), scope.sectionId());
    }

    private static PressureRestrictionAuditEvidenceContract.Witness witness(QualifiedPressureWorkflowCandidate candidate,
                                                                              Approval approval) {
        return new PressureRestrictionAuditEvidenceContract.Witness(
                PressureRestrictionAuditEvidenceContract.Kind.valueOf(candidate.kind().name()), candidate.id(),
                approval.reviewerId(), approval.instanceId(), approval.taskId(), approval.actionId(), approval.actedAt());
    }
}
