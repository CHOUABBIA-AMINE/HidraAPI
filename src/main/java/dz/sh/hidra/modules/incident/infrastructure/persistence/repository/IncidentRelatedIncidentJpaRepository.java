/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentRelatedIncidentJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for IncidentRelatedIncident.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.repository;

import dz.sh.hidra.modules.incident.infrastructure.persistence.entity.IncidentRelatedIncidentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for IncidentRelatedIncident.
 */
@Repository
public interface IncidentRelatedIncidentJpaRepository extends JpaRepository<IncidentRelatedIncidentJpaEntity, String> {
}
