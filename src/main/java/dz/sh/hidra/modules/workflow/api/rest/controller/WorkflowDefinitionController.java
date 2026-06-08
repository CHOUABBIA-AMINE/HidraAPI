/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestController
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.controller
 *
 * @Description : REST controller for workflow definition endpoints.
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

import dz.sh.hidra.modules.workflow.api.rest.request.CreateWorkflowDefinitionRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.CreateWorkflowStepRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.CreateWorkflowTransitionRequest;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowDefinitionResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowPageResponse;
import dz.sh.hidra.modules.workflow.application.port.in.ActivateWorkflowDefinitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.CreateWorkflowDefinitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.CreateWorkflowStepUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.CreateWorkflowTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.DeactivateWorkflowDefinitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowDefinitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListWorkflowDefinitionsUseCase;

/**
 * REST controller for workflow definition endpoints.
 *
 * <p>Architecture role:
 * Thin REST controller for the workflow API. It delegates command/query creation to
 * WorkflowRestMapper and all business work to application inbound ports.
 */
@RestController
@Validated
@RequestMapping("/api/v1/workflow")
@Tag(name = "Workflow")
public class WorkflowDefinitionController {

    private final CreateWorkflowDefinitionUseCase createWorkflowDefinitionUseCase;
    private final ActivateWorkflowDefinitionUseCase activateWorkflowDefinitionUseCase;
    private final DeactivateWorkflowDefinitionUseCase deactivateWorkflowDefinitionUseCase;
    private final GetWorkflowDefinitionUseCase getWorkflowDefinitionUseCase;
    private final ListWorkflowDefinitionsUseCase listWorkflowDefinitionsUseCase;
    private final CreateWorkflowStepUseCase createWorkflowStepUseCase;
    private final CreateWorkflowTransitionUseCase createWorkflowTransitionUseCase;
    private final WorkflowRestMapper mapper;

    public WorkflowDefinitionController(
            CreateWorkflowDefinitionUseCase createWorkflowDefinitionUseCase,
            ActivateWorkflowDefinitionUseCase activateWorkflowDefinitionUseCase,
            DeactivateWorkflowDefinitionUseCase deactivateWorkflowDefinitionUseCase,
            GetWorkflowDefinitionUseCase getWorkflowDefinitionUseCase,
            ListWorkflowDefinitionsUseCase listWorkflowDefinitionsUseCase,
            CreateWorkflowStepUseCase createWorkflowStepUseCase,
            CreateWorkflowTransitionUseCase createWorkflowTransitionUseCase,
            WorkflowRestMapper mapper) {

        this.createWorkflowDefinitionUseCase = Objects.requireNonNull(createWorkflowDefinitionUseCase, "CreateWorkflowDefinitionUseCase must not be null.");
        this.activateWorkflowDefinitionUseCase = Objects.requireNonNull(activateWorkflowDefinitionUseCase, "ActivateWorkflowDefinitionUseCase must not be null.");
        this.deactivateWorkflowDefinitionUseCase = Objects.requireNonNull(deactivateWorkflowDefinitionUseCase, "DeactivateWorkflowDefinitionUseCase must not be null.");
        this.getWorkflowDefinitionUseCase = Objects.requireNonNull(getWorkflowDefinitionUseCase, "GetWorkflowDefinitionUseCase must not be null.");
        this.listWorkflowDefinitionsUseCase = Objects.requireNonNull(listWorkflowDefinitionsUseCase, "ListWorkflowDefinitionsUseCase must not be null.");
        this.createWorkflowStepUseCase = Objects.requireNonNull(createWorkflowStepUseCase, "CreateWorkflowStepUseCase must not be null.");
        this.createWorkflowTransitionUseCase = Objects.requireNonNull(createWorkflowTransitionUseCase, "CreateWorkflowTransitionUseCase must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "WorkflowRestMapper must not be null.");
    }

    @PostMapping("/definitions")
    @Operation(summary = "Create workflow definition")
    public ResponseEntity<WorkflowDefinitionResponse> createDefinition(@Valid @RequestBody CreateWorkflowDefinitionRequest request) {
        return ResponseEntity.ok(mapper.toResponse(createWorkflowDefinitionUseCase.createWorkflowDefinition(mapper.toCommand(request))));
    }

    @PostMapping("/definitions/{definitionId}/activate")
    @Operation(summary = "Activate workflow definition")
    public ResponseEntity<WorkflowDefinitionResponse> activateDefinition(@PathVariable String definitionId) {
        return ResponseEntity.ok(mapper.toResponse(activateWorkflowDefinitionUseCase.activateWorkflowDefinition(
                mapper.toActivateWorkflowDefinitionCommand(definitionId))));
    }

    @PostMapping("/definitions/{definitionId}/deactivate")
    @Operation(summary = "Deactivate workflow definition")
    public ResponseEntity<WorkflowDefinitionResponse> deactivateDefinition(@PathVariable String definitionId) {
        return ResponseEntity.ok(mapper.toResponse(deactivateWorkflowDefinitionUseCase.deactivateWorkflowDefinition(
                mapper.toDeactivateWorkflowDefinitionCommand(definitionId))));
    }

    @GetMapping("/definitions/{definitionId}")
    @Operation(summary = "Get workflow definition by id")
    public ResponseEntity<WorkflowDefinitionResponse> getDefinition(@PathVariable String definitionId) {
        return ResponseEntity.ok(mapper.toResponse(getWorkflowDefinitionUseCase.getWorkflowDefinition(
                mapper.toGetWorkflowDefinitionQuery(definitionId))));
    }

    @GetMapping("/definitions")
    @Operation(summary = "List workflow definitions")
    public ResponseEntity<WorkflowPageResponse<WorkflowDefinitionResponse>> listDefinitions(
            @RequestParam(required = false) String typeId,
            @RequestParam(required = false) String typeCode,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection) {

        return ResponseEntity.ok(mapper.toPageResponse(
                listWorkflowDefinitionsUseCase.listWorkflowDefinitions(mapper.toListWorkflowDefinitionsQuery(
                        typeId,
                        typeCode,
                        status,
                        searchTerm,
                        page,
                        size,
                        sortField,
                        sortDirection)),
                mapper::toResponse));
    }

    @PostMapping("/definitions/{definitionId}/steps")
    @Operation(summary = "Create workflow definition step")
    public ResponseEntity<WorkflowDefinitionResponse> createStep(
            @PathVariable String definitionId,
            @Valid @RequestBody CreateWorkflowStepRequest request) {

        return ResponseEntity.ok(mapper.toResponse(createWorkflowStepUseCase.createWorkflowStep(
                mapper.toCommand(definitionId, request))));
    }

    @PostMapping("/definitions/{definitionId}/transitions")
    @Operation(summary = "Create workflow definition transition")
    public ResponseEntity<WorkflowDefinitionResponse> createTransition(
            @PathVariable String definitionId,
            @Valid @RequestBody CreateWorkflowTransitionRequest request) {

        return ResponseEntity.ok(mapper.toResponse(createWorkflowTransitionUseCase.createWorkflowTransition(
                mapper.toCommand(definitionId, request))));
    }
}
