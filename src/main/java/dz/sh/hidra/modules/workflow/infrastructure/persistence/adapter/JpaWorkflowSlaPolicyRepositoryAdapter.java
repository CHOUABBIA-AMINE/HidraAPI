/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowSlaPolicyRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WorkflowSlaPolicy.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowSlaPolicyRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowSlaPolicy;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowSlaPolicyJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for WorkflowSlaPolicy.
 */
@Component
public class JpaWorkflowSlaPolicyRepositoryAdapter implements WorkflowSlaPolicyRepositoryPort {

    private final WorkflowSlaPolicyJpaRepository repository;

    public JpaWorkflowSlaPolicyRepositoryAdapter(WorkflowSlaPolicyJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "WorkflowSlaPolicyJpaRepository must not be null.");
    }

    @Override
    public WorkflowSlaPolicy save(WorkflowSlaPolicy model) {
        return WorkflowPersistenceMapper.toDomain(repository.save(WorkflowPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<WorkflowSlaPolicy> findById(String id) {
        return repository.findById(id).map(WorkflowPersistenceMapper::toDomain);
    }
}
