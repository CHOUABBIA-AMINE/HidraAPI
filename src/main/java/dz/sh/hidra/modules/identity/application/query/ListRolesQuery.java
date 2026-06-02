/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListRolesQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.query
 *
 * @Description : Query record for listing identity roles.
 *
 */
package dz.sh.hidra.modules.identity.application.query;

import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;

/**
 * Query used to list identity roles.
 *
 * <p>Business role: carries pagination intent for reading roles available in the
 * identity module.</p>
 *
 * <p>Architecture role: application-layer query implementing the kernel {@link Query}
 * marker without framework, platform, persistence, or organization dependencies.</p>
 *
 * <p>Validation responsibility: rejects negative page indexes and invalid page sizes.</p>
 *
 * <p>Usage: pass this record to the future list-roles use case.</p>
 */
public record ListRolesQuery(
        int pageIndex,
        int pageSize
) implements Query {

    private static final int MAX_PAGE_SIZE = 200;

    public ListRolesQuery {
        if (pageIndex < 0) {
            throw new InvalidValueObjectException("pageIndex must not be negative.");
        }

        if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
            throw new InvalidValueObjectException("pageSize must be between 1 and 200.");
        }
    }

    public static ListRolesQuery firstPage(int pageSize) {
        return new ListRolesQuery(0, pageSize);
    }
}
