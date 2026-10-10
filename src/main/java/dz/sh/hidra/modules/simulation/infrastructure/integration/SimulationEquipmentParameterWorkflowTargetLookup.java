/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEquipmentParameterWorkflowTargetLookup
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

import dz.sh.hidra.modules.simulation.application.port.out.SimulationEquipmentParameterRevisionRepositoryPort;
import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SimulationEquipmentParameterWorkflowTargetLookup implements WorkflowOwnedTargetLookup {
    private final SimulationEquipmentParameterRevisionRepositoryPort revisions;
    public SimulationEquipmentParameterWorkflowTargetLookup(SimulationEquipmentParameterRevisionRepositoryPort revisions) { this.revisions = revisions; }
    @Override public String module() { return "simulation"; }
    @Override public Set<String> targetTypeCodes() { return Set.of("EQUIPMENT_PARAMETER_REVISION"); }
    @Override @Transactional(readOnly = true)
    public Optional<Target> eligibleTarget(String type, String id) {
        if (!targetTypeCodes().contains(type) || id == null || !id.matches("[0-9a-f]{64}")) return Optional.empty();
        return revisions.findByApprovalTargetId(id).map(s -> new Target(s.sha256(), s.revision().revisionId(),
                "Equipment parameters " + s.revision().sourceId() + " / " + s.revision().revisionId()));
    }
}
