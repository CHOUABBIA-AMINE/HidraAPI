/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Mapper
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper
 *
 * @Description : Mapper between workflow domain models and workflow JPA entities.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

import dz.sh.hidra.modules.workflow.domain.model.WorkflowAction;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowAssignment;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowComment;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDelegation;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowEscalationRule;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStateHistory;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStep;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTransition;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTypeCatalog;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTypeTranslation;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActionType;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowAssignmentId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowAssignmentStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCatalogId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCatalogTranslationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCommentId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCommentText;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecisionNote;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDelegationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDueDate;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowEscalationRuleId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowLocalizedName;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowName;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowPriorityReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowReasonReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowStepId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetTypeReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTransitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTypeReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowVersion;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowActionJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowAssignmentJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowCommentJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowDefinitionJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowDelegationJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowEscalationRuleJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowInstanceJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowStateHistoryJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowStepJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTaskJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTransitionJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTypeCatalogJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTypeTranslationJpaEntity;

/**
 * Mapper between workflow domain models and workflow JPA entities.
 *
 * <p>Architecture role:
 * Infrastructure-only mapper. It must not be used by domain, application, REST, telemetry, topology,
 * identity, organization, planning, monitoring, incidents, audit, integration, analytics, reporting,
 * or notification code.
 *
 * <p>Catalog note:
 * Persistence tables store catalog foreign key ids. The mapper restores catalog references with the
 * catalog id as the stable id and fallback code. API/catalog enrichment can resolve localized labels
 * from the catalog repository.
 */
public final class WorkflowPersistenceMapper {

