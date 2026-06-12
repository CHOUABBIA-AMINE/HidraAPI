/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakLocalizationEstimateJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for LeakLocalizationEstimate.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository;

import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity.LeakLocalizationEstimateJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for LeakLocalizationEstimate.
 */
@Repository
public interface LeakLocalizationEstimateJpaRepository extends JpaRepository<LeakLocalizationEstimateJpaEntity, String> {
}
