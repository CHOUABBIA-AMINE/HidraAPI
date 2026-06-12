/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WorkflowCatalogEntry.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowCatalogEntry;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for WorkflowCatalogEntry.
 */
@Component
public class JpaWorkflowCatalogEntryRepositoryAdapter implements WorkflowCatalogEntryRepositoryPort {

    private final WorkflowCatalogEntryJpaRepository repository;

    public JpaWorkflowCatalogEntryRepositoryAdapter(WorkflowCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "WorkflowCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public WorkflowCatalogEntry save(WorkflowCatalogEntry model) {
        return WorkflowPersistenceMapper.toDomain(repository.save(WorkflowPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<WorkflowCatalogEntry> findById(String id) {
        return repository.findById(id).map(WorkflowPersistenceMapper::toDomain);
    }
}
