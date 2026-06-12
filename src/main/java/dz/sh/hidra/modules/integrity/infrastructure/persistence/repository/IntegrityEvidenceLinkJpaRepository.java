/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityEvidenceLinkJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for IntegrityEvidenceLink.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.repository;

import dz.sh.hidra.modules.integrity.infrastructure.persistence.entity.IntegrityEvidenceLinkJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for IntegrityEvidenceLink.
 */
@Repository
public interface IntegrityEvidenceLinkJpaRepository extends JpaRepository<IntegrityEvidenceLinkJpaEntity, String> {
}
