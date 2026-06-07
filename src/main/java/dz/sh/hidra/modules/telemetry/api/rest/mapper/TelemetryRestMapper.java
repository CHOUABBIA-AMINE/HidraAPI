/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Mapper
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.mapper
 *
 * @Description : REST mapper for telemetry request, query, and response objects.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.mapper;

import java.time.Instant;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.SortDirection;
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
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryCatalogResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryCatalogTranslationResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryDeviceResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryIngestionBatchResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryLocalizedNameResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPageResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPointBindingResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPointResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryReadingResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryReadingValueResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetrySourceResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryTypeReferenceResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TopologyAssetReferenceResponse;
import dz.sh.hidra.modules.telemetry.application.command.AcceptTelemetryReadingCommand;
import dz.sh.hidra.modules.telemetry.application.command.ActivateTelemetryDeviceCommand;
import dz.sh.hidra.modules.telemetry.application.command.ActivateTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.command.ActivateTelemetrySourceCommand;
import dz.sh.hidra.modules.telemetry.application.command.BindTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.command.CloseTelemetryPointBindingCommand;
import dz.sh.hidra.modules.telemetry.application.command.CompleteTelemetryIngestionBatchCommand;
import dz.sh.hidra.modules.telemetry.application.command.DeactivateTelemetryDeviceCommand;
import dz.sh.hidra.modules.telemetry.application.command.DeactivateTelemetrySourceCommand;
import dz.sh.hidra.modules.telemetry.application.command.FailTelemetryIngestionBatchCommand;
import dz.sh.hidra.modules.telemetry.application.command.MarkTelemetryIngestionBatchProcessingCommand;
import dz.sh.hidra.modules.telemetry.application.command.MarkTelemetryReadingDuplicateCommand;
import dz.sh.hidra.modules.telemetry.application.command.QuarantineTelemetryReadingCommand;
import dz.sh.hidra.modules.telemetry.application.command.ReceiveTelemetryReadingCommand;
import dz.sh.hidra.modules.telemetry.application.command.RegisterTelemetryDeviceCommand;
import dz.sh.hidra.modules.telemetry.application.command.RegisterTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.command.RegisterTelemetrySourceCommand;
import dz.sh.hidra.modules.telemetry.application.command.RejectTelemetryReadingCommand;
import dz.sh.hidra.modules.telemetry.application.command.RetireTelemetryDeviceCommand;
import dz.sh.hidra.modules.telemetry.application.command.RetireTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.command.RetireTelemetrySourceCommand;
import dz.sh.hidra.modules.telemetry.application.command.StartTelemetryIngestionBatchCommand;
import dz.sh.hidra.modules.telemetry.application.command.SuspendTelemetryPointCommand;
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
import dz.sh.hidra.modules.telemetry.application.query.GetLatestTelemetryReadingQuery;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryCatalogTypeQuery;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryDeviceByIdQuery;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryIngestionBatchByIdQuery;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryPointBindingByIdQuery;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryPointByIdQuery;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryReadingByIdQuery;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetrySourceByIdQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryCatalogTypesQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryDevicesQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryIngestionBatchesQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryPointBindingsQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryPointsQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryReadingsQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetrySourcesQuery;
import dz.sh.hidra.modules.telemetry.application.query.ResolveTelemetryCatalogTypeQuery;
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
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryUnitReference;
import dz.sh.hidra.modules.telemetry.domain.value.TopologyAssetReference;

/**
 * REST mapper for telemetry request, query, and response objects.
 *
 * <p>Business role:
 * Converts REST contracts into application command/query objects and converts application DTOs into
 * REST response DTOs.
 *
 * <p>Architecture role:
 * API-layer mapper only. It must not call application services, repositories, persistence mappers,
 * topology implementation classes, flow, risk, analytics, workflow, reporting, or notification code.
 */
