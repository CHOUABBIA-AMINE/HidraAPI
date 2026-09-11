/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraPermissionWebMvcConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Registers backend route-authorization enforcement for Hidra API handlers.
 *
 */
package dz.sh.hidra.platform.configuration;

import dz.sh.hidra.platform.permissions.HidraRouteAuthorizationInterceptor;
import java.util.Objects;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Registers the Hidra route authorization interceptor after MVC handler resolution.
 */
@Configuration(proxyBeanMethods = false)
public final class HidraPermissionWebMvcConfiguration implements WebMvcConfigurer {

    private final HidraRouteAuthorizationInterceptor authorizationInterceptor;

    public HidraPermissionWebMvcConfiguration(HidraRouteAuthorizationInterceptor authorizationInterceptor) {
        this.authorizationInterceptor = Objects.requireNonNull(
                authorizationInterceptor,
                "HidraRouteAuthorizationInterceptor must not be null."
        );
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/api/v1/**");
    }
}
