/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditEventRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AuditEvent.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.audit.application.port.out.AuditEventRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditEvent;
import dz.sh.hidra.modules.audit.infrastructure.persistence.mapper.AuditPersistenceMapper;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditEventJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuditEvent.
 */
@Component
public class JpaAuditEventRepositoryAdapter implements AuditEventRepositoryPort {

    private final AuditEventJpaRepository repository;
    private final jakarta.persistence.EntityManager entityManager;
    private final dz.sh.hidra.modules.audit.application.port.out.AuditCatalogEligibilityPort catalogs;
    private final dz.sh.hidra.modules.audit.application.service.AuditInputPolicy policy;

    public JpaAuditEventRepositoryAdapter(AuditEventJpaRepository repository, jakarta.persistence.EntityManager entityManager,
            dz.sh.hidra.modules.audit.application.port.out.AuditCatalogEligibilityPort catalogs,
            dz.sh.hidra.modules.audit.application.service.AuditInputPolicy policy) {
        this.entityManager=Objects.requireNonNull(entityManager);this.catalogs=Objects.requireNonNull(catalogs);this.policy=Objects.requireNonNull(policy);
        this.repository = Objects.requireNonNull(repository, "AuditEventJpaRepository must not be null.");
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public AuditEvent save(AuditEvent model) {
        model=policy.event(Objects.requireNonNull(model));
        catalogs.requireActive(model.eventTypeId(),"EVENT_TYPE");
        catalogs.requireActive(model.eventCategoryId(),"EVENT_CATEGORY");
        if(model.severityId()!=null)catalogs.requireActive(model.severityId(),"SEVERITY");
        if(model.reasonId()!=null)catalogs.requireActive(model.reasonId(),"DECISION_REASON");
        var entity=AuditPersistenceMapper.toEntity(model);entityManager.persist(entity);entityManager.flush();
        return AuditPersistenceMapper.toDomain(entity);
    }

    @Override
    public Optional<AuditEvent> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
