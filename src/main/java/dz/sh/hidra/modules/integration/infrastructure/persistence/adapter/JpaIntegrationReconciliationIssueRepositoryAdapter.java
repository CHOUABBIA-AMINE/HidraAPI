/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationReconciliationIssueRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationReconciliationIssue.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationReconciliationIssueRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationReconciliationIssue;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationReconciliationIssueJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationReconciliationIssue.
 */
@Component
public class JpaIntegrationReconciliationIssueRepositoryAdapter implements IntegrationReconciliationIssueRepositoryPort {

    private final IntegrationReconciliationIssueJpaRepository repository;

    public JpaIntegrationReconciliationIssueRepositoryAdapter(IntegrationReconciliationIssueJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrationReconciliationIssueJpaRepository must not be null.");
    }

    @Override
    public IntegrationReconciliationIssue save(IntegrationReconciliationIssue model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationReconciliationIssue> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
