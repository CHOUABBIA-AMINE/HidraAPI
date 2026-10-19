/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraRouteAuthorizationInterceptor
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-17
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.permissions
 *
 * @Description : Enforces route-level Hidra permissions after Spring MVC resolves the concrete handler.
 *
 */
package dz.sh.hidra.platform.permissions;

import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

/**
 * Enforces the permission code published for each API route.
 */
@Component
public final class HidraRouteAuthorizationInterceptor implements HandlerInterceptor {

    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/api/v1/security/oidc",
            "/api/v1/identity/authentication/login"
    );

    private static final Set<String> AUTHENTICATED_ONLY_PATHS = Set.of(
            "/api/v1/identity/me",
            "/api/v1/identity/me/permissions"
    );

    private final HidraEffectivePermissionResolver permissionResolver;
    private final HidraRoutePermissionNaming permissionNaming;
    private final boolean securityEnabled;
    private final String authenticationMode;

    public HidraRouteAuthorizationInterceptor(
            HidraEffectivePermissionResolver permissionResolver,
            HidraRoutePermissionNaming permissionNaming,
            @Value("${hidra.platform.security.enabled:true}") boolean securityEnabled,
            @Value("${hidra.platform.security.authentication-mode:jwt}") String authenticationMode
    ) {
        this.permissionResolver = Objects.requireNonNull(permissionResolver, "Permission resolver must not be null.");
        this.permissionNaming = Objects.requireNonNull(permissionNaming, "Permission naming must not be null.");
        this.securityEnabled = securityEnabled;
        this.authenticationMode = authenticationMode == null ? "jwt" : authenticationMode.trim().toLowerCase(Locale.ROOT);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!securityEnabled || "disabled".equals(authenticationMode)) {
            return true;
        }
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        String path = request.getRequestURI();
        if (isPublicOrAuthenticatedOnly(path)) {
            return true;
        }

        Object patternAttribute = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String routePattern = patternAttribute == null ? path : patternAttribute.toString();
        if (!routePattern.startsWith("/api/v1")) {
            return true;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // The four draft operations have their own company/network-scoped native Identity
        // authority, sampled in the protected transaction. A static module:resource route
        // permission is unrelated to those paired grants and must not replace them.
        // Only this exact compiled controller/method/route tuple delegates to that owner;
        // every other route retains the ordinary effective-permission check.
        if (isOwnedEngineeringDraftRoute(handlerMethod, routePattern, request.getMethod())) {
            if (authentication == null || !authentication.isAuthenticated()
                    || authentication instanceof AnonymousAuthenticationToken) {
                throw new AccessDeniedException("Engineering draft authentication required.");
            }
            return true;
        }

        String requiredPermission = permissionNaming.permissionFor(routePattern, request.getMethod(), handlerMethod);
        if (permissionResolver.hasPermission(authentication, requiredPermission)) {
            return true;
        }

        throw new AccessDeniedException("Missing required permission: " + requiredPermission);
    }

    private static boolean isOwnedEngineeringDraftRoute(
            HandlerMethod handler, String routePattern, String method) {
        if (handler == null || routePattern == null || method == null
                || !handler.getBeanType().getName().equals(
                    "dz.sh.hidra.modules.topology.api.rest.controller.EngineeringNetworkDraftController")) {
            return false;
        }
        String selected = method + " " + routePattern + " " + handler.getMethod().getName();
        return switch (selected) {
            case "POST /api/v1/topology/engineering-networks create",
                 "GET /api/v1/topology/engineering-networks/{draftId} read",
                 "PUT /api/v1/topology/engineering-networks/{draftId} update",
                 "POST /api/v1/topology/engineering-networks/{draftId}/validate validate" -> true;
            default -> false;
        };
    }

    private static boolean isPublicOrAuthenticatedOnly(String path) {
        if (path == null) {
            return false;
        }
        if (PUBLIC_PATHS.contains(path)) {
            return true;
        }
        if (path.startsWith("/api/v1/realtime/ws")) {
            return true;
        }
        return AUTHENTICATED_ONLY_PATHS.contains(path);
    }
}
