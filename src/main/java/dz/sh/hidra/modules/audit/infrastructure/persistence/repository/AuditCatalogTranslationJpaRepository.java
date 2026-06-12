/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditCatalogTranslationJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for AuditCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.repository;

import dz.sh.hidra.modules.audit.infrastructure.persistence.entity.AuditCatalogTranslationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for AuditCatalogTranslation.
 */
@Repository
public interface AuditCatalogTranslationJpaRepository extends JpaRepository<AuditCatalogTranslationJpaEntity, String> {
}
