/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningPersistenceMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.mapper
 *
 * @Description : Maps planning domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.planning.domain.model.*;
import dz.sh.hidra.modules.planning.infrastructure.persistence.entity.*;

/**
 * Maps planning domain models to JPA entities.
 */
public final class PlanningPersistenceMapper {

    private PlanningPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static PlanningPeriodJpaEntity toEntity(PlanningPeriod model) {
            return new PlanningPeriodJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.periodTypeId(),
                        model.periodStart(),
                        model.periodEnd(),
                        model.timeZone(),
                        model.status(),
                        model.createdByActorId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PlanningPeriod toDomain(PlanningPeriodJpaEntity entity) {
            return new PlanningPeriod(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.periodTypeId(),
                        entity.periodStart(),
                        entity.periodEnd(),
                        entity.timeZone(),
                        entity.status(),
                        entity.createdByActorId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static OperationalPlanJpaEntity toEntity(OperationalPlan model) {
            return new OperationalPlanJpaEntity(
                        model.id(),
                        model.periodId(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.planTypeId(),
                        model.productTypeId(),
                        model.topologyScopeType(),
                        model.topologyScopeId(),
                        model.topologyScopeCode(),
                        model.topologyScopeNameSnapshot(),
                        model.responsibleOrganizationUnitId(),
                        model.status(),
                        model.currentRevisionId(),
                        model.approvedRevisionId(),
                        model.createdByActorId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static OperationalPlan toDomain(OperationalPlanJpaEntity entity) {
            return new OperationalPlan(
                        entity.id(),
                        entity.periodId(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.planTypeId(),
                        entity.productTypeId(),
                        entity.topologyScopeType(),
                        entity.topologyScopeId(),
                        entity.topologyScopeCode(),
                        entity.topologyScopeNameSnapshot(),
                        entity.responsibleOrganizationUnitId(),
                        entity.status(),
                        entity.currentRevisionId(),
                        entity.approvedRevisionId(),
                        entity.createdByActorId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PlanRevisionJpaEntity toEntity(PlanRevision model) {
            return new PlanRevisionJpaEntity(
                        model.id(),
                        model.planId(),
                        model.revisionNumber(),
                        model.revisionCode(),
                        model.status(),
                        model.changeReasonCodeId(),
                        model.changeReasonText(),
                        model.baseRevisionId(),
                        model.submittedByActorId(),
                        model.submittedAt(),
                        model.approvedByActorId(),
                        model.approvedAt(),
                        model.workflowInstanceId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PlanRevision toDomain(PlanRevisionJpaEntity entity) {
            return new PlanRevision(
                        entity.id(),
                        entity.planId(),
                        entity.revisionNumber(),
                        entity.revisionCode(),
                        entity.status(),
                        entity.changeReasonCodeId(),
                        entity.changeReasonText(),
                        entity.baseRevisionId(),
                        entity.submittedByActorId(),
                        entity.submittedAt(),
                        entity.approvedByActorId(),
                        entity.approvedAt(),
                        entity.workflowInstanceId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PlanScenarioJpaEntity toEntity(PlanScenario model) {
            return new PlanScenarioJpaEntity(
                        model.id(),
                        model.revisionId(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.scenarioTypeId(),
                        model.primaryScenario(),
                        model.status(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PlanScenario toDomain(PlanScenarioJpaEntity entity) {
            return new PlanScenario(
                        entity.id(),
                        entity.revisionId(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.scenarioTypeId(),
                        entity.primaryScenario(),
                        entity.status(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static NominationJpaEntity toEntity(Nomination model) {
            return new NominationJpaEntity(
                        model.id(),
                        model.revisionId(),
                        model.scenarioId(),
                        model.code(),
                        model.nominationTypeId(),
                        model.productTypeId(),
                        model.quantity(),
                        model.quantityUnitId(),
                        model.rate(),
                        model.rateUnitId(),
                        model.sourceAssetType(),
                        model.sourceAssetId(),
                        model.sourceAssetCode(),
                        model.destinationAssetType(),
                        model.destinationAssetId(),
                        model.destinationAssetCode(),
                        model.shipperPartyId(),
                        model.shipperPartyCodeSnapshot(),
                        model.counterpartyId(),
                        model.contractReferenceId(),
                        model.priority(),
                        model.status(),
                        model.periodStart(),
                        model.periodEnd(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static Nomination toDomain(NominationJpaEntity entity) {
            return new Nomination(
                        entity.id(),
                        entity.revisionId(),
                        entity.scenarioId(),
                        entity.code(),
                        entity.nominationTypeId(),
                        entity.productTypeId(),
                        entity.quantity(),
                        entity.quantityUnitId(),
                        entity.rate(),
                        entity.rateUnitId(),
                        entity.sourceAssetType(),
                        entity.sourceAssetId(),
                        entity.sourceAssetCode(),
                        entity.destinationAssetType(),
                        entity.destinationAssetId(),
                        entity.destinationAssetCode(),
                        entity.shipperPartyId(),
                        entity.shipperPartyCodeSnapshot(),
                        entity.counterpartyId(),
                        entity.contractReferenceId(),
                        entity.priority(),
                        entity.status(),
                        entity.periodStart(),
                        entity.periodEnd(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static NominationScheduleLineJpaEntity toEntity(NominationScheduleLine model) {
            return new NominationScheduleLineJpaEntity(
                        model.id(),
                        model.nominationId(),
                        model.sequenceNumber(),
                        model.lineStart(),
                        model.lineEnd(),
                        model.plannedQuantity(),
                        model.quantityUnitId(),
                        model.plannedRate(),
                        model.rateUnitId(),
                        model.notes()
            );
        }

        public static NominationScheduleLine toDomain(NominationScheduleLineJpaEntity entity) {
            return new NominationScheduleLine(
                        entity.id(),
                        entity.nominationId(),
                        entity.sequenceNumber(),
                        entity.lineStart(),
                        entity.lineEnd(),
                        entity.plannedQuantity(),
                        entity.quantityUnitId(),
                        entity.plannedRate(),
                        entity.rateUnitId(),
                        entity.notes()
            );
        }

        public static PlanTargetJpaEntity toEntity(PlanTarget model) {
            return new PlanTargetJpaEntity(
                        model.id(),
                        model.revisionId(),
                        model.scenarioId(),
                        model.nominationId(),
                        model.targetTypeId(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.topologyAssetCode(),
                        model.topologyAssetNameSnapshot(),
                        model.telemetryPointId(),
                        model.telemetryPointCodeSnapshot(),
                        model.targetValue(),
                        model.targetTextValue(),
                        model.unitId(),
                        model.toleranceLow(),
                        model.toleranceHigh(),
                        model.validFrom(),
                        model.validTo(),
                        model.priority(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PlanTarget toDomain(PlanTargetJpaEntity entity) {
            return new PlanTarget(
                        entity.id(),
                        entity.revisionId(),
                        entity.scenarioId(),
                        entity.nominationId(),
                        entity.targetTypeId(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCode(),
                        entity.topologyAssetNameSnapshot(),
                        entity.telemetryPointId(),
                        entity.telemetryPointCodeSnapshot(),
                        entity.targetValue(),
                        entity.targetTextValue(),
                        entity.unitId(),
                        entity.toleranceLow(),
                        entity.toleranceHigh(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.priority(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ExpectedFlowStateJpaEntity toEntity(ExpectedFlowState model) {
            return new ExpectedFlowStateJpaEntity(
                        model.id(),
                        model.revisionId(),
                        model.scenarioId(),
                        model.planTargetId(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.topologyAssetCode(),
                        model.expectedAt(),
                        model.expectedFlowRate(),
                        model.flowRateUnitId(),
                        model.expectedPressureIn(),
                        model.expectedPressureOut(),
                        model.pressureUnitId(),
                        model.expectedTemperature(),
                        model.temperatureUnitId(),
                        model.expectedVolume(),
                        model.volumeUnitId(),
                        model.expectedOperatingMode(),
                        model.validFrom(),
                        model.validTo(),
                        model.createdAt()
            );
        }

        public static ExpectedFlowState toDomain(ExpectedFlowStateJpaEntity entity) {
            return new ExpectedFlowState(
                        entity.id(),
                        entity.revisionId(),
                        entity.scenarioId(),
                        entity.planTargetId(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCode(),
                        entity.expectedAt(),
                        entity.expectedFlowRate(),
                        entity.flowRateUnitId(),
                        entity.expectedPressureIn(),
                        entity.expectedPressureOut(),
                        entity.pressureUnitId(),
                        entity.expectedTemperature(),
                        entity.temperatureUnitId(),
                        entity.expectedVolume(),
                        entity.volumeUnitId(),
                        entity.expectedOperatingMode(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.createdAt()
            );
        }

        public static PlannedOperationWindowJpaEntity toEntity(PlannedOperationWindow model) {
            return new PlannedOperationWindowJpaEntity(
                        model.id(),
                        model.revisionId(),
                        model.scenarioId(),
                        model.code(),
                        model.windowTypeId(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.topologyAssetCode(),
                        model.plannedStart(),
                        model.plannedEnd(),
                        model.capacityImpactPercent(),
                        model.description(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PlannedOperationWindow toDomain(PlannedOperationWindowJpaEntity entity) {
            return new PlannedOperationWindow(
                        entity.id(),
                        entity.revisionId(),
                        entity.scenarioId(),
                        entity.code(),
                        entity.windowTypeId(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCode(),
                        entity.plannedStart(),
                        entity.plannedEnd(),
                        entity.capacityImpactPercent(),
                        entity.description(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PlanConstraintJpaEntity toEntity(PlanConstraint model) {
            return new PlanConstraintJpaEntity(
                        model.id(),
                        model.revisionId(),
                        model.scenarioId(),
                        model.constraintTypeId(),
                        model.severity(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.topologyAssetCode(),
                        model.constraintValue(),
                        model.unitId(),
                        model.validFrom(),
                        model.validTo(),
                        model.sourceModule(),
                        model.sourceReferenceId(),
                        model.description(),
                        model.blocking(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PlanConstraint toDomain(PlanConstraintJpaEntity entity) {
            return new PlanConstraint(
                        entity.id(),
                        entity.revisionId(),
                        entity.scenarioId(),
                        entity.constraintTypeId(),
                        entity.severity(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCode(),
                        entity.constraintValue(),
                        entity.unitId(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.sourceModule(),
                        entity.sourceReferenceId(),
                        entity.description(),
                        entity.blocking(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PlanApprovalReferenceJpaEntity toEntity(PlanApprovalReference model) {
            return new PlanApprovalReferenceJpaEntity(
                        model.id(),
                        model.revisionId(),
                        model.workflowInstanceId(),
                        model.workflowDefinitionCodeSnapshot(),
                        model.approvalStatusSnapshot(),
                        model.submittedByActorId(),
                        model.submittedAt(),
                        model.decidedByActorId(),
                        model.decidedAt(),
                        model.decisionReasonSnapshot(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PlanApprovalReference toDomain(PlanApprovalReferenceJpaEntity entity) {
            return new PlanApprovalReference(
                        entity.id(),
                        entity.revisionId(),
                        entity.workflowInstanceId(),
                        entity.workflowDefinitionCodeSnapshot(),
                        entity.approvalStatusSnapshot(),
                        entity.submittedByActorId(),
                        entity.submittedAt(),
                        entity.decidedByActorId(),
                        entity.decidedAt(),
                        entity.decisionReasonSnapshot(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PlanningCatalogEntryJpaEntity toEntity(PlanningCatalogEntry model) {
            return new PlanningCatalogEntryJpaEntity(
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

        public static PlanningCatalogEntry toDomain(PlanningCatalogEntryJpaEntity entity) {
            return new PlanningCatalogEntry(
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

        public static PlanningCatalogTranslationJpaEntity toEntity(PlanningCatalogTranslation model) {
            return new PlanningCatalogTranslationJpaEntity(
                        model.id(),
                        model.catalogEntryId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PlanningCatalogTranslation toDomain(PlanningCatalogTranslationJpaEntity entity) {
            return new PlanningCatalogTranslation(
                        entity.id(),
                        entity.catalogEntryId(),
                        entity.locale(),
                        entity.name(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ForecastSeriesJpaEntity toEntity(ForecastSeries model) {
            return new ForecastSeriesJpaEntity(
                        model.id(),
                        model.periodId(),
                        model.code(),
                        model.forecastTypeId(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.productTypeId(),
                        model.sourceModule(),
                        model.sourceReferenceId(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ForecastSeries toDomain(ForecastSeriesJpaEntity entity) {
            return new ForecastSeries(
                        entity.id(),
                        entity.periodId(),
                        entity.code(),
                        entity.forecastTypeId(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.productTypeId(),
                        entity.sourceModule(),
                        entity.sourceReferenceId(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ForecastPointJpaEntity toEntity(ForecastPoint model) {
            return new ForecastPointJpaEntity(
                        model.id(),
                        model.forecastSeriesId(),
                        model.forecastAt(),
                        model.validFrom(),
                        model.validTo(),
                        model.value(),
                        model.unitId(),
                        model.confidenceLevel(),
                        model.createdAt()
            );
        }

        public static ForecastPoint toDomain(ForecastPointJpaEntity entity) {
            return new ForecastPoint(
                        entity.id(),
                        entity.forecastSeriesId(),
                        entity.forecastAt(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.value(),
                        entity.unitId(),
                        entity.confidenceLevel(),
                        entity.createdAt()
            );
        }

        public static PlanActualReviewSnapshotJpaEntity toEntity(PlanActualReviewSnapshot model) {
            return new PlanActualReviewSnapshotJpaEntity(
                        model.id(),
                        model.planTargetId(),
                        model.monitoringDeviationId(),
                        model.trustedTelemetryReadingId(),
                        model.actualValue(),
                        model.plannedValue(),
                        model.differenceValue(),
                        model.differencePercent(),
                        model.unitId(),
                        model.reviewedAt(),
                        model.reviewSource(),
                        model.notes()
            );
        }

        public static PlanActualReviewSnapshot toDomain(PlanActualReviewSnapshotJpaEntity entity) {
            return new PlanActualReviewSnapshot(
                        entity.id(),
                        entity.planTargetId(),
                        entity.monitoringDeviationId(),
                        entity.trustedTelemetryReadingId(),
                        entity.actualValue(),
                        entity.plannedValue(),
                        entity.differenceValue(),
                        entity.differencePercent(),
                        entity.unitId(),
                        entity.reviewedAt(),
                        entity.reviewSource(),
                        entity.notes()
            );
        }

}
