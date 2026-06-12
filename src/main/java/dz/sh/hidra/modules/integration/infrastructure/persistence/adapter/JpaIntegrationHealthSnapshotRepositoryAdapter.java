/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationHealthSnapshotRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationHealthSnapshot.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationHealthSnapshotRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationHealthSnapshot;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationHealthSnapshotJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationHealthSnapshot.
 */
@Component
public class JpaIntegrationHealthSnapshotRepositoryAdapter implements IntegrationHealthSnapshotRepositoryPort {

    private final IntegrationHealthSnapshotJpaRepository repository;

    public JpaIntegrationHealthSnapshotRepositoryAdapter(IntegrationHealthSnapshotJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrationHealthSnapshotJpaRepository must not be null.");
    }

    @Override
    public IntegrationHealthSnapshot save(IntegrationHealthSnapshot model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationHealthSnapshot> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
