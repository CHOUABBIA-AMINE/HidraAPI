/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListPermissionsQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.query
 *
 * @Description : Query record for listing identity permissions.
 *
 */
package dz.sh.hidra.modules.identity.application.query;

import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;

/**
 * Query used to list identity permissions.
 *
 * <p>Business role: carries pagination intent for reading the permission catalog.</p>
 *
 * <p>Architecture role: application-layer query implementing the kernel {@link Query}
 * marker without persistence, REST, platform, or organization dependencies.</p>
 *
 * <p>Validation responsibility: rejects negative page indexes and invalid page sizes.</p>
 *
 * <p>Usage: pass this record to the future list-permissions use case.</p>
 */
public record ListPermissionsQuery(
        int pageIndex,
        int pageSize
) implements Query {

    private static final int MAX_PAGE_SIZE = 200;

    public ListPermissionsQuery {
        if (pageIndex < 0) {
            throw new InvalidValueObjectException("pageIndex must not be negative.");
        }

        if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
            throw new InvalidValueObjectException("pageSize must be between 1 and 200.");
        }
    }

    public static ListPermissionsQuery firstPage(int pageSize) {
        return new ListPermissionsQuery(0, pageSize);
    }
}
