/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCatalogPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.policy
 *
 * @Description : Domain policy for telemetry controlled vocabulary catalogs.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.policy;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryTypeCatalog;

/**
 * Domain policy for telemetry controlled vocabulary catalogs.
 *
 * <p>Business role:
 * Verifies telemetry catalog entries are usable by domain models and create/register workflows.
 *
 * <p>Architecture role:
 * Pure domain policy. It does not read repositories, invoke REST, use JPA, or import external module
 * implementation classes.
 */
public final class TelemetryCatalogPolicy {

    private static final Set<String> REQUIRED_LOCALES = Set.of("fr", "ar", "en");

    /**
     * Ensures the catalog entry can be used by a create/register command.
     *
     * @param catalog catalog entry to validate
     * @param expectedCatalogName expected catalog family
     */
    public void requireUsableCatalog(TelemetryTypeCatalog catalog, String expectedCatalogName) {
        Objects.requireNonNull(catalog, "Telemetry catalog must not be null.");
        requireExpectedCatalog(catalog, expectedCatalogName);
        requireActive(catalog);
    }

    /**
     * Ensures the catalog entry belongs to the expected catalog family.
     *
     * @param catalog catalog entry to validate
     * @param expectedCatalogName expected catalog family
     */
    public void requireExpectedCatalog(TelemetryTypeCatalog catalog, String expectedCatalogName) {
        Objects.requireNonNull(catalog, "Telemetry catalog must not be null.");

        if (!catalog.matchesCatalog(expectedCatalogName)) {
            throw new BusinessRuleViolationException(
                    "Telemetry catalog entry " + catalog.code().value()
                            + " does not belong to expected catalog " + normalizeCatalogName(expectedCatalogName) + ".");
        }
    }

    /**
     * Ensures the catalog entry is active.
     *
     * @param catalog catalog entry to validate
     */
    public void requireActive(TelemetryTypeCatalog catalog) {
        Objects.requireNonNull(catalog, "Telemetry catalog must not be null.");

        if (!catalog.active()) {
            throw new BusinessRuleViolationException(
                    "Inactive telemetry catalog entry cannot be used: " + catalog.code().value() + ".");
        }
    }

    /**
     * Ensures the catalog entry has at least one display translation.
     *
     * @param catalog catalog entry to validate
     */
    public void requireDisplayTranslation(TelemetryTypeCatalog catalog) {
        Objects.requireNonNull(catalog, "Telemetry catalog must not be null.");

        if (catalog.defaultTranslation().isEmpty()) {
            throw new BusinessRuleViolationException(
                    "Telemetry catalog entry requires at least one localized display translation: "
                            + catalog.code().value() + ".");
        }
    }

    /**
     * Ensures the catalog entry is ready for user-facing APIs in all mandatory languages.
     *
     * @param catalog catalog entry to validate
     */
    public void requireMandatoryTranslations(TelemetryTypeCatalog catalog) {
        Objects.requireNonNull(catalog, "Telemetry catalog must not be null.");

        Set<String> existingLocales = catalog.translations().stream()
                .map(translation -> normalizeLocale(translation.locale()))
                .collect(java.util.stream.Collectors.toSet());

        if (!existingLocales.containsAll(REQUIRED_LOCALES)) {
            throw new BusinessRuleViolationException(
                    "Telemetry catalog entry requires fr, ar, and en translations: " + catalog.code().value() + ".");
        }
    }

    private static String normalizeCatalogName(String value) {
        if (value == null || value.isBlank()) {
            throw new BusinessRuleViolationException("Expected telemetry catalog name must not be null or blank.");
        }

        return value.trim()
                .replace('-', '_')
                .replace(' ', '_')
                .toUpperCase(Locale.ROOT);
    }

    private static String normalizeLocale(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }

        String normalized = value.trim()
                .replace('_', '-')
                .toLowerCase(Locale.ROOT);

        int separator = normalized.indexOf('-');
        if (separator > 0) {
            normalized = normalized.substring(0, separator);
        }

        return normalized;
    }
}
