/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationDeadLetterRecordRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationDeadLetterRecord.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationDeadLetterRecordRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationDeadLetterRecord;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationDeadLetterRecordJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationDeadLetterRecord.
 */
@Component
public class JpaIntegrationDeadLetterRecordRepositoryAdapter implements IntegrationDeadLetterRecordRepositoryPort {

    private final IntegrationDeadLetterRecordJpaRepository repository;

    public JpaIntegrationDeadLetterRecordRepositoryAdapter(IntegrationDeadLetterRecordJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrationDeadLetterRecordJpaRepository must not be null.");
    }

    @Override
    public IntegrationDeadLetterRecord save(IntegrationDeadLetterRecord model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationDeadLetterRecord> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
