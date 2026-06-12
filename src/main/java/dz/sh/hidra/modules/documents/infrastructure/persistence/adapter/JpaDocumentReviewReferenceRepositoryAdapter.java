/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaDocumentReviewReferenceRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for DocumentReviewReference.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.documents.application.port.out.DocumentReviewReferenceRepositoryPort;
import dz.sh.hidra.modules.documents.domain.model.DocumentReviewReference;
import dz.sh.hidra.modules.documents.infrastructure.persistence.mapper.DocumentsPersistenceMapper;
import dz.sh.hidra.modules.documents.infrastructure.persistence.repository.DocumentReviewReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for DocumentReviewReference.
 */
@Component
public class JpaDocumentReviewReferenceRepositoryAdapter implements DocumentReviewReferenceRepositoryPort {

    private final DocumentReviewReferenceJpaRepository repository;

    public JpaDocumentReviewReferenceRepositoryAdapter(DocumentReviewReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "DocumentReviewReferenceJpaRepository must not be null.");
    }

    @Override
    public DocumentReviewReference save(DocumentReviewReference model) {
        return DocumentsPersistenceMapper.toDomain(repository.save(DocumentsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<DocumentReviewReference> findById(String id) {
        return repository.findById(id).map(DocumentsPersistenceMapper::toDomain);
    }
}
