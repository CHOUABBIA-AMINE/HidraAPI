/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityApplicationMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.mapper
 *
 * @Description : Maps identity domain models to application DTOs.
 *
 */
package dz.sh.hidra.modules.identity.application.mapper;

import dz.sh.hidra.modules.identity.application.dto.UserSummaryDto;
import dz.sh.hidra.modules.identity.domain.model.User;

/**
 * Maps identity domain models to application DTOs.
 */
public final class IdentityApplicationMapper {

    private IdentityApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static UserSummaryDto toSummary(User user) {
        return new UserSummaryDto(
                user.id(),
                user.username(),
                user.emailAddress(),
                user.displayName(),
                user.userType(),
                user.status()
        );
    }
}
