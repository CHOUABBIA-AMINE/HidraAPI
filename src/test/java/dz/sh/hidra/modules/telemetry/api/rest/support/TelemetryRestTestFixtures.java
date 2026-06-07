/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryRestTestFixtures
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : TestSupport
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.support
 *
 * @Description : Reusable telemetry REST mapper and controller test fixtures.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import dz.sh.hidra.modules.telemetry.api.rest.request.BindTelemetryPointRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.CloseTelemetryPointBindingRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.CompleteTelemetryIngestionBatchRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.FailTelemetryIngestionBatchRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.QuarantineTelemetryReadingRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.ReceiveTelemetryReadingRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.RegisterTelemetryDeviceRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.RegisterTelemetryPointRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.RegisterTelemetrySourceRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.RejectTelemetryReadingRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.StartTelemetryIngestionBatchRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.TelemetryLocalizedNameRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.TelemetryReadingValueRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.TelemetryTypeReferenceRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.TopologyAssetReferenceRequest;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryCatalogDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryCatalogTranslationDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryDeviceDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryIngestionBatchDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryLocalizedNameDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPageDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPointBindingDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPointDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryReadingDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryReadingValueDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetrySourceDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryTypeReferenceDto;
import dz.sh.hidra.modules.telemetry.application.dto.TopologyAssetReferenceDto;

/**
 * Reusable telemetry REST mapper and controller test fixtures.
 */
public final class TelemetryRestTestFixtures {

    public static final Instant CREATED_AT = Instant.parse("2026-01-01T00:00:00Z");
    public static final Instant UPDATED_AT = Instant.parse("2026-01-01T00:01:00Z");
    public static final Instant SOURCE_TIMESTAMP = Instant.parse("2026-01-01T00:00:30Z");
    public static final Instant RECEIVED_AT = Instant.parse("2026-01-01T00:01:00Z");

    private TelemetryRestTestFixtures() {
        // Test fixtures only.
    }

    public static TelemetryLocalizedNameRequest nameRequest() {
        return new TelemetryLocalizedNameRequest("اسم", "Nom français", "English name");
    }

    public static TelemetryTypeReferenceRequest sourceTypeRequest() {
        return new TelemetryTypeReferenceRequest("source-type-scada", "SCADA");
    }

    public static TelemetryTypeReferenceRequest protocolRequest() {
        return new TelemetryTypeReferenceRequest("protocol-opc-ua", "OPC_UA");
    }

    public static TelemetryTypeReferenceRequest deviceTypeRequest() {
        return new TelemetryTypeReferenceRequest("device-type-rtu", "RTU");
    }

    public static TelemetryTypeReferenceRequest pointTypeRequest() {
        return new TelemetryTypeReferenceRequest("point-type-pressure", "PRESSURE");
    }

    public static TelemetryTypeReferenceRequest signalTypeRequest() {
        return new TelemetryTypeReferenceRequest("signal-type-numeric", "NUMERIC");
    }

    public static TelemetryTypeReferenceRequest unitRequest() {
        return new TelemetryTypeReferenceRequest("unit-bar", "BAR");
    }

    public static TelemetryTypeReferenceRequest aggregationRequest() {
        return new TelemetryTypeReferenceRequest("aggregation-average", "AVERAGE");
    }

    public static TelemetryTypeReferenceRequest qualityRequest() {
        return new TelemetryTypeReferenceRequest("quality-good", "GOOD");
    }

    public static TelemetryTypeReferenceRequest bindingRoleRequest() {
        return new TelemetryTypeReferenceRequest("binding-role-primary", "PRIMARY_MEASUREMENT");
    }

    public static TopologyAssetReferenceRequest topologyAssetRequest() {
        return new TopologyAssetReferenceRequest("PIPELINE", "pipeline-001", "GPL-001", "Pipeline GPL 001");
    }

    public static TelemetryReadingValueRequest readingValueRequest() {
        return new TelemetryReadingValueRequest(BigDecimal.valueOf(42.25), null, null);
    }

