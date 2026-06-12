/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaDocumentAccessGrantRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for DocumentAccessGrant.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.documents.application.port.out.DocumentAccessGrantRepositoryPort;
import dz.sh.hidra.modules.documents.domain.model.DocumentAccessGrant;
import dz.sh.hidra.modules.documents.infrastructure.persistence.mapper.DocumentsPersistenceMapper;
import dz.sh.hidra.modules.documents.infrastructure.persistence.repository.DocumentAccessGrantJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for DocumentAccessGrant.
 */
@Component
public class JpaDocumentAccessGrantRepositoryAdapter implements DocumentAccessGrantRepositoryPort {

    private final DocumentAccessGrantJpaRepository repository;

    public JpaDocumentAccessGrantRepositoryAdapter(DocumentAccessGrantJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "DocumentAccessGrantJpaRepository must not be null.");
    }

    @Override
    public DocumentAccessGrant save(DocumentAccessGrant model) {
        return DocumentsPersistenceMapper.toDomain(repository.save(DocumentsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<DocumentAccessGrant> findById(String id) {
        return repository.findById(id).map(DocumentsPersistenceMapper::toDomain);
    }
}
