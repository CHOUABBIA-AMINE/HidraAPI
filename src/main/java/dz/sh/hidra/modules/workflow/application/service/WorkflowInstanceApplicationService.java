/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Service
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Application service for workflow instance use cases.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import java.util.List;
import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.application.command.CancelWorkflowInstanceCommand;
import dz.sh.hidra.modules.workflow.application.command.StartWorkflowInstanceCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowActionDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowActorReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowInstanceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowLocalizedNameDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowPriorityReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowReasonReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTargetReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTaskDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTimelineDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTypeReferenceDto;
import dz.sh.hidra.modules.workflow.application.port.in.CancelWorkflowInstanceUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowInstanceUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowTimelineUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListWorkflowInstancesUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.StartWorkflowInstanceUseCase;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowAuditEventPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowDefinitionRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTargetLookupPort;
import dz.sh.hidra.modules.workflow.application.query.GetWorkflowInstanceQuery;
import dz.sh.hidra.modules.workflow.application.query.GetWorkflowTimelineQuery;
import dz.sh.hidra.modules.workflow.application.query.ListWorkflowInstancesQuery;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowAction;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.service.WorkflowInstanceDomainService;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowLocalizedName;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowPriorityReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowReasonReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetTypeReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTypeReference;


/**
 * Application service for workflow instance use cases.
 */
