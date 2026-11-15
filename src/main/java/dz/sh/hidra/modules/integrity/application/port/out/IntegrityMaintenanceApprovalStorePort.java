/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrityMaintenanceApprovalStorePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.port.out
 * @Description : T18 governed Integrity and Assets maintenance handoff boundary.
 */
package dz.sh.hidra.modules.integrity.application.port.out;

import java.time.Instant;
import java.util.Optional;
public interface IntegrityMaintenanceApprovalStorePort {
    record Source(String findingId,String recommendationId,String caseId,String defectId,
            String assetType,String assetId,String runStatus,String recommendationStatus,
            String caseStatus,String findingDefect,String recommendationDefect,
            String caseDefect,String defectFinding,String runType,String runAsset,
            String findingType,String findingAsset,String defectType,String defectAsset,
            String caseType,String caseAsset,String title,String sourceVersion) { }
    record Approval(String approvalId,String commandId,String commandDigest,String findingId,
            String caseId,String defectId,String recommendationId,String assetType,
            String assetId,String companyId,String approverId,Instant approvedAt,
            String reason,String sourceVersion,String workOrderId,
            String verificationId,String verificationStatus) { }
    Source lockSource(String findingId,String recommendationId,String caseId);
    Source lockApprovedSource(String recommendationId,String findingId,String caseId);
    Optional<Approval> byCommand(String commandId);
    Optional<Approval> byId(String approvalId);
    Optional<Approval> byRecommendation(String recommendationId);
    Approval approve(Source source,String companyId,String actorId,String reason,
            String commandId,String digest,Instant at);
    void link(String approvalId,String workOrderId);
    void recordVerification(String approvalId,String workOrderId,String executionId,
            String verificationId,String actorId,String verdict,String evidence,Instant at);
}
