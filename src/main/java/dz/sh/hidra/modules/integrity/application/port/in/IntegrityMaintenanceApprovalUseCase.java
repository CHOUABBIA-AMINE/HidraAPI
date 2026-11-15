/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrityMaintenanceApprovalUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.port.in
 * @Description : T18 governed Integrity and Assets maintenance handoff boundary.
 */
package dz.sh.hidra.modules.integrity.application.port.in;

import java.time.Instant;
public interface IntegrityMaintenanceApprovalUseCase {
    record Command(String commandId,String recommendationId,String caseId,String reason) { }
    record View(String approvalId,String findingId,String caseId,String defectId,
            String recommendationId,String topologyAssetType,String topologyAssetId,
            String companyId,String approverId,Instant approvedAt,String reason,
            String sourceVersion,String workOrderId,String verificationId,String verificationStatus,
            boolean replay) { }
    View approve(String findingId,Command command);
    View read(String findingId,String approvalId);
}
