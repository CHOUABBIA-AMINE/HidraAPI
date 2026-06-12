/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaConfigurationCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ConfigurationCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationCatalogTranslation;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.mapper.ConfigurationPersistenceMapper;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.repository.ConfigurationCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ConfigurationCatalogTranslation.
 */
@Component
public class JpaConfigurationCatalogTranslationRepositoryAdapter implements ConfigurationCatalogTranslationRepositoryPort {

    private final ConfigurationCatalogTranslationJpaRepository repository;

    public JpaConfigurationCatalogTranslationRepositoryAdapter(ConfigurationCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ConfigurationCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public ConfigurationCatalogTranslation save(ConfigurationCatalogTranslation model) {
        return ConfigurationPersistenceMapper.toDomain(repository.save(ConfigurationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ConfigurationCatalogTranslation> findById(String id) {
        return repository.findById(id).map(ConfigurationPersistenceMapper::toDomain);
    }
}
