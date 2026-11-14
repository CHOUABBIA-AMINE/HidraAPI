/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JpaIncidentTopologyOwnershipAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.reference
 * @Description : Typed current company ownership without Incident authorization.
 */
package dz.sh.hidra.modules.topology.infrastructure.reference;

import dz.sh.hidra.modules.topology.application.contract.incident.IncidentTopologyOwnershipContract;
import dz.sh.hidra.modules.topology.application.contract.alarm.AlarmTopologyOwnershipContract;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JpaIncidentTopologyOwnershipAdapter implements IncidentTopologyOwnershipContract {
    private final AlarmTopologyOwnershipContract owners;
    public JpaIncidentTopologyOwnershipAdapter(AlarmTopologyOwnershipContract owners) {
        this.owners=Objects.requireNonNull(owners);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<OwnedAsset> currentOwner(String type, String id) {
        return owners.currentOwner(type,id)
            .map(o->new OwnedAsset(o.typeCode(),o.assetId(),o.companyPartyId()));
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public List<OwnedAsset> boundedOwnedAssets() {
        return owners.boundedOwnedAssets().stream()
            .map(o->new OwnedAsset(o.typeCode(),o.assetId(),o.companyPartyId())).toList();
    }
}
