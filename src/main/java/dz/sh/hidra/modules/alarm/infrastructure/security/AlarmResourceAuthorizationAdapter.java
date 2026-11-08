/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Name        : AlarmResourceAuthorizationAdapter
 * @UpdatedOn   : 2026-11-08
 * @Type        : Class
 * @Layer       : Class
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.security
 * @Description : Joins native Identity authorization and current Topology company ownership.
 */
package dz.sh.hidra.modules.alarm.infrastructure.security;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmResourceAuthorizationPort;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmRepositoryPort;
import dz.sh.hidra.modules.alarm.domain.exception.AlarmResourceNotFoundException;
import dz.sh.hidra.modules.identity.application.contract.alarm.AlarmScopedAuthorizationContract;
import dz.sh.hidra.modules.topology.application.contract.alarm.AlarmTopologyOwnershipContract;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
@Component
public final class AlarmResourceAuthorizationAdapter implements AlarmResourceAuthorizationPort {
    private final AlarmTopologyOwnershipContract owners;
    private final AlarmScopedAuthorizationContract grants;
    private final AlarmRepositoryPort alarms;
    public AlarmResourceAuthorizationAdapter(AlarmTopologyOwnershipContract owners,
            AlarmScopedAuthorizationContract grants,AlarmRepositoryPort alarms) {
        this.owners=Objects.requireNonNull(owners);this.grants=Objects.requireNonNull(grants);
        this.alarms=Objects.requireNonNull(alarms);
    }
    @Override public List<AssetRef> permittedAssets(String permission) {
        return owners.boundedOwnedAssets().stream()
                .filter(o->grants.permitted(permission,o.companyPartyId(),o.typeCode(),o.assetId()))
                .map(o->new AssetRef(o.typeCode(),o.assetId())).toList();
    }
    @Override public void requireAsset(String permission,String type,String id) {
        var owner=owners.currentOwner(type,id).orElseThrow(AlarmResourceNotFoundException::new);
        if(!grants.permitted(permission,owner.companyPartyId(),owner.typeCode(),owner.assetId()))
            throw new AlarmResourceNotFoundException();
    }
    @Override public void requireAlarm(String permission,String id) {
        if(id==null||id.isBlank())throw new AlarmResourceNotFoundException();
        var alarm=alarms.findById(id).orElseThrow(AlarmResourceNotFoundException::new);
        requireAsset(permission,alarm.topologyAssetTypeCode(),alarm.topologyAssetId());
    }
}
