/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationJobRunStepRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationJobRunStep.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationJobRunStepRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationJobRunStep;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationJobRunStepJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationJobRunStep.
 */
@Component
public class JpaIntegrationJobRunStepRepositoryAdapter implements IntegrationJobRunStepRepositoryPort {

    private final IntegrationJobRunStepJpaRepository repository;

    public JpaIntegrationJobRunStepRepositoryAdapter(IntegrationJobRunStepJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrationJobRunStepJpaRepository must not be null.");
    }

    @Override
    public IntegrationJobRunStep save(IntegrationJobRunStep model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationJobRunStep> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
