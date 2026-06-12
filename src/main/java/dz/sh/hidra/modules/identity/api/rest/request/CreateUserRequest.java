/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateUserRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.request
 *
 * @Description : REST request for creating an identity user.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.request;

import dz.sh.hidra.modules.identity.domain.value.UserType;

/**
 * REST request for creating an identity user.
 *
 * @param username username
 * @param emailAddress email address
 * @param displayName display name
 * @param userType user type
 * @param employeeReferenceId optional organization employee reference
 */
public record CreateUserRequest(
        String username,
        String emailAddress,
        String displayName,
        UserType userType,
        String employeeReferenceId
) {
}
