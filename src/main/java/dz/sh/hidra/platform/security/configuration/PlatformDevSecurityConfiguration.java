/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlatformDevSecurityConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security.configuration
 *
 * @Description : Development-only Spring Security baseline for local HidraAPI usage.
 *
 */
package dz.sh.hidra.platform.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Development-only Spring Security baseline for local HidraAPI usage.
 *
 * <p>Business role:
 * Provides a predictable local development user so the application no longer relies on Spring
 * Security's generated password during local boot checks.
 *
 * <p>Architecture role:
 * This class belongs to platform security configuration. It does not configure business identity
 * users, roles, permissions, organization employees, topology assets, or production authentication.
 *
 * <p>Validation:
 * The bean is active only when the <code>dev</code> profile is active. Production, staging, and test
 * profiles are not weakened by this configuration.
 *
 * <p>Usage:
 * Use the local user only for development requests to protected business APIs. Health and OpenAPI
 * documentation endpoints remain publicly accessible in the dev profile.
 */
@Configuration(proxyBeanMethods = false)
@Profile("dev")
public class PlatformDevSecurityConfiguration {

    /** Development username. */
    public static final String DEV_USERNAME = "hidra-dev";

    /** Development password. */
    public static final String DEV_PASSWORD = "hidra-dev";

    /** Development role. */
    public static final String DEV_ROLE = "DEV";

    /**
     * Creates a deterministic development-only in-memory user store.
     *
     * @return development user details service
     */
    @Bean
    public UserDetailsService devUserDetailsService() {
        return new InMemoryUserDetailsManager(User
                .withUsername(DEV_USERNAME)
                .password("{noop}" + DEV_PASSWORD)
                .roles(DEV_ROLE)
                .build());
    }
}
