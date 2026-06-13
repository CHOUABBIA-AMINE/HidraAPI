/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraRoutePermissionCatalogService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
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
import java.util.Locale;
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
 * Publishes route-specific permission metadata derived from registered Spring MVC mappings.
 */
@Service
public class HidraRoutePermissionCatalogService {

    private final RequestMappingHandlerMapping handlerMapping;

    public HidraRoutePermissionCatalogService(
            @Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping
    ) {
        this.handlerMapping = Objects.requireNonNull(handlerMapping, "RequestMappingHandlerMapping must not be null.");
    }

    public Map<String, Object> catalog() {
        List<RoutePermissionDescriptor> routes = routes();
        return Map.of(
                "strategy", "derived-route-permission-catalog",
                "enforcement", "catalog-only; backend currently authenticates all operational routes and does not expose route-specific @PreAuthorize evidence",
                "permissionFormat", "HIDRA_<MODULE>_<RESOURCE>_<ACTION>",
                "routes", routes
        );
    }

    public List<RoutePermissionDescriptor> routes() {
        return handlerMapping.getHandlerMethods().entrySet().stream()
                .flatMap(entry -> descriptors(entry.getKey(), entry.getValue()).stream())
                .sorted(Comparator.comparing(RoutePermissionDescriptor::route).thenComparing(RoutePermissionDescriptor::permission))
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
        RouteParts parts = routeParts(route);
        String action = action(methods, route, method);
        String permission = "HIDRA_"
                + token(parts.module())
                + "_"
                + token(parts.resource())
                + "_"
                + token(action);
        return new RoutePermissionDescriptor(
                route,
                methods,
                parts.module(),
                parts.resource(),
                action,
                permission,
                "metadata-published; route-specific authorization annotations unavailable from current HidraAPI evidence"
        );
    }

    private static Set<String> methods(RequestMappingInfo info) {
        Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
        if (methods.isEmpty()) {
            return Set.of("ANY");
        }
        return methods.stream().map(RequestMethod::name).collect(java.util.stream.Collectors.toCollection(TreeSet::new));
    }

    private static RouteParts routeParts(String route) {
        String[] segments = route.split("/");
        String module = segment(segments, 3, "platform");
        String resource = segment(segments, 4, "root");
        if ("workbench".equals(module) && segments.length > 4) {
            module = segment(segments, 4, "dynamic-module");
            resource = segment(segments, 5, "resources");
        }
        if (resource.startsWith("{")) {
            resource = "dynamic-resource";
        }
        if (module.startsWith("{")) {
            module = "dynamic-module";
        }
        return new RouteParts(module, resource);
    }

    private static String action(Set<String> methods, String route, HandlerMethod method) {
        String methodName = method.getMethod().getName().toLowerCase(Locale.ROOT);
        if (route.contains("/search") || methodName.contains("search")) {
            return "search";
        }
        if (route.contains("/capabilities") || route.contains("/catalog") || methodName.contains("capabilit")) {
            return "read";
        }
        if (methods.contains("GET")) {
            if (route.endsWith("/{id}")) {
                return "detail";
            }
            return "read";
        }
        if (methods.contains("POST")) {
            return "execute";
        }
        if (methods.contains("PUT") || methods.contains("PATCH")) {
            return "update";
        }
        if (methods.contains("DELETE")) {
            return "delete";
        }
        return "access";
    }

    private static String segment(String[] segments, int index, String fallback) {
        if (segments.length <= index || segments[index] == null || segments[index].isBlank()) {
            return fallback;
        }
        return segments[index];
    }

    private static String token(String value) {
        return value.replaceAll("[{}]", "")
                .replace('-', '_')
                .replaceAll("[^A-Za-z0-9_]", "_")
                .toUpperCase(Locale.ROOT);
    }

    private record RouteParts(String module, String resource) { }
}
