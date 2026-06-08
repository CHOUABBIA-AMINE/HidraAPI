/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Service
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Application service for workflow task use cases.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.workflow.application.command.ApproveWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.AssignWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.ClaimWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.CommentWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.DelegateWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.EscalateWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.RejectWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.RequestWorkflowCorrectionCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowCommentDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTaskDto;
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
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowAuditEventPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTaskRepositoryPort;
import dz.sh.hidra.modules.workflow.application.query.GetWorkflowTaskQuery;
import dz.sh.hidra.modules.workflow.application.query.ListMyWorkflowTasksQuery;
import dz.sh.hidra.modules.workflow.application.query.ListWorkflowTasksQuery;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowAction;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowComment;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.service.WorkflowDecisionDomainService;
import dz.sh.hidra.modules.workflow.domain.service.WorkflowTaskDomainService;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowActorReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowLocalizedNameDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowPriorityReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowReasonReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTargetReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTypeReferenceDto;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowLocalizedName;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowPriorityReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowReasonReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetTypeReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTypeReference;


/**
 * Application service for workflow task use cases.
 */
public final class WorkflowTaskApplicationService implements
        AssignWorkflowTaskUseCase,
        ClaimWorkflowTaskUseCase,
        ApproveWorkflowTaskUseCase,
        RejectWorkflowTaskUseCase,
        RequestWorkflowCorrectionUseCase,
        DelegateWorkflowTaskUseCase,
        EscalateWorkflowTaskUseCase,
        CommentWorkflowTaskUseCase,
        GetWorkflowTaskUseCase,
        ListWorkflowTasksUseCase,
        ListMyWorkflowTasksUseCase {

    private final WorkflowInstanceRepositoryPort instanceRepository;
    private final WorkflowTaskRepositoryPort taskRepository;
    private final WorkflowAuditEventPort auditEventPort;
    private final WorkflowTaskDomainService taskDomainService;
    private final WorkflowDecisionDomainService decisionDomainService;

    public WorkflowTaskApplicationService(
            WorkflowInstanceRepositoryPort instanceRepository,
            WorkflowTaskRepositoryPort taskRepository,
            WorkflowAuditEventPort auditEventPort,
            WorkflowTaskDomainService taskDomainService,
            WorkflowDecisionDomainService decisionDomainService) {
        this.instanceRepository = Objects.requireNonNull(instanceRepository, "WorkflowInstanceRepositoryPort must not be null.");
        this.taskRepository = Objects.requireNonNull(taskRepository, "WorkflowTaskRepositoryPort must not be null.");
        this.auditEventPort = Objects.requireNonNull(auditEventPort, "WorkflowAuditEventPort must not be null.");
        this.taskDomainService = Objects.requireNonNull(taskDomainService, "WorkflowTaskDomainService must not be null.");
        this.decisionDomainService = Objects.requireNonNull(decisionDomainService, "WorkflowDecisionDomainService must not be null.");
    }

    @Override
    public WorkflowTaskDto assignWorkflowTask(AssignWorkflowTaskCommand command) {
        Objects.requireNonNull(command, "AssignWorkflowTaskCommand must not be null.");
        WorkflowTask saved = taskRepository.save(taskDomainService.delegateTask(findTask(command.taskId()), command.assignedActor(), command.assignedOrganization()));
        auditEventPort.recordWorkflowTaskChanged(saved);
        return toTaskDto(saved);
    }

    @Override
    public WorkflowTaskDto claimWorkflowTask(ClaimWorkflowTaskCommand command) {
        Objects.requireNonNull(command, "ClaimWorkflowTaskCommand must not be null.");
        WorkflowTask saved = taskRepository.save(taskDomainService.claimTask(findTask(command.taskId()), command.actor()));
        auditEventPort.recordWorkflowTaskChanged(saved);
        return toTaskDto(saved);
    }

    @Override
    public WorkflowTaskDto approveWorkflowTask(ApproveWorkflowTaskCommand command) {
        Objects.requireNonNull(command, "ApproveWorkflowTaskCommand must not be null.");
        return decideAndClose(command.instanceId(), command.taskId(), WorkflowDecision.APPROVE, command.reason(), command.note(), command.comment(), command.actor(), command.organization(), command.correlationId());
    }

    @Override
    public WorkflowTaskDto rejectWorkflowTask(RejectWorkflowTaskCommand command) {
        Objects.requireNonNull(command, "RejectWorkflowTaskCommand must not be null.");
        return decideAndClose(command.instanceId(), command.taskId(), WorkflowDecision.REJECT, command.reason(), command.note(), command.comment(), command.actor(), command.organization(), command.correlationId());
    }

    @Override
    public WorkflowTaskDto requestWorkflowCorrection(RequestWorkflowCorrectionCommand command) {
        Objects.requireNonNull(command, "RequestWorkflowCorrectionCommand must not be null.");
        WorkflowInstance instance = findInstance(command.instanceId());
        WorkflowTask task = findTask(command.taskId());
        WorkflowAction action = decisionDomainService.recordDecision(instance, task, WorkflowDecision.REQUEST_CORRECTION, command.reason(), null, command.comment(), command.actor(), command.organization(), command.correlationId());
        instanceRepository.save(instance.recordAction(action));
        auditEventPort.recordWorkflowAction(action);
        return toTaskDto(task);
    }

    @Override
    public WorkflowTaskDto delegateWorkflowTask(DelegateWorkflowTaskCommand command) {
        Objects.requireNonNull(command, "DelegateWorkflowTaskCommand must not be null.");
        WorkflowTask saved = taskRepository.save(taskDomainService.delegateTask(findTask(command.taskId()), command.toActor(), command.toOrganization()));
        auditEventPort.recordWorkflowTaskChanged(saved);
        return toTaskDto(saved);
    }

    @Override
    public WorkflowTaskDto escalateWorkflowTask(EscalateWorkflowTaskCommand command) {
        Objects.requireNonNull(command, "EscalateWorkflowTaskCommand must not be null.");
        WorkflowTask task = findTask(command.taskId());
        WorkflowInstance instance = findInstance(task.instanceId());
        WorkflowAction action = decisionDomainService.recordDecision(instance, task, WorkflowDecision.ESCALATE, command.reason(), command.note(), null, command.actor(), command.organization(), command.correlationId());
        instanceRepository.save(instance.recordAction(action));
        auditEventPort.recordWorkflowAction(action);
        return toTaskDto(task);
    }

    @Override
    public WorkflowCommentDto commentWorkflowTask(CommentWorkflowTaskCommand command) {
        Objects.requireNonNull(command, "CommentWorkflowTaskCommand must not be null.");
        WorkflowInstance instance = findInstance(command.instanceId());
        WorkflowTask task = command.taskId() == null ? null : findTask(command.taskId());
        WorkflowComment comment = taskDomainService.addComment(instance, task, command.actor(), command.comment());
        WorkflowAction action = decisionDomainService.recordComment(instance, task, command.comment(), command.actor(), command.organization(), command.correlationId());
        instanceRepository.save(instance.recordAction(action));
        auditEventPort.recordWorkflowAction(action);
        return new WorkflowCommentDto(comment.id().value(), comment.instanceId().value(), comment.taskId() == null ? null : comment.taskId().value(), toActorReferenceDto(comment.actor()), comment.text().value(), comment.commentedAt());
    }

    @Override
    public WorkflowTaskDto getWorkflowTask(GetWorkflowTaskQuery query) {
        Objects.requireNonNull(query, "GetWorkflowTaskQuery must not be null.");
        return toTaskDto(findTask(query.taskId()));
    }

    @Override
    public PageResult<WorkflowTaskDto> listWorkflowTasks(ListWorkflowTasksQuery query) {
        Objects.requireNonNull(query, "ListWorkflowTasksQuery must not be null.");
        return toTaskPage(taskRepository.findAll(query.instanceId(), query.status(), query.assignedActor(), query.assignedOrganization(), query.pageRequest()));
    }

    @Override
    public PageResult<WorkflowTaskDto> listMyWorkflowTasks(ListMyWorkflowTasksQuery query) {
        Objects.requireNonNull(query, "ListMyWorkflowTasksQuery must not be null.");
        return toTaskPage(taskRepository.findVisibleToActor(query.actor(), query.organization(), query.status(), query.pageRequest()));
    }

    private WorkflowTaskDto decideAndClose(
            dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId instanceId,
            dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskId taskId,
            WorkflowDecision decision,
            WorkflowReasonReference reason,
            dz.sh.hidra.modules.workflow.domain.value.WorkflowDecisionNote note,
            dz.sh.hidra.modules.workflow.domain.value.WorkflowCommentText comment,
            WorkflowActorReference actor,
            dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference organization,
            dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId correlationId) {
        WorkflowInstance instance = findInstance(instanceId);
        WorkflowTask task = findTask(taskId);
        WorkflowAction action = decisionDomainService.recordDecision(instance, task, decision, reason, note, comment, actor, organization, correlationId);
        WorkflowTask saved = WorkflowDecision.APPROVE.equals(decision) ? taskRepository.save(taskDomainService.approveTask(task, actor)) : taskRepository.save(taskDomainService.rejectTask(task, actor));
        instanceRepository.save(instance.recordAction(action));
        auditEventPort.recordWorkflowAction(action);
        auditEventPort.recordWorkflowTaskChanged(saved);
        return toTaskDto(saved);
    }

    private WorkflowTask findTask(dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskId taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> notFound("Workflow task not found: " + taskId.value()));
    }

    private WorkflowInstance findInstance(dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId instanceId) {
        return instanceRepository.findById(instanceId).orElseThrow(() -> notFound("Workflow instance not found: " + instanceId.value()));
    }

    private PageResult<WorkflowTaskDto> toTaskPage(PageResult<WorkflowTask> page) {
        return new PageResult<>(page.items().stream().map(this::toTaskDto).toList(), page.page(), page.size(), page.totalElements(), page.totalPages());
    }

    private WorkflowTaskDto toTaskDto(WorkflowTask task) {
        return new WorkflowTaskDto(task.id().value(), task.instanceId().value(), task.stepId().value(), task.status().name(), toActorReferenceDto(task.assignedActor()), task.assignedOrganization() == null ? null : task.assignedOrganization().organizationUnitId(), task.assignedOrganization() == null ? null : task.assignedOrganization().organizationUnitNameSnapshot(), toPriorityReferenceDto(task.priority()), task.dueDate() == null ? null : task.dueDate().value(), toActorReferenceDto(task.claimedBy()), task.claimedAt(), toActorReferenceDto(task.completedBy()), task.completedAt(), task.createdAt(), task.updatedAt());
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
