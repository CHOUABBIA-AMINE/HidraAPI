/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestController
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.controller
 *
 * @Description : REST controller for workflow task endpoints.
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

import dz.sh.hidra.modules.workflow.api.rest.request.ApproveWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.AssignWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.ClaimWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.CommentWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.DelegateWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.EscalateWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.RejectWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.RequestWorkflowCorrectionRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowActorReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowOrganizationReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowCommentResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowPageResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowTaskResponse;
import dz.sh.hidra.modules.workflow.application.port.in.ApproveWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.AssignWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ClaimWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.CommentWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.DelegateWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.EscalateWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListMyWorkflowTasksUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListWorkflowTasksUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.RejectWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.RequestWorkflowCorrectionUseCase;

/**
 * REST controller for workflow task endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/workflow")
@Tag(name = "Workflow")
public class WorkflowTaskController {

    private final AssignWorkflowTaskUseCase assignWorkflowTaskUseCase;
    private final ClaimWorkflowTaskUseCase claimWorkflowTaskUseCase;
    private final ApproveWorkflowTaskUseCase approveWorkflowTaskUseCase;
    private final RejectWorkflowTaskUseCase rejectWorkflowTaskUseCase;
    private final RequestWorkflowCorrectionUseCase requestWorkflowCorrectionUseCase;
    private final DelegateWorkflowTaskUseCase delegateWorkflowTaskUseCase;
    private final EscalateWorkflowTaskUseCase escalateWorkflowTaskUseCase;
    private final CommentWorkflowTaskUseCase commentWorkflowTaskUseCase;
    private final GetWorkflowTaskUseCase getWorkflowTaskUseCase;
    private final ListWorkflowTasksUseCase listWorkflowTasksUseCase;
    private final ListMyWorkflowTasksUseCase listMyWorkflowTasksUseCase;
    private final WorkflowRestMapper mapper;

    public WorkflowTaskController(
            AssignWorkflowTaskUseCase assignWorkflowTaskUseCase,
            ClaimWorkflowTaskUseCase claimWorkflowTaskUseCase,
            ApproveWorkflowTaskUseCase approveWorkflowTaskUseCase,
            RejectWorkflowTaskUseCase rejectWorkflowTaskUseCase,
            RequestWorkflowCorrectionUseCase requestWorkflowCorrectionUseCase,
            DelegateWorkflowTaskUseCase delegateWorkflowTaskUseCase,
            EscalateWorkflowTaskUseCase escalateWorkflowTaskUseCase,
            CommentWorkflowTaskUseCase commentWorkflowTaskUseCase,
            GetWorkflowTaskUseCase getWorkflowTaskUseCase,
            ListWorkflowTasksUseCase listWorkflowTasksUseCase,
            ListMyWorkflowTasksUseCase listMyWorkflowTasksUseCase,
            WorkflowRestMapper mapper) {

        this.assignWorkflowTaskUseCase = Objects.requireNonNull(assignWorkflowTaskUseCase, "AssignWorkflowTaskUseCase must not be null.");
        this.claimWorkflowTaskUseCase = Objects.requireNonNull(claimWorkflowTaskUseCase, "ClaimWorkflowTaskUseCase must not be null.");
        this.approveWorkflowTaskUseCase = Objects.requireNonNull(approveWorkflowTaskUseCase, "ApproveWorkflowTaskUseCase must not be null.");
        this.rejectWorkflowTaskUseCase = Objects.requireNonNull(rejectWorkflowTaskUseCase, "RejectWorkflowTaskUseCase must not be null.");
        this.requestWorkflowCorrectionUseCase = Objects.requireNonNull(requestWorkflowCorrectionUseCase, "RequestWorkflowCorrectionUseCase must not be null.");
        this.delegateWorkflowTaskUseCase = Objects.requireNonNull(delegateWorkflowTaskUseCase, "DelegateWorkflowTaskUseCase must not be null.");
        this.escalateWorkflowTaskUseCase = Objects.requireNonNull(escalateWorkflowTaskUseCase, "EscalateWorkflowTaskUseCase must not be null.");
        this.commentWorkflowTaskUseCase = Objects.requireNonNull(commentWorkflowTaskUseCase, "CommentWorkflowTaskUseCase must not be null.");
        this.getWorkflowTaskUseCase = Objects.requireNonNull(getWorkflowTaskUseCase, "GetWorkflowTaskUseCase must not be null.");
        this.listWorkflowTasksUseCase = Objects.requireNonNull(listWorkflowTasksUseCase, "ListWorkflowTasksUseCase must not be null.");
        this.listMyWorkflowTasksUseCase = Objects.requireNonNull(listMyWorkflowTasksUseCase, "ListMyWorkflowTasksUseCase must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "WorkflowRestMapper must not be null.");
    }

    @GetMapping("/tasks/{taskId}")
    @Operation(summary = "Get workflow task by id")
    public ResponseEntity<WorkflowTaskResponse> getTask(@PathVariable String taskId) {
        return ResponseEntity.ok(mapper.toResponse(getWorkflowTaskUseCase.getWorkflowTask(
                mapper.toGetWorkflowTaskQuery(taskId))));
    }

    @GetMapping("/tasks")
    @Operation(summary = "List workflow tasks")
    public ResponseEntity<WorkflowPageResponse<WorkflowTaskResponse>> listTasks(
            @RequestParam(required = false) String instanceId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String assignedActorId,
            @RequestParam(required = false) String assignedActorUsername,
            @RequestParam(required = false) String assignedActorDisplayName,
            @RequestParam(required = false) String assignedActorRoleCode,
            @RequestParam(required = false) String assignedOrganizationUnitId,
            @RequestParam(required = false) String assignedOrganizationUnitName,
            @RequestParam(required = false) String assignedOrganizationRoleCode,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection) {

        return ResponseEntity.ok(mapper.toPageResponse(
                listWorkflowTasksUseCase.listWorkflowTasks(mapper.toListWorkflowTasksQuery(
                        instanceId,
                        status,
                        actorRequest(assignedActorId, assignedActorUsername, assignedActorDisplayName, assignedActorRoleCode),
                        organizationRequest(assignedOrganizationUnitId, assignedOrganizationUnitName, assignedOrganizationRoleCode),
                        page,
                        size,
                        sortField,
                        sortDirection)),
                mapper::toResponse));
    }

    @GetMapping("/tasks/my")
    @Operation(summary = "List workflow tasks visible to an actor")
    public ResponseEntity<WorkflowPageResponse<WorkflowTaskResponse>> listMyTasks(
            @RequestParam String actorId,
            @RequestParam(required = false) String actorUsername,
            @RequestParam(required = false) String actorDisplayName,
            @RequestParam(required = false) String actorRoleCode,
            @RequestParam(required = false) String organizationUnitId,
            @RequestParam(required = false) String organizationUnitName,
            @RequestParam(required = false) String organizationRoleCode,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection) {

        return ResponseEntity.ok(mapper.toPageResponse(
                listMyWorkflowTasksUseCase.listMyWorkflowTasks(mapper.toListMyWorkflowTasksQuery(
                        actorRequest(actorId, actorUsername, actorDisplayName, actorRoleCode),
                        organizationRequest(organizationUnitId, organizationUnitName, organizationRoleCode),
                        status,
                        page,
                        size,
                        sortField,
                        sortDirection)),
                mapper::toResponse));
    }

    @PostMapping("/tasks/{taskId}/assign")
    @Operation(summary = "Assign workflow task")
    public ResponseEntity<WorkflowTaskResponse> assignTask(
            @PathVariable String taskId,
            @Valid @RequestBody AssignWorkflowTaskRequest request) {

        return ResponseEntity.ok(mapper.toResponse(assignWorkflowTaskUseCase.assignWorkflowTask(
                mapper.toCommand(taskId, request))));
    }

    @PostMapping("/tasks/{taskId}/claim")
    @Operation(summary = "Claim workflow task")
    public ResponseEntity<WorkflowTaskResponse> claimTask(
            @PathVariable String taskId,
            @Valid @RequestBody ClaimWorkflowTaskRequest request) {

        return ResponseEntity.ok(mapper.toResponse(claimWorkflowTaskUseCase.claimWorkflowTask(
                mapper.toCommand(taskId, request))));
    }

    @PostMapping("/instances/{instanceId}/tasks/{taskId}/approve")
    @Operation(summary = "Approve workflow task")
    public ResponseEntity<WorkflowTaskResponse> approveTask(
            @PathVariable String instanceId,
            @PathVariable String taskId,
            @Valid @RequestBody ApproveWorkflowTaskRequest request) {

        return ResponseEntity.ok(mapper.toResponse(approveWorkflowTaskUseCase.approveWorkflowTask(
                mapper.toCommand(instanceId, taskId, request))));
    }

    @PostMapping("/instances/{instanceId}/tasks/{taskId}/reject")
    @Operation(summary = "Reject workflow task")
    public ResponseEntity<WorkflowTaskResponse> rejectTask(
            @PathVariable String instanceId,
            @PathVariable String taskId,
            @Valid @RequestBody RejectWorkflowTaskRequest request) {

        return ResponseEntity.ok(mapper.toResponse(rejectWorkflowTaskUseCase.rejectWorkflowTask(
                mapper.toCommand(instanceId, taskId, request))));
    }

    @PostMapping("/instances/{instanceId}/tasks/{taskId}/correction-request")
    @Operation(summary = "Request workflow task correction")
    public ResponseEntity<WorkflowTaskResponse> requestCorrection(
            @PathVariable String instanceId,
            @PathVariable String taskId,
            @Valid @RequestBody RequestWorkflowCorrectionRequest request) {

        return ResponseEntity.ok(mapper.toResponse(requestWorkflowCorrectionUseCase.requestWorkflowCorrection(
                mapper.toCommand(instanceId, taskId, request))));
    }

    @PostMapping("/tasks/{taskId}/delegate")
    @Operation(summary = "Delegate workflow task")
    public ResponseEntity<WorkflowTaskResponse> delegateTask(
            @PathVariable String taskId,
            @Valid @RequestBody DelegateWorkflowTaskRequest request) {

        return ResponseEntity.ok(mapper.toResponse(delegateWorkflowTaskUseCase.delegateWorkflowTask(
                mapper.toCommand(taskId, request))));
    }

    @PostMapping("/tasks/{taskId}/escalate")
    @Operation(summary = "Escalate workflow task")
    public ResponseEntity<WorkflowTaskResponse> escalateTask(
            @PathVariable String taskId,
            @Valid @RequestBody EscalateWorkflowTaskRequest request) {

        return ResponseEntity.ok(mapper.toResponse(escalateWorkflowTaskUseCase.escalateWorkflowTask(
                mapper.toCommand(taskId, request))));
    }

    @PostMapping("/instances/{instanceId}/tasks/{taskId}/comments")
    @Operation(summary = "Comment workflow task")
    public ResponseEntity<WorkflowCommentResponse> commentTask(
            @PathVariable String instanceId,
            @PathVariable String taskId,
            @Valid @RequestBody CommentWorkflowTaskRequest request) {

        return ResponseEntity.ok(mapper.toResponse(commentWorkflowTaskUseCase.commentWorkflowTask(
                mapper.toCommand(instanceId, taskId, request))));
    }

    @PostMapping("/instances/{instanceId}/comments")
    @Operation(summary = "Comment workflow instance")
    public ResponseEntity<WorkflowCommentResponse> commentInstance(
            @PathVariable String instanceId,
            @Valid @RequestBody CommentWorkflowTaskRequest request) {

        return ResponseEntity.ok(mapper.toResponse(commentWorkflowTaskUseCase.commentWorkflowTask(
                mapper.toCommand(instanceId, null, request))));
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

    private WorkflowOrganizationReferenceRequest organizationRequest(
            String organizationUnitId,
            String organizationUnitName,
            String roleCode) {

        if (isBlank(organizationUnitId)) {
            return null;
        }

        return new WorkflowOrganizationReferenceRequest(
                organizationUnitId,
                isBlank(organizationUnitName) ? organizationUnitId : organizationUnitName,
                roleCode);
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
