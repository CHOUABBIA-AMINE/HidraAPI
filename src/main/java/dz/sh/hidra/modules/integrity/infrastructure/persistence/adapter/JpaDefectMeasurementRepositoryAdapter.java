/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaDefectMeasurementRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for DefectMeasurement.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.DefectMeasurementRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.DefectMeasurement;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.DefectMeasurementJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for DefectMeasurement.
 */
@Component
public class JpaDefectMeasurementRepositoryAdapter implements DefectMeasurementRepositoryPort {

    private final DefectMeasurementJpaRepository repository;

    public JpaDefectMeasurementRepositoryAdapter(DefectMeasurementJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "DefectMeasurementJpaRepository must not be null.");
    }

    @Override
    public DefectMeasurement save(DefectMeasurement model) {
        return IntegrityPersistenceMapper.toDomain(repository.save(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<DefectMeasurement> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
