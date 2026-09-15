/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticationCompletionResult
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.model
 *
 * @Description : Returns the normalized principal, logical session, and Hidra access token after provider-neutral authentication completion.
 *
 */
package dz.sh.hidra.modules.identity.application.model;

import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.model.LoginSession;

/**
 * Provider-neutral completion result shared by direct and externally validated authentication paths.
 */
public record AuthenticationCompletionResult(
        HidraPrincipal principal,
        LoginSession session,
        IssuedAccessToken accessToken
) {
}
