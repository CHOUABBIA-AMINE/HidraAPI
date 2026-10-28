/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyInstalledUnitCompanyEvidenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.contract.topology
 *
 * @Description : Exports current company Party evidence through private transaction-bound tickets.
 *
 */
package dz.sh.hidra.modules.party.application.contract.topology;

import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.util.List;

/** Party evidence only; not an Identity permission, manufacturer classification or bearer proof. */
public interface TopologyInstalledUnitCompanyEvidenceContract {
    Admission acquire(String exactCompanyPartyId);
    Contribution read(Admission admission);
    void requireCurrent(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
    interface Admission {}
    record Contribution(String companyPartyId, String partyStatus, long partyGeneration,
            int backendPid, long transactionId, String databaseRole) {
        public Contribution {
            for(String value : new String[]{companyPartyId,databaseRole}) {
                if(value==null || value.isBlank() || !value.equals(value.strip())
                    || value.codePointCount(0,value.length())>80)
                    throw new IllegalArgumentException("Exact bounded company coordinates required");
            }
            if(!"ACTIVE".equals(partyStatus) || partyGeneration<=0 || backendPid<=0 || transactionId<=0)
                throw new IllegalArgumentException("Current positive company evidence required");
        }
        @Override public String toString(){return "Contribution[Party company scalars]";}
    }
}
