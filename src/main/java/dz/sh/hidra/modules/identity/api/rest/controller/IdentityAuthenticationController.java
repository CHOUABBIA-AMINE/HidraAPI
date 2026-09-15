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
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationResult;
import dz.sh.hidra.modules.identity.application.model.IssuedAccessToken;
import dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.model.LoginSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Canonical direct-login endpoint for explicitly selected LOCAL and LDAP/AD providers.
 *
 * <p>The API depends only on the Identity application boundary. Credential verification,
 * provider routing, session persistence, and JWT implementation details remain behind ports.</p>
 */
@RestController
@RequestMapping("/api/v1/identity/authentication")
public final class IdentityAuthenticationController {

    private final AuthenticateDirectUserUseCase authenticateDirectUserUseCase;

    public IdentityAuthenticationController(AuthenticateDirectUserUseCase authenticateDirectUserUseCase) {
        this.authenticateDirectUserUseCase = Objects.requireNonNull(authenticateDirectUserUseCase);
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
                servletRequest == null ? null : servletRequest.getRemoteAddr(),
                header(servletRequest, "User-Agent"),
                header(servletRequest, "X-Correlation-Id")
        ));

        HidraPrincipal principal = result.principal();
        LoginSession session = result.session();
        IssuedAccessToken accessToken = result.accessToken();
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

    private static String header(HttpServletRequest request, String name) {
        if (request == null) {
            return null;
        }
        String value = request.getHeader(name);
        return value == null || value.isBlank() ? null : value.trim();
    }
}
