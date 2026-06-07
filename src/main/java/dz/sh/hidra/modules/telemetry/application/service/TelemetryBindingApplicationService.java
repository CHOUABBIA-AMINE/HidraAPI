/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryBindingApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Service
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Application service for telemetry point binding use cases.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.application.command.BindTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.command.CloseTelemetryPointBindingCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPageDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPointBindingDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryTypeReferenceDto;
import dz.sh.hidra.modules.telemetry.application.dto.TopologyAssetReferenceDto;
import dz.sh.hidra.modules.telemetry.application.port.in.BindTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.CloseTelemetryPointBindingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryPointBindingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryPointBindingsUseCase;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointBindingRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryTopologyAssetLookupPort;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryPointBindingByIdQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryPointBindingsQuery;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPointBinding;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryBindingDomainService;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryBindingRoleReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointBindingId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointId;
import dz.sh.hidra.modules.telemetry.domain.value.TopologyAssetReference;

/**
 * Application service for telemetry point binding use cases.
 */
public final class TelemetryBindingApplicationService implements
        BindTelemetryPointUseCase,
        CloseTelemetryPointBindingUseCase,
        GetTelemetryPointBindingUseCase,
        ListTelemetryPointBindingsUseCase {

    private final TelemetryPointRepositoryPort pointRepository;
    private final TelemetryPointBindingRepositoryPort bindingRepository;
    private final TelemetryTopologyAssetLookupPort topologyAssetLookupPort;
    private final TelemetryBindingDomainService bindingDomainService;

    public TelemetryBindingApplicationService(
            TelemetryPointRepositoryPort pointRepository,
            TelemetryPointBindingRepositoryPort bindingRepository,
            TelemetryTopologyAssetLookupPort topologyAssetLookupPort,
            TelemetryBindingDomainService bindingDomainService) {

        this.pointRepository = Objects.requireNonNull(pointRepository, "TelemetryPointRepositoryPort must not be null.");
        this.bindingRepository = Objects.requireNonNull(bindingRepository, "TelemetryPointBindingRepositoryPort must not be null.");
        this.topologyAssetLookupPort = Objects.requireNonNull(topologyAssetLookupPort, "TelemetryTopologyAssetLookupPort must not be null.");
        this.bindingDomainService = Objects.requireNonNull(bindingDomainService, "TelemetryBindingDomainService must not be null.");
    }

    @Override
    public TelemetryPointBindingDto bindTelemetryPoint(BindTelemetryPointCommand command) {
        Objects.requireNonNull(command, "BindTelemetryPointCommand must not be null.");

        TelemetryPoint point = findPoint(command.pointId());
        TopologyAssetReference topologyReference = command.topologyAssetReference();

        if (!topologyAssetLookupPort.existsTopologyAsset(topologyReference.assetTypeCode(), topologyReference.assetId())) {
            throw new BusinessRuleViolationException("Topology asset referenced by telemetry binding does not exist.");
        }

        TelemetryPointBinding binding = bindingDomainService.bindPointToTopology(
                point,
                topologyReference,
                command.bindingRole(),
                bindingRepository.findActiveByPointId(command.pointId()),
                command.validFrom());

        return toDto(bindingRepository.save(binding));
    }

    @Override
    public TelemetryPointBindingDto closeTelemetryPointBinding(CloseTelemetryPointBindingCommand command) {
        Objects.requireNonNull(command, "CloseTelemetryPointBindingCommand must not be null.");
        TelemetryPointBinding binding = findBinding(command.bindingId());
        return toDto(bindingRepository.save(bindingDomainService.closeBinding(binding, command.closedAt())));
    }

    @Override
    public TelemetryPointBindingDto getTelemetryPointBinding(GetTelemetryPointBindingByIdQuery query) {
        Objects.requireNonNull(query, "GetTelemetryPointBindingByIdQuery must not be null.");
        return toDto(findBinding(query.bindingId()));
    }

    @Override
    public TelemetryPageDto<TelemetryPointBindingDto> listTelemetryPointBindings(ListTelemetryPointBindingsQuery query) {
        Objects.requireNonNull(query, "ListTelemetryPointBindingsQuery must not be null.");

        PageResult<TelemetryPointBinding> page = bindingRepository.findAll(
                query.pointId(),
                query.assetTypeCode(),
                query.assetId(),
                query.bindingRole(),
                query.active(),
                query.pageRequest());

        return new TelemetryPageDto<>(
                page.items().stream().map(this::toDto).toList(),
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages());
    }

    private TelemetryPoint findPoint(TelemetryPointId pointId) {
        return pointRepository.findById(pointId)
                .orElseThrow(() -> new BusinessRuleViolationException("Telemetry point not found: " + pointId.value()));
    }

    private TelemetryPointBinding findBinding(TelemetryPointBindingId bindingId) {
        return bindingRepository.findById(bindingId)
                .orElseThrow(() -> new BusinessRuleViolationException("Telemetry point binding not found: " + bindingId.value()));
    }

    private TelemetryPointBindingDto toDto(TelemetryPointBinding binding) {
        return new TelemetryPointBindingDto(
                binding.id().value(),
                binding.pointId().value(),
                toTopologyDto(binding.topologyAssetReference()),
                toTypeDto(binding.bindingRole()),
                binding.active(),
                binding.validFrom(),
                binding.validTo(),
                binding.createdAt(),
                binding.updatedAt());
    }

    private static TopologyAssetReferenceDto toTopologyDto(TopologyAssetReference reference) {
        return new TopologyAssetReferenceDto(
                reference.assetTypeCode().value(),
                reference.assetId(),
                reference.assetCode().value(),
                reference.assetNameSnapshot());
    }

    private static TelemetryTypeReferenceDto toTypeDto(TelemetryBindingRoleReference reference) {
        return new TelemetryTypeReferenceDto(reference.id(), reference.name(), reference.name(), null);
    }
}
