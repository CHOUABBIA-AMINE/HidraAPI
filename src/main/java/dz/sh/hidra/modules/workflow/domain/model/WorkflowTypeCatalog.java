/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTypeCatalog
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Domain model for workflow controlled vocabulary catalog entries.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.AggregateRoot;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCatalogId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;

/**
 * Domain model for a workflow controlled vocabulary catalog entry.
 *
 * <p>Business role:
 * Represents configurable workflow taxonomy entries such as workflow types, decision reasons,
 * priorities, target types, escalation reasons, and delegation reasons.
 *
 * <p>Architecture role:
 * This model is the catalog aggregate used by workflow reference value objects. Business taxonomy
 * concepts must be represented by this model and typed reference value objects, not Java enums.
 *
 * <p>Validation:
 * Catalog id, catalog name, language-neutral code, lifecycle flag, sort order, creation instant, and
 * update instant are mandatory. Translations are immutable after construction.
 */
public final class WorkflowTypeCatalog implements AggregateRoot<WorkflowCatalogId> {

    private final WorkflowCatalogId id;
    private final String catalogName;
    private final WorkflowCode code;
    private final boolean active;
    private final int sortOrder;
    private final boolean systemDefined;
    private final List<WorkflowTypeTranslation> translations;
    private final Instant createdAt;
    private final Instant updatedAt;

    private WorkflowTypeCatalog(
            WorkflowCatalogId id,
            String catalogName,
            WorkflowCode code,
            boolean active,
            int sortOrder,
            boolean systemDefined,
            List<WorkflowTypeTranslation> translations,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Workflow type catalog id must not be null.");
        this.catalogName = normalizeCatalogName(catalogName);
        this.code = Objects.requireNonNull(code, "Workflow type catalog code must not be null.");
        this.active = active;
        this.sortOrder = requireNonNegative(sortOrder);
        this.systemDefined = systemDefined;
        this.translations = translations == null ? List.of() : List.copyOf(translations);
        this.createdAt = Objects.requireNonNull(createdAt, "Workflow type catalog createdAt must not be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Workflow type catalog updatedAt must not be null.");

        if (this.updatedAt.isBefore(this.createdAt)) {
            throw new BusinessRuleViolationException("Workflow type catalog updatedAt must not be before createdAt.");
        }

        validateTranslationsBelongToThisCatalog();
    }

    /**
     * Creates a new active workflow catalog entry with a generated identifier.
     *
     * @param catalogName catalog family name, for example WORKFLOW_TYPE
     * @param code stable language-neutral code
     * @param sortOrder display ordering value
     * @param systemDefined whether this entry is system-defined
     * @param translations localized labels
     * @return created workflow catalog entry
     */
    public static WorkflowTypeCatalog create(
            String catalogName,
            WorkflowCode code,
            int sortOrder,
            boolean systemDefined,
            List<WorkflowTypeTranslation> translations) {

        Instant now = Instant.now();
        return new WorkflowTypeCatalog(
                WorkflowCatalogId.newId(),
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
     * Restores an existing workflow catalog entry from persistence.
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
     * @return restored workflow catalog entry
     */
    public static WorkflowTypeCatalog restore(
            WorkflowCatalogId id,
            String catalogName,
            WorkflowCode code,
            boolean active,
            int sortOrder,
            boolean systemDefined,
            List<WorkflowTypeTranslation> translations,
            Instant createdAt,
            Instant updatedAt) {

        return new WorkflowTypeCatalog(
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
    public WorkflowCatalogId id() {
        return id;
    }

    public String catalogName() {
        return catalogName;
    }

    public WorkflowCode code() {
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

    public List<WorkflowTypeTranslation> translations() {
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

    public Optional<WorkflowTypeTranslation> translationForLocale(String locale) {
        String normalizedLocale = normalizeLocale(locale);
        return translations.stream()
                .filter(translation -> translation.locale().equalsIgnoreCase(normalizedLocale))
                .findFirst();
    }

    public Optional<WorkflowTypeTranslation> defaultTranslation() {
        return translationForLocale("fr")
                .or(() -> translationForLocale("en"))
                .or(() -> translationForLocale("ar"))
                .or(() -> translations.stream().findFirst());
    }

    public WorkflowTypeCatalog activate() {
        if (active) {
            return this;
        }

        return withActive(true);
    }

    public WorkflowTypeCatalog deactivate() {
        if (!active) {
            return this;
        }

        if (systemDefined) {
            throw new BusinessRuleViolationException("System-defined workflow type catalog entries cannot be deactivated.");
        }

        return withActive(false);
    }

    private WorkflowTypeCatalog withActive(boolean newActive) {
        return new WorkflowTypeCatalog(
                id,
                catalogName,
                code,
                newActive,
                sortOrder,
                systemDefined,
                translations,
                createdAt,
                Instant.now());
    }

    private void validateTranslationsBelongToThisCatalog() {
        boolean hasForeignTranslation = translations.stream()
                .anyMatch(translation -> !id.equals(translation.typeId()));

        if (hasForeignTranslation) {
            throw new BusinessRuleViolationException("Workflow type catalog translations must belong to the same catalog entry.");
        }
    }

    private static String normalizeCatalogName(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("Workflow type catalog name must not be null or blank.");
        }

        String normalized = value.trim()
                .replace('-', '_')
                .replace(' ', '_')
                .toUpperCase(Locale.ROOT);

        if (normalized.length() < 2 || normalized.length() > 80) {
            throw new InvalidValueObjectException("Workflow type catalog name length must be between 2 and 80 characters.");
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
            throw new BusinessRuleViolationException("Workflow type catalog sortOrder must be greater than or equal to zero.");
        }
        return value;
    }
}
