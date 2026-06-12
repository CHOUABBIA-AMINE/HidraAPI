/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationSchemaVersionRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationSchemaVersion.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationSchemaVersionRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationSchemaVersion;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationSchemaVersionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationSchemaVersion.
 */
@Component
public class JpaIntegrationSchemaVersionRepositoryAdapter implements IntegrationSchemaVersionRepositoryPort {

    private final IntegrationSchemaVersionJpaRepository repository;

    public JpaIntegrationSchemaVersionRepositoryAdapter(IntegrationSchemaVersionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrationSchemaVersionJpaRepository must not be null.");
    }

    @Override
    public IntegrationSchemaVersion save(IntegrationSchemaVersion model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationSchemaVersion> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
