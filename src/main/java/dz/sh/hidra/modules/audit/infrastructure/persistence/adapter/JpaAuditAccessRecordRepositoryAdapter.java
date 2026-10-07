/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditAccessRecordRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
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
    private final jakarta.persistence.EntityManager entityManager;
    private final dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditEventJpaRepository events;
    private final dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditExportRequestJpaRepository exports;

    public JpaAuditAccessRecordRepositoryAdapter(AuditAccessRecordJpaRepository repository, jakarta.persistence.EntityManager entityManager,
            dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditEventJpaRepository events,
            dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditExportRequestJpaRepository exports) {
        this.entityManager=Objects.requireNonNull(entityManager);this.events=Objects.requireNonNull(events);this.exports=Objects.requireNonNull(exports);
        this.repository = Objects.requireNonNull(repository, "AuditAccessRecordJpaRepository must not be null.");
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public AuditAccessRecord save(AuditAccessRecord model) {
        Objects.requireNonNull(model);
        if(model.auditEventId()!=null && !events.existsById(model.auditEventId()))
            throw new dz.sh.hidra.modules.audit.domain.exception.InvalidAuditValueException("Audit access event reference is unknown.");
        if(model.exportRequestId()!=null && !exports.existsById(model.exportRequestId()))
            throw new dz.sh.hidra.modules.audit.domain.exception.InvalidAuditValueException("Audit access export reference is unknown.");
        var entity=AuditPersistenceMapper.toEntity(model);entityManager.persist(entity);entityManager.flush();
        return AuditPersistenceMapper.toDomain(entity);
    }

    @Override
    public Optional<AuditAccessRecord> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
