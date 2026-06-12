/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper
 *
 * @Description : Maps alarm domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.alarm.domain.model.*;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.entity.*;

/**
 * Maps alarm domain models to JPA entities.
 */
public final class AlarmPersistenceMapper {

    private AlarmPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static AlarmJpaEntity toEntity(Alarm model) {
            return new AlarmJpaEntity(
                        model.id(),
                        model.alarmNumber(),
                        model.alarmTypeId(),
                        model.severityId(),
                        model.priorityId(),
                        model.titleAr(),
                        model.titleFr(),
                        model.titleEn(),
                        model.descriptionAr(),
                        model.descriptionFr(),
                        model.descriptionEn(),
                        model.sourceType(),
                        model.sourceReferenceId(),
                        model.monitoringAlertCandidateId(),
                        model.monitoringEvaluationId(),
                        model.telemetryReadingId(),
                        model.planningTargetId(),
                        model.topologyAssetTypeCode(),
                        model.topologyAssetId(),
                        model.topologyAssetCode(),
                        model.topologyAssetNameSnapshot(),
                        model.currentState(),
                        model.raisedAt(),
                        model.firstDetectedAt(),
                        model.lastUpdatedAt(),
                        model.clearedAt(),
                        model.closedAt(),
                        model.acknowledgedAt(),
                        model.acknowledgedByActorId(),
                        model.owningOrganizationUnitId(),
                        model.owningOrganizationUnitCode(),
                        model.owningOrganizationUnitNameSnapshot(),
                        model.workflowInstanceId(),
                        model.incidentId(),
                        model.correlationId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static Alarm toDomain(AlarmJpaEntity entity) {
            return new Alarm(
                        entity.id(),
                        entity.alarmNumber(),
                        entity.alarmTypeId(),
                        entity.severityId(),
                        entity.priorityId(),
                        entity.titleAr(),
                        entity.titleFr(),
                        entity.titleEn(),
                        entity.descriptionAr(),
                        entity.descriptionFr(),
                        entity.descriptionEn(),
                        entity.sourceType(),
                        entity.sourceReferenceId(),
                        entity.monitoringAlertCandidateId(),
                        entity.monitoringEvaluationId(),
                        entity.telemetryReadingId(),
                        entity.planningTargetId(),
                        entity.topologyAssetTypeCode(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCode(),
                        entity.topologyAssetNameSnapshot(),
                        entity.currentState(),
                        entity.raisedAt(),
                        entity.firstDetectedAt(),
                        entity.lastUpdatedAt(),
                        entity.clearedAt(),
                        entity.closedAt(),
                        entity.acknowledgedAt(),
                        entity.acknowledgedByActorId(),
                        entity.owningOrganizationUnitId(),
                        entity.owningOrganizationUnitCode(),
                        entity.owningOrganizationUnitNameSnapshot(),
                        entity.workflowInstanceId(),
                        entity.incidentId(),
                        entity.correlationId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AlarmLifecycleEventJpaEntity toEntity(AlarmLifecycleEvent model) {
            return new AlarmLifecycleEventJpaEntity(
                        model.id(),
                        model.alarmId(),
                        model.eventType(),
                        model.previousState(),
                        model.newState(),
                        model.reasonId(),
                        model.reasonText(),
                        model.actorId(),
                        model.actorDisplayName(),
                        model.organizationUnitId(),
                        model.organizationUnitCode(),
                        model.organizationUnitNameSnapshot(),
                        model.occurredAt(),
                        model.correlationId(),
                        model.metadataJson()
            );
        }

        public static AlarmLifecycleEvent toDomain(AlarmLifecycleEventJpaEntity entity) {
            return new AlarmLifecycleEvent(
                        entity.id(),
                        entity.alarmId(),
                        entity.eventType(),
                        entity.previousState(),
                        entity.newState(),
                        entity.reasonId(),
                        entity.reasonText(),
                        entity.actorId(),
                        entity.actorDisplayName(),
                        entity.organizationUnitId(),
                        entity.organizationUnitCode(),
                        entity.organizationUnitNameSnapshot(),
                        entity.occurredAt(),
                        entity.correlationId(),
                        entity.metadataJson()
            );
        }

        public static AlarmAcknowledgementJpaEntity toEntity(AlarmAcknowledgement model) {
            return new AlarmAcknowledgementJpaEntity(
                        model.id(),
                        model.alarmId(),
                        model.acknowledgedByActorId(),
                        model.acknowledgedByDisplayName(),
                        model.organizationUnitId(),
                        model.organizationUnitCode(),
                        model.acknowledgedAt(),
                        model.comment(),
                        model.correlationId()
            );
        }

        public static AlarmAcknowledgement toDomain(AlarmAcknowledgementJpaEntity entity) {
            return new AlarmAcknowledgement(
                        entity.id(),
                        entity.alarmId(),
                        entity.acknowledgedByActorId(),
                        entity.acknowledgedByDisplayName(),
                        entity.organizationUnitId(),
                        entity.organizationUnitCode(),
                        entity.acknowledgedAt(),
                        entity.comment(),
                        entity.correlationId()
            );
        }

        public static AlarmShelvingJpaEntity toEntity(AlarmShelving model) {
            return new AlarmShelvingJpaEntity(
                        model.id(),
                        model.alarmId(),
                        model.shelvingReasonId(),
                        model.reasonText(),
                        model.shelvedByActorId(),
                        model.shelvedAt(),
                        model.shelvedUntil(),
                        model.unshelvedAt(),
                        model.unshelvedByActorId(),
                        model.status(),
                        model.correlationId()
            );
        }

        public static AlarmShelving toDomain(AlarmShelvingJpaEntity entity) {
            return new AlarmShelving(
                        entity.id(),
                        entity.alarmId(),
                        entity.shelvingReasonId(),
                        entity.reasonText(),
                        entity.shelvedByActorId(),
                        entity.shelvedAt(),
                        entity.shelvedUntil(),
                        entity.unshelvedAt(),
                        entity.unshelvedByActorId(),
                        entity.status(),
                        entity.correlationId()
            );
        }

        public static AlarmSuppressionJpaEntity toEntity(AlarmSuppression model) {
            return new AlarmSuppressionJpaEntity(
                        model.id(),
                        model.scopeType(),
                        model.scopeReferenceId(),
                        model.alarmId(),
                        model.alarmTypeId(),
                        model.topologyAssetTypeCode(),
                        model.topologyAssetId(),
                        model.suppressionReasonId(),
                        model.reasonText(),
                        model.suppressedByActorId(),
                        model.suppressedAt(),
                        model.suppressedUntil(),
                        model.releasedAt(),
                        model.releasedByActorId(),
                        model.status(),
                        model.workflowInstanceId(),
                        model.correlationId()
            );
        }

        public static AlarmSuppression toDomain(AlarmSuppressionJpaEntity entity) {
            return new AlarmSuppression(
                        entity.id(),
                        entity.scopeType(),
                        entity.scopeReferenceId(),
                        entity.alarmId(),
                        entity.alarmTypeId(),
                        entity.topologyAssetTypeCode(),
                        entity.topologyAssetId(),
                        entity.suppressionReasonId(),
                        entity.reasonText(),
                        entity.suppressedByActorId(),
                        entity.suppressedAt(),
                        entity.suppressedUntil(),
                        entity.releasedAt(),
                        entity.releasedByActorId(),
                        entity.status(),
                        entity.workflowInstanceId(),
                        entity.correlationId()
            );
        }

        public static AlarmEscalationJpaEntity toEntity(AlarmEscalation model) {
            return new AlarmEscalationJpaEntity(
                        model.id(),
                        model.alarmId(),
                        model.escalationLevel(),
                        model.escalationType(),
                        model.targetOrganizationUnitId(),
                        model.targetActorId(),
                        model.workflowInstanceId(),
                        model.incidentId(),
                        model.reasonId(),
                        model.reasonText(),
                        model.escalatedByActorId(),
                        model.escalatedAt(),
                        model.status(),
                        model.correlationId()
            );
        }

        public static AlarmEscalation toDomain(AlarmEscalationJpaEntity entity) {
            return new AlarmEscalation(
                        entity.id(),
                        entity.alarmId(),
                        entity.escalationLevel(),
                        entity.escalationType(),
                        entity.targetOrganizationUnitId(),
                        entity.targetActorId(),
                        entity.workflowInstanceId(),
                        entity.incidentId(),
                        entity.reasonId(),
                        entity.reasonText(),
                        entity.escalatedByActorId(),
                        entity.escalatedAt(),
                        entity.status(),
                        entity.correlationId()
            );
        }

        public static AlarmClosureJpaEntity toEntity(AlarmClosure model) {
            return new AlarmClosureJpaEntity(
                        model.id(),
                        model.alarmId(),
                        model.closureType(),
                        model.closureReasonId(),
                        model.closureComment(),
                        model.closedByActorId(),
                        model.closedAt(),
                        model.requiresReview(),
                        model.reviewWorkflowInstanceId(),
                        model.correlationId()
            );
        }

        public static AlarmClosure toDomain(AlarmClosureJpaEntity entity) {
            return new AlarmClosure(
                        entity.id(),
                        entity.alarmId(),
                        entity.closureType(),
                        entity.closureReasonId(),
                        entity.closureComment(),
                        entity.closedByActorId(),
                        entity.closedAt(),
                        entity.requiresReview(),
                        entity.reviewWorkflowInstanceId(),
                        entity.correlationId()
            );
        }

        public static AlarmEvidenceLinkJpaEntity toEntity(AlarmEvidenceLink model) {
            return new AlarmEvidenceLinkJpaEntity(
                        model.id(),
                        model.alarmId(),
                        model.evidenceType(),
                        model.evidenceReferenceId(),
                        model.evidenceCodeSnapshot(),
                        model.evidenceNameSnapshot(),
                        model.description(),
                        model.createdAt()
            );
        }

        public static AlarmEvidenceLink toDomain(AlarmEvidenceLinkJpaEntity entity) {
            return new AlarmEvidenceLink(
                        entity.id(),
                        entity.alarmId(),
                        entity.evidenceType(),
                        entity.evidenceReferenceId(),
                        entity.evidenceCodeSnapshot(),
                        entity.evidenceNameSnapshot(),
                        entity.description(),
                        entity.createdAt()
            );
        }

        public static AlarmCommentJpaEntity toEntity(AlarmComment model) {
            return new AlarmCommentJpaEntity(
                        model.id(),
                        model.alarmId(),
                        model.commentText(),
                        model.visibility(),
                        model.createdByActorId(),
                        model.createdByDisplayName(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AlarmComment toDomain(AlarmCommentJpaEntity entity) {
            return new AlarmComment(
                        entity.id(),
                        entity.alarmId(),
                        entity.commentText(),
                        entity.visibility(),
                        entity.createdByActorId(),
                        entity.createdByDisplayName(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AlarmRuleBindingJpaEntity toEntity(AlarmRuleBinding model) {
            return new AlarmRuleBindingJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.monitoringRuleId(),
                        model.monitoringThresholdId(),
                        model.candidateTypeId(),
                        model.alarmTypeId(),
                        model.defaultSeverityId(),
                        model.defaultPriorityId(),
                        model.autoRaise(),
                        model.requiresOperatorConfirmation(),
                        model.active(),
                        model.effectiveFrom(),
                        model.effectiveTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AlarmRuleBinding toDomain(AlarmRuleBindingJpaEntity entity) {
            return new AlarmRuleBinding(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.monitoringRuleId(),
                        entity.monitoringThresholdId(),
                        entity.candidateTypeId(),
                        entity.alarmTypeId(),
                        entity.defaultSeverityId(),
                        entity.defaultPriorityId(),
                        entity.autoRaise(),
                        entity.requiresOperatorConfirmation(),
                        entity.active(),
                        entity.effectiveFrom(),
                        entity.effectiveTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AlarmCatalogEntryJpaEntity toEntity(AlarmCatalogEntry model) {
            return new AlarmCatalogEntryJpaEntity(
                        model.id(),
                        model.catalogName(),
                        model.code(),
                        model.active(),
                        model.sortOrder(),
                        model.systemDefined(),
                        model.severityRank(),
                        model.colorCode(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AlarmCatalogEntry toDomain(AlarmCatalogEntryJpaEntity entity) {
            return new AlarmCatalogEntry(
                        entity.id(),
                        entity.catalogName(),
                        entity.code(),
                        entity.active(),
                        entity.sortOrder(),
                        entity.systemDefined(),
                        entity.severityRank(),
                        entity.colorCode(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AlarmCatalogTranslationJpaEntity toEntity(AlarmCatalogTranslation model) {
            return new AlarmCatalogTranslationJpaEntity(
                        model.id(),
                        model.catalogEntryId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AlarmCatalogTranslation toDomain(AlarmCatalogTranslationJpaEntity entity) {
            return new AlarmCatalogTranslation(
                        entity.id(),
                        entity.catalogEntryId(),
                        entity.locale(),
                        entity.name(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

}
