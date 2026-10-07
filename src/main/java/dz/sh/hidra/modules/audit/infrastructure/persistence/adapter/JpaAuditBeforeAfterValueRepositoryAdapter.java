/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditBeforeAfterValueRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
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
    private final jakarta.persistence.EntityManager entityManager;
    private final dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditEventJpaRepository events;
    private final dz.sh.hidra.modules.audit.application.port.out.AuditCatalogEligibilityPort catalogs;
    private final dz.sh.hidra.modules.audit.application.service.AuditInputPolicy policy;

    public JpaAuditBeforeAfterValueRepositoryAdapter(AuditBeforeAfterValueJpaRepository repository, jakarta.persistence.EntityManager entityManager,
            dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditEventJpaRepository events,
            dz.sh.hidra.modules.audit.application.port.out.AuditCatalogEligibilityPort catalogs,
            dz.sh.hidra.modules.audit.application.service.AuditInputPolicy policy) {
        this.entityManager=Objects.requireNonNull(entityManager);this.events=Objects.requireNonNull(events);
        this.catalogs=Objects.requireNonNull(catalogs);this.policy=Objects.requireNonNull(policy);
        this.repository = Objects.requireNonNull(repository, "AuditBeforeAfterValueJpaRepository must not be null.");
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public AuditBeforeAfterValue save(AuditBeforeAfterValue model) {
        Objects.requireNonNull(model);
        if(!events.existsById(model.auditEventId()))
            throw new dz.sh.hidra.modules.audit.domain.exception.InvalidAuditValueException("Audit before/after parent event is unknown.");
        if(model.maskReasonId()!=null)catalogs.requireActive(model.maskReasonId(),"MASK_REASON");
        model=new AuditBeforeAfterValue(model.id(),model.auditEventId(),policy.text(model.fieldPath(),240),
                policy.text(model.fieldLabelSnapshot(),240),model.valueType(),policy.text(model.beforeValueText(),2000),
                policy.text(model.afterValueText(),2000),policy.text(model.beforeValueHash(),256),policy.text(model.afterValueHash(),256),
                model.masked(),model.maskReasonId(),model.changed(),model.recordedAt());
        var entity=AuditPersistenceMapper.toEntity(model);entityManager.persist(entity);entityManager.flush();
        return AuditPersistenceMapper.toDomain(entity);
    }

    @Override
    public Optional<AuditBeforeAfterValue> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
