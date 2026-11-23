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
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class PressureRestrictionDecisionResponses {
    private PressureRestrictionDecisionResponses() { }

    @Schema(name = "PressureRestrictionStreamVersion")
    public record Stream(String companyPartyId, String pipelineId, String sectionId,
                         String conditionCode, long expectedVersion) { }

    @Schema(name = "PressureRestrictionReviewProposal")
    public record ReviewProposal(UUID requestId, UUID candidateId, String kind, String companyPartyId,
            String pipelineId, String sectionId, String conditionCode, java.math.BigDecimal upperPressure,
            String pressureUnit, String pressureBasis, Instant effectiveFrom, Instant effectiveUntil,
            UUID predecessorId, UUID successorId, UUID baselineReceiptId, String baselineDigest,
            String engineeringSourceRevision) {
        public static ReviewProposal from(PressureRestrictionDecisionUseCase.ReviewProposal view) {
            var item = view.candidate();
            var scope = item.scope();
            return new ReviewProposal(view.requestId(), item.id(), item.kind().name(), scope.companyPartyId(),
                    scope.pipelineId(), scope.sectionId(), scope.conditionCode(), item.upperPressure(),
                    item.pressureUnit(), item.pressureBasis() == null ? null : item.pressureBasis().name(),
                    item.effectiveFrom(), item.effectiveUntil(), item.retiredRevisionId(), item.successorRevisionId(),
                    item.baselineReceiptId(), item.baselineDigest(), item.engineeringSourceRevision());
        }
    }

    @Schema(name = "PressureRestrictionPublicationDecisionReference")
    public record PublicationDecisionReference(UUID candidateId, String instanceId, String taskId, String actionId) { }

    @Schema(name = "PressureRestrictionPublicationInputs")
    public record PublicationInputs(UUID requestId, long expectedVersion,
            List<PublicationDecisionReference> decisions, Publication publication) {
        public static PublicationInputs from(PressureRestrictionDecisionUseCase.PublicationInputs view) {
            return new PublicationInputs(view.requestId(), view.expectedVersion(),
                    view.decisions().stream().map(ref -> new PublicationDecisionReference(
                            ref.candidateId(), ref.instanceId(), ref.taskId(), ref.actionId())).toList(),
                    view.publication() == null ? null : Publication.from(view.publication()));
        }
    }

    @Schema(name = "PressureRestrictionCandidate")
    public record Candidate(UUID candidateId, String kind, String workflowLocator) { }

    @Schema(name = "PressureRestrictionRequest")
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

    @Schema(name = "PressureRestrictionPublicationReceipt")
    public record Publication(UUID requestId, long streamVersion, UUID revisionId, UUID terminationId, Instant recordedAt) {
        public static Publication from(PressureRestrictionDecisionUseCase.Published source) {
            return new Publication(source.requestId(), source.streamVersion(), source.revisionId(),
                    source.terminationId(), source.recordedAt());
        }
    }

    @Schema(name = "PressureRestrictionDecisionWitness")
    public record Decision(UUID candidateId, String instanceId, String taskId, String actionId,
                           String reviewerId, Instant decidedAt, String auditEventId) { }

    @Schema(name = "PressureRestrictionDecisionEvidence")
    public record Evidence(Publication publication, List<Decision> decisions) {
        public static Evidence from(PressureRestrictionDecisionUseCase.EvidenceView source) {
            return new Evidence(Publication.from(source.publication()), source.decisions().stream()
                    .map(decision -> new Decision(decision.candidateId(), decision.instanceId(), decision.taskId(),
                            decision.actionId(), decision.reviewerId(), decision.decidedAt(), decision.auditEventId()))
                    .toList());
        }
    }
}
