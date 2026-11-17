/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JpaRiskAssessmentAssetOwnershipAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.reference
 * @Description : Locks current active Topology facility ownership for Risk.
 */
package dz.sh.hidra.modules.topology.infrastructure.reference;

import dz.sh.hidra.modules.topology.application.contract.risk.RiskAssessmentAssetOwnershipContract;
import dz.sh.hidra.modules.topology.domain.value.FacilityStatus;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JpaRiskAssessmentAssetOwnershipAdapter implements RiskAssessmentAssetOwnershipContract {
    private final EntityManager em;
    public JpaRiskAssessmentAssetOwnershipAdapter(EntityManager em) { this.em=em; }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Facility> currentFacility(String id) {
        if (id==null||id.isBlank())return Optional.empty();
        var f=em.find(FacilityJpaEntity.class,id,LockModeType.PESSIMISTIC_READ);
        if(f==null||f.status()!=FacilityStatus.ACTIVE||f.ownerPartyId()==null
                ||f.ownerPartyId().isBlank()||f.retiredAt()!=null)return Optional.empty();
        return Optional.of(new Facility(f.id(),f.ownerPartyId(),f.code()));
    }
}
