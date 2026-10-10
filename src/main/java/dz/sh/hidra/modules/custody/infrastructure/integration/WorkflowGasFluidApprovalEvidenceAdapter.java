/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowGasFluidApprovalEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.integration
 *
 * @Description : Consumes the exported Workflow contract through a Custody-local evidence port.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.integration;

import dz.sh.hidra.modules.custody.application.port.out.CustodyGasFluidApprovalEvidencePort;
import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevision.GovernanceBinding;
import dz.sh.hidra.modules.workflow.application.contract.target.RevisionApprovalEvidenceContract;
import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class WorkflowGasFluidApprovalEvidenceAdapter implements CustodyGasFluidApprovalEvidencePort {
    private final RevisionApprovalEvidenceContract workflow;
    public WorkflowGasFluidApprovalEvidenceAdapter(RevisionApprovalEvidenceContract workflow) { this.workflow = workflow; }
    @Override
    public Optional<Evidence> resolve(String digest, GovernanceBinding b, String instance, String task, String action, Instant at) {
        return workflow.resolve(new RevisionApprovalEvidenceContract.Request("custody", digest, b.definitionId(),
                b.definitionVersion(), b.targetTypeId(), b.purposeId(), instance, task, action, at))
                .map(a -> new Evidence(a.instanceId(), a.taskId(), a.actionId(), a.definitionId(), a.definitionVersion(),
                        a.targetTypeId(), a.purposeId(), a.actorId(), a.actorDisplayName(), a.actedAt()));
    }
}
