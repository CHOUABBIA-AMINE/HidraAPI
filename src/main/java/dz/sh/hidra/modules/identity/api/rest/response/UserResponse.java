/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.response
 *
 * @Description : REST response for identity user summary.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.response;

import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.UserType;

/**
 * REST response for identity user summary.
 *
 * @param id user identifier
 * @param username username
 * @param emailAddress email address
 * @param displayName display name
 * @param userType user type
 * @param status user status
 */
public record UserResponse(
        String id,
        String username,
        String emailAddress,
        String displayName,
        UserType userType,
        UserStatus status
) {
}
