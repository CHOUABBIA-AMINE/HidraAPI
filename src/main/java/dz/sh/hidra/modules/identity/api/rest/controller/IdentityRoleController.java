/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityRoleController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : REST controller for identity role endpoints.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import dz.sh.hidra.modules.identity.api.rest.mapper.IdentityRestMapper;
import dz.sh.hidra.modules.identity.api.rest.request.CreateRoleRequest;
import dz.sh.hidra.modules.identity.api.rest.request.GrantPermissionToRoleRequest;
import dz.sh.hidra.modules.identity.api.rest.response.RoleResponse;
import dz.sh.hidra.modules.identity.application.port.in.CreateRoleUseCase;
import dz.sh.hidra.modules.identity.application.port.in.GrantPermissionToRoleUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
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
 * REST controller for identity role endpoints.
 *
 * <p>Business role: exposes role creation and permission grant endpoints under the
 * identity API path.</p>
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
@RequestMapping("/api/v1/identity/roles")
public class IdentityRoleController {

    private final CreateRoleUseCase createRoleUseCase;
    private final GrantPermissionToRoleUseCase grantPermissionToRoleUseCase;
    private final IdentityRestMapper mapper;

    public IdentityRoleController(
            CreateRoleUseCase createRoleUseCase,
            GrantPermissionToRoleUseCase grantPermissionToRoleUseCase,
            IdentityRestMapper mapper
    ) {
        this.createRoleUseCase = createRoleUseCase;
        this.grantPermissionToRoleUseCase = grantPermissionToRoleUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleResponse createRole(@Valid @RequestBody CreateRoleRequest request) {
        return mapper.toRoleResponse(
                createRoleUseCase.createRole(mapper.toCreateRoleCommand(request))
        );
    }

    @GetMapping
    public List<RoleResponse> listRoles() {
        throw new ResponseStatusException(
                HttpStatus.NOT_IMPLEMENTED,
                "List roles requires a dedicated application port not created before ID-015."
        );
    }

    @GetMapping("/{roleId}")
    public RoleResponse getRoleById(
            @PathVariable
            @NotBlank
            @Size(max = 36)
            String roleId
    ) {
        throw new ResponseStatusException(
                HttpStatus.NOT_IMPLEMENTED,
                "Get role by id requires a dedicated application port not created before ID-015."
        );
    }

    @PostMapping("/{roleId}/permissions")
    public RoleResponse grantPermissionToRole(
            @PathVariable
            @NotBlank
            @Size(max = 36)
            String roleId,
            @Valid @RequestBody GrantPermissionToRoleRequest request
    ) {
        return mapper.toRoleResponse(
                grantPermissionToRoleUseCase.grantPermissionToRole(
                        mapper.toGrantPermissionToRoleCommand(roleId, request)
                )
        );
    }

    @PostMapping("/{roleId}/permissions/{permissionId}/revoke")
    public RoleResponse revokePermissionFromRole(
            @PathVariable
            @NotBlank
            @Size(max = 36)
            String roleId,
            @PathVariable
            @NotBlank
            @Size(max = 36)
            String permissionId
    ) {
        throw new ResponseStatusException(
                HttpStatus.NOT_IMPLEMENTED,
                "Revoking permissions from roles requires a dedicated application port not created before ID-015."
        );
    }
}
