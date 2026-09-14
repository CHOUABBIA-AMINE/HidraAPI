/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalAuthenticationToken
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security
 *
 * @Description : Represents an unauthenticated LOCAL username/password authentication request.
 *
 */
package dz.sh.hidra.platform.security;

import java.io.Serial;
import java.util.Objects;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Spring Security request token used only to route LOCAL credentials to the
 * LOCAL authentication provider.
 */
public final class LocalAuthenticationToken extends AbstractAuthenticationToken {

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
    public String getCredentials() {
        return credentials;
    }

    @Override
    public String getPrincipal() {
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
            throw new IllegalArgumentException("LOCAL authentication request token cannot be marked authenticated.");
        }
        super.setAuthenticated(false);
    }
}
