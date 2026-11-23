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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RevisionApprovalEvidenceContract {
    record Request(String targetModule, String targetDigest, String definitionId, int definitionVersion,
            String targetTypeId, String purposeId, String instanceId, String taskId, String actionId,
            Instant evaluationAt) {}
    record Evidence(String instanceId, String taskId, String actionId, String definitionId,
            int definitionVersion, String targetTypeId, String purposeId, String actorId,
            String actorDisplayName, Instant actedAt) {}
    record BaselineBinding(String locator, String submitterId, String correlationId, Instant submittedAt) {}
    record PressureRequest(UUID candidateId, String instanceId, String taskId, String actionId,
                           String submitterId, Instant submittedAt, Instant evaluationAt) {}
    record PressureCandidate(UUID candidateId, String submitterId, Instant submittedAt, Instant evaluationAt) {}
    Optional<Evidence> resolve(Request request);
    default boolean pressureReviewTask(PressureCandidate candidate, String taskId, String actorId) { return false; }
    default List<Evidence> discoverPressureApprovals(PressureCandidate candidate) { return List.of(); }
    default Optional<Evidence> resolvePressure(PressureRequest request) { return Optional.empty(); }
    default Optional<Evidence> resolveBaseline(Request request, BaselineBinding binding) { return Optional.empty(); }
}
