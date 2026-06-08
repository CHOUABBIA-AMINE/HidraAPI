/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCatalogRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter for workflow catalog repository port.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowCatalogRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTypeCatalog;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCatalogId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTypeCatalogJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowTypeCatalogJpaRepository;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowTypeTranslationJpaRepository;

/**
 * Persistence adapter for workflow catalog repository port.
 */
@Repository
public class WorkflowCatalogRepositoryAdapter implements WorkflowCatalogRepositoryPort {

    private final WorkflowTypeCatalogJpaRepository catalogRepository;
    private final WorkflowTypeTranslationJpaRepository translationRepository;
    private final WorkflowPersistenceMapper mapper;

    public WorkflowCatalogRepositoryAdapter(
            WorkflowTypeCatalogJpaRepository catalogRepository,
            WorkflowTypeTranslationJpaRepository translationRepository,
            WorkflowPersistenceMapper mapper) {

        this.catalogRepository = Objects.requireNonNull(catalogRepository, "WorkflowTypeCatalogJpaRepository must not be null.");
        this.translationRepository = Objects.requireNonNull(translationRepository, "WorkflowTypeTranslationJpaRepository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "WorkflowPersistenceMapper must not be null.");
    }

    @Override
    public WorkflowTypeCatalog save(WorkflowTypeCatalog catalog) {
        Objects.requireNonNull(catalog, "Workflow type catalog must not be null.");

        WorkflowTypeCatalogJpaEntity saved = catalogRepository.save(mapper.toEntity(catalog));
        catalog.translations().forEach(translation -> translationRepository.save(mapper.toEntity(translation)));

        return mapper.toDomain(saved, translationRepository.findByTypeId(saved.getId()));
    }

    @Override
    public Optional<WorkflowTypeCatalog> findById(WorkflowCatalogId id) {
        Objects.requireNonNull(id, "Workflow catalog id must not be null.");

        return catalogRepository.findById(id.value())
                .map(entity -> mapper.toDomain(entity, translationRepository.findByTypeId(entity.getId())));
    }

    @Override
    public Optional<WorkflowTypeCatalog> findByCatalogNameAndCode(String catalogName, WorkflowCode code) {
        Objects.requireNonNull(code, "Workflow catalog code must not be null.");

        return catalogRepository.findByCatalogNameAndCode(catalogName, code.value())
                .map(entity -> mapper.toDomain(entity, translationRepository.findByTypeId(entity.getId())));
    }

    @Override
    public boolean existsByCatalogNameAndCode(String catalogName, WorkflowCode code) {
        Objects.requireNonNull(code, "Workflow catalog code must not be null.");
        return catalogRepository.existsByCatalogNameAndCode(catalogName, code.value());
    }

    @Override
    public PageResult<WorkflowTypeCatalog> findAll(
            String catalogName,
            Boolean active,
            String locale,
            PageRequest pageRequest) {

        Pageable pageable = WorkflowSpringPageables.from(pageRequest);
        Page<WorkflowTypeCatalogJpaEntity> page;

        if (catalogName != null && active != null) {
            page = catalogRepository.findByCatalogNameAndActive(catalogName, active, pageable);
        } else if (catalogName != null) {
            page = catalogRepository.findByCatalogName(catalogName, pageable);
        } else if (active != null) {
            page = catalogRepository.findByActive(active, pageable);
        } else {
            page = catalogRepository.findAll(pageable);
        }

        return WorkflowSpringPageables.toPageResult(
                page,
                entity -> mapper.toDomain(entity, translationRepository.findByTypeId(entity.getId())));
    }
}
