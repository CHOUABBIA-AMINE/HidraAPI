/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryDeviceApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Service
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Application service for telemetry device use cases.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.application.command.ActivateTelemetryDeviceCommand;
import dz.sh.hidra.modules.telemetry.application.command.DeactivateTelemetryDeviceCommand;
import dz.sh.hidra.modules.telemetry.application.command.RegisterTelemetryDeviceCommand;
import dz.sh.hidra.modules.telemetry.application.command.RetireTelemetryDeviceCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryDeviceDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryLocalizedNameDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPageDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryTypeReferenceDto;
import dz.sh.hidra.modules.telemetry.application.port.in.ActivateTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.DeactivateTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryDevicesUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RegisterTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RetireTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryDeviceRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetrySourceRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryDeviceByIdQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryDevicesQuery;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryDevice;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryRegistrationDomainService;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryExternalReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceId;

/**
 * Application service for telemetry device use cases.
 */
public final class TelemetryDeviceApplicationService implements
        RegisterTelemetryDeviceUseCase,
        ActivateTelemetryDeviceUseCase,
        DeactivateTelemetryDeviceUseCase,
        RetireTelemetryDeviceUseCase,
        GetTelemetryDeviceUseCase,
        ListTelemetryDevicesUseCase {

    private final TelemetrySourceRepositoryPort sourceRepository;
    private final TelemetryDeviceRepositoryPort deviceRepository;
    private final TelemetryRegistrationDomainService registrationDomainService;

    public TelemetryDeviceApplicationService(
            TelemetrySourceRepositoryPort sourceRepository,
            TelemetryDeviceRepositoryPort deviceRepository,
            TelemetryRegistrationDomainService registrationDomainService) {

        this.sourceRepository = Objects.requireNonNull(sourceRepository, "TelemetrySourceRepositoryPort must not be null.");
        this.deviceRepository = Objects.requireNonNull(deviceRepository, "TelemetryDeviceRepositoryPort must not be null.");
        this.registrationDomainService = Objects.requireNonNull(registrationDomainService, "TelemetryRegistrationDomainService must not be null.");
    }

    @Override
    public TelemetryDeviceDto registerTelemetryDevice(RegisterTelemetryDeviceCommand command) {
        Objects.requireNonNull(command, "RegisterTelemetryDeviceCommand must not be null.");

        if (deviceRepository.existsByCode(command.code())) {
            throw new BusinessRuleViolationException("Telemetry device code already exists: " + command.code().value());
        }

        TelemetrySource source = findSource(command.sourceId());
        TelemetryDevice device = registrationDomainService.registerDevice(
                source,
                command.code(),
                command.name(),
                command.deviceType(),
                command.externalReference());

        return toDto(deviceRepository.save(device));
    }

    @Override
    public TelemetryDeviceDto activateTelemetryDevice(ActivateTelemetryDeviceCommand command) {
        TelemetrySource source = findSource(command.sourceId());
        TelemetryDevice device = findDevice(command.deviceId());
        return toDto(deviceRepository.save(registrationDomainService.activateDevice(source, device)));
    }

    @Override
    public TelemetryDeviceDto deactivateTelemetryDevice(DeactivateTelemetryDeviceCommand command) {
        TelemetryDevice device = findDevice(command.deviceId());
        return toDto(deviceRepository.save(device.deactivate()));
    }

    @Override
    public TelemetryDeviceDto retireTelemetryDevice(RetireTelemetryDeviceCommand command) {
        TelemetryDevice device = findDevice(command.deviceId());
        return toDto(deviceRepository.save(device.retire()));
    }

    @Override
    public TelemetryDeviceDto getTelemetryDevice(GetTelemetryDeviceByIdQuery query) {
        Objects.requireNonNull(query, "GetTelemetryDeviceByIdQuery must not be null.");
        return toDto(findDevice(query.deviceId()));
    }

    @Override
    public TelemetryPageDto<TelemetryDeviceDto> listTelemetryDevices(ListTelemetryDevicesQuery query) {
        Objects.requireNonNull(query, "ListTelemetryDevicesQuery must not be null.");

        PageResult<TelemetryDevice> page = deviceRepository.findAll(
                query.searchText(),
                query.sourceId(),
                query.deviceType(),
                query.status(),
                query.pageRequest());

        return new TelemetryPageDto<>(
                page.items().stream().map(this::toDto).toList(),
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages());
    }

    private TelemetrySource findSource(TelemetrySourceId sourceId) {
        return sourceRepository.findById(sourceId)
                .orElseThrow(() -> new BusinessRuleViolationException("Telemetry source not found: " + sourceId.value()));
    }

    private TelemetryDevice findDevice(TelemetryDeviceId deviceId) {
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessRuleViolationException("Telemetry device not found: " + deviceId.value()));
    }

    private TelemetryDeviceDto toDto(TelemetryDevice device) {
        return new TelemetryDeviceDto(
                device.id().value(),
                device.sourceId().value(),
                device.code().value(),
                new TelemetryLocalizedNameDto(device.name().nameAr(), device.name().nameFr(), device.name().nameEn()),
                toTypeDto(device.deviceType()),
                value(device.externalReference()),
                device.status().name(),
                device.createdAt(),
                device.updatedAt());
    }

    private static TelemetryTypeReferenceDto toTypeDto(TelemetryDeviceTypeReference reference) {
        return new TelemetryTypeReferenceDto(reference.id(), reference.name(), reference.name(), null);
    }

    private static String value(TelemetryExternalReference reference) {
        return reference == null ? null : reference.value();
    }
}
