/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SearchUsersQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.query
 *
 * @Description : Query record for searching identity users.
 *
 */
package dz.sh.hidra.modules.identity.application.query;

import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;

/**
 * Query used to search identity users.
 *
 * <p>Business role: carries optional user search filters and pagination intent for
 * identity administration screens and use cases.</p>
 *
 * <p>Architecture role: application-layer query implementing the kernel {@link Query}
 * marker. It avoids REST DTOs, persistence entities, platform security classes, and
 * organization module dependencies.</p>
 *
 * <p>Validation responsibility: normalizes blank filter strings to {@code null}, rejects
 * negative page indexes, and rejects invalid page sizes.</p>
 *
 * <p>Usage: pass this record to the future search-users use case.</p>
 */
public record SearchUsersQuery(
        String usernameContains,
        String emailContains,
        UserStatus status,
        int pageIndex,
        int pageSize
) implements Query {

    private static final int MAX_PAGE_SIZE = 200;

    public SearchUsersQuery {
        usernameContains = normalize(usernameContains);
        emailContains = normalize(emailContains);

        if (pageIndex < 0) {
            throw new InvalidValueObjectException("pageIndex must not be negative.");
        }

        if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
            throw new InvalidValueObjectException("pageSize must be between 1 and 200.");
        }
    }

    public static SearchUsersQuery firstPage(int pageSize) {
        return new SearchUsersQuery(null, null, null, 0, pageSize);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }
}
