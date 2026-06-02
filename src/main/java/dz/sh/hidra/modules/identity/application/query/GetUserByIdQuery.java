/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetUserByIdQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.query
 *
 * @Description : Query record for retrieving an identity user by identifier.
 *
 */
package dz.sh.hidra.modules.identity.application.query;

import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.UserId;

/**
 * Query used to retrieve an identity user by identifier.
 *
 * <p>Business role: identifies the user to be loaded for read-only identity use cases.</p>
 *
 * <p>Architecture role: application-layer query implementing the kernel {@link Query}
 * marker without importing REST, persistence, platform, or organization types.</p>
 *
 * <p>Validation responsibility: rejects a missing user identifier.</p>
 *
 * <p>Usage: pass this record to the future get-user-by-id use case.</p>
 */
public record GetUserByIdQuery(UserId userId) implements Query {

    public GetUserByIdQuery {
        userId = requireNonNull(userId, "UserId");
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
