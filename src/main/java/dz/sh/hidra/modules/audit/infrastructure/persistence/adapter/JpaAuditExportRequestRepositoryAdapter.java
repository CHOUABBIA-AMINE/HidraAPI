/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditExportRequestRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
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
    private final jakarta.persistence.EntityManager entityManager;
    private final dz.sh.hidra.modules.audit.application.port.out.AuditCatalogEligibilityPort catalogs;
    private final dz.sh.hidra.modules.audit.application.port.out.AuditDocumentReferencePort documents;
    private final dz.sh.hidra.modules.audit.application.port.out.AuditWorkflowReferencePort workflows;
    private final dz.sh.hidra.modules.audit.application.service.AuditInputPolicy policy;

    public JpaAuditExportRequestRepositoryAdapter(AuditExportRequestJpaRepository repository, jakarta.persistence.EntityManager entityManager,
            dz.sh.hidra.modules.audit.application.port.out.AuditCatalogEligibilityPort catalogs,
            dz.sh.hidra.modules.audit.application.port.out.AuditDocumentReferencePort documents,
            dz.sh.hidra.modules.audit.application.port.out.AuditWorkflowReferencePort workflows,
            dz.sh.hidra.modules.audit.application.service.AuditInputPolicy policy) {
        this.entityManager=Objects.requireNonNull(entityManager);
        this.catalogs=Objects.requireNonNull(catalogs);this.documents=Objects.requireNonNull(documents);
        this.workflows=Objects.requireNonNull(workflows);this.policy=Objects.requireNonNull(policy);
        this.repository = Objects.requireNonNull(repository, "AuditExportRequestJpaRepository must not be null.");
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public AuditExportRequest save(AuditExportRequest model) {
        Objects.requireNonNull(model);
        if (model.status() != dz.sh.hidra.modules.audit.domain.value.AuditExportStatus.REQUESTED) {
            throw new dz.sh.hidra.modules.audit.domain.exception.InvalidAuditValueException("Audit export lifecycle is not admitted.");
        }
        catalogs.requireActive(model.purposeId(), "EXPORT_PURPOSE");
        if (model.workflowInstanceId()!=null && !workflows.available(model.workflowInstanceId()))
            throw new dz.sh.hidra.modules.audit.domain.exception.InvalidAuditValueException("Audit export Workflow reference is unknown.");
        if (model.resultDocumentReferenceId()!=null && !documents.available(model.resultDocumentReferenceId()))
            throw new dz.sh.hidra.modules.audit.domain.exception.InvalidAuditValueException("Audit export Document reference is unknown.");
        model = new AuditExportRequest(model.id(), model.requestedByActorId(), policy.text(model.requestedByDisplayNameSnapshot(),160),
                model.purposeId(), policy.json(model.filterJson(),true),policy.text(model.format(),40), model.status(),
                model.workflowInstanceId(),model.resultDocumentReferenceId(),model.recordCount(),policy.text(model.checksum(),256),
                model.requestedAt(),model.completedAt(),model.expiresAt());
        var entity=AuditPersistenceMapper.toEntity(model);
        entityManager.persist(entity);entityManager.flush();
        return AuditPersistenceMapper.toDomain(entity);
    }

    @Override
    public Optional<AuditExportRequest> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
