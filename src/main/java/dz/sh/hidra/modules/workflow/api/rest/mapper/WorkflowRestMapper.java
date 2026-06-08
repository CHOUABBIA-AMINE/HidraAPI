/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Mapper
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.mapper
 *
 * @Description : REST mapper for workflow request, query, and response objects.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.mapper;

import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.application.pagination.SortDirection;
import dz.sh.hidra.modules.workflow.api.rest.request.ApproveWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.AssignWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.CancelWorkflowInstanceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.ClaimWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.CommentWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.CreateWorkflowDefinitionRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.CreateWorkflowStepRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.CreateWorkflowTransitionRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.DelegateWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.EscalateWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.RejectWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.RequestWorkflowCorrectionRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.StartWorkflowInstanceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowActorReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowLocalizedNameRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowOrganizationReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowPriorityReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowReasonReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowTargetReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowTargetTypeReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowTypeReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowActionResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowActorReferenceResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowAssignmentResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowCatalogResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowCatalogTranslationResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowCommentResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowDefinitionResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowDelegationResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowEscalationResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowInstanceResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowLocalizedNameResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowPageResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowPriorityReferenceResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowReasonReferenceResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowStepResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowTargetReferenceResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowTaskResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowTimelineResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowTransitionResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowTypeReferenceResponse;
import dz.sh.hidra.modules.workflow.application.command.ActivateWorkflowDefinitionCommand;
import dz.sh.hidra.modules.workflow.application.command.ApproveWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.AssignWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.CancelWorkflowInstanceCommand;
import dz.sh.hidra.modules.workflow.application.command.ClaimWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.CommentWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.CreateWorkflowDefinitionCommand;
import dz.sh.hidra.modules.workflow.application.command.CreateWorkflowStepCommand;
import dz.sh.hidra.modules.workflow.application.command.CreateWorkflowTransitionCommand;
import dz.sh.hidra.modules.workflow.application.command.DeactivateWorkflowDefinitionCommand;
import dz.sh.hidra.modules.workflow.application.command.DelegateWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.EscalateWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.RejectWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.RequestWorkflowCorrectionCommand;
import dz.sh.hidra.modules.workflow.application.command.StartWorkflowInstanceCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowActionDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowActorReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowAssignmentDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowCatalogDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowCatalogTranslationDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowCommentDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowDefinitionDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowDelegationDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowEscalationDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowInstanceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowLocalizedNameDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowPriorityReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowStepDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowReasonReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTargetReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTaskDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTimelineDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTransitionDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTypeReferenceDto;
import dz.sh.hidra.modules.workflow.application.query.GetWorkflowCatalogTypeQuery;
import dz.sh.hidra.modules.workflow.application.query.GetWorkflowDefinitionQuery;
import dz.sh.hidra.modules.workflow.application.query.GetWorkflowInstanceQuery;
import dz.sh.hidra.modules.workflow.application.query.GetWorkflowTaskQuery;
import dz.sh.hidra.modules.workflow.application.query.GetWorkflowTimelineQuery;
import dz.sh.hidra.modules.workflow.application.query.ListMyWorkflowTasksQuery;
import dz.sh.hidra.modules.workflow.application.query.ListWorkflowCatalogTypesQuery;
import dz.sh.hidra.modules.workflow.application.query.ListWorkflowDefinitionsQuery;
import dz.sh.hidra.modules.workflow.application.query.ListWorkflowInstancesQuery;
import dz.sh.hidra.modules.workflow.application.query.ListWorkflowTasksQuery;
import dz.sh.hidra.modules.workflow.application.query.ResolveWorkflowCatalogTypeQuery;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCatalogId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCommentText;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecisionNote;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDueDate;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowLocalizedName;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowPriorityReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowReasonReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowStepId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetTypeReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTypeReference;

