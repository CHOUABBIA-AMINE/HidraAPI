/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTypeTranslation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Localized label and description for a workflow controlled vocabulary entry.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import java.time.Instant;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCatalogId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCatalogTranslationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowName;

/**
 * Localized translation for a workflow controlled vocabulary entry.
 *
 * <p>Business role:
 * Carries multilingual labels and descriptions for configurable workflow catalog entries.
 *
 * <p>Architecture role:
 * This is a pure workflow domain value. It must not depend on Spring, JPA, REST, telemetry,
 * topology, organization, identity, planning, monitoring, incidents, audit, integration, analytics,
 * reporting, or notification implementation packages.
 *
 * <p>Validation:
 * The translation id, catalog entry id, locale, name, creation instant, and update instant are
 * mandatory. Locale is normalized to short language tags such as <code>fr</code>, <code>ar</code>,
 * or <code>en</code>.
 *
 * @param id translation identifier
 * @param typeId catalog entry identifier
 * @param locale normalized locale code
 * @param name localized display name
 * @param description optional localized description
 * @param createdAt creation instant
 * @param updatedAt update instant
 */
public record WorkflowTypeTranslation(
        WorkflowCatalogTranslationId id,
        WorkflowCatalogId typeId,
        String locale,
        WorkflowName name,
        String description,
        Instant createdAt,
        Instant updatedAt) implements ValueObject {

    private static final Pattern LOCALE_PATTERN = Pattern.compile("^[a-z]{2}([_-][a-z0-9]{2,8})?$", Pattern.CASE_INSENSITIVE);

    public WorkflowTypeTranslation {
        id = Objects.requireNonNull(id, "WorkflowTypeTranslation id must not be null.");
        typeId = Objects.requireNonNull(typeId, "WorkflowTypeTranslation typeId must not be null.");
        locale = normalizeLocale(locale);
        name = Objects.requireNonNull(name, "WorkflowTypeTranslation name must not be null.");
        description = normalizeOptional(description);
        createdAt = Objects.requireNonNull(createdAt, "WorkflowTypeTranslation createdAt must not be null.");
        updatedAt = Objects.requireNonNull(updatedAt, "WorkflowTypeTranslation updatedAt must not be null.");

        if (updatedAt.isBefore(createdAt)) {
            throw new InvalidValueObjectException("WorkflowTypeTranslation updatedAt must not be before createdAt.");
        }
    }

    /**
     * Creates a new translation with a generated identifier.
     *
     * @param typeId catalog entry identifier
     * @param locale locale tag
     * @param name localized name
     * @param description optional localized description
     * @return created translation
     */
    public static WorkflowTypeTranslation create(
            WorkflowCatalogId typeId,
            String locale,
            WorkflowName name,
            String description) {

        Instant now = Instant.now();
        return new WorkflowTypeTranslation(
                WorkflowCatalogTranslationId.newId(),
                typeId,
                locale,
                name,
                description,
                now,
                now);
    }

    /**
     * Restores an existing persisted translation.
     *
     * @param id translation identifier
     * @param typeId catalog entry identifier
     * @param locale locale tag
     * @param name localized name
     * @param description optional localized description
     * @param createdAt creation instant
     * @param updatedAt update instant
     * @return restored translation
     */
    public static WorkflowTypeTranslation restore(
            WorkflowCatalogTranslationId id,
            WorkflowCatalogId typeId,
            String locale,
            WorkflowName name,
            String description,
            Instant createdAt,
            Instant updatedAt) {

        return new WorkflowTypeTranslation(id, typeId, locale, name, description, createdAt, updatedAt);
    }

    public boolean isFrench() {
        return "fr".equals(locale);
    }

    public boolean isArabic() {
        return "ar".equals(locale);
    }

    public boolean isEnglish() {
        return "en".equals(locale);
    }

    private static String normalizeLocale(String value) {
        String normalized = requireText(value, "WorkflowTypeTranslation locale")
                .replace('_', '-')
                .toLowerCase(Locale.ROOT);

        int regionSeparator = normalized.indexOf('-');
        if (regionSeparator > 0) {
            normalized = normalized.substring(0, regionSeparator);
        }

        if (!LOCALE_PATTERN.matcher(normalized).matches()) {
            throw new InvalidValueObjectException("WorkflowTypeTranslation locale must be a valid short language tag.");
        }

        return normalized;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(fieldName + " must not be null or blank.");
        }
        return value.trim();
    }

    private static String normalizeOptional(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();

        if (normalized.length() > 500) {
            throw new InvalidValueObjectException("WorkflowTypeTranslation description length must not exceed 500 characters.");
        }

        return normalized;
    }
}
