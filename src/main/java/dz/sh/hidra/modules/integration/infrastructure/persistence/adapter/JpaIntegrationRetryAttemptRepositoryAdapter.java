/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationRetryAttemptRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationRetryAttempt.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationRetryAttemptRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationRetryAttempt;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationRetryAttemptJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationRetryAttempt.
 */
@Component
public class JpaIntegrationRetryAttemptRepositoryAdapter implements IntegrationRetryAttemptRepositoryPort {

    private final IntegrationRetryAttemptJpaRepository repository;

    public JpaIntegrationRetryAttemptRepositoryAdapter(IntegrationRetryAttemptJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrationRetryAttemptJpaRepository must not be null.");
    }

    @Override
    public IntegrationRetryAttempt save(IntegrationRetryAttempt model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationRetryAttempt> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
