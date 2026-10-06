/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityOidcAuthorizationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Resolves bounded and explainable Identity authorization evidence.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import dz.sh.hidra.modules.identity.api.rest.mapper.IdentityAuthenticationWebMapper;
import dz.sh.hidra.modules.identity.api.rest.mapper.IdentityRestMapper;
import dz.sh.hidra.modules.identity.api.rest.request.EvaluatePermissionRequest;
import dz.sh.hidra.modules.identity.api.rest.response.PermissionDecisionResponse;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Objects;

/** Live external assertion evaluation is limited to the already authenticated subject. */
@RestController
@RequestMapping("/api/v1/identity/authentication/oidc")
public final class IdentityOidcAuthorizationController {
    private final EvaluatePermissionUseCase evaluateUseCase;
    public IdentityOidcAuthorizationController(EvaluatePermissionUseCase useCase) { this.evaluateUseCase=Objects.requireNonNull(useCase); }
    @PostMapping("/evaluate")
    public PermissionDecisionResponse evaluate(@AuthenticationPrincipal Object principal,
            @Valid @RequestBody EvaluatePermissionRequest request) {
        var input=IdentityAuthenticationWebMapper.oidcPrincipal(principal);
        if(input.externalIdentityId()==null || input.identityProviderId()==null || !input.userId().equals(request.userId()))
            throw new AccessDeniedException("External authorization evaluation requires the authenticated linked user.");
        return IdentityRestMapper.toResponse(evaluateUseCase.evaluate(IdentityRestMapper.toQuery(request)));
    }
}
