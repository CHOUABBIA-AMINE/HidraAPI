/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyGasFluidApprovalEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 *
 * @Description : Maintains exact immutable revision evidence.
 *
 */
package dz.sh.hidra.modules.custody.application.port.out;

import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevision.GovernanceBinding;
import java.time.Instant;
import java.util.Optional;

public interface CustodyGasFluidApprovalEvidencePort {
    record Evidence(String instanceId, String taskId, String actionId, String definitionId, int definitionVersion,
            String targetTypeId, String purposeId, String actorId, String actorDisplayName, Instant actedAt) {}
    Optional<Evidence> resolve(String digest, GovernanceBinding binding, String instanceId, String taskId,
            String actionId, Instant evaluationAt);
}
