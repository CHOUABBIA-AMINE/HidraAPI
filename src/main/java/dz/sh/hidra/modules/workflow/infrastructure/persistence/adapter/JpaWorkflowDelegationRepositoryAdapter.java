/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowDelegationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WorkflowDelegation.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowDelegationRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDelegation;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowDelegationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for WorkflowDelegation.
 */
@Component
public class JpaWorkflowDelegationRepositoryAdapter implements WorkflowDelegationRepositoryPort {

    private final WorkflowDelegationJpaRepository repository;

    public JpaWorkflowDelegationRepositoryAdapter(WorkflowDelegationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "WorkflowDelegationJpaRepository must not be null.");
    }

    @Override
    public WorkflowDelegation save(WorkflowDelegation model) {
        return WorkflowPersistenceMapper.toDomain(repository.save(WorkflowPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<WorkflowDelegation> findById(String id) {
        return repository.findById(id).map(WorkflowPersistenceMapper::toDomain);
    }
}
