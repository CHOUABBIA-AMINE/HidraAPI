/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaDocumentCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for DocumentCatalogEntry.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.documents.application.port.out.DocumentCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.documents.domain.model.DocumentCatalogEntry;
import dz.sh.hidra.modules.documents.infrastructure.persistence.mapper.DocumentsPersistenceMapper;
import dz.sh.hidra.modules.documents.infrastructure.persistence.repository.DocumentCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for DocumentCatalogEntry.
 */
@Component
public class JpaDocumentCatalogEntryRepositoryAdapter implements DocumentCatalogEntryRepositoryPort {

    private final DocumentCatalogEntryJpaRepository repository;

    public JpaDocumentCatalogEntryRepositoryAdapter(DocumentCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "DocumentCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public DocumentCatalogEntry save(DocumentCatalogEntry model) {
        return DocumentsPersistenceMapper.toDomain(repository.save(DocumentsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<DocumentCatalogEntry> findById(String id) {
        return repository.findById(id).map(DocumentsPersistenceMapper::toDomain);
    }
}
