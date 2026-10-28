/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraRouteAuthorizationInterceptorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
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

    @Test void appointmentDelegationRequiresEveryExactControllerMethodVerbAndPattern() throws Exception {
        var interceptor=new HidraRouteAuthorizationInterceptor(org.mockito.Mockito.mock(HidraEffectivePermissionResolver.class),naming,true,"jwt");
        var operation=new dz.sh.hidra.modules.organization.api.rest.controller.EmployeeAppointmentLifecycleController(
                org.mockito.Mockito.mock(dz.sh.hidra.modules.organization.application.port.in.EmployeeAppointmentLifecycleUseCase.class));
        var legacy=new dz.sh.hidra.modules.organization.api.rest.controller.SpringOrganizationController(
                org.mockito.Mockito.mock(dz.sh.hidra.modules.organization.application.port.in.AssignEmployeeUseCase.class),
                org.mockito.Mockito.mock(dz.sh.hidra.modules.organization.application.port.in.CreateOrganizationUnitUseCase.class),
                org.mockito.Mockito.mock(dz.sh.hidra.modules.organization.application.port.in.RegisterEmployeeUseCase.class));
        var query=new dz.sh.hidra.modules.organization.api.rest.controller.OrganizationAdministrationQueryController(
                org.mockito.Mockito.mock(dz.sh.hidra.modules.organization.application.port.in.OrganizationAdministrationQueryUseCase.class));
        Object[][] rows={
            {operation,"end","POST","/api/v1/organization/assignments/{id}/end"},
            {operation,"transfer","POST","/api/v1/organization/assignments/{id}/transfer"},
            {operation,"correct","POST","/api/v1/organization/assignments/{id}/corrections"},
            {operation,"assignmentHistory","GET","/api/v1/organization/assignments/{id}/history"},
            {operation,"exactVersion","GET","/api/v1/organization/assignments/{id}/versions/{version}"},
            {operation,"employeeHistory","GET","/api/v1/organization/employees/{employeeId}/appointment-history"},
            {operation,"positionHistory","GET","/api/v1/organization/units/{unitId}/positions/{positionId}/appointment-history"},
            {operation,"readPolicy","GET","/api/v1/organization/units/{unitId}/appointment-policy"},
            {legacy,"assignEmployee","POST","/api/v1/organization/assign-employee"},
            {legacy,"assignEmployee","POST","/api/v1/organization/employees/assignments"},
            {query,"assignments","GET","/api/v1/organization/assignments"},
            {query,"employeeAssignments","GET","/api/v1/organization/employees/{id}/assignments"}};
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("synthetic",null,List.of()));
        for(Object[] row:rows) {
            var method=java.util.Arrays.stream(row[0].getClass().getMethods()).filter(m->m.getName().equals(row[1])).findFirst().orElseThrow();
            var handler=new HandlerMethod(row[0],method);
            var request=new MockHttpServletRequest((String)row[2],"/api/v1/organization/selected");
            request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,row[3]);
            assertTrue(interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            request.setMethod("DELETE");assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            request.setMethod((String)row[2]);request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,row[3]+"/sibling");
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,row[3]);SecurityContextHolder.clearContext();
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("synthetic",null,List.of()));
        }
        var wrongController=new HandlerMethod(query,query.getClass().getMethod("units",String.class,int.class,int.class));
        var wrong=new MockHttpServletRequest("POST","/api/v1/organization/assignments/selected/end");
        wrong.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,"/api/v1/organization/assignments/{id}/end");
        assertThrows(AccessDeniedException.class,()->interceptor.preHandle(wrong,new MockHttpServletResponse(),wrongController));
        var wrongMethod=new HandlerMethod(operation,operation.getClass().getMethod("readPolicy",String.class,String.class));
        assertThrows(AccessDeniedException.class,()->interceptor.preHandle(wrong,new MockHttpServletResponse(),wrongMethod));
    }

    @Test void productDraftDelegationRequiresExactAuthenticatedOwnerTuples() throws Exception {
        var resolver=org.mockito.Mockito.mock(HidraEffectivePermissionResolver.class);
        var interceptor=new HidraRouteAuthorizationInterceptor(resolver,naming,true,"jwt");
        var useCase=org.mockito.Mockito.mock(dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.class);
        var owner=new dz.sh.hidra.modules.custody.api.rest.controller.ProductEngineeringDraftController(useCase);
        String root="/api/v1/custody/engineering-products";
        String[] names={"create","read","update"},verbs={"POST","GET","PUT"},routes={root,root+"/{draftId}",root+"/{draftId}"};
        for(int i=0;i<names.length;i++) {
            final String name=names[i];
            var method=java.util.Arrays.stream(owner.getClass().getDeclaredMethods()).filter(m->m.getName().equals(name)).findFirst().orElseThrow();
            var handler=new HandlerMethod(owner,method);
            var request=new MockHttpServletRequest(verbs[i],routes[i].replace("{draftId}","selected"));
            request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,routes[i]);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("operator",null,List.of()));
            org.mockito.Mockito.clearInvocations(resolver);
            assertTrue(interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            org.mockito.Mockito.verifyNoInteractions(resolver,useCase);
            request.setMethod("DELETE");assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            request.setMethod(verbs[i]);request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,routes[i]+"/sibling");
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,routes[i]);
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handlerMethod()));
            final String wrongName=names[(i+1)%names.length];
            var wrong=java.util.Arrays.stream(owner.getClass().getDeclaredMethods()).filter(m->m.getName().equals(wrongName)).findFirst().orElseThrow();
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),new HandlerMethod(owner,wrong)));
            SecurityContextHolder.clearContext();
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("operator",null));
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            SecurityContextHolder.getContext().setAuthentication(new org.springframework.security.authentication.AnonymousAuthenticationToken(
                    "synthetic-key","anonymous",List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))));
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
        }
        org.mockito.Mockito.verifyNoInteractions(useCase);
    }

    @Test void suppliedFluidDelegationRequiresExactAuthenticatedOwnerTuples() throws Exception {
        var resolver=org.mockito.Mockito.mock(HidraEffectivePermissionResolver.class);
        var interceptor=new HidraRouteAuthorizationInterceptor(resolver,naming,true,"jwt");
        var useCase=org.mockito.Mockito.mock(dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.class);
        var owner=new dz.sh.hidra.modules.custody.api.rest.controller.SuppliedFluidPropertyRevisionController(useCase);
        String root="/api/v1/custody/engineering-products";
        String[] names={"read","write"},verbs={"GET","PUT"},routes={root+"/{draftId}/properties",root+"/{draftId}/properties"};
        for(int i=0;i<names.length;i++) {
            final String name=names[i];
            var method=java.util.Arrays.stream(owner.getClass().getDeclaredMethods()).filter(m->m.getName().equals(name)).findFirst().orElseThrow();
            var handler=new HandlerMethod(owner,method);
            var request=new MockHttpServletRequest(verbs[i],routes[i].replace("{draftId}","selected"));
            request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,routes[i]);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("operator",null,List.of()));
            org.mockito.Mockito.clearInvocations(resolver);
            assertTrue(interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            org.mockito.Mockito.verifyNoInteractions(resolver,useCase);
            request.setMethod("DELETE");assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            request.setMethod(verbs[i]);request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,routes[i]+"/sibling");
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,routes[i]);
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handlerMethod()));
            final String wrongName=names[(i+1)%names.length];
            var wrong=java.util.Arrays.stream(owner.getClass().getDeclaredMethods()).filter(m->m.getName().equals(wrongName)).findFirst().orElseThrow();
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),new HandlerMethod(owner,wrong)));
            SecurityContextHolder.clearContext();
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("operator",null));
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            SecurityContextHolder.getContext().setAuthentication(new org.springframework.security.authentication.AnonymousAuthenticationToken(
                    "synthetic-key","anonymous",List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))));
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
        }
        org.mockito.Mockito.verifyNoInteractions(useCase);
    }

    private final HidraRoutePermissionNaming naming = new HidraRoutePermissionNaming();

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test void productPublicationDelegationRequiresExactAuthenticatedOwnerTuples() throws Exception {
        var resolver=org.mockito.Mockito.mock(HidraEffectivePermissionResolver.class);
        var interceptor=new HidraRouteAuthorizationInterceptor(resolver,naming,true,"jwt");
        var useCase=org.mockito.Mockito.mock(dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase.class);
        var owner=new dz.sh.hidra.modules.custody.api.rest.controller.ProductRevisionPublicationController(useCase);
        String[] names={"prepare","candidate","publish","withdraw","reactivate","exact","current","effective","history","receipt"},verbs={"POST","GET","POST","POST","POST","GET","GET","GET","GET","GET"},routes={"/api/v1/custody/engineering-products/{draftId}/publications/candidates","/api/v1/custody/engineering-products/{draftId}/publications/candidates/{digest}","/api/v1/custody/engineering-products/{draftId}/publications","/api/v1/custody/engineering-products/{draftId}/publications/{publicationId}/withdraw","/api/v1/custody/engineering-products/{draftId}/publications/{publicationId}/reactivate","/api/v1/custody/engineering-products/{draftId}/publications/{publicationId}","/api/v1/custody/engineering-products/{draftId}/publications/current","/api/v1/custody/engineering-products/{draftId}/publications/effective","/api/v1/custody/engineering-products/{draftId}/publications/history","/api/v1/custody/engineering-products/{draftId}/publications/requests/{requestId}"};
        for(int i=0;i<names.length;i++) {
            final String name=names[i];
            var method=java.util.Arrays.stream(owner.getClass().getDeclaredMethods()).filter(m->m.getName().equals(name)).findFirst().orElseThrow();
            var handler=new HandlerMethod(owner,method);
            var request=new MockHttpServletRequest(verbs[i],routes[i].replace("{draftId}","selected"));
            request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,routes[i]);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("operator",null,List.of()));
            org.mockito.Mockito.clearInvocations(resolver);
            assertTrue(interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            org.mockito.Mockito.verifyNoInteractions(resolver,useCase);
            request.setMethod("DELETE");assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            request.setMethod(verbs[i]);request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,routes[i]+"/sibling");
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,routes[i]);
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handlerMethod()));
            final String wrongName=names[(i+1)%names.length];
            var wrong=java.util.Arrays.stream(owner.getClass().getDeclaredMethods()).filter(m->m.getName().equals(wrongName)).findFirst().orElseThrow();
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),new HandlerMethod(owner,wrong)));
            SecurityContextHolder.clearContext();
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("operator",null));
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            SecurityContextHolder.getContext().setAuthentication(new org.springframework.security.authentication.AnonymousAuthenticationToken(
                    "synthetic-key","anonymous",List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))));
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
        }
        org.mockito.Mockito.verifyNoInteractions(useCase);
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


    @Test
    void engineeringDraftNativeOwnerOnlyHandlesExactSixCompiledOperations() throws Exception {
        var resolver = new HidraEffectivePermissionResolver(List.of());
        var interceptor = new HidraRouteAuthorizationInterceptor(resolver, naming, true, "jwt");
        var owner = new dz.sh.hidra.modules.topology.api.rest.controller.EngineeringNetworkDraftController(
            command -> { throw new AssertionError("No business execution from route test"); });
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
            "operator", "n/a", List.of(new SimpleGrantedAuthority("ROLE_OPERATOR"))));
        String[] methods = {"create", "read", "update", "validate", "readGraph", "updateGraph"};
        String[] verbs = {"POST", "GET", "PUT", "POST", "GET", "PUT"};
        String[] routes = {
            "/api/v1/topology/engineering-networks",
            "/api/v1/topology/engineering-networks/{draftId}",
            "/api/v1/topology/engineering-networks/{draftId}",
            "/api/v1/topology/engineering-networks/{draftId}/validate",
            "/api/v1/topology/engineering-networks/{draftId}/graph",
            "/api/v1/topology/engineering-networks/{draftId}/graph"
        };
        for (int i=0; i<methods.length; i++) {
            final int selected = i;
            java.lang.reflect.Method m = java.util.Arrays.stream(owner.getClass().getDeclaredMethods())
                .filter(q -> q.getName().equals(methods[selected])).findFirst().orElseThrow();
            var handler = new HandlerMethod(owner,m);
            var actual = new MockHttpServletRequest(verbs[i],routes[i].replace("{draftId}","draft-id"));
            actual.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,routes[i]);
            assertTrue(interceptor.preHandle(actual,new MockHttpServletResponse(),handler));
            // Renaming, method switching, or mismatched routes must fail to ordinary permission gating.
            var wrong = new MockHttpServletRequest("DELETE",actual.getRequestURI());
            wrong.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,routes[i]);
            assertThrows(AccessDeniedException.class,
                ()->interceptor.preHandle(wrong,new MockHttpServletResponse(),handler));
        }
    }

    @Test
    void engineeringDraftNeverDelegatesAnonymousOrDifferentController() throws Exception {
        var resolver=new HidraEffectivePermissionResolver(List.of());
        var interceptor=new HidraRouteAuthorizationInterceptor(resolver,naming,true,"jwt");
        var owner=new dz.sh.hidra.modules.topology.api.rest.controller.EngineeringNetworkDraftController(
            command -> {throw new AssertionError("No business execution from route test");});
        var m=java.util.Arrays.stream(owner.getClass().getDeclaredMethods())
            .filter(q->q.getName().equals("create")).findFirst().orElseThrow();
        var req=new MockHttpServletRequest("POST","/api/v1/topology/engineering-networks");
        req.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,
            "/api/v1/topology/engineering-networks");
        assertThrows(AccessDeniedException.class,
            ()->interceptor.preHandle(req,new MockHttpServletResponse(),new HandlerMethod(owner,m)));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
            "operator","n/a",List.of(new SimpleGrantedAuthority("ROLE_OPERATOR"))));
        assertThrows(AccessDeniedException.class,
            ()->interceptor.preHandle(req,new MockHttpServletResponse(),handlerMethod()));
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

    @Test void surveyDelegationRequiresExactNativeControllerMethodAndAuthentication() throws Exception {
        var interceptor=new HidraRouteAuthorizationInterceptor(new HidraEffectivePermissionResolver(List.of()),naming,true,"jwt");
        var owner=new dz.sh.hidra.modules.topology.api.rest.controller.EngineeringNetworkDraftController(command->{throw new AssertionError();});
        String route="/api/v1/topology/engineering-networks/{draftId}/survey/{branchId}";
        for(String method:List.of("readSurvey","updateSurvey")){
            var m=java.util.Arrays.stream(owner.getClass().getDeclaredMethods()).filter(x->x.getName().equals(method)).findFirst().orElseThrow();var handler=new HandlerMethod(owner,m);
            var req=new MockHttpServletRequest(method.equals("readSurvey")?"GET":"PUT",route);req.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,route);
            SecurityContextHolder.clearContext();assertThrows(AccessDeniedException.class,()->interceptor.preHandle(req,new MockHttpServletResponse(),handler));
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("operator","n/a",List.of(new SimpleGrantedAuthority("ROLE_OPERATOR"))));
            assertTrue(interceptor.preHandle(req,new MockHttpServletResponse(),handler));
            req.setMethod("DELETE");assertThrows(AccessDeniedException.class,()->interceptor.preHandle(req,new MockHttpServletResponse(),handler));
            req.setMethod(method.equals("readSurvey")?"GET":"PUT");assertThrows(AccessDeniedException.class,()->interceptor.preHandle(req,new MockHttpServletResponse(),handlerMethod()));
        }
    }

    @Test void publicationDelegatesOnlyEightAuthenticatedExactCompiledTuples() throws Exception {
        var interceptor=new HidraRouteAuthorizationInterceptor(new HidraEffectivePermissionResolver(List.of()),naming,true,"jwt");
        var owner=new dz.sh.hidra.modules.topology.api.rest.controller.EngineeringRevisionPublicationController(command->{throw new AssertionError();});
        String base="/api/v1/topology/engineering-networks/{draftId}/publications";
        String[] names={"publish","withdraw","reactivate","readExact","readCurrent","readEffective","listHistory","readRequest"};
        String[] suffixes={"","/withdraw","/reactivate","/revisions/{revisionId}","/current","/effective","/history","/requests/{requestId}"};
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("operator","n/a",List.of(new SimpleGrantedAuthority("ROLE_OPERATOR"))));
        for(int n=0;n<names.length;n++) {
            final String name=names[n];String route=base+suffixes[n];
            var method=java.util.Arrays.stream(owner.getClass().getDeclaredMethods()).filter(x->x.getName().equals(name)).findFirst().orElseThrow();
            var handler=new HandlerMethod(owner,method);
            var request=new MockHttpServletRequest(n<3?"POST":"GET",route);request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,route);
            assertTrue(interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            var wrong=new MockHttpServletRequest("DELETE",route);wrong.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,route);
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(wrong,new MockHttpServletResponse(),handler));
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handlerMethod()));
            SecurityContextHolder.clearContext();
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("operator","n/a",List.of(new SimpleGrantedAuthority("ROLE_OPERATOR"))));
        }
    }


    @Test void exchangeDelegatesOnlyFourExactAuthenticatedControllerTuples() {
        var interceptor=new HidraRouteAuthorizationInterceptor(org.mockito.Mockito.mock(HidraEffectivePermissionResolver.class),naming,true,"jwt");
        var controller=new dz.sh.hidra.modules.topology.api.rest.controller.EngineeringLocalExchangeController(
                org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.in.EngineeringLocalExchangeUseCase.class));
        String root="/api/v1/topology/engineering-networks/{draftId}/exchanges/{editor}";
        String[] names={"export","dryRun","importOne","capabilities"},tails={"/export","/dry-runs","/imports","/capabilities"},verbs={"GET","POST","POST","GET"};
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("fixture",null,List.of()));
        for(int i=0;i<names.length;i++) {
            final String name=names[i];
            var method=java.util.Arrays.stream(controller.getClass().getMethods()).filter(m->m.getName().equals(name)).findFirst().orElseThrow();
            var handler=new HandlerMethod(controller,method);String pattern=root+tails[i];
            var request=new MockHttpServletRequest(verbs[i],pattern);request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,pattern);
            assertTrue(interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            request.setMethod("DELETE");assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            request.setMethod(verbs[i]);request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,pattern+"/sibling");
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,pattern);
            assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handlerMethod()));
            SecurityContextHolder.clearContext();assertThrows(AccessDeniedException.class,()->interceptor.preHandle(request,new MockHttpServletResponse(),handler));
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("fixture",null,List.of()));
        }
    }
}
