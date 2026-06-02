/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityUserController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : REST controller for identity user endpoints.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import dz.sh.hidra.modules.identity.api.rest.mapper.IdentityRestMapper;
import dz.sh.hidra.modules.identity.api.rest.request.ActivateUserRequest;
import dz.sh.hidra.modules.identity.api.rest.request.AssignRoleToUserRequest;
import dz.sh.hidra.modules.identity.api.rest.request.RegisterUserRequest;
import dz.sh.hidra.modules.identity.api.rest.response.PermissionResponse;
import dz.sh.hidra.modules.identity.api.rest.response.UserResponse;
import dz.sh.hidra.modules.identity.application.port.in.ActivateUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.AssignRoleToUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.GetUserPermissionsUseCase;
import dz.sh.hidra.modules.identity.application.port.in.RegisterUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.RevokeRoleFromUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.SuspendUserUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * REST controller for identity user endpoints.
 *
 * <p>Business role: exposes user registration, lifecycle, role assignment, role
 * revocation, and effective permission endpoints under the identity API path.</p>
 *
 * <p>Architecture role: API adapter that depends only on application inbound ports and
 * {@link IdentityRestMapper}. It does not access repositories, JPA entities, platform
 * security plumbing, or organization structures.</p>
 *
 * <p>Validation responsibility: uses {@link Valid} request bodies and Bean Validation
 * annotations for path values.</p>
 *
 * <p>Usage: discovered by Spring MVC as part of the identity REST API.</p>
 */
@RestController
@RequestMapping("/api/v1/identity/users")
public class IdentityUserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final ActivateUserUseCase activateUserUseCase;
    private final SuspendUserUseCase suspendUserUseCase;
    private final AssignRoleToUserUseCase assignRoleToUserUseCase;
    private final RevokeRoleFromUserUseCase revokeRoleFromUserUseCase;
    private final GetUserPermissionsUseCase getUserPermissionsUseCase;
    private final IdentityRestMapper mapper;

    public IdentityUserController(
            RegisterUserUseCase registerUserUseCase,
            ActivateUserUseCase activateUserUseCase,
            SuspendUserUseCase suspendUserUseCase,
            AssignRoleToUserUseCase assignRoleToUserUseCase,
            RevokeRoleFromUserUseCase revokeRoleFromUserUseCase,
            GetUserPermissionsUseCase getUserPermissionsUseCase,
            IdentityRestMapper mapper
    ) {
        this.registerUserUseCase = registerUserUseCase;
        this.activateUserUseCase = activateUserUseCase;
        this.suspendUserUseCase = suspendUserUseCase;
        this.assignRoleToUserUseCase = assignRoleToUserUseCase;
        this.revokeRoleFromUserUseCase = revokeRoleFromUserUseCase;
        this.getUserPermissionsUseCase = getUserPermissionsUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@Valid @RequestBody RegisterUserRequest request) {
        return mapper.toUserResponse(
                registerUserUseCase.registerUser(mapper.toRegisterUserCommand(request))
        );
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(
            @PathVariable
            @NotBlank
            @Size(max = 36)
            String userId
    ) {
        throw new ResponseStatusException(
                HttpStatus.NOT_IMPLEMENTED,
                "Get user by id requires a dedicated application port not created before ID-015."
        );
    }

    @GetMapping
    public List<UserResponse> searchUsers() {
        throw new ResponseStatusException(
                HttpStatus.NOT_IMPLEMENTED,
                "Search users requires a dedicated application port not created before ID-015."
        );
    }

    @PostMapping("/{userId}/activate")
    public UserResponse activateUser(
            @PathVariable
            @NotBlank
            @Size(max = 36)
            String userId,
            @Valid @RequestBody ActivateUserRequest request
    ) {
        return mapper.toUserResponse(
                activateUserUseCase.activate(mapper.toActivateUserCommand(userId, request))
        );
    }

    @PostMapping("/{userId}/suspend")
    public UserResponse suspendUser(
            @PathVariable
            @NotBlank
            @Size(max = 36)
            String userId
    ) {
        return mapper.toUserResponse(
                suspendUserUseCase.suspendUser(mapper.toSuspendUserCommand(userId))
        );
    }

    @PostMapping("/{userId}/roles")
    public UserResponse assignRoleToUser(
            @PathVariable
            @NotBlank
            @Size(max = 36)
            String userId,
            @Valid @RequestBody AssignRoleToUserRequest request
    ) {
        return mapper.toUserResponse(
                assignRoleToUserUseCase.assignRoleToUser(
                        mapper.toAssignRoleToUserCommand(userId, request)
                )
        );
    }

    @DeleteMapping("/{userId}/roles/{roleId}")
    public UserResponse revokeRoleFromUser(
            @PathVariable
            @NotBlank
            @Size(max = 36)
            String userId,
            @PathVariable
            @NotBlank
            @Size(max = 36)
            String roleId
    ) {
        return mapper.toUserResponse(
                revokeRoleFromUserUseCase.revokeRoleFromUser(
                        mapper.toRevokeRoleFromUserCommand(userId, roleId)
                )
        );
    }

    @GetMapping("/{userId}/permissions")
    public List<PermissionResponse> getUserPermissions(
            @PathVariable
            @NotBlank
            @Size(max = 36)
            String userId
    ) {
        return mapper.toPermissionResponses(
                getUserPermissionsUseCase.getUserPermissions(mapper.toGetUserPermissionsQuery(userId))
        );
    }
}
