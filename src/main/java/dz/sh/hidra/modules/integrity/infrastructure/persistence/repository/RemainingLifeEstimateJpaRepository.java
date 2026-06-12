/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RemainingLifeEstimateJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for RemainingLifeEstimate.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.repository;

import dz.sh.hidra.modules.integrity.infrastructure.persistence.entity.RemainingLifeEstimateJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for RemainingLifeEstimate.
 */
@Repository
public interface RemainingLifeEstimateJpaRepository extends JpaRepository<RemainingLifeEstimateJpaEntity, String> {
}
