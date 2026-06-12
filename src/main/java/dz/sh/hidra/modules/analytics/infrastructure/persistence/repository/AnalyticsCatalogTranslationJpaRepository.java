/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsCatalogTranslationJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for AnalyticsCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.repository;

import dz.sh.hidra.modules.analytics.infrastructure.persistence.entity.AnalyticsCatalogTranslationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for AnalyticsCatalogTranslation.
 */
@Repository
public interface AnalyticsCatalogTranslationJpaRepository extends JpaRepository<AnalyticsCatalogTranslationJpaEntity, String> {
}
