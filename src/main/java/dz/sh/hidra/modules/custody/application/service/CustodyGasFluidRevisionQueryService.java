/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyGasFluidRevisionQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.service
 *
 * @Description : Resolves one exact qualification while reattesting current product and Workflow evidence.
 *
 */
package dz.sh.hidra.modules.custody.application.service;

import dz.sh.hidra.modules.custody.application.contract.simulation.SimulationGasFluidRevisionContract;
import dz.sh.hidra.modules.custody.application.contract.simulation.SimulationProductCandidateContract;
import dz.sh.hidra.modules.custody.application.port.out.CustodyGasFluidRevisionRepositoryPort;
import dz.sh.hidra.modules.custody.application.port.out.CustodyGasFluidApprovalEvidencePort;
import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevision;
import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustodyGasFluidRevisionQueryService implements SimulationGasFluidRevisionContract {
    private final CustodyGasFluidRevisionRepositoryPort revisions;
    private final CustodyGasFluidApprovalEvidencePort approvals;
    private final SimulationProductCandidateContract products;
    public CustodyGasFluidRevisionQueryService(CustodyGasFluidRevisionRepositoryPort revisions,
            CustodyGasFluidApprovalEvidencePort approvals, SimulationProductCandidateContract products) {
        this.revisions = revisions; this.approvals = approvals; this.products = products;
    }
    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Optional<StoredRevision> findStored(String sourceId, String revisionId) {
        return revisions.findStored(sourceId, revisionId).map(s -> new StoredRevision(export(s.revision()), s.payloadFormat(), s.sha256()));
    }
    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Optional<QualifiedRevision> resolve(String sourceId, String revisionId, String qualificationId,
            Instant at, SimulationGasFluidRevisionContract.SupportedUse use) {
        if (at == null || use == null) return Optional.empty();
        var s = revisions.findStored(sourceId, revisionId).orElse(null);
        var q = revisions.findQualification(qualificationId).orElse(null);
        if (s == null || q == null) return Optional.empty();
        var v = s.revision(); var b = v.governanceBinding();
        if (!q.sourceId().equals(v.sourceId()) || !q.revisionId().equals(v.revisionId()) || !q.payloadSha256().equals(s.sha256())
                || !v.effectiveAt(at) || !v.method().effectiveAt(at) || q.qualifiedAt().isAfter(at)
                || !v.effectiveAt(q.approvedAt()) || !v.method().effectiveAt(q.approvedAt())
                || !v.effectiveAt(q.qualifiedAt()) || !v.method().effectiveAt(q.qualifiedAt())
                || !v.method().supportedUses().contains(CustodyGasFluidRevision.SupportedUse.valueOf(use.name()))
                || !b.definitionId().equals(q.definitionId()) || b.definitionVersion() != q.definitionVersion()
                || !b.targetTypeId().equals(q.targetTypeId()) || !b.purposeId().equals(q.purposeId())) return Optional.empty();
        var p = products.resolve(v.productSnapshot().id()).orElse(null);
        var snapshot = v.productSnapshot();
        if (p == null || !p.active() || !snapshot.id().equals(p.id()) || !snapshot.catalogName().equals(p.catalogName())
                || !snapshot.code().equals(p.code()) || snapshot.active() != p.active()
                || !snapshot.createdAt().equals(p.createdAt()) || !snapshot.updatedAt().equals(p.updatedAt())) return Optional.empty();
        var a = approvals.resolve(s.sha256(), b, q.workflowInstanceId(), q.workflowTaskId(), q.workflowActionId(), at).orElse(null);
        if (a == null || !q.approverId().equals(a.actorId()) || !q.approverDisplayName().equals(a.actorDisplayName())
                || !q.approvedAt().equals(a.actedAt()) || !q.workflowInstanceId().equals(a.instanceId())
                || !q.workflowTaskId().equals(a.taskId()) || !q.workflowActionId().equals(a.actionId())
                || !q.definitionId().equals(a.definitionId()) || q.definitionVersion() != a.definitionVersion()
                || !q.targetTypeId().equals(a.targetTypeId()) || !q.purposeId().equals(a.purposeId())) return Optional.empty();
        return Optional.of(new QualifiedRevision(new StoredRevision(export(v), s.payloadFormat(), s.sha256()), export(q)));
    }
    private SimulationGasFluidRevisionContract.ProductSnapshot export(CustodyGasFluidRevision.ProductSnapshot v) { return new SimulationGasFluidRevisionContract.ProductSnapshot(v.id(), v.catalogName(), v.code(), v.active(), v.createdAt(), v.updatedAt()); }
    private SimulationGasFluidRevisionContract.GovernanceBinding export(CustodyGasFluidRevision.GovernanceBinding v) { return new SimulationGasFluidRevisionContract.GovernanceBinding(v.definitionId(), v.definitionVersion(), v.targetTypeId(), v.purposeId()); }
    private SimulationGasFluidRevisionContract.Component export(CustodyGasFluidRevision.Component v) { return new SimulationGasFluidRevisionContract.Component(v.componentReference(), v.moleFraction()); }
    private SimulationGasFluidRevisionContract.Method export(CustodyGasFluidRevision.Method v) { return new SimulationGasFluidRevisionContract.Method(v.reference(), v.revisionId(), v.recordedAt(), v.effectiveFrom(), v.effectiveUntil(), SimulationGasFluidRevisionContract.Origin.valueOf(v.origin().name()), v.evidenceReference(), SimulationGasFluidRevisionContract.InputRepresentation.valueOf(v.inputRepresentation().name()), v.allowedComponentReferences(), v.minimumPressurePascalsAbsolute(), v.maximumPressurePascalsAbsolute(), v.minimumTemperatureKelvin(), v.maximumTemperatureKelvin(), v.supportedUses().stream().map(item -> SimulationGasFluidRevisionContract.SupportedUse.valueOf(item.name())).toList()); }
    private SimulationGasFluidRevisionContract.Revision export(CustodyGasFluidRevision v) { return new SimulationGasFluidRevisionContract.Revision(v.sourceId(), v.revisionId(), v.recordedAt(), v.effectiveFrom(), v.effectiveUntil(), SimulationGasFluidRevisionContract.Origin.valueOf(v.origin().name()), v.evidenceReference(), export(v.productSnapshot()), SimulationGasFluidRevisionContract.ProductKind.valueOf(v.productKind().name()), export(v.method()), v.components().stream().map(this::export).toList(), export(v.governanceBinding())); }
    private SimulationGasFluidRevisionContract.Qualification export(CustodyGasFluidRevisionRepositoryPort.Qualification v) { return new SimulationGasFluidRevisionContract.Qualification(v.qualificationId(), v.sourceId(), v.revisionId(), v.payloadSha256(), v.qualifiedAt(), v.workflowInstanceId(), v.workflowTaskId(), v.workflowActionId(), v.approverId(), v.approverDisplayName(), v.approvedAt(), v.definitionId(), v.definitionVersion(), v.targetTypeId(), v.purposeId()); }
}
