/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaDocumentCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for DocumentCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.documents.application.port.out.DocumentCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.documents.domain.model.DocumentCatalogTranslation;
import dz.sh.hidra.modules.documents.infrastructure.persistence.mapper.DocumentsPersistenceMapper;
import dz.sh.hidra.modules.documents.infrastructure.persistence.repository.DocumentCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for DocumentCatalogTranslation.
 */
@Component
public class JpaDocumentCatalogTranslationRepositoryAdapter implements DocumentCatalogTranslationRepositoryPort {

    private final DocumentCatalogTranslationJpaRepository repository;

    public JpaDocumentCatalogTranslationRepositoryAdapter(DocumentCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "DocumentCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public DocumentCatalogTranslation save(DocumentCatalogTranslation model) {
        return DocumentsPersistenceMapper.toDomain(repository.save(DocumentsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<DocumentCatalogTranslation> findById(String id) {
        return repository.findById(id).map(DocumentsPersistenceMapper::toDomain);
    }
}
