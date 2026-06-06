/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResolveTopologyCatalogTypeQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.query
 *
 * @Description : Application query for resolving a topology catalog type by catalog name and code.
 *
 */
package dz.sh.hidra.modules.topology.application.query;

import java.util.Locale;
import java.util.Objects;

import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;

/**
 * Application query for resolving a topology catalog type by catalog name and code.
 *
 * <p>Business role:
 * Resolves a stable language-neutral type code into a catalog entry and localized label.
 *
 * <p>Architecture role:
 * This query is used by application services before create/update operations can replace enum-based
 * asset fields with catalog references.
 *
 * <p>Validation:
 * Catalog name, code, and active requirement flag are explicit. Locale is optional and normalized.
 * When {@code requireActive} is true, the application service rejects inactive catalog values.
 *
 * <p>Usage:
 * Use {@link #forCreation(String, TopologyCode, String)} when resolving type codes for create
 * commands.
 *
 * @param catalogName catalog name, for example FACILITY_TYPE
 * @param code language-neutral catalog code
 * @param locale preferred locale, for example en, fr, or ar
 * @param requireActive whether inactive catalog entries must be rejected
 */
public record ResolveTopologyCatalogTypeQuery(
        String catalogName,
        TopologyCode code,
        String locale,
        boolean requireActive) implements Query {

    public ResolveTopologyCatalogTypeQuery {
        catalogName = normalizeCatalogName(catalogName);
        code = Objects.requireNonNull(code, "Topology catalog code must not be null.");
        locale = normalizeOptionalLocale(locale);
    }

    public static ResolveTopologyCatalogTypeQuery forCreation(
            String catalogName,
            TopologyCode code,
            String locale) {

        return new ResolveTopologyCatalogTypeQuery(catalogName, code, locale, true);
    }

    public static ResolveTopologyCatalogTypeQuery forRead(
            String catalogName,
            TopologyCode code,
            String locale) {

        return new ResolveTopologyCatalogTypeQuery(catalogName, code, locale, false);
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
