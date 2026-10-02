/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraRouteAuthorizationInterceptorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-17
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.permissions
 *
 * @Description : Verifies route permission naming and backend forbidden/success authorization behavior.
 *
 */
package dz.sh.hidra.platform.permissions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

class HidraRouteAuthorizationInterceptorTest {

    private final HidraRoutePermissionNaming naming = new HidraRoutePermissionNaming();

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void derivesIdentityRoadmapPermissionFormat() throws Exception {
        assertEquals(
                "telemetry:points:read",
                naming.permissionFor("/api/v1/telemetry/points/{pointId}/readings", "GET", handlerMethod())
        );
    }

    @Test
    void permitsMatchingEffectivePermission() throws Exception {
        HidraEffectivePermissionResolver resolver = new HidraEffectivePermissionResolver(List.of());
        HidraRouteAuthorizationInterceptor interceptor = new HidraRouteAuthorizationInterceptor(resolver, naming, true, "jwt");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                "operator",
                "n/a",
                List.of(new SimpleGrantedAuthority("SCOPE_telemetry:points:read"))
        ));

        assertTrue(interceptor.preHandle(request(), new MockHttpServletResponse(), handlerMethod()));
    }

    @Test
    void forbidsMissingEffectivePermission() throws Exception {
        HidraEffectivePermissionResolver resolver = new HidraEffectivePermissionResolver(List.of());
        HidraRouteAuthorizationInterceptor interceptor = new HidraRouteAuthorizationInterceptor(resolver, naming, true, "jwt");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                "operator",
                "n/a",
                List.of(new SimpleGrantedAuthority("ROLE_OPERATOR"))
        ));

        assertThrows(
                AccessDeniedException.class,
                () -> interceptor.preHandle(request(), new MockHttpServletResponse(), handlerMethod())
        );
    }

    @Test
    void permitsAnonymousDirectLoginBeforeRoutePermissionResolution() throws Exception {
        HidraEffectivePermissionResolver resolver = new HidraEffectivePermissionResolver(List.of());
        HidraRouteAuthorizationInterceptor interceptor = new HidraRouteAuthorizationInterceptor(resolver, naming, true, "jwt");

        MockHttpServletRequest request = new MockHttpServletRequest(
                "POST",
                "/api/v1/identity/authentication/login"
        );
        request.setAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,
                "/api/v1/identity/authentication/login"
        );

        assertTrue(interceptor.preHandle(request, new MockHttpServletResponse(), handlerMethod()));
    }

    @Test
    void anonymousProtectedRouteStillRequiresPermission() throws Exception {
        HidraEffectivePermissionResolver resolver = new HidraEffectivePermissionResolver(List.of());
        HidraRouteAuthorizationInterceptor interceptor = new HidraRouteAuthorizationInterceptor(resolver, naming, true, "jwt");

        assertThrows(
                AccessDeniedException.class,
                () -> interceptor.preHandle(request(), new MockHttpServletResponse(), handlerMethod())
        );
    }

    private static MockHttpServletRequest request() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/v1/telemetry/points/point-1/readings");
        request.setAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,
                "/api/v1/telemetry/points/{pointId}/readings"
        );
        return request;
    }

    private static HandlerMethod handlerMethod() throws Exception {
        Method method = Fixture.class.getDeclaredMethod("handle");
        return new HandlerMethod(new Fixture(), method);
    }

    static final class Fixture {
        void handle() {
        }
    }
}
