/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringCatalogEntryJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for MonitoringCatalogEntry.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository;

import dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity.MonitoringCatalogEntryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for MonitoringCatalogEntry.
 */
@Repository
public interface MonitoringCatalogEntryJpaRepository extends JpaRepository<MonitoringCatalogEntryJpaEntity, String> {
}
