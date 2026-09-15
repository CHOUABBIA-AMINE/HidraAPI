/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraLdapSecurityConfigurationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Verifies LDAP infrastructure requires secure transport in production-like environments and valid timeout configuration.
 *
 */
package dz.sh.hidra.platform.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.ldap.core.support.LdapContextSource;

class HidraLdapSecurityConfigurationTest {

    private final HidraLdapSecurityConfiguration configuration = new HidraLdapSecurityConfiguration();

    @Test
    void rejectsPlainLdapTransportInProduction() {
        HidraLdapSecurityProperties properties = properties("ldap://directory.example.invalid:389");

        assertThatThrownBy(() -> configuration.hidraLdapContextSource(properties, "production"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("LDAP authentication must use ldaps:// in staging and production environments.");
    }

    @Test
    void rejectsPlainLdapTransportInStaging() {
        HidraLdapSecurityProperties properties = properties("ldap://directory.example.invalid:389");

        assertThatThrownBy(() -> configuration.hidraLdapContextSource(properties, "staging"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("LDAP authentication must use ldaps:// in staging and production environments.");
    }

    @Test
    void acceptsLdapsTransportInProduction() {
        HidraLdapSecurityProperties properties = properties("ldaps://directory.example.invalid:636");

        LdapContextSource contextSource = configuration.hidraLdapContextSource(properties, "production");

        assertThat(contextSource).isNotNull();
        assertThat(contextSource.getBaseLdapPathAsString()).isEqualTo("dc=example,dc=invalid");
    }

    @Test
    void permitsPlainLdapOnlyOutsideProductionLikeEnvironments() {
        HidraLdapSecurityProperties properties = properties("ldap://directory.example.invalid:389");

        LdapContextSource contextSource = configuration.hidraLdapContextSource(properties, "test");

        assertThat(contextSource).isNotNull();
    }

    @Test
    void rejectsNonPositiveConnectionAndReadTimeouts() {
        HidraLdapSecurityProperties zeroConnectTimeout = new HidraLdapSecurityProperties(
                true,
                "ldaps://directory.example.invalid:636",
                "DC=example,DC=invalid",
                "OU=Users",
                "(userPrincipalName={0})",
                null,
                null,
                Duration.ZERO,
                Duration.ofSeconds(5)
        );
        HidraLdapSecurityProperties zeroReadTimeout = new HidraLdapSecurityProperties(
                true,
                "ldaps://directory.example.invalid:636",
                "DC=example,DC=invalid",
                "OU=Users",
                "(userPrincipalName={0})",
                null,
                null,
                Duration.ofSeconds(5),
                Duration.ZERO
        );

        assertThatThrownBy(() -> configuration.hidraLdapContextSource(zeroConnectTimeout, "production"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("HIDRA_LDAP_CONNECT_TIMEOUT must be greater than zero.");
        assertThatThrownBy(() -> configuration.hidraLdapContextSource(zeroReadTimeout, "production"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("HIDRA_LDAP_READ_TIMEOUT must be greater than zero.");
    }

    private static HidraLdapSecurityProperties properties(String url) {
        return new HidraLdapSecurityProperties(
                true,
                url,
                "DC=example,DC=invalid",
                "OU=Users",
                "(userPrincipalName={0})",
                "CN=Service,OU=System,DC=example,DC=invalid",
                "external-secret",
                Duration.ofSeconds(2),
                Duration.ofSeconds(3)
        );
    }
}
