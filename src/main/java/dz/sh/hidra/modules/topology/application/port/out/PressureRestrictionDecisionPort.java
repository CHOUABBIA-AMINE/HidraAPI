/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PressureRestrictionDecisionPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Defines Topology-owned durable restriction requests and serialized publication operations.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.application.port.in.PressureRestrictionDecisionUseCase.Published;
import dz.sh.hidra.modules.topology.application.port.in.PressureRestrictionDecisionUseCase.Target;
import dz.sh.hidra.modules.topology.application.port.out.PressureRestrictionDecisionEvidencePort.Approval;
import dz.sh.hidra.modules.topology.domain.model.PressureRestrictionDecisionRequest;
import dz.sh.hidra.modules.topology.domain.model.PressureRestrictionDecisionRequest.Source;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.Scope;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureWorkflowCandidate;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PressureRestrictionDecisionPort {
    record StoredRequest(PressureRestrictionDecisionRequest request, Source source, String submitterId,
                         Instant submittedAt, List<QualifiedPressureWorkflowCandidate> candidates) {
        public StoredRequest {
            candidates = List.copyOf(candidates);
        }
    }

    record BoundDecision(Approval approval, String auditEventId) { }

    record StoredPublication(Published receipt, List<BoundDecision> decisions) {
        public StoredPublication {
            decisions = List.copyOf(decisions);
        }
    }

    /** Request locator discovery is internal. Callers must authorize before disclosure. */
    Optional<StoredRequest> request(Target target);
    Optional<StoredRequest> requestByCandidate(UUID candidateId);

    /** Keep the canonical target and exact baseline source bound until transaction completion. */
    boolean lockSource(Scope scope, Source source);

    /** Flush writes from all participants in the shared transaction before the final guard. */
    void flush();

    /** Single scalar check executed during the protected executor's final guard phase. */
    boolean guard(StoredRequest request, long streamVersion);

    long lockStream(Scope scope, String pipelineSystemId);

    /** Persist the idempotent request and all candidates with the stream locked. */
    StoredRequest submit(StoredRequest request);

    Optional<StoredPublication> publication(UUID requestId);

    /** Enforce exact lineage, source and one-use decisions and append all owner rows atomically. */
    StoredPublication publish(StoredRequest request, long expectedVersion, Instant recordedAt,
                              List<BoundDecision> decisions);
}
