/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QualifiedOperatingConstraintReadApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Enforces target, source and decision projection checks before qualified disclosure.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselineReadUseCase;
import dz.sh.hidra.modules.topology.application.port.in.PipelineDossierReadUseCase;
import dz.sh.hidra.modules.topology.application.port.in.QualifiedOperatingConstraintReadUseCase;
import dz.sh.hidra.modules.topology.application.port.out.QualifiedOperatingConstraintReadPort;
import dz.sh.hidra.modules.topology.application.port.out.QualifiedOperatingConstraintReadPort.Snapshot;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.*;
import dz.sh.hidra.modules.topology.domain.service.QualifiedPressureRestrictionResolver;
import dz.sh.hidra.modules.topology.domain.service.QualifiedPressureRestrictionResolver.Status;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 * Only T38 can subsequently publish governed approval and withdrawal records.
 * T36 reads an owner-controlled, immutable and insert-time-evidence-checked
 * projection. No source UUID or claimed approved flag by itself grants authority.
 *
 * The protected T07 EXACT read owns its own independent transaction; deliberately
 * do NOT surround this service with a @Transactional boundary.
 */
@Service
public final class QualifiedOperatingConstraintReadApplicationService
        implements QualifiedOperatingConstraintReadUseCase {
    private final PipelineDossierReadUseCase dossier;
    private final EngineeringBaselineReadUseCase baselines;
    private final QualifiedOperatingConstraintReadPort projection;
    private final QualifiedPressureRestrictionResolver resolver = new QualifiedPressureRestrictionResolver();

    public QualifiedOperatingConstraintReadApplicationService(PipelineDossierReadUseCase dossier,
            EngineeringBaselineReadUseCase baselines, QualifiedOperatingConstraintReadPort projection) {
        this.dossier = Objects.requireNonNull(dossier);
        this.baselines = Objects.requireNonNull(baselines);
        this.projection = Objects.requireNonNull(projection);
    }

    @Override
    public Result read(Query query) {
        Objects.requireNonNull(query);
        Scope scope = new Scope(query.companyPartyId(), query.pipelineId(),
                query.sectionId(), query.conditionCode());
        if (query.effectiveAt().getNano() % 1000 != 0)
            throw new IllegalArgumentException("Microsecond-aligned effectiveAt required");
        var pipeline = dossier.pipeline(scope.companyPartyId(), scope.pipelineId());
        if (!scope.pipelineLevel()) dossier.section(scope.companyPartyId(), scope.pipelineId(), scope.sectionId());
        // A missing current system grant is not a reason to expose a hidden source receipt.
        String system = pipeline.pipelineSystemId();
        if (system == null || !projection.targetMatches(scope, system))
            return unavailable();
        Instant recordedBy = Instant.now();
        Snapshot first;
        try {
            first = projection.snapshot(scope, recordedBy);
        } catch (IllegalArgumentException corruptedRow) {
            return unavailable();
        }
        // Every contributing row must remain separately T07-authorized; a hidden
        // conflicting record may never be reported as a conflict or counted.
        for (Revision row : first.revisions()) {
            if (!same(scope, row.scope()) || !projection.conditionAllows(scope, row.pressureUnit())
                    || !sourceAuthorized(scope, system, row.evidence()))
                return unavailable();
        }
        for (Termination event : first.terminations()) {
            if (!same(scope, event.scope())
                    || !sourceAuthorized(scope, system, event.evidence()))
                return unavailable();
        }
        var resolved = resolver.resolve(scope, query.effectiveAt(), recordedBy,
                first.revisions(), first.terminations());

        // Revalidate live Topology identity, current grants, and source rights
        // AFTER reading all candidate evidence, including for a CONFLICT.
        var current = dossier.pipeline(scope.companyPartyId(), scope.pipelineId());
        if (!pipeline.equals(current) || !Objects.equals(system, current.pipelineSystemId()))
            return unavailable();
        if (!scope.pipelineLevel())
            dossier.section(scope.companyPartyId(), scope.pipelineId(), scope.sectionId());
        if (!projection.targetMatches(scope, system)) return unavailable();
        Snapshot again;
        try {
            again = projection.snapshot(scope, recordedBy);
        } catch (IllegalArgumentException corruptedRow) {
            return unavailable();
        }
        if (!first.equals(again)) return unavailable();
        for (Revision row : first.revisions()) {
            if (!projection.conditionAllows(scope, row.pressureUnit())
                    || !sourceAuthorized(scope, system, row.evidence()))
                return unavailable();
        }
        for (Termination event : first.terminations())
            if (!sourceAuthorized(scope, system, event.evidence()))
                return unavailable();
        return new Result(resolved.status(), resolved.restriction());
    }

    private boolean sourceAuthorized(Scope scope, String pipelineSystemId,
            Evidence evidence) {
        try {
            // Do not reuse an earlier protected admission: the T07 owner checks
            // current grant and each exact manifest source on every read.
            var source = baselines.read(new EngineeringBaselineReadUseCase.Query(
                    EngineeringBaselineReadUseCase.Action.EXACT,
                    scope.companyPartyId(), pipelineSystemId,
                    evidence.baselineReceiptId(), null, null, 0, null)).primary();
            if (source == null || !source.id().equals(evidence.baselineReceiptId())
                    || !source.submission().sha256().equals(evidence.baselineDigest())
                    || !scope.companyPartyId().equals(source.submission().manifest().companyPartyId())
                    || !pipelineSystemId.equals(source.submission().manifest().pipelineSystemId()))
                return false;
            return source.submission().manifest().pins().stream()
                    .anyMatch(pin -> pin.kind() == EngineeringBaselineManifest.Kind.NETWORK_PUBLICATION
                            && pin.revisionId().equals(evidence.engineeringSourceRevision()));
        } catch (AccessDeniedException | dz.sh.hidra.modules.topology.application.model.EngineeringBaselineDraftFailure
                | java.util.NoSuchElementException denied) {
            return false;
        }
    }

    private static boolean same(Scope a, Scope b) { return Objects.equals(a, b); }
    private static Result unavailable() { return new Result(Status.UNAVAILABLE, null); }
}
