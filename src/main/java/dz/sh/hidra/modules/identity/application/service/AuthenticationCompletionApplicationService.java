/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticationCompletionApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Completes normalized authentication by issuing the unified Hidra token and creating the logical login session.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.model.AuthenticatedPrincipalInput;
import dz.sh.hidra.modules.identity.application.model.AuthenticationResult;
import dz.sh.hidra.modules.identity.application.model.IssuedAccessToken;
import dz.sh.hidra.modules.identity.application.port.in.CompleteAuthenticatedPrincipalUseCase;
import dz.sh.hidra.modules.identity.application.port.out.AccessTokenIssuerPort;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.model.LoginSession;
import java.util.Objects;
import org.springframework.stereotype.Service;

/**
 * Shared provider-neutral completion path used after a normalized identity has been authenticated.
 */
@Service
public final class AuthenticationCompletionApplicationService implements CompleteAuthenticatedPrincipalUseCase {

    private final AccessTokenIssuerPort accessTokenIssuer;
    private final AuthenticationSessionLifecycleApplicationService sessionLifecycle;

    public AuthenticationCompletionApplicationService(
            AccessTokenIssuerPort accessTokenIssuer,
            AuthenticationSessionLifecycleApplicationService sessionLifecycle
    ) {
        this.accessTokenIssuer = Objects.requireNonNull(accessTokenIssuer);
        this.sessionLifecycle = Objects.requireNonNull(sessionLifecycle);
    }

    @Override
    public AuthenticationResult complete(
            AuthenticatedPrincipalInput input,
            String clientIp,
            String userAgent,
            String correlationId
    ) {
        Objects.requireNonNull(input, "AuthenticatedPrincipalInput must not be null.");

        HidraPrincipal principal = new HidraPrincipal(
                input.userId(),
                input.username(),
                input.displayName(),
                input.authenticationType(),
                input.identityProviderId(),
                input.roles(),
                input.permissions(),
                input.externalIdentityId()
        );
        IssuedAccessToken accessToken = accessTokenIssuer.issue(principal);
        LoginSession session = sessionLifecycle.startSession(
                principal,
                accessToken.expiresAt(),
                clientIp,
                userAgent,
                correlationId
        );
        return result(principal, session, accessToken);
    }

    private static AuthenticationResult result(
            HidraPrincipal principal,
            LoginSession session,
            IssuedAccessToken accessToken
    ) {
        return new AuthenticationResult(
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
}
