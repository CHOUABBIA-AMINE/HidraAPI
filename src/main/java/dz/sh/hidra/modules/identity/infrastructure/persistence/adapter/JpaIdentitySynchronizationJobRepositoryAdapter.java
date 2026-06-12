/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIdentitySynchronizationJobRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed repository adapter for IdentitySynchronizationJob.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.IdentitySynchronizationJobRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.IdentitySynchronizationJob;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.IdentitySynchronizationJobJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IdentitySynchronizationJob.
 */
@Component
public class JpaIdentitySynchronizationJobRepositoryAdapter implements IdentitySynchronizationJobRepositoryPort {

    private final IdentitySynchronizationJobJpaRepository repository;

    public JpaIdentitySynchronizationJobRepositoryAdapter(IdentitySynchronizationJobJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IdentitySynchronizationJobJpaRepository must not be null.");
    }

    @Override
    public IdentitySynchronizationJob save(IdentitySynchronizationJob model) {
        return IdentityPersistenceMapper.toDomain(repository.save(IdentityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IdentitySynchronizationJob> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
