/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraProductionStartupGuard
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Fails fast when a production runtime is started with the wrong profile or incomplete mandatory configuration.
 *
 */
package dz.sh.hidra.platform.configuration;

import java.util.Arrays;
import java.util.Locale;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Enforces the minimum application-side production startup contract before bean creation.
 */
public final class HidraProductionStartupGuard implements EnvironmentPostProcessor, Ordered {

    private static final String PRODUCTION = "production";
    private static final String JWT = "jwt";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        boolean productionIntent = PRODUCTION.equalsIgnoreCase(normalize(
                firstNonBlank(
                        read(environment, "HIDRA_ENVIRONMENT"),
                        read(environment, "hidra.environment")
                )
        ));
        boolean productionProfileActive = Arrays.stream(environment.getActiveProfiles())
                .anyMatch(PRODUCTION::equalsIgnoreCase);

        if (!productionIntent && !productionProfileActive) {
            return;
        }

        if (!productionProfileActive) {
            throw new IllegalStateException(
                    "Production runtime intent requires SPRING_PROFILES_ACTIVE to include 'production'."
            );
        }

        require(environment, "spring.datasource.url", "HIDRA_DATASOURCE_URL");
        require(environment, "spring.datasource.username", "HIDRA_DATASOURCE_USERNAME");
        require(environment, "spring.datasource.password", "HIDRA_DATASOURCE_PASSWORD");

        boolean corsEnabled = Boolean.parseBoolean(normalizeOrDefault(
                read(environment, "hidra.platform.security.cors.enabled"),
                "true"
        ));
        if (corsEnabled) {
            require(environment, "hidra.platform.security.cors.allowed-origins", "HIDRA_CORS_ALLOWED_ORIGINS");
        }

        String authenticationMode = normalizeOrDefault(
                read(environment, "hidra.platform.security.authentication-mode"),
                JWT
        ).toLowerCase(Locale.ROOT);
        if (!JWT.equals(authenticationMode)) {
            throw new IllegalStateException(
                    "Production runtime requires HIDRA_SECURITY_AUTHENTICATION_MODE=jwt."
            );
        }

        require(environment, "hidra.platform.security.jwt.hmac-secret", "HIDRA_JWT_HMAC_SECRET");

        boolean bootstrapEnabled = Boolean.parseBoolean(normalizeOrDefault(
                read(environment, "hidra.security.bootstrap.enabled"),
                "false"
        ));
        if (bootstrapEnabled) {
            require(environment, "hidra.security.bootstrap.password", "HIDRA_SECURITY_BOOTSTRAP_PASSWORD");
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    private static void require(ConfigurableEnvironment environment, String propertyName, String externalName) {
        String value = read(environment, propertyName);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Production runtime requires non-empty " + externalName + " (" + propertyName + ")."
            );
        }
    }

    private static String read(ConfigurableEnvironment environment, String propertyName) {
        try {
            return environment.getProperty(propertyName);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private static String firstNonBlank(String first, String second) {
        if (first != null && !first.isBlank()) {
            return first;
        }
        return second;
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    private static String normalizeOrDefault(String value, String defaultValue) {
        String normalized = normalize(value);
        return normalized.isEmpty() ? defaultValue : normalized;
    }
}
