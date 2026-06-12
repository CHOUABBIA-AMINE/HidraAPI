/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyDocumentReferenceRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyDocumentReference.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyDocumentReferenceRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyDocumentReference;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyDocumentReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyDocumentReference.
 */
@Component
public class JpaCustodyDocumentReferenceRepositoryAdapter implements CustodyDocumentReferenceRepositoryPort {

    private final CustodyDocumentReferenceJpaRepository repository;

    public JpaCustodyDocumentReferenceRepositoryAdapter(CustodyDocumentReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyDocumentReferenceJpaRepository must not be null.");
    }

    @Override
    public CustodyDocumentReference save(CustodyDocumentReference model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyDocumentReference> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
