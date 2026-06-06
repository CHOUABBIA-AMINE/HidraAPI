/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyCatalogApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Application service implementing topology catalog use cases.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.application.dto.TopologyCatalogDto;
import dz.sh.hidra.modules.topology.application.dto.TopologyCatalogTranslationDto;
import dz.sh.hidra.modules.topology.application.port.in.GetTopologyCatalogTypeUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListTopologyCatalogTypesUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ResolveTopologyCatalogTypeUseCase;
import dz.sh.hidra.modules.topology.application.port.out.TopologyCatalogRepositoryPort;
import dz.sh.hidra.modules.topology.application.query.GetTopologyCatalogTypeQuery;
import dz.sh.hidra.modules.topology.application.query.ListTopologyCatalogTypesQuery;
import dz.sh.hidra.modules.topology.application.query.ResolveTopologyCatalogTypeQuery;
import dz.sh.hidra.modules.topology.domain.model.TopologyTypeCatalog;
import dz.sh.hidra.modules.topology.domain.model.TopologyTypeTranslation;

/**
 * Application service implementing topology catalog use cases.
 *
 * <p>Business role:
 * Resolves multilingual configurable topology type catalog entries by identifier, catalog/code, or
 * catalog list queries.
 *
 * <p>Architecture role:
 * This application service depends on inbound use-case contracts, an outbound repository port, domain
 * catalog models, and application DTOs only. It does not depend on REST, JPA, Spring, identity,
 * organization implementation, measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * Missing catalog entries and inactive catalog entries required for create/update workflows are
 * rejected with business-rule violations. Localized labels fall back to the default locale, then to
 * the first available translation.
 *
 * <p>Usage:
 * Wire this service as the implementation for topology catalog use-case ports after the persistence
 * adapter is added.
 */
public final class TopologyCatalogApplicationService implements
        GetTopologyCatalogTypeUseCase,
        ListTopologyCatalogTypesUseCase,
        ResolveTopologyCatalogTypeUseCase {

    private static final String DEFAULT_LOCALE = "en";

    private final TopologyCatalogRepositoryPort topologyCatalogRepository;

    public TopologyCatalogApplicationService(TopologyCatalogRepositoryPort topologyCatalogRepository) {
        this.topologyCatalogRepository = Objects.requireNonNull(
                topologyCatalogRepository,
                "Topology catalog repository port must not be null.");
    }

    @Override
    public TopologyCatalogDto getTopologyCatalogType(GetTopologyCatalogTypeQuery query) {
        Objects.requireNonNull(query, "Get topology catalog type query must not be null.");

        return topologyCatalogRepository.findById(query.id())
                .map(catalog -> toDto(catalog, query.locale()))
                .orElseThrow(() -> new BusinessRuleViolationException("Topology catalog type was not found."));
    }

    @Override
    public PageResult<TopologyCatalogDto> listTopologyCatalogTypes(ListTopologyCatalogTypesQuery query) {
        Objects.requireNonNull(query, "List topology catalog types query must not be null.");

        PageResult<TopologyTypeCatalog> pageResult = topologyCatalogRepository.findAll(query);
        return PageResult.of(
                pageResult.items().stream()
                        .map(catalog -> toDto(catalog, query.locale()))
                        .toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

    @Override
    public TopologyCatalogDto resolveTopologyCatalogType(ResolveTopologyCatalogTypeQuery query) {
        Objects.requireNonNull(query, "Resolve topology catalog type query must not be null.");

        TopologyTypeCatalog catalog = topologyCatalogRepository
                .findByCatalogNameAndCode(query.catalogName(), query.code())
                .orElseThrow(() -> new BusinessRuleViolationException("Topology catalog type code was not found."));

        if (query.requireActive() && !catalog.isActive()) {
            throw new BusinessRuleViolationException("Topology catalog type is inactive and cannot be used for this operation.");
        }

        return toDto(catalog, query.locale());
    }

    private static TopologyCatalogDto toDto(TopologyTypeCatalog catalog, String requestedLocale) {
        Objects.requireNonNull(catalog, "Topology type catalog must not be null.");

        TopologyTypeTranslation resolvedTranslation = resolveTranslation(catalog, requestedLocale);
        return new TopologyCatalogDto(
                catalog.id(),
                catalog.catalogName(),
                catalog.code().value(),
                catalog.status().name(),
                catalog.sortOrder(),
                catalog.systemDefined(),
                resolvedTranslation == null ? normalizeLocale(requestedLocale) : resolvedTranslation.locale(),
                resolvedTranslation == null ? null : resolvedTranslation.name().value(),
                resolvedTranslation == null ? null : resolvedTranslation.description(),
                catalog.translations().stream()
                        .sorted(Comparator.comparing(TopologyTypeTranslation::locale))
                        .map(TopologyCatalogApplicationService::toTranslationDto)
                        .toList(),
                catalog.createdAt(),
                catalog.updatedAt());
    }

    private static TopologyCatalogTranslationDto toTranslationDto(TopologyTypeTranslation translation) {
        return new TopologyCatalogTranslationDto(
                translation.id(),
                translation.typeId(),
                translation.locale(),
                translation.name().value(),
                translation.description(),
                translation.createdAt(),
                translation.updatedAt());
    }

    private static TopologyTypeTranslation resolveTranslation(TopologyTypeCatalog catalog, String requestedLocale) {
        String normalizedRequestedLocale = normalizeLocale(requestedLocale);

        if (normalizedRequestedLocale != null) {
            return catalog.translationForLocale(normalizedRequestedLocale)
                    .or(() -> catalog.translationForLocale(DEFAULT_LOCALE))
                    .or(() -> catalog.translations().stream().findFirst())
                    .orElse(null);
        }

        return catalog.translationForLocale(DEFAULT_LOCALE)
                .or(() -> catalog.translations().stream().findFirst())
                .orElse(null);
    }

    private static String normalizeLocale(String locale) {
        if (locale == null || locale.isBlank()) {
            return null;
        }
        return locale.trim().replace('_', '-').toLowerCase(Locale.ROOT);
    }
}