@Component
public final class TelemetryRestMapper {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;

    public RegisterTelemetrySourceCommand toCommand(RegisterTelemetrySourceRequest request) {
        Objects.requireNonNull(request, "RegisterTelemetrySourceRequest must not be null.");

        return new RegisterTelemetrySourceCommand(
                code(request.code()),
                localizedName(request.name()),
                sourceType(request.sourceType()),
                protocol(request.protocol()),
                endpoint(request.endpointUri()),
                externalReference(request.externalReference()));
    }

    public RegisterTelemetryDeviceCommand toCommand(RegisterTelemetryDeviceRequest request) {
        Objects.requireNonNull(request, "RegisterTelemetryDeviceRequest must not be null.");

        return new RegisterTelemetryDeviceCommand(
                TelemetrySourceId.of(request.sourceId()),
                code(request.code()),
                localizedName(request.name()),
                deviceType(request.deviceType()),
                externalReference(request.externalReference()));
    }

    public RegisterTelemetryPointCommand toCommand(RegisterTelemetryPointRequest request) {
        Objects.requireNonNull(request, "RegisterTelemetryPointRequest must not be null.");

        return new RegisterTelemetryPointCommand(
                TelemetryDeviceId.of(request.deviceId()),
                code(request.code()),
                localizedName(request.name()),
                pointType(request.pointType()),
                signalType(request.signalType()),
                unit(request.unit()),
                aggregation(request.defaultAggregationMethod()),
                request.samplingPeriodSeconds() == null ? null : TelemetrySamplingPeriodSeconds.of(request.samplingPeriodSeconds()),
                externalReference(request.externalReference()));
    }

    public BindTelemetryPointCommand toCommand(BindTelemetryPointRequest request) {
        Objects.requireNonNull(request, "BindTelemetryPointRequest must not be null.");

        return new BindTelemetryPointCommand(
                TelemetryPointId.of(request.pointId()),
                topologyAssetReference(request.topologyAssetReference()),
                bindingRole(request.bindingRole()),
                request.validFrom());
    }

    public CloseTelemetryPointBindingCommand toCommand(String bindingId, CloseTelemetryPointBindingRequest request) {
        return new CloseTelemetryPointBindingCommand(
                TelemetryPointBindingId.of(bindingId),
                request == null ? null : request.closedAt());
    }

    public ReceiveTelemetryReadingCommand toCommand(ReceiveTelemetryReadingRequest request) {
        Objects.requireNonNull(request, "ReceiveTelemetryReadingRequest must not be null.");

        return new ReceiveTelemetryReadingCommand(
                TelemetryPointId.of(request.pointId()),
                readingValue(request.value()),
                qualityCode(request.qualityCode()),
                TelemetryTimestamp.of(request.sourceTimestamp()),
                optionalIngestionBatchId(request.ingestionBatchId()),
                optionalCorrelationId(request.correlationId()));
    }

    public RejectTelemetryReadingCommand toCommand(String readingId, RejectTelemetryReadingRequest request) {
        Objects.requireNonNull(request, "RejectTelemetryReadingRequest must not be null.");
        return new RejectTelemetryReadingCommand(TelemetryReadingId.of(readingId), request.reason());
    }

    public QuarantineTelemetryReadingCommand toCommand(String readingId, QuarantineTelemetryReadingRequest request) {
        Objects.requireNonNull(request, "QuarantineTelemetryReadingRequest must not be null.");
        return new QuarantineTelemetryReadingCommand(TelemetryReadingId.of(readingId), request.reason());
    }

    public StartTelemetryIngestionBatchCommand toCommand(StartTelemetryIngestionBatchRequest request) {
        Objects.requireNonNull(request, "StartTelemetryIngestionBatchRequest must not be null.");
        return new StartTelemetryIngestionBatchCommand(
                TelemetrySourceId.of(request.sourceId()),
                optionalCorrelationId(request.correlationId()));
    }

