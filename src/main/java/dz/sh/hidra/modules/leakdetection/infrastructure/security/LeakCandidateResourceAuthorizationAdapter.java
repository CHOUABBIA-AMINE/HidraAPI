/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakCandidateResourceAuthorizationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.security
 * @Description : T13 exact owner contract and fail-closed source/asset authority.
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.security;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakCandidateResourceAuthorizationPort;
import dz.sh.hidra.modules.leakdetection.application.port.out.LeakCandidateRepositoryPort;
import dz.sh.hidra.modules.identity.application.contract.leakdetection.LeakDetectionScopedAuthorizationContract;
import dz.sh.hidra.modules.topology.application.contract.leakdetection.LeakDetectionTopologyOwnershipContract;
import java.util.List;
import java.util.Objects;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LeakCandidateResourceAuthorizationAdapter implements LeakCandidateResourceAuthorizationPort {
    private final LeakDetectionScopedAuthorizationContract grants;
    private final LeakDetectionTopologyOwnershipContract owners;
    private final LeakCandidateRepositoryPort candidates;
    public LeakCandidateResourceAuthorizationAdapter(LeakDetectionScopedAuthorizationContract grants,
            LeakDetectionTopologyOwnershipContract owners, LeakCandidateRepositoryPort candidates) {
        this.grants=Objects.requireNonNull(grants);this.owners=Objects.requireNonNull(owners);
        this.candidates=Objects.requireNonNull(candidates);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public List<AssetRef> permittedAssets(String permission) {
        return owners.boundedOwnedAssets().stream()
                .filter(o->grants.permitted(permission,o.companyPartyId(),o.type(),o.id()))
                .map(o->new AssetRef(o.type(),o.id())).toList();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public String requireAsset(String permission,String type,String id) {
        var owner=owners.currentOwner(type,id).orElseThrow(LeakCandidateResourceAuthorizationAdapter::unavailable);
        if(!grants.permitted(permission,owner.companyPartyId(),owner.type(),owner.id()))throw unavailable();
        return grants.authenticatedActor();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public String requireSource(String permission,String company,String type,String id) {
        var owner=owners.currentOwner(type,id).orElseThrow(LeakCandidateResourceAuthorizationAdapter::unavailable);
        if(!owner.companyPartyId().equals(company))throw unavailable();
        return requireAsset(permission,type,id);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void requireCandidate(String permission,String id) {
        if(id==null||id.isBlank())throw unavailable();
        var c=candidates.findById(id).orElseThrow(LeakCandidateResourceAuthorizationAdapter::unavailable);
        requireAsset(permission,c.topologyAssetType(),c.topologyAssetId());
    }
    private static NoSuchElementException unavailable() {
        return new NoSuchElementException("Protected Leak candidate unavailable.");
    }
}
