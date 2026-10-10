/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEquipmentParameterApprovalEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.out
 *
 * @Description : Maintains immutable governed equipment source evidence.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.out;

import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision.GovernanceBinding;
import java.time.Instant;
import java.util.Optional;

public interface SimulationEquipmentParameterApprovalEvidencePort {
    record Evidence(String instanceId, String taskId, String actionId, String definitionId, int definitionVersion,
            String targetTypeId, String purposeId, String actorId, String actorDisplayName, Instant actedAt) {}
    Optional<Evidence> resolve(String digest, GovernanceBinding binding, String instanceId, String taskId,
            String actionId, Instant evaluationAt);
}
