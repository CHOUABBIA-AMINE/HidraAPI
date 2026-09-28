/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAuthenticationWebMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.mapper
 *
 * @Description : Adapts Spring-injected authentication representation and application results at the HTTP boundary.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.mapper;

import dz.sh.hidra.modules.identity.api.rest.response.AuthenticationLoginResponse;
import dz.sh.hidra.modules.identity.application.model.AuthenticatedPrincipalInput;
import dz.sh.hidra.modules.identity.application.model.AuthenticationResult;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import org.springframework.security.access.AccessDeniedException;

/**
 * Keeps Spring/domain principal representation outside the authentication controller contract.
 */
public final class IdentityAuthenticationWebMapper {

    private IdentityAuthenticationWebMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static AuthenticatedPrincipalInput oidcPrincipal(Object principal) {
        if (!(principal instanceof HidraPrincipal hidraPrincipal)
                || hidraPrincipal.authenticationType() != ProviderType.OIDC) {
            throw new AccessDeniedException(
                    "OIDC completion requires an externally validated OIDC Hidra principal."
            );
        }
        return new AuthenticatedPrincipalInput(
                hidraPrincipal.userId(),
                hidraPrincipal.username(),
                hidraPrincipal.displayName(),
                hidraPrincipal.authenticationType(),
                hidraPrincipal.identityProviderId(),
                hidraPrincipal.roles(),
                hidraPrincipal.permissions()
        );
    }

    public static AuthenticationLoginResponse response(AuthenticationResult result) {
        return new AuthenticationLoginResponse(
                result.sessionId(),
                result.accessToken(),
                result.tokenType(),
                result.jti(),
                result.issuedAt(),
                result.expiresAt(),
                result.userId(),
                result.username(),
                result.displayName(),
                result.authenticationType(),
                result.identityProviderId(),
                result.roles(),
                result.permissions()
        );
    }
}
