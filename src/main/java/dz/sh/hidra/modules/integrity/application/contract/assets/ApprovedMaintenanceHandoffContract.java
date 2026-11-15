/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ApprovedMaintenanceHandoffContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.contract.assets
 * @Description : T18 governed Integrity and Assets maintenance handoff boundary.
 */
package dz.sh.hidra.modules.integrity.application.contract.assets;

import java.time.Instant;
import java.util.Optional;
/** The only Assets-visible Integrity-owned approval and final outcome boundary. */
public interface ApprovedMaintenanceHandoffContract {
    record Approved(String approvalId,String recommendationId,String findingId,
            String caseId,String defectId,String assetType,String assetId,
            String companyId,String title,String approverId,String sourceVersion) { }
    Approved current(String approvalId);
    Optional<String> approvedRecommendation(String recommendationId);
    void link(String approvalId,String workOrderId);
    void recordVerification(String approvalId,String workOrderId,String executionId,
            String verificationId,String actorId,String verdict,String evidence,Instant at);
}
