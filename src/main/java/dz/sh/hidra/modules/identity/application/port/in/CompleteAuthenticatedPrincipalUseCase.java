/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CompleteAuthenticatedPrincipalUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.in
 *
 * @Description : Completes an already normalized Hidra authentication by issuing a token and creating its logical session.
 *
 */
package dz.sh.hidra.modules.identity.application.port.in;

import dz.sh.hidra.modules.identity.application.model.AuthenticationCompletionResult;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;

/**
 * Provider-neutral post-authentication completion boundary.
 */
public interface CompleteAuthenticatedPrincipalUseCase {

    AuthenticationCompletionResult complete(
            HidraPrincipal principal,
            String clientIp,
            String userAgent,
            String correlationId
    );
}
