/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleAssetOwnershipContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.monitoring
 *
 * @Description : Exports current authoritative Monitoring asset and company ownership.
 *
 */
package dz.sh.hidra.modules.topology.application.contract.monitoring;

import java.util.Optional;

public interface MonitoringRuleAssetOwnershipContract {
    record OwnedAsset(String type,String id,String companyPartyId){ }
    Optional<OwnedAsset> current(String type,String id);
}
