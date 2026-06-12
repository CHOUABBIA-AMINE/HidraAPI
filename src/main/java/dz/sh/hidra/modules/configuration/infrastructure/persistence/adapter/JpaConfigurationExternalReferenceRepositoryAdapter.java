/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaConfigurationExternalReferenceRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ConfigurationExternalReference.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationExternalReferenceRepositoryPort;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationExternalReference;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.mapper.ConfigurationPersistenceMapper;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.repository.ConfigurationExternalReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ConfigurationExternalReference.
 */
@Component
public class JpaConfigurationExternalReferenceRepositoryAdapter implements ConfigurationExternalReferenceRepositoryPort {

    private final ConfigurationExternalReferenceJpaRepository repository;

    public JpaConfigurationExternalReferenceRepositoryAdapter(ConfigurationExternalReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ConfigurationExternalReferenceJpaRepository must not be null.");
    }

    @Override
    public ConfigurationExternalReference save(ConfigurationExternalReference model) {
        return ConfigurationPersistenceMapper.toDomain(repository.save(ConfigurationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ConfigurationExternalReference> findById(String id) {
        return repository.findById(id).map(ConfigurationPersistenceMapper::toDomain);
    }
}