    public CompleteTelemetryIngestionBatchCommand toCommand(String batchId, CompleteTelemetryIngestionBatchRequest request) {
        Objects.requireNonNull(request, "CompleteTelemetryIngestionBatchRequest must not be null.");

        return new CompleteTelemetryIngestionBatchCommand(
                TelemetryIngestionBatchId.of(batchId),
                request.receivedCount(),
                request.acceptedCount(),
                request.rejectedCount(),
                request.duplicateCount(),
                request.quarantinedCount());
    }

    public FailTelemetryIngestionBatchCommand toCommand(String batchId, FailTelemetryIngestionBatchRequest request) {
        Objects.requireNonNull(request, "FailTelemetryIngestionBatchRequest must not be null.");
        return new FailTelemetryIngestionBatchCommand(TelemetryIngestionBatchId.of(batchId), request.reason());
    }

    public ActivateTelemetrySourceCommand toActivateTelemetrySourceCommand(String sourceId) {
        return new ActivateTelemetrySourceCommand(TelemetrySourceId.of(sourceId));
    }

    public DeactivateTelemetrySourceCommand toDeactivateTelemetrySourceCommand(String sourceId) {
        return new DeactivateTelemetrySourceCommand(TelemetrySourceId.of(sourceId));
    }

    public RetireTelemetrySourceCommand toRetireTelemetrySourceCommand(String sourceId) {
        return new RetireTelemetrySourceCommand(TelemetrySourceId.of(sourceId));
    }

    public ActivateTelemetryDeviceCommand toActivateTelemetryDeviceCommand(String sourceId, String deviceId) {
        return new ActivateTelemetryDeviceCommand(TelemetrySourceId.of(sourceId), TelemetryDeviceId.of(deviceId));
    }

    public DeactivateTelemetryDeviceCommand toDeactivateTelemetryDeviceCommand(String deviceId) {
        return new DeactivateTelemetryDeviceCommand(TelemetryDeviceId.of(deviceId));
    }

    public RetireTelemetryDeviceCommand toRetireTelemetryDeviceCommand(String deviceId) {
        return new RetireTelemetryDeviceCommand(TelemetryDeviceId.of(deviceId));
    }

    public ActivateTelemetryPointCommand toActivateTelemetryPointCommand(String deviceId, String pointId) {
        return new ActivateTelemetryPointCommand(TelemetryDeviceId.of(deviceId), TelemetryPointId.of(pointId));
    }

    public SuspendTelemetryPointCommand toSuspendTelemetryPointCommand(String pointId) {
        return new SuspendTelemetryPointCommand(TelemetryPointId.of(pointId));
    }

    public RetireTelemetryPointCommand toRetireTelemetryPointCommand(String pointId) {
        return new RetireTelemetryPointCommand(TelemetryPointId.of(pointId));
    }

    public AcceptTelemetryReadingCommand toAcceptTelemetryReadingCommand(String readingId) {
        return new AcceptTelemetryReadingCommand(TelemetryReadingId.of(readingId));
    }

    public MarkTelemetryReadingDuplicateCommand toMarkTelemetryReadingDuplicateCommand(String readingId) {
        return new MarkTelemetryReadingDuplicateCommand(TelemetryReadingId.of(readingId));
    }

    public MarkTelemetryIngestionBatchProcessingCommand toMarkTelemetryIngestionBatchProcessingCommand(String batchId) {
        return new MarkTelemetryIngestionBatchProcessingCommand(TelemetryIngestionBatchId.of(batchId));
    }

    public GetTelemetrySourceByIdQuery toGetTelemetrySourceByIdQuery(String sourceId) {
        return new GetTelemetrySourceByIdQuery(TelemetrySourceId.of(sourceId));
    }

