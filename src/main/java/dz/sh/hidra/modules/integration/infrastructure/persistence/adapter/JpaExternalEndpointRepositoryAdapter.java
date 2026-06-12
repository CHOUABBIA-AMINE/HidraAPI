/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaExternalEndpointRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ExternalEndpoint.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.ExternalEndpointRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.ExternalEndpoint;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.ExternalEndpointJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ExternalEndpoint.
 */
@Component
public class JpaExternalEndpointRepositoryAdapter implements ExternalEndpointRepositoryPort {

    private final ExternalEndpointJpaRepository repository;

    public JpaExternalEndpointRepositoryAdapter(ExternalEndpointJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ExternalEndpointJpaRepository must not be null.");
    }

    @Override
    public ExternalEndpoint save(ExternalEndpoint model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ExternalEndpoint> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
