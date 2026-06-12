/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaConnectorInstanceRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ConnectorInstance.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.ConnectorInstanceRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.ConnectorInstance;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.ConnectorInstanceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ConnectorInstance.
 */
@Component
public class JpaConnectorInstanceRepositoryAdapter implements ConnectorInstanceRepositoryPort {

    private final ConnectorInstanceJpaRepository repository;

    public JpaConnectorInstanceRepositoryAdapter(ConnectorInstanceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ConnectorInstanceJpaRepository must not be null.");
    }

    @Override
    public ConnectorInstance save(ConnectorInstance model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ConnectorInstance> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
