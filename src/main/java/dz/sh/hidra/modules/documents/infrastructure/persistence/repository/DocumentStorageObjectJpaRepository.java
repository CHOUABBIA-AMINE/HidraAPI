/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentStorageObjectJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for DocumentStorageObject.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.repository;

import dz.sh.hidra.modules.documents.infrastructure.persistence.entity.DocumentStorageObjectJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for DocumentStorageObject.
 */
@Repository
public interface DocumentStorageObjectJpaRepository extends JpaRepository<DocumentStorageObjectJpaEntity, String> {

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM hidra_documents_catalog_entry
                WHERE id = :storageProviderId
                  AND catalog_name = 'DOCUMENT_STORAGE_PROVIDER'
                  AND active = TRUE
            )
            """, nativeQuery = true)
    boolean existsActiveStorageProviderById(@Param("storageProviderId") String storageProviderId);
}
