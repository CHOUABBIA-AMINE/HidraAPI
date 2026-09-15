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
 * @Description : Exposes the canonical direct LOCAL and LDAP/Active Directory login boundary.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import dz.sh.hidra.modules.identity.api.rest.request.AuthenticationLoginRequest;
import dz.sh.hidra.modules.identity.api.rest.response.AuthenticationLoginResponse;
import dz.sh.hidra.modules.identity.application.model.IssuedAccessToken;
import dz.sh.hidra.modules.identity.application.service.AuthenticationSessionLifecycleApplicationService;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.model.LoginSession;
import dz.sh.hidra.modules.identity.infrastructure.security.HidraAccessTokenIssuer;
import dz.sh.hidra.modules.identity.infrastructure.security.IdentityAuthenticationRequestRouter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Canonical direct-login endpoint for explicitly selected LOCAL and LDAP/AD providers.
 *
 * <p>Credential verification remains delegated to provider-specific AuthenticationProvider
 * implementations. This API layer performs no persistence access, password comparison,
 * LDAP bind, role assignment, provider fallback, or OIDC callback/token exchange.</p>
 */
@RestController
@RequestMapping("/api/v1/identity/authentication")
public final class IdentityAuthenticationController {

    private final IdentityAuthenticationRequestRouter authenticationRequestRouter;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationSessionLifecycleApplicationService sessionLifecycle;
    private final HidraAccessTokenIssuer accessTokenIssuer;

    public IdentityAuthenticationController(
            IdentityAuthenticationRequestRouter authenticationRequestRouter,
            @Qualifier("hidraAuthenticationManager") AuthenticationManager authenticationManager,
            AuthenticationSessionLifecycleApplicationService sessionLifecycle,
            HidraAccessTokenIssuer accessTokenIssuer
    ) {
        this.authenticationRequestRouter = Objects.requireNonNull(authenticationRequestRouter);
        this.authenticationManager = Objects.requireNonNull(authenticationManager);
        this.sessionLifecycle = Objects.requireNonNull(sessionLifecycle);
        this.accessTokenIssuer = Objects.requireNonNull(accessTokenIssuer);
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

        Authentication authenticationRequest = authenticationRequestRouter.route(
                request.providerType(),
                request.principal(),
                request.credentials()
        );
        Authentication authenticated = authenticationManager.authenticate(authenticationRequest);
        HidraPrincipal principal = requireHidraPrincipal(authenticated);

        IssuedAccessToken accessToken = accessTokenIssuer.issue(principal);
        LoginSession session = sessionLifecycle.startSession(
                principal,
                accessToken.expiresAt(),
                servletRequest == null ? null : servletRequest.getRemoteAddr(),
                header(servletRequest, "User-Agent"),
                header(servletRequest, "X-Correlation-Id")
        );

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

    private static HidraPrincipal requireHidraPrincipal(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Direct authentication did not produce an authenticated result.");
        }
        if (!(authentication.getPrincipal() instanceof HidraPrincipal principal)) {
            throw new IllegalStateException("Direct authentication did not produce a HidraPrincipal.");
        }
        return principal;
    }

    private static String header(HttpServletRequest request, String name) {
        if (request == null) {
            return null;
        }
        String value = request.getHeader(name);
        return value == null || value.isBlank() ? null : value.trim();
    }
}
