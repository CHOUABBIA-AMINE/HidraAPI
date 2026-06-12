/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper
 *
 * @Description : Maps workflow domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.workflow.domain.model.*;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.*;

/**
 * Maps workflow domain models to JPA entities.
 */
public final class WorkflowPersistenceMapper {

    private WorkflowPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static WorkflowCatalogEntryJpaEntity toEntity(WorkflowCatalogEntry model) {
            return new WorkflowCatalogEntryJpaEntity(
                        model.id(),
                        model.catalogName(),
                        model.code(),
                        model.active(),
                        model.sortOrder(),
                        model.systemDefined(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static WorkflowCatalogEntry toDomain(WorkflowCatalogEntryJpaEntity entity) {
            return new WorkflowCatalogEntry(
                        entity.id(),
                        entity.catalogName(),
                        entity.code(),
                        entity.active(),
                        entity.sortOrder(),
                        entity.systemDefined(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static WorkflowCatalogTranslationJpaEntity toEntity(WorkflowCatalogTranslation model) {
            return new WorkflowCatalogTranslationJpaEntity(
                        model.id(),
                        model.typeId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static WorkflowCatalogTranslation toDomain(WorkflowCatalogTranslationJpaEntity entity) {
            return new WorkflowCatalogTranslation(
                        entity.id(),
                        entity.typeId(),
                        entity.locale(),
                        entity.name(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static WorkflowDefinitionJpaEntity toEntity(WorkflowDefinition model) {
            return new WorkflowDefinitionJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.typeId(),
                        model.status(),
                        model.version(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static WorkflowDefinition toDomain(WorkflowDefinitionJpaEntity entity) {
            return new WorkflowDefinition(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.typeId(),
                        entity.status(),
                        entity.version(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static WorkflowStepJpaEntity toEntity(WorkflowStep model) {
            return new WorkflowStepJpaEntity(
                        model.id(),
                        model.definitionId(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.stepOrder(),
                        model.mandatory(),
                        model.stepTypeId(),
                        model.defaultAssignmentRuleId(),
                        model.slaPolicyId(),
                        model.allowClaim(),
                        model.allowDelegation(),
                        model.allowEscalation(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static WorkflowStep toDomain(WorkflowStepJpaEntity entity) {
            return new WorkflowStep(
                        entity.id(),
                        entity.definitionId(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.stepOrder(),
                        entity.mandatory(),
                        entity.stepTypeId(),
                        entity.defaultAssignmentRuleId(),
                        entity.slaPolicyId(),
                        entity.allowClaim(),
                        entity.allowDelegation(),
                        entity.allowEscalation(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static WorkflowTransitionJpaEntity toEntity(WorkflowTransition model) {
            return new WorkflowTransitionJpaEntity(
                        model.id(),
                        model.definitionId(),
                        model.fromStepId(),
                        model.toStepId(),
                        model.decision(),
                        model.reasonRequired(),
                        model.commentRequired(),
                        model.conditionExpression(),
                        model.requiredPermissionCode(),
                        model.targetModuleCallback(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static WorkflowTransition toDomain(WorkflowTransitionJpaEntity entity) {
            return new WorkflowTransition(
                        entity.id(),
                        entity.definitionId(),
                        entity.fromStepId(),
                        entity.toStepId(),
                        entity.decision(),
                        entity.reasonRequired(),
                        entity.commentRequired(),
                        entity.conditionExpression(),
                        entity.requiredPermissionCode(),
                        entity.targetModuleCallback(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static WorkflowInstanceJpaEntity toEntity(WorkflowInstance model) {
            return new WorkflowInstanceJpaEntity(
                        model.id(),
                        model.definitionId(),
                        model.definitionVersion(),
                        model.workflowPurposeId(),
                        model.targetModule(),
                        model.targetTypeId(),
                        model.targetId(),
                        model.targetCodeSnapshot(),
                        model.targetLabelSnapshot(),
                        model.status(),
                        model.currentStepId(),
                        model.startedByActorId(),
                        model.startedByUsernameSnapshot(),
                        model.startedByDisplayNameSnapshot(),
                        model.startedByRoleCodeSnapshot(),
                        model.startedAt(),
                        model.completedAt(),
                        model.cancelledAt(),
                        model.correlationId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static WorkflowInstance toDomain(WorkflowInstanceJpaEntity entity) {
            return new WorkflowInstance(
                        entity.id(),
                        entity.definitionId(),
                        entity.definitionVersion(),
                        entity.workflowPurposeId(),
                        entity.targetModule(),
                        entity.targetTypeId(),
                        entity.targetId(),
                        entity.targetCodeSnapshot(),
                        entity.targetLabelSnapshot(),
                        entity.status(),
                        entity.currentStepId(),
                        entity.startedByActorId(),
                        entity.startedByUsernameSnapshot(),
                        entity.startedByDisplayNameSnapshot(),
                        entity.startedByRoleCodeSnapshot(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.cancelledAt(),
                        entity.correlationId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static WorkflowTaskJpaEntity toEntity(WorkflowTask model) {
            return new WorkflowTaskJpaEntity(
                        model.id(),
                        model.instanceId(),
                        model.stepId(),
                        model.status(),
                        model.assignedActorId(),
                        model.assignedActorUsernameSnapshot(),
                        model.assignedActorDisplayNameSnapshot(),
                        model.assignedOrganizationUnitId(),
                        model.assignedOrganizationUnitNameSnapshot(),
                        model.assignedRoleCodeSnapshot(),
                        model.priorityId(),
                        model.dueAt(),
                        model.claimedByActorId(),
                        model.claimedAt(),
                        model.completedByActorId(),
                        model.completedAt(),
                        model.assignmentModeId(),
                        model.taskLabelSnapshot(),
                        model.slaStatus(),
                        model.escalatedAt(),
                        model.delegatedAt(),
                        model.expiresAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static WorkflowTask toDomain(WorkflowTaskJpaEntity entity) {
            return new WorkflowTask(
                        entity.id(),
                        entity.instanceId(),
                        entity.stepId(),
                        entity.status(),
                        entity.assignedActorId(),
                        entity.assignedActorUsernameSnapshot(),
                        entity.assignedActorDisplayNameSnapshot(),
                        entity.assignedOrganizationUnitId(),
                        entity.assignedOrganizationUnitNameSnapshot(),
                        entity.assignedRoleCodeSnapshot(),
                        entity.priorityId(),
                        entity.dueAt(),
                        entity.claimedByActorId(),
                        entity.claimedAt(),
                        entity.completedByActorId(),
                        entity.completedAt(),
                        entity.assignmentModeId(),
                        entity.taskLabelSnapshot(),
                        entity.slaStatus(),
                        entity.escalatedAt(),
                        entity.delegatedAt(),
                        entity.expiresAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static WorkflowAssignmentJpaEntity toEntity(WorkflowAssignment model) {
            return new WorkflowAssignmentJpaEntity(
                        model.id(),
                        model.taskId(),
                        model.actorId(),
                        model.actorUsernameSnapshot(),
                        model.actorDisplayNameSnapshot(),
                        model.roleCodeSnapshot(),
                        model.organizationUnitId(),
                        model.organizationUnitNameSnapshot(),
                        model.status(),
                        model.assignedAt(),
                        model.updatedAt()
            );
        }

        public static WorkflowAssignment toDomain(WorkflowAssignmentJpaEntity entity) {
            return new WorkflowAssignment(
                        entity.id(),
                        entity.taskId(),
                        entity.actorId(),
                        entity.actorUsernameSnapshot(),
                        entity.actorDisplayNameSnapshot(),
                        entity.roleCodeSnapshot(),
                        entity.organizationUnitId(),
                        entity.organizationUnitNameSnapshot(),
                        entity.status(),
                        entity.assignedAt(),
                        entity.updatedAt()
            );
        }

        public static WorkflowActionJpaEntity toEntity(WorkflowAction model) {
            return new WorkflowActionJpaEntity(
                        model.id(),
                        model.instanceId(),
                        model.taskId(),
                        model.actionType(),
                        model.decision(),
                        model.reasonId(),
                        model.decisionNote(),
                        model.commentText(),
                        model.actorId(),
                        model.actorUsernameSnapshot(),
                        model.actorDisplayNameSnapshot(),
                        model.actorRoleCodeSnapshot(),
                        model.organizationUnitId(),
                        model.organizationUnitNameSnapshot(),
                        model.organizationRoleCodeSnapshot(),
                        model.correlationId(),
                        model.actionSequence(),
                        model.sourceSystem(),
                        model.ipAddressHash(),
                        model.userAgentHash(),
                        model.actedAt()
            );
        }

        public static WorkflowAction toDomain(WorkflowActionJpaEntity entity) {
            return new WorkflowAction(
                        entity.id(),
                        entity.instanceId(),
                        entity.taskId(),
                        entity.actionType(),
                        entity.decision(),
                        entity.reasonId(),
                        entity.decisionNote(),
                        entity.commentText(),
                        entity.actorId(),
                        entity.actorUsernameSnapshot(),
                        entity.actorDisplayNameSnapshot(),
                        entity.actorRoleCodeSnapshot(),
                        entity.organizationUnitId(),
                        entity.organizationUnitNameSnapshot(),
                        entity.organizationRoleCodeSnapshot(),
                        entity.correlationId(),
                        entity.actionSequence(),
                        entity.sourceSystem(),
                        entity.ipAddressHash(),
                        entity.userAgentHash(),
                        entity.actedAt()
            );
        }

        public static WorkflowDelegationJpaEntity toEntity(WorkflowDelegation model) {
            return new WorkflowDelegationJpaEntity(
                        model.id(),
                        model.taskId(),
                        model.fromActorId(),
                        model.fromActorUsernameSnapshot(),
                        model.fromActorDisplayNameSnapshot(),
                        model.fromActorRoleCodeSnapshot(),
                        model.toActorId(),
                        model.toActorUsernameSnapshot(),
                        model.toActorDisplayNameSnapshot(),
                        model.toActorRoleCodeSnapshot(),
                        model.toOrganizationUnitId(),
                        model.toOrganizationUnitNameSnapshot(),
                        model.toOrganizationRoleCodeSnapshot(),
                        model.reasonId(),
                        model.delegationStatus(),
                        model.delegatedAt(),
                        model.acceptedAt(),
                        model.validUntil(),
                        model.delegationDepth()
            );
        }

        public static WorkflowDelegation toDomain(WorkflowDelegationJpaEntity entity) {
            return new WorkflowDelegation(
                        entity.id(),
                        entity.taskId(),
                        entity.fromActorId(),
                        entity.fromActorUsernameSnapshot(),
                        entity.fromActorDisplayNameSnapshot(),
                        entity.fromActorRoleCodeSnapshot(),
                        entity.toActorId(),
                        entity.toActorUsernameSnapshot(),
                        entity.toActorDisplayNameSnapshot(),
                        entity.toActorRoleCodeSnapshot(),
                        entity.toOrganizationUnitId(),
                        entity.toOrganizationUnitNameSnapshot(),
                        entity.toOrganizationRoleCodeSnapshot(),
                        entity.reasonId(),
                        entity.delegationStatus(),
                        entity.delegatedAt(),
                        entity.acceptedAt(),
                        entity.validUntil(),
                        entity.delegationDepth()
            );
        }

        public static WorkflowEscalationRuleJpaEntity toEntity(WorkflowEscalationRule model) {
            return new WorkflowEscalationRuleJpaEntity(
                        model.id(),
                        model.definitionId(),
                        model.stepId(),
                        model.afterDurationSeconds(),
                        model.escalateToActorId(),
                        model.escalateToActorUsernameSnapshot(),
                        model.escalateToActorDisplayNameSnapshot(),
                        model.escalateToActorRoleCodeSnapshot(),
                        model.escalateToOrganizationUnitId(),
                        model.escalateToOrganizationUnitNameSnapshot(),
                        model.escalateToOrganizationRoleCodeSnapshot(),
                        model.escalationReasonId(),
                        model.repeatable(),
                        model.maxRepeatCount(),
                        model.escalationLevel(),
                        model.businessHoursCalendarId(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static WorkflowEscalationRule toDomain(WorkflowEscalationRuleJpaEntity entity) {
            return new WorkflowEscalationRule(
                        entity.id(),
                        entity.definitionId(),
                        entity.stepId(),
                        entity.afterDurationSeconds(),
                        entity.escalateToActorId(),
                        entity.escalateToActorUsernameSnapshot(),
                        entity.escalateToActorDisplayNameSnapshot(),
                        entity.escalateToActorRoleCodeSnapshot(),
                        entity.escalateToOrganizationUnitId(),
                        entity.escalateToOrganizationUnitNameSnapshot(),
                        entity.escalateToOrganizationRoleCodeSnapshot(),
                        entity.escalationReasonId(),
                        entity.repeatable(),
                        entity.maxRepeatCount(),
                        entity.escalationLevel(),
                        entity.businessHoursCalendarId(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static WorkflowCommentJpaEntity toEntity(WorkflowComment model) {
            return new WorkflowCommentJpaEntity(
                        model.id(),
                        model.instanceId(),
                        model.taskId(),
                        model.actorId(),
                        model.actorUsernameSnapshot(),
                        model.actorDisplayNameSnapshot(),
                        model.actorRoleCodeSnapshot(),
                        model.commentText(),
                        model.visibility(),
                        model.parentCommentId(),
                        model.commentedAt(),
                        model.editedAt()
            );
        }

        public static WorkflowComment toDomain(WorkflowCommentJpaEntity entity) {
            return new WorkflowComment(
                        entity.id(),
                        entity.instanceId(),
                        entity.taskId(),
                        entity.actorId(),
                        entity.actorUsernameSnapshot(),
                        entity.actorDisplayNameSnapshot(),
                        entity.actorRoleCodeSnapshot(),
                        entity.commentText(),
                        entity.visibility(),
                        entity.parentCommentId(),
                        entity.commentedAt(),
                        entity.editedAt()
            );
        }

        public static WorkflowStateHistoryJpaEntity toEntity(WorkflowStateHistory model) {
            return new WorkflowStateHistoryJpaEntity(
                        model.id(),
                        model.instanceId(),
                        model.taskId(),
                        model.fromStepId(),
                        model.toStepId(),
                        model.fromStatus(),
                        model.toStatus(),
                        model.actorId(),
                        model.actorUsernameSnapshot(),
                        model.actorDisplayNameSnapshot(),
                        model.actorRoleCodeSnapshot(),
                        model.actionId(),
                        model.reasonId(),
                        model.correlationId(),
                        model.changedAt()
            );
        }

        public static WorkflowStateHistory toDomain(WorkflowStateHistoryJpaEntity entity) {
            return new WorkflowStateHistory(
                        entity.id(),
                        entity.instanceId(),
                        entity.taskId(),
                        entity.fromStepId(),
                        entity.toStepId(),
                        entity.fromStatus(),
                        entity.toStatus(),
                        entity.actorId(),
                        entity.actorUsernameSnapshot(),
                        entity.actorDisplayNameSnapshot(),
                        entity.actorRoleCodeSnapshot(),
                        entity.actionId(),
                        entity.reasonId(),
                        entity.correlationId(),
                        entity.changedAt()
            );
        }

        public static WorkflowDefinitionTargetBindingJpaEntity toEntity(WorkflowDefinitionTargetBinding model) {
            return new WorkflowDefinitionTargetBindingJpaEntity(
                        model.id(),
                        model.definitionId(),
                        model.targetModule(),
                        model.targetTypeId(),
                        model.workflowPurposeId(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static WorkflowDefinitionTargetBinding toDomain(WorkflowDefinitionTargetBindingJpaEntity entity) {
            return new WorkflowDefinitionTargetBinding(
                        entity.id(),
                        entity.definitionId(),
                        entity.targetModule(),
                        entity.targetTypeId(),
                        entity.workflowPurposeId(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static WorkflowStepAssignmentRuleJpaEntity toEntity(WorkflowStepAssignmentRule model) {
            return new WorkflowStepAssignmentRuleJpaEntity(
                        model.id(),
                        model.definitionId(),
                        model.stepId(),
                        model.assignmentModeId(),
                        model.actorId(),
                        model.roleCode(),
                        model.organizationUnitId(),
                        model.organizationRoleCode(),
                        model.targetOwnerMode(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static WorkflowStepAssignmentRule toDomain(WorkflowStepAssignmentRuleJpaEntity entity) {
            return new WorkflowStepAssignmentRule(
                        entity.id(),
                        entity.definitionId(),
                        entity.stepId(),
                        entity.assignmentModeId(),
                        entity.actorId(),
                        entity.roleCode(),
                        entity.organizationUnitId(),
                        entity.organizationRoleCode(),
                        entity.targetOwnerMode(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static WorkflowSlaPolicyJpaEntity toEntity(WorkflowSlaPolicy model) {
            return new WorkflowSlaPolicyJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.durationSeconds(),
                        model.calendarMode(),
                        model.warningBeforeSeconds(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static WorkflowSlaPolicy toDomain(WorkflowSlaPolicyJpaEntity entity) {
            return new WorkflowSlaPolicy(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.durationSeconds(),
                        entity.calendarMode(),
                        entity.warningBeforeSeconds(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static WorkflowAuditOutboxReferenceJpaEntity toEntity(WorkflowAuditOutboxReference model) {
            return new WorkflowAuditOutboxReferenceJpaEntity(
                        model.id(),
                        model.instanceId(),
                        model.taskId(),
                        model.actionId(),
                        model.eventType(),
                        model.outboxEventId(),
                        model.emittedAt(),
                        model.status(),
                        model.failureReason()
            );
        }

        public static WorkflowAuditOutboxReference toDomain(WorkflowAuditOutboxReferenceJpaEntity entity) {
            return new WorkflowAuditOutboxReference(
                        entity.id(),
                        entity.instanceId(),
                        entity.taskId(),
                        entity.actionId(),
                        entity.eventType(),
                        entity.outboxEventId(),
                        entity.emittedAt(),
                        entity.status(),
                        entity.failureReason()
            );
        }

}
