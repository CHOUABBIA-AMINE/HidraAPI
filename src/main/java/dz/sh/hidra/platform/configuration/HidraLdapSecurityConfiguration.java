/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraLdapSecurityConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Creates LDAP connection infrastructure with production secure-transport and timeout safeguards.
 *
 */
package dz.sh.hidra.platform.configuration;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * Technical LDAP infrastructure only; credential verification belongs to the Identity LDAP adapter.
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(HidraLdapSecurityProperties.class)
public class HidraLdapSecurityConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "hidra.platform.security.ldap", name = "enabled", havingValue = "true")
    LdapContextSource hidraLdapContextSource(
            HidraLdapSecurityProperties properties,
            @Value("${hidra.environment:local}") String environment
    ) {
        validate(properties, environment);

        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(properties.url());
        contextSource.setBase(properties.baseDn());
        if (properties.bindDn() != null) {
            contextSource.setUserDn(properties.bindDn());
            contextSource.setPassword(properties.bindPassword() == null ? "" : properties.bindPassword());
        }

        Map<String, Object> environmentProperties = new HashMap<>();
        environmentProperties.put(
                "com.sun.jndi.ldap.connect.timeout",
                Long.toString(properties.connectTimeout().toMillis())
        );
        environmentProperties.put(
                "com.sun.jndi.ldap.read.timeout",
                Long.toString(properties.readTimeout().toMillis())
        );
        contextSource.setBaseEnvironmentProperties(environmentProperties);
        contextSource.afterPropertiesSet();
        return contextSource;
    }

    private static void validate(HidraLdapSecurityProperties properties, String environment) {
        requireConfigured(properties.url(), "HIDRA_LDAP_URL");
        requireConfigured(properties.baseDn(), "HIDRA_LDAP_BASE_DN");
        requireConfigured(properties.userSearchFilter(), "HIDRA_LDAP_USER_SEARCH_FILTER");

        if (properties.connectTimeout().isZero() || properties.connectTimeout().isNegative()) {
            throw new IllegalStateException("HIDRA_LDAP_CONNECT_TIMEOUT must be greater than zero.");
        }
        if (properties.readTimeout().isZero() || properties.readTimeout().isNegative()) {
            throw new IllegalStateException("HIDRA_LDAP_READ_TIMEOUT must be greater than zero.");
        }

        if (productionLike(environment) && !properties.url().toLowerCase().startsWith("ldaps://")) {
            throw new IllegalStateException(
                    "LDAP authentication must use ldaps:// in staging and production environments."
            );
        }
    }

    private static void requireConfigured(String value, String variableName) {
        if (value == null) {
            throw new IllegalStateException(variableName + " must be configured when LDAP authentication is enabled.");
        }
    }

    private static boolean productionLike(String environment) {
        if (environment == null) {
            return false;
        }
        String normalized = environment.trim().toLowerCase();
        return "production".equals(normalized) || "prod".equals(normalized) || "staging".equals(normalized);
    }
}
