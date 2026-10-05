/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsTopologyReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.assets
 *
 * @Description : Topology-owned typed asset reference contract exported to Assets.
 *
 */
package dz.sh.hidra.modules.topology.application.contract.assets;

import java.util.Optional;

public interface AssetsTopologyReferenceContract {

    Optional<ReferenceView> resolve(String topologyAssetTypeCode, String topologyAssetId);

    record ReferenceView(
            String id,
            String code,
            String name
    ) { }
}
