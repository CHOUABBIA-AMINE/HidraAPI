/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyGasFluidRevisionRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 *
 * @Description : Maintains exact immutable revision evidence.
 *
 */
package dz.sh.hidra.modules.custody.application.port.out;

import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevision;
import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevision.Method;
import java.time.Instant;
import java.util.Optional;

public interface CustodyGasFluidRevisionRepositoryPort {
    record StoredRevision(CustodyGasFluidRevision revision, String payloadFormat, String sha256) {}
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
    CustodyGasFluidRevision append(CustodyGasFluidRevision revision);
    Optional<StoredRevision> findStored(String sourceId, String revisionId);
    Optional<StoredRevision> findByApprovalTargetId(String digest);
    Method appendMethod(Method method);
    Optional<Method> findMethodStored(String reference, String revisionId);
    Qualification appendQualification(String sourceId, String revisionId, Qualification qualification);
    Optional<Qualification> findQualification(String qualificationId);
}
