/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QualifiedPressureWorkflowCandidateReadApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Revalidates exact Topology, T07 and condition evidence for an unapproved Workflow target.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselineReadUseCase;
import dz.sh.hidra.modules.topology.application.port.in.PipelineDossierReadUseCase;
import dz.sh.hidra.modules.topology.application.port.in.QualifiedPressureWorkflowCandidateReadUseCase;
import dz.sh.hidra.modules.topology.application.port.out.QualifiedOperatingConstraintReadPort;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureWorkflowCandidate;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.Scope;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * A pending candidate is NOT evidence of approval. Its identity, exact
 * engineering source, company, condition and pressure are fixed *before*
 * Workflow creates an independent human review. The T38 publication writer
 * remains out of scope. T07's protected executor must not be nested inside a
 * Workflow or JDBC application transaction.
 */
@Service
public class QualifiedPressureWorkflowCandidateReadApplicationService
        implements QualifiedPressureWorkflowCandidateReadUseCase {
    private final QualifiedOperatingConstraintReadPort projection;
    private final PipelineDossierReadUseCase dossier;
    private final EngineeringBaselineReadUseCase baselines;
    public QualifiedPressureWorkflowCandidateReadApplicationService(
            QualifiedOperatingConstraintReadPort projection,
            PipelineDossierReadUseCase dossier, EngineeringBaselineReadUseCase baselines) {
        this.projection=Objects.requireNonNull(projection);
        this.dossier=Objects.requireNonNull(dossier);
        this.baselines=Objects.requireNonNull(baselines);
    }
    @Override
    public Optional<String> validatedLocator(UUID candidateId) {
        if(candidateId==null) return Optional.empty();
        try {
            var first=projection.candidate(candidateId).orElse(null);
            if(first==null) return Optional.empty();
            var scope=first.scope();
            var pipeline=dossier.pipeline(scope.companyPartyId(),scope.pipelineId());
            if(!scope.pipelineLevel())
                dossier.section(scope.companyPartyId(),scope.pipelineId(),scope.sectionId());
            String system=pipeline.pipelineSystemId();
            if(system==null || !system.equals(first.pipelineSystemId())
                    || !projection.targetMatches(scope,system)
                    || !candidateAllowed(first,system)) return Optional.empty();
            var again=projection.candidate(candidateId).orElse(null);
            if(!first.equals(again)) return Optional.empty();
            var current=dossier.pipeline(scope.companyPartyId(),scope.pipelineId());
            if(!pipeline.equals(current) || !projection.targetMatches(scope,system)
                    || !candidateAllowed(first,system)) return Optional.empty();
            if(!scope.pipelineLevel())
                dossier.section(scope.companyPartyId(),scope.pipelineId(),scope.sectionId());
            return Optional.of(first.workflowLocator());
        } catch(RuntimeException deniedOrStale) {
            // No target existence, foreign source, failed reviewer grant or
            // malformed candidate detail is returned to Workflow callers.
            return Optional.empty();
        }
    }
    private boolean candidateAllowed(QualifiedPressureWorkflowCandidate c,String system) {
        Scope scope=c.scope();
        if(c.kind()==QualifiedPressureWorkflowCandidate.Kind.REVISION) {
            if(!projection.conditionAllows(scope,c.pressureUnit())) return false;
        } else {
            if(!projection.conditionKnown(scope)) return false;
            var snapshot=projection.snapshot(scope,Instant.now());
            var prior=snapshot.revisions().stream()
                    .filter(r->r.id().equals(c.retiredRevisionId())).findFirst().orElse(null);
            if(prior==null || c.effectiveFrom().isBefore(prior.effectiveFrom()))
                return false;
            if(c.kind()==QualifiedPressureWorkflowCandidate.Kind.SUPERSEDE
                    && snapshot.revisions().stream().noneMatch(r->
                        r.id().equals(c.successorRevisionId())
                            && c.effectiveFrom().equals(r.effectiveFrom())
                            && c.retiredRevisionId().equals(r.supersedes())))
                return false;
        }
        var receipt=baselines.read(new EngineeringBaselineReadUseCase.Query(
                EngineeringBaselineReadUseCase.Action.EXACT,
                scope.companyPartyId(),system,c.baselineReceiptId(),
                null,null,0,null)).primary();
        return receipt!=null && receipt.id().equals(c.baselineReceiptId())
                && receipt.submission().sha256().equals(c.baselineDigest())
                && scope.companyPartyId().equals(receipt.submission().manifest().companyPartyId())
                && system.equals(receipt.submission().manifest().pipelineSystemId())
                && receipt.submission().manifest().pins().stream()
                   .anyMatch(pin->pin.kind()==EngineeringBaselineManifest.Kind.NETWORK_PUBLICATION
                       && pin.revisionId().equals(c.engineeringSourceRevision()));
    }
}