    public static RegisterTelemetrySourceRequest registerSourceRequest() {
        return new RegisterTelemetrySourceRequest(
                "SCADA-TRC-01",
                nameRequest(),
                sourceTypeRequest(),
                protocolRequest(),
                "opc.tcp://source.local:4840",
                "EXT-SOURCE-001");
    }

    public static RegisterTelemetryDeviceRequest registerDeviceRequest() {
        return new RegisterTelemetryDeviceRequest(
                "source-001",
                "RTU-001",
                nameRequest(),
                deviceTypeRequest(),
                "EXT-DEVICE-001");
    }

    public static RegisterTelemetryPointRequest registerPointRequest() {
        return new RegisterTelemetryPointRequest(
                "device-001",
                "PT-001",
                nameRequest(),
                pointTypeRequest(),
                signalTypeRequest(),
                unitRequest(),
                aggregationRequest(),
                60,
                "SCADA.PT001.PV");
    }

    public static BindTelemetryPointRequest bindPointRequest() {
        return new BindTelemetryPointRequest(
                "point-001",
                topologyAssetRequest(),
                bindingRoleRequest(),
                CREATED_AT);
    }

    public static CloseTelemetryPointBindingRequest closeBindingRequest() {
        return new CloseTelemetryPointBindingRequest(UPDATED_AT);
    }

    public static ReceiveTelemetryReadingRequest receiveReadingRequest() {
        return new ReceiveTelemetryReadingRequest(
                "point-001",
                readingValueRequest(),
                qualityRequest(),
                SOURCE_TIMESTAMP,
                "batch-001",
                "corr-001");
    }

    public static RejectTelemetryReadingRequest rejectReadingRequest() {
        return new RejectTelemetryReadingRequest("Invalid quality flag");
    }

    public static QuarantineTelemetryReadingRequest quarantineReadingRequest() {
        return new QuarantineTelemetryReadingRequest("Timestamp out of window");
    }

    public static StartTelemetryIngestionBatchRequest startBatchRequest() {
        return new StartTelemetryIngestionBatchRequest("source-001", "corr-001");
    }

    public static CompleteTelemetryIngestionBatchRequest completeBatchRequest() {
        return new CompleteTelemetryIngestionBatchRequest(10, 8, 1, 1, 0);
    }

    public static FailTelemetryIngestionBatchRequest failBatchRequest() {
        return new FailTelemetryIngestionBatchRequest("Source endpoint timeout");
    }

    public static TelemetryLocalizedNameDto localizedNameDto() {
        return new TelemetryLocalizedNameDto("اسم", "Nom français", "English name");
    }

    public static TelemetryTypeReferenceDto typeDto(String id, String code, String label) {
        return new TelemetryTypeReferenceDto(id, code, label, "fr");
    }

    public static TopologyAssetReferenceDto topologyAssetDto() {
        return new TopologyAssetReferenceDto("PIPELINE", "pipeline-001", "GPL-001", "Pipeline GPL 001");
    }

