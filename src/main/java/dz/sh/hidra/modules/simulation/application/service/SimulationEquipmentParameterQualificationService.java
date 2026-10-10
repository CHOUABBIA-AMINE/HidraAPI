/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEquipmentParameterQualificationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.service
 *
 * @Description : Maintains immutable governed equipment source evidence.
 *
 */
package dz.sh.hidra.modules.simulation.application.service;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationEquipmentParameterRevisionRepositoryPort;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationEquipmentParameterRevisionRepositoryPort.Qualification;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationEquipmentParameterApprovalEvidencePort;
import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimulationEquipmentParameterQualificationService {
    private final SimulationEquipmentParameterRevisionRepositoryPort revisions;
    private final SimulationEquipmentParameterApprovalEvidencePort approvals;
    private final SimulationEquipmentParameterRevisionQueryService sources;
    public SimulationEquipmentParameterQualificationService(SimulationEquipmentParameterRevisionRepositoryPort revisions,
            SimulationEquipmentParameterApprovalEvidencePort approvals, SimulationEquipmentParameterRevisionQueryService sources) {
        this.revisions = revisions; this.approvals = approvals; this.sources = sources;
    }
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Qualification qualify(String qualificationId, String sourceId, String revisionId,
            String instanceId, String taskId, String actionId) {
        var s = revisions.findStored(sourceId, revisionId).orElseThrow(() -> invalid("Missing source revision."));
        var v = s.revision(); Instant now = Instant.now();
        var a = approvals.resolve(s.sha256(), v.governanceBinding(), instanceId, taskId, actionId, now)
                .orElseThrow(() -> invalid("Actual exact final approval is unavailable."));
        if (sources.compatible(v,now,null).isEmpty() || sources.compatible(v,a.actedAt(),null).isEmpty())
            throw invalid("Exact qualified fluid, network and characteristics must be valid at qualification and approval.");
        var q = new Qualification(qualificationId, v.sourceId(), v.revisionId(), s.sha256(), now,
                a.instanceId(), a.taskId(), a.actionId(), a.actorId(), a.actorDisplayName(), a.actedAt(),
                a.definitionId(), a.definitionVersion(), a.targetTypeId(), a.purposeId());
        var existing = revisions.findQualification(qualificationId).orElse(null);
        if (existing != null) {
            var same = new Qualification(q.qualificationId(), q.sourceId(), q.revisionId(), q.payloadSha256(), existing.qualifiedAt(),
                    q.workflowInstanceId(), q.workflowTaskId(), q.workflowActionId(), q.approverId(), q.approverDisplayName(),
                    q.approvedAt(), q.definitionId(), q.definitionVersion(), q.targetTypeId(), q.purposeId());
            if (sources.compatible(v,existing.qualifiedAt(),null).isEmpty()) throw invalid("Source was invalid at original qualification time.");
            if (!existing.equals(same)) throw invalid("Conflicting qualification identity.");
            return existing;
        }
        return revisions.appendQualification(v.sourceId(), v.revisionId(), q);
    }
    private static InvalidSimulationValueException invalid(String message) { return new InvalidSimulationValueException(message); }
}
