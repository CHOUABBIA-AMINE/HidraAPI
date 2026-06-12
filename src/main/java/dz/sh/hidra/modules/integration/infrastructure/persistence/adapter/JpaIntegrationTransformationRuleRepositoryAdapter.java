/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationTransformationRuleRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationTransformationRule.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationTransformationRuleRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationTransformationRule;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationTransformationRuleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationTransformationRule.
 */
@Component
public class JpaIntegrationTransformationRuleRepositoryAdapter implements IntegrationTransformationRuleRepositoryPort {

    private final IntegrationTransformationRuleJpaRepository repository;

    public JpaIntegrationTransformationRuleRepositoryAdapter(IntegrationTransformationRuleJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrationTransformationRuleJpaRepository must not be null.");
    }

    @Override
    public IntegrationTransformationRule save(IntegrationTransformationRule model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationTransformationRule> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
