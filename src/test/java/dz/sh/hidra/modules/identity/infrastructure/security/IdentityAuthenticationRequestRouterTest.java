/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAuthenticationRequestRouterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Verifies deterministic provider-selection routing to provider-specific authentication request tokens.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.platform.security.LdapAuthenticationToken;
import dz.sh.hidra.platform.security.LocalAuthenticationToken;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

class IdentityAuthenticationRequestRouterTest {

    private final IdentityAuthenticationRequestRouter router = new IdentityAuthenticationRequestRouter();

    @Test
    void routesLocalOnlyToLocalAuthenticationToken() {
        Authentication authentication = router.route(ProviderType.LOCAL, "operator", "secret");

        assertThat(authentication).isInstanceOf(LocalAuthenticationToken.class);
        assertThat(authentication.getPrincipal()).isEqualTo("operator");
        assertThat(authentication.getCredentials()).isEqualTo("secret");
        assertThat(authentication.isAuthenticated()).isFalse();
    }

    @Test
    void routesLdapAndActiveDirectoryOnlyToLdapAuthenticationToken() {
        Authentication ldap = router.route(ProviderType.LDAP, "operator", "secret");
        Authentication activeDirectory = router.route(ProviderType.ACTIVE_DIRECTORY, "operator", "secret");

        assertThat(ldap).isInstanceOf(LdapAuthenticationToken.class);
        assertThat(activeDirectory).isInstanceOf(LdapAuthenticationToken.class);
        assertThat(ldap.getPrincipal()).isEqualTo("operator");
        assertThat(activeDirectory.getPrincipal()).isEqualTo("operator");
        assertThat(ldap.isAuthenticated()).isFalse();
        assertThat(activeDirectory.isAuthenticated()).isFalse();
    }

    @Test
    void rejectsOidcFromDirectCredentialRouting() {
        assertThatThrownBy(() -> router.route(ProviderType.OIDC, "operator", "secret"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("external OIDC flow");
    }

    @Test
    void rejectsEveryOtherUnsupportedDirectProviderTypeWithoutFallback() {
        assertThat(ProviderType.values())
                .filteredOn(providerType -> providerType != ProviderType.LOCAL
                        && providerType != ProviderType.LDAP
                        && providerType != ProviderType.ACTIVE_DIRECTORY
                        && providerType != ProviderType.OIDC)
                .allSatisfy(providerType -> assertThatThrownBy(
                        () -> router.route(providerType, "operator", "secret")
                )
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("Unsupported direct authentication provider type"));
    }

    @Test
    void rejectsMissingProviderSelection() {
        assertThatThrownBy(() -> router.route(null, "operator", "secret"))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Provider type must not be null.");
    }
}
