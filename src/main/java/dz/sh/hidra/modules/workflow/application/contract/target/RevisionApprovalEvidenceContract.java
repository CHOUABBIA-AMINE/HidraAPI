/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RevisionApprovalEvidenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.contract.target
 *
 * @Description : Exports actual final Workflow approval evidence for an exact revision digest.
 *
 */
package dz.sh.hidra.modules.workflow.application.contract.target;

import java.time.Instant;
import java.util.Optional;

public interface RevisionApprovalEvidenceContract {
    record Request(String targetModule, String targetDigest, String definitionId, int definitionVersion,
            String targetTypeId, String purposeId, String instanceId, String taskId, String actionId,
            Instant evaluationAt) {}
    record Evidence(String instanceId, String taskId, String actionId, String definitionId,
            int definitionVersion, String targetTypeId, String purposeId, String actorId,
            String actorDisplayName, Instant actedAt) {}
    Optional<Evidence> resolve(Request request);
}
