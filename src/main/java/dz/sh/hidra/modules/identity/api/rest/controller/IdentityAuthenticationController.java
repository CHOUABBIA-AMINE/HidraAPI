/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAuthenticationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Exposes direct login and externally validated OIDC completion through the Identity application boundary.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import dz.sh.hidra.modules.identity.api.rest.mapper.IdentityAuthenticationWebMapper;
import dz.sh.hidra.modules.identity.api.rest.request.AuthenticationLoginRequest;
import dz.sh.hidra.modules.identity.api.rest.response.AuthenticationLoginResponse;
import dz.sh.hidra.modules.identity.application.model.AuthenticatedPrincipalInput;
import dz.sh.hidra.modules.identity.application.model.AuthenticationResult;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand;
import dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.CompleteAuthenticatedPrincipalUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication HTTP boundary for direct credentials and externally validated OIDC identities.
 */
@RestController
@RequestMapping("/api/v1/identity/authentication")
public final class IdentityAuthenticationController {

    private final AuthenticateDirectUserUseCase authenticateDirectUserUseCase;
    private final CompleteAuthenticatedPrincipalUseCase completionUseCase;

    public IdentityAuthenticationController(
            AuthenticateDirectUserUseCase authenticateDirectUserUseCase,
            CompleteAuthenticatedPrincipalUseCase completionUseCase
    ) {
        this.authenticateDirectUserUseCase = Objects.requireNonNull(authenticateDirectUserUseCase);
        this.completionUseCase = Objects.requireNonNull(completionUseCase);
    }

    @PostMapping("/login")
    public AuthenticationLoginResponse login(
            @Valid @RequestBody AuthenticationLoginRequest request,
            HttpServletRequest servletRequest
    ) {
        Objects.requireNonNull(request, "AuthenticationLoginRequest must not be null.");

        AuthenticationResult result = authenticateDirectUserUseCase.authenticate(new DirectAuthenticationCommand(
                request.providerType(),
                request.principal(),
                request.credentials(),
                clientIp(servletRequest),
                header(servletRequest, "User-Agent"),
                header(servletRequest, "X-Correlation-Id")
        ));
        return IdentityAuthenticationWebMapper.response(result);
    }

    @PostMapping("/oidc/complete")
    public AuthenticationLoginResponse completeOidc(
            @AuthenticationPrincipal Object springPrincipal,
            HttpServletRequest servletRequest
    ) {
        AuthenticatedPrincipalInput principal = IdentityAuthenticationWebMapper.oidcPrincipal(springPrincipal);
        AuthenticationResult result = completionUseCase.complete(
                principal,
                clientIp(servletRequest),
                header(servletRequest, "User-Agent"),
                header(servletRequest, "X-Correlation-Id")
        );
        return IdentityAuthenticationWebMapper.response(result);
    }

    private static String clientIp(HttpServletRequest request) {
        return request == null ? null : request.getRemoteAddr();
    }

    private static String header(HttpServletRequest request, String name) {
        if (request == null) {
            return null;
        }
        String value = request.getHeader(name);
        return value == null || value.isBlank() ? null : value.trim();
    }
}
