/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WorkflowCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowCatalogTranslation;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for WorkflowCatalogTranslation.
 */
@Component
public class JpaWorkflowCatalogTranslationRepositoryAdapter implements WorkflowCatalogTranslationRepositoryPort {

    private final WorkflowCatalogTranslationJpaRepository repository;

    public JpaWorkflowCatalogTranslationRepositoryAdapter(WorkflowCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "WorkflowCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public WorkflowCatalogTranslation save(WorkflowCatalogTranslation model) {
        return WorkflowPersistenceMapper.toDomain(repository.save(WorkflowPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<WorkflowCatalogTranslation> findById(String id) {
        return repository.findById(id).map(WorkflowPersistenceMapper::toDomain);
    }
}
