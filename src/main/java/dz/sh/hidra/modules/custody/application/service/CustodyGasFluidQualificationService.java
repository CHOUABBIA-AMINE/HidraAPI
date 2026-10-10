/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyGasFluidQualificationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.service
 *
 * @Description : Registers server-derived qualification evidence from an actual revision-bound approval.
 *
 */
package dz.sh.hidra.modules.custody.application.service;

import dz.sh.hidra.modules.custody.application.port.out.CustodyGasFluidRevisionRepositoryPort;
import dz.sh.hidra.modules.custody.application.port.out.CustodyGasFluidRevisionRepositoryPort.Qualification;
import dz.sh.hidra.modules.custody.application.port.out.CustodyGasFluidApprovalEvidencePort;
import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import java.time.Instant;
import dz.sh.hidra.modules.custody.application.contract.simulation.SimulationProductCandidateContract;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustodyGasFluidQualificationService {
    private final CustodyGasFluidRevisionRepositoryPort revisions;
    private final CustodyGasFluidApprovalEvidencePort approvals;
    private final SimulationProductCandidateContract products;
    public CustodyGasFluidQualificationService(CustodyGasFluidRevisionRepositoryPort revisions,
            CustodyGasFluidApprovalEvidencePort approvals, SimulationProductCandidateContract products) {
        this.revisions = revisions; this.approvals = approvals; this.products = products;
    }
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Qualification qualify(String qualificationId, String sourceId, String revisionId,
            String instanceId, String taskId, String actionId) {
        var s = revisions.findStored(sourceId, revisionId).orElseThrow(() -> invalid("Missing source revision."));
        var v = s.revision(); Instant now = Instant.now();
        var snapshot = v.productSnapshot(); var p = products.resolve(snapshot.id()).orElse(null);
        if (p == null || !p.active() || !snapshot.id().equals(p.id()) || !snapshot.catalogName().equals(p.catalogName())
                || !snapshot.code().equals(p.code()) || snapshot.active() != p.active()
                || !snapshot.createdAt().equals(p.createdAt()) || !snapshot.updatedAt().equals(p.updatedAt()))
            throw invalid("Current Custody product no longer matches source snapshot.");
        var a = approvals.resolve(s.sha256(), v.governanceBinding(), instanceId, taskId, actionId, now)
                .orElseThrow(() -> invalid("Actual exact final approval is unavailable."));
        if (!v.effectiveAt(now) || !v.method().effectiveAt(now) || !v.effectiveAt(a.actedAt()) || !v.method().effectiveAt(a.actedAt()))
            throw invalid("Source and method must be valid at qualification and approval.");
        var q = new Qualification(qualificationId, v.sourceId(), v.revisionId(), s.sha256(), now,
                a.instanceId(), a.taskId(), a.actionId(), a.actorId(), a.actorDisplayName(), a.actedAt(),
                a.definitionId(), a.definitionVersion(), a.targetTypeId(), a.purposeId());
        var existing = revisions.findQualification(qualificationId).orElse(null);
        if (existing != null) {
            var same = new Qualification(q.qualificationId(), q.sourceId(), q.revisionId(), q.payloadSha256(), existing.qualifiedAt(),
                    q.workflowInstanceId(), q.workflowTaskId(), q.workflowActionId(), q.approverId(), q.approverDisplayName(),
                    q.approvedAt(), q.definitionId(), q.definitionVersion(), q.targetTypeId(), q.purposeId());
            if (!existing.equals(same)) throw invalid("Conflicting qualification identity.");
            return existing;
        }
        return revisions.appendQualification(v.sourceId(), v.revisionId(), q);
    }
    private static InvalidCustodyValueException invalid(String message) { return new InvalidCustodyValueException(message); }
}
