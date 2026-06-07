/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCatalogDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.service
 *
 * @Description : Domain service for telemetry catalog validation and localized translation selection.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.service;

import java.util.Objects;

import dz.sh.hidra.modules.telemetry.domain.model.TelemetryTypeCatalog;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryTypeTranslation;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryCatalogPolicy;

/**
 * Domain service for telemetry catalog validation and localized translation selection.
 *
 * <p>Business role:
 * Coordinates catalog readiness checks for telemetry source, device, point, signal, quality, unit,
 * protocol, aggregation, and binding-role taxonomies.
 *
 * <p>Architecture role:
 * Pure domain service. It does not read repositories, expose REST contracts, use JPA, or resolve
 * catalog entries from persistence. Application services must load catalog entries and pass them in.
 */
public final class TelemetryCatalogDomainService {

    private final TelemetryCatalogPolicy catalogPolicy;

    public TelemetryCatalogDomainService(TelemetryCatalogPolicy catalogPolicy) {
        this.catalogPolicy = Objects.requireNonNull(catalogPolicy, "TelemetryCatalogPolicy must not be null.");
    }

    public void requireUsableCatalog(TelemetryTypeCatalog catalog, String expectedCatalogName) {
        catalogPolicy.requireUsableCatalog(catalog, expectedCatalogName);
        catalogPolicy.requireDisplayTranslation(catalog);
    }

    public void requireUserFacingCatalog(TelemetryTypeCatalog catalog, String expectedCatalogName) {
        requireUsableCatalog(catalog, expectedCatalogName);
        catalogPolicy.requireMandatoryTranslations(catalog);
    }

    public TelemetryTypeTranslation selectTranslation(TelemetryTypeCatalog catalog, String locale) {
        Objects.requireNonNull(catalog, "Telemetry type catalog must not be null.");
        catalogPolicy.requireDisplayTranslation(catalog);

        return catalog.translationForLocale(locale)
                .or(catalog::defaultTranslation)
                .orElseThrow(() -> new IllegalStateException("Telemetry catalog has no available translation."));
    }
}
