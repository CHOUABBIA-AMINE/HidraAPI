/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditDecisionContextRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AuditDecisionContext.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.audit.application.port.out.AuditDecisionContextRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditDecisionContext;
import dz.sh.hidra.modules.audit.infrastructure.persistence.mapper.AuditPersistenceMapper;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditDecisionContextJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuditDecisionContext.
 */
@Component
public class JpaAuditDecisionContextRepositoryAdapter implements AuditDecisionContextRepositoryPort {

    private final AuditDecisionContextJpaRepository repository;

    public JpaAuditDecisionContextRepositoryAdapter(AuditDecisionContextJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuditDecisionContextJpaRepository must not be null.");
    }

    @Override
    public AuditDecisionContext save(AuditDecisionContext model) {
        return AuditPersistenceMapper.toDomain(repository.save(AuditPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuditDecisionContext> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
