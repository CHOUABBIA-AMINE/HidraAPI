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
 * @Description : Orchestrates provider-selected direct authentication and delegates provider-neutral session/token completion.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.model.AuthenticationCompletionResult;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationResult;
import dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.CompleteAuthenticatedPrincipalUseCase;
import dz.sh.hidra.modules.identity.application.port.out.DirectAuthenticationPort;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import java.util.Objects;
import org.springframework.stereotype.Service;

/**
 * Application orchestration for the canonical direct-login flow.
 */
@Service
public final class DirectAuthenticationApplicationService implements AuthenticateDirectUserUseCase {

    private final DirectAuthenticationPort directAuthenticationPort;
    private final CompleteAuthenticatedPrincipalUseCase completionUseCase;

    public DirectAuthenticationApplicationService(
            DirectAuthenticationPort directAuthenticationPort,
            CompleteAuthenticatedPrincipalUseCase completionUseCase
    ) {
        this.directAuthenticationPort = Objects.requireNonNull(directAuthenticationPort);
        this.completionUseCase = Objects.requireNonNull(completionUseCase);
    }

    @Override
    public DirectAuthenticationResult authenticate(DirectAuthenticationCommand command) {
        Objects.requireNonNull(command, "DirectAuthenticationCommand must not be null.");

        HidraPrincipal principal = directAuthenticationPort.authenticate(
                command.providerType(),
                command.principal(),
                command.credentials()
        );
        AuthenticationCompletionResult completion = completionUseCase.complete(
                principal,
                command.clientIp(),
                command.userAgent(),
                command.correlationId()
        );
        return new DirectAuthenticationResult(
                completion.principal(),
                completion.session(),
                completion.accessToken()
        );
    }
}
