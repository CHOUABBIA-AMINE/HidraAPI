/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaDocumentTargetLinkRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for DocumentTargetLink.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.documents.application.port.out.DocumentTargetLinkRepositoryPort;
import dz.sh.hidra.modules.documents.domain.model.DocumentTargetLink;
import dz.sh.hidra.modules.documents.infrastructure.persistence.mapper.DocumentsPersistenceMapper;
import dz.sh.hidra.modules.documents.infrastructure.persistence.repository.DocumentTargetLinkJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for DocumentTargetLink.
 */
@Component
public class JpaDocumentTargetLinkRepositoryAdapter implements DocumentTargetLinkRepositoryPort {

    private final DocumentTargetLinkJpaRepository repository;

    public JpaDocumentTargetLinkRepositoryAdapter(DocumentTargetLinkJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "DocumentTargetLinkJpaRepository must not be null.");
    }

    @Override
    public DocumentTargetLink save(DocumentTargetLink model) {
        return DocumentsPersistenceMapper.toDomain(repository.save(DocumentsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<DocumentTargetLink> findById(String id) {
        return repository.findById(id).map(DocumentsPersistenceMapper::toDomain);
    }
}
