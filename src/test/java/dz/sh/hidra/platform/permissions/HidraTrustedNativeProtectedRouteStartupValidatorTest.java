package dz.sh.hidra.platform.permissions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.method.HandlerMethod;

class HidraTrustedNativeProtectedRouteStartupValidatorTest {
    private static final String PATH = "/api/v1/platform/synthetic-startup";

    static final class Owner {
        @NativeProtectedOperation
        public String execute(String value) { return value; }

        public String unmarked(String value) { return value; }
    }

    private static Method method(String name) throws Exception {
        return Owner.class.getMethod(name, String.class);
    }

    private static HidraTrustedNativeProtectedRouteRegistry.Route row(String name) throws Exception {
        return new HidraTrustedNativeProtectedRouteRegistry.Route(
                Owner.class, method(name), "POST", PATH);
    }

    private static RequestMappingHandlerMapping mapped(String name, RequestMethod verb, String path)
            throws Exception {
        var mapping = new RequestMappingHandlerMapping();
        mapping.registerMapping(RequestMappingInfo.paths(path).methods(verb).build(),
                new Owner(), method(name));
        return mapping;
    }

    @Test void noTrustedProductionEntriesAllowCleanStartup() {
        var registry = new HidraTrustedNativeProtectedRouteRegistry();
        new HidraTrustedNativeProtectedRouteStartupValidator(
                new RequestMappingHandlerMapping(), registry).validate();
        assertTrue(registry.candidates().isEmpty());
    }

    @Test void exactMappedAnnotatedRouteBecomesTrustedOnlyAfterValidation() throws Exception {
        var trusted = row("execute");
        var registry = new HidraTrustedNativeProtectedRouteRegistry(List.of(trusted));
        var handler = new HandlerMethod(new Owner(), method("execute"));
        assertFalse(registry.isTrusted(handler, "POST", PATH));
        new HidraTrustedNativeProtectedRouteStartupValidator(
                mapped("execute", RequestMethod.POST, PATH), registry).validate();
        assertTrue(registry.isTrusted(handler, "POST", PATH));
    }

    @Test void startupFailsOnAbsentControllerMapping() throws Exception {
        var registry = new HidraTrustedNativeProtectedRouteRegistry(List.of(row("execute")));
        assertThrows(IllegalStateException.class, () ->
                new HidraTrustedNativeProtectedRouteStartupValidator(
                        new RequestMappingHandlerMapping(), registry).validate());
    }

    @Test void startupFailsOnWrongVerbOrPattern() throws Exception {
        var registry = new HidraTrustedNativeProtectedRouteRegistry(List.of(row("execute")));
        assertThrows(IllegalStateException.class, () ->
                new HidraTrustedNativeProtectedRouteStartupValidator(
                        mapped("execute", RequestMethod.GET, PATH), registry).validate());
        assertThrows(IllegalStateException.class, () ->
                new HidraTrustedNativeProtectedRouteStartupValidator(
                        mapped("execute", RequestMethod.POST, PATH + "/other"), registry).validate());
    }

    @Test void startupFailsWhenMethodIsNotAnnotated() throws Exception {
        var registry = new HidraTrustedNativeProtectedRouteRegistry(List.of(row("unmarked")));
        assertThrows(IllegalStateException.class, () ->
                new HidraTrustedNativeProtectedRouteStartupValidator(
                        mapped("unmarked", RequestMethod.POST, PATH), registry).validate());
    }

    @Test void startupFailsOnAmbiguousRegisteredMapping() throws Exception {
        var mapping = new RequestMappingHandlerMapping();
        var owner = new Owner();
        mapping.registerMapping(RequestMappingInfo.paths(PATH).methods(RequestMethod.POST)
                .consumes("application/json").build(), owner, method("execute"));
        mapping.registerMapping(RequestMappingInfo.paths(PATH).methods(RequestMethod.POST)
                .consumes("text/plain").build(), owner, method("execute"));
        var registry = new HidraTrustedNativeProtectedRouteRegistry(List.of(row("execute")));
        assertThrows(IllegalStateException.class, () ->
                new HidraTrustedNativeProtectedRouteStartupValidator(mapping, registry).validate());
    }

    @Test void rejectedStartupNeverActivatesTrustedDelegation() throws Exception {
        var registry = new HidraTrustedNativeProtectedRouteRegistry(List.of(row("execute")));
        try {
            new HidraTrustedNativeProtectedRouteStartupValidator(
                    mapped("execute", RequestMethod.GET, PATH), registry).validate();
        } catch (IllegalStateException expected) {
            // Startup remains failed closed; no mutable candidate can authorize a request.
        }
        assertFalse(registry.isTrusted(new HandlerMethod(new Owner(), method("execute")), "POST", PATH));
    }
}
