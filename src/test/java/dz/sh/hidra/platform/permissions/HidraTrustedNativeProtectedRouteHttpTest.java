package dz.sh.hidra.platform.permissions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

class HidraTrustedNativeProtectedRouteHttpTest {
    private static final String TRUSTED = "/api/v1/platform/synthetic-native";
    private static final String UNTRUSTED = "/api/v1/platform/untrusted-native";

    @RestController
    static final class OwnerController {
        final AtomicInteger calls = new AtomicInteger();

        @NativeProtectedOperation
        @PostMapping(TRUSTED)
        public String protectedWrite() {
            calls.incrementAndGet();
            return "synthetic-owner";
        }
    }

    @RestController
    static final class UntrustedController {
        final AtomicInteger calls = new AtomicInteger();

        @NativeProtectedOperation
        @PostMapping(UNTRUSTED)
        public String markedButNotRegistered() {
            calls.incrementAndGet();
            return "generic-only";
        }

        @GetMapping(TRUSTED)
        public String differentVerb() {
            calls.incrementAndGet();
            return "generic-read";
        }
    }

    private static HidraTrustedNativeProtectedRouteRegistry validated() throws Exception {
        var method = OwnerController.class.getMethod("protectedWrite");
        var row = new HidraTrustedNativeProtectedRouteRegistry.Route(
                OwnerController.class, method, "POST", TRUSTED);
        var registry = new HidraTrustedNativeProtectedRouteRegistry(List.of(row));
        var actual = new RequestMappingHandlerMapping();
        actual.registerMapping(RequestMappingInfo.paths(TRUSTED).methods(RequestMethod.POST).build(),
                new OwnerController(), method);
        new HidraTrustedNativeProtectedRouteStartupValidator(actual, registry).validate();
        return registry;
    }

    private static MockMvc mvc(OwnerController owned, UntrustedController sibling,
            HidraEffectivePermissionResolver resolver,
            HidraTrustedNativeProtectedRouteRegistry trusted) {
        return MockMvcBuilders.standaloneSetup(owned, sibling)
                .addInterceptors(new HidraRouteAuthorizationInterceptor(
                        resolver, new HidraRoutePermissionNaming(), true, "jwt", trusted))
                .build();
    }

    private static void authenticated() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("synthetic", null, List.of()));
    }

    @AfterEach void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }

    @Test void exactNativeControllerRouteDelegatesWithoutGenericPermission() throws Exception {
        var owner = new OwnerController();
        var resolver = org.mockito.Mockito.mock(HidraEffectivePermissionResolver.class);
        authenticated();
        var result = mvc(owner, new UntrustedController(), resolver, validated())
                .perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(TRUSTED)
                        .accept(MediaType.TEXT_PLAIN))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("synthetic-owner", result.getResponse().getContentAsString());
        assertEquals(1, owner.calls.get());
        verifyNoInteractions(resolver);
    }

    @Test void anonymousRequestIsDeniedBeforeNativeHandler() throws Exception {
        var owner = new OwnerController();
        var resolver = org.mockito.Mockito.mock(HidraEffectivePermissionResolver.class);
        var mvc = mvc(owner, new UntrustedController(), resolver, validated());
        assertThrows(Exception.class, () ->
                mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(TRUSTED)));
        assertEquals(0, owner.calls.get());
        verifyNoInteractions(resolver);
    }

    @Test void annotationOnlyControllerCannotSkipGenericPermission() throws Exception {
        var owner = new OwnerController();
        var other = new UntrustedController();
        var resolver = org.mockito.Mockito.mock(HidraEffectivePermissionResolver.class);
        authenticated();
        var mvc = mvc(owner, other, resolver, validated());
        assertThrows(Exception.class, () ->
                mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(UNTRUSTED)));
        assertEquals(0, owner.calls.get());
        assertEquals(0, other.calls.get());
        org.mockito.Mockito.verify(resolver).hasPermission(any(), anyString());
    }

    @Test void ordinaryPermissionsStillGovernUnregisteredRoutes() throws Exception {
        var other = new UntrustedController();
        var resolver = org.mockito.Mockito.mock(HidraEffectivePermissionResolver.class);
        when(resolver.hasPermission(any(), anyString())).thenReturn(true);
        authenticated();
        var result = mvc(new OwnerController(), other, resolver, validated())
                .perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(UNTRUSTED))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals(1, other.calls.get());
    }

    @Test void wrongVerbCannotReuseNativeDelegation() throws Exception {
        var other = new UntrustedController();
        var resolver = org.mockito.Mockito.mock(HidraEffectivePermissionResolver.class);
        authenticated();
        var mvc = mvc(new OwnerController(), other, resolver, validated());
        assertThrows(Exception.class, () ->
                mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(TRUSTED)));
        assertEquals(0, other.calls.get());
        org.mockito.Mockito.verify(resolver).hasPermission(any(), anyString());
    }

    @Test void productionManufacturerRegistryDoesNotDelegateSyntheticController() throws Exception {
        var owner = new OwnerController();
        var resolver = org.mockito.Mockito.mock(HidraEffectivePermissionResolver.class);
        authenticated();
        var registry = new HidraTrustedNativeProtectedRouteRegistry();
        assertEquals(5,registry.candidates().size());
        var mvc = mvc(owner, new UntrustedController(), resolver,registry);
        assertThrows(Exception.class, () ->
                mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(TRUSTED)));
        assertEquals(0, owner.calls.get());
    }

    @Test void realManufacturerTupleRegistryRejectsWrongVerbAndSiblingPattern() throws Exception {
        // The actual compiled manufacturer methods (not an annotation or name alias) are
        // independently reconciled by Platform's startup validator.
        var registry=new HidraTrustedNativeProtectedRouteRegistry();
        var routes=registry.candidates();
        assertEquals(5,routes.size());
        var expected=java.util.Set.of(
            "PUT /api/v1/simulation/manufacturer-characteristics/{sourceId}/revisions/{revisionId}",
            "GET /api/v1/simulation/manufacturer-characteristics/{sourceId}/revisions/{revisionId}",
            "GET /api/v1/simulation/manufacturer-characteristics/{sourceId}/history",
            "GET /api/v1/simulation/manufacturer-characteristics/{sourceId}/revisions/{revisionId}/certificate",
            "POST /api/v1/simulation/manufacturer-characteristics/{sourceId}/revisions/{revisionId}/qualification");
        assertEquals(expected,routes.stream().map(r->r.verb()+" "+r.pattern())
            .collect(java.util.stream.Collectors.toSet()));
        var controller=new dz.sh.hidra.modules.simulation.api.rest.controller
            .ManufacturerCharacteristicRevisionController(org.mockito.Mockito.mock(
                dz.sh.hidra.modules.simulation.application.port.in
                    .ManufacturerCharacteristicRevisionUseCase.class));
        var actual=new RequestMappingHandlerMapping();
        for(var entry:routes) {
            actual.registerMapping(RequestMappingInfo.paths(entry.pattern())
                .methods(RequestMethod.valueOf(entry.verb())).build(),controller,entry.method());
        }
        new HidraTrustedNativeProtectedRouteStartupValidator(actual,registry).validate();
        for(var entry:routes) {
            var handler=new org.springframework.web.method.HandlerMethod(controller,entry.method());
            assertTrue(registry.isTrusted(handler,entry.verb(),entry.pattern()));
            assertTrue(!registry.isTrusted(handler,
                entry.verb().equals("GET")?"PUT":"GET",entry.pattern()));
            assertTrue(!registry.isTrusted(handler,entry.verb(),entry.pattern()+"/sibling"));
        }
    }
}
