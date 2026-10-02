/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditExportRequestRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AuditExportRequest.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.audit.application.port.out.AuditExportRequestRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditExportRequest;
import dz.sh.hidra.modules.audit.infrastructure.persistence.mapper.AuditPersistenceMapper;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditExportRequestJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuditExportRequest.
 */
@Component
public class JpaAuditExportRequestRepositoryAdapter implements AuditExportRequestRepositoryPort {

    private final AuditExportRequestJpaRepository repository;

    public JpaAuditExportRequestRepositoryAdapter(AuditExportRequestJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuditExportRequestJpaRepository must not be null.");
    }

    @Override
    public AuditExportRequest save(AuditExportRequest model) {
        return AuditPersistenceMapper.toDomain(repository.save(AuditPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuditExportRequest> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
