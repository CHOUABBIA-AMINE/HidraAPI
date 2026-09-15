/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraLdapSecurityProperties
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Binds externalized LDAP connection, search, bind, and timeout settings for Hidra authentication.
 *
 */
package dz.sh.hidra.platform.configuration;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Externalized LDAP/Active Directory technical connection settings.
 */
@ConfigurationProperties(prefix = "hidra.platform.security.ldap")
public record HidraLdapSecurityProperties(
        boolean enabled,
        String url,
        String baseDn,
        String userSearchBase,
        String userSearchFilter,
        String bindDn,
        String bindPassword,
        Duration connectTimeout,
        Duration readTimeout
) {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(5);

    public HidraLdapSecurityProperties {
        url = normalize(url);
        baseDn = normalize(baseDn);
        userSearchBase = normalize(userSearchBase);
        userSearchFilter = normalize(userSearchFilter);
        bindDn = normalize(bindDn);
        bindPassword = normalizeSecret(bindPassword);
        connectTimeout = connectTimeout == null ? DEFAULT_TIMEOUT : connectTimeout;
        readTimeout = readTimeout == null ? DEFAULT_TIMEOUT : readTimeout;
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private static String normalizeSecret(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return value;
    }
}
