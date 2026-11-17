/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JpaControlledDocumentFacilityOwnershipAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.reference
 * @Description : Current facility owner resolved via Topology JPA and scoped read/write locks.
 */
package dz.sh.hidra.modules.topology.infrastructure.reference;

import dz.sh.hidra.modules.topology.application.contract.documents.ControlledDocumentFacilityOwnershipContract;
import dz.sh.hidra.modules.topology.domain.value.FacilityStatus;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class JpaControlledDocumentFacilityOwnershipAdapter
        implements ControlledDocumentFacilityOwnershipContract {
    private final EntityManager em;
    public JpaControlledDocumentFacilityOwnershipAdapter(EntityManager em){this.em=em;}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Facility> currentFacility(String facilityId){
        if(facilityId==null||facilityId.isBlank())return Optional.empty();
        boolean readOnly=TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        var f=readOnly ? em.find(FacilityJpaEntity.class,facilityId)
                : em.find(FacilityJpaEntity.class,facilityId,LockModeType.PESSIMISTIC_READ);
        if(f==null||f.status()!=FacilityStatus.ACTIVE||f.retiredAt()!=null
                ||f.ownerPartyId()==null||f.ownerPartyId().isBlank())return Optional.empty();
        return Optional.of(new Facility(f.id(),f.ownerPartyId(),f.code(),f.code()));
    }
}
