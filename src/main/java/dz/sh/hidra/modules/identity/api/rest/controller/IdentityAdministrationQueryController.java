package dz.sh.hidra.modules.identity.api.rest.controller;

import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/identity")
public class IdentityAdministrationQueryController {
    private final IdentityAdministrationQueryUseCase useCase;
    public IdentityAdministrationQueryController(IdentityAdministrationQueryUseCase useCase){this.useCase=Objects.requireNonNull(useCase);}

    @GetMapping("/me") public PrincipalView me(Authentication authentication){return useCase.principal(authentication.getName(),authorities(authentication));}
    @GetMapping("/me/permissions") public List<String> myPermissions(Authentication authentication){return me(authentication).effectivePermissions();}
    @GetMapping("/users") public Page<UserView> users(@RequestParam(required=false)String q,@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="50")int size){return useCase.users(q,page,size);}
    @GetMapping("/users/{id}") public UserView user(@PathVariable String id){return useCase.user(id);}
    @GetMapping("/roles") public Page<RoleView> roles(@RequestParam(required=false)String q,@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="50")int size){return useCase.roles(q,page,size);}
    @GetMapping("/permissions") public Page<PermissionView> permissions(@RequestParam(required=false)String q,@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="50")int size){return useCase.permissions(q,page,size);}
    @PostMapping("/roles") public RoleView createRole(@Valid @RequestBody CreateRoleCommand command){return useCase.createRole(command);}
    @PostMapping("/permissions") public PermissionView createPermission(@Valid @RequestBody CreatePermissionCommand command){return useCase.createPermission(command);}
    @PostMapping("/users/role-grants") public String grantRole(@Valid @RequestBody UserRoleGrantCommand command){return useCase.grantRoleToUser(command);}
    @PostMapping("/roles/permission-grants") public String grantRolePermission(@Valid @RequestBody RolePermissionGrantCommand command){return useCase.grantPermissionToRole(command);}
    @PostMapping("/users/permission-grants") public String grantUserPermission(@Valid @RequestBody UserPermissionGrantCommand command){return useCase.grantPermissionToUser(command);}
    private static List<String> authorities(Authentication a){return a.getAuthorities().stream().map(Object::toString).toList();}
}
