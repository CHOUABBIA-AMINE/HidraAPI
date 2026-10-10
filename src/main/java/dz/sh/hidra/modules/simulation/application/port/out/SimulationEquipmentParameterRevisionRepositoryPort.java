/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEquipmentParameterRevisionRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.out
 *
 * @Description : Maintains immutable governed equipment source evidence.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.out;

import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision.*;
import java.time.Instant;
import java.util.Optional;

public interface SimulationEquipmentParameterRevisionRepositoryPort {
    record StoredRevision(SimulationEquipmentParameterRevision revision, String payloadFormat, String sha256) {}
    record Qualification(String qualificationId, String sourceId, String revisionId, String payloadSha256,
            Instant qualifiedAt, String workflowInstanceId, String workflowTaskId, String workflowActionId,
            String approverId, String approverDisplayName, Instant approvedAt, String definitionId,
            int definitionVersion, String targetTypeId, String purposeId) {
        public Qualification {
            if (qualificationId == null || qualificationId.isBlank() || sourceId == null || sourceId.isBlank()
                    || revisionId == null || revisionId.isBlank() || payloadSha256 == null || !payloadSha256.matches("[0-9a-f]{64}")
                    || qualifiedAt == null || approvedAt == null || approvedAt.isAfter(qualifiedAt)
                    || workflowInstanceId == null || workflowInstanceId.isBlank() || workflowTaskId == null || workflowTaskId.isBlank()
                    || workflowActionId == null || workflowActionId.isBlank() || approverId == null || approverId.isBlank()
                    || approverDisplayName == null || approverDisplayName.isBlank() || definitionId == null || definitionId.isBlank()
                    || definitionVersion < 1 || targetTypeId == null || targetTypeId.isBlank() || purposeId == null || purposeId.isBlank())
                throw new IllegalArgumentException("Complete actual qualification evidence required.");
            qualificationId = qualificationId.trim(); sourceId = sourceId.trim(); revisionId = revisionId.trim();
            workflowInstanceId = workflowInstanceId.trim(); workflowTaskId = workflowTaskId.trim(); workflowActionId = workflowActionId.trim();
            approverId = approverId.trim(); approverDisplayName = approverDisplayName.trim(); definitionId = definitionId.trim();
            targetTypeId = targetTypeId.trim(); purposeId = purposeId.trim();
        }
    }
    SimulationEquipmentParameterRevision append(SimulationEquipmentParameterRevision revision);
    Optional<StoredRevision> findStored(String sourceId, String revisionId);
    Optional<StoredRevision> findByApprovalTargetId(String digest);
    CompressorCurve appendCharacteristic(CompressorCurve value);
    ValveCharacteristic appendCharacteristic(ValveCharacteristic value);
    Optional<CompressorCurve> findCompressorCurveStored(String id, String revisionId);
    Optional<ValveCharacteristic> findValveCharacteristicStored(String id, String revisionId);
    Qualification appendQualification(String sourceId, String revisionId, Qualification qualification);
    Optional<Qualification> findQualification(String qualificationId);
}