public final class WorkflowInstanceApplicationService implements
        StartWorkflowInstanceUseCase,
        CancelWorkflowInstanceUseCase,
        GetWorkflowInstanceUseCase,
        ListWorkflowInstancesUseCase,
        GetWorkflowTimelineUseCase {

    private final WorkflowDefinitionRepositoryPort definitionRepository;
    private final WorkflowInstanceRepositoryPort instanceRepository;
    private final WorkflowTargetLookupPort targetLookupPort;
    private final WorkflowAuditEventPort auditEventPort;
    private final WorkflowInstanceDomainService instanceDomainService;

    public WorkflowInstanceApplicationService(
            WorkflowDefinitionRepositoryPort definitionRepository,
            WorkflowInstanceRepositoryPort instanceRepository,
            WorkflowTargetLookupPort targetLookupPort,
            WorkflowAuditEventPort auditEventPort,
            WorkflowInstanceDomainService instanceDomainService) {
        this.definitionRepository = Objects.requireNonNull(definitionRepository, "WorkflowDefinitionRepositoryPort must not be null.");
        this.instanceRepository = Objects.requireNonNull(instanceRepository, "WorkflowInstanceRepositoryPort must not be null.");
        this.targetLookupPort = Objects.requireNonNull(targetLookupPort, "WorkflowTargetLookupPort must not be null.");
        this.auditEventPort = Objects.requireNonNull(auditEventPort, "WorkflowAuditEventPort must not be null.");
        this.instanceDomainService = Objects.requireNonNull(instanceDomainService, "WorkflowInstanceDomainService must not be null.");
    }

    @Override
    public WorkflowInstanceDto startWorkflowInstance(StartWorkflowInstanceCommand command) {
        Objects.requireNonNull(command, "StartWorkflowInstanceCommand must not be null.");
        WorkflowDefinition definition = definitionRepository.findById(command.definitionId())
                .orElseThrow(() -> notFound("Workflow definition not found: " + command.definitionId().value()));
        WorkflowTargetReference target = targetLookupPort.resolve(command.target());
        if (!targetLookupPort.canStartWorkflow(target)) {
            throw new BusinessRuleViolationException("Workflow target cannot start workflow: " + target.targetId());
        }
        WorkflowInstance saved = instanceRepository.save(instanceDomainService.startTelemetryReadingValidation(definition, target, command.startedBy(), command.correlationId()));
        auditEventPort.recordWorkflowStarted(saved);
        return toDto(saved);
    }

    @Override
    public WorkflowInstanceDto cancelWorkflowInstance(CancelWorkflowInstanceCommand command) {
        Objects.requireNonNull(command, "CancelWorkflowInstanceCommand must not be null.");
        return toDto(instanceRepository.save(instanceDomainService.cancel(findInstance(command.instanceId()))));
    }

    @Override
    public WorkflowInstanceDto getWorkflowInstance(GetWorkflowInstanceQuery query) {
        Objects.requireNonNull(query, "GetWorkflowInstanceQuery must not be null.");
        return toDto(findInstance(query.instanceId()));
    }

    @Override
    public PageResult<WorkflowInstanceDto> listWorkflowInstances(ListWorkflowInstancesQuery query) {
        Objects.requireNonNull(query, "ListWorkflowInstancesQuery must not be null.");
        PageResult<WorkflowInstance> page = instanceRepository.findAll(query.definitionId(), query.target(), query.status(), query.startedBy(), query.pageRequest());
        return new PageResult<>(page.items().stream().map(this::toDto).toList(), page.page(), page.size(), page.totalElements(), page.totalPages());
    }

    @Override
    public WorkflowTimelineDto getWorkflowTimeline(GetWorkflowTimelineQuery query) {
        Objects.requireNonNull(query, "GetWorkflowTimelineQuery must not be null.");
        WorkflowInstance instance = findInstance(query.instanceId());
        return new WorkflowTimelineDto(instance.id().value(), toTargetReferenceDto(instance.target()), instance.actions().stream().map(this::toActionDto).toList(), List.of(), instance.tasks().stream().map(this::toTaskDto).toList());
    }

    private WorkflowInstance findInstance(dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId instanceId) {
        return instanceRepository.findById(instanceId)
                .orElseThrow(() -> notFound("Workflow instance not found: " + instanceId.value()));
    }

    private WorkflowInstanceDto toDto(WorkflowInstance instance) {
        return new WorkflowInstanceDto(instance.id().value(), instance.definitionId().value(), instance.definitionVersion().value(), toTargetReferenceDto(instance.target()), instance.status().name(), instance.currentStepId() == null ? null : instance.currentStepId().value(), toActorReferenceDto(instance.startedBy()), instance.startedAt(), instance.completedAt(), instance.cancelledAt(), instance.correlationId() == null ? null : instance.correlationId().value(), instance.tasks().stream().map(this::toTaskDto).toList(), instance.actions().stream().map(this::toActionDto).toList(), instance.createdAt(), instance.updatedAt());
    }

    private WorkflowTaskDto toTaskDto(WorkflowTask task) {
        return new WorkflowTaskDto(task.id().value(), task.instanceId().value(), task.stepId().value(), task.status().name(), toActorReferenceDto(task.assignedActor()), task.assignedOrganization() == null ? null : task.assignedOrganization().organizationUnitId(), task.assignedOrganization() == null ? null : task.assignedOrganization().organizationUnitNameSnapshot(), toPriorityReferenceDto(task.priority()), task.dueDate() == null ? null : task.dueDate().value(), toActorReferenceDto(task.claimedBy()), task.claimedAt(), toActorReferenceDto(task.completedBy()), task.completedAt(), task.createdAt(), task.updatedAt());
    }

    private WorkflowActionDto toActionDto(WorkflowAction action) {
        return new WorkflowActionDto(action.id().value(), action.instanceId().value(), action.taskId() == null ? null : action.taskId().value(), action.actionType().name(), action.decision() == null ? null : action.decision().name(), toReasonReferenceDto(action.reason()), action.decisionNote() == null ? null : action.decisionNote().value(), action.comment() == null ? null : action.comment().value(), toActorReferenceDto(action.actor()), action.organization() == null ? null : action.organization().organizationUnitId(), action.organization() == null ? null : action.organization().organizationUnitNameSnapshot(), action.correlationId() == null ? null : action.correlationId().value(), action.actedAt());
    }


    private static WorkflowLocalizedNameDto toLocalizedNameDto(WorkflowLocalizedName name) {
        if (name == null) {
            return null;
        }
        return new WorkflowLocalizedNameDto(name.nameAr(), name.nameFr(), name.nameEn());
    }

    private static WorkflowTypeReferenceDto toTypeReferenceDto(WorkflowTypeReference reference) {
        if (reference == null) {
            return null;
        }
        return new WorkflowTypeReferenceDto(reference.id(), reference.code().value(), reference.code().value(), null);
    }

    private static WorkflowTypeReferenceDto toTargetTypeReferenceDto(WorkflowTargetTypeReference reference) {
        if (reference == null) {
            return null;
        }
        return new WorkflowTypeReferenceDto(reference.id(), reference.code().value(), reference.code().value(), null);
    }

    private static WorkflowReasonReferenceDto toReasonReferenceDto(WorkflowReasonReference reference) {
        if (reference == null) {
            return null;
        }
        return new WorkflowReasonReferenceDto(reference.id(), reference.code().value(), reference.code().value(), null);
    }

    private static WorkflowPriorityReferenceDto toPriorityReferenceDto(WorkflowPriorityReference reference) {
        if (reference == null) {
            return null;
        }
        return new WorkflowPriorityReferenceDto(reference.id(), reference.code().value(), reference.code().value(), null);
    }

    private static WorkflowActorReferenceDto toActorReferenceDto(WorkflowActorReference reference) {
        if (reference == null) {
            return null;
        }
        return new WorkflowActorReferenceDto(
                reference.actorId(),
                reference.actorUsernameSnapshot(),
                reference.actorDisplayNameSnapshot(),
                reference.roleCodeSnapshot());
    }

    private static WorkflowTargetReferenceDto toTargetReferenceDto(WorkflowTargetReference target) {
        if (target == null) {
            return null;
        }
        return new WorkflowTargetReferenceDto(
                target.targetModule(),
                toTargetTypeReferenceDto(target.targetType()),
                target.targetId(),
                target.targetCodeSnapshot(),
                target.targetLabelSnapshot());
    }

    private static BusinessRuleViolationException notFound(String message) {
        return new BusinessRuleViolationException(message);
    }

}
