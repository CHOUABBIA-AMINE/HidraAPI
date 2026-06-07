/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCatalogApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Service
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Application service for telemetry catalog use cases.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryCatalogDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryCatalogTranslationDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPageDto;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryCatalogTypeUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryCatalogTypesUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ResolveTelemetryCatalogTypeUseCase;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryCatalogRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryCatalogTypeQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryCatalogTypesQuery;
import dz.sh.hidra.modules.telemetry.application.query.ResolveTelemetryCatalogTypeQuery;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryTypeCatalog;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryTypeTranslation;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryCatalogDomainService;

/**
 * Application service for telemetry catalog use cases.
 *
 * <p>Business role:
 * Resolves configurable telemetry catalog entries and localized catalog labels for API/application
 * consumers.
 *
 * <p>Architecture role:
 * Orchestrates catalog repository and domain catalog service. It does not use JPA, REST, Spring
 * annotations, topology implementation classes, flow calculation, risk scoring, or analytics.
 */
public final class TelemetryCatalogApplicationService implements
        GetTelemetryCatalogTypeUseCase,
        ListTelemetryCatalogTypesUseCase,
        ResolveTelemetryCatalogTypeUseCase {

    private final TelemetryCatalogRepositoryPort catalogRepository;
    private final TelemetryCatalogDomainService catalogDomainService;

    public TelemetryCatalogApplicationService(
            TelemetryCatalogRepositoryPort catalogRepository,
            TelemetryCatalogDomainService catalogDomainService) {

        this.catalogRepository = Objects.requireNonNull(catalogRepository, "TelemetryCatalogRepositoryPort must not be null.");
        this.catalogDomainService = Objects.requireNonNull(catalogDomainService, "TelemetryCatalogDomainService must not be null.");
    }

    @Override
    public TelemetryCatalogDto getTelemetryCatalogType(GetTelemetryCatalogTypeQuery query) {
        Objects.requireNonNull(query, "GetTelemetryCatalogTypeQuery must not be null.");

        TelemetryTypeCatalog catalog = catalogRepository.findById(query.typeId())
                .orElseThrow(() -> notFound("Telemetry catalog type not found: " + query.typeId().value()));

        return toDto(catalog, query.locale());
    }

    @Override
    public TelemetryPageDto<TelemetryCatalogDto> listTelemetryCatalogTypes(ListTelemetryCatalogTypesQuery query) {
        Objects.requireNonNull(query, "ListTelemetryCatalogTypesQuery must not be null.");

        PageResult<TelemetryTypeCatalog> page = catalogRepository.findAll(
                query.catalogName(),
                query.active(),
                query.pageRequest());

        return new TelemetryPageDto<>(
                page.items().stream().map(catalog -> toDto(catalog, query.locale())).toList(),
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages());
    }

    @Override
    public TelemetryCatalogDto resolveTelemetryCatalogType(ResolveTelemetryCatalogTypeQuery query) {
        Objects.requireNonNull(query, "ResolveTelemetryCatalogTypeQuery must not be null.");

        TelemetryTypeCatalog catalog = catalogRepository.findByCatalogNameAndCode(query.catalogName(), query.code())
                .orElseThrow(() -> notFound("Telemetry catalog type not found: " + query.catalogName() + "/" + query.code().value()));

        if (query.requireActive()) {
            catalogDomainService.requireUsableCatalog(catalog, query.catalogName());
        }

        return toDto(catalog, query.locale());
    }

    private TelemetryCatalogDto toDto(TelemetryTypeCatalog catalog, String locale) {
        TelemetryTypeTranslation resolved = catalogDomainService.selectTranslation(catalog, locale);

        return new TelemetryCatalogDto(
                catalog.id().value(),
                catalog.catalogName(),
                catalog.code().value(),
                catalog.active(),
                catalog.sortOrder(),
                catalog.systemDefined(),
                resolved.locale(),
                resolved.name().value(),
                resolved.description(),
                catalog.translations().stream().map(this::toTranslationDto).toList(),
                catalog.createdAt(),
                catalog.updatedAt());
    }

    private TelemetryCatalogTranslationDto toTranslationDto(TelemetryTypeTranslation translation) {
        return new TelemetryCatalogTranslationDto(
                translation.id().value(),
                translation.typeId().value(),
                translation.locale(),
                translation.name().value(),
                translation.description(),
                translation.createdAt(),
                translation.updatedAt());
    }

    private static BusinessRuleViolationException notFound(String message) {
        return new BusinessRuleViolationException(message);
    }
}
