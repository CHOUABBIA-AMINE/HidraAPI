/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditCorrelationContextRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AuditCorrelationContext.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.audit.application.port.out.AuditCorrelationContextRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditCorrelationContext;
import dz.sh.hidra.modules.audit.infrastructure.persistence.mapper.AuditPersistenceMapper;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditCorrelationContextJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuditCorrelationContext.
 */
@Component
public class JpaAuditCorrelationContextRepositoryAdapter implements AuditCorrelationContextRepositoryPort {

    private final AuditCorrelationContextJpaRepository repository;

    public JpaAuditCorrelationContextRepositoryAdapter(AuditCorrelationContextJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuditCorrelationContextJpaRepository must not be null.");
    }

    @Override
    public AuditCorrelationContext save(AuditCorrelationContext model) {
        return AuditPersistenceMapper.toDomain(repository.save(AuditPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuditCorrelationContext> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
