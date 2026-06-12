/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditActorSnapshotRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AuditActorSnapshot.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.audit.application.port.out.AuditActorSnapshotRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditActorSnapshot;
import dz.sh.hidra.modules.audit.infrastructure.persistence.mapper.AuditPersistenceMapper;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditActorSnapshotJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuditActorSnapshot.
 */
@Component
public class JpaAuditActorSnapshotRepositoryAdapter implements AuditActorSnapshotRepositoryPort {

    private final AuditActorSnapshotJpaRepository repository;

    public JpaAuditActorSnapshotRepositoryAdapter(AuditActorSnapshotJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuditActorSnapshotJpaRepository must not be null.");
    }

    @Override
    public AuditActorSnapshot save(AuditActorSnapshot model) {
        return AuditPersistenceMapper.toDomain(repository.save(AuditPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuditActorSnapshot> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
