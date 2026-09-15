/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAuthenticationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
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

import dz.sh.hidra.modules.identity.api.rest.request.AuthenticationLoginRequest;
import dz.sh.hidra.modules.identity.api.rest.response.AuthenticationLoginResponse;
import dz.sh.hidra.modules.identity.application.model.AuthenticationCompletionResult;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationResult;
import dz.sh.hidra.modules.identity.application.model.IssuedAccessToken;
import dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.CompleteAuthenticatedPrincipalUseCase;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.model.LoginSession;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication HTTP boundary for direct credentials and externally validated OIDC identities.
 *
 * <p>The API depends only on Identity application contracts. Browser authorization-code + PKCE
 * remains external to HidraAPI; the OIDC completion endpoint accepts only the HidraPrincipal
 * produced after Spring Security has already validated and normalized the external bearer JWT.</p>
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

    /**
     * Authenticates through the selected direct provider and returns a Hidra session/token result.
     */
    @PostMapping("/login")
    public AuthenticationLoginResponse login(
            @Valid @RequestBody AuthenticationLoginRequest request,
            HttpServletRequest servletRequest
    ) {
        Objects.requireNonNull(request, "AuthenticationLoginRequest must not be null.");

        DirectAuthenticationResult result = authenticateDirectUserUseCase.authenticate(new DirectAuthenticationCommand(
                request.providerType(),
                request.principal(),
                request.credentials(),
                clientIp(servletRequest),
                header(servletRequest, "User-Agent"),
                header(servletRequest, "X-Correlation-Id")
        ));
        return response(result.principal(), result.session(), result.accessToken());
    }

    /**
     * Completes an externally validated OIDC authentication into the same Hidra session/token result.
     */
    @PostMapping("/oidc/complete")
    public AuthenticationLoginResponse completeOidc(
            @AuthenticationPrincipal HidraPrincipal principal,
            HttpServletRequest servletRequest
    ) {
        if (principal == null || principal.authenticationType() != ProviderType.OIDC) {
            throw new AccessDeniedException("OIDC completion requires an externally validated OIDC Hidra principal.");
        }

        AuthenticationCompletionResult result = completionUseCase.complete(
                principal,
                clientIp(servletRequest),
                header(servletRequest, "User-Agent"),
                header(servletRequest, "X-Correlation-Id")
        );
        return response(result.principal(), result.session(), result.accessToken());
    }

    private static AuthenticationLoginResponse response(
            HidraPrincipal principal,
            LoginSession session,
            IssuedAccessToken accessToken
    ) {
        return new AuthenticationLoginResponse(
                session.id(),
                accessToken.tokenValue(),
                accessToken.tokenType(),
                accessToken.jti(),
                accessToken.issuedAt(),
                accessToken.expiresAt(),
                principal.userId(),
                principal.username(),
                principal.displayName(),
                principal.authenticationType(),
                principal.identityProviderId(),
                principal.roles(),
                principal.permissions()
        );
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
