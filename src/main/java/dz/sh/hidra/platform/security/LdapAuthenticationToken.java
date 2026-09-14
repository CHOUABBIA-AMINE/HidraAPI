/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LdapAuthenticationToken
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security
 *
 * @Description : Represents an unauthenticated LDAP or Active Directory username/password authentication request.
 *
 */
package dz.sh.hidra.platform.security;

import java.io.Serial;
import java.util.Objects;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Spring Security request token used only to route LDAP/Active Directory
 * credentials to the directory authentication provider.
 */
public final class LdapAuthenticationToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String principal;
    private String credentials;

    private LdapAuthenticationToken(String principal, String credentials) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.principal = Objects.requireNonNull(principal, "Principal must not be null.");
        this.credentials = Objects.requireNonNull(credentials, "Credentials must not be null.");
        super.setAuthenticated(false);
    }

    public static LdapAuthenticationToken unauthenticated(String principal, String credentials) {
        return new LdapAuthenticationToken(principal, credentials);
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
            throw new IllegalArgumentException("LDAP authentication request token cannot be marked authenticated.");
        }
        super.setAuthenticated(false);
    }
}
