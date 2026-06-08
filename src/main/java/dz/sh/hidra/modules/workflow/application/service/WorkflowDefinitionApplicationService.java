/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Service
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Application service for workflow definition use cases.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.application.command.ActivateWorkflowDefinitionCommand;
import dz.sh.hidra.modules.workflow.application.command.CreateWorkflowDefinitionCommand;
import dz.sh.hidra.modules.workflow.application.command.CreateWorkflowStepCommand;
import dz.sh.hidra.modules.workflow.application.command.CreateWorkflowTransitionCommand;
import dz.sh.hidra.modules.workflow.application.command.DeactivateWorkflowDefinitionCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowActorReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowDefinitionDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowLocalizedNameDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowPriorityReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowReasonReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowStepDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTargetReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTransitionDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTypeReferenceDto;
import dz.sh.hidra.modules.workflow.application.port.in.ActivateWorkflowDefinitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.CreateWorkflowDefinitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.CreateWorkflowStepUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.CreateWorkflowTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.DeactivateWorkflowDefinitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowDefinitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListWorkflowDefinitionsUseCase;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowDefinitionRepositoryPort;
import dz.sh.hidra.modules.workflow.application.query.GetWorkflowDefinitionQuery;
import dz.sh.hidra.modules.workflow.application.query.ListWorkflowDefinitionsQuery;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStep;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTransition;
import dz.sh.hidra.modules.workflow.domain.service.WorkflowDefinitionDomainService;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowLocalizedName;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowPriorityReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowReasonReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetTypeReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTypeReference;


/**
 * Application service for workflow definition use cases.
 */
public final class WorkflowDefinitionApplicationService implements
        CreateWorkflowDefinitionUseCase,
        ActivateWorkflowDefinitionUseCase,
        DeactivateWorkflowDefinitionUseCase,
        GetWorkflowDefinitionUseCase,
        ListWorkflowDefinitionsUseCase,
        CreateWorkflowStepUseCase,
        CreateWorkflowTransitionUseCase {

    private final WorkflowDefinitionRepositoryPort definitionRepository;
    private final WorkflowDefinitionDomainService definitionDomainService;

    public WorkflowDefinitionApplicationService(
            WorkflowDefinitionRepositoryPort definitionRepository,
            WorkflowDefinitionDomainService definitionDomainService) {
        this.definitionRepository = Objects.requireNonNull(definitionRepository, "WorkflowDefinitionRepositoryPort must not be null.");
        this.definitionDomainService = Objects.requireNonNull(definitionDomainService, "WorkflowDefinitionDomainService must not be null.");
    }

    @Override
    public WorkflowDefinitionDto createWorkflowDefinition(CreateWorkflowDefinitionCommand command) {
        Objects.requireNonNull(command, "CreateWorkflowDefinitionCommand must not be null.");
        if (definitionRepository.existsByCode(command.code())) {
            throw new BusinessRuleViolationException("Workflow definition already exists: " + command.code().value());
        }
        WorkflowDefinition definition = definitionDomainService.createDefinition(command.code(), command.name(), command.type());
        return toDto(definitionRepository.save(definition));
    }

    @Override
    public WorkflowDefinitionDto activateWorkflowDefinition(ActivateWorkflowDefinitionCommand command) {
        Objects.requireNonNull(command, "ActivateWorkflowDefinitionCommand must not be null.");
        return toDto(definitionRepository.save(definitionDomainService.activate(findDefinition(command.definitionId()))));
    }

    @Override
    public WorkflowDefinitionDto deactivateWorkflowDefinition(DeactivateWorkflowDefinitionCommand command) {
        Objects.requireNonNull(command, "DeactivateWorkflowDefinitionCommand must not be null.");
        return toDto(definitionRepository.save(definitionDomainService.deactivate(findDefinition(command.definitionId()))));
    }

    @Override
    public WorkflowDefinitionDto getWorkflowDefinition(GetWorkflowDefinitionQuery query) {
        Objects.requireNonNull(query, "GetWorkflowDefinitionQuery must not be null.");
        return toDto(findDefinition(query.definitionId()));
    }

    @Override
    public PageResult<WorkflowDefinitionDto> listWorkflowDefinitions(ListWorkflowDefinitionsQuery query) {
        Objects.requireNonNull(query, "ListWorkflowDefinitionsQuery must not be null.");
        PageResult<WorkflowDefinition> page = definitionRepository.findAll(query.searchTerm(), query.type(), query.status(), query.pageRequest());
        return new PageResult<>(page.items().stream().map(this::toDto).toList(), page.page(), page.size(), page.totalElements(), page.totalPages());
    }

    @Override
    public WorkflowDefinitionDto createWorkflowStep(CreateWorkflowStepCommand command) {
        Objects.requireNonNull(command, "CreateWorkflowStepCommand must not be null.");
        WorkflowDefinition updated = definitionDomainService.addStep(
                findDefinition(command.definitionId()),
                command.stepCode().value(),
                command.name(),
                command.stepOrder(),
                command.mandatory());
        return toDto(definitionRepository.save(updated));
    }

    @Override
    public WorkflowDefinitionDto createWorkflowTransition(CreateWorkflowTransitionCommand command) {
        Objects.requireNonNull(command, "CreateWorkflowTransitionCommand must not be null.");
        WorkflowDefinition updated = definitionDomainService.addTransition(
                findDefinition(command.definitionId()),
                command.fromStepId(),
                command.toStepId(),
                command.decision(),
                command.reasonRequired(),
                command.commentRequired());
        return toDto(definitionRepository.save(updated));
    }

    private WorkflowDefinition findDefinition(dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId definitionId) {
        return definitionRepository.findById(definitionId)
                .orElseThrow(() -> notFound("Workflow definition not found: " + definitionId.value()));
    }

    private WorkflowDefinitionDto toDto(WorkflowDefinition definition) {
        return new WorkflowDefinitionDto(
                definition.id().value(),
                definition.code().value(),
                toLocalizedNameDto(definition.name()),
                toTypeReferenceDto(definition.type()),
                definition.status().name(),
                definition.version().value(),
                definition.steps().stream().map(this::toStepDto).toList(),
                definition.transitions().stream().map(this::toTransitionDto).toList(),
                definition.createdAt(),
                definition.updatedAt());
    }

    private WorkflowStepDto toStepDto(WorkflowStep step) {
        return new WorkflowStepDto(step.id().value(), step.definitionId().value(), step.code(), toLocalizedNameDto(step.name()), step.stepOrder(), step.mandatory(), step.createdAt(), step.updatedAt());
    }

    private WorkflowTransitionDto toTransitionDto(WorkflowTransition transition) {
        return new WorkflowTransitionDto(transition.id().value(), transition.definitionId().value(), transition.fromStepId().value(), transition.toStepId().value(), transition.decision().name(), transition.reasonRequired(), transition.commentRequired(), transition.createdAt(), transition.updatedAt());
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
