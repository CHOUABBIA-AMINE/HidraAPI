/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestController
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.controller
 *
 * @Description : REST controller for workflow instance endpoints.
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

import dz.sh.hidra.modules.workflow.api.rest.request.CancelWorkflowInstanceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.StartWorkflowInstanceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowActorReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowTargetReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowTargetTypeReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowInstanceResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowPageResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowTimelineResponse;
import dz.sh.hidra.modules.workflow.application.port.in.CancelWorkflowInstanceUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowInstanceUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowTimelineUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListWorkflowInstancesUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.StartWorkflowInstanceUseCase;

/**
 * REST controller for workflow instance endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/workflow")
@Tag(name = "Workflow")
public class WorkflowInstanceController {

    private final StartWorkflowInstanceUseCase startWorkflowInstanceUseCase;
    private final CancelWorkflowInstanceUseCase cancelWorkflowInstanceUseCase;
    private final GetWorkflowInstanceUseCase getWorkflowInstanceUseCase;
    private final ListWorkflowInstancesUseCase listWorkflowInstancesUseCase;
    private final GetWorkflowTimelineUseCase getWorkflowTimelineUseCase;
    private final WorkflowRestMapper mapper;

    public WorkflowInstanceController(
            StartWorkflowInstanceUseCase startWorkflowInstanceUseCase,
            CancelWorkflowInstanceUseCase cancelWorkflowInstanceUseCase,
            GetWorkflowInstanceUseCase getWorkflowInstanceUseCase,
            ListWorkflowInstancesUseCase listWorkflowInstancesUseCase,
            GetWorkflowTimelineUseCase getWorkflowTimelineUseCase,
            WorkflowRestMapper mapper) {

        this.startWorkflowInstanceUseCase = Objects.requireNonNull(startWorkflowInstanceUseCase, "StartWorkflowInstanceUseCase must not be null.");
        this.cancelWorkflowInstanceUseCase = Objects.requireNonNull(cancelWorkflowInstanceUseCase, "CancelWorkflowInstanceUseCase must not be null.");
        this.getWorkflowInstanceUseCase = Objects.requireNonNull(getWorkflowInstanceUseCase, "GetWorkflowInstanceUseCase must not be null.");
        this.listWorkflowInstancesUseCase = Objects.requireNonNull(listWorkflowInstancesUseCase, "ListWorkflowInstancesUseCase must not be null.");
        this.getWorkflowTimelineUseCase = Objects.requireNonNull(getWorkflowTimelineUseCase, "GetWorkflowTimelineUseCase must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "WorkflowRestMapper must not be null.");
    }

    @PostMapping("/instances")
    @Operation(summary = "Start workflow instance")
    public ResponseEntity<WorkflowInstanceResponse> startInstance(@Valid @RequestBody StartWorkflowInstanceRequest request) {
        return ResponseEntity.ok(mapper.toResponse(startWorkflowInstanceUseCase.startWorkflowInstance(mapper.toCommand(request))));
    }

    @PostMapping("/instances/{instanceId}/cancel")
    @Operation(summary = "Cancel workflow instance")
    public ResponseEntity<WorkflowInstanceResponse> cancelInstance(
            @PathVariable String instanceId,
            @Valid @RequestBody CancelWorkflowInstanceRequest request) {

        return ResponseEntity.ok(mapper.toResponse(cancelWorkflowInstanceUseCase.cancelWorkflowInstance(
                mapper.toCommand(instanceId, request))));
    }

    @GetMapping("/instances/{instanceId}")
    @Operation(summary = "Get workflow instance by id")
    public ResponseEntity<WorkflowInstanceResponse> getInstance(@PathVariable String instanceId) {
        return ResponseEntity.ok(mapper.toResponse(getWorkflowInstanceUseCase.getWorkflowInstance(
                mapper.toGetWorkflowInstanceQuery(instanceId))));
    }

    @GetMapping("/instances")
    @Operation(summary = "List workflow instances")
    public ResponseEntity<WorkflowPageResponse<WorkflowInstanceResponse>> listInstances(
            @RequestParam(required = false) String definitionId,
            @RequestParam(required = false) String targetModule,
            @RequestParam(required = false) String targetTypeId,
            @RequestParam(required = false) String targetTypeCode,
            @RequestParam(required = false) String targetId,
            @RequestParam(required = false) String startedByActorId,
            @RequestParam(required = false) String startedByUsername,
            @RequestParam(required = false) String startedByDisplayName,
            @RequestParam(required = false) String startedByRoleCode,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection) {

        return ResponseEntity.ok(mapper.toPageResponse(
                listWorkflowInstancesUseCase.listWorkflowInstances(mapper.toListWorkflowInstancesQuery(
                        definitionId,
                        targetRequest(targetModule, targetTypeId, targetTypeCode, targetId),
                        status,
                        actorRequest(startedByActorId, startedByUsername, startedByDisplayName, startedByRoleCode),
                        page,
                        size,
                        sortField,
                        sortDirection)),
                mapper::toResponse));
    }

    @GetMapping("/instances/{instanceId}/timeline")
    @Operation(summary = "Get workflow instance timeline")
    public ResponseEntity<WorkflowTimelineResponse> getTimeline(@PathVariable String instanceId) {
        return ResponseEntity.ok(mapper.toResponse(getWorkflowTimelineUseCase.getWorkflowTimeline(
                mapper.toGetWorkflowTimelineQuery(instanceId))));
    }

    private WorkflowTargetReferenceRequest targetRequest(
            String targetModule,
            String targetTypeId,
            String targetTypeCode,
            String targetId) {

        if (isBlank(targetModule) && isBlank(targetTypeId) && isBlank(targetTypeCode) && isBlank(targetId)) {
            return null;
        }

        return new WorkflowTargetReferenceRequest(
                targetModule,
                new WorkflowTargetTypeReferenceRequest(targetTypeId, targetTypeCode),
                targetId,
                targetId,
                null);
    }

    private WorkflowActorReferenceRequest actorRequest(
            String actorId,
            String username,
            String displayName,
            String roleCode) {

        if (isBlank(actorId)) {
            return null;
        }

        return new WorkflowActorReferenceRequest(
                actorId,
                username,
                isBlank(displayName) ? actorId : displayName,
                roleCode);
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
