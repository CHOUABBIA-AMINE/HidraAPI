/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrityMaintenanceApprovalResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : API
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.api.rest.response
 * @Description : T18 governed Integrity and Assets maintenance handoff boundary.
 */
package dz.sh.hidra.modules.integrity.api.rest.response;

import dz.sh.hidra.modules.integrity.application.port.in.IntegrityMaintenanceApprovalUseCase;
import java.time.Instant;
public record IntegrityMaintenanceApprovalResponse(
        String approvalId,String findingId,String caseId,String defectId,String recommendationId,
        String topologyAssetType,String topologyAssetId,String companyId,String approverId,
        Instant approvedAt,String reason,String sourceVersion,String workOrderId,
        String verificationId,String verificationStatus,boolean replay) {
    public static IntegrityMaintenanceApprovalResponse from(IntegrityMaintenanceApprovalUseCase.View v) {
        return new IntegrityMaintenanceApprovalResponse(v.approvalId(),v.findingId(),v.caseId(),
                v.defectId(),v.recommendationId(),v.topologyAssetType(),v.topologyAssetId(),
                v.companyId(),v.approverId(),v.approvedAt(),v.reason(),v.sourceVersion(),
                v.workOrderId(),v.verificationId(),v.verificationStatus(),v.replay());
    }
}
