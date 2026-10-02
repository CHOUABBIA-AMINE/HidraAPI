/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaDocumentStorageObjectRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for DocumentStorageObject.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.documents.application.port.out.DocumentStorageObjectRepositoryPort;
import dz.sh.hidra.modules.documents.domain.model.DocumentStorageObject;
import dz.sh.hidra.modules.documents.infrastructure.persistence.mapper.DocumentsPersistenceMapper;
import dz.sh.hidra.modules.documents.infrastructure.persistence.repository.DocumentStorageObjectJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for DocumentStorageObject.
 */
@Component
public class JpaDocumentStorageObjectRepositoryAdapter implements DocumentStorageObjectRepositoryPort {

    private final DocumentStorageObjectJpaRepository repository;

    public JpaDocumentStorageObjectRepositoryAdapter(DocumentStorageObjectJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "DocumentStorageObjectJpaRepository must not be null.");
    }

    @Override
    public DocumentStorageObject save(DocumentStorageObject model) {
        return DocumentsPersistenceMapper.toDomain(repository.save(DocumentsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<DocumentStorageObject> findById(String id) {
        return repository.findById(id).map(DocumentsPersistenceMapper::toDomain);
    }
}