    public ListTelemetrySourcesQuery toListTelemetrySourcesQuery(
            String searchText,
            String sourceTypeId,
            String sourceTypeCode,
            String protocolId,
            String protocolCode,
            String status,
            Integer page,
            Integer size,
            String sortField,
            String sortDirection) {

        return new ListTelemetrySourcesQuery(
                blankToNull(searchText),
                optionalSourceType(sourceTypeId, sourceTypeCode),
                optionalProtocol(protocolId, protocolCode),
                optionalEnum(status, TelemetrySourceStatus.class),
                pageRequest(page, size, sortField, sortDirection));
    }

    public GetTelemetryDeviceByIdQuery toGetTelemetryDeviceByIdQuery(String deviceId) {
        return new GetTelemetryDeviceByIdQuery(TelemetryDeviceId.of(deviceId));
    }

    public ListTelemetryDevicesQuery toListTelemetryDevicesQuery(
            String searchText,
            String sourceId,
            String deviceTypeId,
            String deviceTypeCode,
            String status,
            Integer page,
            Integer size,
            String sortField,
            String sortDirection) {

        return new ListTelemetryDevicesQuery(
                blankToNull(searchText),
                optionalSourceId(sourceId),
                optionalDeviceType(deviceTypeId, deviceTypeCode),
                optionalEnum(status, TelemetryDeviceStatus.class),
                pageRequest(page, size, sortField, sortDirection));
    }

    public GetTelemetryPointByIdQuery toGetTelemetryPointByIdQuery(String pointId) {
        return new GetTelemetryPointByIdQuery(TelemetryPointId.of(pointId));
    }

    public ListTelemetryPointsQuery toListTelemetryPointsQuery(
            String searchText,
            String deviceId,
            String pointTypeId,
            String pointTypeCode,
            String signalTypeId,
            String signalTypeCode,
            String status,
            Integer page,
            Integer size,
            String sortField,
            String sortDirection) {

        return new ListTelemetryPointsQuery(
                blankToNull(searchText),
                optionalDeviceId(deviceId),
                optionalPointType(pointTypeId, pointTypeCode),
                optionalSignalType(signalTypeId, signalTypeCode),
                optionalEnum(status, TelemetryPointStatus.class),
                pageRequest(page, size, sortField, sortDirection));
    }

    public GetTelemetryPointBindingByIdQuery toGetTelemetryPointBindingByIdQuery(String bindingId) {
        return new GetTelemetryPointBindingByIdQuery(TelemetryPointBindingId.of(bindingId));
    }

    public ListTelemetryPointBindingsQuery toListTelemetryPointBindingsQuery(
            String pointId,
            String assetTypeCode,
            String assetId,
            String bindingRoleId,
            String bindingRoleCode,
            Boolean active,
            Integer page,
            Integer size,
            String sortField,
            String sortDirection) {

        return new ListTelemetryPointBindingsQuery(
                optionalPointId(pointId),
                optionalCode(assetTypeCode),
                blankToNull(assetId),
                optionalBindingRole(bindingRoleId, bindingRoleCode),
                active,
                pageRequest(page, size, sortField, sortDirection));
    }

    public GetTelemetryReadingByIdQuery toGetTelemetryReadingByIdQuery(String readingId) {
        return new GetTelemetryReadingByIdQuery(TelemetryReadingId.of(readingId));
    }

    public GetLatestTelemetryReadingQuery toGetLatestTelemetryReadingQuery(String pointId) {
        return new GetLatestTelemetryReadingQuery(TelemetryPointId.of(pointId));
    }

    public ListTelemetryReadingsQuery toListTelemetryReadingsQuery(
            String pointId,
            String qualityCodeId,
            String qualityCode,
            String state,
            Instant fromSourceTimestamp,
            Instant toSourceTimestamp,
            String ingestionBatchId,
            Integer page,
            Integer size,
            String sortField,
            String sortDirection) {

        return new ListTelemetryReadingsQuery(
                optionalPointId(pointId),
                optionalQualityCode(qualityCodeId, qualityCode),
                optionalEnum(state, TelemetryReadingState.class),
                fromSourceTimestamp == null ? null : TelemetryTimestamp.of(fromSourceTimestamp),
                toSourceTimestamp == null ? null : TelemetryTimestamp.of(toSourceTimestamp),
                optionalIngestionBatchId(ingestionBatchId),
                pageRequest(page, size, sortField, sortDirection));
    }

