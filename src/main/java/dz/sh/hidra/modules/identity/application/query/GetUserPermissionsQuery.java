/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetUserPermissionsQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.query
 *
 * @Description : Query record for retrieving effective permissions of an identity user.
 *
 */
package dz.sh.hidra.modules.identity.application.query;

import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.UserId;

/**
 * Query used to retrieve effective permissions for a user.
 *
 * <p>Business role: identifies the user whose effective permissions should be calculated
 * from assigned roles.</p>
 *
 * <p>Architecture role: application-layer query implementing the kernel {@link Query}
 * marker without depending on platform security current-principal extraction.</p>
 *
 * <p>Validation responsibility: rejects a missing user identifier.</p>
 *
 * <p>Usage: pass this record to the future get-user-permissions use case.</p>
 */
public record GetUserPermissionsQuery(UserId userId) implements Query {

    public GetUserPermissionsQuery {
        userId = requireNonNull(userId, "UserId");
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
