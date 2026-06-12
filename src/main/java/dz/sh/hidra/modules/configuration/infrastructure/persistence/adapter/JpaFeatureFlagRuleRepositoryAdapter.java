/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaFeatureFlagRuleRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for FeatureFlagRule.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.configuration.application.port.out.FeatureFlagRuleRepositoryPort;
import dz.sh.hidra.modules.configuration.domain.model.FeatureFlagRule;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.mapper.ConfigurationPersistenceMapper;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.repository.FeatureFlagRuleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for FeatureFlagRule.
 */
@Component
public class JpaFeatureFlagRuleRepositoryAdapter implements FeatureFlagRuleRepositoryPort {

    private final FeatureFlagRuleJpaRepository repository;

    public JpaFeatureFlagRuleRepositoryAdapter(FeatureFlagRuleJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "FeatureFlagRuleJpaRepository must not be null.");
    }

    @Override
    public FeatureFlagRule save(FeatureFlagRule model) {
        return ConfigurationPersistenceMapper.toDomain(repository.save(ConfigurationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<FeatureFlagRule> findById(String id) {
        return repository.findById(id).map(ConfigurationPersistenceMapper::toDomain);
    }
}