/**
 * REST mapper for workflow request, query, and response objects.
 *
 * <p>Business role:
 * Converts workflow REST contracts into application command/query objects and converts application
 * DTOs into REST response DTOs.
 *
 * <p>Architecture role:
 * API-layer mapper only. It must not call application services, repositories, persistence mappers,
 * telemetry implementation classes, topology implementation classes, identity implementation
 * classes, organization implementation classes, planning, monitoring, incidents, audit
 * implementation, integration, analytics, reporting, or notification code.
 */
public final class WorkflowRestMapper {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;

    public CreateWorkflowDefinitionCommand toCommand(CreateWorkflowDefinitionRequest request) {
        Objects.requireNonNull(request, "CreateWorkflowDefinitionRequest must not be null.");

        return new CreateWorkflowDefinitionCommand(
                WorkflowCode.of(request.code()),
                localizedName(request.name()),
                workflowType(request.type()));
    }

    public ActivateWorkflowDefinitionCommand toActivateWorkflowDefinitionCommand(String definitionId) {
        return new ActivateWorkflowDefinitionCommand(WorkflowDefinitionId.of(definitionId));
    }

    public DeactivateWorkflowDefinitionCommand toDeactivateWorkflowDefinitionCommand(String definitionId) {
        return new DeactivateWorkflowDefinitionCommand(WorkflowDefinitionId.of(definitionId));
    }

    public CreateWorkflowStepCommand toCommand(String definitionId, CreateWorkflowStepRequest request) {
        Objects.requireNonNull(request, "CreateWorkflowStepRequest must not be null.");

        return new CreateWorkflowStepCommand(
                WorkflowDefinitionId.of(definitionId),
                WorkflowCode.of(request.stepCode()),
                localizedName(request.name()),
                request.stepOrder(),
                request.mandatory());
    }

    public CreateWorkflowTransitionCommand toCommand(String definitionId, CreateWorkflowTransitionRequest request) {
        Objects.requireNonNull(request, "CreateWorkflowTransitionRequest must not be null.");

        return new CreateWorkflowTransitionCommand(
                WorkflowDefinitionId.of(definitionId),
                WorkflowStepId.of(request.fromStepId()),
                WorkflowStepId.of(request.toStepId()),
                enumValue(request.decision(), WorkflowDecision.class),
                request.reasonRequired(),
                request.commentRequired());
    }

    public StartWorkflowInstanceCommand toCommand(StartWorkflowInstanceRequest request) {
        Objects.requireNonNull(request, "StartWorkflowInstanceRequest must not be null.");

        return new StartWorkflowInstanceCommand(
                WorkflowDefinitionId.of(request.definitionId()),
                targetReference(request.target()),
                actorReference(request.startedBy()),
                optionalCorrelationId(request.correlationId()));
    }

    public CancelWorkflowInstanceCommand toCommand(String instanceId, CancelWorkflowInstanceRequest request) {
        Objects.requireNonNull(request, "CancelWorkflowInstanceRequest must not be null.");

        return new CancelWorkflowInstanceCommand(
                WorkflowInstanceId.of(instanceId),
                actorReference(request.actor()),
                reasonReference(request.reason()),
                optionalDecisionNote(request.note()),
                optionalCorrelationId(request.correlationId()));
    }

    public AssignWorkflowTaskCommand toCommand(String taskId, AssignWorkflowTaskRequest request) {
        Objects.requireNonNull(request, "AssignWorkflowTaskRequest must not be null.");

        return new AssignWorkflowTaskCommand(
                WorkflowTaskId.of(taskId),
                actorReference(request.assignedActor()),
                organizationReference(request.assignedOrganization()),
                priorityReference(request.priority()),
                request.dueAt() == null ? null : WorkflowDueDate.of(request.dueAt()),
                optionalCorrelationId(request.correlationId()));
    }

