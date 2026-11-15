/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JpaIntegrityMaintenanceTopologyOwnershipAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.reference
 * @Description : T18 native-scoped Integrity/Assets governed work-order operations.
 */
package dz.sh.hidra.modules.topology.infrastructure.reference;

import dz.sh.hidra.modules.topology.application.contract.integrity.IntegrityMaintenanceTopologyOwnershipContract;
import dz.sh.hidra.modules.topology.application.contract.alarm.AlarmTopologyOwnershipContract;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public final class JpaIntegrityMaintenanceTopologyOwnershipAdapter implements IntegrityMaintenanceTopologyOwnershipContract {
    private final AlarmTopologyOwnershipContract source;
    public JpaIntegrityMaintenanceTopologyOwnershipAdapter(AlarmTopologyOwnershipContract source){
        this.source=Objects.requireNonNull(source);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Owned> currentOwner(String type,String id){
        if(!"EQUIPMENT".equals(type))return Optional.empty();
        return source.currentOwner(type,id).map(a->new Owned(a.typeCode(),a.assetId(),a.companyPartyId()));
    }
}
