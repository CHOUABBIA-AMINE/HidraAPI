/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Service
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Application service for telemetry source use cases.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.application.command.ActivateTelemetrySourceCommand;
import dz.sh.hidra.modules.telemetry.application.command.DeactivateTelemetrySourceCommand;
import dz.sh.hidra.modules.telemetry.application.command.RegisterTelemetrySourceCommand;
import dz.sh.hidra.modules.telemetry.application.command.RetireTelemetrySourceCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryLocalizedNameDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPageDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetrySourceDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryTypeReferenceDto;
import dz.sh.hidra.modules.telemetry.application.port.in.ActivateTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.DeactivateTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetrySourcesUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RegisterTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RetireTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetrySourceRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetrySourceByIdQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetrySourcesQuery;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryRegistrationDomainService;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryExternalReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryProtocolReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceTypeReference;

/**
 * Application service for telemetry source use cases.
 *
 * <p>Business role:
 * Registers and manages telemetry acquisition sources.
 *
 * <p>Architecture role:
 * Coordinates repository ports and domain services without depending on REST, JPA, Spring
 * annotations, topology implementation classes, flow calculation, risk scoring, or analytics.
 */
public final class TelemetrySourceApplicationService implements
        RegisterTelemetrySourceUseCase,
        ActivateTelemetrySourceUseCase,
        DeactivateTelemetrySourceUseCase,
        RetireTelemetrySourceUseCase,
        GetTelemetrySourceUseCase,
        ListTelemetrySourcesUseCase {

    private final TelemetrySourceRepositoryPort sourceRepository;
    private final TelemetryRegistrationDomainService registrationDomainService;

    public TelemetrySourceApplicationService(
            TelemetrySourceRepositoryPort sourceRepository,
            TelemetryRegistrationDomainService registrationDomainService) {

        this.sourceRepository = Objects.requireNonNull(sourceRepository, "TelemetrySourceRepositoryPort must not be null.");
        this.registrationDomainService = Objects.requireNonNull(registrationDomainService, "TelemetryRegistrationDomainService must not be null.");
    }

    @Override
    public TelemetrySourceDto registerTelemetrySource(RegisterTelemetrySourceCommand command) {
        Objects.requireNonNull(command, "RegisterTelemetrySourceCommand must not be null.");

        if (sourceRepository.existsByCode(command.code())) {
            throw new BusinessRuleViolationException("Telemetry source code already exists: " + command.code().value());
        }

        TelemetrySource source = registrationDomainService.registerSource(
                command.code(),
                command.name(),
                command.sourceType(),
                command.protocol(),
                command.endpointUri(),
                command.externalReference());

        return toDto(sourceRepository.save(source));
    }

    @Override
    public TelemetrySourceDto activateTelemetrySource(ActivateTelemetrySourceCommand command) {
        TelemetrySource source = findSource(command.sourceId());
        return toDto(sourceRepository.save(source.activate()));
    }

    @Override
    public TelemetrySourceDto deactivateTelemetrySource(DeactivateTelemetrySourceCommand command) {
        TelemetrySource source = findSource(command.sourceId());
        return toDto(sourceRepository.save(source.deactivate()));
    }

    @Override
    public TelemetrySourceDto retireTelemetrySource(RetireTelemetrySourceCommand command) {
        TelemetrySource source = findSource(command.sourceId());
        return toDto(sourceRepository.save(source.retire()));
    }

    @Override
    public TelemetrySourceDto getTelemetrySource(GetTelemetrySourceByIdQuery query) {
        Objects.requireNonNull(query, "GetTelemetrySourceByIdQuery must not be null.");
        return toDto(findSource(query.sourceId()));
    }

    @Override
    public TelemetryPageDto<TelemetrySourceDto> listTelemetrySources(ListTelemetrySourcesQuery query) {
        Objects.requireNonNull(query, "ListTelemetrySourcesQuery must not be null.");

        PageResult<TelemetrySource> page = sourceRepository.findAll(
                query.searchText(),
                query.sourceType(),
                query.protocol(),
                query.status(),
                query.pageRequest());

        return new TelemetryPageDto<>(
                page.items().stream().map(this::toDto).toList(),
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages());
    }

    private TelemetrySource findSource(dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceId sourceId) {
        return sourceRepository.findById(sourceId)
                .orElseThrow(() -> new BusinessRuleViolationException("Telemetry source not found: " + sourceId.value()));
    }

    private TelemetrySourceDto toDto(TelemetrySource source) {
        return new TelemetrySourceDto(
                source.id().value(),
                source.code().value(),
                new TelemetryLocalizedNameDto(source.name().nameAr(), source.name().nameFr(), source.name().nameEn()),
                toTypeDto(source.sourceType()),
                toTypeDto(source.protocol()),
                source.endpointUri() == null ? null : source.endpointUri().value(),
                value(source.externalReference()),
                source.status().name(),
                source.createdAt(),
                source.updatedAt());
    }

    private static TelemetryTypeReferenceDto toTypeDto(TelemetrySourceTypeReference reference) {
        return new TelemetryTypeReferenceDto(reference.id(), reference.name(), reference.name(), null);
    }

    private static TelemetryTypeReferenceDto toTypeDto(TelemetryProtocolReference reference) {
        return new TelemetryTypeReferenceDto(reference.id(), reference.name(), reference.name(), null);
    }

    private static String value(TelemetryExternalReference reference) {
        return reference == null ? null : reference.value();
    }
}