    public WorkflowTypeCatalog toDomain(
            WorkflowTypeCatalogJpaEntity entity,
            List<WorkflowTypeTranslationJpaEntity> translationEntities) {

        Objects.requireNonNull(entity, "Workflow type catalog entity must not be null.");

        List<WorkflowTypeTranslation> translations = translationEntities == null
                ? List.of()
                : translationEntities.stream().map(this::toDomain).toList();

        return WorkflowTypeCatalog.restore(
                WorkflowCatalogId.of(entity.getId()),
                entity.getCatalogName(),
                WorkflowCode.of(entity.getCode()),
                Boolean.TRUE.equals(entity.getActive()),
                entity.getSortOrder() == null ? 0 : entity.getSortOrder(),
                Boolean.TRUE.equals(entity.getSystemDefined()),
                translations,
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public WorkflowTypeTranslation toDomain(WorkflowTypeTranslationJpaEntity entity) {
        Objects.requireNonNull(entity, "Workflow type translation entity must not be null.");

        return WorkflowTypeTranslation.restore(
                WorkflowCatalogTranslationId.of(entity.getId()),
                WorkflowCatalogId.of(entity.getTypeId()),
                entity.getLocale(),
                WorkflowName.of(entity.getName()),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public WorkflowTypeCatalogJpaEntity toEntity(WorkflowTypeCatalog catalog) {
        Objects.requireNonNull(catalog, "Workflow type catalog must not be null.");

        return new WorkflowTypeCatalogJpaEntity(
                catalog.id().value(),
                catalog.catalogName(),
                catalog.code().value(),
                catalog.active(),
                catalog.sortOrder(),
                catalog.systemDefined(),
                catalog.createdAt(),
                catalog.updatedAt());
    }

    public WorkflowTypeTranslationJpaEntity toEntity(WorkflowTypeTranslation translation) {
        Objects.requireNonNull(translation, "Workflow type translation must not be null.");

        return new WorkflowTypeTranslationJpaEntity(
                translation.id().value(),
                translation.typeId().value(),
                translation.locale(),
                translation.name().value(),
                translation.description(),
                translation.createdAt(),
                translation.updatedAt());
    }

    public WorkflowDefinition toDomain(
            WorkflowDefinitionJpaEntity entity,
            List<WorkflowStepJpaEntity> stepEntities,
            List<WorkflowTransitionJpaEntity> transitionEntities) {

        Objects.requireNonNull(entity, "Workflow definition entity must not be null.");

        List<WorkflowStep> steps = stepEntities == null
                ? List.of()
                : stepEntities.stream().map(this::toDomain).toList();

        List<WorkflowTransition> transitions = transitionEntities == null
                ? List.of()
                : transitionEntities.stream().map(this::toDomain).toList();

        return WorkflowDefinition.restore(
                WorkflowDefinitionId.of(entity.getId()),
                WorkflowCode.of(entity.getCode()),
                WorkflowLocalizedName.of(entity.getNameAr(), entity.getNameFr(), entity.getNameEn()),
                WorkflowTypeReference.of(entity.getTypeId(), entity.getTypeId()),
                WorkflowDefinitionStatus.valueOf(entity.getStatus()),
                WorkflowVersion.of(entity.getVersion()),
                steps,
                transitions,
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public WorkflowDefinitionJpaEntity toEntity(WorkflowDefinition definition) {
        Objects.requireNonNull(definition, "Workflow definition must not be null.");

        return new WorkflowDefinitionJpaEntity(
                definition.id().value(),
                definition.code().value(),
                definition.name().nameAr(),
                definition.name().nameFr(),
                definition.name().nameEn(),
                definition.type().id(),
                definition.status().name(),
                definition.version().value(),
                definition.createdAt(),
                definition.updatedAt());
    }

    public WorkflowStep toDomain(WorkflowStepJpaEntity entity) {
        Objects.requireNonNull(entity, "Workflow step entity must not be null.");

        return WorkflowStep.restore(
                WorkflowStepId.of(entity.getId()),
                WorkflowDefinitionId.of(entity.getDefinitionId()),
                entity.getCode(),
                WorkflowLocalizedName.of(entity.getNameAr(), entity.getNameFr(), entity.getNameEn()),
                entity.getStepOrder(),
                Boolean.TRUE.equals(entity.getMandatory()),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public WorkflowStepJpaEntity toEntity(WorkflowStep step) {
        Objects.requireNonNull(step, "Workflow step must not be null.");

        return new WorkflowStepJpaEntity(
                step.id().value(),
                step.definitionId().value(),
                step.code(),
                step.name().nameAr(),
                step.name().nameFr(),
                step.name().nameEn(),
                step.stepOrder(),
                step.mandatory(),
                step.createdAt(),
                step.updatedAt());
    }

    public WorkflowTransition toDomain(WorkflowTransitionJpaEntity entity) {
        Objects.requireNonNull(entity, "Workflow transition entity must not be null.");

        return WorkflowTransition.restore(
                WorkflowTransitionId.of(entity.getId()),
                WorkflowDefinitionId.of(entity.getDefinitionId()),
                WorkflowStepId.of(entity.getFromStepId()),
                WorkflowStepId.of(entity.getToStepId()),
                WorkflowDecision.valueOf(entity.getDecision()),
                Boolean.TRUE.equals(entity.getReasonRequired()),
                Boolean.TRUE.equals(entity.getCommentRequired()),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public WorkflowTransitionJpaEntity toEntity(WorkflowTransition transition) {
        Objects.requireNonNull(transition, "Workflow transition must not be null.");

        return new WorkflowTransitionJpaEntity(
                transition.id().value(),
                transition.definitionId().value(),
                transition.fromStepId().value(),
                transition.toStepId().value(),
                transition.decision().name(),
                transition.reasonRequired(),
                transition.commentRequired(),
                transition.createdAt(),
                transition.updatedAt());
    }

    public WorkflowInstance toDomain(
            WorkflowInstanceJpaEntity entity,
            List<WorkflowTaskJpaEntity> taskEntities,
            List<WorkflowActionJpaEntity> actionEntities,
            List<WorkflowStateHistoryJpaEntity> stateHistoryEntities) {

        Objects.requireNonNull(entity, "Workflow instance entity must not be null.");

        List<WorkflowTask> tasks = taskEntities == null
                ? List.of()
                : taskEntities.stream().map(this::toDomain).toList();

        List<WorkflowAction> actions = actionEntities == null
                ? List.of()
                : actionEntities.stream().map(this::toDomain).toList();

        List<WorkflowStateHistory> stateHistory = stateHistoryEntities == null
                ? List.of()
                : stateHistoryEntities.stream().map(this::toDomain).toList();

        return WorkflowInstance.restore(
                WorkflowInstanceId.of(entity.getId()),
                WorkflowDefinitionId.of(entity.getDefinitionId()),
                WorkflowVersion.of(entity.getDefinitionVersion()),
                targetReference(entity),
                WorkflowInstanceStatus.valueOf(entity.getStatus()),
                entity.getCurrentStepId() == null ? null : WorkflowStepId.of(entity.getCurrentStepId()),
                actorReference(
                        entity.getStartedByActorId(),
                        entity.getStartedByUsernameSnapshot(),
                        entity.getStartedByDisplayNameSnapshot(),
                        entity.getStartedByRoleCodeSnapshot()),
                entity.getStartedAt(),
                entity.getCompletedAt(),
                entity.getCancelledAt(),
                entity.getCorrelationId() == null ? null : WorkflowCorrelationId.of(entity.getCorrelationId()),
                tasks,
                actions,
                stateHistory,
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public WorkflowInstanceJpaEntity toEntity(WorkflowInstance instance) {
        Objects.requireNonNull(instance, "Workflow instance must not be null.");

        WorkflowActorReference startedBy = instance.startedBy();

        return new WorkflowInstanceJpaEntity(
                instance.id().value(),
                instance.definitionId().value(),
                instance.definitionVersion().value(),
                instance.target().targetModule(),
                instance.target().targetType().id(),
                instance.target().targetId(),
                instance.target().targetCodeSnapshot(),
                instance.target().targetLabelSnapshot(),
                instance.status().name(),
                instance.currentStepId() == null ? null : instance.currentStepId().value(),
                startedBy.actorId(),
                startedBy.actorUsernameSnapshot(),
                startedBy.actorDisplayNameSnapshot(),
                startedBy.roleCodeSnapshot(),
                instance.startedAt(),
                instance.completedAt(),
                instance.cancelledAt(),
                instance.correlationId() == null ? null : instance.correlationId().value(),
                instance.createdAt(),
                instance.updatedAt());
    }

    public WorkflowTask toDomain(WorkflowTaskJpaEntity entity) {
        Objects.requireNonNull(entity, "Workflow task entity must not be null.");

        return WorkflowTask.restore(
                WorkflowTaskId.of(entity.getId()),
                WorkflowInstanceId.of(entity.getInstanceId()),
                WorkflowStepId.of(entity.getStepId()),
                WorkflowTaskStatus.valueOf(entity.getStatus()),
                actorReference(
                        entity.getAssignedActorId(),
                        entity.getAssignedActorUsernameSnapshot(),
                        entity.getAssignedActorDisplayNameSnapshot(),
                        entity.getAssignedRoleCodeSnapshot()),
                organizationReference(
                        entity.getAssignedOrganizationUnitId(),
                        entity.getAssignedOrganizationUnitNameSnapshot(),
                        entity.getAssignedRoleCodeSnapshot()),
                entity.getPriorityId() == null ? null : WorkflowPriorityReference.of(entity.getPriorityId(), entity.getPriorityId()),
                entity.getDueAt() == null ? null : WorkflowDueDate.of(entity.getDueAt()),
                actorReference(
                        entity.getClaimedByActorId(),
                        entity.getClaimedByUsernameSnapshot(),
                        entity.getClaimedByDisplayNameSnapshot(),
                        entity.getClaimedByRoleCodeSnapshot()),
                entity.getClaimedAt(),
                actorReference(
                        entity.getCompletedByActorId(),
                        entity.getCompletedByUsernameSnapshot(),
                        entity.getCompletedByDisplayNameSnapshot(),
                        entity.getCompletedByRoleCodeSnapshot()),
                entity.getCompletedAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public WorkflowTaskJpaEntity toEntity(WorkflowTask task) {
        Objects.requireNonNull(task, "Workflow task must not be null.");

        WorkflowActorReference assignedActor = task.assignedActor();
        WorkflowOrganizationReference assignedOrganization = task.assignedOrganization();
        WorkflowActorReference claimedBy = task.claimedBy();
        WorkflowActorReference completedBy = task.completedBy();

        return new WorkflowTaskJpaEntity(
                task.id().value(),
                task.instanceId().value(),
                task.stepId().value(),
                task.status().name(),
                assignedActor == null ? null : assignedActor.actorId(),
                assignedActor == null ? null : assignedActor.actorUsernameSnapshot(),
                assignedActor == null ? null : assignedActor.actorDisplayNameSnapshot(),
                assignedOrganization == null ? null : assignedOrganization.organizationUnitId(),
                assignedOrganization == null ? null : assignedOrganization.organizationUnitNameSnapshot(),
                assignedOrganization == null ? null : assignedOrganization.roleCodeSnapshot(),
                task.priority() == null ? null : task.priority().id(),
                task.dueDate() == null ? null : task.dueDate().value(),
                claimedBy == null ? null : claimedBy.actorId(),
                claimedBy == null ? null : claimedBy.actorUsernameSnapshot(),
                claimedBy == null ? null : claimedBy.actorDisplayNameSnapshot(),
                claimedBy == null ? null : claimedBy.roleCodeSnapshot(),
                task.claimedAt(),
                completedBy == null ? null : completedBy.actorId(),
                completedBy == null ? null : completedBy.actorUsernameSnapshot(),
                completedBy == null ? null : completedBy.actorDisplayNameSnapshot(),
                completedBy == null ? null : completedBy.roleCodeSnapshot(),
                task.completedAt(),
                task.createdAt(),
                task.updatedAt());
    }

    public WorkflowAssignment toDomain(WorkflowAssignmentJpaEntity entity) {
        Objects.requireNonNull(entity, "Workflow assignment entity must not be null.");

        return WorkflowAssignment.restore(
                WorkflowAssignmentId.of(entity.getId()),
                WorkflowTaskId.of(entity.getTaskId()),
                actorReference(
                        entity.getActorId(),
                        entity.getActorUsernameSnapshot(),
                        entity.getActorDisplayNameSnapshot(),
                        entity.getRoleCodeSnapshot()),
                organizationReference(
                        entity.getOrganizationUnitId(),
                        entity.getOrganizationUnitNameSnapshot(),
                        entity.getRoleCodeSnapshot()),
                WorkflowAssignmentStatus.valueOf(entity.getStatus()),
                entity.getAssignedAt(),
                entity.getUpdatedAt());
    }

    public WorkflowAssignmentJpaEntity toEntity(WorkflowAssignment assignment) {
        Objects.requireNonNull(assignment, "Workflow assignment must not be null.");

        WorkflowActorReference actor = assignment.actor();
        WorkflowOrganizationReference organization = assignment.organization();

        return new WorkflowAssignmentJpaEntity(
                assignment.id().value(),
                assignment.taskId().value(),
                actor == null ? null : actor.actorId(),
                actor == null ? null : actor.actorUsernameSnapshot(),
                actor == null ? null : actor.actorDisplayNameSnapshot(),
                actor == null ? null : actor.roleCodeSnapshot(),
                organization == null ? null : organization.organizationUnitId(),
                organization == null ? null : organization.organizationUnitNameSnapshot(),
                assignment.status().name(),
                assignment.assignedAt(),
                assignment.updatedAt());
    }

    public WorkflowAction toDomain(WorkflowActionJpaEntity entity) {
        Objects.requireNonNull(entity, "Workflow action entity must not be null.");

        return WorkflowAction.restore(
                WorkflowActionId.of(entity.getId()),
                WorkflowInstanceId.of(entity.getInstanceId()),
                entity.getTaskId() == null ? null : WorkflowTaskId.of(entity.getTaskId()),
                WorkflowActionType.valueOf(entity.getActionType()),
                entity.getDecision() == null ? null : WorkflowDecision.valueOf(entity.getDecision()),
                entity.getReasonId() == null ? null : WorkflowReasonReference.of(entity.getReasonId(), entity.getReasonId()),
                entity.getDecisionNote() == null ? null : WorkflowDecisionNote.of(entity.getDecisionNote()),
                entity.getCommentText() == null ? null : WorkflowCommentText.of(entity.getCommentText()),
                actorReference(
                        entity.getActorId(),
                        entity.getActorUsernameSnapshot(),
                        entity.getActorDisplayNameSnapshot(),
                        entity.getActorRoleCodeSnapshot()),
                organizationReference(
                        entity.getOrganizationUnitId(),
                        entity.getOrganizationUnitNameSnapshot(),
                        entity.getOrganizationRoleCodeSnapshot()),
                entity.getCorrelationId() == null ? null : WorkflowCorrelationId.of(entity.getCorrelationId()),
                entity.getActedAt());
    }

    public WorkflowActionJpaEntity toEntity(WorkflowAction action) {
        Objects.requireNonNull(action, "Workflow action must not be null.");

        WorkflowActorReference actor = action.actor();
        WorkflowOrganizationReference organization = action.organization();

        return new WorkflowActionJpaEntity(
                action.id().value(),
                action.instanceId().value(),
                action.taskId() == null ? null : action.taskId().value(),
                action.actionType().name(),
                action.decision() == null ? null : action.decision().name(),
                action.reason() == null ? null : action.reason().id(),
                action.decisionNote() == null ? null : action.decisionNote().value(),
                action.comment() == null ? null : action.comment().value(),
                actor.actorId(),
                actor.actorUsernameSnapshot(),
                actor.actorDisplayNameSnapshot(),
                actor.roleCodeSnapshot(),
                organization == null ? null : organization.organizationUnitId(),
                organization == null ? null : organization.organizationUnitNameSnapshot(),
                organization == null ? null : organization.roleCodeSnapshot(),
                action.correlationId() == null ? null : action.correlationId().value(),
                action.actedAt());
    }

    public WorkflowDelegation toDomain(WorkflowDelegationJpaEntity entity) {
        Objects.requireNonNull(entity, "Workflow delegation entity must not be null.");

        return WorkflowDelegation.restore(
                WorkflowDelegationId.of(entity.getId()),
                WorkflowTaskId.of(entity.getTaskId()),
                actorReference(
                        entity.getFromActorId(),
                        entity.getFromActorUsernameSnapshot(),
                        entity.getFromActorDisplayNameSnapshot(),
                        entity.getFromActorRoleCodeSnapshot()),
                actorReference(
                        entity.getToActorId(),
                        entity.getToActorUsernameSnapshot(),
                        entity.getToActorDisplayNameSnapshot(),
                        entity.getToActorRoleCodeSnapshot()),
                organizationReference(
                        entity.getToOrganizationUnitId(),
                        entity.getToOrganizationUnitNameSnapshot(),
                        entity.getToOrganizationRoleCodeSnapshot()),
                WorkflowReasonReference.of(entity.getReasonId(), entity.getReasonId()),
                entity.getDelegatedAt());
    }

    public WorkflowDelegationJpaEntity toEntity(WorkflowDelegation delegation) {
        Objects.requireNonNull(delegation, "Workflow delegation must not be null.");

        WorkflowActorReference fromActor = delegation.fromActor();
        WorkflowActorReference toActor = delegation.toActor();
        WorkflowOrganizationReference toOrganization = delegation.toOrganization();

        return new WorkflowDelegationJpaEntity(
                delegation.id().value(),
                delegation.taskId().value(),
                fromActor.actorId(),
                fromActor.actorUsernameSnapshot(),
                fromActor.actorDisplayNameSnapshot(),
                fromActor.roleCodeSnapshot(),
                toActor == null ? null : toActor.actorId(),
                toActor == null ? null : toActor.actorUsernameSnapshot(),
                toActor == null ? null : toActor.actorDisplayNameSnapshot(),
                toActor == null ? null : toActor.roleCodeSnapshot(),
                toOrganization == null ? null : toOrganization.organizationUnitId(),
                toOrganization == null ? null : toOrganization.organizationUnitNameSnapshot(),
                toOrganization == null ? null : toOrganization.roleCodeSnapshot(),
                delegation.reason().id(),
                delegation.delegatedAt());
    }

    public WorkflowEscalationRule toDomain(WorkflowEscalationRuleJpaEntity entity) {
        Objects.requireNonNull(entity, "Workflow escalation rule entity must not be null.");

        return WorkflowEscalationRule.restore(
                WorkflowEscalationRuleId.of(entity.getId()),
                WorkflowDefinitionId.of(entity.getDefinitionId()),
                WorkflowStepId.of(entity.getStepId()),
                Duration.ofSeconds(entity.getAfterDurationSeconds()),
                actorReference(
                        entity.getEscalateToActorId(),
                        entity.getEscalateToActorUsernameSnapshot(),
                        entity.getEscalateToActorDisplayNameSnapshot(),
                        entity.getEscalateToActorRoleCodeSnapshot()),
                organizationReference(
                        entity.getEscalateToOrganizationUnitId(),
                        entity.getEscalateToOrganizationUnitNameSnapshot(),
                        entity.getEscalateToOrganizationRoleCodeSnapshot()),
                Boolean.TRUE.equals(entity.getActive()),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public WorkflowEscalationRuleJpaEntity toEntity(WorkflowEscalationRule rule) {
        Objects.requireNonNull(rule, "Workflow escalation rule must not be null.");

        WorkflowActorReference actor = rule.escalateToActor();
        WorkflowOrganizationReference organization = rule.escalateToOrganization();

        return new WorkflowEscalationRuleJpaEntity(
                rule.id().value(),
                rule.definitionId().value(),
                rule.stepId().value(),
                Math.toIntExact(rule.afterDuration().toSeconds()),
                actor == null ? null : actor.actorId(),
                actor == null ? null : actor.actorUsernameSnapshot(),
                actor == null ? null : actor.actorDisplayNameSnapshot(),
                actor == null ? null : actor.roleCodeSnapshot(),
                organization == null ? null : organization.organizationUnitId(),
                organization == null ? null : organization.organizationUnitNameSnapshot(),
                organization == null ? null : organization.roleCodeSnapshot(),
                rule.active(),
                rule.createdAt(),
                rule.updatedAt());
    }

    public WorkflowComment toDomain(WorkflowCommentJpaEntity entity) {
        Objects.requireNonNull(entity, "Workflow comment entity must not be null.");

        return WorkflowComment.restore(
                WorkflowCommentId.of(entity.getId()),
                WorkflowInstanceId.of(entity.getInstanceId()),
                entity.getTaskId() == null ? null : WorkflowTaskId.of(entity.getTaskId()),
                actorReference(
                        entity.getActorId(),
                        entity.getActorUsernameSnapshot(),
                        entity.getActorDisplayNameSnapshot(),
                        entity.getActorRoleCodeSnapshot()),
                WorkflowCommentText.of(entity.getCommentText()),
                entity.getCommentedAt());
    }

    public WorkflowCommentJpaEntity toEntity(WorkflowComment comment) {
        Objects.requireNonNull(comment, "Workflow comment must not be null.");

        WorkflowActorReference actor = comment.actor();

        return new WorkflowCommentJpaEntity(
                comment.id().value(),
                comment.instanceId().value(),
                comment.taskId() == null ? null : comment.taskId().value(),
                actor.actorId(),
                actor.actorUsernameSnapshot(),
                actor.actorDisplayNameSnapshot(),
                actor.roleCodeSnapshot(),
                comment.text().value(),
                comment.commentedAt());
    }

    public WorkflowStateHistory toDomain(WorkflowStateHistoryJpaEntity entity) {
        Objects.requireNonNull(entity, "Workflow state history entity must not be null.");

        return WorkflowStateHistory.restore(
                WorkflowActionId.of(entity.getId()),
                WorkflowInstanceId.of(entity.getInstanceId()),
                entity.getTaskId() == null ? null : WorkflowTaskId.of(entity.getTaskId()),
                entity.getFromStepId() == null ? null : WorkflowStepId.of(entity.getFromStepId()),
                entity.getToStepId() == null ? null : WorkflowStepId.of(entity.getToStepId()),
                entity.getFromStatus() == null ? null : WorkflowInstanceStatus.valueOf(entity.getFromStatus()),
                WorkflowInstanceStatus.valueOf(entity.getToStatus()),
                actorReference(
                        entity.getActorId(),
                        entity.getActorUsernameSnapshot(),
                        entity.getActorDisplayNameSnapshot(),
                        entity.getActorRoleCodeSnapshot()),
                entity.getChangedAt());
    }

    public WorkflowStateHistoryJpaEntity toEntity(WorkflowStateHistory history) {
        Objects.requireNonNull(history, "Workflow state history must not be null.");

        WorkflowActorReference actor = history.actor();

        return new WorkflowStateHistoryJpaEntity(
                history.id().value(),
                history.instanceId().value(),
                history.taskId() == null ? null : history.taskId().value(),
                history.fromStepId() == null ? null : history.fromStepId().value(),
                history.toStepId() == null ? null : history.toStepId().value(),
                history.fromStatus() == null ? null : history.fromStatus().name(),
                history.toStatus().name(),
                actor.actorId(),
                actor.actorUsernameSnapshot(),
                actor.actorDisplayNameSnapshot(),
                actor.roleCodeSnapshot(),
                history.changedAt());
    }

    private WorkflowTargetReference targetReference(WorkflowInstanceJpaEntity entity) {
        return WorkflowTargetReference.of(
                entity.getTargetModule(),
                WorkflowTargetTypeReference.of(entity.getTargetTypeId(), entity.getTargetTypeId()),
                entity.getTargetId(),
                entity.getTargetCodeSnapshot(),
                entity.getTargetLabelSnapshot());
    }

    private WorkflowActorReference actorReference(
            String actorId,
            String usernameSnapshot,
            String displayNameSnapshot,
            String roleCodeSnapshot) {

        if (actorId == null || actorId.isBlank()) {
            return null;
        }

        String displayName = displayNameSnapshot == null || displayNameSnapshot.isBlank()
                ? actorId
                : displayNameSnapshot;

        return WorkflowActorReference.of(
                actorId,
                usernameSnapshot,
                displayName,
                roleCodeSnapshot);
    }

    private WorkflowOrganizationReference organizationReference(
            String organizationUnitId,
            String organizationUnitNameSnapshot,
            String roleCodeSnapshot) {

        if (organizationUnitId == null || organizationUnitId.isBlank()) {
            return null;
        }

        String displayName = organizationUnitNameSnapshot == null || organizationUnitNameSnapshot.isBlank()
                ? organizationUnitId
                : organizationUnitNameSnapshot;

        return WorkflowOrganizationReference.of(
                organizationUnitId,
                displayName,
                roleCodeSnapshot);
    }
}
