/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaDocumentExtractionRecordRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for DocumentExtractionRecord.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.documents.application.port.out.DocumentExtractionRecordRepositoryPort;
import dz.sh.hidra.modules.documents.domain.model.DocumentExtractionRecord;
import dz.sh.hidra.modules.documents.infrastructure.persistence.mapper.DocumentsPersistenceMapper;
import dz.sh.hidra.modules.documents.infrastructure.persistence.repository.DocumentExtractionRecordJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for DocumentExtractionRecord.
 */
@Component
public class JpaDocumentExtractionRecordRepositoryAdapter implements DocumentExtractionRecordRepositoryPort {

    private final DocumentExtractionRecordJpaRepository repository;

    public JpaDocumentExtractionRecordRepositoryAdapter(DocumentExtractionRecordJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "DocumentExtractionRecordJpaRepository must not be null.");
    }

    @Override
    public DocumentExtractionRecord save(DocumentExtractionRecord model) {
        return DocumentsPersistenceMapper.toDomain(repository.save(DocumentsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<DocumentExtractionRecord> findById(String id) {
        return repository.findById(id).map(DocumentsPersistenceMapper::toDomain);
    }
}
