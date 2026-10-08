/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
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
    public static AlarmLifecycleEventJpaEntity toEntity(AlarmLifecycleEvent model) {
        return new AlarmLifecycleEventJpaEntity(model.id(), model.alarmId(), model.eventType(), model.previousState(), model.newState(), model.reasonId(), model.reasonText(), model.actorId(), model.actorDisplayName(), model.organizationUnitId(), model.organizationUnitCode(), model.organizationUnitNameSnapshot(), model.occurredAt(), model.correlationId(), model.metadataJson());
    }
    public static AlarmLifecycleEvent toDomain(AlarmLifecycleEventJpaEntity entity) {
        return new AlarmLifecycleEvent(entity.id(), entity.alarmId(), entity.eventType(), entity.previousState(), entity.newState(), entity.reasonId(), entity.reasonText(), entity.actorId(), entity.actorDisplayName(), entity.organizationUnitId(), entity.organizationUnitCode(), entity.organizationUnitNameSnapshot(), entity.occurredAt(), entity.correlationId(), entity.metadataJson());
    }
}
