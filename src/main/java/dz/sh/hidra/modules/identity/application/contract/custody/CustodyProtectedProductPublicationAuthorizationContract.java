/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyProtectedProductPublicationAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.custody
 *
 * @Description : Exports distinct company and product publication authorization and protected human reviewer facts.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.custody;

import java.time.Instant;
import java.util.*;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;

/** Scalar exported observations never substitute for the issuing owner's opaque admission. */
public interface CustodyProtectedProductPublicationAuthorizationContract {
    enum Action { READ, PREPARE, PUBLISH, WITHDRAW, REACTIVATE }
    enum Decision { PERMIT, DENY, INDETERMINATE }
    record Request(Action action,String companyPartyId,String catalogProductId){
        public Request {Objects.requireNonNull(action);exact(companyPartyId);exact(catalogProductId);}
        private static void exact(String s){if(s==null || s.isBlank() || !s.equals(s.strip()) || s.indexOf(0)>=0 || s.codePointCount(0,s.length())>80)
            throw new IllegalArgumentException("Exact publication scope required");
            for(int n=0;n<s.length();n++){char c=s.charAt(n);if(Character.isHighSurrogate(c)){
                if(++n>=s.length() || !Character.isLowSurrogate(s.charAt(n)))throw new IllegalArgumentException("Invalid publication scope Unicode");
            }else if(Character.isLowSurrogate(c))throw new IllegalArgumentException("Invalid publication scope Unicode");}}
    }
    interface Prepared {}
    interface Admission {}
    Prepared prepare();
    Admission acquire(Prepared prepared,Request request);
    Contribution contribute(Admission admission);
    void requireCurrent(Admission admission);
    Reviewer reviewer(Admission admission,String actorId);
    record Reviewer(String actorId,String displayName,Instant sampledAt) {}
    List<ProtectedRelationFence> finalFences(Admission admission);
    static String permissionCode(Action action) {
        return "custody:engineering-product-publication:" + Objects.requireNonNull(action).name().toLowerCase(Locale.ROOT);
    }
    static String resourceType() { return "PRODUCT_ENGINEERING_PUBLICATION"; }
    record Contribution(Request request,Decision companyDecision,Decision productDecision,
            String bindingId,String sessionId,String userId,Instant sampledAt,Instant validUntil,int backendPid,long transactionId){
        public Contribution {Objects.requireNonNull(request);Objects.requireNonNull(companyDecision);Objects.requireNonNull(productDecision);
            Objects.requireNonNull(bindingId);Objects.requireNonNull(sessionId);Objects.requireNonNull(userId);
            Objects.requireNonNull(sampledAt);Objects.requireNonNull(validUntil);
            if(!sampledAt.isBefore(validUntil) || backendPid<=0 || transactionId<=0)throw new IllegalArgumentException("Invalid publication observation");}
    }
}
