/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowAuditOutboxReferenceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WorkflowAuditOutboxReference.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowAuditOutboxReferenceRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowAuditOutboxReference;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowAuditOutboxReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for WorkflowAuditOutboxReference.
 */
@Component
public class JpaWorkflowAuditOutboxReferenceRepositoryAdapter implements WorkflowAuditOutboxReferenceRepositoryPort {

    private final WorkflowAuditOutboxReferenceJpaRepository repository;

    public JpaWorkflowAuditOutboxReferenceRepositoryAdapter(WorkflowAuditOutboxReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "WorkflowAuditOutboxReferenceJpaRepository must not be null.");
    }

    @Override
    public WorkflowAuditOutboxReference save(WorkflowAuditOutboxReference model) {
        return WorkflowPersistenceMapper.toDomain(repository.save(WorkflowPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<WorkflowAuditOutboxReference> findById(String id) {
        return repository.findById(id).map(WorkflowPersistenceMapper::toDomain);
    }
}
