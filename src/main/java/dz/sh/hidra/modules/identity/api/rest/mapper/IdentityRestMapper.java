/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.mapper
 *
 * @Description : Maps identity REST contracts to application commands and responses.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.mapper;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.api.rest.request.ActivateUserRequest;
import dz.sh.hidra.modules.identity.api.rest.request.AssignRoleToUserRequest;
import dz.sh.hidra.modules.identity.api.rest.request.CheckPermissionRequest;
import dz.sh.hidra.modules.identity.api.rest.request.CreateRoleRequest;
import dz.sh.hidra.modules.identity.api.rest.request.GrantPermissionToRoleRequest;
import dz.sh.hidra.modules.identity.api.rest.request.RegisterUserRequest;
import dz.sh.hidra.modules.identity.api.rest.response.PermissionDecisionResponse;
import dz.sh.hidra.modules.identity.api.rest.response.PermissionResponse;
import dz.sh.hidra.modules.identity.api.rest.response.RoleResponse;
import dz.sh.hidra.modules.identity.api.rest.response.UserResponse;
import dz.sh.hidra.modules.identity.application.command.ActivateUserCommand;
import dz.sh.hidra.modules.identity.application.command.AssignRoleToUserCommand;
import dz.sh.hidra.modules.identity.application.command.CreateRoleCommand;
import dz.sh.hidra.modules.identity.application.command.GrantPermissionToRoleCommand;
import dz.sh.hidra.modules.identity.application.command.RegisterUserCommand;
import dz.sh.hidra.modules.identity.application.command.RevokeRoleFromUserCommand;
import dz.sh.hidra.modules.identity.application.command.SuspendUserCommand;
import dz.sh.hidra.modules.identity.application.dto.PermissionDecisionDto;
import dz.sh.hidra.modules.identity.application.dto.PermissionDto;
import dz.sh.hidra.modules.identity.application.dto.RoleDto;
import dz.sh.hidra.modules.identity.application.dto.UserDto;
import dz.sh.hidra.modules.identity.application.query.CheckPermissionQuery;
import dz.sh.hidra.modules.identity.application.query.GetUserPermissionsQuery;
import dz.sh.hidra.modules.identity.domain.value.EmailAddress;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.RoleName;
import dz.sh.hidra.modules.identity.domain.value.UserId;
import dz.sh.hidra.modules.identity.domain.value.Username;

import java.util.List;
import java.util.Objects;

/**
 * Maps identity REST contracts to application commands and responses.
 *
 * <p>Business role: translates API payloads into identity use-case inputs and
 * application outputs into REST responses.</p>
 *
 * <p>Architecture role: API-layer mapper. It depends on application commands, queries,
 * DTOs, and domain value objects only; it does not access repositories, JPA entities,
 * platform security classes, or organization models.</p>
 *
 * <p>Validation responsibility: rejects null objects and ensures route/body identifiers
 * match for endpoints that carry both.</p>
 *
 * <p>Usage: injected into identity REST controllers.</p>
 */
public final class IdentityRestMapper {

    public RegisterUserCommand toRegisterUserCommand(RegisterUserRequest request) {
        RegisterUserRequest requiredRequest = requireNonNull(request, "RegisterUserRequest");

        return new RegisterUserCommand(
                Username.of(requiredRequest.username()),
                EmailAddress.of(requiredRequest.emailAddress())
        );
    }

    public ActivateUserCommand toActivateUserCommand(String routeUserId, ActivateUserRequest request) {
        ActivateUserRequest requiredRequest = requireNonNull(request, "ActivateUserRequest");
        requireMatchingIdentifier(routeUserId, requiredRequest.userId(), "userId");

        return new ActivateUserCommand(UserId.of(routeUserId));
    }

    public SuspendUserCommand toSuspendUserCommand(String routeUserId) {
        return new SuspendUserCommand(UserId.of(routeUserId));
    }

    public AssignRoleToUserCommand toAssignRoleToUserCommand(
            String routeUserId,
            AssignRoleToUserRequest request
    ) {
        AssignRoleToUserRequest requiredRequest = requireNonNull(request, "AssignRoleToUserRequest");

        return new AssignRoleToUserCommand(
                UserId.of(routeUserId),
                RoleId.of(requiredRequest.roleId())
        );
    }