    public GetTelemetryIngestionBatchByIdQuery toGetTelemetryIngestionBatchByIdQuery(String batchId) {
        return new GetTelemetryIngestionBatchByIdQuery(TelemetryIngestionBatchId.of(batchId));
    }

    public ListTelemetryIngestionBatchesQuery toListTelemetryIngestionBatchesQuery(
            String sourceId,
            String status,
            Instant startedFrom,
            Instant startedTo,
            Integer page,
            Integer size,
            String sortField,
            String sortDirection) {

        return new ListTelemetryIngestionBatchesQuery(
                optionalSourceId(sourceId),
                optionalEnum(status, TelemetryIngestionBatchStatus.class),
                startedFrom == null ? null : TelemetryTimestamp.of(startedFrom),
                startedTo == null ? null : TelemetryTimestamp.of(startedTo),
                pageRequest(page, size, sortField, sortDirection));
    }

    public GetTelemetryCatalogTypeQuery toGetTelemetryCatalogTypeQuery(String typeId, String locale) {
        return new GetTelemetryCatalogTypeQuery(TelemetryTypeCatalogId.of(typeId), blankToNull(locale));
    }

    public ListTelemetryCatalogTypesQuery toListTelemetryCatalogTypesQuery(
            String catalogName,
            String locale,
            Boolean active,
            Integer page,
            Integer size,
            String sortField,
            String sortDirection) {

        return new ListTelemetryCatalogTypesQuery(
                blankToNull(catalogName),
                blankToNull(locale),
                active,
                pageRequest(page, size, sortField, sortDirection));
    }

    public ResolveTelemetryCatalogTypeQuery toResolveTelemetryCatalogTypeQuery(
            String catalogName,
            String code,
            Boolean requireActive,
            String locale) {

        return new ResolveTelemetryCatalogTypeQuery(
                requireText(catalogName, "catalogName"),
                TelemetryCode.of(code),
                requireActive == null || requireActive,
                blankToNull(locale));
    }

