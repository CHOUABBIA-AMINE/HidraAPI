/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyTransferTicketApprovalEvidenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.contract.custody
 * @Description : Provides exact completed independent Workflow evidence for one immutable ticket target.
 */
package dz.sh.hidra.modules.workflow.application.contract.custody;
import java.time.Instant;
import java.util.Optional;
public interface CustodyTransferTicketApprovalEvidenceContract {
    record Request(String ticketId,String approvalTargetDigest,String reconciliationDigest,
        String issuerId,String reconcilerId,String instanceId,String taskId,String actionId,Instant evaluatedAt){}
    record Evidence(String instanceId,String taskId,String actionId,String actorId,
        String actorDisplayName,String definitionId,int definitionVersion,String targetTypeId,
        String purposeId,Instant actedAt){}
    Optional<Evidence> resolve(Request request);
}
