/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditTargetReferenceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AuditTargetReference.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.audit.application.port.out.AuditTargetReferenceRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditTargetReference;
import dz.sh.hidra.modules.audit.infrastructure.persistence.mapper.AuditPersistenceMapper;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditTargetReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuditTargetReference.
 */
@Component
public class JpaAuditTargetReferenceRepositoryAdapter implements AuditTargetReferenceRepositoryPort {

    private final AuditTargetReferenceJpaRepository repository;

    public JpaAuditTargetReferenceRepositoryAdapter(AuditTargetReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuditTargetReferenceJpaRepository must not be null.");
    }

    @Override
    public AuditTargetReference save(AuditTargetReference model) {
        return AuditPersistenceMapper.toDomain(repository.save(AuditPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuditTargetReference> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
