package dz.sh.hidra.modules.topology.infrastructure.integration;

import dz.sh.hidra.modules.topology.application.port.out.InstalledCharacteristicLinkApprovalEvidencePort;
import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLinkApproval;
import dz.sh.hidra.modules.topology.domain.service.InstalledCharacteristicLinkApprovalTargetDigester;
import dz.sh.hidra.modules.workflow.application.contract.target.RevisionApprovalEvidenceContract;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Owner-neutral port adapter: native Topology authorization must be acquired by
 * the S3 coordinator BEFORE invoking this on the same protected physical transaction.
 * Neither a caller digest nor this returned immutable value grants publication.
 */
@Component
public class WorkflowInstalledCharacteristicLinkApprovalEvidenceAdapter
        implements InstalledCharacteristicLinkApprovalEvidencePort {
    private final RevisionApprovalEvidenceContract workflow;
    public WorkflowInstalledCharacteristicLinkApprovalEvidenceAdapter(RevisionApprovalEvidenceContract workflow) {
        this.workflow=Objects.requireNonNull(workflow);
    }
    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public Optional<InstalledCharacteristicLinkApproval.WorkflowEvidence> resolve(Request r) {
        if(r==null||r.target()==null||r.evaluatedAt()==null
                ||!TransactionSynchronizationManager.isActualTransactionActive()
                ||TransactionSynchronizationManager.isCurrentTransactionReadOnly())
            return Optional.empty();
        var proof=workflow.resolve(new RevisionApprovalEvidenceContract.Request(
                "topology",InstalledCharacteristicLinkApprovalTargetDigester.digest(r.target()),
                r.definitionId(),r.definitionVersion(),r.targetTypeId(),r.purposeId(),
                r.instanceId(),r.taskId(),r.actionId(),r.evaluatedAt()));
        return proof.map(x->new InstalledCharacteristicLinkApproval.WorkflowEvidence(
                x.definitionId(),x.definitionVersion(),x.targetTypeId(),
                InstalledCharacteristicLinkApproval.TARGET_TYPE_CODE,
                x.purposeId(),InstalledCharacteristicLinkApproval.PURPOSE_CODE,
                x.instanceId(),x.taskId(),x.actionId(),x.actorId(),x.actedAt()));
    }
}
