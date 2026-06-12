/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowActionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WorkflowAction.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowActionRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowAction;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowActionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for WorkflowAction.
 */
@Component
public class JpaWorkflowActionRepositoryAdapter implements WorkflowActionRepositoryPort {

    private final WorkflowActionJpaRepository repository;

    public JpaWorkflowActionRepositoryAdapter(WorkflowActionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "WorkflowActionJpaRepository must not be null.");
    }

    @Override
    public WorkflowAction save(WorkflowAction model) {
        return WorkflowPersistenceMapper.toDomain(repository.save(WorkflowPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<WorkflowAction> findById(String id) {
        return repository.findById(id).map(WorkflowPersistenceMapper::toDomain);
    }
}
