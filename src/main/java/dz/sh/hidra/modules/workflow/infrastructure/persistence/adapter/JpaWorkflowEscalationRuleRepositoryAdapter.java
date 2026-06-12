/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowEscalationRuleRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WorkflowEscalationRule.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowEscalationRuleRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowEscalationRule;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowEscalationRuleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for WorkflowEscalationRule.
 */
@Component
public class JpaWorkflowEscalationRuleRepositoryAdapter implements WorkflowEscalationRuleRepositoryPort {

    private final WorkflowEscalationRuleJpaRepository repository;

    public JpaWorkflowEscalationRuleRepositoryAdapter(WorkflowEscalationRuleJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "WorkflowEscalationRuleJpaRepository must not be null.");
    }

    @Override
    public WorkflowEscalationRule save(WorkflowEscalationRule model) {
        return WorkflowPersistenceMapper.toDomain(repository.save(WorkflowPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<WorkflowEscalationRule> findById(String id) {
        return repository.findById(id).map(WorkflowPersistenceMapper::toDomain);
    }
}
