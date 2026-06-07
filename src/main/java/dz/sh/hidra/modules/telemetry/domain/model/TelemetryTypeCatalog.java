/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryTypeCatalog
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Domain model for telemetry controlled vocabulary catalog entries.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.AggregateRoot;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTypeCatalogId;

/**
 * Domain model for a telemetry controlled vocabulary catalog entry.
 *
 * <p>Business role:
 * Represents configurable telemetry taxonomy entries such as source types, device types, point
 * types, signal types, unit references, quality codes, protocols, aggregation methods, and binding
 * roles.
 *
 * <p>Architecture role:
 * This model is the catalog entity used by telemetry references. Business taxonomy concepts must be
 * represented by this model and typed reference value objects, not Java enums.
 *
 * <p>Validation:
 * Catalog id, catalog name, language-neutral code, lifecycle flag, sort order, creation instant, and
 * update instant are mandatory. Translations are immutable after construction.
 */
public final class TelemetryTypeCatalog implements AggregateRoot<TelemetryTypeCatalogId> {

    private final TelemetryTypeCatalogId id;
    private final String catalogName;
    private final TelemetryCode code;
    private final boolean active;
    private final int sortOrder;
    private final boolean systemDefined;
    private final List<TelemetryTypeTranslation> translations;
    private final Instant createdAt;
    private final Instant updatedAt;

    private TelemetryTypeCatalog(
            TelemetryTypeCatalogId id,
            String catalogName,
            TelemetryCode code,
            boolean active,
            int sortOrder,
            boolean systemDefined,
            List<TelemetryTypeTranslation> translations,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Telemetry type catalog id must not be null.");
        this.catalogName = normalizeCatalogName(catalogName);
        this.code = Objects.requireNonNull(code, "Telemetry type catalog code must not be null.");
        this.active = active;
        this.sortOrder = requireNonNegative(sortOrder);
        this.systemDefined = systemDefined;
        this.translations = translations == null ? List.of() : List.copyOf(translations);
        this.createdAt = Objects.requireNonNull(createdAt, "Telemetry type catalog createdAt must not be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Telemetry type catalog updatedAt must not be null.");

        if (this.updatedAt.isBefore(this.createdAt)) {
            throw new BusinessRuleViolationException("Telemetry type catalog updatedAt must not be before createdAt.");
        }

        validateTranslationsBelongToThisCatalog();
    }

    /**
     * Creates a new active telemetry catalog entry with a generated identifier.
     *
     * @param catalogName catalog family name, for example SOURCE_TYPE
     * @param code stable language-neutral code
     * @param sortOrder display ordering value
     * @param systemDefined whether this entry is system-defined
     * @param translations localized labels
     * @return created telemetry catalog entry
     */
    public static TelemetryTypeCatalog create(
            String catalogName,
            TelemetryCode code,
            int sortOrder,
            boolean systemDefined,
            List<TelemetryTypeTranslation> translations) {

        Instant now = Instant.now();
        return new TelemetryTypeCatalog(
                TelemetryTypeCatalogId.newId(),
                catalogName,
                code,
                true,
                sortOrder,
                systemDefined,
                translations,
                now,
                now);
    }

    /**
     * Restores an existing telemetry catalog entry from persistence.
     *
     * @param id catalog identifier
     * @param catalogName catalog family name
     * @param code stable language-neutral code
     * @param active whether the catalog entry is active
     * @param sortOrder display ordering value
     * @param systemDefined whether this entry is system-defined
     * @param translations localized labels
     * @param createdAt creation instant
     * @param updatedAt update instant
     * @return restored telemetry catalog entry
     */
    public static TelemetryTypeCatalog restore(
            TelemetryTypeCatalogId id,
            String catalogName,
            TelemetryCode code,
            boolean active,
            int sortOrder,
            boolean systemDefined,
            List<TelemetryTypeTranslation> translations,
            Instant createdAt,
            Instant updatedAt) {

        return new TelemetryTypeCatalog(
                id,
                catalogName,
                code,
                active,
                sortOrder,
                systemDefined,
                translations,
                createdAt,
                updatedAt);
    }

    @Override
    public TelemetryTypeCatalogId id() {
        return id;
    }

    public String catalogName() {
        return catalogName;
    }

    public TelemetryCode code() {
        return code;
    }

    public boolean active() {
        return active;
    }

    public int sortOrder() {
        return sortOrder;
    }

    public boolean systemDefined() {
        return systemDefined;
    }

    public List<TelemetryTypeTranslation> translations() {
        return translations;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public boolean matchesCatalog(String expectedCatalogName) {
        return catalogName.equals(normalizeCatalogName(expectedCatalogName));
    }

    public Optional<TelemetryTypeTranslation> translationForLocale(String locale) {
        String normalizedLocale = normalizeLocale(locale);
        return translations.stream()
                .filter(translation -> translation.locale().equalsIgnoreCase(normalizedLocale))
                .findFirst();
    }

    public Optional<TelemetryTypeTranslation> defaultTranslation() {
        return translationForLocale("fr")
                .or(() -> translationForLocale("en"))
                .or(() -> translationForLocale("ar"))
                .or(() -> translations.stream().findFirst());
    }

    private void validateTranslationsBelongToThisCatalog() {
        boolean hasForeignTranslation = translations.stream()
                .anyMatch(translation -> !id.equals(translation.typeId()));

        if (hasForeignTranslation) {
            throw new BusinessRuleViolationException("Telemetry type catalog translations must belong to the same catalog entry.");
        }
    }

    private static String normalizeCatalogName(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("Telemetry type catalog name must not be null or blank.");
        }

        String normalized = value.trim()
                .replace('-', '_')
                .replace(' ', '_')
                .toUpperCase(Locale.ROOT);

        if (normalized.length() < 2 || normalized.length() > 80) {
            throw new InvalidValueObjectException("Telemetry type catalog name length must be between 2 and 80 characters.");
        }

        return normalized;
    }

    private static String normalizeLocale(String value) {
        if (value == null || value.isBlank()) {
            return "fr";
        }

        String normalized = value.trim()
                .replace('_', '-')
                .toLowerCase(Locale.ROOT);

        int regionSeparator = normalized.indexOf('-');
        if (regionSeparator > 0) {
            normalized = normalized.substring(0, regionSeparator);
        }

        return normalized;
    }

    private static int requireNonNegative(int value) {
        if (value < 0) {
            throw new BusinessRuleViolationException("Telemetry type catalog sortOrder must be greater than or equal to zero.");
        }
        return value;
    }
}
