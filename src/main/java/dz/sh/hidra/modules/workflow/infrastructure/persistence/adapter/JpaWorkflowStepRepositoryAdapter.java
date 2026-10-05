/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowStepRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WorkflowStep.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowStepRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStep;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowStepAssignmentRuleJpaRepository;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowStepJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for WorkflowStep.
 */
@Component
public class JpaWorkflowStepRepositoryAdapter implements WorkflowStepRepositoryPort {

    private final WorkflowStepJpaRepository repository;
    private final WorkflowStepAssignmentRuleJpaRepository assignmentRuleRepository;

    public JpaWorkflowStepRepositoryAdapter(
            WorkflowStepJpaRepository repository,
            WorkflowStepAssignmentRuleJpaRepository assignmentRuleRepository
    ) {
        this.repository = Objects.requireNonNull(
                repository,
                "WorkflowStepJpaRepository must not be null."
        );
        this.assignmentRuleRepository = Objects.requireNonNull(
                assignmentRuleRepository,
                "WorkflowStepAssignmentRuleJpaRepository must not be null."
        );
    }

    @Override
    public WorkflowStep save(WorkflowStep model) {
        Objects.requireNonNull(model, "WorkflowStep must not be null.");

        if (repository.existsByDefinitionIdAndCodeAndIdNot(
                model.definitionId(),
                model.code(),
                model.id()
        )) {
            throw new InvalidWorkflowValueException(
                    "WorkflowStep code must be unique within its WorkflowDefinition."
            );
        }
        if (repository.existsByDefinitionIdAndStepOrderAndIdNot(
                model.definitionId(),
                model.stepOrder(),
                model.id()
        )) {
            throw new InvalidWorkflowValueException(
                    "WorkflowStep order must be unique within its WorkflowDefinition."
            );
        }
        if (model.defaultAssignmentRuleId() != null
                && !assignmentRuleRepository.existsById(model.defaultAssignmentRuleId())) {
            throw new InvalidWorkflowValueException(
                    "WorkflowStep default assignment rule must reference an existing WorkflowStepAssignmentRule."
            );
        }

        return WorkflowPersistenceMapper.toDomain(
                repository.save(WorkflowPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<WorkflowStep> findById(String id) {
        return repository.findById(id).map(WorkflowPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByDefinitionIdAndCodeExcludingId(
            String definitionId,
            String code,
            String excludedId
    ) {
        return repository.existsByDefinitionIdAndCodeAndIdNot(definitionId, code, excludedId);
    }

    @Override
    public boolean existsByDefinitionIdAndStepOrderExcludingId(
            String definitionId,
            int stepOrder,
            String excludedId
    ) {
        return repository.existsByDefinitionIdAndStepOrderAndIdNot(
                definitionId,
                stepOrder,
                excludedId
        );
    }
}
