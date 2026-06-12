/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationReconciliationRunRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationReconciliationRun.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationReconciliationRunRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationReconciliationRun;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationReconciliationRunJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationReconciliationRun.
 */
@Component
public class JpaIntegrationReconciliationRunRepositoryAdapter implements IntegrationReconciliationRunRepositoryPort {

    private final IntegrationReconciliationRunJpaRepository repository;

    public JpaIntegrationReconciliationRunRepositoryAdapter(IntegrationReconciliationRunJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrationReconciliationRunJpaRepository must not be null.");
    }

    @Override
    public IntegrationReconciliationRun save(IntegrationReconciliationRun model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationReconciliationRun> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
