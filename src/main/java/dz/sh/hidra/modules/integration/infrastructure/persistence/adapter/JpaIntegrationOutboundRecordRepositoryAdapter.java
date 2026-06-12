/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationOutboundRecordRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationOutboundRecord.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationOutboundRecordRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationOutboundRecord;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationOutboundRecordJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationOutboundRecord.
 */
@Component
public class JpaIntegrationOutboundRecordRepositoryAdapter implements IntegrationOutboundRecordRepositoryPort {

    private final IntegrationOutboundRecordJpaRepository repository;

    public JpaIntegrationOutboundRecordRepositoryAdapter(IntegrationOutboundRecordJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrationOutboundRecordJpaRepository must not be null.");
    }

    @Override
    public IntegrationOutboundRecord save(IntegrationOutboundRecord model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationOutboundRecord> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
