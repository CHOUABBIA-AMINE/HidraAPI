/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
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
}
