/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraTrustedNativeProtectedRouteHttpTest
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
   assertEquals(68,registry.candidates().size());
   var mvc = mvc(owner, new UntrustedController(), resolver,registry);
   assertThrows(Exception.class, () ->
      mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(TRUSTED)));
   assertEquals(0, owner.calls.get());
 }
 @Test void realManufacturerTupleRegistryRejectsWrongVerbAndSiblingPattern() throws Exception {
   var registry=new HidraTrustedNativeProtectedRouteRegistry();
   var routes=registry.candidates();
   assertEquals(68,routes.size());
   var expected=java.util.Set.of(
     "POST /api/v1/leakdetection/candidates/from-evaluation",
     "GET /api/v1/leakdetection/candidates/{id}/evidence",
     "POST /api/v1/leakdetection/candidates",
     "POST /api/v1/leakdetection/create-leak-candidate",
     "GET /api/v1/leakdetection/candidates",
     "GET /api/v1/leakdetection/candidates/{id}",
     "POST /api/v1/alarm/alarms/from-evaluation",
     "GET /api/v1/alarm/alarms/{id}/evidence",
    "POST /api/v1/monitoring/rules",
    "POST /api/v1/monitoring/create-monitoring-rule",
    "GET /api/v1/monitoring/rules",
    "GET /api/v1/monitoring/rules/{id}",
    "POST /api/v1/monitoring/rules/{id}/revisions",
    "POST /api/v1/monitoring/rules/{id}/revisions/{revision}/activate",
    "GET /api/v1/monitoring/rules/{id}/revisions/{revision}",
    "GET /api/v1/monitoring/rules/{id}/effective",
    "GET /api/v1/monitoring/rules/{id}/history",
    "POST /api/v1/monitoring/rules/{id}/evaluations",
    "GET /api/v1/monitoring/rules/{id}/evaluations",
    "GET /api/v1/monitoring/rules/{id}/evaluations/{evaluationId}",
    "POST /api/v1/telemetry/points/{pointId}/readings/{readingId}/assessment",
    "GET /api/v1/telemetry/points/{pointId}/readings/{readingId}/assessment",
    "POST /api/v1/telemetry/points/{pointId}/readings/{readingId}/trust",
    "GET /api/v1/telemetry/points/{pointId}/trusted-readings/{trustedId}",
    "GET /api/v1/telemetry/points/{pointId}/trusted-readings",
    "POST /api/v1/telemetry/commissioning",
    "POST /api/v1/telemetry/commissioning/points/{pointId}/bindings",
    "GET /api/v1/telemetry/commissioning/points/{pointId}/effective",
    "GET /api/v1/telemetry/commissioning/points/{pointId}/history",
    "GET /api/v1/topology/engineering-sources",
    "GET /api/v1/topology/engineering-sources/{kind}/{ownerId}/{sourceId}/revisions/{revisionId}",
    "GET /api/v1/workflow/definitions/installed-characteristic-link-target",
    "PUT /api/v1/simulation/manufacturer-characteristics/{sourceId}/revisions/{revisionId}",
    "GET /api/v1/simulation/manufacturer-characteristics/{sourceId}/revisions/{revisionId}",
    "GET /api/v1/simulation/manufacturer-characteristics/{sourceId}/history",
    "GET /api/v1/simulation/manufacturer-characteristics/{sourceId}/revisions/{revisionId}/certificate",
    "POST /api/v1/simulation/manufacturer-characteristics/{sourceId}/revisions/{revisionId}/qualification",
    "POST /api/v1/topology/installed-units/{unitId}/characteristic-links/{linkId}/revisions/{revisionId}/approval",
    "GET /api/v1/topology/installed-units/{unitId}/characteristic-links/{linkId}/revisions/{revisionId}/approval",
    "GET /api/v1/topology/installed-units/{unitId}/characteristic-links/{linkId}/approvals/history",
    "POST /api/v1/topology/engineering-baselines",
    "PUT /api/v1/topology/engineering-baselines/{manifestId}",
    "GET /api/v1/topology/engineering-baselines/{manifestId}",
    "GET /api/v1/topology/engineering-baselines/{manifestId}/revisions/{version}",
    "POST /api/v1/topology/engineering-baselines/{manifestId}/validation",
    "POST /api/v1/topology/engineering-baselines/{manifestId}/submissions",
    "POST /api/v1/topology/engineering-baselines/submissions/{submissionId}/publications",
    "GET /api/v1/topology/engineering-baselines/publication-receipts/{receiptId}",
    "GET /api/v1/topology/engineering-baselines/publications/{receiptId}",
    "GET /api/v1/topology/engineering-baselines/publications/current",
    "GET /api/v1/topology/engineering-baselines/publications/effective",
    "GET /api/v1/topology/engineering-baselines/publications/history",
    "GET /api/v1/topology/engineering-baselines/publications/compare",
    "PUT /api/v1/topology/engineering-networks/{draftId}/authored-topology",
    "GET /api/v1/topology/engineering-networks/{draftId}/authored-topology",
    "GET /api/v1/topology/engineering-networks/{draftId}/authoring-choices",
    "GET /api/v1/topology/foundation/choices/{catalog}",
    "POST /api/v1/topology/foundation/pipeline-systems",
    "POST /api/v1/topology/foundation/facilities",
    "POST /api/v1/topology/foundation/facilities/{facilityId}/activation",
    "POST /api/v1/topology/foundation/facilities/{facilityId}/equipment-positions",
    "GET /api/v1/topology/foundation/facilities/{facilityId}/equipment-positions",
    "GET /api/v1/topology/foundation/facilities/{facilityId}/installations",
    "PUT /api/v1/topology/facilities/{facilityId}/station-graph/revisions/{revisionId}",
    "GET /api/v1/topology/facilities/{facilityId}/station-graph/revisions/{revisionId}",
    "POST /api/v1/topology/facilities/{facilityId}/station-graph/publications",
    "GET /api/v1/topology/facilities/{facilityId}/station-graph/publications/current",
    "GET /api/v1/topology/facilities/{facilityId}/station-graph/publications/history");
   assertEquals(expected,routes.stream().map(r->r.verb()+" "+r.pattern())
    .collect(java.util.stream.Collectors.toSet()));
   var controller=new dz.sh.hidra.modules.simulation.api.rest.controller
    .ManufacturerCharacteristicRevisionController(org.mockito.Mockito.mock(
      dz.sh.hidra.modules.simulation.application.port.in
        .ManufacturerCharacteristicRevisionUseCase.class));
   var installed=new dz.sh.hidra.modules.topology.api.rest.controller.InstalledCharacteristicLinkController(
    org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase.class));
   var baselinePublication=new dz.sh.hidra.modules.topology.api.rest.controller.EngineeringBaselinePublicationController(
      org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselinePublicationUseCase.class));
    var baselineRead=new dz.sh.hidra.modules.topology.api.rest.controller.EngineeringBaselineReadController(
        org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselineReadUseCase.class));
   var baseline=new dz.sh.hidra.modules.topology.api.rest.controller.EngineeringBaselineDraftController(
      org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselineDraftUseCase.class));
    var pipeline=new dz.sh.hidra.modules.topology.api.rest.controller.PipelineAuthoringController(
      org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.in.PipelineAuthoringUseCase.class),
      org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.in.AuthoredTopologyViewUseCase.class));
    var foundation=new dz.sh.hidra.modules.topology.api.rest.controller.TopologyFoundationController(
      org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.in.TopologyFoundationUseCase.class));
    var station=new dz.sh.hidra.modules.topology.api.rest.controller.StationGraphController(
      org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase.class));
    var discovery=new dz.sh.hidra.modules.topology.api.rest.controller.EngineeringSourceDiscoveryController(
      org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.in.EngineeringSourceDiscoveryUseCase.class));
    var telemetryCommission=new dz.sh.hidra.modules.telemetry.api.rest.controller.TelemetryCommissioningController(
      org.mockito.Mockito.mock(dz.sh.hidra.modules.telemetry.application.port.in.TelemetryCommissioningUseCase.class));
    var workflow=new dz.sh.hidra.modules.workflow.api.rest.controller.WorkflowDefinitionDiscoveryController(
      org.mockito.Mockito.mock(dz.sh.hidra.modules.workflow.application.port.in.WorkflowDefinitionDiscoveryUseCase.class));
    var owners=new java.util.HashMap<Class<?>,Object>(java.util.Map.<Class<?>,Object>of(controller.getClass(),controller,installed.getClass(),installed,
      pipeline.getClass(),pipeline,baseline.getClass(),baseline,foundation.getClass(),foundation,station.getClass(),station,
      workflow.getClass(),workflow,discovery.getClass(),discovery,
       baselinePublication.getClass(),baselinePublication,baselineRead.getClass(),baselineRead));
    owners.put(telemetryCommission.getClass(),telemetryCommission);
    var telemetryQuality=new dz.sh.hidra.modules.telemetry.api.rest.controller.TelemetryQualityController(
       org.mockito.Mockito.mock(dz.sh.hidra.modules.telemetry.application.port.in.QualifyTelemetryReadingUseCase.class),
       org.mockito.Mockito.mock(dz.sh.hidra.modules.telemetry.application.port.in.TrustedTelemetryReadingQueryUseCase.class));
    owners.put(telemetryQuality.getClass(),telemetryQuality);
    var monitoringWrite=new dz.sh.hidra.modules.monitoring.api.rest.controller.SpringMonitoringController(
        org.mockito.Mockito.mock(dz.sh.hidra.modules.monitoring.application.port.in.CreateMonitoringRuleUseCase.class),
        org.mockito.Mockito.mock(dz.sh.hidra.modules.monitoring.application.port.in.RecordDeviationUseCase.class),
        org.mockito.Mockito.mock(dz.sh.hidra.modules.monitoring.application.port.in.ActivateMonitoringRuleRevisionUseCase.class));
    var monitoringRead=new dz.sh.hidra.modules.monitoring.api.rest.controller.MonitoringQueryController(
        org.mockito.Mockito.mock(dz.sh.hidra.modules.monitoring.application.port.in.MonitoringQueryUseCase.class));
    owners.put(monitoringWrite.getClass(),monitoringWrite);
    owners.put(monitoringRead.getClass(),monitoringRead);
    var evaluations=new dz.sh.hidra.modules.monitoring.api.rest.controller.AuthoritativeDeviationController(
        org.mockito.Mockito.mock(dz.sh.hidra.modules.monitoring.application.port.in.AuthoritativeDeviationUseCase.class));
    owners.put(evaluations.getClass(),evaluations);
    var alarmEvidenceController = new dz.sh.hidra.modules.alarm.api.rest.controller.EvidenceLinkedAlarmController(
        org.mockito.Mockito.mock(dz.sh.hidra.modules.alarm.application.port.in.EvidenceLinkedAlarmUseCase.class));
    owners.put(alarmEvidenceController.getClass(), alarmEvidenceController);
    var leakEvidenceController=new dz.sh.hidra.modules.leakdetection.api.rest.controller.EvidenceLinkedLeakCandidateController(
        org.mockito.Mockito.mock(dz.sh.hidra.modules.leakdetection.application.port.in.EvidenceLinkedLeakCandidateUseCase.class));
    owners.put(leakEvidenceController.getClass(),leakEvidenceController);
    var leakWriteController=new dz.sh.hidra.modules.leakdetection.api.rest.controller.SpringLeakdetectionController(
        org.mockito.Mockito.mock(dz.sh.hidra.modules.leakdetection.application.port.in.CreateLeakCandidateUseCase.class),
        org.mockito.Mockito.mock(dz.sh.hidra.modules.leakdetection.application.port.in.EscalateLeakCaseUseCase.class),
        org.mockito.Mockito.mock(dz.sh.hidra.modules.leakdetection.application.port.in.OpenLeakCaseUseCase.class));
    owners.put(leakWriteController.getClass(),leakWriteController);
    var leakReadController=new dz.sh.hidra.modules.leakdetection.api.rest.controller.LeakDetectionQueryController(
        org.mockito.Mockito.mock(dz.sh.hidra.modules.leakdetection.application.port.in.LeakDetectionQueryUseCase.class));
    owners.put(leakReadController.getClass(),leakReadController);

    // The same logical operation has two distinct native-protected MVC methods, never one multi-pattern mapping.
    var ruleCreate=routes.stream().filter(r->r.pattern().equals("/api/v1/monitoring/rules")
        &&r.verb().equals("POST")).findFirst().orElseThrow();
    var aliasCreate=routes.stream().filter(r->r.pattern().equals("/api/v1/monitoring/create-monitoring-rule")
        &&r.verb().equals("POST")).findFirst().orElseThrow();
    assertTrue(!ruleCreate.method().equals(aliasCreate.method()));
    var actual=new RequestMappingHandlerMapping();
   for(var entry:routes) {
    Object owner=owners.get(entry.controller());
    assertTrue(owner!=null);
    actual.registerMapping(RequestMappingInfo.paths(entry.pattern())
      .methods(RequestMethod.valueOf(entry.verb())).build(),owner,entry.method());
   }
   new HidraTrustedNativeProtectedRouteStartupValidator(actual,registry).validate();
   assertTrue(!registry.isTrusted(new org.springframework.web.method.HandlerMethod(workflow,
      workflow.getClass().getMethod("definitions",String.class,String.class,String.class,String.class,String.class)),
      "GET","/api/v1/workflow/definitions"));
   for(var entry:routes) {
    var handler=new org.springframework.web.method.HandlerMethod(
      owners.get(entry.controller()),entry.method());
    assertTrue(registry.isTrusted(handler,entry.verb(),entry.pattern()));
    assertTrue(!registry.isTrusted(handler,
      entry.verb().equals("GET")?"PUT":"GET",entry.pattern()));
    assertTrue(!registry.isTrusted(handler,entry.verb(),entry.pattern()+"/sibling"));
   }
 }
}
