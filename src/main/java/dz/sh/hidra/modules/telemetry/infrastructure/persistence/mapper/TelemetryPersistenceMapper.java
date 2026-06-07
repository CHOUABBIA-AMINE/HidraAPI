/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Mapper
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper
 *
 * @Description : Mapper between telemetry domain models and telemetry JPA entities.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper;

import java.util.List;
import java.util.Objects;

import dz.sh.hidra.modules.telemetry.domain.model.TelemetryDevice;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryIngestionBatch;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPointBinding;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryTypeCatalog;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryTypeTranslation;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryAggregationMethodReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryBindingRoleReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCorrelationId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryEndpointUri;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryExternalReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryLocalizedName;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointBindingId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryProtocolReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryQualityCodeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingState;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingValue;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySamplingPeriodSeconds;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySignalTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTimestamp;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTypeCatalogId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTypeTranslationId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryUnitReference;
import dz.sh.hidra.modules.telemetry.domain.value.TopologyAssetReference;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryDeviceJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryIngestionBatchJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryPointBindingJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryPointJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryReadingJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetrySourceJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryTypeCatalogJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryTypeTranslationJpaEntity;

/**
 * Mapper between telemetry domain models and telemetry JPA entities.
 *
 * <p>Architecture role:
 * Infrastructure-only mapper. It must not be used by domain, application, REST, topology, flow,
 * risk, analytics, workflow, reporting, or notification code.
 *
 * <p>Catalog note:
 * Persistence tables store catalog foreign key ids. The mapper restores catalog references with the
 * catalog id as the stable id and as fallback code. Later API/catalog enrichment can resolve
 * localized labels from the catalog repository.
 */
public final class TelemetryPersistenceMapper {

