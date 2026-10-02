/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsModelRunJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for AnalyticsModelRun.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.repository;

import dz.sh.hidra.modules.analytics.infrastructure.persistence.entity.AnalyticsModelRunJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for AnalyticsModelRun.
 */
@Repository
public interface AnalyticsModelRunJpaRepository extends JpaRepository<AnalyticsModelRunJpaEntity, String> {
}
