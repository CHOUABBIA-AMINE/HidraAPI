/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraSecurityConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Configures protected APIs for Hidra bearer JWTs with no ordinary in-memory authentication authority.
 *
 */
package dz.sh.hidra.platform.configuration;

import dz.sh.hidra.platform.security.HidraJwtGrantedAuthoritiesConverter;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security configuration for HidraAPI.
 */
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableMethodSecurity
public class HidraSecurityConfiguration {

    private static final String AUTHENTICATION_MODE_DISABLED = "disabled";
    private static final String AUTHENTICATION_MODE_JWT = "jwt";
    private static final String OIDC_COMPLETION_PATH = "/api/v1/identity/authentication/oidc/complete";

    /**
     * External OIDC bearer tokens are accepted only on the completion bridge that exchanges
     * an already validated external identity for the standardized Hidra session/token result.
     */
    @Bean
    @Order(1)
    @ConditionalOnExpression("${hidra.platform.security.enabled:true} && '${hidra.platform.security.authentication-mode:jwt}' == 'jwt'")
    SecurityFilterChain hidraOidcCompletionSecurityFilterChain(
            HttpSecurity http,
            @Qualifier("externalOidcJwtDecoder") JwtDecoder externalOidcJwtDecoder,
            @Qualifier("identityOidcJwtAuthenticationConverter")
            Converter<Jwt, ? extends AbstractAuthenticationToken> oidcAuthenticationConverter,
            @Value("${hidra.platform.security.csrf.enabled:false}") boolean csrfEnabled,
            @Value("${hidra.platform.security.cors.allowed-origins:}") String allowedOrigins,
            @Value("${hidra.platform.security.cors.allowed-methods:GET,POST,PUT,PATCH,DELETE,OPTIONS}") String allowedMethods,
            @Value("${hidra.platform.security.cors.allowed-headers:Authorization,Content-Type,X-Correlation-Id,X-Request-Id}") String allowedHeaders,
            @Value("${hidra.platform.security.cors.exposed-headers:X-Correlation-Id,X-Request-Id,Content-Disposition,Content-Length,Accept-Ranges}") String exposedHeaders
    ) throws Exception {
        http.securityMatcher(OIDC_COMPLETION_PATH);
        configureStatelessHttp(http, csrfEnabled, allowedOrigins, allowedMethods, allowedHeaders, exposedHeaders);
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
                .decoder(externalOidcJwtDecoder)
                .jwtAuthenticationConverter(oidcAuthenticationConverter)
        ));
        return http.build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain hidraSecurityFilterChain(
            HttpSecurity http,
            @Qualifier("hidraJwtAuthenticationConverter")
            Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter,
            @Qualifier("hidraJwtDecoder") JwtDecoder hidraJwtDecoder,
            @Value("${hidra.platform.security.enabled:true}") boolean securityEnabled,
            @Value("${hidra.platform.security.csrf.enabled:false}") boolean csrfEnabled,
            @Value("${hidra.platform.security.authentication-mode:jwt}") String authenticationMode,
            @Value("${hidra.platform.security.cors.allowed-origins:}") String allowedOrigins,
            @Value("${hidra.platform.security.cors.allowed-methods:GET,POST,PUT,PATCH,DELETE,OPTIONS}") String allowedMethods,
            @Value("${hidra.platform.security.cors.allowed-headers:Authorization,Content-Type,X-Correlation-Id,X-Request-Id}") String allowedHeaders,
            @Value("${hidra.platform.security.cors.exposed-headers:X-Correlation-Id,X-Request-Id,Content-Disposition,Content-Length,Accept-Ranges}") String exposedHeaders
    ) throws Exception {
        configureStatelessHttp(http, csrfEnabled, allowedOrigins, allowedMethods, allowedHeaders, exposedHeaders);

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
                        "/swagger-ui.html",
                        "/api/v1/security/oidc",
                        "/api/v1/identity/authentication/login"
                ).permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
        );

        if (!AUTHENTICATION_MODE_JWT.equals(normalizedMode)) {
            throw new IllegalStateException("Unsupported Hidra security authentication mode: " + authenticationMode);
        }

        http.httpBasic(AbstractHttpConfigurer::disable);
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
                .decoder(hidraJwtDecoder)
                .jwtAuthenticationConverter(jwtAuthenticationConverter)
        ));
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
    PasswordEncoder hidraPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static void configureStatelessHttp(
            HttpSecurity http,
            boolean csrfEnabled,
            String allowedOrigins,
            String allowedMethods,
            String allowedHeaders,
            String exposedHeaders
    ) throws Exception {
        if (!csrfEnabled) {
            http.csrf(AbstractHttpConfigurer::disable);
        }
        http.cors(cors -> cors.configurationSource(buildCorsConfigurationSource(
                allowedOrigins,
                allowedMethods,
                allowedHeaders,
                exposedHeaders
        )));
        http.formLogin(AbstractHttpConfigurer::disable);
        http.logout(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    static UrlBasedCorsConfigurationSource buildCorsConfigurationSource(
            String allowedOrigins,
            String allowedMethods,
            String allowedHeaders,
            String exposedHeaders
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

}
