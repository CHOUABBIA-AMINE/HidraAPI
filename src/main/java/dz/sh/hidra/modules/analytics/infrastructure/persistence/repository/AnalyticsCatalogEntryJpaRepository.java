/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsCatalogEntryJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for AnalyticsCatalogEntry.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.repository;

import dz.sh.hidra.modules.analytics.infrastructure.persistence.entity.AnalyticsCatalogEntryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for AnalyticsCatalogEntry.
 */
@Repository
public interface AnalyticsCatalogEntryJpaRepository extends JpaRepository<AnalyticsCatalogEntryJpaEntity, String> {
}
