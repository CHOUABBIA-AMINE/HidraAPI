/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetTopologyCatalogTypeQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.query
 *
 * @Description : Application query for retrieving one topology catalog type by identifier.
 *
 */
package dz.sh.hidra.modules.topology.application.query;

import java.util.Locale;

import dz.sh.hidra.kernel.application.query.Query;

/**
 * Application query for retrieving one topology catalog type by identifier.
 *
 * <p>Business role:
 * Carries the catalog entry identifier and preferred locale for localized topology type lookup.
 *
 * <p>Architecture role:
 * This query is framework-independent and belongs to the application layer only.
 *
 * <p>Validation:
 * Identifier is mandatory. Locale is optional and normalized to a short lower-case language tag when
 * present.
 *
 * <p>Usage:
 * Use this query from API or other callers that need one catalog entry by id.
 *
 * @param id catalog entry identifier
 * @param locale preferred locale, for example en, fr, or ar
 */
public record GetTopologyCatalogTypeQuery(
        String id,
        String locale) implements Query {

    public GetTopologyCatalogTypeQuery {
        id = requireText(id, "Topology catalog type id");
        locale = normalizeOptionalLocale(locale);
    }

    private static String requireText(String value, String label) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(label + " must not be null or blank.");
        }
        return value.trim();
    }

    private static String normalizeOptionalLocale(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim().replace('_', '-').toLowerCase(Locale.ROOT);
    }
}
