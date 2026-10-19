/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyProtectedEngineeringDraftAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.topology
 *
 * @Description : Implements the bounded company-scoped engineering draft contract.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.topology;

import java.time.Instant;
import java.util.*;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;

/** Scalar exported observations never substitute for the issuing owner's opaque admission. */
public interface TopologyProtectedEngineeringDraftAuthorizationContract {
    enum Action { CREATE, READ, UPDATE, VALIDATE }
    enum Decision { PERMIT, DENY, INDETERMINATE }
    record Request(Action action,String companyPartyId,String pipelineSystemId){
        public Request {Objects.requireNonNull(action);exact(companyPartyId);exact(pipelineSystemId);}
        private static void exact(String s){if(s==null || s.isBlank() || !s.equals(s.trim()) || s.codePointCount(0,s.length())>80)
            throw new IllegalArgumentException("Exact draft scope required");}
    }
    interface Prepared {}
    interface Admission {}
    Prepared prepare();
    Admission acquire(Prepared prepared,Request request);
    Contribution contribute(Admission admission);
    void requireCurrent(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
    static String permissionCode(Action a){return "topology:engineering-draft:"+Objects.requireNonNull(a).name().toLowerCase(Locale.ROOT);}
    record Contribution(Request request,Decision companyDecision,Decision networkDecision,
            String bindingId,String sessionId,String userId,Instant sampledAt,Instant validUntil,int backendPid,long transactionId){
        public Contribution {Objects.requireNonNull(request);Objects.requireNonNull(companyDecision);Objects.requireNonNull(networkDecision);
            Objects.requireNonNull(bindingId);Objects.requireNonNull(sessionId);Objects.requireNonNull(userId);
            Objects.requireNonNull(sampledAt);Objects.requireNonNull(validUntil);
            if(!sampledAt.isBefore(validUntil) || backendPid<=0 || transactionId<=0)throw new IllegalArgumentException("Invalid draft observation");}
    }
}
