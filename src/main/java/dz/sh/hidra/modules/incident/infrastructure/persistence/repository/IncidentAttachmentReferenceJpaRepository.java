/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentAttachmentReferenceJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for IncidentAttachmentReference.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.repository;

import dz.sh.hidra.modules.incident.infrastructure.persistence.entity.IncidentAttachmentReferenceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for IncidentAttachmentReference.
 */
@Repository
public interface IncidentAttachmentReferenceJpaRepository extends JpaRepository<IncidentAttachmentReferenceJpaEntity, String> {
}
