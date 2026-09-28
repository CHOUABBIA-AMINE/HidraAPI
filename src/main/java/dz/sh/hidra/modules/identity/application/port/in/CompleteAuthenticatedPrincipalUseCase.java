/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CompleteAuthenticatedPrincipalUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.in
 *
 * @Description : Completes an already normalized Hidra authentication through application-owned input/output contracts.
 *
 */
package dz.sh.hidra.modules.identity.application.port.in;

import dz.sh.hidra.modules.identity.application.model.AuthenticatedPrincipalInput;
import dz.sh.hidra.modules.identity.application.model.AuthenticationResult;

/**
 * Provider-neutral post-authentication completion boundary.
 */
public interface CompleteAuthenticatedPrincipalUseCase {

    AuthenticationResult complete(
            AuthenticatedPrincipalInput principal,
            String clientIp,
            String userAgent,
            String correlationId
    );
}
