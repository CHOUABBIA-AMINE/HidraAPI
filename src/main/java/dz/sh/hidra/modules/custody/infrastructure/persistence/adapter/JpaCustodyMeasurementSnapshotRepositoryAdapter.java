/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyMeasurementSnapshotRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyMeasurementSnapshot.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyMeasurementSnapshotRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyMeasurementSnapshot;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyMeasurementSnapshotJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyMeasurementSnapshot.
 */
@Component
public class JpaCustodyMeasurementSnapshotRepositoryAdapter implements CustodyMeasurementSnapshotRepositoryPort {

    private final CustodyMeasurementSnapshotJpaRepository repository;

    public JpaCustodyMeasurementSnapshotRepositoryAdapter(CustodyMeasurementSnapshotJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyMeasurementSnapshotJpaRepository must not be null.");
    }

    @Override
    public CustodyMeasurementSnapshot save(CustodyMeasurementSnapshot model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyMeasurementSnapshot> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