    public TelemetrySourceResponse toResponse(TelemetrySourceDto dto) {
        if (dto == null) {
            return null;
        }

        return new TelemetrySourceResponse(
                dto.id(),
                dto.code(),
                toResponse(dto.name()),
                toResponse(dto.sourceType()),
                toResponse(dto.protocol()),
                dto.endpointUri(),
                dto.externalReference(),
                dto.status(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public TelemetryDeviceResponse toResponse(TelemetryDeviceDto dto) {
        if (dto == null) {
            return null;
        }

        return new TelemetryDeviceResponse(
                dto.id(),
                dto.sourceId(),
                dto.code(),
                toResponse(dto.name()),
                toResponse(dto.deviceType()),
                dto.externalReference(),
                dto.status(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public TelemetryPointResponse toResponse(TelemetryPointDto dto) {
        if (dto == null) {
            return null;
        }

        return new TelemetryPointResponse(
                dto.id(),
                dto.deviceId(),
                dto.code(),
                toResponse(dto.name()),
                toResponse(dto.pointType()),
                toResponse(dto.signalType()),
                toResponse(dto.unit()),
                toResponse(dto.defaultAggregationMethod()),
                dto.samplingPeriodSeconds(),
                dto.externalReference(),
                dto.status(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public TelemetryPointBindingResponse toResponse(TelemetryPointBindingDto dto) {
        if (dto == null) {
            return null;
        }

        return new TelemetryPointBindingResponse(
                dto.id(),
                dto.pointId(),
                toResponse(dto.topologyAssetReference()),
                toResponse(dto.bindingRole()),
                dto.active(),
                dto.validFrom(),
                dto.validTo(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public TelemetryReadingResponse toResponse(TelemetryReadingDto dto) {
        if (dto == null) {
            return null;
        }

        return new TelemetryReadingResponse(
                dto.id(),
                dto.pointId(),
                toResponse(dto.value()),
                toResponse(dto.qualityCode()),
                dto.sourceTimestamp(),
                dto.receivedAt(),
                dto.state(),
                dto.ingestionBatchId(),
                dto.correlationId(),
                dto.rejectionReason());
    }

    public TelemetryIngestionBatchResponse toResponse(TelemetryIngestionBatchDto dto) {
        if (dto == null) {
            return null;
        }

        return new TelemetryIngestionBatchResponse(
                dto.id(),
                dto.sourceId(),
                dto.correlationId(),
                dto.status(),
                dto.receivedCount(),
                dto.acceptedCount(),
                dto.rejectedCount(),
                dto.duplicateCount(),
                dto.quarantinedCount(),
                dto.startedAt(),
                dto.completedAt(),
                dto.failureReason());
    }

    public TelemetryCatalogResponse toResponse(TelemetryCatalogDto dto) {
        if (dto == null) {
            return null;
        }

        return new TelemetryCatalogResponse(
                dto.id(),
                dto.catalogName(),
                dto.code(),
                dto.active(),
                dto.sortOrder(),
                dto.systemDefined(),
                dto.resolvedLocale(),
                dto.resolvedName(),
                dto.resolvedDescription(),
                dto.translations().stream().map(this::toResponse).toList(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public <D, R> TelemetryPageResponse<R> toResponse(
            TelemetryPageDto<D> page,
            Function<D, R> itemMapper) {

        Objects.requireNonNull(itemMapper, "Telemetry page itemMapper must not be null.");

        if (page == null) {
            return TelemetryPageResponse.empty(DEFAULT_PAGE, DEFAULT_SIZE);
        }

        return new TelemetryPageResponse<>(
                page.items().stream().map(itemMapper).toList(),
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages());
    }

    public TelemetryPageResponse<TelemetrySourceResponse> toSourcePageResponse(TelemetryPageDto<TelemetrySourceDto> page) {
        return toResponse(page, this::toResponse);
    }

    public TelemetryPageResponse<TelemetryDeviceResponse> toDevicePageResponse(TelemetryPageDto<TelemetryDeviceDto> page) {
        return toResponse(page, this::toResponse);
    }

    public TelemetryPageResponse<TelemetryPointResponse> toPointPageResponse(TelemetryPageDto<TelemetryPointDto> page) {
        return toResponse(page, this::toResponse);
    }

    public TelemetryPageResponse<TelemetryPointBindingResponse> toPointBindingPageResponse(TelemetryPageDto<TelemetryPointBindingDto> page) {
        return toResponse(page, this::toResponse);
    }

    public TelemetryPageResponse<TelemetryReadingResponse> toReadingPageResponse(TelemetryPageDto<TelemetryReadingDto> page) {
        return toResponse(page, this::toResponse);
    }

    public TelemetryPageResponse<TelemetryIngestionBatchResponse> toIngestionBatchPageResponse(TelemetryPageDto<TelemetryIngestionBatchDto> page) {
        return toResponse(page, this::toResponse);
    }

    public TelemetryPageResponse<TelemetryCatalogResponse> toCatalogPageResponse(TelemetryPageDto<TelemetryCatalogDto> page) {
        return toResponse(page, this::toResponse);
    }

    private TelemetryLocalizedNameResponse toResponse(TelemetryLocalizedNameDto dto) {
        return dto == null ? null : new TelemetryLocalizedNameResponse(dto.nameAr(), dto.nameFr(), dto.nameEn());
    }

    private TelemetryTypeReferenceResponse toResponse(TelemetryTypeReferenceDto dto) {
        return dto == null ? null : new TelemetryTypeReferenceResponse(dto.id(), dto.code(), dto.label(), dto.locale());
    }

    private TopologyAssetReferenceResponse toResponse(dz.sh.hidra.modules.telemetry.application.dto.TopologyAssetReferenceDto dto) {
        return dto == null
                ? null
                : new TopologyAssetReferenceResponse(
                        dto.assetTypeCode(),
                        dto.assetId(),
                        dto.assetCode(),
                        dto.assetNameSnapshot());
    }

    private TelemetryReadingValueResponse toResponse(TelemetryReadingValueDto dto) {
        return dto == null
                ? null
                : new TelemetryReadingValueResponse(dto.numericValue(), dto.textValue(), dto.booleanValue());
    }

    private TelemetryCatalogTranslationResponse toResponse(TelemetryCatalogTranslationDto dto) {
        return dto == null
                ? null
                : new TelemetryCatalogTranslationResponse(
                        dto.id(),
                        dto.typeId(),
                        dto.locale(),
                        dto.name(),
                        dto.description(),
                        dto.createdAt(),
                        dto.updatedAt());
    }

    private static TelemetryLocalizedName localizedName(TelemetryLocalizedNameRequest request) {
        Objects.requireNonNull(request, "TelemetryLocalizedNameRequest must not be null.");
        return TelemetryLocalizedName.of(request.nameAr(), request.nameFr(), request.nameEn());
    }

    private static TelemetryCode code(String value) {
        return TelemetryCode.of(value);
    }

    private static TelemetrySourceTypeReference sourceType(TelemetryTypeReferenceRequest request) {
        Objects.requireNonNull(request, "Telemetry source type reference must not be null.");
        return TelemetrySourceTypeReference.of(request.id(), request.code());
    }

    private static TelemetrySourceTypeReference optionalSourceType(String id, String code) {
        return id == null || id.isBlank() ? null : TelemetrySourceTypeReference.of(id, fallbackCode(id, code));
    }

    private static TelemetryDeviceTypeReference deviceType(TelemetryTypeReferenceRequest request) {
        Objects.requireNonNull(request, "Telemetry device type reference must not be null.");
        return TelemetryDeviceTypeReference.of(request.id(), request.code());
    }

    private static TelemetryDeviceTypeReference optionalDeviceType(String id, String code) {
        return id == null || id.isBlank() ? null : TelemetryDeviceTypeReference.of(id, fallbackCode(id, code));
    }

    private static TelemetryPointTypeReference pointType(TelemetryTypeReferenceRequest request) {
        Objects.requireNonNull(request, "Telemetry point type reference must not be null.");
        return TelemetryPointTypeReference.of(request.id(), request.code());
    }

    private static TelemetryPointTypeReference optionalPointType(String id, String code) {
        return id == null || id.isBlank() ? null : TelemetryPointTypeReference.of(id, fallbackCode(id, code));
    }

    private static TelemetrySignalTypeReference signalType(TelemetryTypeReferenceRequest request) {
        Objects.requireNonNull(request, "Telemetry signal type reference must not be null.");
        return TelemetrySignalTypeReference.of(request.id(), request.code());
    }

    private static TelemetrySignalTypeReference optionalSignalType(String id, String code) {
        return id == null || id.isBlank() ? null : TelemetrySignalTypeReference.of(id, fallbackCode(id, code));
    }

    private static TelemetryUnitReference unit(TelemetryTypeReferenceRequest request) {
        return request == null ? null : TelemetryUnitReference.of(request.id(), request.code());
    }

    private static TelemetryAggregationMethodReference aggregation(TelemetryTypeReferenceRequest request) {
        return request == null ? null : TelemetryAggregationMethodReference.of(request.id(), request.code());
    }

    private static TelemetryProtocolReference protocol(TelemetryTypeReferenceRequest request) {
        Objects.requireNonNull(request, "Telemetry protocol reference must not be null.");
        return TelemetryProtocolReference.of(request.id(), request.code());
    }

    private static TelemetryProtocolReference optionalProtocol(String id, String code) {
        return id == null || id.isBlank() ? null : TelemetryProtocolReference.of(id, fallbackCode(id, code));
    }

    private static TelemetryBindingRoleReference bindingRole(TelemetryTypeReferenceRequest request) {
        Objects.requireNonNull(request, "Telemetry binding role reference must not be null.");
        return TelemetryBindingRoleReference.of(request.id(), request.code());
    }

    private static TelemetryBindingRoleReference optionalBindingRole(String id, String code) {
        return id == null || id.isBlank() ? null : TelemetryBindingRoleReference.of(id, fallbackCode(id, code));
    }

    private static TelemetryQualityCodeReference qualityCode(TelemetryTypeReferenceRequest request) {
        Objects.requireNonNull(request, "Telemetry quality code reference must not be null.");
        return TelemetryQualityCodeReference.of(request.id(), request.code());
    }

    private static TelemetryQualityCodeReference optionalQualityCode(String id, String code) {
        return id == null || id.isBlank() ? null : TelemetryQualityCodeReference.of(id, fallbackCode(id, code));
    }

    private static TopologyAssetReference topologyAssetReference(TopologyAssetReferenceRequest request) {
        Objects.requireNonNull(request, "TopologyAssetReferenceRequest must not be null.");

        return TopologyAssetReference.of(
                request.assetTypeCode(),
                request.assetId(),
                request.assetCode(),
                request.assetNameSnapshot());
    }

    private static TelemetryReadingValue readingValue(TelemetryReadingValueRequest request) {
        Objects.requireNonNull(request, "TelemetryReadingValueRequest must not be null.");
        return new TelemetryReadingValue(request.numericValue(), request.textValue(), request.booleanValue());
    }

    private static TelemetryEndpointUri endpoint(String value) {
        return value == null || value.isBlank() ? null : TelemetryEndpointUri.of(value);
    }

    private static TelemetryExternalReference externalReference(String value) {
        return value == null || value.isBlank() ? null : TelemetryExternalReference.of(value);
    }

    private static TelemetryCorrelationId optionalCorrelationId(String value) {
        return value == null || value.isBlank() ? null : TelemetryCorrelationId.of(value);
    }

    private static TelemetryIngestionBatchId optionalIngestionBatchId(String value) {
        return value == null || value.isBlank() ? null : TelemetryIngestionBatchId.of(value);
    }

    private static TelemetrySourceId optionalSourceId(String value) {
        return value == null || value.isBlank() ? null : TelemetrySourceId.of(value);
    }

    private static TelemetryDeviceId optionalDeviceId(String value) {
        return value == null || value.isBlank() ? null : TelemetryDeviceId.of(value);
    }

    private static TelemetryPointId optionalPointId(String value) {
        return value == null || value.isBlank() ? null : TelemetryPointId.of(value);
    }

    private static TelemetryCode optionalCode(String value) {
        return value == null || value.isBlank() ? null : TelemetryCode.of(value);
    }

    private static PageRequest pageRequest(Integer page, Integer size, String sortField, String sortDirection) {
        int safePage = page == null ? DEFAULT_PAGE : page;
        int safeSize = size == null ? DEFAULT_SIZE : size;
        String safeSortField = blankToNull(sortField);

        if (safeSortField == null) {
            return PageRequest.of(safePage, safeSize);
        }

        return PageRequest.sorted(safePage, safeSize, safeSortField, optionalSortDirection(sortDirection));
    }

    private static SortDirection optionalSortDirection(String value) {
        if (value == null || value.isBlank()) {
            return SortDirection.ASC;
        }

        return SortDirection.valueOf(value.trim().toUpperCase(Locale.ROOT));
    }

    private static <E extends Enum<E>> E optionalEnum(String value, Class<E> enumType) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return Enum.valueOf(enumType, value.trim().toUpperCase(Locale.ROOT));
    }

    private static String fallbackCode(String id, String code) {
        return code == null || code.isBlank() ? id : code;
    }

    private static String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be null or blank.");
        }

        return value.trim();
    }
}
