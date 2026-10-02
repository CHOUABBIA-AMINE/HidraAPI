/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrendAnalysisJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for TrendAnalysis.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.repository;

import dz.sh.hidra.modules.analytics.infrastructure.persistence.entity.TrendAnalysisJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for TrendAnalysis.
 */
@Repository
public interface TrendAnalysisJpaRepository extends JpaRepository<TrendAnalysisJpaEntity, String> {
}
