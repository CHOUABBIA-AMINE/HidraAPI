/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCatalogApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Service
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Application service for workflow catalog use cases.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowCatalogDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowCatalogTranslationDto;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowCatalogTypeUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListWorkflowCatalogTypesUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ResolveWorkflowCatalogTypeUseCase;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowCatalogRepositoryPort;
import dz.sh.hidra.modules.workflow.application.query.GetWorkflowCatalogTypeQuery;
import dz.sh.hidra.modules.workflow.application.query.ListWorkflowCatalogTypesQuery;
import dz.sh.hidra.modules.workflow.application.query.ResolveWorkflowCatalogTypeQuery;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTypeCatalog;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTypeTranslation;

/**
 * Application service for workflow catalog use cases.
 */
public final class WorkflowCatalogApplicationService implements
        GetWorkflowCatalogTypeUseCase,
        ListWorkflowCatalogTypesUseCase,
        ResolveWorkflowCatalogTypeUseCase {

    private final WorkflowCatalogRepositoryPort catalogRepository;

    public WorkflowCatalogApplicationService(WorkflowCatalogRepositoryPort catalogRepository) {
        this.catalogRepository = Objects.requireNonNull(catalogRepository, "WorkflowCatalogRepositoryPort must not be null.");
    }

    @Override
    public WorkflowCatalogDto getWorkflowCatalogType(GetWorkflowCatalogTypeQuery query) {
        Objects.requireNonNull(query, "GetWorkflowCatalogTypeQuery must not be null.");
        WorkflowTypeCatalog catalog = catalogRepository.findById(query.catalogId())
                .orElseThrow(() -> notFound("Workflow catalog type not found: " + query.catalogId().value()));
        return toDto(catalog);
    }

    @Override
    public PageResult<WorkflowCatalogDto> listWorkflowCatalogTypes(ListWorkflowCatalogTypesQuery query) {
        Objects.requireNonNull(query, "ListWorkflowCatalogTypesQuery must not be null.");
        PageResult<WorkflowTypeCatalog> page = catalogRepository.findAll(query.catalogName(), query.active(), query.locale(), query.pageRequest());
        return new PageResult<>(page.items().stream().map(this::toDto).toList(), page.page(), page.size(), page.totalElements(), page.totalPages());
    }

    @Override
    public WorkflowCatalogDto resolveWorkflowCatalogType(ResolveWorkflowCatalogTypeQuery query) {
        Objects.requireNonNull(query, "ResolveWorkflowCatalogTypeQuery must not be null.");
        WorkflowTypeCatalog catalog = catalogRepository.findByCatalogNameAndCode(query.catalogName(), query.code())
                .orElseThrow(() -> notFound("Workflow catalog type not found: " + query.catalogName() + "/" + query.code().value()));
        return toDto(catalog);
    }

    private WorkflowCatalogDto toDto(WorkflowTypeCatalog catalog) {
        return new WorkflowCatalogDto(
                catalog.id().value(), catalog.catalogName(), catalog.code().value(), catalog.active(), catalog.sortOrder(), catalog.systemDefined(),
                catalog.translations().stream().map(this::toTranslationDto).toList(), catalog.createdAt(), catalog.updatedAt());
    }

    private WorkflowCatalogTranslationDto toTranslationDto(WorkflowTypeTranslation translation) {
        return new WorkflowCatalogTranslationDto(translation.id().value(), translation.typeId().value(), translation.locale(), translation.name().value(), translation.description(), translation.createdAt(), translation.updatedAt());
    }

    private static BusinessRuleViolationException notFound(String message) {
        return new BusinessRuleViolationException(message);
    }
}
