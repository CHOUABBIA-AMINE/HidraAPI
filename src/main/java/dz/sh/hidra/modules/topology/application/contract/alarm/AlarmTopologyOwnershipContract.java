/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Name        : AlarmTopologyOwnershipContract
 * @UpdatedOn   : 2026-11-08
 * @Type        : Interface
 * @Layer       : Interface
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.alarm
 * @Description : Exports current Topology-owned asset and company identity, not permission.
 */
package dz.sh.hidra.modules.topology.application.contract.alarm;

import java.util.List;
import java.util.Optional;
public interface AlarmTopologyOwnershipContract {
    record OwnedAsset(String typeCode,String assetId,String companyPartyId) {
        public OwnedAsset {
            if(typeCode==null||typeCode.isBlank()||assetId==null||assetId.isBlank()
                 ||companyPartyId==null||companyPartyId.isBlank())
                throw new IllegalArgumentException("Current owned asset identity required.");
        }
    }
    Optional<OwnedAsset> currentOwner(String typeCode,String assetId);
    List<OwnedAsset> boundedOwnedAssets();
}
