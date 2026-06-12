/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditActionReferenceRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AuditActionReference.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.audit.application.port.out.AuditActionReferenceRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditActionReference;
import dz.sh.hidra.modules.audit.infrastructure.persistence.mapper.AuditPersistenceMapper;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditActionReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuditActionReference.
 */
@Component
public class JpaAuditActionReferenceRepositoryAdapter implements AuditActionReferenceRepositoryPort {

    private final AuditActionReferenceJpaRepository repository;

    public JpaAuditActionReferenceRepositoryAdapter(AuditActionReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuditActionReferenceJpaRepository must not be null.");
    }

    @Override
    public AuditActionReference save(AuditActionReference model) {
        return AuditPersistenceMapper.toDomain(repository.save(AuditPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuditActionReference> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
