/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyCatalogDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Application DTO exposing a topology controlled vocabulary catalog entry.
 *
 */
package dz.sh.hidra.modules.topology.application.dto;

import java.time.Instant;
import java.util.List;

/**
 * Application DTO exposing a topology controlled vocabulary catalog entry.
 *
 * <p>Business role:
 * Represents multilingual configurable topology type data such as facility types, product types,
 * node types, appurtenance types, valve types, equipment types, and connection types.
 *
 * <p>Architecture role:
 * This DTO is returned by application use cases and can be consumed by REST or other callers without
 * exposing domain models or persistence entities.
 *
 * <p>Validation:
 * Catalog values are validated by the domain model. The localized fields are resolved by the
 * application service using requested locale, fallback locale, and available translations.
 *
 * <p>Usage:
 * Use this DTO when callers need either a resolved localized label or all available translations for
 * a topology type catalog entry.
 *
 * @param id catalog entry identifier
 * @param catalogName catalog name, for example FACILITY_TYPE
 * @param code language-neutral catalog code
 * @param status catalog entry lifecycle status
 * @param sortOrder display ordering value
 * @param systemDefined whether the entry is system-defined
 * @param locale resolved locale returned to the caller
 * @param localizedName localized display name for resolved locale
 * @param localizedDescription optional localized description for resolved locale
 * @param translations all available translations
 * @param createdAt creation instant
 * @param updatedAt update instant
 */
public record TopologyCatalogDto(
        String id,
        String catalogName,
        String code,
        String status,
        int sortOrder,
        boolean systemDefined,
        String locale,
        String localizedName,
        String localizedDescription,
        List<TopologyCatalogTranslationDto> translations,
        Instant createdAt,
        Instant updatedAt) {

    public TopologyCatalogDto {
        translations = translations == null ? List.of() : List.copyOf(translations);
    }
}
