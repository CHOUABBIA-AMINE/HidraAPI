/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyBaselineProductSourceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.contract.topology
 *
 * @Description : Exports exact product publication facts without detached protected execution.
 *
 */
package dz.sh.hidra.modules.custody.application.contract.topology;


import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.time.Instant;
import java.util.*;

public interface TopologyBaselineProductSourceContract {
    record Request(String companyPartyId,UUID draftId,String catalogProductId,UUID publicationId,
                   String sha256,Instant effectiveFrom) {
        public Request {
            Objects.requireNonNull(companyPartyId);
            Objects.requireNonNull(draftId);
            Objects.requireNonNull(catalogProductId);
            Objects.requireNonNull(publicationId);
            Objects.requireNonNull(sha256);
            if(effectiveFrom==null || effectiveFrom.getNano()%1000!=0)
                throw new IllegalArgumentException("Exact baseline effective instant required");
        }
    }
    record Original(int backendPid,long transactionId,String databaseRole,Instant sampledAt,Instant validUntil) {}
    record Facts(Instant effectiveFrom,Instant effectiveUntil,boolean currentlyEligible,
                 boolean methodSupplied,boolean inputsComplete,boolean approved) {}
    interface Admission {}
    Admission acquire(Request request,Original original);
    Facts read(Admission admission);
    void requireCurrent(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
}
