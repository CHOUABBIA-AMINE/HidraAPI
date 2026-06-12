/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserSecurityProjection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.projection
 *
 * @Description : Read projection for identity user security state.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.projection;

import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.UserType;

/**
 * Read projection for identity user security state.
 *
 * @param userId user identifier
 * @param username username
 * @param userType user type
 * @param status user status
 */
public record UserSecurityProjection(
        String userId,
        String username,
        UserType userType,
        UserStatus status
) {
}
