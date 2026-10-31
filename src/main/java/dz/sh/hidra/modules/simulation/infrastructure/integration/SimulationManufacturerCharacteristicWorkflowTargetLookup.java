/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationManufacturerCharacteristicWorkflowTargetLookup
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.integration
 *
 * @Description : Manufacturer-target resolution through protected Simulation owner reads.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.integration;

import dz.sh.hidra.modules.simulation.application.service.ManufacturerCharacteristicRevisionApplicationService;
import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;

/** Failure is non-disclosing, including denial, missing authority or unavailable native provider. */
@Component
public final class SimulationManufacturerCharacteristicWorkflowTargetLookup implements WorkflowOwnedTargetLookup {
    public static final String TARGET_TYPE="MANUFACTURER_CHARACTERISTIC_REVISION";
    private final ManufacturerCharacteristicRevisionApplicationService owner;
    public SimulationManufacturerCharacteristicWorkflowTargetLookup(ManufacturerCharacteristicRevisionApplicationService owner) {
        this.owner = Objects.requireNonNull(owner);
    }
    @Override public String module() { return "simulation"; }
    @Override public Set<String> targetTypeCodes() { return Set.of(TARGET_TYPE); }
    @Override public Optional<Target> eligibleTarget(String type,String id) {
        if (!TARGET_TYPE.equals(type) || id == null || !id.matches("[0-9a-f]{64}")) return Optional.empty();
        return owner.resolveWorkflowTarget(id, false).map(v ->
                new Target(v.workflowTargetDigest(), v.revisionId(), "Manufacturer characteristic " + v.revisionId()));
    }
    @Override public boolean protectedReadAllowed(String type,String id,ProtectedRead operation) {
        if (!TARGET_TYPE.equals(type) || operation == null || id == null || !id.matches("[0-9a-f]{64}"))
            return false;
        return owner.resolveWorkflowTarget(id, operation == ProtectedRead.HISTORY).isPresent();
    }
}
