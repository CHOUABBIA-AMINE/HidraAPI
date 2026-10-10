/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyGasFluidWorkflowTargetLookup
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.integration
 *
 * @Description : Exports only verified persisted gas revision digests as Workflow subjects.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.integration;

import dz.sh.hidra.modules.custody.application.port.out.CustodyGasFluidRevisionRepositoryPort;
import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CustodyGasFluidWorkflowTargetLookup implements WorkflowOwnedTargetLookup {
    private final CustodyGasFluidRevisionRepositoryPort revisions;
    public CustodyGasFluidWorkflowTargetLookup(CustodyGasFluidRevisionRepositoryPort revisions) { this.revisions = revisions; }
    @Override public String module() { return "custody"; }
    @Override public Set<String> targetTypeCodes() { return Set.of("GAS_FLUID_REVISION"); }
    @Override @Transactional(readOnly = true)
    public Optional<Target> eligibleTarget(String type, String id) {
        if (!targetTypeCodes().contains(type) || id == null || !id.matches("[0-9a-f]{64}")) return Optional.empty();
        return revisions.findByApprovalTargetId(id).map(s -> new Target(s.sha256(), s.revision().revisionId(),
                "Gas fluid " + s.revision().sourceId() + " / " + s.revision().revisionId()));
    }
}
