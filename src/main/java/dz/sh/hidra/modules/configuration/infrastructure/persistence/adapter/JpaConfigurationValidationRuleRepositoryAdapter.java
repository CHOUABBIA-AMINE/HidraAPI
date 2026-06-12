/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaConfigurationValidationRuleRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ConfigurationValidationRule.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationValidationRuleRepositoryPort;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationValidationRule;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.mapper.ConfigurationPersistenceMapper;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.repository.ConfigurationValidationRuleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ConfigurationValidationRule.
 */
@Component
public class JpaConfigurationValidationRuleRepositoryAdapter implements ConfigurationValidationRuleRepositoryPort {

    private final ConfigurationValidationRuleJpaRepository repository;

    public JpaConfigurationValidationRuleRepositoryAdapter(ConfigurationValidationRuleJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ConfigurationValidationRuleJpaRepository must not be null.");
    }

    @Override
    public ConfigurationValidationRule save(ConfigurationValidationRule model) {
        return ConfigurationPersistenceMapper.toDomain(repository.save(ConfigurationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ConfigurationValidationRule> findById(String id) {
        return repository.findById(id).map(ConfigurationPersistenceMapper::toDomain);
    }
}
