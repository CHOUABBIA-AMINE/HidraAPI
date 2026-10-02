/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalAuthenticationToken
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security
 *
 * @Description : Carries an unauthenticated LOCAL username/password request for deterministic provider routing.
 *
 */
package dz.sh.hidra.platform.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.authority.AuthorityUtils;

import java.io.Serial;
import java.util.Objects;

/**
 * Provider-specific Spring Security request token for Hidra-owned LOCAL credentials.
 *
 * <p>This token is intentionally unauthenticated. Authentication providers must return
 * a separate authenticated result after credential verification.</p>
 */
public final class LocalAuthenticationToken extends AbstractAuthenticationToken implements CredentialsContainer {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String principal;
    private String credentials;

    private LocalAuthenticationToken(String principal, String credentials) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.principal = Objects.requireNonNull(principal, "Principal must not be null.");
        this.credentials = Objects.requireNonNull(credentials, "Credentials must not be null.");
        super.setAuthenticated(false);
    }

    public static LocalAuthenticationToken unauthenticated(String principal, String credentials) {
        return new LocalAuthenticationToken(principal, credentials);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException("Cannot mark a LOCAL authentication request as authenticated.");
        }
        super.setAuthenticated(false);
    }
}
