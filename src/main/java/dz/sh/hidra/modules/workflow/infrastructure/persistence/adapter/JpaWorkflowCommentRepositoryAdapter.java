/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowCommentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WorkflowComment.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowCommentRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowComment;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowCommentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for WorkflowComment.
 */
@Component
public class JpaWorkflowCommentRepositoryAdapter implements WorkflowCommentRepositoryPort {

    private final WorkflowCommentJpaRepository repository;

    public JpaWorkflowCommentRepositoryAdapter(WorkflowCommentJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "WorkflowCommentJpaRepository must not be null.");
    }

    @Override
    public WorkflowComment save(WorkflowComment model) {
        return WorkflowPersistenceMapper.toDomain(repository.save(WorkflowPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<WorkflowComment> findById(String id) {
        return repository.findById(id).map(WorkflowPersistenceMapper::toDomain);
    }
}
