/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraTrustedNativeProtectedRouteHttpTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
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
 @Test void t24DeclaredNativeRoutesKeepExactMethodAndSignature() {
    var routes=new HidraTrustedNativeProtectedRouteRegistry().candidates().stream()
        .filter(x->x.controller().getSimpleName().equals("ControlledDocumentsController")).toList();
    assertEquals(9,routes.size());
    assertTrue(routes.stream().allMatch(x->x.method().isAnnotationPresent(NativeProtectedOperation.class)));
    assertEquals(9,routes.stream().map(x->x.verb()+" "+x.pattern()).distinct().count());
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
   assertEquals(138,registry.candidates().size());
   var mvc = mvc(owner, new UntrustedController(), resolver,registry);
   assertThrows(Exception.class, () ->
      mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(TRUSTED)));
   assertEquals(0, owner.calls.get());
 }
 @Test void realManufacturerTupleRegistryRejectsWrongVerbAndSiblingPattern() throws Exception {
   var registry=new HidraTrustedNativeProtectedRouteRegistry();
   var routes=registry.candidates();
   assertEquals(138,routes.size());
   var expected=java.util.Set.of(
     "POST /api/v1/hse/companies/{companyId}/facilities/{facilityId}/cases/from-incident",
     "POST /api/v1/hse/companies/{companyId}/facilities/{facilityId}/cases/{caseId}/impact-assessments",
     "GET /api/v1/hse/companies/{companyId}/facilities/{facilityId}/cases",
     "GET /api/v1/hse/companies/{companyId}/facilities/{facilityId}/cases/{caseId}",
     "POST /api/v1/hse/companies/{companyId}/facilities/{facilityId}/cases/{caseId}/capas",
     "GET /api/v1/hse/companies/{companyId}/facilities/{facilityId}/cases/{caseId}/capas",
     "GET /api/v1/hse/companies/{companyId}/facilities/{facilityId}/cases/{caseId}/capas/{capaId}",
     "POST /api/v1/hse/companies/{companyId}/facilities/{facilityId}/cases/{caseId}/capas/{capaId}/approve",
     "POST /api/v1/hse/companies/{companyId}/facilities/{facilityId}/cases/{caseId}/capas/{capaId}/assign",
     "POST /api/v1/hse/companies/{companyId}/facilities/{facilityId}/cases/{caseId}/capas/{capaId}/evidence",
     "POST /api/v1/hse/companies/{companyId}/facilities/{facilityId}/cases/{caseId}/capas/{capaId}/complete",
     "POST /api/v1/hse/companies/{companyId}/facilities/{facilityId}/cases/{caseId}/capas/{capaId}/verify",
     "POST /api/v1/hse/companies/{companyId}/facilities/{facilityId}/cases/{caseId}/close",
     "GET /api/v1/hse/companies/{companyId}/facilities/{facilityId}/cases/{caseId}/history",
     "POST /api/v1/documents/companies/{companyId}/facilities/{facilityId}/controlled-documents",
     "GET /api/v1/documents/companies/{companyId}/facilities/{facilityId}/controlled-documents",
     "GET /api/v1/documents/companies/{companyId}/facilities/{facilityId}/controlled-documents/{documentId}",
     "GET /api/v1/documents/companies/{companyId}/facilities/{facilityId}/controlled-documents/{documentId}/versions",
     "GET /api/v1/documents/companies/{companyId}/facilities/{facilityId}/controlled-documents/{documentId}/links",
     "POST /api/v1/documents/companies/{companyId}/facilities/{facilityId}/controlled-documents/{documentId}/versions/upload",
     "GET /api/v1/documents/companies/{companyId}/facilities/{facilityId}/controlled-documents/{documentId}/versions/{versionId}/content",
     "POST /api/v1/documents/companies/{companyId}/facilities/{facilityId}/controlled-documents/{documentId}/versions/{versionId}/accept",
     "POST /api/v1/documents/companies/{companyId}/facilities/{facilityId}/controlled-documents/{documentId}/versions/{versionId}/supersede",
     "POST /api/v1/analytics/companies/{companyId}/facilities/{facilityId}/deviation-metric-runs",
     "GET /api/v1/analytics/companies/{companyId}/facilities/{facilityId}/deviation-metric-runs/{runId}",
     "GET /api/v1/risk/assessments/{id}",
     "GET /api/v1/risk/assessments/{id}/decision-history",
     "POST /api/v1/planning/revisions/{revisionId}/nominations",
     "POST /api/v1/planning/nominations/{id}/disposition",
     "GET /api/v1/planning/nominations/{id}/history",
     "POST /api/v1/integrity/findings/{findingId}/maintenance-approvals",
     "GET /api/v1/integrity/findings/{findingId}/maintenance-handoffs/{approvalId}",
     "GET /api/v1/custody/measurement-periods/{periodId}",
     "GET /api/v1/custody/measurement-periods/{periodId}/reconciliations",
     "POST /api/v1/custody/measurement-periods/{periodId}/reconciliations",
     "GET /api/v1/custody/reconciliations/{reconciliationId}",
     "GET /api/v1/custody/transfer-tickets/{ticketId}",
     "GET /api/v1/custody/transfer-tickets/{ticketId}/history",
     "POST /api/v1/custody/transfer-tickets/{ticketId}/finalizations",
     "POST /api/v1/custody/transfer-tickets/{ticketId}/corrections",
     "GET /api/v1/custody/reconciliations/{reconciliationId}/discrepancies",
     "GET /api/v1/custody/discrepancies/{discrepancyId}",
     "POST /api/v1/custody/discrepancies/{discrepancyId}/dispositions",
     "POST /api/v1/custody/measurement-periods/{periodId}/measurements",
     "GET /api/v1/custody/measurement-periods/{periodId}/measurements/{measurementId}",
     "POST /api/v1/assets/maintenance-work-orders/from-integrity",
     "POST /api/v1/assets/maintenance-work-orders/{id}/assignment",
     "POST /api/v1/assets/maintenance-work-orders/{id}/start",
     "POST /api/v1/assets/maintenance-work-orders/{id}/execution",
     "POST /api/v1/assets/maintenance-work-orders/{id}/verification",
     "GET /api/v1/assets/maintenance-work-orders/{id}/integrity-history",
     "GET /api/v1/incident/incidents/{id}/investigation",
     "POST /api/v1/incident/incidents/{id}/investigation/assign",
     "POST /api/v1/incident/incidents/{id}/investigation/evidence",
     "POST /api/v1/incident/incidents/{id}/investigation/actions",
     "POST /api/v1/incident/incidents/{id}/investigation/resolution",
     "POST /api/v1/incident/incidents/{id}/investigation/review",
     "POST /api/v1/incident/incidents/{id}/investigation/close",
     "POST /api/v1/leakdetection/cases/from-candidate",
     "GET /api/v1/leakdetection/cases/{id}/investigation",
     "POST /api/v1/leakdetection/cases/{id}/investigation/assign",
     "POST /api/v1/leakdetection/cases/{id}/investigation/evidence",
     "POST /api/v1/leakdetection/cases/{id}/investigation/actions",
     "POST /api/v1/leakdetection/cases/{id}/investigation/disposition",
     "POST /api/v1/leakdetection/cases/{id}/investigation/review",
     "POST /api/v1/incident/incidents/from-evidence",
     "GET /api/v1/incident/incidents/{id}/escalation",
     "GET /api/v1/incident/incidents/{id}/timeline",
     "GET /api/v1/incident/incidents",
     "GET /api/v1/incident/incidents/{id}",
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
    var incidentEvidence=new dz.sh.hidra.modules.incident.api.rest.controller.EvidenceLinkedIncidentEscalationController(
         org.mockito.Mockito.mock(dz.sh.hidra.modules.incident.application.port.in.EscalateEvidenceToIncidentUseCase.class));
    owners.put(incidentEvidence.getClass(),incidentEvidence);
    var incidentRead=new dz.sh.hidra.modules.incident.api.rest.controller.IncidentQueryController(
         org.mockito.Mockito.mock(dz.sh.hidra.modules.incident.application.port.in.IncidentQueryUseCase.class));
    owners.put(incidentRead.getClass(),incidentRead);
    var t16Incident=new dz.sh.hidra.modules.incident.api.rest.controller.IncidentInvestigationController(
         org.mockito.Mockito.mock(dz.sh.hidra.modules.incident.application.port.in.IncidentInvestigationUseCase.class));
    owners.put(t16Incident.getClass(),t16Incident);
    var t16Leak=new dz.sh.hidra.modules.leakdetection.api.rest.controller.LeakInvestigationController(
         org.mockito.Mockito.mock(dz.sh.hidra.modules.leakdetection.application.port.in.LeakInvestigationUseCase.class));
    owners.put(t16Leak.getClass(),t16Leak);
    var t18Integrity=new dz.sh.hidra.modules.integrity.api.rest.controller.IntegrityMaintenanceApprovalController(
        org.mockito.Mockito.mock(dz.sh.hidra.modules.integrity.application.port.in.IntegrityMaintenanceApprovalUseCase.class));
    owners.put(t18Integrity.getClass(),t18Integrity);
    var t18Assets=new dz.sh.hidra.modules.assets.api.rest.controller.IntegrityWorkOrderController(
        org.mockito.Mockito.mock(dz.sh.hidra.modules.assets.application.port.in.IntegrityWorkOrderUseCase.class));
    owners.put(t18Assets.getClass(),t18Assets);
    var nominations = new dz.sh.hidra.modules.planning.api.rest.controller.PlanningNominationCommandController(
        org.mockito.Mockito.mock(dz.sh.hidra.modules.planning.application.port.in.PlanningNominationCommandUseCase.class));
    owners.put(nominations.getClass(), nominations);
    var t20Custody = new dz.sh.hidra.modules.custody.api.rest.controller.CustodyMeasurementCaptureController(
        org.mockito.Mockito.mock(dz.sh.hidra.modules.custody.application.port.in.CustodyMeasurementCaptureUseCase.class));
    owners.put(t20Custody.getClass(), t20Custody);
    var t21Custody=new dz.sh.hidra.modules.custody.api.rest.controller.CustodyTransferLifecycleController(
        org.mockito.Mockito.mock(dz.sh.hidra.modules.custody.application.port.in.CustodyTransferLifecycleUseCase.class));
    owners.put(t21Custody.getClass(),t21Custody);
    var t22Risk=new dz.sh.hidra.modules.risk.api.rest.controller.GovernedRiskAssessmentController(
        org.mockito.Mockito.mock(dz.sh.hidra.modules.risk.application.port.in.ReadGovernedRiskAssessmentUseCase.class));
    owners.put(t22Risk.getClass(),t22Risk);
    var t23Analytics=new dz.sh.hidra.modules.analytics.api.rest.controller.DeviationMetricRunController(
       org.mockito.Mockito.mock(dz.sh.hidra.modules.analytics.application.port.in.DeviationMetricRunUseCase.class));
    owners.put(t23Analytics.getClass(),t23Analytics);
    var t24Documents=new dz.sh.hidra.modules.documents.api.rest.controller.ControlledDocumentsController(
      org.mockito.Mockito.mock(dz.sh.hidra.modules.documents.application.port.in.ControlledDocumentsUseCase.class),
      52428800L);
    owners.put(t24Documents.getClass(),t24Documents);
    var t17Hse=new dz.sh.hidra.modules.hse.api.rest.controller.HseCapaLifecycleController(
        org.mockito.Mockito.mock(dz.sh.hidra.modules.hse.application.port.in.HseCapaLifecycleUseCase.class));
    owners.put(t17Hse.getClass(),t17Hse);


    // The same logical operation has two distinct native-protected MVC methods, never one multi-pattern mapping.
    var ruleCreate=routes.stream().filter(r->r.pattern().equals("/api/v1/monitoring/rules")
        &&r.verb().equals("POST")).findFirst().orElseThrow();
    var aliasCreate=routes.stream().filter(r->r.pattern().equals("/api/v1/monitoring/create-monitoring-rule")
        &&r.verb().equals("POST")).findFirst().orElseThrow();
    assertTrue(!ruleCreate.method().equals(aliasCreate.method()));
    // T13 uses the same strict one-MVC-method-per-native-pattern rule.
    var leakCanonical=routes.stream().filter(v->v.pattern().equals("/api/v1/leakdetection/candidates")
        &&v.verb().equals("POST")).findFirst().orElseThrow();
    var leakLegacy=routes.stream().filter(v->v.pattern().equals("/api/v1/leakdetection/create-leak-candidate")
        &&v.verb().equals("POST")).findFirst().orElseThrow();
    assertTrue(!leakCanonical.method().equals(leakLegacy.method()));
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
