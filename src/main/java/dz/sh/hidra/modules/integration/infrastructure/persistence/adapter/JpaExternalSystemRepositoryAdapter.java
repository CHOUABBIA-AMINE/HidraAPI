/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaExternalSystemRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ExternalSystem.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.ExternalSystemRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.ExternalSystem;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.ExternalSystemJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ExternalSystem.
 */
@Component
public class JpaExternalSystemRepositoryAdapter implements ExternalSystemRepositoryPort {

    private final ExternalSystemJpaRepository repository;

    public JpaExternalSystemRepositoryAdapter(ExternalSystemJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ExternalSystemJpaRepository must not be null.");
    }

    @Override
    public ExternalSystem save(ExternalSystem model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ExternalSystem> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
