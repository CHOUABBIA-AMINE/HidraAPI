/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyMeterRunSnapshotRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyMeterRunSnapshot.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyMeterRunSnapshotRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyMeterRunSnapshot;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyMeterRunSnapshotJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyMeterRunSnapshot.
 */
@Component
public class JpaCustodyMeterRunSnapshotRepositoryAdapter implements CustodyMeterRunSnapshotRepositoryPort {

    private final CustodyMeterRunSnapshotJpaRepository repository;

    public JpaCustodyMeterRunSnapshotRepositoryAdapter(CustodyMeterRunSnapshotJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyMeterRunSnapshotJpaRepository must not be null.");
    }

    @Override
    public CustodyMeterRunSnapshot save(CustodyMeterRunSnapshot model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyMeterRunSnapshot> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
