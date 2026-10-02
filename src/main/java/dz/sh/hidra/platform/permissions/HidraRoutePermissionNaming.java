/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraRoutePermissionNaming
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.permissions
 *
 * @Description : Derives stable lower-case business permission codes from registered HTTP routes.
 *
 */
package dz.sh.hidra.platform.permissions;

import java.util.Locale;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

/**
 * Creates the route-level permission code used consistently by catalog publication and enforcement.
 */
@Component
public final class HidraRoutePermissionNaming {

    public String permissionFor(String route, String httpMethod, HandlerMethod handlerMethod) {
        RouteParts parts = routeParts(route);
        String action = action(Set.of(normalizeMethod(httpMethod)), route, handlerMethod);
        return token(parts.module()) + ":" + token(parts.resource()) + ":" + token(action);
    }

    public String action(Set<String> methods, String route, HandlerMethod handlerMethod) {
        String methodName = handlerMethod == null ? "" : handlerMethod.getMethod().getName().toLowerCase(Locale.ROOT);
        if (route != null && route.contains("/search") || methodName.contains("search")) {
            return "search";
        }
        if ((route != null && (route.contains("/capabilities") || route.contains("/catalog")))
                || methodName.contains("capabilit")) {
            return "read";
        }
        if (methods.contains("GET")) {
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

    public RouteParts routeParts(String route) {
        String[] segments = route == null ? new String[0] : route.split("/");
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
        return new RouteParts(token(module), token(resource));
    }

    private static String normalizeMethod(String method) {
        if (method == null || method.isBlank()) {
            return "ANY";
        }
        return method.trim().toUpperCase(Locale.ROOT);
    }

    private static String segment(String[] segments, int index, String fallback) {
        if (segments.length <= index || segments[index] == null || segments[index].isBlank()) {
            return fallback;
        }
        return segments[index];
    }

    private static String token(String value) {
        return value.replaceAll("[{}]", "")
                .replace('_', '-')
                .replaceAll("[^A-Za-z0-9-]", "-")
                .replaceAll("-+", "-")
                .toLowerCase(Locale.ROOT);
    }

    public record RouteParts(String module, String resource) { }
}
