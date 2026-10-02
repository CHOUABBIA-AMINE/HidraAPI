/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowStepAssignmentRuleRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WorkflowStepAssignmentRule.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowStepAssignmentRuleRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStepAssignmentRule;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowStepAssignmentRuleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for WorkflowStepAssignmentRule.
 */
@Component
public class JpaWorkflowStepAssignmentRuleRepositoryAdapter implements WorkflowStepAssignmentRuleRepositoryPort {

    private final WorkflowStepAssignmentRuleJpaRepository repository;

    public JpaWorkflowStepAssignmentRuleRepositoryAdapter(WorkflowStepAssignmentRuleJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "WorkflowStepAssignmentRuleJpaRepository must not be null.");
    }

    @Override
    public WorkflowStepAssignmentRule save(WorkflowStepAssignmentRule model) {
        return WorkflowPersistenceMapper.toDomain(repository.save(WorkflowPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<WorkflowStepAssignmentRule> findById(String id) {
        return repository.findById(id).map(WorkflowPersistenceMapper::toDomain);
    }
}
