/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowInstanceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WorkflowInstance.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowInstanceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for WorkflowInstance.
 */
@Component
public class JpaWorkflowInstanceRepositoryAdapter implements WorkflowInstanceRepositoryPort {

    private final WorkflowInstanceJpaRepository repository;

    public JpaWorkflowInstanceRepositoryAdapter(WorkflowInstanceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "WorkflowInstanceJpaRepository must not be null.");
    }

    @Override
    public WorkflowInstance save(WorkflowInstance model) {
        return WorkflowPersistenceMapper.toDomain(repository.save(WorkflowPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<WorkflowInstance> findById(String id) {
        return repository.findById(id).map(WorkflowPersistenceMapper::toDomain);
    }
}
