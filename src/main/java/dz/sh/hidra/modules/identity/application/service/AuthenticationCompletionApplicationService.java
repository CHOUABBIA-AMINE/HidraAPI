/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticationCompletionApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
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

import dz.sh.hidra.modules.identity.application.model.AuthenticationCompletionResult;
import dz.sh.hidra.modules.identity.application.model.IssuedAccessToken;
import dz.sh.hidra.modules.identity.application.port.in.CompleteAuthenticatedPrincipalUseCase;
import dz.sh.hidra.modules.identity.application.port.out.AccessTokenIssuerPort;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.model.LoginSession;
import java.util.Objects;
import org.springframework.stereotype.Service;

/**
 * Shared provider-neutral completion path used after a HidraPrincipal has been authenticated.
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
    public AuthenticationCompletionResult complete(
            HidraPrincipal principal,
            String clientIp,
            String userAgent,
            String correlationId
    ) {
        Objects.requireNonNull(principal, "Hidra principal must not be null.");

        IssuedAccessToken accessToken = accessTokenIssuer.issue(principal);
        LoginSession session = sessionLifecycle.startSession(
                principal,
                accessToken.expiresAt(),
                clientIp,
                userAgent,
                correlationId
        );
        return new AuthenticationCompletionResult(principal, session, accessToken);
    }
}
