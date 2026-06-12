/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationSyncCursorRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationSyncCursor.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationSyncCursorRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationSyncCursor;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationSyncCursorJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationSyncCursor.
 */
@Component
public class JpaIntegrationSyncCursorRepositoryAdapter implements IntegrationSyncCursorRepositoryPort {

    private final IntegrationSyncCursorJpaRepository repository;

    public JpaIntegrationSyncCursorRepositoryAdapter(IntegrationSyncCursorJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrationSyncCursorJpaRepository must not be null.");
    }

    @Override
    public IntegrationSyncCursor save(IntegrationSyncCursor model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationSyncCursor> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
