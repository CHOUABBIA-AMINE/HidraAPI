/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationFieldMappingRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationFieldMapping.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationFieldMappingRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationFieldMapping;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationFieldMappingJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationFieldMapping.
 */
@Component
public class JpaIntegrationFieldMappingRepositoryAdapter implements IntegrationFieldMappingRepositoryPort {

    private final IntegrationFieldMappingJpaRepository repository;

    public JpaIntegrationFieldMappingRepositoryAdapter(IntegrationFieldMappingJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrationFieldMappingJpaRepository must not be null.");
    }

    @Override
    public IntegrationFieldMapping save(IntegrationFieldMapping model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationFieldMapping> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
