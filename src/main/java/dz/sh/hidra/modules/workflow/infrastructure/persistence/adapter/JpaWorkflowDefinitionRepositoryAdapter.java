/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowDefinitionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WorkflowDefinition.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowDefinitionRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowDefinitionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for WorkflowDefinition.
 */
@Component
public class JpaWorkflowDefinitionRepositoryAdapter implements WorkflowDefinitionRepositoryPort {

    private final WorkflowDefinitionJpaRepository repository;

    public JpaWorkflowDefinitionRepositoryAdapter(WorkflowDefinitionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "WorkflowDefinitionJpaRepository must not be null.");
    }

    @Override
    public WorkflowDefinition save(WorkflowDefinition model) {
        Objects.requireNonNull(model, "WorkflowDefinition must not be null.");

        if (repository.existsByCodeAndVersionAndIdNot(model.code(), model.version(), model.id())) {
            throw new InvalidWorkflowValueException(
                    "WorkflowDefinition code and version must be unique."
            );
        }

        repository.findById(model.id()).ifPresent(existing -> {
            if (existing.status() == WorkflowDefinitionStatus.ACTIVE
                    && (!Objects.equals(existing.code(), model.code())
                    || !Objects.equals(existing.typeId(), model.typeId())
                    || existing.version() != model.version())) {
                throw new InvalidWorkflowValueException(
                        "ACTIVE WorkflowDefinition structural fields cannot be edited in place; create a new version."
                );
            }
        });

        return WorkflowPersistenceMapper.toDomain(repository.save(WorkflowPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<WorkflowDefinition> findById(String id) {
        return repository.findById(id).map(WorkflowPersistenceMapper::toDomain);
    }
}
