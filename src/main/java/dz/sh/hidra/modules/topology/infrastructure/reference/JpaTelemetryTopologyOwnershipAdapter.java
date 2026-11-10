/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTelemetryTopologyOwnershipAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.reference
 *
 * @Description : Locks current Topology asset/company ownership for synthetic telemetry only.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.reference;


import dz.sh.hidra.modules.topology.application.contract.telemetry.TelemetryTopologyOwnershipContract;
import dz.sh.hidra.modules.topology.domain.value.FacilityStatus;
import dz.sh.hidra.modules.topology.domain.value.EquipmentStatus;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.EquipmentJpaEntity;
import dz.sh.hidra.modules.party.application.contract.topology.TopologyPartyReferenceContract;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JpaTelemetryTopologyOwnershipAdapter implements TelemetryTopologyOwnershipContract {
    private final EntityManager entityManager;
    private final TopologyPartyReferenceContract parties;
    public JpaTelemetryTopologyOwnershipAdapter(EntityManager entityManager,
            TopologyPartyReferenceContract parties) {
        this.entityManager=Objects.requireNonNull(entityManager);
        this.parties=Objects.requireNonNull(parties);
    }
    @Override
    @Transactional(propagation=Propagation.MANDATORY,readOnly=true)
    public Optional<OwnedAsset> currentOwner(String type,String id) {
        if(id==null||id.isBlank()||!id.equals(id.strip()))return Optional.empty();
        if("FACILITY".equals(type)) {
            var facility=entityManager.find(FacilityJpaEntity.class,id,LockModeType.PESSIMISTIC_READ);
            return facility==null?Optional.empty():owner(type,id,facility);
        }
        if("EQUIPMENT".equals(type)) {
            var equipment=entityManager.find(EquipmentJpaEntity.class,id,LockModeType.PESSIMISTIC_READ);
            if(equipment==null||equipment.status()!=EquipmentStatus.ACTIVE
                    ||equipment.facilityId()==null||equipment.facilityId().isBlank())return Optional.empty();
            var facility=entityManager.find(FacilityJpaEntity.class,equipment.facilityId(),
                    LockModeType.PESSIMISTIC_READ);
            return facility==null?Optional.empty():owner(type,id,facility);
        }
        return Optional.empty(); // Unsupported topology asset families are not assumed company-owned.
    }
    private Optional<OwnedAsset> owner(String type,String id,FacilityJpaEntity f) {
        if(f.status()!=FacilityStatus.ACTIVE || f.ownerPartyId()==null
                ||f.ownerPartyId().isBlank() || !parties.eligibleFacilityOwner(f.ownerPartyId()))
            return Optional.empty();
        return Optional.of(new OwnedAsset(type,id,f.ownerPartyId()));
    }
}
