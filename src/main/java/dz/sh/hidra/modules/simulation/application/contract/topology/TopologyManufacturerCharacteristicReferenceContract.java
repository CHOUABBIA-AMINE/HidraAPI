/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyManufacturerCharacteristicReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.contract.topology
 *
 * @Description : Simulation-owned exact source applicability and live Party eligibility verification.
 *
 */
package dz.sh.hidra.modules.simulation.application.contract.topology;

import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/** Only verified opaque original-transaction tickets may support a Topology draft. */
public interface TopologyManufacturerCharacteristicReferenceContract {
    record Requested(String companyPartyId,String sourceId,String revisionId,
                     String sourceSha256,String kind,Instant effectiveFrom,Instant effectiveUntil) {
        public Requested {
            exact(companyPartyId);exact(sourceId);exact(revisionId);
            if(sourceSha256==null||!sourceSha256.matches("[0-9a-f]{64}")
                    ||!java.util.Set.of("COMPRESSOR_MAP","VALVE_MAP","PARAMETER_TABLE","OPERATING_LIMITS").contains(kind)
                    ||effectiveFrom==null ||effectiveUntil!=null&&!effectiveUntil.isAfter(effectiveFrom))
                throw new IllegalArgumentException("Invalid manufacturer source evidence descriptor");
        }
        @Override public String toString(){return "ManufacturerSourceReference[redacted]";}
    }
    record Original(String companyPartyId,String bindingId,String userId,int backendPid,long transactionId,
                    String databaseRole,Instant sampledAt,Instant validUntil){
        public Original{
            exact(companyPartyId);exact(bindingId);exact(userId);exact(databaseRole);
            Objects.requireNonNull(sampledAt);Objects.requireNonNull(validUntil);
            if(backendPid<=0||transactionId<=0||!sampledAt.isBefore(validUntil))
                throw new IllegalArgumentException("Invalid original source transaction");
        }
    }
    record Verified(String companyPartyId,String sourceId,String revisionId,String payloadSha256,String kind,
                    int backendPid,long transactionId,String databaseRole){
        public Verified{
            exact(companyPartyId);exact(sourceId);exact(revisionId);exact(databaseRole);
            if(payloadSha256==null||!payloadSha256.matches("[0-9a-f]{64}")||backendPid<=0||transactionId<=0)
                throw new IllegalArgumentException("Unqualified source descriptor");
        }
        @Override public String toString(){return "VerifiedManufacturerReference[redacted]";}
    }
    interface Admission{}
    Admission acquire(Requested request,Original original);
    Verified verify(Admission admission);
    void requireCurrent(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
    private static void exact(String value){
        if(value==null||value.isBlank()||!value.equals(value.strip())
                ||value.codePointCount(0,value.length())>80
                ||value.codePoints().anyMatch(c->Character.isISOControl(c)||c>=0xd800&&c<=0xdfff))
            throw new IllegalArgumentException("Exact bounded identity required");
    }
}