    public ClaimWorkflowTaskCommand toCommand(String taskId, ClaimWorkflowTaskRequest request) {
        Objects.requireNonNull(request, "ClaimWorkflowTaskRequest must not be null.");

        return new ClaimWorkflowTaskCommand(
                WorkflowTaskId.of(taskId),
                actorReference(request.actor()),
                optionalCorrelationId(request.correlationId()));
    }

    public ApproveWorkflowTaskCommand toCommand(
            String instanceId,
            String taskId,
            ApproveWorkflowTaskRequest request) {

        Objects.requireNonNull(request, "ApproveWorkflowTaskRequest must not be null.");

        return new ApproveWorkflowTaskCommand(
                WorkflowInstanceId.of(instanceId),
                WorkflowTaskId.of(taskId),
                actorReference(request.actor()),
                organizationReference(request.organization()),
                reasonReference(request.reason()),
                optionalDecisionNote(request.note()),
                optionalCommentText(request.comment()),
                optionalCorrelationId(request.correlationId()));
    }

    public RejectWorkflowTaskCommand toCommand(
            String instanceId,
            String taskId,
            RejectWorkflowTaskRequest request) {

        Objects.requireNonNull(request, "RejectWorkflowTaskRequest must not be null.");

        return new RejectWorkflowTaskCommand(
                WorkflowInstanceId.of(instanceId),
                WorkflowTaskId.of(taskId),
                actorReference(request.actor()),
                organizationReference(request.organization()),
                reasonReference(request.reason()),
                optionalDecisionNote(request.note()),
                optionalCommentText(request.comment()),
                optionalCorrelationId(request.correlationId()));
    }

    public RequestWorkflowCorrectionCommand toCommand(
            String instanceId,
            String taskId,
            RequestWorkflowCorrectionRequest request) {

        Objects.requireNonNull(request, "RequestWorkflowCorrectionRequest must not be null.");

        return new RequestWorkflowCorrectionCommand(
                WorkflowInstanceId.of(instanceId),
                WorkflowTaskId.of(taskId),
                actorReference(request.actor()),
                organizationReference(request.organization()),
                reasonReference(request.reason()),
                WorkflowCommentText.of(request.comment()),
                optionalCorrelationId(request.correlationId()));
    }

    public DelegateWorkflowTaskCommand toCommand(String taskId, DelegateWorkflowTaskRequest request) {
        Objects.requireNonNull(request, "DelegateWorkflowTaskRequest must not be null.");

        return new DelegateWorkflowTaskCommand(
                WorkflowTaskId.of(taskId),
                actorReference(request.fromActor()),
                actorReference(request.toActor()),
                organizationReference(request.toOrganization()),
                reasonReference(request.reason()),
                optionalDecisionNote(request.note()),
                optionalCorrelationId(request.correlationId()));
    }

    public EscalateWorkflowTaskCommand toCommand(String taskId, EscalateWorkflowTaskRequest request) {
        Objects.requireNonNull(request, "EscalateWorkflowTaskRequest must not be null.");

        return new EscalateWorkflowTaskCommand(
                WorkflowTaskId.of(taskId),
                actorReference(request.actor()),
                organizationReference(request.organization()),
                reasonReference(request.reason()),
                optionalDecisionNote(request.note()),
                optionalCorrelationId(request.correlationId()));
    }

    public CommentWorkflowTaskCommand toCommand(
            String instanceId,
            String taskId,
            CommentWorkflowTaskRequest request) {

        Objects.requireNonNull(request, "CommentWorkflowTaskRequest must not be null.");

        return new CommentWorkflowTaskCommand(
                WorkflowInstanceId.of(instanceId),
                optionalTaskId(taskId),
                actorReference(request.actor()),
                organizationReference(request.organization()),
                WorkflowCommentText.of(request.comment()),
                optionalCorrelationId(request.correlationId()));
    }

