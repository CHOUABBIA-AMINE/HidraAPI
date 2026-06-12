/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowDefinitionTargetBindingRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WorkflowDefinitionTargetBinding.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowDefinitionTargetBindingRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinitionTargetBinding;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowDefinitionTargetBindingJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for WorkflowDefinitionTargetBinding.
 */
@Component
public class JpaWorkflowDefinitionTargetBindingRepositoryAdapter implements WorkflowDefinitionTargetBindingRepositoryPort {

    private final WorkflowDefinitionTargetBindingJpaRepository repository;

    public JpaWorkflowDefinitionTargetBindingRepositoryAdapter(WorkflowDefinitionTargetBindingJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "WorkflowDefinitionTargetBindingJpaRepository must not be null.");
    }

    @Override
    public WorkflowDefinitionTargetBinding save(WorkflowDefinitionTargetBinding model) {
        return WorkflowPersistenceMapper.toDomain(repository.save(WorkflowPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<WorkflowDefinitionTargetBinding> findById(String id) {
        return repository.findById(id).map(WorkflowPersistenceMapper::toDomain);
    }
}
