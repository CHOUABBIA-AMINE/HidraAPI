/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAdministrationCommandController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Exposes validated role, permission, and grant administration mutation APIs.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import dz.sh.hidra.modules.identity.api.rest.request.IdentityAdministrationRequests;
import dz.sh.hidra.modules.identity.application.command.IdentityAdministrationCommands;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationCommandUseCase;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase.PermissionView;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase.RoleView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Identity administration mutation REST controller.
 */
@RestController
@Validated
@RequestMapping("/api/v1/identity")
@Tag(name = "Identity Administration", description = "Role, permission, and authorization-grant administration commands.")
public final class IdentityAdministrationCommandController {

    private final IdentityAdministrationCommandUseCase commandUseCase;

    public IdentityAdministrationCommandController(IdentityAdministrationCommandUseCase commandUseCase) {
        this.commandUseCase = Objects.requireNonNull(commandUseCase, "IdentityAdministrationCommandUseCase must not be null.");
    }

    @PostMapping("/roles")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create identity role")
    public RoleView createRole(@Valid @RequestBody IdentityAdministrationRequests.CreateRole request) {
        return commandUseCase.createRole(new IdentityAdministrationCommands.CreateRole(
                request.code(),
                request.nameAr(),
                request.nameFr(),
                request.nameEn(),
                request.description(),
                request.roleType(),
                request.status()
        ));
    }

    @PostMapping("/permissions")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create identity permission")
    public PermissionView createPermission(@Valid @RequestBody IdentityAdministrationRequests.CreatePermission request) {
        return commandUseCase.createPermission(new IdentityAdministrationCommands.CreatePermission(
                request.code(),
                request.nameAr(),
                request.nameFr(),
                request.nameEn(),
                request.description(),
                request.permissionDomain(),
                request.resourceType(),
                request.action(),
                request.sensitive(),
                request.status()
        ));
    }

    @PostMapping("/users/role-grants")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Grant role to user")
    public String grantRole(@Valid @RequestBody IdentityAdministrationRequests.GrantRoleToUser request) {
        return commandUseCase.grantRoleToUser(new IdentityAdministrationCommands.GrantRoleToUser(
                request.userId(),
                request.roleId(),
                request.scopeType(),
                request.scopeReferenceId(),
                request.scopeCodeSnapshot(),
                request.reason(),
                request.approvedByWorkflowId(),
                request.validFrom(),
                request.validTo()
        ));
    }

    @PostMapping("/roles/permission-grants")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Grant permission to role")
    public String grantRolePermission(@Valid @RequestBody IdentityAdministrationRequests.GrantPermissionToRole request) {
        return commandUseCase.grantPermissionToRole(new IdentityAdministrationCommands.GrantPermissionToRole(
                request.roleId(),
                request.permissionId(),
                request.effect(),
                request.conditionExpression(),
                request.validFrom(),
                request.validTo()
        ));
    }

    @PostMapping("/users/permission-grants")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Grant permission to user")
    public String grantUserPermission(@Valid @RequestBody IdentityAdministrationRequests.GrantPermissionToUser request) {
        return commandUseCase.grantPermissionToUser(new IdentityAdministrationCommands.GrantPermissionToUser(
                request.userId(),
                request.permissionId(),
                request.effect(),
                request.scopeType(),
                request.scopeReferenceId(),
                request.scopeCodeSnapshot(),
                request.reason(),
                request.approvedByWorkflowId(),
                request.emergencyAccess(),
                request.validFrom(),
                request.validTo()
        ));
    }
}
