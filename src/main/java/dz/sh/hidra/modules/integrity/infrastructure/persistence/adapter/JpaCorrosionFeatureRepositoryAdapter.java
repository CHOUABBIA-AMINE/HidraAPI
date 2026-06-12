/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCorrosionFeatureRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CorrosionFeature.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.CorrosionFeatureRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.CorrosionFeature;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.CorrosionFeatureJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CorrosionFeature.
 */
@Component
public class JpaCorrosionFeatureRepositoryAdapter implements CorrosionFeatureRepositoryPort {

    private final CorrosionFeatureJpaRepository repository;

    public JpaCorrosionFeatureRepositoryAdapter(CorrosionFeatureJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CorrosionFeatureJpaRepository must not be null.");
    }

    @Override
    public CorrosionFeature save(CorrosionFeature model) {
        return IntegrityPersistenceMapper.toDomain(repository.save(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CorrosionFeature> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
