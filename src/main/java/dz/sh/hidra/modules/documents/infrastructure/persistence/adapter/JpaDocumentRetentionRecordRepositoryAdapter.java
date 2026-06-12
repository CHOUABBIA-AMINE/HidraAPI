/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaDocumentRetentionRecordRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for DocumentRetentionRecord.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.documents.application.port.out.DocumentRetentionRecordRepositoryPort;
import dz.sh.hidra.modules.documents.domain.model.DocumentRetentionRecord;
import dz.sh.hidra.modules.documents.infrastructure.persistence.mapper.DocumentsPersistenceMapper;
import dz.sh.hidra.modules.documents.infrastructure.persistence.repository.DocumentRetentionRecordJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for DocumentRetentionRecord.
 */
@Component
public class JpaDocumentRetentionRecordRepositoryAdapter implements DocumentRetentionRecordRepositoryPort {

    private final DocumentRetentionRecordJpaRepository repository;

    public JpaDocumentRetentionRecordRepositoryAdapter(DocumentRetentionRecordJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "DocumentRetentionRecordJpaRepository must not be null.");
    }

    @Override
    public DocumentRetentionRecord save(DocumentRetentionRecord model) {
        return DocumentsPersistenceMapper.toDomain(repository.save(DocumentsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<DocumentRetentionRecord> findById(String id) {
        return repository.findById(id).map(DocumentsPersistenceMapper::toDomain);
    }
}
