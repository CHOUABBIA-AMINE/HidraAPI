/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraOidcAuthenticationToken
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Represents a validated OIDC identity normalized to an authenticated Hidra principal.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import java.util.Objects;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Authenticated Spring Security token carrying the normalized Hidra principal after OIDC validation.
 */
public final class HidraOidcAuthenticationToken extends AbstractAuthenticationToken {

    private final HidraPrincipal principal;

    public HidraOidcAuthenticationToken(HidraPrincipal principal) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.principal = Objects.requireNonNull(principal, "Hidra principal must not be null.");
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public HidraPrincipal getPrincipal() {
        return principal;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException("Use the authenticated OIDC token constructor.");
        }
        super.setAuthenticated(false);
    }
}
