/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyBatchRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyBatch.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyBatchRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyBatch;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyBatchJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyBatch.
 */
@Component
public class JpaCustodyBatchRepositoryAdapter implements CustodyBatchRepositoryPort {

    private final CustodyBatchJpaRepository repository;

    public JpaCustodyBatchRepositoryAdapter(CustodyBatchJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyBatchJpaRepository must not be null.");
    }

    @Override
    public CustodyBatch save(CustodyBatch model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyBatch> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
