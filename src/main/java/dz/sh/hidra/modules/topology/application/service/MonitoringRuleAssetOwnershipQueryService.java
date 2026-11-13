/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleAssetOwnershipQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Reuses Topology's locked active facility and equipment ownership contract.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.contract.monitoring.MonitoringRuleAssetOwnershipContract;
import dz.sh.hidra.modules.topology.application.contract.telemetry.TelemetryTopologyOwnershipContract;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public final class MonitoringRuleAssetOwnershipQueryService implements MonitoringRuleAssetOwnershipContract {
    private final TelemetryTopologyOwnershipContract topology;
    public MonitoringRuleAssetOwnershipQueryService(TelemetryTopologyOwnershipContract topology){
        this.topology=Objects.requireNonNull(topology);
    }
    @Override public Optional<OwnedAsset> current(String type,String id){
        return topology.currentOwner(type,id)
            .map(a->new OwnedAsset(a.typeCode(),a.assetId(),a.companyPartyId()));
    }
}
