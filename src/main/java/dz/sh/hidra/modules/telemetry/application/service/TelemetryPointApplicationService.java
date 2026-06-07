/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Service
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Application service for telemetry point use cases.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.application.command.ActivateTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.command.RegisterTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.command.RetireTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.command.SuspendTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryLocalizedNameDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPageDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPointDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryTypeReferenceDto;
import dz.sh.hidra.modules.telemetry.application.port.in.ActivateTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryPointsUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RegisterTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RetireTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.SuspendTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryDeviceRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryPointByIdQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryPointsQuery;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryDevice;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryRegistrationDomainService;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryAggregationMethodReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryExternalReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySignalTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryUnitReference;

/**
 * Application service for telemetry point use cases.
 */
public final class TelemetryPointApplicationService implements
        RegisterTelemetryPointUseCase,
        ActivateTelemetryPointUseCase,
        SuspendTelemetryPointUseCase,
        RetireTelemetryPointUseCase,
        GetTelemetryPointUseCase,
        ListTelemetryPointsUseCase {

    private final TelemetryDeviceRepositoryPort deviceRepository;
    private final TelemetryPointRepositoryPort pointRepository;
    private final TelemetryRegistrationDomainService registrationDomainService;

    public TelemetryPointApplicationService(
            TelemetryDeviceRepositoryPort deviceRepository,
            TelemetryPointRepositoryPort pointRepository,
            TelemetryRegistrationDomainService registrationDomainService) {

        this.deviceRepository = Objects.requireNonNull(deviceRepository, "TelemetryDeviceRepositoryPort must not be null.");
        this.pointRepository = Objects.requireNonNull(pointRepository, "TelemetryPointRepositoryPort must not be null.");
        this.registrationDomainService = Objects.requireNonNull(registrationDomainService, "TelemetryRegistrationDomainService must not be null.");
    }

    @Override
    public TelemetryPointDto registerTelemetryPoint(RegisterTelemetryPointCommand command) {
        Objects.requireNonNull(command, "RegisterTelemetryPointCommand must not be null.");

        if (pointRepository.existsByCode(command.code())) {
            throw new BusinessRuleViolationException("Telemetry point code already exists: " + command.code().value());
        }

        TelemetryDevice device = findDevice(command.deviceId());
        TelemetryPoint point = registrationDomainService.registerPoint(
                device,
                command.code(),
                command.name(),
                command.pointType(),
                command.signalType(),
                command.unit(),
                command.defaultAggregationMethod(),
                command.samplingPeriod(),
                command.externalReference());

        return toDto(pointRepository.save(point));
    }

    @Override
    public TelemetryPointDto activateTelemetryPoint(ActivateTelemetryPointCommand command) {
        TelemetryDevice device = findDevice(command.deviceId());
        TelemetryPoint point = findPoint(command.pointId());
        return toDto(pointRepository.save(registrationDomainService.activatePoint(device, point)));
    }

    @Override
    public TelemetryPointDto suspendTelemetryPoint(SuspendTelemetryPointCommand command) {
        TelemetryPoint point = findPoint(command.pointId());
        return toDto(pointRepository.save(registrationDomainService.suspendPoint(point)));
    }

    @Override
    public TelemetryPointDto retireTelemetryPoint(RetireTelemetryPointCommand command) {
        TelemetryPoint point = findPoint(command.pointId());
        return toDto(pointRepository.save(point.retire()));
    }

    @Override
    public TelemetryPointDto getTelemetryPoint(GetTelemetryPointByIdQuery query) {
        Objects.requireNonNull(query, "GetTelemetryPointByIdQuery must not be null.");
        return toDto(findPoint(query.pointId()));
    }

    @Override
    public TelemetryPageDto<TelemetryPointDto> listTelemetryPoints(ListTelemetryPointsQuery query) {
        Objects.requireNonNull(query, "ListTelemetryPointsQuery must not be null.");

        PageResult<TelemetryPoint> page = pointRepository.findAll(
                query.searchText(),
                query.deviceId(),
                query.pointType(),
                query.signalType(),
                query.status(),
                query.pageRequest());

        return new TelemetryPageDto<>(
                page.items().stream().map(this::toDto).toList(),
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages());
    }

    private TelemetryDevice findDevice(TelemetryDeviceId deviceId) {
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessRuleViolationException("Telemetry device not found: " + deviceId.value()));
    }

    private TelemetryPoint findPoint(TelemetryPointId pointId) {
        return pointRepository.findById(pointId)
                .orElseThrow(() -> new BusinessRuleViolationException("Telemetry point not found: " + pointId.value()));
    }

    private TelemetryPointDto toDto(TelemetryPoint point) {
        return new TelemetryPointDto(
                point.id().value(),
                point.deviceId().value(),
                point.code().value(),
                new TelemetryLocalizedNameDto(point.name().nameAr(), point.name().nameFr(), point.name().nameEn()),
                toTypeDto(point.pointType()),
                toTypeDto(point.signalType()),
                toTypeDto(point.unit()),
                toTypeDto(point.defaultAggregationMethod()),
                point.samplingPeriod() == null ? null : point.samplingPeriod().value(),
                value(point.externalReference()),
                point.status().name(),
                point.createdAt(),
                point.updatedAt());
    }

    private static TelemetryTypeReferenceDto toTypeDto(TelemetryPointTypeReference reference) {
        return new TelemetryTypeReferenceDto(reference.id(), reference.name(), reference.name(), null);
    }

    private static TelemetryTypeReferenceDto toTypeDto(TelemetrySignalTypeReference reference) {
        return new TelemetryTypeReferenceDto(reference.id(), reference.name(), reference.name(), null);
    }

    private static TelemetryTypeReferenceDto toTypeDto(TelemetryUnitReference reference) {
        return reference == null ? null : new TelemetryTypeReferenceDto(reference.id(), reference.name(), reference.name(), null);
    }

    private static TelemetryTypeReferenceDto toTypeDto(TelemetryAggregationMethodReference reference) {
        return reference == null ? null : new TelemetryTypeReferenceDto(reference.id(), reference.name(), reference.name(), null);
    }

    private static String value(TelemetryExternalReference reference) {
        return reference == null ? null : reference.value();
    }
}
