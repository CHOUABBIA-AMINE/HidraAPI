/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWallThicknessMeasurementRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WallThicknessMeasurement.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.WallThicknessMeasurementRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.WallThicknessMeasurement;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.WallThicknessMeasurementJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for WallThicknessMeasurement.
 */
@Component
public class JpaWallThicknessMeasurementRepositoryAdapter implements WallThicknessMeasurementRepositoryPort {

    private final WallThicknessMeasurementJpaRepository repository;

    public JpaWallThicknessMeasurementRepositoryAdapter(WallThicknessMeasurementJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "WallThicknessMeasurementJpaRepository must not be null.");
    }

    @Override
    public WallThicknessMeasurement save(WallThicknessMeasurement model) {
        return IntegrityPersistenceMapper.toDomain(repository.save(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<WallThicknessMeasurement> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
