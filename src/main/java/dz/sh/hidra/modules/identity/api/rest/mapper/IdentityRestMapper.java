/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.mapper
 *
 * @Description : Maps identity REST models to application models.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.mapper;

import dz.sh.hidra.modules.identity.api.rest.request.CreateUserRequest;
import dz.sh.hidra.modules.identity.api.rest.request.EvaluatePermissionRequest;
import dz.sh.hidra.modules.identity.api.rest.response.PermissionDecisionResponse;
import dz.sh.hidra.modules.identity.api.rest.response.UserResponse;
import dz.sh.hidra.modules.identity.application.command.CreateUserCommand;
import dz.sh.hidra.modules.identity.application.dto.PermissionDecisionDto;
import dz.sh.hidra.modules.identity.application.dto.UserSummaryDto;
import dz.sh.hidra.modules.identity.application.query.EvaluatePermissionQuery;

/**
 * Maps identity REST models to application models.
 */
public final class IdentityRestMapper {

    private IdentityRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateUserCommand toCommand(CreateUserRequest request) {
        return new CreateUserCommand(
                request.username(),
                request.emailAddress(),
                request.displayName(),
                request.userType(),
                request.employeeReferenceId()
        );
    }

    public static EvaluatePermissionQuery toQuery(EvaluatePermissionRequest request) {
        return new EvaluatePermissionQuery(
                request.userId(),
                request.permissionCode(),
                request.resourceType(),
                request.resourceReferenceId(),
                request.scope()
        );
    }

    public static UserResponse toResponse(UserSummaryDto dto) {
        return new UserResponse(
                dto.id(),
                dto.username(),
                dto.emailAddress(),
                dto.displayName(),
                dto.userType(),
                dto.status()
        );
    }

    public static PermissionDecisionResponse toResponse(PermissionDecisionDto dto) {
        return new PermissionDecisionResponse(
                dto.permitted(),
                dto.decision(),
                dto.reasonCode(),
                dto.reasonMessage(),
                dto.evaluatedAt()
        );
    }
}
