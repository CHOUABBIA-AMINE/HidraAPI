/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditAccessRecordRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AuditAccessRecord.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.audit.application.port.out.AuditAccessRecordRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditAccessRecord;
import dz.sh.hidra.modules.audit.infrastructure.persistence.mapper.AuditPersistenceMapper;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditAccessRecordJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuditAccessRecord.
 */
@Component
public class JpaAuditAccessRecordRepositoryAdapter implements AuditAccessRecordRepositoryPort {

    private final AuditAccessRecordJpaRepository repository;

    public JpaAuditAccessRecordRepositoryAdapter(AuditAccessRecordJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuditAccessRecordJpaRepository must not be null.");
    }

    @Override
    public AuditAccessRecord save(AuditAccessRecord model) {
        return AuditPersistenceMapper.toDomain(repository.save(AuditPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuditAccessRecord> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
