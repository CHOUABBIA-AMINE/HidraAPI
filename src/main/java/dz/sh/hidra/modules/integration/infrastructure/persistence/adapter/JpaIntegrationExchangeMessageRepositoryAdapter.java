/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationExchangeMessageRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationExchangeMessage.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationExchangeMessageRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationExchangeMessage;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationExchangeMessageJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationExchangeMessage.
 */
@Component
public class JpaIntegrationExchangeMessageRepositoryAdapter implements IntegrationExchangeMessageRepositoryPort {

    private final IntegrationExchangeMessageJpaRepository repository;

    public JpaIntegrationExchangeMessageRepositoryAdapter(IntegrationExchangeMessageJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrationExchangeMessageJpaRepository must not be null.");
    }

    @Override
    public IntegrationExchangeMessage save(IntegrationExchangeMessage model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationExchangeMessage> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
