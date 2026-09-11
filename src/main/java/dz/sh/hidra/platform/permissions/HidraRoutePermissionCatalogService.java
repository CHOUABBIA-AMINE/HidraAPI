/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraRoutePermissionCatalogService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.permissions
 *
 * @Description : Publishes route-specific permission metadata derived from registered Spring MVC mappings.
 *
 */
package dz.sh.hidra.platform.permissions;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Publishes the same route-level permission codes that are enforced by the HTTP interceptor.
 */
@Service
public final class HidraRoutePermissionCatalogService {

    private final RequestMappingHandlerMapping handlerMapping;
    private final HidraRoutePermissionNaming permissionNaming;

    public HidraRoutePermissionCatalogService(
            @Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping,
            HidraRoutePermissionNaming permissionNaming
    ) {
        this.handlerMapping = Objects.requireNonNull(handlerMapping, "RequestMappingHandlerMapping must not be null.");
        this.permissionNaming = Objects.requireNonNull(permissionNaming, "HidraRoutePermissionNaming must not be null.");
    }

    public Map<String, Object> catalog() {
        List<RoutePermissionDescriptor> routes = routes();
        return Map.of(
                "strategy", "derived-route-permission-catalog",
                "enforcement", "backend-enforced by HidraRouteAuthorizationInterceptor",
                "permissionFormat", "<module>:<resource>:<action>",
                "bootstrapAdminBypass", "ROLE_HIDRA_ADMIN",
                "routes", routes
        );
    }

    public List<RoutePermissionDescriptor> routes() {
        return handlerMapping.getHandlerMethods().entrySet().stream()
                .flatMap(entry -> descriptors(entry.getKey(), entry.getValue()).stream())
                .sorted(Comparator.comparing(RoutePermissionDescriptor::route)
                        .thenComparing(RoutePermissionDescriptor::permission))
                .toList();
    }

    private List<RoutePermissionDescriptor> descriptors(RequestMappingInfo info, HandlerMethod method) {
        Set<String> patterns = new TreeSet<>(info.getPatternValues());
        Set<String> methods = methods(info);
        return patterns.stream()
                .filter(pattern -> pattern.startsWith("/api/v1"))
                .map(pattern -> descriptor(pattern, methods, method))
                .toList();
    }

    private RoutePermissionDescriptor descriptor(String route, Set<String> methods, HandlerMethod method) {
        HidraRoutePermissionNaming.RouteParts parts = permissionNaming.routeParts(route);
        String action = permissionNaming.action(methods, route, method);
        String representativeMethod = methods.isEmpty() ? "ANY" : methods.iterator().next();
        String permission = permissionNaming.permissionFor(route, representativeMethod, method);
        return new RoutePermissionDescriptor(
                route,
                methods,
                parts.module(),
                parts.resource(),
                action,
                permission,
                "backend-enforced"
        );
    }

    private static Set<String> methods(RequestMappingInfo info) {
        Set<RequestMethod> requestMethods = info.getMethodsCondition().getMethods();
        if (requestMethods.isEmpty()) {
            return Set.of("ANY");
        }
        return requestMethods.stream()
                .map(RequestMethod::name)
                .collect(java.util.stream.Collectors.toCollection(TreeSet::new));
    }
}
