/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationRetryPolicyRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationRetryPolicy.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationRetryPolicyRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationRetryPolicy;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationRetryPolicyJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationRetryPolicy.
 */
@Component
public class JpaIntegrationRetryPolicyRepositoryAdapter implements IntegrationRetryPolicyRepositoryPort {

    private final IntegrationRetryPolicyJpaRepository repository;

    public JpaIntegrationRetryPolicyRepositoryAdapter(IntegrationRetryPolicyJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrationRetryPolicyJpaRepository must not be null.");
    }

    @Override
    public IntegrationRetryPolicy save(IntegrationRetryPolicy model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationRetryPolicy> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
