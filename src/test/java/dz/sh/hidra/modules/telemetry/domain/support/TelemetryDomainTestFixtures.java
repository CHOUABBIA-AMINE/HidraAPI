/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryDomainTestFixtures
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : TestSupport
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.support
 *
 * @Description : Reusable telemetry domain test fixtures.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.support;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

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
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryName;
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

/**
 * Reusable telemetry domain test fixtures.
 */
public final class TelemetryDomainTestFixtures {

    public static final Instant CREATED_AT = Instant.parse("2026-01-01T00:00:00Z");
    public static final Instant UPDATED_AT = Instant.parse("2026-01-01T00:01:00Z");
    public static final Instant SOURCE_TIMESTAMP = Instant.parse("2026-01-01T00:00:30Z");
    public static final Instant RECEIVED_AT = Instant.parse("2026-01-01T00:01:00Z");

    private TelemetryDomainTestFixtures() {
        // Test fixtures only.
    }

    public static TelemetryCode code(String value) {
        return TelemetryCode.of(value);
    }

    public static TelemetryLocalizedName localizedName() {
        return TelemetryLocalizedName.of("اسم", "Nom français", "English name");
    }

    public static TelemetrySourceTypeReference sourceType() {
        return TelemetrySourceTypeReference.of("source-type-scada", "SCADA");
    }

    public static TelemetryProtocolReference protocol() {
        return TelemetryProtocolReference.of("protocol-opc-ua", "OPC_UA");
    }

    public static TelemetryDeviceTypeReference deviceType() {
        return TelemetryDeviceTypeReference.of("device-type-rtu", "RTU");
    }

    public static TelemetryPointTypeReference pointType() {
        return TelemetryPointTypeReference.of("point-type-pressure", "PRESSURE");
    }

    public static TelemetrySignalTypeReference signalType(String code) {
        String safeCode = code.trim().toLowerCase(Locale.ROOT).replace('_', '-');
        return TelemetrySignalTypeReference.of("signal-type-" + safeCode, code);
    }

    public static TelemetryUnitReference unit() {
        return TelemetryUnitReference.of("unit-bar", "BAR");
    }

    public static TelemetryAggregationMethodReference aggregation() {
        return TelemetryAggregationMethodReference.of("aggregation-average", "AVERAGE");
    }

    public static TelemetryQualityCodeReference quality() {
        return TelemetryQualityCodeReference.of("quality-good", "GOOD");
    }

    public static TelemetryBindingRoleReference bindingRole() {
        return TelemetryBindingRoleReference.of("binding-role-primary", "PRIMARY_MEASUREMENT");
    }

