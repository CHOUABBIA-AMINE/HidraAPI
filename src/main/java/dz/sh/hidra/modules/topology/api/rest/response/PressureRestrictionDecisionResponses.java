/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PressureRestrictionDecisionResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : Maps restriction decisions and authorized evidence to explicit API responses.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import dz.sh.hidra.modules.topology.application.port.in.PressureRestrictionDecisionUseCase;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class PressureRestrictionDecisionResponses {
    private PressureRestrictionDecisionResponses() { }

    public record Candidate(UUID candidateId, String kind, String workflowLocator) { }

    public record Request(UUID requestId, long expectedVersion, Instant submittedAt,
                          List<Candidate> candidates, Publication publication) {
        public static Request from(PressureRestrictionDecisionUseCase.RequestView source) {
            return new Request(source.requestId(), source.expectedVersion(), source.submittedAt(),
                    source.candidates().stream()
                            .map(candidate -> new Candidate(candidate.id(), candidate.kind().name(), candidate.workflowLocator()))
                            .toList(),
                    source.published() == null ? null : Publication.from(source.published()));
        }
    }

    public record Publication(UUID requestId, long streamVersion, UUID revisionId, UUID terminationId, Instant recordedAt) {
        public static Publication from(PressureRestrictionDecisionUseCase.Published source) {
            return new Publication(source.requestId(), source.streamVersion(), source.revisionId(),
                    source.terminationId(), source.recordedAt());
        }
    }

    public record Decision(UUID candidateId, String instanceId, String taskId, String actionId,
                           String reviewerId, Instant decidedAt, String auditEventId) { }

    public record Evidence(Publication publication, List<Decision> decisions) {
        public static Evidence from(PressureRestrictionDecisionUseCase.EvidenceView source) {
            return new Evidence(Publication.from(source.publication()), source.decisions().stream()
                    .map(decision -> new Decision(decision.candidateId(), decision.instanceId(), decision.taskId(),
                            decision.actionId(), decision.reviewerId(), decision.decidedAt(), decision.auditEventId()))
                    .toList());
        }
    }
}
