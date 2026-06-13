/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraSecurityConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Configures Spring Security with JWT resource-server support and optional bootstrap Basic authentication.
 *
 */
package dz.sh.hidra.platform.configuration;


import dz.sh.hidra.platform.security.HidraJwtGrantedAuthoritiesConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Spring Security configuration for HidraAPI.
 */
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableMethodSecurity
public class HidraSecurityConfiguration {

    private static final String DEVELOPMENT_PASSWORD = "hidra-dev-change-me";
    private static final String AUTHENTICATION_MODE_DISABLED = "disabled";
    private static final String AUTHENTICATION_MODE_BASIC = "basic";
    private static final String AUTHENTICATION_MODE_JWT = "jwt";

    @Bean
    SecurityFilterChain hidraSecurityFilterChain(
            HttpSecurity http,
            @Qualifier("hidraCorsConfigurationSource") CorsConfigurationSource corsConfigurationSource,
            JwtAuthenticationConverter jwtAuthenticationConverter,
            @Value("${hidra.platform.security.enabled:true}") boolean securityEnabled,
            @Value("${hidra.platform.security.csrf.enabled:false}") boolean csrfEnabled,
            @Value("${hidra.platform.security.authentication-mode:jwt}") String authenticationMode
    ) throws Exception {
        if (!csrfEnabled) {
            http.csrf(AbstractHttpConfigurer::disable);
        }

        http.cors(cors -> cors.configurationSource(corsConfigurationSource));
        http.formLogin(AbstractHttpConfigurer::disable);
        http.logout(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        String normalizedMode = normalize(authenticationMode, AUTHENTICATION_MODE_JWT).toLowerCase();

        if (!securityEnabled || AUTHENTICATION_MODE_DISABLED.equals(normalizedMode)) {
            http.httpBasic(AbstractHttpConfigurer::disable);
            http.oauth2ResourceServer(AbstractHttpConfigurer::disable);
            http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
            return http.build();
        }

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                        "/actuator/health",
                        "/actuator/health/**",
                        "/actuator/info",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                ).permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
        );

        if (AUTHENTICATION_MODE_BASIC.equals(normalizedMode)) {
            http.oauth2ResourceServer(AbstractHttpConfigurer::disable);
            http.httpBasic(Customizer.withDefaults());
            return http.build();
        }

        if (!AUTHENTICATION_MODE_JWT.equals(normalizedMode)) {
            throw new IllegalStateException("Unsupported Hidra security authentication mode: " + authenticationMode);
        }

        http.httpBasic(AbstractHttpConfigurer::disable);
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));
        return http.build();
    }

    @Bean
    JwtAuthenticationConverter hidraJwtAuthenticationConverter(
            @Value("${hidra.platform.security.jwt.principal-claim:sub}") String principalClaim,
            @Value("${hidra.platform.security.jwt.roles-claim:roles}") String rolesClaim,
            @Value("${hidra.platform.security.jwt.scope-claim:scope}") String scopeClaim,
            @Value("${hidra.platform.security.jwt.authority-prefix:ROLE_}") String authorityPrefix
    ) {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setPrincipalClaimName(normalize(principalClaim, "sub"));
        converter.setJwtGrantedAuthoritiesConverter(new HidraJwtGrantedAuthoritiesConverter(
                normalize(rolesClaim, "roles"),
                normalize(scopeClaim, "scope"),
                normalize(authorityPrefix, "ROLE_")
        ));
        return converter;
    }

    @Bean
    UserDetailsService hidraBootstrapUserDetailsService(
            PasswordEncoder passwordEncoder,
            @Value("${hidra.environment:local}") String environment,
            @Value("${hidra.platform.security.authentication-mode:jwt}") String authenticationMode,
            @Value("${hidra.security.bootstrap.username:hidra-admin}") String username,
            @Value("${hidra.security.bootstrap.password:}") String password,
            @Value("${hidra.security.bootstrap.roles:HIDRA_ADMIN}") String roles
    ) {
        String normalizedMode = normalize(authenticationMode, AUTHENTICATION_MODE_JWT).toLowerCase();
        if (!AUTHENTICATION_MODE_BASIC.equals(normalizedMode)) {
            return new InMemoryUserDetailsManager();
        }

        String normalizedEnvironment = normalize(environment, "local");
        String normalizedUsername = normalize(username, "hidra-admin");
        String normalizedPassword = normalize(password, null);
        String effectivePassword = normalizedPassword == null ? DEVELOPMENT_PASSWORD : normalizedPassword;

        if (productionLike(normalizedEnvironment) && DEVELOPMENT_PASSWORD.equals(effectivePassword)) {
            throw new IllegalStateException("HIDRA_SECURITY_BOOTSTRAP_PASSWORD must be set for production-like environments when basic authentication is enabled.");
        }

        UserDetails bootstrapUser = User.withUsername(normalizedUsername)
                .password(passwordEncoder.encode(effectivePassword))
                .roles(roleArray(roles))
                .build();

        return new InMemoryUserDetailsManager(bootstrapUser);
    }

    @Bean
    PasswordEncoder hidraPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource hidraCorsConfigurationSource(
            @Value("${hidra.platform.security.cors.allowed-origins:}") String allowedOrigins,
            @Value("${hidra.platform.security.cors.allowed-methods:GET,POST,PUT,PATCH,DELETE,OPTIONS}") String allowedMethods,
            @Value("${hidra.platform.security.cors.allowed-headers:Authorization,Content-Type,X-Correlation-Id,X-Request-Id}") String allowedHeaders,
            @Value("${hidra.platform.security.cors.exposed-headers:X-Correlation-Id,X-Request-Id}") String exposedHeaders
    ) {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(csv(allowedOrigins));
        configuration.setAllowedMethods(csv(allowedMethods));
        configuration.setAllowedHeaders(csv(allowedHeaders));
        configuration.setExposedHeaders(csv(exposedHeaders));
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private static String normalize(String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value.trim();
    }

    private static List<String> csv(String value) {
        if (value == null || value.isBlank()) {
            return List.of();
        }
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(item -> !item.isBlank())
                .toList();
    }

    private static String[] roleArray(String roles) {
        List<String> normalizedRoles = csv(roles).stream()
                .map(role -> role.startsWith("ROLE_") ? role.substring("ROLE_".length()) : role)
                .filter(role -> !role.isBlank())
                .toList();
        if (normalizedRoles.isEmpty()) {
            return new String[] { "HIDRA_ADMIN" };
        }
        return normalizedRoles.toArray(String[]::new);
    }

    private static boolean productionLike(String environment) {
        String normalized = normalize(environment, "local").toLowerCase();
        return "production".equals(normalized) || "prod".equals(normalized) || "staging".equals(normalized);
    }
}
