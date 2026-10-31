package dz.sh.hidra.platform.permissions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.web.method.HandlerMethod;

class HidraTrustedNativeProtectedRouteRegistryTest {
    private static final String PATH = "/api/v1/platform/synthetic-trusted";

    static final class Owner {
        @NativeProtectedOperation
        public String save(String value) { return value; }

        @NativeProtectedOperation
        public String save(Integer value) { return String.valueOf(value); }

        public String ordinary(String value) { return value; }
    }

    static final class Other {
        @NativeProtectedOperation
        public String save(String value) { return value; }
    }

    private static HidraTrustedNativeProtectedRouteRegistry.Route row() throws Exception {
        return new HidraTrustedNativeProtectedRouteRegistry.Route(
                Owner.class, Owner.class.getMethod("save", String.class), "POST", PATH);
    }

    @Test void productionRegistryDeclaresOnlyFiveUnverifiedManufacturerTuples() throws Exception {
        var registry = new HidraTrustedNativeProtectedRouteRegistry();
        assertEquals(5,registry.candidates().size());
        assertEquals(java.util.Set.of("save","revision","history","certificate","qualify"),
                registry.candidates().stream().map(v->v.method().getName())
                    .collect(java.util.stream.Collectors.toSet()));
        assertTrue(registry.candidates().stream().allMatch(v->
                v.method().isAnnotationPresent(NativeProtectedOperation.class)));
        assertFalse(registry.isTrusted(new HandlerMethod(new Owner(), row().method()), "POST", PATH));
    }

    @Test void markedMethodCannotSelfRegister() throws Exception {
        var registry = new HidraTrustedNativeProtectedRouteRegistry();
        assertTrue(row().method().isAnnotationPresent(NativeProtectedOperation.class));
        assertFalse(registry.isTrusted(new HandlerMethod(new Owner(), row().method()), "POST", PATH));
    }

    @Test void unvalidatedTrustedEntryNeverDelegates() throws Exception {
        var registry = new HidraTrustedNativeProtectedRouteRegistry(List.of(row()));
        assertFalse(registry.isTrusted(new HandlerMethod(new Owner(), row().method()), "POST", PATH));
        assertEquals(1, registry.candidates().size());
    }

    @Test void verifiedEntryMatchesExactCompiledSignatureVerbAndRoute() throws Exception {
        var route = row();
        var registry = new HidraTrustedNativeProtectedRouteRegistry(List.of(route));
        registry.activate(List.of(route));
        assertTrue(registry.isTrusted(new HandlerMethod(new Owner(), route.method()), "POST", PATH));
        Method overload = Owner.class.getMethod("save", Integer.class);
        assertFalse(registry.isTrusted(new HandlerMethod(new Owner(), overload), "POST", PATH));
        assertFalse(registry.isTrusted(new HandlerMethod(new Other(),
                Other.class.getMethod("save", String.class)), "POST", PATH));
        assertFalse(registry.isTrusted(new HandlerMethod(new Owner(), route.method()), "GET", PATH));
        assertFalse(registry.isTrusted(new HandlerMethod(new Owner(), route.method()), "POST", PATH + "/sibling"));
        assertFalse(registry.isTrusted(new HandlerMethod(new Owner(), Owner.class.getMethod("ordinary",
                String.class)), "POST", PATH));
    }

    @Test void trustedDeclarationsAreImmutableAndCannotBeSilentlyReplaced() throws Exception {
        var mutable = new ArrayList<>(List.of(row()));
        var registry = new HidraTrustedNativeProtectedRouteRegistry(mutable);
        mutable.clear();
        assertEquals(1, registry.candidates().size());
        assertThrows(UnsupportedOperationException.class, () -> registry.candidates().clear());
        assertThrows(IllegalStateException.class, () -> registry.activate(List.of()));
    }

    @Test void duplicateAndInvalidEntriesAreRejected() throws Exception {
        var route = row();
        assertThrows(IllegalArgumentException.class, () ->
                new HidraTrustedNativeProtectedRouteRegistry(List.of(route, route)));
        assertThrows(IllegalArgumentException.class, () ->
                new HidraTrustedNativeProtectedRouteRegistry.Route(
                        Owner.class, route.method(), "ANY", PATH));
        assertThrows(IllegalArgumentException.class, () ->
                new HidraTrustedNativeProtectedRouteRegistry.Route(
                        Owner.class, route.method(), "POST", "/api/v1/platform/*"));
        assertThrows(IllegalArgumentException.class, () ->
                new HidraTrustedNativeProtectedRouteRegistry.Route(
                        Other.class, route.method(), "POST", PATH));
        assertThrows(IllegalArgumentException.class, () ->
                new HidraTrustedNativeProtectedRouteRegistry.Route(
                        Owner.class, route.method(), "POST", "/api/v1/realtime/ws/private"));
        assertThrows(IllegalArgumentException.class, () ->
                new HidraTrustedNativeProtectedRouteRegistry.Route(
                        Owner.class, route.method(), "POST", "/api/v1/identity/me/permissions"));
    }

    @Test void matchingRequiresBothVerifiedTupleAndMethodMarker() throws Exception {
        var unmarked = Owner.class.getMethod("ordinary", String.class);
        var route = new HidraTrustedNativeProtectedRouteRegistry.Route(
                Owner.class, unmarked, "POST", PATH);
        var registry = new HidraTrustedNativeProtectedRouteRegistry(List.of(route));
        registry.activate(List.of(route));
        assertFalse(registry.isTrusted(new HandlerMethod(new Owner(), unmarked), "POST", PATH));
    }
}
