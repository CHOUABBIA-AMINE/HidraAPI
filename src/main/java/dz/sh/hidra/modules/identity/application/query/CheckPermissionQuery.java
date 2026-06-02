/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CheckPermissionQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.query
 *
 * @Description : Query record for checking whether an identity user has a permission.
 *
 */
package dz.sh.hidra.modules.identity.application.query;

import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.UserId;

/**
 * Query used to check whether a user has a permission.
 *
 * <p>Business role: identifies the user and permission involved in a business access
 * decision.</p>
 *
 * <p>Architecture role: application-layer query implementing the kernel {@link Query}
 * marker. It does not implement Spring Security authorization or platform access
 * handlers.</p>
 *
 * <p>Validation responsibility: rejects missing user identifier and permission code
 * values.</p>
 *
 * <p>Usage: pass this record to the future evaluate-permission use case.</p>
 */
public record CheckPermissionQuery(
        UserId userId,
        PermissionCode permissionCode
) implements Query {

    public CheckPermissionQuery {
        userId = requireNonNull(userId, "UserId");
        permissionCode = requireNonNull(permissionCode, "PermissionCode");
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
