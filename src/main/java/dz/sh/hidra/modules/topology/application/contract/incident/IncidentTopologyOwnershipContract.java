/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentTopologyOwnershipContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.incident
 * @Description : Current Incident-authorizable asset ownership scalar export.
 */
package dz.sh.hidra.modules.topology.application.contract.incident;

import java.util.List;
import java.util.Optional;
public interface IncidentTopologyOwnershipContract {
    record OwnedAsset(String type, String id, String companyId) {}
    Optional<OwnedAsset> currentOwner(String type,String id);
    List<OwnedAsset> boundedOwnedAssets();
}
