/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCathodicProtectionMeasurementRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CathodicProtectionMeasurement.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.CathodicProtectionMeasurementRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.CathodicProtectionMeasurement;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.CathodicProtectionMeasurementJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CathodicProtectionMeasurement.
 */
@Component
public class JpaCathodicProtectionMeasurementRepositoryAdapter implements CathodicProtectionMeasurementRepositoryPort {

    private final CathodicProtectionMeasurementJpaRepository repository;

    public JpaCathodicProtectionMeasurementRepositoryAdapter(CathodicProtectionMeasurementJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CathodicProtectionMeasurementJpaRepository must not be null.");
    }

    @Override
    public CathodicProtectionMeasurement save(CathodicProtectionMeasurement model) {
        return IntegrityPersistenceMapper.toDomain(repository.save(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CathodicProtectionMeasurement> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
