/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionProfileJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for LeakDetectionProfile.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository;

import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity.LeakDetectionProfileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for LeakDetectionProfile.
 */
@Repository
public interface LeakDetectionProfileJpaRepository extends JpaRepository<LeakDetectionProfileJpaEntity, String> {
}
