/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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

        public static TelemetrySourceEndpointJpaEntity toEntity(TelemetrySourceEndpoint model) {
            return new TelemetrySourceEndpointJpaEntity(
                        model.id(),
                        model.sourceId(),
                        model.code(),
                        model.endpointRole(),
                        model.protocolId(),
                        model.endpointUri(),
                        model.host(),
                        model.port(),
                        model.pathOrTopic(),
                        model.pollingIntervalSeconds(),
                        model.timeoutSeconds(),
                        model.credentialReference(),
                        model.connectionOptionsJson(),
                        model.active(),
                        model.validFrom(),
                        model.validTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static TelemetrySourceEndpoint toDomain(TelemetrySourceEndpointJpaEntity entity) {
            return new TelemetrySourceEndpoint(
                        entity.id(),
                        entity.sourceId(),
                        entity.code(),
                        entity.endpointRole(),
                        entity.protocolId(),
                        entity.endpointUri(),
                        entity.host(),
                        entity.port(),
                        entity.pathOrTopic(),
                        entity.pollingIntervalSeconds(),
                        entity.timeoutSeconds(),
                        entity.credentialReference(),
                        entity.connectionOptionsJson(),
                        entity.active(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static TelemetryDeviceJpaEntity toEntity(TelemetryDevice model) {
            return new TelemetryDeviceJpaEntity(
                        model.id(),
                        model.sourceId(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.deviceTypeId(),
                        model.externalReference(),
                        model.manufacturerPartyId(),
                        model.modelReference(),
                        model.serialNumber(),
                        model.firmwareVersion(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static TelemetryDevice toDomain(TelemetryDeviceJpaEntity entity) {
            return new TelemetryDevice(
                        entity.id(),
                        entity.sourceId(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.deviceTypeId(),
                        entity.externalReference(),
                        entity.manufacturerPartyId(),
                        entity.modelReference(),
                        entity.serialNumber(),
                        entity.firmwareVersion(),
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

        public static TelemetryExternalTagMappingJpaEntity toEntity(TelemetryExternalTagMapping model) {
            return new TelemetryExternalTagMappingJpaEntity(
                        model.id(),
                        model.sourceId(),
                        model.deviceId(),
                        model.pointId(),
                        model.externalTagName(),
                        model.externalTagId(),
                        model.externalNamespace(),
                        model.externalDataType(),
                        model.mappingMode(),
                        model.transformationExpression(),
                        model.active(),
                        model.validFrom(),
                        model.validTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static TelemetryExternalTagMapping toDomain(TelemetryExternalTagMappingJpaEntity entity) {
            return new TelemetryExternalTagMapping(
                        entity.id(),
                        entity.sourceId(),
                        entity.deviceId(),
                        entity.pointId(),
                        entity.externalTagName(),
                        entity.externalTagId(),
                        entity.externalNamespace(),
                        entity.externalDataType(),
                        entity.mappingMode(),
                        entity.transformationExpression(),
                        entity.active(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static TelemetryPointBindingJpaEntity toEntity(TelemetryPointBinding model) {
            return new TelemetryPointBindingJpaEntity(
                        model.id(),
                        model.pointId(),
                        model.topologyAssetTypeCode(),
                        model.topologyAssetId(),
                        model.topologyAssetCode(),
                        model.topologyAssetNameSnapshot(),
                        model.topologySnapshotId(),
                        model.bindingRoleId(),
                        model.active(),
                        model.validFrom(),
                        model.validTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static TelemetryPointBinding toDomain(TelemetryPointBindingJpaEntity entity) {
            return new TelemetryPointBinding(
                        entity.id(),
                        entity.pointId(),
                        entity.topologyAssetTypeCode(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCode(),
                        entity.topologyAssetNameSnapshot(),
                        entity.topologySnapshotId(),
                        entity.bindingRoleId(),
                        entity.active(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static TelemetryIngestionBatchJpaEntity toEntity(TelemetryIngestionBatch model) {
            return new TelemetryIngestionBatchJpaEntity(
                        model.id(),
                        model.sourceId(),
                        model.endpointId(),
                        model.correlationId(),
                        model.status(),
                        model.receivedCount(),
                        model.acceptedCount(),
                        model.rejectedCount(),
                        model.duplicateCount(),
                        model.quarantinedCount(),
                        model.startedAt(),
                        model.completedAt(),
                        model.failureReason(),
                        model.createdByActorId()
            );
        }

        public static TelemetryIngestionBatch toDomain(TelemetryIngestionBatchJpaEntity entity) {
            return new TelemetryIngestionBatch(
                        entity.id(),
                        entity.sourceId(),
                        entity.endpointId(),
                        entity.correlationId(),
                        entity.status(),
                        entity.receivedCount(),
                        entity.acceptedCount(),
                        entity.rejectedCount(),
                        entity.duplicateCount(),
                        entity.quarantinedCount(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.failureReason(),
                        entity.createdByActorId()
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

        public static TelemetryQualityAssessmentJpaEntity toEntity(TelemetryQualityAssessment model) {
            return new TelemetryQualityAssessmentJpaEntity(
                        model.id(),
                        model.readingId(),
                        model.pointId(),
                        model.assessmentStatus(),
                        model.inputQualityCodeId(),
                        model.resolvedQualityCodeId(),
                        model.trustLevel(),
                        model.validationRuleId(),
                        model.reasonCode(),
                        model.reasonMessage(),
                        model.assessedAt(),
                        model.assessedByActorId(),
                        model.workflowInstanceId()
            );
        }

        public static TelemetryQualityAssessment toDomain(TelemetryQualityAssessmentJpaEntity entity) {
            return new TelemetryQualityAssessment(
                        entity.id(),
                        entity.readingId(),
                        entity.pointId(),
                        entity.assessmentStatus(),
                        entity.inputQualityCodeId(),
                        entity.resolvedQualityCodeId(),
                        entity.trustLevel(),
                        entity.validationRuleId(),
                        entity.reasonCode(),
                        entity.reasonMessage(),
                        entity.assessedAt(),
                        entity.assessedByActorId(),
                        entity.workflowInstanceId()
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

        public static TelemetryQuarantineRecordJpaEntity toEntity(TelemetryQuarantineRecord model) {
            return new TelemetryQuarantineRecordJpaEntity(
                        model.id(),
                        model.sourceId(),
                        model.endpointId(),
                        model.ingestionBatchId(),
                        model.externalTagName(),
                        model.sourceTimestamp(),
                        model.receivedAt(),
                        model.reasonCode(),
                        model.reasonMessage(),
                        model.rawPayload(),
                        model.rawPayloadHash(),
                        model.status(),
                        model.resolvedReadingId(),
                        model.resolvedAt(),
                        model.resolvedByActorId()
            );
        }

        public static TelemetryQuarantineRecord toDomain(TelemetryQuarantineRecordJpaEntity entity) {
            return new TelemetryQuarantineRecord(
                        entity.id(),
                        entity.sourceId(),
                        entity.endpointId(),
                        entity.ingestionBatchId(),
                        entity.externalTagName(),
                        entity.sourceTimestamp(),
                        entity.receivedAt(),
                        entity.reasonCode(),
                        entity.reasonMessage(),
                        entity.rawPayload(),
                        entity.rawPayloadHash(),
                        entity.status(),
                        entity.resolvedReadingId(),
                        entity.resolvedAt(),
                        entity.resolvedByActorId()
            );
        }

        public static TelemetryCatalogEntryJpaEntity toEntity(TelemetryCatalogEntry model) {
            return new TelemetryCatalogEntryJpaEntity(
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

        public static TelemetryCatalogEntry toDomain(TelemetryCatalogEntryJpaEntity entity) {
            return new TelemetryCatalogEntry(
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

        public static TelemetryCatalogTranslationJpaEntity toEntity(TelemetryCatalogTranslation model) {
            return new TelemetryCatalogTranslationJpaEntity(
                        model.id(),
                        model.typeId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static TelemetryCatalogTranslation toDomain(TelemetryCatalogTranslationJpaEntity entity) {
            return new TelemetryCatalogTranslation(
                        entity.id(),
                        entity.typeId(),
                        entity.locale(),
                        entity.name(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static TelemetryUnitJpaEntity toEntity(TelemetryUnit model) {
            return new TelemetryUnitJpaEntity(
                        model.id(),
                        model.code(),
                        model.symbol(),
                        model.dimension(),
                        model.baseUnitId(),
                        model.toBaseFactor(),
                        model.toBaseOffset(),
                        model.displayPrecision(),
                        model.active(),
                        model.systemDefined(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static TelemetryUnit toDomain(TelemetryUnitJpaEntity entity) {
            return new TelemetryUnit(
                        entity.id(),
                        entity.code(),
                        entity.symbol(),
                        entity.dimension(),
                        entity.baseUnitId(),
                        entity.toBaseFactor(),
                        entity.toBaseOffset(),
                        entity.displayPrecision(),
                        entity.active(),
                        entity.systemDefined(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static TelemetryValidationRuleJpaEntity toEntity(TelemetryValidationRule model) {
            return new TelemetryValidationRuleJpaEntity(
                        model.id(),
                        model.code(),
                        model.name(),
                        model.scopeType(),
                        model.scopeReferenceId(),
                        model.ruleTypeId(),
                        model.severity(),
                        model.actionOnFailure(),
                        model.expression(),
                        model.configurationJson(),
                        model.active(),
                        model.validFrom(),
                        model.validTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static TelemetryValidationRule toDomain(TelemetryValidationRuleJpaEntity entity) {
            return new TelemetryValidationRule(
                        entity.id(),
                        entity.code(),
                        entity.name(),
                        entity.scopeType(),
                        entity.scopeReferenceId(),
                        entity.ruleTypeId(),
                        entity.severity(),
                        entity.actionOnFailure(),
                        entity.expression(),
                        entity.configurationJson(),
                        entity.active(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static TelemetryPointStateSnapshotJpaEntity toEntity(TelemetryPointStateSnapshot model) {
            return new TelemetryPointStateSnapshotJpaEntity(
                        model.pointId(),
                        model.lastReadingId(),
                        model.lastTrustedReadingId(),
                        model.lastNumericValue(),
                        model.lastTextValue(),
                        model.lastBooleanValue(),
                        model.lastQualityCodeId(),
                        model.lastSourceTimestamp(),
                        model.lastReceivedAt(),
                        model.communicationState(),
                        model.staleSince(),
                        model.updatedAt()
            );
        }

        public static TelemetryPointStateSnapshot toDomain(TelemetryPointStateSnapshotJpaEntity entity) {
            return new TelemetryPointStateSnapshot(
                        entity.pointId(),
                        entity.lastReadingId(),
                        entity.lastTrustedReadingId(),
                        entity.lastNumericValue(),
                        entity.lastTextValue(),
                        entity.lastBooleanValue(),
                        entity.lastQualityCodeId(),
                        entity.lastSourceTimestamp(),
                        entity.lastReceivedAt(),
                        entity.communicationState(),
                        entity.staleSince(),
                        entity.updatedAt()
            );
        }

}
