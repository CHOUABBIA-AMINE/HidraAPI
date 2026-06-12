/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FeatureFlagRuleJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for FeatureFlagRule.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.repository;

import dz.sh.hidra.modules.configuration.infrastructure.persistence.entity.FeatureFlagRuleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for FeatureFlagRule.
 */
@Repository
public interface FeatureFlagRuleJpaRepository extends JpaRepository<FeatureFlagRuleJpaEntity, String> {
}
