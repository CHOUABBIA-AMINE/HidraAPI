/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowEquipmentParameterApprovalEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.integration
 *
 * @Description : Maintains immutable governed equipment source evidence.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.integration;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationEquipmentParameterApprovalEvidencePort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision.GovernanceBinding;
import dz.sh.hidra.modules.workflow.application.contract.target.RevisionApprovalEvidenceContract;
import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class WorkflowEquipmentParameterApprovalEvidenceAdapter implements SimulationEquipmentParameterApprovalEvidencePort {
    private final RevisionApprovalEvidenceContract workflow;
    public WorkflowEquipmentParameterApprovalEvidenceAdapter(RevisionApprovalEvidenceContract workflow) { this.workflow = workflow; }
    @Override
    public Optional<Evidence> resolve(String digest, GovernanceBinding b, String instance, String task, String action, Instant at) {
        return workflow.resolve(new RevisionApprovalEvidenceContract.Request("simulation", digest, b.definitionId(),
                b.definitionVersion(), b.targetTypeId(), b.purposeId(), instance, task, action, at))
                .map(a -> new Evidence(a.instanceId(), a.taskId(), a.actionId(), a.definitionId(), a.definitionVersion(),
                        a.targetTypeId(), a.purposeId(), a.actorId(), a.actorDisplayName(), a.actedAt()));
    }
}
