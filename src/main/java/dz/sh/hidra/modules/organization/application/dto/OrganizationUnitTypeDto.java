/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitTypeDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.dto
 *
 * @Description : Application DTO exposing an organization unit type catalog entry with trilingual labels.
 *
 */
package dz.sh.hidra.modules.organization.application.dto;

import java.time.Instant;
import java.util.List;
import java.util.Locale;

import dz.sh.hidra.modules.organization.domain.model.OrganizationUnitTypeCatalog;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnitTypeTranslation;

/**
 * Application DTO exposing an organization unit type catalog entry with trilingual labels.
 *
 * <p>Business role:
 * Represents organization unit type reference data for organizational structure screens, reporting,
 * and catalog administration.
 *
 * <p>Architecture role:
 * This DTO flattens catalog translations into first-class Arabic, French, and English fields required
 * by HidraAPI multilingual conventions. Domain references keep only id and code.
 *
 * <p>Validation:
 * Catalog validation is performed by domain models and repository adapters before this DTO is built.
 */
public record OrganizationUnitTypeDto(
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

    public static OrganizationUnitTypeDto from(OrganizationUnitTypeCatalog catalog) {
        return new OrganizationUnitTypeDto(
                catalog.reference().id(),
                catalog.reference().code(),
                localizedName(catalog.translations(), "ar"),
                localizedName(catalog.translations(), "fr"),
                localizedName(catalog.translations(), "en"),
                localizedDescription(catalog.translations(), "ar"),
                localizedDescription(catalog.translations(), "fr"),
                localizedDescription(catalog.translations(), "en"),
                catalog.status(),
                catalog.sortOrder(),
                catalog.systemDefined(),
                catalog.createdAt(),
                catalog.updatedAt());
    }

    private static String localizedName(List<OrganizationUnitTypeTranslation> translations, String locale) {
        OrganizationUnitTypeTranslation translation = translationFor(translations, locale);
        return translation == null ? null : translation.name();
    }

    private static String localizedDescription(List<OrganizationUnitTypeTranslation> translations, String locale) {
        OrganizationUnitTypeTranslation translation = translationFor(translations, locale);
        return translation == null ? null : translation.description();
    }

    private static OrganizationUnitTypeTranslation translationFor(
            List<OrganizationUnitTypeTranslation> translations,
            String locale) {

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
