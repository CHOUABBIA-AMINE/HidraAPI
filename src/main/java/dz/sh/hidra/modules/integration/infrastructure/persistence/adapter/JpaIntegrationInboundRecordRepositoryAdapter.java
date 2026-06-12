/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationInboundRecordRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationInboundRecord.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationInboundRecordRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationInboundRecord;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationInboundRecordJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationInboundRecord.
 */
@Component
public class JpaIntegrationInboundRecordRepositoryAdapter implements IntegrationInboundRecordRepositoryPort {

    private final IntegrationInboundRecordJpaRepository repository;

    public JpaIntegrationInboundRecordRepositoryAdapter(IntegrationInboundRecordJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrationInboundRecordJpaRepository must not be null.");
    }

    @Override
    public IntegrationInboundRecord save(IntegrationInboundRecord model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationInboundRecord> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