    public static TelemetrySourceDto sourceDto() {
        return new TelemetrySourceDto(
                "source-001",
                "SCADA-TRC-01",
                localizedNameDto(),
                typeDto("source-type-scada", "SCADA", "SCADA"),
                typeDto("protocol-opc-ua", "OPC_UA", "OPC UA"),
                "opc.tcp://source.local:4840",
                "EXT-SOURCE-001",
                "ACTIVE",
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetryDeviceDto deviceDto() {
        return new TelemetryDeviceDto(
                "device-001",
                "source-001",
                "RTU-001",
                localizedNameDto(),
                typeDto("device-type-rtu", "RTU", "RTU"),
                "EXT-DEVICE-001",
                "ACTIVE",
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetryPointDto pointDto() {
        return new TelemetryPointDto(
                "point-001",
                "device-001",
                "PT-001",
                localizedNameDto(),
                typeDto("point-type-pressure", "PRESSURE", "Pression"),
                typeDto("signal-type-numeric", "NUMERIC", "Numérique"),
                typeDto("unit-bar", "BAR", "bar"),
                typeDto("aggregation-average", "AVERAGE", "Moyenne"),
                60,
                "SCADA.PT001.PV",
                "ACTIVE",
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetryPointBindingDto bindingDto() {
        return new TelemetryPointBindingDto(
                "binding-001",
                "point-001",
                topologyAssetDto(),
                typeDto("binding-role-primary", "PRIMARY_MEASUREMENT", "Mesure principale"),
                true,
                CREATED_AT,
                null,
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetryReadingDto readingDto() {
        return new TelemetryReadingDto(
                "reading-001",
                "point-001",
                new TelemetryReadingValueDto(BigDecimal.valueOf(42.25), null, null),
                typeDto("quality-good", "GOOD", "Bonne"),
                SOURCE_TIMESTAMP,
                RECEIVED_AT,
                "ACCEPTED",
                "batch-001",
                "corr-001",
                null);
    }

    public static TelemetryIngestionBatchDto batchDto() {
        return new TelemetryIngestionBatchDto(
                "batch-001",
                "source-001",
                "corr-001",
                "COMPLETED",
                10,
                8,
                1,
                1,
                0,
                CREATED_AT,
                UPDATED_AT,
                null);
    }

    public static TelemetryCatalogTranslationDto catalogTranslationDto(String locale, String name) {
        return new TelemetryCatalogTranslationDto(
                "catalog-pressure-" + locale,
                "catalog-point-type-pressure",
                locale,
                name,
                "Description " + name,
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetryCatalogDto catalogDto() {
        return new TelemetryCatalogDto(
                "catalog-point-type-pressure",
                "POINT_TYPE",
                "PRESSURE",
                true,
                10,
                true,
                "fr",
                "Pression",
                "Description Pression",
                List.of(
                        catalogTranslationDto("fr", "Pression"),
                        catalogTranslationDto("ar", "ضغط"),
                        catalogTranslationDto("en", "Pressure")),
                CREATED_AT,
                UPDATED_AT);
    }

    public static <T> TelemetryPageDto<T> page(T item) {
        return new TelemetryPageDto<>(List.of(item), 0, 20, 1L, 1);
    }

    @SuppressWarnings("unchecked")
    public static <T> T useCase(Class<T> useCaseType) {
        InvocationHandler handler = (proxy, method, args) -> {
            String methodName = method.getName();

            if ("toString".equals(methodName)) {
                return "TelemetryRestTestUseCaseProxy(" + useCaseType.getSimpleName() + ")";
            }
            if ("hashCode".equals(methodName)) {
                return System.identityHashCode(proxy);
            }
            if ("equals".equals(methodName)) {
                return proxy == args[0];
            }

            Class<?> returnType = method.getReturnType();

            if (TelemetryPageDto.class.equals(returnType)) {
                if (methodName.contains("Catalog")) {
                    return page(catalogDto());
                }
                if (methodName.contains("Sources")) {
                    return page(sourceDto());
                }
                if (methodName.contains("Devices")) {
                    return page(deviceDto());
                }
                if (methodName.contains("PointBindings")) {
                    return page(bindingDto());
                }
                if (methodName.contains("Points")) {
                    return page(pointDto());
                }
                if (methodName.contains("Readings")) {
                    return page(readingDto());
                }
                if (methodName.contains("Batches")) {
                    return page(batchDto());
                }
            }
            if (TelemetryCatalogDto.class.equals(returnType)) {
                return catalogDto();
            }
            if (TelemetrySourceDto.class.equals(returnType)) {
                return sourceDto();
            }
            if (TelemetryDeviceDto.class.equals(returnType)) {
                return deviceDto();
            }
            if (TelemetryPointDto.class.equals(returnType)) {
                return pointDto();
            }
            if (TelemetryPointBindingDto.class.equals(returnType)) {
                return bindingDto();
            }
            if (TelemetryReadingDto.class.equals(returnType)) {
                return readingDto();
            }
            if (TelemetryIngestionBatchDto.class.equals(returnType)) {
                return batchDto();
            }

            throw new UnsupportedOperationException("Unsupported telemetry use case method in REST test proxy: " + methodName);
        };

        return (T) Proxy.newProxyInstance(
                useCaseType.getClassLoader(),
                new Class<?>[] { useCaseType },
                handler);
    }
}
