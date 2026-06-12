/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HsePersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.mapper
 *
 * @Description : Maps HSE domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.hse.domain.model.*;
import dz.sh.hidra.modules.hse.infrastructure.persistence.entity.*;

/**
 * Maps HSE domain models to JPA entities.
 */
public final class HsePersistenceMapper {

    private HsePersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static HseCaseJpaEntity toEntity(HseCase model) {
            return new HseCaseJpaEntity(
                        model.id(),
                        model.caseNumber(),
                        model.title(),
                        model.description(),
                        model.caseTypeId(),
                        model.severityId(),
                        model.priorityId(),
                        model.status(),
                        model.sourceType(),
                        model.incidentReferenceId(),
                        model.incidentCodeSnapshot(),
                        model.incidentTitleSnapshot(),
                        model.targetModule(),
                        model.targetTypeCode(),
                        model.targetId(),
                        model.targetCodeSnapshot(),
                        model.targetLabelSnapshot(),
                        model.occurredAt(),
                        model.reportedAt(),
                        model.reportedByActorId(),
                        model.reportedByDisplayNameSnapshot(),
                        model.responsibleOrganizationUnitId(),
                        model.responsibleOrganizationUnitNameSnapshot(),
                        model.workflowInstanceId(),
                        model.auditReferenceId(),
                        model.controlledAt(),
                        model.resolvedAt(),
                        model.closedAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static HseCase toDomain(HseCaseJpaEntity entity) {
            return new HseCase(
                        entity.id(),
                        entity.caseNumber(),
                        entity.title(),
                        entity.description(),
                        entity.caseTypeId(),
                        entity.severityId(),
                        entity.priorityId(),
                        entity.status(),
                        entity.sourceType(),
                        entity.incidentReferenceId(),
                        entity.incidentCodeSnapshot(),
                        entity.incidentTitleSnapshot(),
                        entity.targetModule(),
                        entity.targetTypeCode(),
                        entity.targetId(),
                        entity.targetCodeSnapshot(),
                        entity.targetLabelSnapshot(),
                        entity.occurredAt(),
                        entity.reportedAt(),
                        entity.reportedByActorId(),
                        entity.reportedByDisplayNameSnapshot(),
                        entity.responsibleOrganizationUnitId(),
                        entity.responsibleOrganizationUnitNameSnapshot(),
                        entity.workflowInstanceId(),
                        entity.auditReferenceId(),
                        entity.controlledAt(),
                        entity.resolvedAt(),
                        entity.closedAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static HseCaseStatusHistoryJpaEntity toEntity(HseCaseStatusHistory model) {
            return new HseCaseStatusHistoryJpaEntity(
                        model.id(),
                        model.hseCaseId(),
                        model.oldStatus(),
                        model.newStatus(),
                        model.reasonId(),
                        model.reasonText(),
                        model.changedByActorId(),
                        model.changedByDisplayNameSnapshot(),
                        model.changedAt(),
                        model.correlationId()
            );
        }

        public static HseCaseStatusHistory toDomain(HseCaseStatusHistoryJpaEntity entity) {
            return new HseCaseStatusHistory(
                        entity.id(),
                        entity.hseCaseId(),
                        entity.oldStatus(),
                        entity.newStatus(),
                        entity.reasonId(),
                        entity.reasonText(),
                        entity.changedByActorId(),
                        entity.changedByDisplayNameSnapshot(),
                        entity.changedAt(),
                        entity.correlationId()
            );
        }

        public static HseCaseEvidenceLinkJpaEntity toEntity(HseCaseEvidenceLink model) {
            return new HseCaseEvidenceLinkJpaEntity(
                        model.id(),
                        model.hseCaseId(),
                        model.evidenceType(),
                        model.evidenceReferenceId(),
                        model.evidenceCodeSnapshot(),
                        model.evidenceLabelSnapshot(),
                        model.evidenceSummary(),
                        model.evidenceTimestamp(),
                        model.attachedByActorId(),
                        model.attachedAt()
            );
        }

        public static HseCaseEvidenceLink toDomain(HseCaseEvidenceLinkJpaEntity entity) {
            return new HseCaseEvidenceLink(
                        entity.id(),
                        entity.hseCaseId(),
                        entity.evidenceType(),
                        entity.evidenceReferenceId(),
                        entity.evidenceCodeSnapshot(),
                        entity.evidenceLabelSnapshot(),
                        entity.evidenceSummary(),
                        entity.evidenceTimestamp(),
                        entity.attachedByActorId(),
                        entity.attachedAt()
            );
        }

        public static HseImpactAssessmentJpaEntity toEntity(HseImpactAssessment model) {
            return new HseImpactAssessmentJpaEntity(
                        model.id(),
                        model.hseCaseId(),
                        model.impactDomain(),
                        model.impactTypeId(),
                        model.severity(),
                        model.description(),
                        model.peopleAffectedCount(),
                        model.injuryCount(),
                        model.spillVolume(),
                        model.spillVolumeUnitId(),
                        model.estimatedCost(),
                        model.currencyCode(),
                        model.regulatoryReferenceId(),
                        model.assessedByActorId(),
                        model.assessedAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static HseImpactAssessment toDomain(HseImpactAssessmentJpaEntity entity) {
            return new HseImpactAssessment(
                        entity.id(),
                        entity.hseCaseId(),
                        entity.impactDomain(),
                        entity.impactTypeId(),
                        entity.severity(),
                        entity.description(),
                        entity.peopleAffectedCount(),
                        entity.injuryCount(),
                        entity.spillVolume(),
                        entity.spillVolumeUnitId(),
                        entity.estimatedCost(),
                        entity.currencyCode(),
                        entity.regulatoryReferenceId(),
                        entity.assessedByActorId(),
                        entity.assessedAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static HseCorrectivePreventiveActionJpaEntity toEntity(HseCorrectivePreventiveAction model) {
            return new HseCorrectivePreventiveActionJpaEntity(
                        model.id(),
                        model.hseCaseId(),
                        model.actionNumber(),
                        model.actionTypeId(),
                        model.title(),
                        model.description(),
                        model.ownerActorId(),
                        model.ownerDisplayNameSnapshot(),
                        model.ownerOrganizationUnitId(),
                        model.ownerOrganizationUnitNameSnapshot(),
                        model.targetDate(),
                        model.completedAt(),
                        model.verificationRequired(),
                        model.verifiedByActorId(),
                        model.verifiedAt(),
                        model.status(),
                        model.linkedWorkOrderId(),
                        model.workflowTaskId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static HseCorrectivePreventiveAction toDomain(HseCorrectivePreventiveActionJpaEntity entity) {
            return new HseCorrectivePreventiveAction(
                        entity.id(),
                        entity.hseCaseId(),
                        entity.actionNumber(),
                        entity.actionTypeId(),
                        entity.title(),
                        entity.description(),
                        entity.ownerActorId(),
                        entity.ownerDisplayNameSnapshot(),
                        entity.ownerOrganizationUnitId(),
                        entity.ownerOrganizationUnitNameSnapshot(),
                        entity.targetDate(),
                        entity.completedAt(),
                        entity.verificationRequired(),
                        entity.verifiedByActorId(),
                        entity.verifiedAt(),
                        entity.status(),
                        entity.linkedWorkOrderId(),
                        entity.workflowTaskId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static HseClosureJpaEntity toEntity(HseClosure model) {
            return new HseClosureJpaEntity(
                        model.id(),
                        model.hseCaseId(),
                        model.closureSummary(),
                        model.impactAssessed(),
                        model.capaCompleted(),
                        model.evidenceReviewed(),
                        model.regulatoryReviewed(),
                        model.closedByActorId(),
                        model.closedByDisplayNameSnapshot(),
                        model.closedAt(),
                        model.workflowInstanceId()
            );
        }

        public static HseClosure toDomain(HseClosureJpaEntity entity) {
            return new HseClosure(
                        entity.id(),
                        entity.hseCaseId(),
                        entity.closureSummary(),
                        entity.impactAssessed(),
                        entity.capaCompleted(),
                        entity.evidenceReviewed(),
                        entity.regulatoryReviewed(),
                        entity.closedByActorId(),
                        entity.closedByDisplayNameSnapshot(),
                        entity.closedAt(),
                        entity.workflowInstanceId()
            );
        }

        public static HazardReportJpaEntity toEntity(HazardReport model) {
            return new HazardReportJpaEntity(
                        model.id(),
                        model.reportNumber(),
                        model.hazardTypeId(),
                        model.title(),
                        model.description(),
                        model.targetModule(),
                        model.targetTypeCode(),
                        model.targetId(),
                        model.targetCodeSnapshot(),
                        model.initialSeverity(),
                        model.status(),
                        model.reportedByActorId(),
                        model.reportedAt(),
                        model.linkedHseCaseId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static HazardReport toDomain(HazardReportJpaEntity entity) {
            return new HazardReport(
                        entity.id(),
                        entity.reportNumber(),
                        entity.hazardTypeId(),
                        entity.title(),
                        entity.description(),
                        entity.targetModule(),
                        entity.targetTypeCode(),
                        entity.targetId(),
                        entity.targetCodeSnapshot(),
                        entity.initialSeverity(),
                        entity.status(),
                        entity.reportedByActorId(),
                        entity.reportedAt(),
                        entity.linkedHseCaseId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static NearMissReportJpaEntity toEntity(NearMissReport model) {
            return new NearMissReportJpaEntity(
                        model.id(),
                        model.reportNumber(),
                        model.nearMissTypeId(),
                        model.title(),
                        model.description(),
                        model.potentialConsequenceId(),
                        model.potentialSeverity(),
                        model.targetModule(),
                        model.targetTypeCode(),
                        model.targetId(),
                        model.status(),
                        model.reportedByActorId(),
                        model.reportedAt(),
                        model.linkedHseCaseId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static NearMissReport toDomain(NearMissReportJpaEntity entity) {
            return new NearMissReport(
                        entity.id(),
                        entity.reportNumber(),
                        entity.nearMissTypeId(),
                        entity.title(),
                        entity.description(),
                        entity.potentialConsequenceId(),
                        entity.potentialSeverity(),
                        entity.targetModule(),
                        entity.targetTypeCode(),
                        entity.targetId(),
                        entity.status(),
                        entity.reportedByActorId(),
                        entity.reportedAt(),
                        entity.linkedHseCaseId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static SafetyObservationJpaEntity toEntity(SafetyObservation model) {
            return new SafetyObservationJpaEntity(
                        model.id(),
                        model.observationNumber(),
                        model.observationType(),
                        model.title(),
                        model.description(),
                        model.targetModule(),
                        model.targetTypeCode(),
                        model.targetId(),
                        model.observedByActorId(),
                        model.observedAt(),
                        model.status(),
                        model.linkedHseCaseId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static SafetyObservation toDomain(SafetyObservationJpaEntity entity) {
            return new SafetyObservation(
                        entity.id(),
                        entity.observationNumber(),
                        entity.observationType(),
                        entity.title(),
                        entity.description(),
                        entity.targetModule(),
                        entity.targetTypeCode(),
                        entity.targetId(),
                        entity.observedByActorId(),
                        entity.observedAt(),
                        entity.status(),
                        entity.linkedHseCaseId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static EnvironmentalEventJpaEntity toEntity(EnvironmentalEvent model) {
            return new EnvironmentalEventJpaEntity(
                        model.id(),
                        model.eventNumber(),
                        model.eventType(),
                        model.title(),
                        model.description(),
                        model.substanceId(),
                        model.quantity(),
                        model.quantityUnitId(),
                        model.mediumAffectedId(),
                        model.targetModule(),
                        model.targetTypeCode(),
                        model.targetId(),
                        model.severity(),
                        model.status(),
                        model.occurredAt(),
                        model.linkedHseCaseId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static EnvironmentalEvent toDomain(EnvironmentalEventJpaEntity entity) {
            return new EnvironmentalEvent(
                        entity.id(),
                        entity.eventNumber(),
                        entity.eventType(),
                        entity.title(),
                        entity.description(),
                        entity.substanceId(),
                        entity.quantity(),
                        entity.quantityUnitId(),
                        entity.mediumAffectedId(),
                        entity.targetModule(),
                        entity.targetTypeCode(),
                        entity.targetId(),
                        entity.severity(),
                        entity.status(),
                        entity.occurredAt(),
                        entity.linkedHseCaseId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PermitToWorkJpaEntity toEntity(PermitToWork model) {
            return new PermitToWorkJpaEntity(
                        model.id(),
                        model.permitNumber(),
                        model.permitTypeId(),
                        model.title(),
                        model.description(),
                        model.targetModule(),
                        model.targetTypeCode(),
                        model.targetId(),
                        model.requestedByActorId(),
                        model.approvedByActorId(),
                        model.validFrom(),
                        model.validTo(),
                        model.status(),
                        model.workflowInstanceId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PermitToWork toDomain(PermitToWorkJpaEntity entity) {
            return new PermitToWork(
                        entity.id(),
                        entity.permitNumber(),
                        entity.permitTypeId(),
                        entity.title(),
                        entity.description(),
                        entity.targetModule(),
                        entity.targetTypeCode(),
                        entity.targetId(),
                        entity.requestedByActorId(),
                        entity.approvedByActorId(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.status(),
                        entity.workflowInstanceId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static HseInspectionJpaEntity toEntity(HseInspection model) {
            return new HseInspectionJpaEntity(
                        model.id(),
                        model.inspectionNumber(),
                        model.inspectionTypeId(),
                        model.title(),
                        model.targetModule(),
                        model.targetTypeCode(),
                        model.targetId(),
                        model.inspectorActorId(),
                        model.plannedAt(),
                        model.startedAt(),
                        model.completedAt(),
                        model.status(),
                        model.findingSummary(),
                        model.linkedHseCaseId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static HseInspection toDomain(HseInspectionJpaEntity entity) {
            return new HseInspection(
                        entity.id(),
                        entity.inspectionNumber(),
                        entity.inspectionTypeId(),
                        entity.title(),
                        entity.targetModule(),
                        entity.targetTypeCode(),
                        entity.targetId(),
                        entity.inspectorActorId(),
                        entity.plannedAt(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.status(),
                        entity.findingSummary(),
                        entity.linkedHseCaseId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static EmergencyDrillJpaEntity toEntity(EmergencyDrill model) {
            return new EmergencyDrillJpaEntity(
                        model.id(),
                        model.drillNumber(),
                        model.drillTypeId(),
                        model.title(),
                        model.targetModule(),
                        model.targetTypeCode(),
                        model.targetId(),
                        model.plannedAt(),
                        model.executedAt(),
                        model.status(),
                        model.participantsCount(),
                        model.evaluationSummary(),
                        model.linkedHseCaseId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static EmergencyDrill toDomain(EmergencyDrillJpaEntity entity) {
            return new EmergencyDrill(
                        entity.id(),
                        entity.drillNumber(),
                        entity.drillTypeId(),
                        entity.title(),
                        entity.targetModule(),
                        entity.targetTypeCode(),
                        entity.targetId(),
                        entity.plannedAt(),
                        entity.executedAt(),
                        entity.status(),
                        entity.participantsCount(),
                        entity.evaluationSummary(),
                        entity.linkedHseCaseId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ComplianceObligationJpaEntity toEntity(ComplianceObligation model) {
            return new ComplianceObligationJpaEntity(
                        model.id(),
                        model.obligationNumber(),
                        model.obligationTypeId(),
                        model.regulatoryReference(),
                        model.title(),
                        model.description(),
                        model.jurisdictionId(),
                        model.responsibleOrganizationUnitId(),
                        model.effectiveFrom(),
                        model.effectiveTo(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ComplianceObligation toDomain(ComplianceObligationJpaEntity entity) {
            return new ComplianceObligation(
                        entity.id(),
                        entity.obligationNumber(),
                        entity.obligationTypeId(),
                        entity.regulatoryReference(),
                        entity.title(),
                        entity.description(),
                        entity.jurisdictionId(),
                        entity.responsibleOrganizationUnitId(),
                        entity.effectiveFrom(),
                        entity.effectiveTo(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ComplianceAssessmentJpaEntity toEntity(ComplianceAssessment model) {
            return new ComplianceAssessmentJpaEntity(
                        model.id(),
                        model.obligationId(),
                        model.assessmentNumber(),
                        model.complianceStatus(),
                        model.assessmentSummary(),
                        model.assessedByActorId(),
                        model.assessedAt(),
                        model.evidenceReferenceId(),
                        model.linkedHseCaseId(),
                        model.nextAssessmentDueAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ComplianceAssessment toDomain(ComplianceAssessmentJpaEntity entity) {
            return new ComplianceAssessment(
                        entity.id(),
                        entity.obligationId(),
                        entity.assessmentNumber(),
                        entity.complianceStatus(),
                        entity.assessmentSummary(),
                        entity.assessedByActorId(),
                        entity.assessedAt(),
                        entity.evidenceReferenceId(),
                        entity.linkedHseCaseId(),
                        entity.nextAssessmentDueAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static HseCatalogEntryJpaEntity toEntity(HseCatalogEntry model) {
            return new HseCatalogEntryJpaEntity(
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

        public static HseCatalogEntry toDomain(HseCatalogEntryJpaEntity entity) {
            return new HseCatalogEntry(
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

        public static HseCatalogTranslationJpaEntity toEntity(HseCatalogTranslation model) {
            return new HseCatalogTranslationJpaEntity(
                        model.id(),
                        model.catalogEntryId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static HseCatalogTranslation toDomain(HseCatalogTranslationJpaEntity entity) {
            return new HseCatalogTranslation(
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
