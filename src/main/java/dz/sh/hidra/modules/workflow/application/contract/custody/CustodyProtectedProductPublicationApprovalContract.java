/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyProtectedProductPublicationApprovalContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.contract.custody
 *
 * @Description : Provides bounded product source publication governance.
 *
 */
package dz.sh.hidra.modules.workflow.application.contract.custody;

import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
public interface CustodyProtectedProductPublicationApprovalContract {
    record Request(String companyPartyId,String targetDigest,String instanceId,String taskId,String actionId) {
        public Request {
            exact(companyPartyId);
            if(targetDigest==null){if(instanceId!=null || taskId!=null || actionId!=null)throw new IllegalArgumentException("Policy request only");}
            else {if(!targetDigest.matches("[0-9a-f]{64}"))throw new IllegalArgumentException("Exact envelope digest required");exact(instanceId);exact(taskId);exact(actionId);}
        }
        private static void exact(String s){if(s==null || s.isBlank() || !s.equals(s.strip()) || s.indexOf(0)>=0 || s.codePointCount(0,s.length())>80)
            throw new IllegalArgumentException("Exact publication reference required");
            for(int n=0;n<s.length();n++){char c=s.charAt(n);if(Character.isHighSurrogate(c)){
                if(++n>=s.length() || !Character.isLowSurrogate(s.charAt(n)))throw new IllegalArgumentException("Invalid Unicode");
            }else if(Character.isLowSurrogate(c))throw new IllegalArgumentException("Invalid Unicode");}}
    }
    record Policy(String companyPartyId,String definitionId,int definitionVersion,String targetTypeId,String purposeId,Instant updatedAt) {}
    record Evidence(Policy policy,String targetDigest,String instanceId,String taskId,String actionId,String historyId,
            String actorId,String actorDisplayName,Instant actedAt) {}
    record Contribution(Policy policy,Evidence approval,int backendPid,long transactionId,Instant sampledAt) {
        public Contribution {Objects.requireNonNull(policy);Objects.requireNonNull(sampledAt);if(backendPid<=0 || transactionId<=0)throw new IllegalArgumentException("Physical transaction required");}
    }
    interface Admission {}
    Admission acquire(Request request);
    Contribution read(Admission admission);
    void requireCurrent(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
}
