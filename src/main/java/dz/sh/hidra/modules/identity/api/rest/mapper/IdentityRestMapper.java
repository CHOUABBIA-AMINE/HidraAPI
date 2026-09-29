/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
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

import dz.sh.hidra.modules.identity.api.rest.request.AuthorizationScopeRequest;
import dz.sh.hidra.modules.identity.api.rest.request.CreateUserRequest;
import dz.sh.hidra.modules.identity.api.rest.request.EvaluatePermissionRequest;
import dz.sh.hidra.modules.identity.api.rest.response.PermissionDecisionResponse;
import dz.sh.hidra.modules.identity.api.rest.response.UserResponse;
import dz.sh.hidra.modules.identity.application.command.CreateUserCommand;
import dz.sh.hidra.modules.identity.application.dto.PermissionDecisionDto;
import dz.sh.hidra.modules.identity.application.dto.UserSummaryDto;
import dz.sh.hidra.modules.identity.application.query.EvaluatePermissionQuery;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import java.util.Objects;

/**
 * Maps identity REST models to application models.
 */
public final class IdentityRestMapper {

    private static final IdentityGeneratedRestMapper GENERATED = IdentityGeneratedRestMapper.INSTANCE;

    private IdentityRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateUserCommand toCommand(CreateUserRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateUserRequest must not be null."));
    }

    public static EvaluatePermissionQuery toQuery(EvaluatePermissionRequest request) {
        Objects.requireNonNull(request, "EvaluatePermissionRequest must not be null.");
        return new EvaluatePermissionQuery(
                request.userId(),
                request.permissionCode(),
                request.resourceType(),
                request.resourceReferenceId(),
                toDomainScope(request.scope())
        );
    }

    public static UserResponse toResponse(UserSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "UserSummaryDto must not be null."));
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

    private static AuthorizationScope toDomainScope(AuthorizationScopeRequest scope) {
        if (scope == null) {
            return null;
        }
        return new AuthorizationScope(
                scope.scopeType(),
                scope.scopeReferenceId(),
                scope.scopeCodeSnapshot()
        );
    }
}
