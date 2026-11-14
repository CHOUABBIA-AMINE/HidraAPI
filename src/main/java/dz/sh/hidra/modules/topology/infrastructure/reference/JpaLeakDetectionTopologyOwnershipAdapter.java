/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JpaLeakDetectionTopologyOwnershipAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.reference
 * @Description : T13 exact owner contract and fail-closed source/asset authority.
 */
package dz.sh.hidra.modules.topology.infrastructure.reference;

import dz.sh.hidra.modules.topology.application.contract.leakdetection.LeakDetectionTopologyOwnershipContract;
import dz.sh.hidra.modules.topology.application.contract.alarm.AlarmTopologyOwnershipContract;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** Topology reuses its existing lock-protected active facility/equipment owner resolution. */
@Component
public class JpaLeakDetectionTopologyOwnershipAdapter implements LeakDetectionTopologyOwnershipContract {
    private final AlarmTopologyOwnershipContract source;
    public JpaLeakDetectionTopologyOwnershipAdapter(AlarmTopologyOwnershipContract source) {
        this.source=Objects.requireNonNull(source);
    }
    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public Optional<OwnedAsset> currentOwner(String type,String id) {
        return source.currentOwner(type,id).map(s->new OwnedAsset(s.typeCode(),s.assetId(),s.companyPartyId()));
    }
    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public List<OwnedAsset> boundedOwnedAssets() {
        return source.boundedOwnedAssets().stream()
            .map(s->new OwnedAsset(s.typeCode(),s.assetId(),s.companyPartyId())).toList();
    }
}
