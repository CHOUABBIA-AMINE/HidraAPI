/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ManufacturerCharacteristicApprovalEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.out
 *
 * @Description : Workflow-only authoritative terminal approval evidence query.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.out;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.List;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;

/** An approval is documentary evidence; this port does not confer current source authority. */
public interface ManufacturerCharacteristicApprovalEvidencePort {
    record Request(String targetDigest, String definitionId, int definitionVersion,
                   String targetTypeId, String purposeId, String instanceId,
                   String taskId, String actionId, Instant evaluatedAt) {
        public Request {
            digest(targetDigest);
            exact(definitionId); exact(targetTypeId); exact(purposeId);
            exact(instanceId); exact(taskId); exact(actionId);
            if (definitionVersion < 1 || evaluatedAt == null)
                throw new IllegalArgumentException("Exact Workflow approval version and evaluation time required");
        }
    }
    record Evidence(String targetDigest, String instanceId, String taskId, String actionId,
                    String actorId, String actorDisplayName, Instant approvedAt,
                    List<ProtectedRelationFence> workflowFences) {
        public Evidence {
            digest(targetDigest); exact(instanceId); exact(taskId); exact(actionId);
            exact(actorId); Objects.requireNonNull(actorDisplayName); Objects.requireNonNull(approvedAt);
            if (actorDisplayName.isBlank()) throw new IllegalArgumentException("Nonempty approved actor required");
            workflowFences = List.copyOf(workflowFences);
            if (workflowFences.isEmpty()) throw new IllegalArgumentException("Workflow evidence fences required");
        }
    }
    Optional<Evidence> resolve(Request request);
    private static void digest(String s) {
        if (s == null || !s.matches("[0-9a-f]{64}")) throw new IllegalArgumentException("Exact Workflow target SHA-256 required");
    }
    private static void exact(String s) {
        if (s == null || s.isBlank() || !s.equals(s.strip()) || s.codePointCount(0,s.length()) > 80)
            throw new IllegalArgumentException("Exact Workflow identity required");
    }
}
