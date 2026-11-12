/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedEngineeringBaselinePublicationCoordinator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Implements the exact engineering baseline publication contract.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract;
import dz.sh.hidra.modules.topology.application.model.EngineeringBaselineDraftFailure;
import dz.sh.hidra.modules.topology.application.model.EngineeringBaselineDraftFailure.Kind;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselinePublicationUseCase.*;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselineReadUseCase;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringBaselineApprovalPort;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringBaselinePublicationRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringBaselineSourcePort;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringBaselineSourcePort.Original;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselinePublication.*;
import dz.sh.hidra.modules.topology.domain.service.EngineeringBaselineCompletenessPolicy;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public final class ProtectedEngineeringBaselinePublicationCoordinator {
    private final TopologyEngineeringBaselineAuthorizationContract identity;
    private final EngineeringBaselinePublicationRepositoryPort storage;
    private final EngineeringBaselineSourcePort sources;
    private final EngineeringBaselineApprovalPort approvals;
    private final ProtectedTransactionExecutor executor;
    private final EngineeringBaselineCompletenessPolicy completeness = new EngineeringBaselineCompletenessPolicy();

    public ProtectedEngineeringBaselinePublicationCoordinator(TopologyEngineeringBaselineAuthorizationContract identity,
            EngineeringBaselinePublicationRepositoryPort storage, EngineeringBaselineSourcePort sources,
            EngineeringBaselineApprovalPort approvals, ProtectedTransactionExecutor executor) {
        this.identity = identity;
        this.storage = storage;
        this.sources = sources;
        this.approvals = approvals;
        this.executor = executor;
    }

    TopologyEngineeringBaselineAuthorizationContract.Prepared prepare() {
        executor.requireNoProtectedContext();
        return identity.prepare();
    }

    record Admission(Command command, TopologyEngineeringBaselineAuthorizationContract.Admission identity,
                     Original origin, EngineeringBaselineSourcePort.Admission sources, List<ProtectedRelationFence> fences,
                     ProtectedTransactionExecutor.ProvisionalPermit permit, Result result) {}

    Admission acquire(Command c, TopologyEngineeringBaselineAuthorizationContract.Prepared prepared) {
        executor.beginAdmission();
        storage.lock();
        var action = switch (c.action()) {
            case SUBMIT -> TopologyEngineeringBaselineAuthorizationContract.Action.SUBMIT;
            case REVIEW -> TopologyEngineeringBaselineAuthorizationContract.Action.REVIEW;
            case PUBLISH -> TopologyEngineeringBaselineAuthorizationContract.Action.PUBLISH;
            case TARGET, RECEIPT -> TopologyEngineeringBaselineAuthorizationContract.Action.READ;
        };
        var auth = identity.acquire(prepared, new TopologyEngineeringBaselineAuthorizationContract.Request(
                action, c.company(), c.pipeline()));
        var proof = identity.contribute(auth);
        if (!storage.scopeAvailable(c.company(), c.pipeline())) throw denied();
        var origin = new Original(proof.bindingId(), proof.sessionId(), proof.userId(), proof.backendPid(),
                proof.transactionId(), proof.databaseRole(), proof.sampledAt(), proof.validUntil());
        Receipt receipt = switch (c.action()) {
            case RECEIPT -> storage.receipt(c.company(), c.pipeline(), c.id());
            case PUBLISH -> storage.receipt(c.company(), c.pipeline(), c.requestId());
            default -> null;
        };
        if (c.action() == Action.RECEIPT && receipt == null) throw new EngineeringBaselineDraftFailure(Kind.NOT_FOUND);
        Submission submission = receipt == null ? storage.submission(c.company(), c.pipeline(), c.id()) : receipt.submission();
        boolean replay = c.action() == Action.SUBMIT && submission != null || c.action() == Action.PUBLISH && receipt != null;
        if (c.action() == Action.SUBMIT && !replay) submission = storage.draft(c, origin);
        if (submission == null) throw new EngineeringBaselineDraftFailure(Kind.NOT_FOUND);
        var source = sources.capture(submission.manifest(), origin, r -> identity.requireSourceRead(auth,
                new TopologyEngineeringBaselineAuthorizationContract.Source(
                        TopologyEngineeringBaselineAuthorizationContract.SourceKind.valueOf(r.kind().name()),
                        r.referenceId(), r.units())));
        if (c.action() == Action.SUBMIT && (!submission.submittedBy().equals(origin.userId())
                || !submission.manifest().manifestId().equals(c.manifestId())
                || submission.manifest().version() != c.version() || !submission.sha256().equals(c.sha256())))
            throw new EngineeringBaselineDraftFailure(Kind.CONFLICT);
        if ((c.action() == Action.TARGET || c.action() == Action.REVIEW) && !submission.sha256().equals(c.sha256()))
            throw denied();
        if (c.action() == Action.REVIEW && submission.submittedBy().equals(origin.userId())) throw denied();
        if (c.action() == Action.PUBLISH && replay && (!receipt.id().equals(c.requestId())
                || !submission.id().equals(c.id()) || !receipt.publishedBy().equals(origin.userId())
                || receipt.epoch() != c.expectedEpoch() + 1 || !receipt.approval().decision().equals(c.decision())))
            throw new EngineeringBaselineDraftFailure(Kind.CONFLICT);

        if (!replay && c.action() != Action.RECEIPT
                && !completeness.inspect(submission.manifest(), sources.facts(source)).complete())
            throw new EngineeringBaselineDraftFailure(Kind.CONFLICT);
        if (c.action() == Action.PUBLISH && !replay) {
            if (storage.head(c.company(), c.pipeline()) != c.expectedEpoch()
                    || storage.published(c.company(), c.pipeline(), submission.id()))
                throw new EngineeringBaselineDraftFailure(Kind.CONFLICT);
            Approval approval = approvals.requireApproval(submission, c.decision(), origin.sampledAt());
            identity.requireCurrentReviewer(auth, approval.reviewerId());
            receipt = new Receipt(c.requestId(), submission, c.expectedEpoch() + 1, approval, origin.userId(), origin.sampledAt());
        }
        var fences = new TreeMap<String, ProtectedRelationFence>();
        for (var group : List.of(identity.finalFences(auth), storage.fences(), sources.finalFences(source))) {
            for (var fence : group) fences.merge(fence.relation(), fence,
                    (left, right) -> strength(left.mode()) >= strength(right.mode()) ? left : right);
        }
        return new Admission(c, auth, origin, source, List.copyOf(fences.values()), executor.sealAdmission(),
                new Result(submission, receipt, replay));
    }


    /** T07 historical reads deliberately avoid T06's live-source completeness capture. */
    record ReadAdmission(TopologyEngineeringBaselineAuthorizationContract.Admission identity,
                         Original origin, List<ProtectedRelationFence> fences,
                         ProtectedTransactionExecutor.ProvisionalPermit permit,
                         EngineeringBaselineReadUseCase.Result result) {}

    ReadAdmission acquireRead(EngineeringBaselineReadUseCase.Query query,
                              TopologyEngineeringBaselineAuthorizationContract.Prepared prepared) {
        executor.beginAdmission();
        storage.lock();
        var auth = identity.acquire(prepared, new TopologyEngineeringBaselineAuthorizationContract.Request(
                TopologyEngineeringBaselineAuthorizationContract.Action.READ, query.company(), query.pipeline()));
        var proof = identity.contribute(auth);
        if (!storage.scopeAvailable(query.company(), query.pipeline())) throw denied();
        var origin = new Original(proof.bindingId(), proof.sessionId(), proof.userId(), proof.backendPid(),
                proof.transactionId(), proof.databaseRole(), proof.sampledAt(), proof.validUntil());

        Receipt left = null;
        Receipt right = null;
        List<Receipt> page = List.of();
        String nextCursor = null;
        switch (query.action()) {
            case EXACT -> left = storage.receipt(query.company(), query.pipeline(), query.receiptId());
            case CURRENT -> left = storage.current(query.company(), query.pipeline());
            case EFFECTIVE -> left = storage.effective(query.company(), query.pipeline(), query.asOf());
            case COMPARE -> {
                left = storage.receipt(query.company(), query.pipeline(), query.receiptId());
                right = storage.receipt(query.company(), query.pipeline(), query.otherReceiptId());
            }
            case HISTORY -> {
                // A future publication must not leak through an earlier as-of history cursor.
                var latestEligible = storage.history(query.company(), query.pipeline(), query.asOf(),
                        Long.MAX_VALUE, Long.MAX_VALUE, 1);
                long water = latestEligible.isEmpty() ? 0 : latestEligible.getFirst().epoch();
                long before = Long.MAX_VALUE;
                if (query.cursor() != null) {
                    String[] values = query.cursor().split(":", -1);
                    try {
                        long requestedWater = Long.parseLong(values[0]);
                        before = Long.parseLong(values[1]);
                        if (requestedWater < 1 || before < 1 || before > requestedWater
                                || requestedWater > water) throw new IllegalArgumentException("Invalid history cursor");
                        water = requestedWater;
                    } catch (NumberFormatException invalid) {
                        throw new IllegalArgumentException("Invalid history cursor", invalid);
                    }
                }
                var slice = water == 0 ? List.<Receipt>of()
                        : storage.history(query.company(), query.pipeline(), query.asOf(), water, before, query.limit() + 1);
                if (slice.size() > query.limit()) {
                    page = List.copyOf(slice.subList(0, query.limit()));
                    nextCursor = water + ":" + page.getLast().epoch();
                } else page = slice;
                // The extra record also influences the has-more indicator and therefore needs authority.
                for (var item : slice) authorizeHistoricalPins(auth, item);
            }
        }
        if (query.action() != EngineeringBaselineReadUseCase.Action.HISTORY) {
            if (left == null || query.action() == EngineeringBaselineReadUseCase.Action.COMPARE && right == null)
                throw new EngineeringBaselineDraftFailure(Kind.NOT_FOUND);
            authorizeHistoricalPins(auth, left);
            if (right != null) authorizeHistoricalPins(auth, right);
        }
        var merged = new TreeMap<String, ProtectedRelationFence>();
        for (var group : List.of(identity.finalFences(auth), storage.fences())) {
            for (var fence : group) merged.merge(fence.relation(), fence,
                    (a,b) -> strength(a.mode()) >= strength(b.mode()) ? a : b);
        }
        var result = new EngineeringBaselineReadUseCase.Result(left, right, page, nextCursor);
        return new ReadAdmission(auth, origin, List.copyOf(merged.values()), executor.sealAdmission(), result);
    }

    private void authorizeHistoricalPins(TopologyEngineeringBaselineAuthorizationContract.Admission auth, Receipt receipt) {
        // Current grants over each immutable selected source, without requiring that the source
        // still be active or its original approval/current-head state be retained.
        var manifest = receipt.submission().manifest();
        try {
            // First authorize each manifest pin before looking up historical link metadata.
            for (var pin : manifest.pins()) {
                var kind = TopologyEngineeringBaselineAuthorizationContract.SourceKind.valueOf(pin.kind().name());
                String id = switch (pin.kind()) {
                    case NETWORK_PUBLICATION -> manifest.pipelineSystemId();
                    case STATION_CONFIGURATION -> pin.ownerId();
                    case CHARACTERISTIC_LINK, PRODUCT_PUBLICATION -> pin.sourceId();
                };
                identity.requireSourceRead(auth, new TopologyEngineeringBaselineAuthorizationContract.Source(
                        kind, id, pin.kind() == dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest.Kind.CHARACTERISTIC_LINK
                                ? List.of(pin.ownerId()) : List.of()));
            }
            // Immutable Topology link metadata supplies the actual manufacturer source scope.
            for (var source : storage.historicalSourceReads(manifest))
                identity.requireSourceRead(auth, new TopologyEngineeringBaselineAuthorizationContract.Source(
                        TopologyEngineeringBaselineAuthorizationContract.SourceKind.valueOf(source.kind().name()),
                        source.referenceId(), source.units()));
        } catch (AccessDeniedException sourceDenied) {
            // An unknown receipt and a receipt whose pin rights are absent are both nondisclosing.
            throw new EngineeringBaselineDraftFailure(Kind.NOT_FOUND);
        }
    }

    void prepareReadFinal(ReadAdmission admission) {
        executor.requireEngineeringBaselinePublicationFinalPreparation();
        identity.requireCurrent(admission.identity());
    }

    void guardRead(ReadAdmission admission) {
        if (!storage.guard(admission.origin(), admission.fences())) throw denied();
        identity.markFinal(admission.identity());
    }

    Result perform(Admission a) {
        var result = a.result();
        if (!result.replayed() && (a.command().action() == Action.SUBMIT || a.command().action() == Action.PUBLISH)) {
            executor.beginProvisional(a.permit(), storage.mutationSql());
            if (a.command().action() == Action.SUBMIT) storage.insert(result.submission());
            else storage.insert(result.receipt());
            executor.endProvisional(a.permit());
        }
        return result;
    }

    void prepareFinal(Admission a) {
        executor.requireEngineeringBaselinePublicationFinalPreparation();
        identity.requireCurrent(a.identity());
        sources.requireCurrent(a.sources());
        if (a.command().action() == Action.PUBLISH && !a.result().replayed())
            identity.requireCurrentReviewer(a.identity(), a.result().receipt().approval().reviewerId());
    }

    void guard(Admission a) {
        if (!storage.guard(a.origin(), a.fences())) throw denied();
        identity.markFinal(a.identity());
    }

    private static int strength(ProtectedRelationFence.Mode mode) {
        return switch (mode) { case ACCESS_SHARE -> 0; case SHARE -> 1; case SHARE_ROW_EXCLUSIVE -> 2; };
    }

    private static AccessDeniedException denied() {
        return new AccessDeniedException("Baseline publication denied");
    }
}
