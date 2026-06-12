/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentEvidenceLinkJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for IncidentEvidenceLink.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.repository;

import dz.sh.hidra.modules.incident.infrastructure.persistence.entity.IncidentEvidenceLinkJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for IncidentEvidenceLink.
 */
@Repository
public interface IncidentEvidenceLinkJpaRepository extends JpaRepository<IncidentEvidenceLinkJpaEntity, String> {
}
