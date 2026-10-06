/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraOidcAuthenticationToken
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
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
import dz.sh.hidra.modules.identity.application.model.VerifiedAuthorizationAssertion;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Authenticated Spring Security token carrying the normalized Hidra principal after OIDC validation.
 */
public final class HidraOidcAuthenticationToken extends AbstractAuthenticationToken {

    private final HidraPrincipal principal;

    private final VerifiedAuthorizationAssertion authorizationAssertion;
    public HidraOidcAuthenticationToken(HidraPrincipal principal) { this(principal,null); }
    public HidraOidcAuthenticationToken(HidraPrincipal principal,VerifiedAuthorizationAssertion assertion) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.principal = Objects.requireNonNull(principal, "Hidra principal must not be null.");
        if(assertion!=null && (!principal.userId().equals(assertion.userId())
                || !Objects.equals(principal.identityProviderId(),assertion.providerId())
                || !Objects.equals(principal.externalIdentityId(),assertion.externalIdentityId())))
            throw new IllegalArgumentException("Authorization assertion must belong to the authenticated principal.");
        this.authorizationAssertion=assertion;
        super.setAuthenticated(true);
    }

    public VerifiedAuthorizationAssertion authorizationAssertion() { return authorizationAssertion; }

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
