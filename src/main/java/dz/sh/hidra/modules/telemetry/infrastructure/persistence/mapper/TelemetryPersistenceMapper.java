/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper
 *
 * @Description : Maps telemetry domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.telemetry.domain.model.*;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.*;

/**
 * Maps telemetry domain models to JPA entities.
 */
public final class TelemetryPersistenceMapper {

    private TelemetryPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static TelemetrySourceJpaEntity toEntity(TelemetrySource model) {
            return new TelemetrySourceJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.sourceTypeId(),
                        model.protocolId(),
                        model.endpointUri(),
                        model.externalReference(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static TelemetrySource toDomain(TelemetrySourceJpaEntity entity) {
            return new TelemetrySource(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.sourceTypeId(),
                        entity.protocolId(),
                        entity.endpointUri(),
                        entity.externalReference(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
        public static TelemetryPointJpaEntity toEntity(TelemetryPoint model) {
            return new TelemetryPointJpaEntity(
                        model.id(),
                        model.deviceId(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.pointTypeId(),
                        model.signalTypeId(),
                        model.unitId(),
                        model.defaultAggregationMethodId(),
                        model.samplingPeriodSeconds(),
                        model.externalReference(),
                        model.deadbandValue(),
                        model.minOperationalValue(),
                        model.maxOperationalValue(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static TelemetryPoint toDomain(TelemetryPointJpaEntity entity) {
            return new TelemetryPoint(
                        entity.id(),
                        entity.deviceId(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.pointTypeId(),
                        entity.signalTypeId(),
                        entity.unitId(),
                        entity.defaultAggregationMethodId(),
                        entity.samplingPeriodSeconds(),
                        entity.externalReference(),
                        entity.deadbandValue(),
                        entity.minOperationalValue(),
                        entity.maxOperationalValue(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
        public static TelemetryReadingJpaEntity toEntity(TelemetryReading model) {
            return new TelemetryReadingJpaEntity(
                        model.id(),
                        model.pointId(),
                        model.numericValue(),
                        model.textValue(),
                        model.booleanValue(),
                        model.qualityCodeId(),
                        model.sourceTimestamp(),
                        model.receivedAt(),
                        model.state(),
                        model.ingestionBatchId(),
                        model.correlationId(),
                        model.rejectionReason(),
                        model.sourceSequenceNumber(),
                        model.externalTagMappingId(),
                        model.rawPayloadHash(),
                        model.createdAt()
            );
        }

        public static TelemetryReading toDomain(TelemetryReadingJpaEntity entity) {
            return new TelemetryReading(
                        entity.id(),
                        entity.pointId(),
                        entity.numericValue(),
                        entity.textValue(),
                        entity.booleanValue(),
                        entity.qualityCodeId(),
                        entity.sourceTimestamp(),
                        entity.receivedAt(),
                        entity.state(),
                        entity.ingestionBatchId(),
                        entity.correlationId(),
                        entity.rejectionReason(),
                        entity.sourceSequenceNumber(),
                        entity.externalTagMappingId(),
                        entity.rawPayloadHash(),
                        entity.createdAt()
            );
        }
        public static TrustedTelemetryReadingJpaEntity toEntity(TrustedTelemetryReading model) {
            return new TrustedTelemetryReadingJpaEntity(
                        model.id(),
                        model.readingId(),
                        model.pointId(),
                        model.numericValue(),
                        model.textValue(),
                        model.booleanValue(),
                        model.unitId(),
                        model.qualityCodeId(),
                        model.trustLevel(),
                        model.sourceTimestamp(),
                        model.trustedAt(),
                        model.qualityAssessmentId(),
                        model.topologyAssetTypeCode(),
                        model.topologyAssetId(),
                        model.topologyAssetCode(),
                        model.topologySnapshotId(),
                        model.ingestionBatchId()
            );
        }

        public static TrustedTelemetryReading toDomain(TrustedTelemetryReadingJpaEntity entity) {
            return new TrustedTelemetryReading(
                        entity.id(),
                        entity.readingId(),
                        entity.pointId(),
                        entity.numericValue(),
                        entity.textValue(),
                        entity.booleanValue(),
                        entity.unitId(),
                        entity.qualityCodeId(),
                        entity.trustLevel(),
                        entity.sourceTimestamp(),
                        entity.trustedAt(),
                        entity.qualityAssessmentId(),
                        entity.topologyAssetTypeCode(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCode(),
                        entity.topologySnapshotId(),
                        entity.ingestionBatchId()
            );
        }
}
