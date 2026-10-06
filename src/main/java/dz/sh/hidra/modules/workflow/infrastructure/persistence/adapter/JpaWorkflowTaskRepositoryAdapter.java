/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowTaskRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WorkflowTask.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTaskRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowTaskJpaRepository;
import java.util.Objects;
import dz.sh.hidra.modules.workflow.application.service.WorkflowExecutionOwnership;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowConfigurationPort;
import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class JpaWorkflowTaskRepositoryAdapter implements WorkflowTaskRepositoryPort {

    private final WorkflowTaskJpaRepository repository;
    private final WorkflowExecutionOwnership ownership;
    private final WorkflowConfigurationPort configuration;

    public JpaWorkflowTaskRepositoryAdapter(WorkflowTaskJpaRepository repository, WorkflowExecutionOwnership ownership, WorkflowConfigurationPort configuration) {
        this.ownership=Objects.requireNonNull(ownership);this.configuration=Objects.requireNonNull(configuration);
        this.repository = Objects.requireNonNull(repository, "WorkflowTaskJpaRepository must not be null.");
    }

    @Override
    public WorkflowTask save(WorkflowTask model) {
        Objects.requireNonNull(model);
        repository.findById(model.id()).map(WorkflowPersistenceMapper::toDomain).ifPresent(previous->{
            if(!previous.openTask()) throw new InvalidWorkflowValueException("Terminal Workflow task evidence is immutable.");
            if(!previous.instanceId().equals(model.instanceId()) || !previous.stepId().equals(model.stepId()) || !previous.createdAt().equals(model.createdAt()))
                throw new InvalidWorkflowValueException("Workflow task ownership and creation evidence are immutable.");
        });
        ownership.validateAssignment(model,configuration);
        return WorkflowPersistenceMapper.toDomain(repository.save(WorkflowPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<WorkflowTask> findById(String id) {
        return repository.findById(id).map(WorkflowPersistenceMapper::toDomain);
    }

    @Override
    public Optional<WorkflowTask> findByIdForUpdate(String id) {
        return repository.findByIdForUpdate(id).map(WorkflowPersistenceMapper::toDomain);
    }
}
