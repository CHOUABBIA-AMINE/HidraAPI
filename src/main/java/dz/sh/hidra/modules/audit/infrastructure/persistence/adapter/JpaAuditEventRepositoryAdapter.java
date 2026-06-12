/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditEventRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AuditEvent.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.audit.application.port.out.AuditEventRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditEvent;
import dz.sh.hidra.modules.audit.infrastructure.persistence.mapper.AuditPersistenceMapper;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditEventJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuditEvent.
 */
@Component
public class JpaAuditEventRepositoryAdapter implements AuditEventRepositoryPort {

    private final AuditEventJpaRepository repository;

    public JpaAuditEventRepositoryAdapter(AuditEventJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuditEventJpaRepository must not be null.");
    }

    @Override
    public AuditEvent save(AuditEvent model) {
        return AuditPersistenceMapper.toDomain(repository.save(AuditPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuditEvent> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
