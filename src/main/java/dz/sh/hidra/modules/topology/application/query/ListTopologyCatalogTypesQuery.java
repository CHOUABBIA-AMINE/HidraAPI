/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListTopologyCatalogTypesQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.query
 *
 * @Description : Application query for listing topology catalog types.
 *
 */
package dz.sh.hidra.modules.topology.application.query;

import java.util.Locale;
import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Application query for listing topology catalog types.
 *
 * <p>Business role:
 * Lists configurable topology type catalog entries by catalog name, status, and locale.
 *
 * <p>Architecture role:
 * This query belongs to the application layer and is consumed by topology catalog use cases and
 * outbound repository ports.
 *
 * <p>Validation:
 * Catalog name and page request are mandatory. Locale and status are optional.
 *
 * <p>Usage:
 * Use this query when callers need paged catalog values for a type family such as FACILITY_TYPE.
 *
 * @param catalogName catalog name, for example FACILITY_TYPE
 * @param locale preferred locale, for example en, fr, or ar
 * @param status optional lifecycle status filter
 * @param pageRequest pagination request
 */
public record ListTopologyCatalogTypesQuery(
        String catalogName,
        String locale,
        TopologyStatus status,
        PageRequest pageRequest) implements Query {

    public ListTopologyCatalogTypesQuery {
        catalogName = normalizeCatalogName(catalogName);
        locale = normalizeOptionalLocale(locale);
        Objects.requireNonNull(pageRequest, "Page request must not be null.");
    }

    public static ListTopologyCatalogTypesQuery all(String catalogName, String locale, PageRequest pageRequest) {
        return new ListTopologyCatalogTypesQuery(catalogName, locale, null, pageRequest);
    }

    private static String normalizeCatalogName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Topology catalog name must not be null or blank.");
        }
        return value.trim().replace('-', '_').replace(' ', '_').toUpperCase(Locale.ROOT);
    }

    private static String normalizeOptionalLocale(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim().replace('_', '-').toLowerCase(Locale.ROOT);
    }
}
