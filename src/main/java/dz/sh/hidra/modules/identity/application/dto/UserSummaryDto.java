/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.dto
 *
 * @Description : DTO containing public identity user summary data.
 *
 */
package dz.sh.hidra.modules.identity.application.dto;

import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.UserType;

/**
 * Public identity user summary.
 *
 * @param id user identifier
 * @param username username
 * @param emailAddress email address
 * @param displayName display name
 * @param userType user type
 * @param status user status
 */
public record UserSummaryDto(
        String id,
        String username,
        String emailAddress,
        String displayName,
        UserType userType,
        UserStatus status
) {
}
