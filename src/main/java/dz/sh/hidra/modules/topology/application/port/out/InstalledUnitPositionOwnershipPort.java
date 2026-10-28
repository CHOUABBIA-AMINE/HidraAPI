/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitPositionOwnershipPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Defines opaque current position and direct facility owner evidence.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.util.List;

/** Topology-private owner observations; never a public permission or transferable attestation. */
public interface InstalledUnitPositionOwnershipPort {
    Admission acquire(String exactPositionId);
    Contribution read(Admission admission);
    void requireCurrent(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
    interface Admission {}
    record Contribution(String positionId, String equipmentTypeId, String facilityId,
            String companyPartyId, String equipmentStatus, String facilityStatus, String facilityKind,
            long facilityOwnerGeneration, int backendPid, long transactionId, String databaseRole) {
        public Contribution {
            for(String id:new String[]{positionId,equipmentTypeId,facilityId,companyPartyId,
                    equipmentStatus,facilityStatus,facilityKind,databaseRole}) exact(id);
            if(facilityOwnerGeneration<=0 || backendPid<=0 || transactionId<=0)
                throw new IllegalArgumentException("Positive actual owner coordinates required");
        }
        @Override public String toString() { return "Contribution[Topology position owner scalars]"; }
    }
    private static void exact(String value) {
        if(value==null || value.isBlank() || !value.equals(value.strip()) || value.codePointCount(0,value.length())>80)
            throw new IllegalArgumentException("Exact bounded owner reference required");
    }
}
