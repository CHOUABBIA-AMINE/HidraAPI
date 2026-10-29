/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraRouteAuthorizationInterceptor
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
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
        // Owned operations have their own paired resource-scoped native Identity
        // authority, sampled in the protected transaction. A static module:resource route
        // permission is unrelated to those paired grants and must not replace them.
        // Only this exact compiled controller/method/route tuple delegates to that owner;
        // every other route retains the ordinary effective-permission check.
        if (isOwnedEngineeringDraftRoute(handlerMethod, routePattern, request.getMethod())
                || isOwnedEngineeringPublicationRoute(handlerMethod,routePattern,request.getMethod())
                || isOwnedEmployeeAppointmentRoute(handlerMethod,routePattern,request.getMethod())
                || isOwnedLocalExchangeRoute(handlerMethod,routePattern,request.getMethod())
                || isOwnedProductDraftRoute(handlerMethod,routePattern,request.getMethod())
                || isOwnedSuppliedFluidRoute(handlerMethod,routePattern,request.getMethod())
                || isOwnedProductPublicationRoute(handlerMethod,routePattern,request.getMethod())
                || isOwnedInstalledUnitLifecycleRoute(handlerMethod,routePattern,request.getMethod())) {
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


    // Only these compiled HTTP handlers may defer the generic route grant to the
    // installed-unit owner's current, resource-scoped native Identity authority.
    // The controller still requires a real authenticated principal; every command
    // and read passes through the protected use case and its transactional fences.
    private static boolean isOwnedInstalledUnitLifecycleRoute(HandlerMethod handler,String route,String verb) {
        if(handler==null || route==null || verb==null || !handler.getBeanType().getName().equals(
                "dz.sh.hidra.modules.topology.api.rest.controller.InstalledUnitLifecycleController"))return false;
        return switch(verb+" "+route+" "+handler.getMethod().getName()) {
            case "PUT /api/v1/topology/installed-units/{unitId} register",
                 "GET /api/v1/topology/installed-units/{unitId} readUnit",
                 "POST /api/v1/topology/installed-units/{unitId}/installations record",
                 "POST /api/v1/topology/installed-units/{unitId}/installations/{installationId}/close close",
                 "POST /api/v1/topology/installed-units/{unitId}/installations/{installationId}/relocate relocate",
                 "POST /api/v1/topology/installed-units/{unitId}/installations/{installationId}/replace replace",
                 "GET /api/v1/topology/installed-units/{unitId}/installations/{installationId} readInstallation",
                 "GET /api/v1/topology/installed-units/{unitId}/installations/{installationId}/history history" -> true;
            default -> false;
        };
    }

    private static boolean isOwnedProductPublicationRoute(HandlerMethod handler,String route,String verb) {
        if(handler==null || route==null || verb==null || !handler.getBeanType().getName().equals("dz.sh.hidra.modules.custody.api.rest.controller.ProductRevisionPublicationController"))return false;
        return switch(verb+" "+route+" "+handler.getMethod().getName()) {
            case "POST /api/v1/custody/engineering-products/{draftId}/publications/candidates prepare",
                 "GET /api/v1/custody/engineering-products/{draftId}/publications/candidates/{digest} candidate",
                 "POST /api/v1/custody/engineering-products/{draftId}/publications publish",
                 "POST /api/v1/custody/engineering-products/{draftId}/publications/{publicationId}/withdraw withdraw",
                 "POST /api/v1/custody/engineering-products/{draftId}/publications/{publicationId}/reactivate reactivate",
                 "GET /api/v1/custody/engineering-products/{draftId}/publications/{publicationId} exact",
                 "GET /api/v1/custody/engineering-products/{draftId}/publications/current current",
                 "GET /api/v1/custody/engineering-products/{draftId}/publications/effective effective",
                 "GET /api/v1/custody/engineering-products/{draftId}/publications/history history",
                 "GET /api/v1/custody/engineering-products/{draftId}/publications/requests/{requestId} receipt" -> true;
            default -> false;
        };
    }

    private static boolean isOwnedSuppliedFluidRoute(HandlerMethod handler,String route,String verb) {
        if(handler==null || route==null || verb==null || !handler.getBeanType().getName().equals(
                "dz.sh.hidra.modules.custody.api.rest.controller.SuppliedFluidPropertyRevisionController"))return false;
        return switch(verb+" "+route+" "+handler.getMethod().getName()) {
            case "GET /api/v1/custody/engineering-products/{draftId}/properties read",
                 "PUT /api/v1/custody/engineering-products/{draftId}/properties write" -> true;
            default -> false;
        };
    }

    private static boolean isOwnedProductDraftRoute(HandlerMethod handler,String route,String verb) {
        if(handler==null || route==null || verb==null || !handler.getBeanType().getName().equals(
                "dz.sh.hidra.modules.custody.api.rest.controller.ProductEngineeringDraftController"))return false;
        return switch(verb+" "+route+" "+handler.getMethod().getName()) {
            case "POST /api/v1/custody/engineering-products create",
                 "GET /api/v1/custody/engineering-products/{draftId} read",
                 "PUT /api/v1/custody/engineering-products/{draftId} update" -> true;
            default -> false;
        };
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
                 "POST /api/v1/topology/engineering-networks/{draftId}/validate validate",
                 "GET /api/v1/topology/engineering-networks/{draftId}/graph readGraph",
                 "PUT /api/v1/topology/engineering-networks/{draftId}/graph updateGraph",
                 "GET /api/v1/topology/engineering-networks/{draftId}/survey/{branchId} readSurvey",
                 "PUT /api/v1/topology/engineering-networks/{draftId}/survey/{branchId} updateSurvey" -> true;
            default -> false;
        };
    }


    private static boolean isOwnedEngineeringPublicationRoute(HandlerMethod handler,String routePattern,String method) {
        if(handler==null || routePattern==null || method==null || !handler.getBeanType().getName().equals(
            "dz.sh.hidra.modules.topology.api.rest.controller.EngineeringRevisionPublicationController"))return false;
        return switch(method+" "+routePattern+" "+handler.getMethod().getName()) {
            case "POST /api/v1/topology/engineering-networks/{draftId}/publications publish",
                 "POST /api/v1/topology/engineering-networks/{draftId}/publications/withdraw withdraw",
                 "POST /api/v1/topology/engineering-networks/{draftId}/publications/reactivate reactivate",
                 "GET /api/v1/topology/engineering-networks/{draftId}/publications/revisions/{revisionId} readExact",
                 "GET /api/v1/topology/engineering-networks/{draftId}/publications/current readCurrent",
                 "GET /api/v1/topology/engineering-networks/{draftId}/publications/effective readEffective",
                 "GET /api/v1/topology/engineering-networks/{draftId}/publications/history listHistory",
                 "GET /api/v1/topology/engineering-networks/{draftId}/publications/requests/{requestId} readRequest" -> true;
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

    private static boolean isOwnedEmployeeAppointmentRoute(HandlerMethod handler,String route,String verb) {
        if(handler==null || route==null || verb==null)return false;
        String type=handler.getBeanType().getName();
        String tuple=verb+" "+route+" "+handler.getMethod().getName();
        if(type.equals("dz.sh.hidra.modules.organization.api.rest.controller.EmployeeAppointmentLifecycleController")) {
            return switch(tuple) {
                case "POST /api/v1/organization/assignments/{id}/end end",
                     "POST /api/v1/organization/assignments/{id}/transfer transfer",
                     "POST /api/v1/organization/assignments/{id}/corrections correct",
                     "GET /api/v1/organization/assignments/{id}/history assignmentHistory",
                     "GET /api/v1/organization/assignments/{id}/versions/{version} exactVersion",
                     "GET /api/v1/organization/employees/{employeeId}/appointment-history employeeHistory",
                     "GET /api/v1/organization/units/{unitId}/positions/{positionId}/appointment-history positionHistory",
                     "GET /api/v1/organization/units/{unitId}/appointment-policy readPolicy" -> true;
                default -> false;
            };
        }
        if(type.equals("dz.sh.hidra.modules.organization.api.rest.controller.SpringOrganizationController"))
            return tuple.equals("POST /api/v1/organization/assign-employee assignEmployee")
                    || tuple.equals("POST /api/v1/organization/employees/assignments assignEmployee");
        if(type.equals("dz.sh.hidra.modules.organization.api.rest.controller.OrganizationAdministrationQueryController"))
            return tuple.equals("GET /api/v1/organization/assignments assignments")
                    || tuple.equals("GET /api/v1/organization/employees/{id}/assignments employeeAssignments");
        return false;
    }

    private static boolean isOwnedLocalExchangeRoute(HandlerMethod handler,String route,String verb) {
        if(handler==null || route==null || verb==null || !handler.getBeanType().getName().equals(
                "dz.sh.hidra.modules.topology.api.rest.controller.EngineeringLocalExchangeController"))return false;
        return switch(verb+" "+route+" "+handler.getMethod().getName()) {
            case "GET /api/v1/topology/engineering-networks/{draftId}/exchanges/{editor}/export export",
                 "POST /api/v1/topology/engineering-networks/{draftId}/exchanges/{editor}/dry-runs dryRun",
                 "POST /api/v1/topology/engineering-networks/{draftId}/exchanges/{editor}/imports importOne",
                 "GET /api/v1/topology/engineering-networks/{draftId}/exchanges/{editor}/capabilities capabilities" -> true;
            default -> false;
        };
    }
}