    public TelemetryTypeCatalog toDomain(
            TelemetryTypeCatalogJpaEntity entity,
            List<TelemetryTypeTranslationJpaEntity> translationEntities) {

        Objects.requireNonNull(entity, "Telemetry type catalog entity must not be null.");

        List<TelemetryTypeTranslation> translations = translationEntities == null
                ? List.of()
                : translationEntities.stream().map(this::toDomain).toList();

        return TelemetryTypeCatalog.restore(
                TelemetryTypeCatalogId.of(entity.getId()),
                entity.getCatalogName(),
                TelemetryCode.of(entity.getCode()),
                entity.getActive(),
                entity.getSortOrder(),
                entity.getSystemDefined(),
                translations,
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public TelemetryTypeTranslation toDomain(TelemetryTypeTranslationJpaEntity entity) {
        Objects.requireNonNull(entity, "Telemetry type translation entity must not be null.");

        return TelemetryTypeTranslation.restore(
                TelemetryTypeTranslationId.of(entity.getId()),
                TelemetryTypeCatalogId.of(entity.getTypeId()),
                entity.getLocale(),
                dz.sh.hidra.modules.telemetry.domain.value.TelemetryName.of(entity.getName()),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public TelemetryTypeCatalogJpaEntity toEntity(TelemetryTypeCatalog catalog) {
        Objects.requireNonNull(catalog, "Telemetry type catalog must not be null.");

        return new TelemetryTypeCatalogJpaEntity(
                catalog.id().value(),
                catalog.catalogName(),
                catalog.code().value(),
                catalog.active(),
                catalog.sortOrder(),
                catalog.systemDefined(),
                catalog.createdAt(),
                catalog.updatedAt());
    }

    public TelemetryTypeTranslationJpaEntity toEntity(TelemetryTypeTranslation translation) {
        Objects.requireNonNull(translation, "Telemetry type translation must not be null.");

        return new TelemetryTypeTranslationJpaEntity(
                translation.id().value(),
                translation.typeId().value(),
                translation.locale(),
                translation.name().value(),
                translation.description(),
                translation.createdAt(),
                translation.updatedAt());
    }

    public TelemetrySource toDomain(TelemetrySourceJpaEntity entity) {
        Objects.requireNonNull(entity, "Telemetry source entity must not be null.");

        return TelemetrySource.restore(
                TelemetrySourceId.of(entity.getId()),
                TelemetryCode.of(entity.getCode()),
                TelemetryLocalizedName.of(entity.getNameAr(), entity.getNameFr(), entity.getNameEn()),
                TelemetrySourceTypeReference.of(entity.getSourceTypeId(), entity.getSourceTypeId()),
                TelemetryProtocolReference.of(entity.getProtocolId(), entity.getProtocolId()),
                optionalEndpoint(entity.getEndpointUri()),
                optionalExternalReference(entity.getExternalReference()),
                TelemetrySourceStatus.valueOf(entity.getStatus()),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public TelemetrySourceJpaEntity toEntity(TelemetrySource source) {
        Objects.requireNonNull(source, "Telemetry source must not be null.");

        return new TelemetrySourceJpaEntity(
                source.id().value(),
                source.code().value(),
                source.name().nameAr(),
                source.name().nameFr(),
                source.name().nameEn(),
                source.sourceType().id(),
                source.protocol().id(),
                source.endpointUri() == null ? null : source.endpointUri().value(),
                source.externalReference() == null ? null : source.externalReference().value(),
                source.status().name(),
                source.createdAt(),
                source.updatedAt());
    }

    public TelemetryDevice toDomain(TelemetryDeviceJpaEntity entity) {
        Objects.requireNonNull(entity, "Telemetry device entity must not be null.");

        return TelemetryDevice.restore(
                TelemetryDeviceId.of(entity.getId()),
                TelemetrySourceId.of(entity.getSourceId()),
                TelemetryCode.of(entity.getCode()),
                TelemetryLocalizedName.of(entity.getNameAr(), entity.getNameFr(), entity.getNameEn()),
                TelemetryDeviceTypeReference.of(entity.getDeviceTypeId(), entity.getDeviceTypeId()),
                optionalExternalReference(entity.getExternalReference()),
                TelemetryDeviceStatus.valueOf(entity.getStatus()),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public TelemetryDeviceJpaEntity toEntity(TelemetryDevice device) {
        Objects.requireNonNull(device, "Telemetry device must not be null.");

        return new TelemetryDeviceJpaEntity(
                device.id().value(),
                device.sourceId().value(),
                device.code().value(),
                device.name().nameAr(),
                device.name().nameFr(),
                device.name().nameEn(),
                device.deviceType().id(),
                device.externalReference() == null ? null : device.externalReference().value(),
                device.status().name(),
                device.createdAt(),
                device.updatedAt());
    }

    public TelemetryPoint toDomain(TelemetryPointJpaEntity entity) {
        Objects.requireNonNull(entity, "Telemetry point entity must not be null.");

        return TelemetryPoint.restore(
                TelemetryPointId.of(entity.getId()),
                TelemetryDeviceId.of(entity.getDeviceId()),
                TelemetryCode.of(entity.getCode()),
                TelemetryLocalizedName.of(entity.getNameAr(), entity.getNameFr(), entity.getNameEn()),
                TelemetryPointTypeReference.of(entity.getPointTypeId(), entity.getPointTypeId()),
                TelemetrySignalTypeReference.of(entity.getSignalTypeId(), entity.getSignalTypeId()),
                entity.getUnitId() == null ? null : TelemetryUnitReference.of(entity.getUnitId(), entity.getUnitId()),
                entity.getDefaultAggregationMethodId() == null ? null : TelemetryAggregationMethodReference.of(
                        entity.getDefaultAggregationMethodId(),
                        entity.getDefaultAggregationMethodId()),
                entity.getSamplingPeriodSeconds() == null ? null : TelemetrySamplingPeriodSeconds.of(entity.getSamplingPeriodSeconds()),
                optionalExternalReference(entity.getExternalReference()),
                TelemetryPointStatus.valueOf(entity.getStatus()),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public TelemetryPointJpaEntity toEntity(TelemetryPoint point) {
        Objects.requireNonNull(point, "Telemetry point must not be null.");

        return new TelemetryPointJpaEntity(
                point.id().value(),
                point.deviceId().value(),
                point.code().value(),
                point.name().nameAr(),
                point.name().nameFr(),
                point.name().nameEn(),
                point.pointType().id(),
                point.signalType().id(),
                point.unit() == null ? null : point.unit().id(),
                point.defaultAggregationMethod() == null ? null : point.defaultAggregationMethod().id(),
                point.samplingPeriod() == null ? null : point.samplingPeriod().value(),
                point.externalReference() == null ? null : point.externalReference().value(),
                point.status().name(),
                point.createdAt(),
                point.updatedAt());
    }

    public TelemetryPointBinding toDomain(TelemetryPointBindingJpaEntity entity) {
        Objects.requireNonNull(entity, "Telemetry point binding entity must not be null.");

        return TelemetryPointBinding.restore(
                TelemetryPointBindingId.of(entity.getId()),
                TelemetryPointId.of(entity.getPointId()),
                TopologyAssetReference.of(
                        entity.getTopologyAssetTypeCode(),
                        entity.getTopologyAssetId(),
                        entity.getTopologyAssetCode(),
                        entity.getTopologyAssetNameSnapshot()),
                TelemetryBindingRoleReference.of(entity.getBindingRoleId(), entity.getBindingRoleId()),
                entity.getActive(),
                entity.getValidFrom(),
                entity.getValidTo(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public TelemetryPointBindingJpaEntity toEntity(TelemetryPointBinding binding) {
        Objects.requireNonNull(binding, "Telemetry point binding must not be null.");

        return new TelemetryPointBindingJpaEntity(
                binding.id().value(),
                binding.pointId().value(),
                binding.topologyAssetReference().assetTypeCode().value(),
                binding.topologyAssetReference().assetId(),
                binding.topologyAssetReference().assetCode().value(),
                binding.topologyAssetReference().assetNameSnapshot(),
                binding.bindingRole().id(),
                binding.active(),
                binding.validFrom(),
                binding.validTo(),
                binding.createdAt(),
                binding.updatedAt());
    }

    public TelemetryReading toDomain(TelemetryReadingJpaEntity entity) {
        Objects.requireNonNull(entity, "Telemetry reading entity must not be null.");

        return TelemetryReading.restore(
                TelemetryReadingId.of(entity.getId()),
                TelemetryPointId.of(entity.getPointId()),
                new TelemetryReadingValue(entity.getNumericValue(), entity.getTextValue(), entity.getBooleanValue()),
                TelemetryQualityCodeReference.of(entity.getQualityCodeId(), entity.getQualityCodeId()),
                TelemetryTimestamp.of(entity.getSourceTimestamp()),
                TelemetryTimestamp.of(entity.getReceivedAt()),
                TelemetryReadingState.valueOf(entity.getState()),
                entity.getIngestionBatchId() == null ? null : TelemetryIngestionBatchId.of(entity.getIngestionBatchId()),
                entity.getCorrelationId() == null ? null : TelemetryCorrelationId.of(entity.getCorrelationId()),
                entity.getRejectionReason());
    }

    public TelemetryReadingJpaEntity toEntity(TelemetryReading reading) {
        Objects.requireNonNull(reading, "Telemetry reading must not be null.");

        return new TelemetryReadingJpaEntity(
                reading.id().value(),
                reading.pointId().value(),
                reading.value().numericValue(),
                reading.value().textValue(),
                reading.value().booleanValue(),
                reading.qualityCode().id(),
                reading.sourceTimestamp().value(),
                reading.receivedAt().value(),
                reading.state().name(),
                reading.ingestionBatchId() == null ? null : reading.ingestionBatchId().value(),
                reading.correlationId() == null ? null : reading.correlationId().value(),
                reading.rejectionReason());
    }

    public TelemetryIngestionBatch toDomain(TelemetryIngestionBatchJpaEntity entity) {
        Objects.requireNonNull(entity, "Telemetry ingestion batch entity must not be null.");

        return TelemetryIngestionBatch.restore(
                TelemetryIngestionBatchId.of(entity.getId()),
                TelemetrySourceId.of(entity.getSourceId()),
                entity.getCorrelationId() == null ? null : TelemetryCorrelationId.of(entity.getCorrelationId()),
                TelemetryIngestionBatchStatus.valueOf(entity.getStatus()),
                entity.getReceivedCount(),
                entity.getAcceptedCount(),
                entity.getRejectedCount(),
                entity.getDuplicateCount(),
                entity.getQuarantinedCount(),
                entity.getStartedAt(),
                entity.getCompletedAt(),
                entity.getFailureReason());
    }

    public TelemetryIngestionBatchJpaEntity toEntity(TelemetryIngestionBatch batch) {
        Objects.requireNonNull(batch, "Telemetry ingestion batch must not be null.");

        return new TelemetryIngestionBatchJpaEntity(
                batch.id().value(),
                batch.sourceId().value(),
                batch.correlationId() == null ? null : batch.correlationId().value(),
                batch.status().name(),
                batch.receivedCount(),
                batch.acceptedCount(),
                batch.rejectedCount(),
                batch.duplicateCount(),
                batch.quarantinedCount(),
                batch.startedAt(),
                batch.completedAt(),
                batch.failureReason());
    }

    private static TelemetryEndpointUri optionalEndpoint(String value) {
        return value == null || value.isBlank() ? null : TelemetryEndpointUri.of(value);
    }

    private static TelemetryExternalReference optionalExternalReference(String value) {
        return value == null || value.isBlank() ? null : TelemetryExternalReference.of(value);
    }
}
