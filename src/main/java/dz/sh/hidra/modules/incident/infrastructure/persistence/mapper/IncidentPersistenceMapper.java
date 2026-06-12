/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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

        public static IncidentTimelineEntryJpaEntity toEntity(IncidentTimelineEntry model) {
            return new IncidentTimelineEntryJpaEntity(
                        model.id(),
                        model.incidentId(),
                        model.entryTypeId(),
                        model.statusBefore(),
                        model.statusAfter(),
                        model.title(),
                        model.description(),
                        model.actorId(),
                        model.actorNameSnapshot(),
                        model.organizationUnitId(),
                        model.organizationUnitNameSnapshot(),
                        model.occurredAt(),
                        model.recordedAt(),
                        model.correlationId()
            );
        }

        public static IncidentTimelineEntry toDomain(IncidentTimelineEntryJpaEntity entity) {
            return new IncidentTimelineEntry(
                        entity.id(),
                        entity.incidentId(),
                        entity.entryTypeId(),
                        entity.statusBefore(),
                        entity.statusAfter(),
                        entity.title(),
                        entity.description(),
                        entity.actorId(),
                        entity.actorNameSnapshot(),
                        entity.organizationUnitId(),
                        entity.organizationUnitNameSnapshot(),
                        entity.occurredAt(),
                        entity.recordedAt(),
                        entity.correlationId()
            );
        }

        public static IncidentEvidenceLinkJpaEntity toEntity(IncidentEvidenceLink model) {
            return new IncidentEvidenceLinkJpaEntity(
                        model.id(),
                        model.incidentId(),
                        model.evidenceType(),
                        model.evidenceReferenceId(),
                        model.evidenceReferenceCode(),
                        model.evidenceTitle(),
                        model.evidenceSummary(),
                        model.evidenceTimestamp(),
                        model.attachedByActorId(),
                        model.attachedAt()
            );
        }

        public static IncidentEvidenceLink toDomain(IncidentEvidenceLinkJpaEntity entity) {
            return new IncidentEvidenceLink(
                        entity.id(),
                        entity.incidentId(),
                        entity.evidenceType(),
                        entity.evidenceReferenceId(),
                        entity.evidenceReferenceCode(),
                        entity.evidenceTitle(),
                        entity.evidenceSummary(),
                        entity.evidenceTimestamp(),
                        entity.attachedByActorId(),
                        entity.attachedAt()
            );
        }

        public static IncidentAssignmentJpaEntity toEntity(IncidentAssignment model) {
            return new IncidentAssignmentJpaEntity(
                        model.id(),
                        model.incidentId(),
                        model.assignmentTypeId(),
                        model.assignedOrganizationUnitId(),
                        model.assignedOrganizationUnitNameSnapshot(),
                        model.assignedActorId(),
                        model.assignedActorNameSnapshot(),
                        model.assignedByActorId(),
                        model.assignedAt(),
                        model.acceptedAt(),
                        model.releasedAt(),
                        model.releaseReason(),
                        model.primaryAssignment()
            );
        }

        public static IncidentAssignment toDomain(IncidentAssignmentJpaEntity entity) {
            return new IncidentAssignment(
                        entity.id(),
                        entity.incidentId(),
                        entity.assignmentTypeId(),
                        entity.assignedOrganizationUnitId(),
                        entity.assignedOrganizationUnitNameSnapshot(),
                        entity.assignedActorId(),
                        entity.assignedActorNameSnapshot(),
                        entity.assignedByActorId(),
                        entity.assignedAt(),
                        entity.acceptedAt(),
                        entity.releasedAt(),
                        entity.releaseReason(),
                        entity.primaryAssignment()
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

        public static IncidentImpactAssessmentJpaEntity toEntity(IncidentImpactAssessment model) {
            return new IncidentImpactAssessmentJpaEntity(
                        model.id(),
                        model.incidentId(),
                        model.impactTypeId(),
                        model.impactLevelId(),
                        model.estimated(),
                        model.description(),
                        model.affectedTopologyAssetTypeCode(),
                        model.affectedTopologyAssetId(),
                        model.affectedOrganizationUnitId(),
                        model.estimatedVolumeLoss(),
                        model.estimatedVolumeUnitId(),
                        model.estimatedDurationMinutes(),
                        model.assessedByActorId(),
                        model.assessedAt()
            );
        }

        public static IncidentImpactAssessment toDomain(IncidentImpactAssessmentJpaEntity entity) {
            return new IncidentImpactAssessment(
                        entity.id(),
                        entity.incidentId(),
                        entity.impactTypeId(),
                        entity.impactLevelId(),
                        entity.estimated(),
                        entity.description(),
                        entity.affectedTopologyAssetTypeCode(),
                        entity.affectedTopologyAssetId(),
                        entity.affectedOrganizationUnitId(),
                        entity.estimatedVolumeLoss(),
                        entity.estimatedVolumeUnitId(),
                        entity.estimatedDurationMinutes(),
                        entity.assessedByActorId(),
                        entity.assessedAt()
            );
        }

        public static IncidentRootCauseAnalysisJpaEntity toEntity(IncidentRootCauseAnalysis model) {
            return new IncidentRootCauseAnalysisJpaEntity(
                        model.id(),
                        model.incidentId(),
                        model.rootCauseCategoryId(),
                        model.rootCauseCodeId(),
                        model.methodId(),
                        model.summary(),
                        model.analysisDetails(),
                        model.contributingFactors(),
                        model.confidenceLevelId(),
                        model.performedByActorId(),
                        model.performedAt(),
                        model.approvedByActorId(),
                        model.approvedAt()
            );
        }

        public static IncidentRootCauseAnalysis toDomain(IncidentRootCauseAnalysisJpaEntity entity) {
            return new IncidentRootCauseAnalysis(
                        entity.id(),
                        entity.incidentId(),
                        entity.rootCauseCategoryId(),
                        entity.rootCauseCodeId(),
                        entity.methodId(),
                        entity.summary(),
                        entity.analysisDetails(),
                        entity.contributingFactors(),
                        entity.confidenceLevelId(),
                        entity.performedByActorId(),
                        entity.performedAt(),
                        entity.approvedByActorId(),
                        entity.approvedAt()
            );
        }

        public static IncidentResolutionJpaEntity toEntity(IncidentResolution model) {
            return new IncidentResolutionJpaEntity(
                        model.id(),
                        model.incidentId(),
                        model.resolutionTypeId(),
                        model.resolutionSummary(),
                        model.correctiveActionRequired(),
                        model.preventiveActionRequired(),
                        model.residualRiskLevelId(),
                        model.resolvedByActorId(),
                        model.resolvedAt(),
                        model.workflowInstanceId()
            );
        }

        public static IncidentResolution toDomain(IncidentResolutionJpaEntity entity) {
            return new IncidentResolution(
                        entity.id(),
                        entity.incidentId(),
                        entity.resolutionTypeId(),
                        entity.resolutionSummary(),
                        entity.correctiveActionRequired(),
                        entity.preventiveActionRequired(),
                        entity.residualRiskLevelId(),
                        entity.resolvedByActorId(),
                        entity.resolvedAt(),
                        entity.workflowInstanceId()
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

        public static IncidentEscalationJpaEntity toEntity(IncidentEscalation model) {
            return new IncidentEscalationJpaEntity(
                        model.id(),
                        model.incidentId(),
                        model.fromLevel(),
                        model.toLevel(),
                        model.reasonId(),
                        model.reasonComment(),
                        model.escalatedToOrganizationUnitId(),
                        model.escalatedToActorId(),
                        model.escalatedByActorId(),
                        model.escalatedAt(),
                        model.acknowledgedAt()
            );
        }

        public static IncidentEscalation toDomain(IncidentEscalationJpaEntity entity) {
            return new IncidentEscalation(
                        entity.id(),
                        entity.incidentId(),
                        entity.fromLevel(),
                        entity.toLevel(),
                        entity.reasonId(),
                        entity.reasonComment(),
                        entity.escalatedToOrganizationUnitId(),
                        entity.escalatedToActorId(),
                        entity.escalatedByActorId(),
                        entity.escalatedAt(),
                        entity.acknowledgedAt()
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

        public static IncidentAttachmentReferenceJpaEntity toEntity(IncidentAttachmentReference model) {
            return new IncidentAttachmentReferenceJpaEntity(
                        model.id(),
                        model.incidentId(),
                        model.documentReferenceId(),
                        model.documentTypeId(),
                        model.filenameSnapshot(),
                        model.contentType(),
                        model.description(),
                        model.uploadedByActorId(),
                        model.uploadedAt()
            );
        }

        public static IncidentAttachmentReference toDomain(IncidentAttachmentReferenceJpaEntity entity) {
            return new IncidentAttachmentReference(
                        entity.id(),
                        entity.incidentId(),
                        entity.documentReferenceId(),
                        entity.documentTypeId(),
                        entity.filenameSnapshot(),
                        entity.contentType(),
                        entity.description(),
                        entity.uploadedByActorId(),
                        entity.uploadedAt()
            );
        }

        public static IncidentCatalogEntryJpaEntity toEntity(IncidentCatalogEntry model) {
            return new IncidentCatalogEntryJpaEntity(
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

        public static IncidentCatalogEntry toDomain(IncidentCatalogEntryJpaEntity entity) {
            return new IncidentCatalogEntry(
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

        public static IncidentCatalogTranslationJpaEntity toEntity(IncidentCatalogTranslation model) {
            return new IncidentCatalogTranslationJpaEntity(
                        model.id(),
                        model.catalogEntryId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static IncidentCatalogTranslation toDomain(IncidentCatalogTranslationJpaEntity entity) {
            return new IncidentCatalogTranslation(
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
