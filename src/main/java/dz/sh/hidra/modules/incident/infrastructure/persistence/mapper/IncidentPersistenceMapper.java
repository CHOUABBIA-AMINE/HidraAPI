/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.mapper
 *
 * @Description : Maps incident domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.incident.domain.model.*;
import dz.sh.hidra.modules.incident.infrastructure.persistence.entity.*;

/**
 * Maps incident domain models to JPA entities.
 */
public final class IncidentPersistenceMapper {

    private IncidentPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static IncidentJpaEntity toEntity(Incident model) {
            return new IncidentJpaEntity(
                        model.id(),
                        model.incidentNumber(),
                        model.title(),
                        model.description(),
                        model.classificationId(),
                        model.severityId(),
                        model.priorityId(),
                        model.status(),
                        model.sourceType(),
                        model.sourceReferenceId(),
                        model.sourceReferenceCode(),
                        model.detectedAt(),
                        model.reportedAt(),
                        model.occurredAt(),
                        model.topologyAssetTypeCode(),
                        model.topologyAssetId(),
                        model.topologyAssetCode(),
                        model.topologyAssetNameSnapshot(),
                        model.locationDescriptionAr(),
                        model.locationDescriptionLt(),
                        model.latitude(),
                        model.longitude(),
                        model.responsibleOrganizationUnitId(),
                        model.responsibleOrganizationUnitCode(),
                        model.responsibleOrganizationUnitNameSnapshot(),
                        model.responsibleActorId(),
                        model.responsibleActorNameSnapshot(),
                        model.workflowInstanceId(),
                        model.currentEscalationLevel(),
                        model.containedAt(),
                        model.resolvedAt(),
                        model.closedAt(),
                        model.cancelledAt(),
                        model.createdByActorId(),
                        model.createdByActorNameSnapshot(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static Incident toDomain(IncidentJpaEntity entity) {
            return new Incident(
                        entity.id(),
                        entity.incidentNumber(),
                        entity.title(),
                        entity.description(),
                        entity.classificationId(),
                        entity.severityId(),
                        entity.priorityId(),
                        entity.status(),
                        entity.sourceType(),
                        entity.sourceReferenceId(),
                        entity.sourceReferenceCode(),
                        entity.detectedAt(),
                        entity.reportedAt(),
                        entity.occurredAt(),
                        entity.topologyAssetTypeCode(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCode(),
                        entity.topologyAssetNameSnapshot(),
                        entity.locationDescriptionAr(),
                        entity.locationDescriptionLt(),
                        entity.latitude(),
                        entity.longitude(),
                        entity.responsibleOrganizationUnitId(),
                        entity.responsibleOrganizationUnitCode(),
                        entity.responsibleOrganizationUnitNameSnapshot(),
                        entity.responsibleActorId(),
                        entity.responsibleActorNameSnapshot(),
                        entity.workflowInstanceId(),
                        entity.currentEscalationLevel(),
                        entity.containedAt(),
                        entity.resolvedAt(),
                        entity.closedAt(),
                        entity.cancelledAt(),
                        entity.createdByActorId(),
                        entity.createdByActorNameSnapshot(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
        public static IncidentResponseActionJpaEntity toEntity(IncidentResponseAction model) {
            return new IncidentResponseActionJpaEntity(
                        model.id(),
                        model.incidentId(),
                        model.actionTypeId(),
                        model.actionStatus(),
                        model.description(),
                        model.targetType(),
                        model.targetReferenceId(),
                        model.targetReferenceCode(),
                        model.plannedStartAt(),
                        model.plannedEndAt(),
                        model.startedAt(),
                        model.completedAt(),
                        model.performedByActorId(),
                        model.performedByActorNameSnapshot(),
                        model.organizationUnitId(),
                        model.resultSummary(),
                        model.failureReason(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static IncidentResponseAction toDomain(IncidentResponseActionJpaEntity entity) {
            return new IncidentResponseAction(
                        entity.id(),
                        entity.incidentId(),
                        entity.actionTypeId(),
                        entity.actionStatus(),
                        entity.description(),
                        entity.targetType(),
                        entity.targetReferenceId(),
                        entity.targetReferenceCode(),
                        entity.plannedStartAt(),
                        entity.plannedEndAt(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.performedByActorId(),
                        entity.performedByActorNameSnapshot(),
                        entity.organizationUnitId(),
                        entity.resultSummary(),
                        entity.failureReason(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
        public static IncidentClosureJpaEntity toEntity(IncidentClosure model) {
            return new IncidentClosureJpaEntity(
                        model.id(),
                        model.incidentId(),
                        model.closureSummary(),
                        model.resolutionVerified(),
                        model.evidenceReviewed(),
                        model.rootCauseReviewed(),
                        model.followUpActionsCreated(),
                        model.closedByActorId(),
                        model.closedByActorNameSnapshot(),
                        model.closedAt(),
                        model.workflowInstanceId()
            );
        }

        public static IncidentClosure toDomain(IncidentClosureJpaEntity entity) {
            return new IncidentClosure(
                        entity.id(),
                        entity.incidentId(),
                        entity.closureSummary(),
                        entity.resolutionVerified(),
                        entity.evidenceReviewed(),
                        entity.rootCauseReviewed(),
                        entity.followUpActionsCreated(),
                        entity.closedByActorId(),
                        entity.closedByActorNameSnapshot(),
                        entity.closedAt(),
                        entity.workflowInstanceId()
            );
        }
        public static IncidentRelatedIncidentJpaEntity toEntity(IncidentRelatedIncident model) {
            return new IncidentRelatedIncidentJpaEntity(
                        model.id(),
                        model.incidentId(),
                        model.relatedIncidentId(),
                        model.relationshipTypeId(),
                        model.comment(),
                        model.createdByActorId(),
                        model.createdAt()
            );
        }

        public static IncidentRelatedIncident toDomain(IncidentRelatedIncidentJpaEntity entity) {
            return new IncidentRelatedIncident(
                        entity.id(),
                        entity.incidentId(),
                        entity.relatedIncidentId(),
                        entity.relationshipTypeId(),
                        entity.comment(),
                        entity.createdByActorId(),
                        entity.createdAt()
            );
        }
}
