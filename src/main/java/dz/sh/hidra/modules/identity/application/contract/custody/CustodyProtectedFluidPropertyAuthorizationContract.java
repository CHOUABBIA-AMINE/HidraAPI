/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyProtectedFluidPropertyAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.custody
 *
 * @Description : Implements the bounded company and catalog-supplied fluid property authorization contract.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.custody;

import java.time.Instant;
import java.util.*;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;

/** Scalar exported observations never substitute for the issuing owner's opaque admission. */
public interface CustodyProtectedFluidPropertyAuthorizationContract {
    enum Action { READ, WRITE }
    enum Decision { PERMIT, DENY, INDETERMINATE }
    record Request(Action action,String companyPartyId,String catalogProductId){
        public Request {Objects.requireNonNull(action);exact(companyPartyId);exact(catalogProductId);}
        private static void exact(String s){if(s==null || s.isBlank() || !s.equals(s.strip()) || s.indexOf(0)>=0 || s.codePointCount(0,s.length())>80)
            throw new IllegalArgumentException("Exact draft scope required");
            for(int n=0;n<s.length();n++){char c=s.charAt(n);if(Character.isHighSurrogate(c)){
                if(++n>=s.length() || !Character.isLowSurrogate(s.charAt(n)))throw new IllegalArgumentException("Invalid fluid scope Unicode");
            }else if(Character.isLowSurrogate(c))throw new IllegalArgumentException("Invalid fluid scope Unicode");}}
    }
    interface Prepared {}
    interface Admission {}
    Prepared prepare();
    Admission acquire(Prepared prepared,Request request);
    Contribution contribute(Admission admission);
    void requireCurrent(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
    static String permissionCode(Action action) {
        return "custody:engineering-product-properties:" + Objects.requireNonNull(action).name().toLowerCase(Locale.ROOT);
    }
    static String resourceType() { return "PRODUCT_ENGINEERING_PROPERTIES"; }
    record Contribution(Request request,Decision companyDecision,Decision productDecision,
            String bindingId,String sessionId,String userId,Instant sampledAt,Instant validUntil,int backendPid,long transactionId){
        public Contribution {Objects.requireNonNull(request);Objects.requireNonNull(companyDecision);Objects.requireNonNull(productDecision);
            Objects.requireNonNull(bindingId);Objects.requireNonNull(sessionId);Objects.requireNonNull(userId);
            Objects.requireNonNull(sampledAt);Objects.requireNonNull(validUntil);
            if(!sampledAt.isBefore(validUntil) || backendPid<=0 || transactionId<=0)throw new IllegalArgumentException("Invalid draft observation");}
    }
}
