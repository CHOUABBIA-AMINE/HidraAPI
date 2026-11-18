/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraTrustedNativeProtectedRouteRegistryTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
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

    @Test void productionRegistryDeclaresFiveManufacturerAndThreeTopologyApprovalTuples() throws Exception {
        var registry = new HidraTrustedNativeProtectedRouteRegistry();
        var legacy=registry.candidates().stream().filter(r->java.util.Set.of("ManufacturerCharacteristicRevisionController","InstalledCharacteristicLinkController").contains(r.controller().getSimpleName())).toList();
        assertEquals(8,legacy.size());
        assertEquals(java.util.Set.of("save","revision","history","certificate","qualify","approve","approvedRevision","approvalHistory"),
                legacy.stream().map(v->v.method().getName())
                    .collect(java.util.stream.Collectors.toSet()));
        assertTrue(registry.candidates().stream().allMatch(v->
                v.method().isAnnotationPresent(NativeProtectedOperation.class)));
        assertFalse(registry.isTrusted(new HandlerMethod(new Owner(), row().method()), "POST", PATH));
    }


    @Test void t17RegistersExactlyFourteenScopedNativeHseTuples() {
        var routes = new HidraTrustedNativeProtectedRouteRegistry().candidates().stream()
                .filter(route -> route.controller().getSimpleName().equals("HseCapaLifecycleController"))
                .toList();
        String cases = "/api/v1/hse/companies/{companyId}/facilities/{facilityId}/cases";
        String casePath = cases + "/{caseId}";
        String capaPath = casePath + "/capas/{capaId}";
        assertEquals(14, routes.size());
        assertEquals(java.util.Set.of(
                "GET " + cases,
                "GET " + casePath,
                "GET " + casePath + "/capas",
                "GET " + capaPath,
                "GET " + casePath + "/history",
                "POST " + cases + "/from-incident",
                "POST " + casePath + "/impact-assessments",
                "POST " + casePath + "/capas",
                "POST " + capaPath + "/approve",
                "POST " + capaPath + "/assign",
                "POST " + capaPath + "/evidence",
                "POST " + capaPath + "/complete",
                "POST " + capaPath + "/verify",
                "POST " + casePath + "/close"),
                routes.stream().map(route -> route.verb() + " " + route.pattern())
                        .collect(java.util.stream.Collectors.toSet()));
        assertTrue(routes.stream().allMatch(route ->
                route.method().isAnnotationPresent(NativeProtectedOperation.class)));
    }

    @Test void t16ReviewerRoutesAreExactlyFourteenNativeCompiledTuples() {
        var routes=new HidraTrustedNativeProtectedRouteRegistry().candidates().stream()
                .filter(v->java.util.Set.of("IncidentInvestigationController","LeakInvestigationController")
                        .contains(v.controller().getSimpleName())).toList();
        assertEquals(14,routes.size());
        assertTrue(routes.stream().allMatch(v->v.method().isAnnotationPresent(NativeProtectedOperation.class)));
        assertEquals(2,routes.stream().filter(v->v.verb().equals("GET")).count());
        assertEquals(12,routes.stream().filter(v->v.verb().equals("POST")).count());
    }
    @Test void t18IntegrityAndAssetsNativeRoutesHaveExactlyEightApprovedTuples() {
        var rows=new HidraTrustedNativeProtectedRouteRegistry().candidates().stream()
                .filter(v->java.util.Set.of("IntegrityMaintenanceApprovalController",
                        "IntegrityWorkOrderController").contains(v.controller().getSimpleName())).toList();
        assertEquals(8,rows.size());
        assertEquals(2,rows.stream().filter(v->v.verb().equals("GET")).count());
        assertEquals(6,rows.stream().filter(v->v.verb().equals("POST")).count());
        assertTrue(rows.stream().allMatch(v->v.method().isAnnotationPresent(NativeProtectedOperation.class)));
    }

    @Test void t20ExactCustodyRoutesHaveOnlyTwoCompiledNativeBindings() {
        var rows=new HidraTrustedNativeProtectedRouteRegistry().candidates().stream()
          .filter(r->r.controller().getSimpleName().equals("CustodyMeasurementCaptureController")).toList();
        assertEquals(2,rows.size());
        assertEquals(java.util.Set.of(
          "POST /api/v1/custody/measurement-periods/{periodId}/measurements",
          "GET /api/v1/custody/measurement-periods/{periodId}/measurements/{measurementId}"),
            rows.stream().map(r->r.verb()+" "+r.pattern()).collect(java.util.stream.Collectors.toSet()));
        assertTrue(rows.stream().allMatch(r->r.method().isAnnotationPresent(NativeProtectedOperation.class)));
    }
    @Test void t21ExactCustodyLifecycleRegistersElevenNativeBindings() {
        var entries=new HidraTrustedNativeProtectedRouteRegistry().candidates().stream()
            .filter(row->row.controller().getSimpleName().equals("CustodyTransferLifecycleController")).toList();
        assertEquals(11,entries.size());
        assertEquals(7,entries.stream().filter(row->row.verb().equals("GET")).count());
        assertEquals(4,entries.stream().filter(row->row.verb().equals("POST")).count());
        assertTrue(entries.stream().allMatch(row->row.method().isAnnotationPresent(NativeProtectedOperation.class)));
        assertEquals(11,entries.stream().map(row->row.verb()+" "+row.pattern()).distinct().count());
    }
    @Test void t19RegistersOnlyExactNominationCommandAndHistoryTuples() {
        var routes = new HidraTrustedNativeProtectedRouteRegistry().candidates().stream()
                .filter(route -> route.controller().getSimpleName().equals("PlanningNominationCommandController"))
                .toList();
        assertEquals(3, routes.size());
        assertEquals(java.util.Set.of(
                "POST /api/v1/planning/revisions/{revisionId}/nominations",
                "POST /api/v1/planning/nominations/{id}/disposition",
                "GET /api/v1/planning/nominations/{id}/history"),
                routes.stream().map(route -> route.verb() + " " + route.pattern())
                        .collect(java.util.stream.Collectors.toSet()));
        assertTrue(routes.stream().allMatch(route -> route.method().isAnnotationPresent(NativeProtectedOperation.class)));
    }

    @Test void discoveryRoutesAreExactCompiledAndCannotSelfRegister(){
        var registry=new HidraTrustedNativeProtectedRouteRegistry();
        var routes=registry.candidates().stream().filter(r->
                r.controller().getSimpleName().equals("EngineeringSourceDiscoveryController")).toList();
        assertEquals(2,routes.size());
        assertEquals(java.util.Set.of(
                "GET /api/v1/topology/engineering-sources",
                "GET /api/v1/topology/engineering-sources/{kind}/{ownerId}/{sourceId}/revisions/{revisionId}"),
                routes.stream().map(r->r.verb()+" "+r.pattern())
                        .collect(java.util.stream.Collectors.toSet()));
        assertTrue(routes.stream().allMatch(r->r.method().isAnnotationPresent(NativeProtectedOperation.class)));
    }

    @Test void baselineRoutesDeclareOnlyFiveExactCompiledTuples(){
        var registry=new HidraTrustedNativeProtectedRouteRegistry();
        assertEquals(138,registry.candidates().size());
        var rows=registry.candidates().stream().filter(r->r.controller().getSimpleName().equals("EngineeringBaselineDraftController")).toList();
        assertEquals(5,rows.size());
        assertEquals(java.util.Set.of("POST /api/v1/topology/engineering-baselines","PUT /api/v1/topology/engineering-baselines/{manifestId}",
            "GET /api/v1/topology/engineering-baselines/{manifestId}","GET /api/v1/topology/engineering-baselines/{manifestId}/revisions/{version}",
            "POST /api/v1/topology/engineering-baselines/{manifestId}/validation"),
            rows.stream().map(r->r.verb()+" "+r.pattern()).collect(java.util.stream.Collectors.toSet()));
        assertTrue(rows.stream().allMatch(r->r.method().isAnnotationPresent(NativeProtectedOperation.class)));
    }

    @Test void controlledDocumentsNineRoutesAreExactNativeCompiledAndMarked() {
        var rows=new HidraTrustedNativeProtectedRouteRegistry().candidates().stream()
            .filter(x->x.controller().getSimpleName().equals("ControlledDocumentsController")).toList();
        final String base="/api/v1/documents/companies/{companyId}/facilities/{facilityId}/controlled-documents";
        assertEquals(9,rows.size());
        assertEquals(java.util.Set.of(
                "POST "+base,
                "GET "+base,
                "GET "+base+"/{documentId}",
                "GET "+base+"/{documentId}/versions",
                "GET "+base+"/{documentId}/links",
                "POST "+base+"/{documentId}/versions/upload",
                "GET "+base+"/{documentId}/versions/{versionId}/content",
                "POST "+base+"/{documentId}/versions/{versionId}/accept",
                "POST "+base+"/{documentId}/versions/{versionId}/supersede"),
                rows.stream().map(x->x.verb()+" "+x.pattern())
                    .collect(java.util.stream.Collectors.toSet()));
        assertTrue(rows.stream().allMatch(x->x.method().isAnnotationPresent(NativeProtectedOperation.class)));
    }

    @Test void deviationMetricRoutesAreExactlyRegistered() {
        var entries=new HidraTrustedNativeProtectedRouteRegistry().candidates().stream()
                .filter(r->r.controller().getSimpleName().equals("DeviationMetricRunController")).toList();
        assertEquals(2,entries.size());
        assertEquals(java.util.Set.of(
                "POST /api/v1/analytics/companies/{companyId}/facilities/{facilityId}/deviation-metric-runs",
                "GET /api/v1/analytics/companies/{companyId}/facilities/{facilityId}/deviation-metric-runs/{runId}"),
                entries.stream().map(r->r.verb()+" "+r.pattern())
                       .collect(java.util.stream.Collectors.toSet()));
        assertTrue(entries.stream().allMatch(r->r.method().isAnnotationPresent(NativeProtectedOperation.class)));
    }

    @Test void protectedHistoricalBaselineReadRoutesAreExact() {
        var rows = new HidraTrustedNativeProtectedRouteRegistry().candidates().stream()
                .filter(r -> r.controller().getSimpleName().equals("EngineeringBaselineReadController")).toList();
        assertEquals(5, rows.size());
        assertEquals(java.util.Set.of(
                "GET /api/v1/topology/engineering-baselines/publications/{receiptId}",
                "GET /api/v1/topology/engineering-baselines/publications/current",
                "GET /api/v1/topology/engineering-baselines/publications/effective",
                "GET /api/v1/topology/engineering-baselines/publications/history",
                "GET /api/v1/topology/engineering-baselines/publications/compare"),
                rows.stream().map(r -> r.verb() + " " + r.pattern()).collect(java.util.stream.Collectors.toSet()));
        assertTrue(rows.stream().allMatch(r -> r.method().isAnnotationPresent(NativeProtectedOperation.class)));
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
    @Test void pipelineRoutesDeclareThreeExactCompiledMarkedSignaturesAndNoSelfActivation(){
        var registry=new HidraTrustedNativeProtectedRouteRegistry();
        var rows=registry.candidates().stream().filter(r->r.controller().getSimpleName().equals("PipelineAuthoringController")).toList();
        assertEquals(3,rows.size());
        assertEquals(java.util.Set.of("PUT /api/v1/topology/engineering-networks/{draftId}/authored-topology","GET /api/v1/topology/engineering-networks/{draftId}/authored-topology","GET /api/v1/topology/engineering-networks/{draftId}/authoring-choices"),rows.stream().map(r->r.verb()+" "+r.pattern()).collect(java.util.stream.Collectors.toSet()));
        assertEquals(java.util.Set.of("write","view","choices"),rows.stream().map(r->r.method().getName()).collect(java.util.stream.Collectors.toSet()));
        var owner=new dz.sh.hidra.modules.topology.api.rest.controller.PipelineAuthoringController(org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.in.PipelineAuthoringUseCase.class),org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.in.AuthoredTopologyViewUseCase.class));
        for(var row:rows){assertTrue(row.method().isAnnotationPresent(NativeProtectedOperation.class));assertFalse(registry.isTrusted(new HandlerMethod(owner,row.method()),row.verb(),row.pattern()));}
    }
}
