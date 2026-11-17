/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyTicketFinalizationPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.service
 * @Description : Require independent Workflow approval and settled reconciliation.
 */
package dz.sh.hidra.modules.custody.domain.service;
import dz.sh.hidra.modules.custody.domain.model.CustodyReconciliationSnapshot;
import java.time.Instant;
import java.util.*;
public final class CustodyTicketFinalizationPolicy {
    public record ApprovalEvidence(String ticketId,String reconciliationDigest,String workflowInstanceId,
          String taskId,String actionId,String reviewerId,Instant approvedAt,boolean completedAndApproved) {
        public ApprovalEvidence {
            for(String s:Arrays.asList(ticketId,reconciliationDigest,workflowInstanceId,taskId,actionId,reviewerId))
                if(s==null||s.isBlank())throw new IllegalArgumentException("Exact approval identity required");
            Objects.requireNonNull(approvedAt);
        }
    }
    public void requireFinalizable(String ticketId,String digest,CustodyReconciliationSnapshot snapshot,
          String issuer,String reconciler,boolean unresolvedDiscrepancy,boolean explicitlyDisposed,
          ApprovalEvidence approval,Instant at){
        Objects.requireNonNull(snapshot);Objects.requireNonNull(approval);Objects.requireNonNull(at);
        if(!approval.completedAndApproved()||!ticketId.equals(approval.ticketId())
           ||!digest.equals(approval.reconciliationDigest())||approval.approvedAt().isAfter(at)
           ||approval.reviewerId().equals(issuer)||approval.reviewerId().equals(reconciler)
           ||unresolvedDiscrepancy||(snapshot.difference().signum()!=0&&!explicitlyDisposed))
            throw new IllegalStateException("Custody ticket cannot be software-finalized without independent approval");
    }
}
