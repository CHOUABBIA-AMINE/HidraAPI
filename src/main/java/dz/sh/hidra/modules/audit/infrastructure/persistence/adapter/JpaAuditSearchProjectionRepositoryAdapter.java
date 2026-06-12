/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditSearchProjectionRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AuditSearchProjection.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.audit.application.port.out.AuditSearchProjectionRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditSearchProjection;
import dz.sh.hidra.modules.audit.infrastructure.persistence.mapper.AuditPersistenceMapper;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditSearchProjectionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuditSearchProjection.
 */
@Component
public class JpaAuditSearchProjectionRepositoryAdapter implements AuditSearchProjectionRepositoryPort {

    private final AuditSearchProjectionJpaRepository repository;

    public JpaAuditSearchProjectionRepositoryAdapter(AuditSearchProjectionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuditSearchProjectionJpaRepository must not be null.");
    }

    @Override
    public AuditSearchProjection save(AuditSearchProjection model) {
        return AuditPersistenceMapper.toDomain(repository.save(AuditPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuditSearchProjection> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
