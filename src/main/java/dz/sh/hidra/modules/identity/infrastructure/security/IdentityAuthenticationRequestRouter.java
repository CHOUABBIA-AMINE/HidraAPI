/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAuthenticationRequestRouter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Routes an explicit Identity provider selection to the corresponding Spring authentication request type.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.platform.security.LdapAuthenticationToken;
import dz.sh.hidra.platform.security.LocalAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Converts supported direct-credential provider selections into provider-specific
 * Spring Security authentication requests without verifying credentials.
 *
 * <p>OIDC intentionally remains on the existing browser authorization-code/PKCE and
 * bearer-JWT resource-server path. This router never converts an OIDC selection into
 * a username/password request and never falls back to another provider.</p>
 */
@Component
public final class IdentityAuthenticationRequestRouter {

    /**
     * Creates a provider-specific direct-credential authentication request.
     *
     * @param providerType explicitly selected Identity provider type
     * @param principal submitted username/principal
     * @param credentials submitted direct credential
     * @return an unauthenticated LOCAL or LDAP/Active Directory request token
     * @throws NullPointerException if provider type is null
     * @throws IllegalArgumentException when the provider does not use this direct-credential route
     */
    public Authentication route(
            ProviderType providerType,
            String principal,
            String credentials
    ) {
        Objects.requireNonNull(providerType, "Provider type must not be null.");

        return switch (providerType) {
            case LOCAL -> LocalAuthenticationToken.unauthenticated(principal, credentials);
            case LDAP, ACTIVE_DIRECTORY -> LdapAuthenticationToken.unauthenticated(principal, credentials);
            case OIDC -> throw new IllegalArgumentException(
                    "OIDC authentication uses the existing external OIDC flow, not direct credentials."
            );
            default -> throw new IllegalArgumentException(
                    "Unsupported direct authentication provider type: " + providerType
            );
        };
    }
}
