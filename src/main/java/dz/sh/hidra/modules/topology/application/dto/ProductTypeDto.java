/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductTypeDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Application DTO exposing a product type catalog entry with trilingual labels.
 *
 */
package dz.sh.hidra.modules.topology.application.dto;

import java.time.Instant;
import java.util.List;
import java.util.Locale;

/**
 * Application DTO exposing a product type catalog entry with trilingual labels.
 *
 * <p>Business role:
 * Represents hydrocarbon product type reference data for API callers and UI catalog screens.
 *
 * <p>Architecture role:
 * This DTO is a product-specific projection of the generic topology catalog DTO. It flattens locale
 * translations into first-class Arabic, French, and English fields required by HidraAPI multilingual
 * conventions.
 *
 * <p>Validation:
 * Catalog validation is performed by domain models and repository adapters before this DTO is built.
 */
public record ProductTypeDto(
        String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String descriptionAr,
        String descriptionFr,
        String descriptionEn,
        String status,
        int sortOrder,
        boolean systemDefined,
        Instant createdAt,
        Instant updatedAt) {

    public static ProductTypeDto from(TopologyCatalogDto catalogDto) {
        return new ProductTypeDto(
                catalogDto.id(),
                catalogDto.code(),
                localizedName(catalogDto.translations(), "ar"),
                localizedName(catalogDto.translations(), "fr"),
                localizedName(catalogDto.translations(), "en"),
                localizedDescription(catalogDto.translations(), "ar"),
                localizedDescription(catalogDto.translations(), "fr"),
                localizedDescription(catalogDto.translations(), "en"),
                catalogDto.status(),
                catalogDto.sortOrder(),
                catalogDto.systemDefined(),
                catalogDto.createdAt(),
                catalogDto.updatedAt());
    }

    private static String localizedName(List<TopologyCatalogTranslationDto> translations, String locale) {
        return translationFor(translations, locale) == null ? null : translationFor(translations, locale).name();
    }

    private static String localizedDescription(List<TopologyCatalogTranslationDto> translations, String locale) {
        return translationFor(translations, locale) == null ? null : translationFor(translations, locale).description();
    }

    private static TopologyCatalogTranslationDto translationFor(List<TopologyCatalogTranslationDto> translations, String locale) {
        if (translations == null || translations.isEmpty()) {
            return null;
        }
        String normalizedLocale = locale.toLowerCase(Locale.ROOT);
        return translations.stream()
                .filter(translation -> normalizedLocale.equals(translation.locale().toLowerCase(Locale.ROOT)))
                .findFirst()
                .orElse(null);
    }
}
