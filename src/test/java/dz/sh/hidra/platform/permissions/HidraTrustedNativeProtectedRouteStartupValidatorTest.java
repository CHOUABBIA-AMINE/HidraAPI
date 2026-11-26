/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraTrustedNativeProtectedRouteStartupValidatorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-26
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.permissions
 *
 * @Description : Preserves exact startup-verified native HTTP delegation and its negative boundaries.
 *
 */
package dz.sh.hidra.platform.permissions;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test void t31StartupInventoryIsExactAndNoWildcardOrExtraVerb() {
        var routes=new HidraTrustedNativeProtectedRouteRegistry().candidates();
        String base="/api/v1/simulation/companies/{companyId}/pipeline-systems/{pipelineSystemId}/scenarios";
        var t31=routes.stream().filter(r->r.controller().getSimpleName().equals("ScenarioInputRevisionController")).toList();
        assertEquals(163,routes.size());assertEquals(4,t31.size());
        var t63=routes.stream().filter(r->java.util.Set.of("discoverTransferPoints","discoverPeriods","listTickets").contains(r.method().getName())
            &&r.controller().getSimpleName().equals("CustodyTransferLifecycleController")
            ||r.method().getName().equals("list")&&r.controller().getSimpleName().equals("CustodyMeasurementCaptureController")).toList();
        assertEquals(4,t63.size());
        assertEquals(java.util.Set.of(
            "GET /api/v1/custody/companies/{companyId}/facilities/{facilityId}/transfer-points",
            "GET /api/v1/custody/companies/{companyId}/transfer-points/{transferPointId}/measurement-periods",
            "GET /api/v1/custody/measurement-periods/{periodId}/transfer-tickets",
            "GET /api/v1/custody/measurement-periods/{periodId}/measurements"),
            t63.stream().map(r->r.verb()+" "+r.pattern()).collect(java.util.stream.Collectors.toSet()));
        assertTrue(t63.stream().allMatch(r->r.method().isAnnotationPresent(NativeProtectedOperation.class)));
        assertTrue(t31.stream().allMatch(r->r.method().isAnnotationPresent(NativeProtectedOperation.class)));
        assertEquals(4,t31.stream().map(r->r.verb()+" "+r.pattern()).distinct().count());
        assertTrue(t31.stream().allMatch(r->r.pattern().equals(base)
             ||r.pattern().startsWith(base+"/{scenarioId}/revisions")));
    }
    @Test void t28StartupRegistryRequiresFourExactAnnotatedHandlers() {
        var registry=new HidraTrustedNativeProtectedRouteRegistry();
        var rows=registry.candidates().stream()
            .filter(x->x.controller().getSimpleName().equals("ScopedAuditEvidenceController")).toList();
        assertEquals(163,registry.candidates().size());assertEquals(4,rows.size());
        assertTrue(rows.stream().allMatch(x->x.method().isAnnotationPresent(NativeProtectedOperation.class)));
        assertEquals(4,rows.stream().map(x->x.verb()+" "+x.pattern()).distinct().count());
    }
    @Test void t27StartupRegistryRequiresThreeExactAnnotatedNotificationHandlers() {
        var registry=new HidraTrustedNativeProtectedRouteRegistry();
        var routes=registry.candidates().stream()
                .filter(r->r.controller().getSimpleName().equals("WorkflowInboxController")).toList();
        assertEquals(163,registry.candidates().size());
        assertEquals(3,routes.size());
        assertTrue(routes.stream().allMatch(r->r.method().isAnnotationPresent(NativeProtectedOperation.class)));
        assertEquals(3,routes.stream().map(r->r.verb()+" "+r.pattern()).distinct().count());
    }

    @Test void productionNativeExactRoutesFailWithoutCompiledMappings() {
        var registry = new HidraTrustedNativeProtectedRouteRegistry();
        assertEquals(163, registry.candidates().size());
        assertThrows(IllegalStateException.class, ()->
                new HidraTrustedNativeProtectedRouteStartupValidator(
                    new RequestMappingHandlerMapping(), registry).validate());
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
