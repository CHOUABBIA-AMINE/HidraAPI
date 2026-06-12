/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentImpactAssessmentJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for IncidentImpactAssessment.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.repository;

import dz.sh.hidra.modules.incident.infrastructure.persistence.entity.IncidentImpactAssessmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for IncidentImpactAssessment.
 */
@Repository
public interface IncidentImpactAssessmentJpaRepository extends JpaRepository<IncidentImpactAssessmentJpaEntity, String> {
}
