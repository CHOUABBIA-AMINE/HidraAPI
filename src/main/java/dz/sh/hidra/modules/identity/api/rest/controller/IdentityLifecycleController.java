/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityLifecycleController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Exposes secured and versioned ordinary identity lifecycle APIs.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import dz.sh.hidra.modules.identity.api.rest.request.IdentityLifecycleRequests;
import dz.sh.hidra.modules.identity.api.rest.response.IdentityLifecycleResponses;
import dz.sh.hidra.modules.identity.application.port.in.FoundationIdentityLifecycleUseCase;
import dz.sh.hidra.modules.identity.application.port.in.FoundationIdentityLifecycleUseCase.GrantKind;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/identity")
public final class IdentityLifecycleController {
    private final FoundationIdentityLifecycleUseCase lifecycle;
    public IdentityLifecycleController(FoundationIdentityLifecycleUseCase lifecycle) { this.lifecycle = lifecycle; }

    @GetMapping("/users/{id}/lifecycle")
    @Operation(summary = "Read current identity lifecycle version for guarded administration")
    public IdentityLifecycleResponses.User currentUser(@PathVariable String id) {
        try { return IdentityLifecycleResponses.User.of(lifecycle.currentUser(id)); }
        catch (RuntimeException e) { throw http(e); }
    }

    @GetMapping("/users/{id}/local-credential/state")
    @Operation(summary = "Read only LOCAL credential generation; never return credential material")
    public IdentityLifecycleResponses.Credential currentCredential(@PathVariable String id) {
        try { return IdentityLifecycleResponses.Credential.of(lifecycle.currentCredential(id)); }
        catch (RuntimeException e) { throw http(e); }
    }

    @PostMapping("/users/{id}/activate")
    @Operation(summary = "Activate credentialed registered LOCAL identity")
    public IdentityLifecycleResponses.User activate(@PathVariable String id,
            @Valid @RequestBody IdentityLifecycleRequests.Transition input) {
        try { return IdentityLifecycleResponses.User.of(lifecycle.activate(id, input.expectedUpdatedAt(), input.reason())); }
        catch (RuntimeException e) { throw http(e); }
    }

    @PostMapping("/users/{id}/deactivate")
    @Operation(summary = "Disable ordinary identity and revoke sessions")
    public IdentityLifecycleResponses.User deactivate(@PathVariable String id,
            @Valid @RequestBody IdentityLifecycleRequests.Transition input) {
        try { return IdentityLifecycleResponses.User.of(lifecycle.deactivate(id, input.expectedUpdatedAt(), input.reason())); }
        catch (RuntimeException e) { throw http(e); }
    }

    @PutMapping("/users/{id}/local-credential")
    @Operation(summary = "Initialize or reset LOCAL credential without returning a password or hash")
    public IdentityLifecycleResponses.Credential credential(@PathVariable String id,
            @Valid @RequestBody IdentityLifecycleRequests.Credential input) {
        try { return IdentityLifecycleResponses.Credential.of(lifecycle.credential(id, input.password(),
                input.expectedSecurityVersion(), input.reason())); }
        catch (RuntimeException e) { throw http(e); }
    }

    @PostMapping("/users/role-grants/{id}/revoke")
    public IdentityLifecycleResponses.Grant revokeUserRole(@PathVariable String id,
            @Valid @RequestBody IdentityLifecycleRequests.Revoke input) {
        return revoke(GrantKind.USER_ROLE, id, input);
    }

    @PostMapping("/users/permission-grants/{id}/revoke")
    public IdentityLifecycleResponses.Grant revokeUserPermission(@PathVariable String id,
            @Valid @RequestBody IdentityLifecycleRequests.Revoke input) {
        return revoke(GrantKind.USER_PERMISSION, id, input);
    }

    @PostMapping("/roles/permission-grants/{id}/revoke")
    public IdentityLifecycleResponses.Grant revokeRolePermission(@PathVariable String id,
            @Valid @RequestBody IdentityLifecycleRequests.Revoke input) {
        return revoke(GrantKind.ROLE_PERMISSION, id, input);
    }

    @GetMapping("/me/operation-capabilities")
    @Operation(summary = "Advisory identity operations; not authorization proof")
    public List<String> capabilities(@RequestParam(required = false) String companyId,
            @RequestParam(required = false) String resourceType,
            @RequestParam(required = false) String resourceId) {
        try { return lifecycle.capabilities(companyId, resourceType, resourceId); }
        catch (RuntimeException e) { throw http(e); }
    }

    private IdentityLifecycleResponses.Grant revoke(GrantKind type, String id, IdentityLifecycleRequests.Revoke input) {
        try { return IdentityLifecycleResponses.Grant.of(lifecycle.revoke(type, id, input.expectedCreatedAt(), input.reason())); }
        catch (RuntimeException e) { throw http(e); }
    }

    private static RuntimeException http(RuntimeException e) {
        if (e instanceof AccessDeniedException) return e;
        if (e instanceof ResponseStatusException) return e;
        if (e instanceof IllegalArgumentException) return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid lifecycle request.");
        if (e instanceof NoSuchElementException) return new ResponseStatusException(HttpStatus.NOT_FOUND, "Identity resource unavailable.");
        if (e instanceof IllegalStateException) return new ResponseStatusException(HttpStatus.CONFLICT, "Identity lifecycle conflict.");
        return e;
    }
}
