/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionRuleJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for LeakDetectionRule.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository;

import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity.LeakDetectionRuleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for LeakDetectionRule.
 */
@Repository
public interface LeakDetectionRuleJpaRepository extends JpaRepository<LeakDetectionRuleJpaEntity, String> {
}
