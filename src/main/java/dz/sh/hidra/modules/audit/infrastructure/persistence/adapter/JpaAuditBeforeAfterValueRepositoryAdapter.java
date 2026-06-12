/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditBeforeAfterValueRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AuditBeforeAfterValue.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.audit.application.port.out.AuditBeforeAfterValueRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditBeforeAfterValue;
import dz.sh.hidra.modules.audit.infrastructure.persistence.mapper.AuditPersistenceMapper;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditBeforeAfterValueJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuditBeforeAfterValue.
 */
@Component
public class JpaAuditBeforeAfterValueRepositoryAdapter implements AuditBeforeAfterValueRepositoryPort {

    private final AuditBeforeAfterValueJpaRepository repository;

    public JpaAuditBeforeAfterValueRepositoryAdapter(AuditBeforeAfterValueJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuditBeforeAfterValueJpaRepository must not be null.");
    }

    @Override
    public AuditBeforeAfterValue save(AuditBeforeAfterValue model) {
        return AuditPersistenceMapper.toDomain(repository.save(AuditPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuditBeforeAfterValue> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
