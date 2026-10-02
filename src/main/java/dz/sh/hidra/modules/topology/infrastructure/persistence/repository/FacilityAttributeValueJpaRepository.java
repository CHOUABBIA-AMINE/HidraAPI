/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityAttributeValueJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for FacilityAttributeValue.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.repository;

import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityAttributeValueJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FacilityAttributeValueJpaRepository extends JpaRepository<FacilityAttributeValueJpaEntity, String> { }
