/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentCatalogEntryJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for IncidentCatalogEntry.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.repository;

import dz.sh.hidra.modules.incident.infrastructure.persistence.entity.IncidentCatalogEntryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for IncidentCatalogEntry.
 */
@Repository
public interface IncidentCatalogEntryJpaRepository extends JpaRepository<IncidentCatalogEntryJpaEntity, String> {
}
