/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DirectAuthenticationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Orchestrates provider-selected direct authentication, logical session creation, and unified Hidra token issuance.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationResult;
import dz.sh.hidra.modules.identity.application.model.IssuedAccessToken;
import dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase;
import dz.sh.hidra.modules.identity.application.port.out.AccessTokenIssuerPort;
import dz.sh.hidra.modules.identity.application.port.out.DirectAuthenticationPort;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.model.LoginSession;
import java.util.Objects;
import org.springframework.stereotype.Service;

/**
 * Application orchestration for the canonical direct-login flow.
 */
@Service
public final class DirectAuthenticationApplicationService implements AuthenticateDirectUserUseCase {

    private final DirectAuthenticationPort directAuthenticationPort;
    private final AccessTokenIssuerPort accessTokenIssuer;
    private final AuthenticationSessionLifecycleApplicationService sessionLifecycle;

    public DirectAuthenticationApplicationService(
            DirectAuthenticationPort directAuthenticationPort,
            AccessTokenIssuerPort accessTokenIssuer,
            AuthenticationSessionLifecycleApplicationService sessionLifecycle
    ) {
        this.directAuthenticationPort = Objects.requireNonNull(directAuthenticationPort);
        this.accessTokenIssuer = Objects.requireNonNull(accessTokenIssuer);
        this.sessionLifecycle = Objects.requireNonNull(sessionLifecycle);
    }

    @Override
    public DirectAuthenticationResult authenticate(DirectAuthenticationCommand command) {
        Objects.requireNonNull(command, "DirectAuthenticationCommand must not be null.");

        HidraPrincipal principal = directAuthenticationPort.authenticate(
                command.providerType(),
                command.principal(),
                command.credentials()
        );
        IssuedAccessToken accessToken = accessTokenIssuer.issue(principal);
        LoginSession session = sessionLifecycle.startSession(
                principal,
                accessToken.expiresAt(),
                command.clientIp(),
                command.userAgent(),
                command.correlationId()
        );

        return new DirectAuthenticationResult(principal, session, accessToken);
    }
}
