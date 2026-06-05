/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SecurityConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security.authentication
 *
 * @Description : Defines initial technical Spring Security filter-chain configuration.
 *
 */
package dz.sh.hidra.platform.security.authentication;

import dz.sh.hidra.platform.configuration.HidraPlatformProperties;
import dz.sh.hidra.platform.security.authorization.RestAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration(proxyBeanMethods = false)
public class SecurityConfiguration {

    private static final Profiles DEV_PROFILE = Profiles.of("dev");

    private static final String[] PUBLIC_OPERATIONAL_ENDPOINTS = {
            "/actuator/health",
            "/actuator/health/**",
            "/actuator/info"
    };

    private static final String[] DEV_PUBLIC_ENDPOINTS = {
            "/actuator/health",
            "/actuator/health/**",
            "/actuator/info",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            HidraPlatformProperties properties,
            RestAuthenticationEntryPoint authenticationEntryPoint,
            RestAccessDeniedHandler accessDeniedHandler,
            Environment environment) throws Exception {
        if (!properties.security().enabled()) {
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                    .build();
        }

        configureCsrf(http, properties);
        configureCors(http, properties);

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(publicEndpointsFor(environment)).permitAll()
                        .anyRequest().authenticated());

        configureHttpBasic(http, environment);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(HidraPlatformProperties properties) {
        CorsConfiguration configuration = new CorsConfiguration();
        HidraPlatformProperties.Cors cors = properties.security().cors();
        configuration.setAllowedOrigins(cors.allowedOrigins());
        configuration.setAllowedMethods(cors.allowedMethods());
        configuration.setAllowedHeaders(cors.allowedHeaders());
        configuration.setExposedHeaders(cors.exposedHeaders());
        configuration.setAllowCredentials(false);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private static void configureCsrf(HttpSecurity http, HidraPlatformProperties properties) throws Exception {
        if (properties.security().csrf().enabled()) {
            http.csrf(Customizer.withDefaults());
            return;
        }
        http.csrf(AbstractHttpConfigurer::disable);
    }

    private static void configureCors(HttpSecurity http, HidraPlatformProperties properties) throws Exception {
        if (properties.security().cors().enabled()) {
            http.cors(Customizer.withDefaults());
            return;
        }
        http.cors(AbstractHttpConfigurer::disable);
    }

    private static void configureHttpBasic(HttpSecurity http, Environment environment) throws Exception {
        if (isDevProfile(environment)) {
            http.httpBasic(Customizer.withDefaults());
            return;
        }
        http.httpBasic(AbstractHttpConfigurer::disable);
    }

    private static String[] publicEndpointsFor(Environment environment) {
        if (isDevProfile(environment)) {
            return DEV_PUBLIC_ENDPOINTS;
        }
        return PUBLIC_OPERATIONAL_ENDPOINTS;
    }

    private static boolean isDevProfile(Environment environment) {
        return environment.acceptsProfiles(DEV_PROFILE);
    }
}
