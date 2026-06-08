/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCatalogController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestController
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.controller
 *
 * @Description : REST controller for workflow controlled vocabulary catalog endpoints.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.controller;


import java.util.Objects;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import dz.sh.hidra.modules.workflow.api.rest.mapper.WorkflowRestMapper;

import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowCatalogResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowPageResponse;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowCatalogTypeUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListWorkflowCatalogTypesUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ResolveWorkflowCatalogTypeUseCase;

/**
 * REST controller for workflow controlled vocabulary catalog endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/workflow")
@Tag(name = "Workflow")
public class WorkflowCatalogController {

    private final GetWorkflowCatalogTypeUseCase getWorkflowCatalogTypeUseCase;
    private final ListWorkflowCatalogTypesUseCase listWorkflowCatalogTypesUseCase;
    private final ResolveWorkflowCatalogTypeUseCase resolveWorkflowCatalogTypeUseCase;
    private final WorkflowRestMapper mapper;

    public WorkflowCatalogController(
            GetWorkflowCatalogTypeUseCase getWorkflowCatalogTypeUseCase,
            ListWorkflowCatalogTypesUseCase listWorkflowCatalogTypesUseCase,
            ResolveWorkflowCatalogTypeUseCase resolveWorkflowCatalogTypeUseCase,
            WorkflowRestMapper mapper) {

        this.getWorkflowCatalogTypeUseCase = Objects.requireNonNull(getWorkflowCatalogTypeUseCase, "GetWorkflowCatalogTypeUseCase must not be null.");
        this.listWorkflowCatalogTypesUseCase = Objects.requireNonNull(listWorkflowCatalogTypesUseCase, "ListWorkflowCatalogTypesUseCase must not be null.");
        this.resolveWorkflowCatalogTypeUseCase = Objects.requireNonNull(resolveWorkflowCatalogTypeUseCase, "ResolveWorkflowCatalogTypeUseCase must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "WorkflowRestMapper must not be null.");
    }

    @GetMapping("/catalog-types/{catalogId}")
    @Operation(summary = "Get workflow catalog entry by id")
    public ResponseEntity<WorkflowCatalogResponse> getCatalogType(@PathVariable String catalogId) {
        return ResponseEntity.ok(mapper.toResponse(getWorkflowCatalogTypeUseCase.getWorkflowCatalogType(
                mapper.toGetWorkflowCatalogTypeQuery(catalogId))));
    }

    @GetMapping("/catalog-types")
    @Operation(summary = "List workflow catalog entries")
    public ResponseEntity<WorkflowPageResponse<WorkflowCatalogResponse>> listCatalogTypes(
            @RequestParam String catalogName,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String locale,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection) {

        return ResponseEntity.ok(mapper.toPageResponse(
                listWorkflowCatalogTypesUseCase.listWorkflowCatalogTypes(mapper.toListWorkflowCatalogTypesQuery(
                        catalogName,
                        active,
                        locale,
                        page,
                        size,
                        sortField,
                        sortDirection)),
                mapper::toResponse));
    }

    @GetMapping("/catalog-types/resolve")
    @Operation(summary = "Resolve workflow catalog entry by catalog name and code")
    public ResponseEntity<WorkflowCatalogResponse> resolveCatalogType(
            @RequestParam String catalogName,
            @RequestParam String code,
            @RequestParam(required = false) String locale) {

        return ResponseEntity.ok(mapper.toResponse(resolveWorkflowCatalogTypeUseCase.resolveWorkflowCatalogType(
                mapper.toResolveWorkflowCatalogTypeQuery(catalogName, code, locale))));
    }
}
