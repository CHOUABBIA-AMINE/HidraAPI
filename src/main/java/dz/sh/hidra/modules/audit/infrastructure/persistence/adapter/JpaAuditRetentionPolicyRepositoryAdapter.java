/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditRetentionPolicyRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AuditRetentionPolicy.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.audit.application.port.out.AuditRetentionPolicyRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditRetentionPolicy;
import dz.sh.hidra.modules.audit.infrastructure.persistence.mapper.AuditPersistenceMapper;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditRetentionPolicyJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuditRetentionPolicy.
 */
@Component
public class JpaAuditRetentionPolicyRepositoryAdapter implements AuditRetentionPolicyRepositoryPort {

    private final AuditRetentionPolicyJpaRepository repository;

    public JpaAuditRetentionPolicyRepositoryAdapter(AuditRetentionPolicyJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuditRetentionPolicyJpaRepository must not be null.");
    }

    @Override
    public AuditRetentionPolicy save(AuditRetentionPolicy model) {
        return AuditPersistenceMapper.toDomain(repository.save(AuditPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuditRetentionPolicy> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