    public GetWorkflowDefinitionQuery toGetWorkflowDefinitionQuery(String definitionId) {
        return new GetWorkflowDefinitionQuery(WorkflowDefinitionId.of(definitionId));
    }

    public ListWorkflowDefinitionsQuery toListWorkflowDefinitionsQuery(
            String typeId,
            String typeCode,
            String status,
            String searchTerm,
            Integer page,
            Integer size,
            String sortField,
            String sortDirection) {

        return new ListWorkflowDefinitionsQuery(
                optionalWorkflowType(typeId, typeCode),
                optionalEnum(status, WorkflowDefinitionStatus.class),
                blankToNull(searchTerm),
                pageRequest(page, size, sortField, sortDirection));
    }

    public GetWorkflowInstanceQuery toGetWorkflowInstanceQuery(String instanceId) {
        return new GetWorkflowInstanceQuery(WorkflowInstanceId.of(instanceId));
    }

    public ListWorkflowInstancesQuery toListWorkflowInstancesQuery(
            String definitionId,
            WorkflowTargetReferenceRequest target,
            String status,
            WorkflowActorReferenceRequest startedBy,
            Integer page,
            Integer size,
            String sortField,
            String sortDirection) {

        return new ListWorkflowInstancesQuery(
                optionalDefinitionId(definitionId),
                targetReference(target),
                optionalEnum(status, WorkflowInstanceStatus.class),
                actorReference(startedBy),
                pageRequest(page, size, sortField, sortDirection));
    }

    public GetWorkflowTimelineQuery toGetWorkflowTimelineQuery(String instanceId) {
        return new GetWorkflowTimelineQuery(WorkflowInstanceId.of(instanceId));
    }

    public GetWorkflowTaskQuery toGetWorkflowTaskQuery(String taskId) {
        return new GetWorkflowTaskQuery(WorkflowTaskId.of(taskId));
    }

    public ListWorkflowTasksQuery toListWorkflowTasksQuery(
            String instanceId,
            String status,
            WorkflowActorReferenceRequest assignedActor,
            WorkflowOrganizationReferenceRequest assignedOrganization,
            Integer page,
            Integer size,
            String sortField,
            String sortDirection) {

        return new ListWorkflowTasksQuery(
                optionalInstanceId(instanceId),
                optionalEnum(status, WorkflowTaskStatus.class),
                actorReference(assignedActor),
                organizationReference(assignedOrganization),
                pageRequest(page, size, sortField, sortDirection));
    }

    public ListMyWorkflowTasksQuery toListMyWorkflowTasksQuery(
            WorkflowActorReferenceRequest actor,
            WorkflowOrganizationReferenceRequest organization,
            String status,
            Integer page,
            Integer size,
            String sortField,
            String sortDirection) {

        return new ListMyWorkflowTasksQuery(
                actorReference(actor),
                organizationReference(organization),
                optionalEnum(status, WorkflowTaskStatus.class),
                pageRequest(page, size, sortField, sortDirection));
    }

    public GetWorkflowCatalogTypeQuery toGetWorkflowCatalogTypeQuery(String catalogId) {
        return new GetWorkflowCatalogTypeQuery(WorkflowCatalogId.of(catalogId));
    }

    public ListWorkflowCatalogTypesQuery toListWorkflowCatalogTypesQuery(
            String catalogName,
            Boolean active,
            String locale,
            Integer page,
            Integer size,
            String sortField,
            String sortDirection) {

        return new ListWorkflowCatalogTypesQuery(
                requireText(catalogName, "catalogName"),
                active,
                blankToNull(locale),
                pageRequest(page, size, sortField, sortDirection));
    }

    public ResolveWorkflowCatalogTypeQuery toResolveWorkflowCatalogTypeQuery(
            String catalogName,
            String code,
            String locale) {

        return new ResolveWorkflowCatalogTypeQuery(
                requireText(catalogName, "catalogName"),
                WorkflowCode.of(code),
                blankToNull(locale));
    }