    public RevokeRoleFromUserCommand toRevokeRoleFromUserCommand(String routeUserId, String routeRoleId) {
        return new RevokeRoleFromUserCommand(UserId.of(routeUserId), RoleId.of(routeRoleId));
    }

    public CreateRoleCommand toCreateRoleCommand(CreateRoleRequest request) {
        CreateRoleRequest requiredRequest = requireNonNull(request, "CreateRoleRequest");

        return new CreateRoleCommand(
                RoleCode.of(requiredRequest.roleCode()),
                RoleName.of(requiredRequest.roleName())
        );
    }

    public GrantPermissionToRoleCommand toGrantPermissionToRoleCommand(
            String routeRoleId,
            GrantPermissionToRoleRequest request
    ) {
        GrantPermissionToRoleRequest requiredRequest = requireNonNull(
                request,
                "GrantPermissionToRoleRequest"
        );

        return new GrantPermissionToRoleCommand(
                RoleId.of(routeRoleId),
                PermissionCode.of(requiredRequest.permissionCode())
        );
    }

    public CheckPermissionQuery toCheckPermissionQuery(CheckPermissionRequest request) {
        CheckPermissionRequest requiredRequest = requireNonNull(request, "CheckPermissionRequest");

        return new CheckPermissionQuery(
                UserId.of(requiredRequest.userId()),
                PermissionCode.of(requiredRequest.permissionCode())
        );
    }

    public GetUserPermissionsQuery toGetUserPermissionsQuery(String routeUserId) {
        return new GetUserPermissionsQuery(UserId.of(routeUserId));
    }

    public UserResponse toUserResponse(UserDto dto) {
        UserDto requiredDto = requireNonNull(dto, "UserDto");

        String employeeReference = requiredDto.employeeReference() == null
                ? null
                : requiredDto.employeeReference().value();

        return new UserResponse(
                requiredDto.id().value(),
                requiredDto.username().value(),
                requiredDto.emailAddress().value(),
                requiredDto.status().name(),
                employeeReference,
                toRoleResponses(requiredDto.roles())
        );
    }

    public RoleResponse toRoleResponse(RoleDto dto) {
        RoleDto requiredDto = requireNonNull(dto, "RoleDto");

        return new RoleResponse(
                requiredDto.id().value(),
                requiredDto.code().value(),
                requiredDto.name().value(),
                requiredDto.status().name(),
                toPermissionResponses(requiredDto.permissions())
        );
    }

    public PermissionResponse toPermissionResponse(PermissionDto dto) {
        PermissionDto requiredDto = requireNonNull(dto, "PermissionDto");

        return new PermissionResponse(
                requiredDto.id().value(),
                requiredDto.code().value(),
                requiredDto.name().value(),
                requiredDto.description()
        );
    }

    public PermissionDecisionResponse toPermissionDecisionResponse(PermissionDecisionDto dto) {
        PermissionDecisionDto requiredDto = requireNonNull(dto, "PermissionDecisionDto");

        return new PermissionDecisionResponse(
                requiredDto.userId().value(),
                requiredDto.permissionCode().value(),
                requiredDto.granted(),
                requiredDto.reason()
        );
    }

    public List<PermissionResponse> toPermissionResponses(List<PermissionDto> dtos) {
        List<PermissionDto> requiredDtos = requireNonNull(dtos, "permissions");

        return requiredDtos.stream()
                .map(this::toPermissionResponse)
                .toList();
    }

    public List<RoleResponse> toRoleResponses(List<RoleDto> dtos) {
        List<RoleDto> requiredDtos = requireNonNull(dtos, "roles");

        return requiredDtos.stream()
                .map(this::toRoleResponse)
                .toList();
    }

    private static void requireMatchingIdentifier(
            String routeValue,
            String bodyValue,
            String fieldName
    ) {
        if (!Objects.equals(routeValue, bodyValue)) {
            throw new InvalidValueObjectException(fieldName + " in route and request body must match.");
        }
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
