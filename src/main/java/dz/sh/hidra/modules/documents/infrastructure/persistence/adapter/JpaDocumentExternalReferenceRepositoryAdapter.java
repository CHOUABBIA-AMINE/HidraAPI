/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaDocumentExternalReferenceRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for DocumentExternalReference.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.documents.application.port.out.DocumentExternalReferenceRepositoryPort;
import dz.sh.hidra.modules.documents.domain.model.DocumentExternalReference;
import dz.sh.hidra.modules.documents.infrastructure.persistence.mapper.DocumentsPersistenceMapper;
import dz.sh.hidra.modules.documents.infrastructure.persistence.repository.DocumentExternalReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for DocumentExternalReference.
 */
@Component
public class JpaDocumentExternalReferenceRepositoryAdapter implements DocumentExternalReferenceRepositoryPort {

    private final DocumentExternalReferenceJpaRepository repository;

    public JpaDocumentExternalReferenceRepositoryAdapter(DocumentExternalReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "DocumentExternalReferenceJpaRepository must not be null.");
    }

    @Override
    public DocumentExternalReference save(DocumentExternalReference model) {
        return DocumentsPersistenceMapper.toDomain(repository.save(DocumentsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<DocumentExternalReference> findById(String id) {
        return repository.findById(id).map(DocumentsPersistenceMapper::toDomain);
    }
}