    public WorkflowDefinitionResponse toResponse(WorkflowDefinitionDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowDefinitionResponse(
                dto.id(),
                dto.code(),
                toResponse(dto.name()),
                toWorkflowTypeResponse(dto.type()),
                dto.status(),
                dto.version(),
                dto.steps().stream().map(this::toResponse).toList(),
                dto.transitions().stream().map(this::toResponse).toList(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public WorkflowStepResponse toResponse(WorkflowStepDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowStepResponse(
                dto.id(),
                dto.definitionId(),
                dto.code(),
                toResponse(dto.name()),
                dto.stepOrder(),
                dto.mandatory(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public WorkflowTransitionResponse toResponse(WorkflowTransitionDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowTransitionResponse(
                dto.id(),
                dto.definitionId(),
                dto.fromStepId(),
                dto.toStepId(),
                dto.decision(),
                dto.reasonRequired(),
                dto.commentRequired(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public WorkflowInstanceResponse toResponse(WorkflowInstanceDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowInstanceResponse(
                dto.id(),
                dto.definitionId(),
                dto.definitionVersion(),
                toResponse(dto.target()),
                dto.status(),
                dto.currentStepId(),
                toResponse(dto.startedBy()),
                dto.startedAt(),
                dto.completedAt(),
                dto.cancelledAt(),
                dto.correlationId(),
                dto.tasks().stream().map(this::toResponse).toList(),
                dto.actions().stream().map(this::toResponse).toList(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public WorkflowTaskResponse toResponse(WorkflowTaskDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowTaskResponse(
                dto.id(),
                dto.instanceId(),
                dto.stepId(),
                dto.status(),
                toResponse(dto.assignedActor()),
                dto.assignedOrganizationUnitId(),
                dto.assignedOrganizationUnitNameSnapshot(),
                toResponse(dto.priority()),
                dto.dueAt(),
                toResponse(dto.claimedBy()),
                dto.claimedAt(),
                toResponse(dto.completedBy()),
                dto.completedAt(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public WorkflowActionResponse toResponse(WorkflowActionDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowActionResponse(
                dto.id(),
                dto.instanceId(),
                dto.taskId(),
                dto.actionType(),
                dto.decision(),
                toResponse(dto.reason()),
                dto.decisionNote(),
                dto.comment(),
                toResponse(dto.actor()),
                dto.organizationUnitId(),
                dto.organizationUnitNameSnapshot(),
                dto.correlationId(),
                dto.actedAt());
    }

    public WorkflowAssignmentResponse toResponse(WorkflowAssignmentDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowAssignmentResponse(
                dto.id(),
                dto.taskId(),
                toResponse(dto.actor()),
                dto.organizationUnitId(),
                dto.organizationUnitNameSnapshot(),
                dto.roleCodeSnapshot(),
                dto.status(),
                dto.assignedAt(),
                dto.updatedAt());
    }

    public WorkflowDelegationResponse toResponse(WorkflowDelegationDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowDelegationResponse(
                dto.id(),
                dto.taskId(),
                toResponse(dto.fromActor()),
                toResponse(dto.toActor()),
                dto.toOrganizationUnitId(),
                dto.toOrganizationUnitNameSnapshot(),
                toResponse(dto.reason()),
                dto.delegatedAt());
    }

    public WorkflowEscalationResponse toResponse(WorkflowEscalationDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowEscalationResponse(
                dto.id(),
                dto.taskId(),
                dto.ruleId(),
                toResponse(dto.actor()),
                dto.organizationUnitId(),
                dto.organizationUnitNameSnapshot(),
                toResponse(dto.reason()),
                dto.note(),
                dto.status(),
                dto.escalatedAt());
    }

    public WorkflowCommentResponse toResponse(WorkflowCommentDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowCommentResponse(
                dto.id(),
                dto.instanceId(),
                dto.taskId(),
                toResponse(dto.actor()),
                dto.text(),
                dto.commentedAt());
    }

    public WorkflowTimelineResponse toResponse(WorkflowTimelineDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowTimelineResponse(
                dto.instanceId(),
                toResponse(dto.target()),
                dto.actions().stream().map(this::toResponse).toList(),
                dto.comments().stream().map(this::toResponse).toList(),
                dto.tasks().stream().map(this::toResponse).toList());
    }

    public WorkflowCatalogResponse toResponse(WorkflowCatalogDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowCatalogResponse(
                dto.id(),
                dto.catalogName(),
                dto.code(),
                dto.active(),
                dto.sortOrder(),
                dto.systemDefined(),
                dto.translations().stream().map(this::toResponse).toList(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public WorkflowCatalogTranslationResponse toResponse(WorkflowCatalogTranslationDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowCatalogTranslationResponse(
                dto.id(),
                dto.catalogId(),
                dto.locale(),
                dto.name(),
                dto.description(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public WorkflowLocalizedNameResponse toResponse(WorkflowLocalizedNameDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowLocalizedNameResponse(dto.nameAr(), dto.nameFr(), dto.nameEn());
    }

    public WorkflowTypeReferenceResponse toWorkflowTypeResponse(WorkflowTypeReferenceDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowTypeReferenceResponse(dto.id(), dto.code(), dto.label(), dto.locale());
    }

    public WorkflowReasonReferenceResponse toResponse(WorkflowReasonReferenceDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowReasonReferenceResponse(dto.id(), dto.code(), dto.label(), dto.locale());
    }

    public WorkflowPriorityReferenceResponse toResponse(WorkflowPriorityReferenceDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowPriorityReferenceResponse(dto.id(), dto.code(), dto.label(), dto.locale());
    }

    public WorkflowTargetReferenceResponse toResponse(WorkflowTargetReferenceDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowTargetReferenceResponse(
                dto.targetModule(),
                toWorkflowTypeResponse(dto.targetType()),
                dto.targetId(),
                dto.targetCodeSnapshot(),
                dto.targetLabelSnapshot());
    }

    public WorkflowActorReferenceResponse toResponse(WorkflowActorReferenceDto dto) {
        if (dto == null) {
            return null;
        }

        return new WorkflowActorReferenceResponse(
                dto.actorId(),
                dto.actorUsernameSnapshot(),
                dto.actorDisplayNameSnapshot(),
                dto.roleCodeSnapshot());
    }

    public <T, R> WorkflowPageResponse<R> toPageResponse(
            PageResult<T> page,
            Function<T, R> mapper) {

        Objects.requireNonNull(mapper, "Workflow page mapper must not be null.");

        if (page == null) {
            return WorkflowPageResponse.empty(DEFAULT_PAGE, DEFAULT_SIZE);
        }

        return new WorkflowPageResponse<>(
                page.items().stream().map(mapper).toList(),
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages());
    }

    private WorkflowLocalizedName localizedName(WorkflowLocalizedNameRequest request) {
        Objects.requireNonNull(request, "WorkflowLocalizedNameRequest must not be null.");
        return WorkflowLocalizedName.of(request.nameAr(), request.nameFr(), request.nameEn());
    }

    private WorkflowTypeReference workflowType(WorkflowTypeReferenceRequest request) {
        Objects.requireNonNull(request, "WorkflowTypeReferenceRequest must not be null.");
        return WorkflowTypeReference.of(request.id(), request.code());
    }

    private WorkflowTargetTypeReference targetType(WorkflowTargetTypeReferenceRequest request) {
        Objects.requireNonNull(request, "WorkflowTargetTypeReferenceRequest must not be null.");
        return WorkflowTargetTypeReference.of(request.id(), request.code());
    }

    private WorkflowReasonReference reasonReference(WorkflowReasonReferenceRequest request) {
        if (request == null) {
            return null;
        }

        return WorkflowReasonReference.of(request.id(), request.code());
    }

    private WorkflowPriorityReference priorityReference(WorkflowPriorityReferenceRequest request) {
        if (request == null) {
            return null;
        }

        return WorkflowPriorityReference.of(request.id(), request.code());
    }

    private WorkflowTargetReference targetReference(WorkflowTargetReferenceRequest request) {
        if (request == null) {
            return null;
        }

        return WorkflowTargetReference.of(
                request.targetModule(),
                targetType(request.targetType()),
                request.targetId(),
                blankToNull(request.targetCodeSnapshot()),
                blankToNull(request.targetLabelSnapshot()));
    }

    private WorkflowActorReference actorReference(WorkflowActorReferenceRequest request) {
        if (request == null) {
            return null;
        }

        return WorkflowActorReference.of(
                request.actorId(),
                blankToNull(request.actorUsernameSnapshot()),
                request.actorDisplayNameSnapshot(),
                blankToNull(request.roleCodeSnapshot()));
    }

    private WorkflowOrganizationReference organizationReference(WorkflowOrganizationReferenceRequest request) {
        if (request == null) {
            return null;
        }

        return WorkflowOrganizationReference.of(
                request.organizationUnitId(),
                request.organizationUnitNameSnapshot(),
                blankToNull(request.roleCodeSnapshot()));
    }

    private WorkflowTypeReference optionalWorkflowType(String typeId, String typeCode) {
        if (isBlank(typeId) && isBlank(typeCode)) {
            return null;
        }

        return WorkflowTypeReference.of(
                requireText(typeId, "typeId"),
                requireText(typeCode, "typeCode"));
    }

    private WorkflowDefinitionId optionalDefinitionId(String definitionId) {
        return isBlank(definitionId) ? null : WorkflowDefinitionId.of(definitionId);
    }

    private WorkflowInstanceId optionalInstanceId(String instanceId) {
        return isBlank(instanceId) ? null : WorkflowInstanceId.of(instanceId);
    }

    private WorkflowTaskId optionalTaskId(String taskId) {
        return isBlank(taskId) ? null : WorkflowTaskId.of(taskId);
    }

    private WorkflowDecisionNote optionalDecisionNote(String value) {
        return isBlank(value) ? null : WorkflowDecisionNote.of(value);
    }

    private WorkflowCommentText optionalCommentText(String value) {
        return isBlank(value) ? null : WorkflowCommentText.of(value);
    }

    private WorkflowCorrelationId optionalCorrelationId(String value) {
        return isBlank(value) ? null : WorkflowCorrelationId.of(value);
    }

    private PageRequest pageRequest(Integer page, Integer size, String sortField, String sortDirection) {
        int normalizedPage = page == null ? DEFAULT_PAGE : Math.max(0, page);
        int normalizedSize = size == null ? DEFAULT_SIZE : Math.max(1, size);

        if (isBlank(sortField)) {
            return PageRequest.of(normalizedPage, normalizedSize);
        }

        return PageRequest.sorted(
                normalizedPage,
                normalizedSize,
                sortField.trim(),
                optionalEnum(sortDirection, SortDirection.class));
    }

    private <E extends Enum<E>> E optionalEnum(String value, Class<E> enumClass) {
        if (isBlank(value)) {
            return null;
        }

        return enumValue(value, enumClass);
    }

    private <E extends Enum<E>> E enumValue(String value, Class<E> enumClass) {
        Objects.requireNonNull(enumClass, "Enum class must not be null.");
        return Enum.valueOf(enumClass, requireText(value, enumClass.getSimpleName()).toUpperCase(Locale.ROOT));
    }

    private String blankToNull(String value) {
        return isBlank(value) ? null : value.trim();
    }

    private String requireText(String value, String fieldName) {
        if (isBlank(value)) {
            throw new IllegalArgumentException(fieldName + " must not be null or blank.");
        }

        return value.trim();
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
