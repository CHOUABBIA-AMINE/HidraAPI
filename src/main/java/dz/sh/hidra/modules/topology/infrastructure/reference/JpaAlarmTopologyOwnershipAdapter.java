/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Name        : JpaAlarmTopologyOwnershipAdapter
 * @UpdatedOn   : 2026-11-08
 * @Type        : Class
 * @Layer       : Class
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.reference
 * @Description : Exports only currently eligible active facility/equipment owner mappings.
 */
package dz.sh.hidra.modules.topology.infrastructure.reference;

import dz.sh.hidra.modules.topology.application.contract.alarm.AlarmTopologyOwnershipContract;
import dz.sh.hidra.modules.topology.domain.value.FacilityStatus;
import dz.sh.hidra.modules.topology.domain.value.EquipmentStatus;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.EquipmentJpaEntity;
import dz.sh.hidra.modules.party.application.contract.topology.TopologyPartyReferenceContract;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class JpaAlarmTopologyOwnershipAdapter implements AlarmTopologyOwnershipContract {
    private static final int MAX_SCOPED_ASSETS = 500;
    private final EntityManager em;
    private final TopologyPartyReferenceContract parties;
    public JpaAlarmTopologyOwnershipAdapter(EntityManager em,TopologyPartyReferenceContract parties) {
        this.em=Objects.requireNonNull(em);this.parties=Objects.requireNonNull(parties);
    }
    @Override public Optional<OwnedAsset> currentOwner(String type,String id) {
        if(id==null||id.isBlank()||!id.equals(id.trim()))return Optional.empty();
        if("FACILITY".equals(type)){
            var f=em.find(FacilityJpaEntity.class,id);
            return f==null?Optional.empty():owner(type,id,f);
        }
        if("EQUIPMENT".equals(type)){
            var e=em.find(EquipmentJpaEntity.class,id);
            if(e==null||e.status()!=EquipmentStatus.ACTIVE
                ||e.facilityId()==null||e.facilityId().isBlank())return Optional.empty();
            var f=em.find(FacilityJpaEntity.class,e.facilityId());
            return f==null?Optional.empty():owner(type,id,f);
        }
        return Optional.empty(); // Pipeline/system/unbound mappings are not authoritative.
    }
    private Optional<OwnedAsset> owner(String type,String id,FacilityJpaEntity f) {
        if(f.status()!=FacilityStatus.ACTIVE||f.ownerPartyId()==null
                ||f.ownerPartyId().isBlank()||!parties.eligibleFacilityOwner(f.ownerPartyId()))
            return Optional.empty();
        return Optional.of(new OwnedAsset(type,id,f.ownerPartyId()));
    }
    @Override public List<OwnedAsset> boundedOwnedAssets(){
        var facilities=em.createQuery("select f from FacilityJpaEntity f where f.status = :status order by f.id",FacilityJpaEntity.class)
                .setParameter("status",FacilityStatus.ACTIVE).setMaxResults(MAX_SCOPED_ASSETS+1).getResultList();
        if(facilities.size()>MAX_SCOPED_ASSETS)throw new IllegalStateException("Alarm owner catalog exceeds limit.");
        var equipment=em.createQuery("select e from EquipmentJpaEntity e where e.status = :status order by e.id",EquipmentJpaEntity.class)
                .setParameter("status",EquipmentStatus.ACTIVE).setMaxResults(MAX_SCOPED_ASSETS+1).getResultList();
        if(facilities.size()+equipment.size()>MAX_SCOPED_ASSETS)
            throw new IllegalStateException("Alarm owner catalog exceeds limit.");
        var result=new ArrayList<OwnedAsset>();
        for(var f:facilities)owner("FACILITY",f.id(),f).ifPresent(result::add);
        for(var e:equipment) {
            if(e.facilityId()==null||e.facilityId().isBlank())continue;
            var f=em.find(FacilityJpaEntity.class,e.facilityId());
            if(f!=null)owner("EQUIPMENT",e.id(),f).ifPresent(result::add);
        }
        return List.copyOf(result);
    }
}
