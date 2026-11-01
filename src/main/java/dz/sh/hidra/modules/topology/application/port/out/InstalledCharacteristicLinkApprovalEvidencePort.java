package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLink;
import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLinkApproval.WorkflowEvidence;
import java.time.Instant;
import java.util.Optional;

/** Outbound Workflow-owner decision evidence. Does not authorize the native Topology approval. */
public interface InstalledCharacteristicLinkApprovalEvidencePort {
    record Request(InstalledCharacteristicLink target,String definitionId,int definitionVersion,
            String targetTypeId,String purposeId,String instanceId,String taskId,String actionId,Instant evaluatedAt) {}
    Optional<WorkflowEvidence> resolve(Request request);
}
