/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentEscalationAuthorizationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.security
 * @Description : Current company/asset scope and no protected Incident disclosure.
 */
package dz.sh.hidra.modules.incident.infrastructure.security;

import dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationAuthorizationPort;
import dz.sh.hidra.modules.incident.application.port.out.IncidentRepositoryPort;
import dz.sh.hidra.modules.identity.application.contract.incident.IncidentScopedAuthorizationContract;
import dz.sh.hidra.modules.topology.application.contract.incident.IncidentTopologyOwnershipContract;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class IncidentEscalationAuthorizationAdapter implements IncidentEscalationAuthorizationPort {
    private final IncidentScopedAuthorizationContract grants;
    private final IncidentTopologyOwnershipContract owners;
    private final IncidentRepositoryPort cases;
    public IncidentEscalationAuthorizationAdapter(IncidentScopedAuthorizationContract grants,
            IncidentTopologyOwnershipContract owners, IncidentRepositoryPort cases) {
        this.grants=Objects.requireNonNull(grants);
        this.owners=Objects.requireNonNull(owners);
        this.cases=Objects.requireNonNull(cases);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public List<AssetRef> permittedAssets(String permission) {
        return owners.boundedOwnedAssets().stream()
                .filter(o->grants.permitted(permission,o.companyId(),o.type(),o.id()))
                .map(o->new AssetRef(o.type(),o.id())).toList();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void requireAsset(String permission,String company,String type,String id) {
        var o=owners.currentOwner(type,id).orElseThrow(IncidentEscalationAuthorizationAdapter::hidden);
        if (!o.companyId().equals(company)||!grants.permitted(permission,o.companyId(),o.type(),o.id()))throw hidden();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void requireIncident(String permission,String incidentId) {
        var i=cases.findById(incidentId).orElseThrow(IncidentEscalationAuthorizationAdapter::hidden);
        var o=owners.currentOwner(i.topologyAssetTypeCode(),i.topologyAssetId())
                .orElseThrow(IncidentEscalationAuthorizationAdapter::hidden);
        requireAsset(permission,o.companyId(),o.type(),o.id());
    }
    private static NoSuchElementException hidden() {
        return new NoSuchElementException("Protected Incident unavailable.");
    }
}
