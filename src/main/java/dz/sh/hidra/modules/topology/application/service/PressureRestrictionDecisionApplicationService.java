/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PressureRestrictionDecisionApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Coordinates protected pressure restriction submission, publication, and evidence reads.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.model.EngineeringBaselineDraftFailure;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselineReadUseCase;
import dz.sh.hidra.modules.topology.application.port.in.PipelineDossierReadUseCase;
import dz.sh.hidra.modules.topology.application.port.in.PressureRestrictionDecisionUseCase;
import dz.sh.hidra.modules.topology.application.port.in.QualifiedPressureWorkflowCandidateReadUseCase;
import dz.sh.hidra.modules.topology.application.port.out.PressureRestrictionDecisionEvidencePort;
import dz.sh.hidra.modules.topology.application.port.out.PressureRestrictionDecisionEvidencePort.Approval;
import dz.sh.hidra.modules.topology.application.port.out.PressureRestrictionDecisionEvidencePort.Operation;
import dz.sh.hidra.modules.topology.application.port.out.PressureRestrictionDecisionPort;
import dz.sh.hidra.modules.topology.application.port.out.PressureRestrictionDecisionPort.BoundDecision;
import dz.sh.hidra.modules.topology.application.port.out.PressureRestrictionDecisionPort.StoredRequest;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest;
import dz.sh.hidra.modules.topology.domain.model.PressureRestrictionDecisionRequest;
import dz.sh.hidra.modules.topology.domain.model.PressureRestrictionDecisionRequest.Source;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.Scope;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureWorkflowCandidate;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public final class PressureRestrictionDecisionApplicationService implements PressureRestrictionDecisionUseCase {
    private final PressureRestrictionDecisionPort store;
    private final PressureRestrictionDecisionEvidencePort authority;
    private final PipelineDossierReadUseCase dossier;
    private final EngineeringBaselineReadUseCase baselines;
    private final ProtectedTransactionExecutor executor;
    private final QualifiedPressureWorkflowCandidateReadUseCase candidateReads;

    public PressureRestrictionDecisionApplicationService(PressureRestrictionDecisionPort store,
            PressureRestrictionDecisionEvidencePort authority, PipelineDossierReadUseCase dossier,
            EngineeringBaselineReadUseCase baselines, ProtectedTransactionExecutor executor,
            QualifiedPressureWorkflowCandidateReadUseCase candidateReads) {
        this.store = store;
        this.authority = authority;
        this.dossier = dossier;
        this.baselines = baselines;
        this.executor = executor;
        this.candidateReads = candidateReads;
    }

    @Override
    public ReviewProposal reviewProposal(UUID candidateId, String locator, String taskId) {
        var request = store.requestByCandidate(candidateId).orElseThrow(this::unavailable);
        var candidate = request.candidates().stream()
                .filter(item -> item.id().equals(candidateId) && item.workflowLocator().equals(locator))
                .findFirst().orElseThrow(this::unavailable);
        String actor = require(candidate.scope(), Operation.REVIEW);
        if (!candidateReads.permitsReview(candidateId, actor)
                || !authority.assignedReviewTask(candidate, taskId, actor)
                || !actor.equals(require(candidate.scope(), Operation.REVIEW))) throw unavailable();
        return new ReviewProposal(request.request().requestId(), candidate);
    }

    @Override
    public PublicationInputs publicationInputs(Target target) {
        var request = authorizedRequest(target, Operation.PUBLISH);
        var scope = request.request().scope();
        String actor = require(scope, Operation.PUBLISH);
        long version = request.request().expectedVersion();
        var published = store.publication(target.requestId()).map(PressureRestrictionDecisionPort.StoredPublication::receipt);
        var decisions = new ArrayList<DecisionReference>(request.candidates().size());
        if (published.isEmpty()) {
            if (!request.source().equals(source(scope, request.source().receiptId()))) throw unavailable();
            if (store.currentStreamVersion(scope, request.source().pipelineSystemId()).orElseThrow(this::unavailable)
                    != version) throw new Conflict();
            for (var candidate : request.candidates()) {
                var approval = authority.discoverApprovals(candidate).stream()
                        .filter(item -> exact(candidate, reference(item), item)
                                && authority.reviewerEligible(scope, item.reviewerId()))
                        .findFirst();
                if (approval.isEmpty()) {
                    decisions.clear();
                    break;
                }
                decisions.add(reference(approval.get()));
            }
        }
        if (!actor.equals(require(scope, Operation.PUBLISH))) throw unavailable();
        return new PublicationInputs(target.requestId(), version, decisions, published.orElse(null));
    }

    @Override
    public long currentStreamVersion(Scope scope) {
        String actor = require(scope, Operation.SUBMIT);
        String system = targetSystem(scope.companyPartyId(), scope.pipelineId(), scope.sectionId());
        long version = store.currentStreamVersion(scope, system).orElseThrow(this::unavailable);
        String currentSystem = targetSystem(scope.companyPartyId(), scope.pipelineId(), scope.sectionId());
        if (!system.equals(currentSystem) || !actor.equals(require(scope, Operation.SUBMIT))) throw unavailable();
        return version;
    }

    @Override
    public RequestView submit(PressureRestrictionDecisionRequest request) {
        Scope scope = request.scope();
        String actor = require(scope, Operation.SUBMIT);
        Source source = source(scope, request.baselineReceiptId());
        Instant submittedAt = now();
        var proposal = new StoredRequest(request, source, actor, submittedAt,
                request.candidates(source, actor, submittedAt));
        var guard = new AtomicReference<Guard>();
        Target target = new Target(scope.companyPartyId(), scope.pipelineId(), scope.sectionId(), request.requestId());
        return protectedWrite(() -> {
            long version = lock(scope, source, actor, Operation.SUBMIT);
            var previous = store.request(target).orElse(null);
            if (previous != null) {
                if (!previous.request().equals(request) || !previous.source().equals(source)
                        || !actor.equals(previous.submitterId())) throw new Conflict();
                guard.set(new Guard(previous, version));
                return view(previous);
            }
            if (version != request.expectedVersion()) throw new Conflict();
            var stored = store.submit(proposal);
            guard.set(new Guard(stored, version));
            return view(stored);
        }, guard);
    }

    @Override
    public RequestView request(Target target) {
        return view(authorizedRequest(target, Operation.EVIDENCE_READ));
    }

    @Override
    public Published publish(Publish command) {
        StoredRequest request = authorizedRequest(command.target(), Operation.PUBLISH);
        Scope scope = request.request().scope();
        String actor = require(scope, Operation.PUBLISH);
        if (!request.source().equals(source(scope, request.source().receiptId()))) throw unavailable();
        var guard = new AtomicReference<Guard>();
        return protectedWrite(() -> {
            long version = lock(scope, request.source(), actor, Operation.PUBLISH);
            if (command.expectedVersion() != request.request().expectedVersion()) throw new Conflict();
            var existing = store.publication(request.request().requestId()).orElse(null);
            if (existing != null) {
                var alreadyBound = existing.decisions().stream()
                        .map(bound -> reference(bound.approval())).toList();
                if (alreadyBound.size() != command.decisions().size()
                        || !alreadyBound.containsAll(command.decisions())) throw new Conflict();
                guard.set(new Guard(request, version));
                return existing.receipt();
            }
            if (version != command.expectedVersion() || request.candidates().size() != command.decisions().size())
                throw new Conflict();

            // Validate the entire ordered candidate set before the first Audit write.
            List<Approval> approvals = new ArrayList<>(request.candidates().size());
            for (var candidate : request.candidates()) {
                var reference = command.decisions().stream().filter(ref -> ref.candidateId().equals(candidate.id()))
                        .findFirst().orElseThrow(Conflict::new);
                var approval = authority.finalApproval(candidate, reference).orElseThrow(Conflict::new);
                if (!exact(candidate, reference, approval) || !authority.reviewerEligible(scope, approval.reviewerId()))
                    throw new Conflict();
                approvals.add(approval);
            }
            Instant recordedAt = now();
            var bound = new ArrayList<BoundDecision>(approvals.size());
            for (int index = 0; index < approvals.size(); index++) {
                var approval = approvals.get(index);
                if (approval.actedAt().isAfter(recordedAt)) throw new Conflict();
                String auditEventId = authority.appendAudit(request.candidates().get(index), approval, recordedAt);
                if (auditEventId == null || auditEventId.isBlank()) throw new Conflict();
                bound.add(new BoundDecision(approval, auditEventId));
            }
            var result = store.publish(request, version, recordedAt, List.copyOf(bound));
            if (!actor.equals(require(scope, Operation.PUBLISH))
                    || approvals.stream().anyMatch(approval -> !authority.reviewerEligible(scope, approval.reviewerId())))
                throw unavailable();
            guard.set(new Guard(request, result.receipt().streamVersion()));
            return result.receipt();
        }, guard);
    }

    @Override
    public EvidenceView evidence(Target target) {
        StoredRequest request = authorizedRequest(target, Operation.EVIDENCE_READ);
        var publication = store.publication(request.request().requestId()).orElseThrow(this::unavailable);
        if (publication.decisions().size() != request.candidates().size()) throw unavailable();
        var view = new ArrayList<DecisionEvidence>(publication.decisions().size());
        for (BoundDecision bound : publication.decisions()) {
            Approval approval = bound.approval();
            var candidate = request.candidates().stream().filter(value -> value.id().equals(approval.candidateId()))
                    .findFirst().orElseThrow(this::unavailable);
            if (!exact(candidate, reference(approval), approval)
                    || !authority.auditMatches(candidate, approval, bound.auditEventId())) throw unavailable();
            view.add(new DecisionEvidence(candidate.id(), approval.instanceId(), approval.taskId(), approval.actionId(),
                    approval.reviewerId(), approval.actedAt(), bound.auditEventId()));
        }
        require(request.request().scope(), Operation.EVIDENCE_READ);
        return new EvidenceView(publication.receipt(), view);
    }

    private StoredRequest authorizedRequest(Target target, Operation op) {
        // T33 admission precedes opaque lookup. Condition-specific authority is checked before disclosure.
        targetSystem(target.companyPartyId(), target.pipelineId(), target.sectionId());
        var stored = store.request(target).orElseThrow(this::unavailable);
        require(stored.request().scope(), op);
        return stored;
    }

    private Source source(Scope scope, UUID receiptId) {
        executor.requireNoProtectedContext();
        String system = targetSystem(scope.companyPartyId(), scope.pipelineId(), scope.sectionId());
        final dz.sh.hidra.modules.topology.domain.model.EngineeringBaselinePublication.Receipt baseline;
        try {
            baseline = baselines.read(new EngineeringBaselineReadUseCase.Query(EngineeringBaselineReadUseCase.Action.EXACT,
                    scope.companyPartyId(), system, receiptId, null, null, 0, null)).primary();
        } catch (ProtectedTransactionExecutor.ExecutionFailure failure) {
            if (lockConflict(failure)) throw new Conflict();
            throw failure;
        } catch (AccessDeniedException denied) {
            throw unavailable();
        } catch (EngineeringBaselineDraftFailure failure) {
            if (failure.kind() == EngineeringBaselineDraftFailure.Kind.NOT_FOUND) throw unavailable();
            throw failure;
        }
        if (baseline == null || !receiptId.equals(baseline.id())) throw unavailable();
        var manifest = baseline.submission().manifest();
        if (!scope.companyPartyId().equals(manifest.companyPartyId()) || !system.equals(manifest.pipelineSystemId()))
            throw unavailable();
        var pins = manifest.pins().stream()
                .filter(pin -> pin.kind() == EngineeringBaselineManifest.Kind.NETWORK_PUBLICATION).toList();
        if (pins.size() != 1) throw unavailable();
        return new Source(system, receiptId, baseline.submission().sha256(), pins.getFirst().revisionId());
    }

    private String targetSystem(String company, String pipeline, String section) {
        var target = dossier.pipeline(company, pipeline);
        if (section != null) dossier.section(company, pipeline, section);
        if (target == null || target.pipelineSystemId() == null) throw unavailable();
        return target.pipelineSystemId();
    }

    private String require(Scope scope, Operation operation) {
        try {
            String actor = authority.requireCurrent(scope, operation);
            if (actor == null || actor.isBlank()) throw unavailable();
            return actor;
        } catch (AccessDeniedException denied) {
            throw unavailable();
        }
    }

    private long lock(Scope scope, Source source, String actor, Operation operation) {
        long version = store.lockStream(scope, source.pipelineSystemId());
        if (!store.lockSource(scope, source) || !actor.equals(require(scope, operation))) throw unavailable();
        return version;
    }

    private RequestView view(StoredRequest request) {
        return new RequestView(request.request().requestId(), request.request().expectedVersion(), request.submittedAt(),
                request.candidates().stream().map(candidate ->
                        new Candidate(candidate.id(), candidate.kind(), candidate.workflowLocator())).toList(),
                store.publication(request.request().requestId()).map(PressureRestrictionDecisionPort.StoredPublication::receipt)
                        .orElse(null));
    }

    private static DecisionReference reference(Approval approval) {
        return new DecisionReference(approval.candidateId(), approval.instanceId(), approval.taskId(), approval.actionId());
    }

    private static boolean exact(QualifiedPressureWorkflowCandidate target, DecisionReference reference, Approval approval) {
        return target.id().equals(approval.candidateId()) && target.workflowLocator().equals(approval.locator())
                && reference.equals(reference(approval)) && target.submittedBy().equals(approval.submitterId())
                && approval.reviewerId() != null && !approval.reviewerId().equals(approval.submitterId())
                && approval.actedAt() != null && !approval.actedAt().isBefore(target.createdAt());
    }

    private <T> T protectedWrite(Supplier<T> work, AtomicReference<Guard> guard) {
        try {
            return executor.executePublication(work, store::flush, () -> {
                var expected = guard.get();
                if (expected == null || !store.guard(expected.request(), expected.streamVersion())) throw new Conflict();
            });
        } catch (ProtectedTransactionExecutor.ExecutionFailure failure) {
            if (lockConflict(failure)) throw new Conflict();
            if (failure.outcome() == ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT
                    && failure.getCause() instanceof RuntimeException cause) {
                if (cause instanceof Conflict || cause instanceof NoSuchElementException
                        || cause instanceof IllegalArgumentException) throw cause;
            }
            throw failure;
        }
    }

    private static boolean lockConflict(ProtectedTransactionExecutor.ExecutionFailure failure) {
        if (failure.outcome() != ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT) return false;
        for (Throwable cause = failure.getCause(); cause != null; cause = cause.getCause()) {
            // Both the protected baseline read and the stream lock can reject contention.
            if (cause instanceof PessimisticLockingFailureException
                    || (cause instanceof SQLException sql && "55P03".equals(sql.getSQLState()))) return true;
        }
        return false;
    }

    private record Guard(StoredRequest request, long streamVersion) { }

    private static Instant now() {
        return Instant.now().truncatedTo(ChronoUnit.MICROS);
    }

    private NoSuchElementException unavailable() {
        return new NoSuchElementException("Pressure restriction request unavailable");
    }
}
