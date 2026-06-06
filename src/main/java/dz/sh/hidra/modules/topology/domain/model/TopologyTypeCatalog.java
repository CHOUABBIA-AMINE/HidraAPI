/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyTypeCatalog
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Domain model for topology controlled vocabulary catalog entries.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.AggregateRoot;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Domain model for a topology controlled vocabulary catalog entry.
 *
 * <p>Business role:
 * This model represents multilingual configurable topology taxonomy entries such as product types,
 * facility types, topology node types, pipeline appurtenance types, valve types, equipment types, and
 * connection types.
 *
 * <p>Architecture role:
 * This is a transitional catalog model introduced before replacing enum-based asset fields. It does
 * not modify existing asset aggregates and does not expose REST or persistence behavior by itself.
 *
 * <p>Validation:
 * Catalog name, identifier, code, status, sort order, creation instant, and update instant are
 * mandatory. Translations are immutable after construction.
 */
public final class TopologyTypeCatalog implements AggregateRoot<String> {

    private static final String PREFIX = "topology_type_";

    private final String id;
    private final String catalogName;
    private final TopologyCode code;
    private final TopologyStatus status;
    private final int sortOrder;
    private final boolean systemDefined;
    private final List<TopologyTypeTranslation> translations;
    private final Instant createdAt;
    private final Instant updatedAt;

    private TopologyTypeCatalog(
            String id,
            String catalogName,
            TopologyCode code,
            TopologyStatus status,
            int sortOrder,
            boolean systemDefined,
            List<TopologyTypeTranslation> translations,
            Instant createdAt,
            Instant updatedAt) {

        this.id = requireText(id, "Topology type catalog id");
        this.catalogName = normalizeCatalogName(catalogName);
        this.code = Objects.requireNonNull(code, "Topology type catalog code must not be null.");
        this.status = Objects.requireNonNull(status, "Topology type catalog status must not be null.");
        this.sortOrder = requireNonNegative(sortOrder);
        this.systemDefined = systemDefined;
        this.translations = translations == null ? List.of() : List.copyOf(translations);
        this.createdAt = Objects.requireNonNull(createdAt, "Topology type catalog createdAt must not be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Topology type catalog updatedAt must not be null.");

        if (this.updatedAt.isBefore(this.createdAt)) {
            throw new BusinessRuleViolationException("Topology type catalog updatedAt must not be before createdAt.");
        }
    }

    /**
     * Creates a new active catalog entry with a generated identifier.
     *
     * @param catalogName catalog name, for example FACILITY_TYPE
     * @param code stable language-neutral code
     * @param sortOrder display ordering value
     * @param systemDefined whether the entry is system-defined
     * @param translations localized labels
     * @return created catalog entry
     */
    public static TopologyTypeCatalog create(
            String catalogName,
            TopologyCode code,
            int sortOrder,
            boolean systemDefined,
            List<TopologyTypeTranslation> translations) {

        Instant now = Instant.now();
        return new TopologyTypeCatalog(
                PREFIX + UUID.randomUUID(),
                catalogName,
                code,
                TopologyStatus.ACTIVE,
                sortOrder,
                systemDefined,
                translations,
                now,
                now);
    }

    /**
     * Restores an existing catalog entry from persistence.
     *
     * @param id catalog entry identifier
     * @param catalogName catalog name
     * @param code language-neutral code
     * @param status lifecycle status
     * @param sortOrder display ordering value
     * @param systemDefined whether the entry is system-defined
     * @param translations localized labels
     * @param createdAt creation instant
     * @param updatedAt update instant
     * @return restored catalog entry
     */
    public static TopologyTypeCatalog restore(
            String id,
            String catalogName,
            TopologyCode code,
            TopologyStatus status,
            int sortOrder,
            boolean systemDefined,
            List<TopologyTypeTranslation> translations,
            Instant createdAt,
            Instant updatedAt) {

        return new TopologyTypeCatalog(
                id,
                catalogName,
                code,
                status,
                sortOrder,
                systemDefined,
                translations,
                createdAt,
                updatedAt);
    }

    @Override
    public String id() {
        return id;
    }

    public String catalogName() {
        return catalogName;
    }

    public TopologyCode code() {
        return code;
    }

    public TopologyStatus status() {
        return status;
    }

    public int sortOrder() {
        return sortOrder;
    }

    public boolean systemDefined() {
        return systemDefined;
    }

    public List<TopologyTypeTranslation> translations() {
        return translations;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public boolean isActive() {
        return status == TopologyStatus.ACTIVE;
    }

    public Optional<TopologyTypeTranslation> translationForLocale(String locale) {
        if (locale == null || locale.isBlank()) {
            return Optional.empty();
        }

        String normalized = locale.trim().replace('_', '-').toLowerCase(Locale.ROOT);
        return translations.stream()
                .filter(translation -> translation.locale().equalsIgnoreCase(normalized))
                .findFirst();
    }

    private static String normalizeCatalogName(String value) {
        return requireText(value, "Topology type catalog name")
                .replace('-', '_')
                .replace(' ', '_')
                .toUpperCase(Locale.ROOT);
    }

    private static int requireNonNegative(int value) {
        if (value < 0) {
            throw new BusinessRuleViolationException("Topology type catalog sortOrder must be greater than or equal to zero.");
        }
        return value;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new BusinessRuleViolationException(fieldName + " must not be null or blank.");
        }
        return value.trim();
    }
}
