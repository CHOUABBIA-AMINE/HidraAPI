/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentCatalogEntryJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for DocumentCatalogEntry.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.repository;

import dz.sh.hidra.modules.documents.infrastructure.persistence.entity.DocumentCatalogEntryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for DocumentCatalogEntry.
 */
@Repository
public interface DocumentCatalogEntryJpaRepository extends JpaRepository<DocumentCatalogEntryJpaEntity, String> {
}
