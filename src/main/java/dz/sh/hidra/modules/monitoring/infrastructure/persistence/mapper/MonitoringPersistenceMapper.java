/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.mapper
 *
 * @Description : Maps monitoring domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.monitoring.domain.model.*;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity.*;

/**
 * Maps monitoring domain models to JPA entities.
 */
public final class MonitoringPersistenceMapper {

    private MonitoringPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static MonitoringRuleJpaEntity toEntity(MonitoringRule model) {
            return new MonitoringRuleJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.ruleType(),
                        model.evaluationFrequencyId(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.topologyAssetCode(),
                        model.telemetryPointId(),
                        model.planningTargetTypeId(),
                        model.expression(),
                        model.status(),
                        model.createdByActorId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static MonitoringRule toDomain(MonitoringRuleJpaEntity entity) {
            return new MonitoringRule(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.ruleType(),
                        entity.evaluationFrequencyId(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCode(),
                        entity.telemetryPointId(),
                        entity.planningTargetTypeId(),
                        entity.expression(),
                        entity.status(),
                        entity.createdByActorId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
        public static PlanActualDeviationJpaEntity toEntity(PlanActualDeviation model) {
            return new PlanActualDeviationJpaEntity(
                        model.id(),
                        model.evaluationId(),
                        model.planTargetId(),
                        model.expectedFlowStateId(),
                        model.trustedTelemetryReadingId(),
                        model.telemetryPointId(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.topologyAssetCode(),
                        model.actualValue(),
                        model.expectedValue(),
                        model.differenceValue(),
                        model.differencePercent(),
                        model.unitId(),
                        model.severity(),
                        model.status(),
                        model.detectedAt(),
                        model.resolvedAt(),
                        model.reasonCode(),
                        model.reasonMessage()
            );
        }

        public static PlanActualDeviation toDomain(PlanActualDeviationJpaEntity entity) {
            return new PlanActualDeviation(
                        entity.id(),
                        entity.evaluationId(),
                        entity.planTargetId(),
                        entity.expectedFlowStateId(),
                        entity.trustedTelemetryReadingId(),
                        entity.telemetryPointId(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCode(),
                        entity.actualValue(),
                        entity.expectedValue(),
                        entity.differenceValue(),
                        entity.differencePercent(),
                        entity.unitId(),
                        entity.severity(),
                        entity.status(),
                        entity.detectedAt(),
                        entity.resolvedAt(),
                        entity.reasonCode(),
                        entity.reasonMessage()
            );
        }
}