    public static TelemetrySource source(TelemetrySourceStatus status) {
        return TelemetrySource.restore(
                TelemetrySourceId.of("source-001"),
                code("SCADA-TRC-01"),
                localizedName(),
                sourceType(),
                protocol(),
                TelemetryEndpointUri.of("opc.tcp://source.local:4840"),
                TelemetryExternalReference.of("EXT-SOURCE-001"),
                status,
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetrySource activeSource() {
        return source(TelemetrySourceStatus.ACTIVE);
    }

    public static TelemetryDevice device(TelemetrySourceId sourceId, TelemetryDeviceStatus status) {
        return TelemetryDevice.restore(
                TelemetryDeviceId.of("device-001"),
                sourceId,
                code("RTU-001"),
                localizedName(),
                deviceType(),
                TelemetryExternalReference.of("EXT-DEVICE-001"),
                status,
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetryDevice activeDevice() {
        return device(TelemetrySourceId.of("source-001"), TelemetryDeviceStatus.ACTIVE);
    }

    public static TelemetryPoint point(
            TelemetryDeviceId deviceId,
            TelemetryPointStatus status,
            String signalTypeCode,
            TelemetryUnitReference unit) {

        return TelemetryPoint.restore(
                TelemetryPointId.of("point-001"),
                deviceId,
                code("PT-001"),
                localizedName(),
                pointType(),
                signalType(signalTypeCode),
                unit,
                aggregation(),
                TelemetrySamplingPeriodSeconds.of(60),
                TelemetryExternalReference.of("SCADA.PT001.PV"),
                status,
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetryPoint activeNumericPoint() {
        return point(TelemetryDeviceId.of("device-001"), TelemetryPointStatus.ACTIVE, "NUMERIC", unit());
    }

    public static TelemetryPoint activeNumericPointWithoutUnit() {
        return point(TelemetryDeviceId.of("device-001"), TelemetryPointStatus.ACTIVE, "NUMERIC", null);
    }

    public static TelemetryPoint activeTextPoint() {
        return point(TelemetryDeviceId.of("device-001"), TelemetryPointStatus.ACTIVE, "TEXT", null);
    }

    public static TopologyAssetReference topologyAssetReference() {
        return TopologyAssetReference.of("PIPELINE", "pipeline-001", "GPL-001", "Pipeline GPL 001");
    }

    public static TelemetryPointBinding activeBinding(TelemetryPointId pointId) {
        return TelemetryPointBinding.restore(
                TelemetryPointBindingId.of("binding-001"),
                pointId,
                topologyAssetReference(),
                bindingRole(),
                true,
                CREATED_AT,
                null,
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetryReading reading(TelemetryPointId pointId, TelemetryReadingState state) {
        return TelemetryReading.restore(
                TelemetryReadingId.of("reading-001"),
                pointId,
                TelemetryReadingValue.numeric(BigDecimal.valueOf(42.25)),
                quality(),
                TelemetryTimestamp.of(SOURCE_TIMESTAMP),
                TelemetryTimestamp.of(RECEIVED_AT),
                state,
                TelemetryIngestionBatchId.of("batch-001"),
                TelemetryCorrelationId.of("corr-001"),
                state == TelemetryReadingState.REJECTED ? "Invalid quality" : null);
    }

    public static TelemetryReading receivedReading(TelemetryPointId pointId) {
        return reading(pointId, TelemetryReadingState.RECEIVED);
    }

    public static TelemetryIngestionBatch batch(TelemetryIngestionBatchStatus status) {
        return TelemetryIngestionBatch.restore(
                TelemetryIngestionBatchId.of("batch-001"),
                TelemetrySourceId.of("source-001"),
                TelemetryCorrelationId.of("corr-001"),
                status,
                10,
                8,
                1,
                1,
                0,
                CREATED_AT,
                status == TelemetryIngestionBatchStatus.RECEIVED || status == TelemetryIngestionBatchStatus.PROCESSING ? null : UPDATED_AT,
                status == TelemetryIngestionBatchStatus.FAILED ? "Source timeout" : null);
    }

    public static TelemetryTypeTranslation translation(TelemetryTypeCatalogId typeId, String locale, String name) {
        return TelemetryTypeTranslation.restore(
                TelemetryTypeTranslationId.of("translation-" + locale.toLowerCase(Locale.ROOT).replace('_', '-')),
                typeId,
                locale,
                TelemetryName.of(name),
                "Description " + name,
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetryTypeCatalog catalogWithMandatoryTranslations(boolean active) {
        TelemetryTypeCatalogId id = TelemetryTypeCatalogId.of("catalog-point-type-pressure");

        return TelemetryTypeCatalog.restore(
                id,
                "POINT_TYPE",
                code("PRESSURE"),
                active,
                10,
                true,
                List.of(
                        translation(id, "fr", "Pression"),
                        translation(id, "ar", "ضغط"),
                        translation(id, "en", "Pressure")),
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetryTypeCatalog catalogWithOnlyFrenchTranslation(boolean active) {
        TelemetryTypeCatalogId id = TelemetryTypeCatalogId.of("catalog-point-type-pressure");

        return TelemetryTypeCatalog.restore(
                id,
                "POINT_TYPE",
                code("PRESSURE"),
                active,
                10,
                true,
                List.of(translation(id, "fr", "Pression")),
                CREATED_AT,
                UPDATED_AT);
    }
}
