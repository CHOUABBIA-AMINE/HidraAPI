/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakDetectionTopologyOwnershipContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.leakdetection
 * @Description : T13 exact owner contract and fail-closed source/asset authority.
 */
package dz.sh.hidra.modules.topology.application.contract.leakdetection;

import java.util.List;
import java.util.Optional;

/** Current eligible company and physical asset, without granting rights. */
public interface LeakDetectionTopologyOwnershipContract {
    record OwnedAsset(String type, String id, String companyPartyId) { }
    Optional<OwnedAsset> currentOwner(String type, String id);
    List<OwnedAsset> boundedOwnedAssets();
}
