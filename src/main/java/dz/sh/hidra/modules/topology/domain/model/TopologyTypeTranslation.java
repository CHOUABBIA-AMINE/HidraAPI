/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyTypeTranslation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Localized label and description for a topology controlled vocabulary entry.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;

/**
 * Localized translation for a topology controlled vocabulary entry.
 *
 * <p>Business role:
 * This value object carries the multilingual label and optional description for configurable topology
 * type catalogs such as facility types, node types, valve types, product types, equipment types, and
 * connection types.
 *
 * <p>Architecture role:
 * This is a pure topology domain type. It must not depend on Spring, JPA, REST, identity,
 * organization, platform, measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * The translation identifier, catalog entry identifier, locale, name, creation instant, and update
 * instant are mandatory. Locale is normalized to lower-case and accepts language tags such as
 * <code>en</code>, <code>fr</code>, or <code>ar</code>.
 *
 * <p>Usage:
 * Use this type to expose translated labels from catalog application services and REST responses.
 *
 * @param id translation identifier
 * @param typeId catalog entry identifier
 * @param locale normalized locale tag
 * @param name localized display name
 * @param description optional localized description
 * @param createdAt creation instant
 * @param updatedAt update instant
 */
public record TopologyTypeTranslation(
        String id,
        String typeId,
        String locale,
        TopologyName name,
        String description,
        Instant createdAt,
        Instant updatedAt) implements ValueObject {

    private static final String PREFIX = "topology_type_translation_";
    private static final Pattern LOCALE_PATTERN = Pattern.compile("^[a-z]{2}([_-][a-z0-9]{2,8})?$", Pattern.CASE_INSENSITIVE);

    public TopologyTypeTranslation {
        id = requireText(id, "TopologyTypeTranslation id");
        typeId = requireText(typeId, "TopologyTypeTranslation typeId");
        locale = normalizeLocale(locale);
        name = java.util.Objects.requireNonNull(name, "TopologyTypeTranslation name must not be null.");
        description = normalizeOptional(description);
        createdAt = java.util.Objects.requireNonNull(createdAt, "TopologyTypeTranslation createdAt must not be null.");
        updatedAt = java.util.Objects.requireNonNull(updatedAt, "TopologyTypeTranslation updatedAt must not be null.");

        if (updatedAt.isBefore(createdAt)) {
            throw new InvalidValueObjectException("TopologyTypeTranslation updatedAt must not be before createdAt.");
        }
    }

    /**
     * Creates a new translation with a generated identifier.
     *
     * @param typeId catalog entry identifier
     * @param locale locale tag
     * @param name localized name
     * @param description optional localized description
     * @return translation
     */
    public static TopologyTypeTranslation create(
            String typeId,
            String locale,
            TopologyName name,
            String description) {

        Instant now = Instant.now();
        return new TopologyTypeTranslation(
                PREFIX + UUID.randomUUID(),
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
    public static TopologyTypeTranslation restore(
            String id,
            String typeId,
            String locale,
            TopologyName name,
            String description,
            Instant createdAt,
            Instant updatedAt) {

        return new TopologyTypeTranslation(id, typeId, locale, name, description, createdAt, updatedAt);
    }

    private static String normalizeLocale(String value) {
        String normalized = requireText(value, "TopologyTypeTranslation locale")
                .replace('_', '-')
                .toLowerCase(Locale.ROOT);

        if (!LOCALE_PATTERN.matcher(normalized).matches()) {
            throw new InvalidValueObjectException("TopologyTypeTranslation locale must be a valid short language tag.");
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
        return value.trim();
    }
}
