/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAdministrationQueryController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Exposes dedicated identity administration and authenticated-principal read APIs.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase.Page;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase.PermissionView;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase.PrincipalView;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase.RoleView;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase.UserView;
import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Read-only identity administration REST controller.
 */
@RestController
@Validated
@RequestMapping("/api/v1/identity")
@Tag(name = "Identity Administration", description = "Authenticated principal, user, role, and permission queries.")
public final class IdentityAdministrationQueryController {

    private final IdentityAdministrationQueryUseCase queryUseCase;
    private final HidraEffectivePermissionResolver permissionResolver;

    public IdentityAdministrationQueryController(
            IdentityAdministrationQueryUseCase queryUseCase,
            HidraEffectivePermissionResolver permissionResolver
    ) {
        this.queryUseCase = Objects.requireNonNull(queryUseCase, "IdentityAdministrationQueryUseCase must not be null.");
        this.permissionResolver = Objects.requireNonNull(permissionResolver, "HidraEffectivePermissionResolver must not be null.");
    }

    @GetMapping("/me")
    @Operation(summary = "Get current identity principal")
    public PrincipalView me(Authentication authentication) {
        PrincipalView base = queryUseCase.principal(authentication.getName(), authorities(authentication));
        List<String> effectivePermissions = permissionResolver.resolve(authentication).stream().sorted().toList();
        return new PrincipalView(
                base.authenticationName(),
                base.authenticationType(),
                base.userId(),
                base.username(),
                base.displayName(),
                base.employeeReferenceId(),
                base.authenticationAuthorities(),
                effectivePermissions
        );
    }

    @GetMapping("/me/permissions")
    @Operation(summary = "Get current effective permissions")
    public List<String> myPermissions(Authentication authentication) {
        return permissionResolver.resolve(authentication).stream().sorted().toList();
    }

    @GetMapping("/users")
    @Operation(summary = "List identity users")
    public Page<UserView> users(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(200) int size
    ) {
        return queryUseCase.users(q, page, size);
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Get identity user")
    public UserView user(@PathVariable String id) {
        return queryUseCase.user(id);
    }

    @GetMapping("/roles")
    @Operation(summary = "List identity roles")
    public Page<RoleView> roles(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(200) int size
    ) {
        return queryUseCase.roles(q, page, size);
    }

    @GetMapping("/permissions")
    @Operation(summary = "List identity permissions")
    public Page<PermissionView> permissions(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(200) int size
    ) {
        return queryUseCase.permissions(q, page, size);
    }

    private static List<String> authorities(Authentication authentication) {
        return authentication.getAuthorities().stream().map(Object::toString).toList();
    }
}
