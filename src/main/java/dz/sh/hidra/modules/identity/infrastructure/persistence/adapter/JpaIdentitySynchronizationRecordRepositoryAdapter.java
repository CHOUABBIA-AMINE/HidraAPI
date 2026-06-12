/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIdentitySynchronizationRecordRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed repository adapter for IdentitySynchronizationRecord.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.IdentitySynchronizationRecordRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.IdentitySynchronizationRecord;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.IdentitySynchronizationRecordJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IdentitySynchronizationRecord.
 */
@Component
public class JpaIdentitySynchronizationRecordRepositoryAdapter implements IdentitySynchronizationRecordRepositoryPort {

    private final IdentitySynchronizationRecordJpaRepository repository;

    public JpaIdentitySynchronizationRecordRepositoryAdapter(IdentitySynchronizationRecordJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IdentitySynchronizationRecordJpaRepository must not be null.");
    }

    @Override
    public IdentitySynchronizationRecord save(IdentitySynchronizationRecord model) {
        return IdentityPersistenceMapper.toDomain(repository.save(IdentityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IdentitySynchronizationRecord> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
