/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraTrustedNativeProtectedRouteRegistry
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.permissions
 *
 * @Description : Preserves exact startup-verified native HTTP delegation and its negative boundaries.
 *
 */
package dz.sh.hidra.platform.permissions;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

/**
 * Independently controlled Platform whitelist for native-protected HTTP routes.
 * No business module, annotation scan or application property can add entries.
 * Runtime activates only explicitly registered native owner tuples after
 * exact Spring MVC compilation validation. This is NOT a native Identity grant.
 */
@Component
public final class HidraTrustedNativeProtectedRouteRegistry {

    private final List<Route> candidates;
    private volatile Set<Route> verified = Set.of();

    public HidraTrustedNativeProtectedRouteRegistry() {
        this(approvedCandidates());
    }

    // Deliberately package-private for Platform's synthetic qualification tests.
    HidraTrustedNativeProtectedRouteRegistry(List<Route> candidates) {
        this.candidates = List.copyOf(Objects.requireNonNull(candidates, "trusted route declarations"));
        if (new HashSet<>(this.candidates).size() != this.candidates.size()) {
            throw new IllegalArgumentException("Duplicate trusted native-protected route");
        }
    }

    /**
     * Independent, fixed Platform-owned exact compiled tuple registration.
     * Literal reflective class/method signatures avoid a prohibited Platform->module
     * compile dependency. No scans, configuration overrides or runtime registration.
     */
    private static List<Route> approvedCandidates() {
        final String prefix="dz.sh.hidra.modules.simulation.api.rest.";
        final String controllerName=prefix+"controller.ManufacturerCharacteristicRevisionController";
        final String base="/api/v1/simulation/manufacturer-characteristics/{sourceId}";
        try {
            Class<?> controller=Class.forName(controllerName);
            Class<?> save=Class.forName(prefix+"request.ManufacturerCharacteristicRevisionRequests$Save");
            Class<?> qualify=Class.forName(prefix+"request.ManufacturerCharacteristicRevisionRequests$Qualify");
            // Four independently compiled native-protected T31 scenario endpoints.
            Class<?> t31=Class.forName(prefix+"controller.ScenarioInputRevisionController");
            Class<?> t31Create=Class.forName(prefix+"request.ScenarioInputRevisionRequests$Create");
            Class<?> t31Append=Class.forName(prefix+"request.ScenarioInputRevisionRequests$Append");
            String t31Base="/api/v1/simulation/companies/{companyId}/pipeline-systems/{pipelineSystemId}/scenarios";
            final String topology="dz.sh.hidra.modules.topology.api.rest.";
            Class<?> installed=Class.forName(topology+"controller.InstalledCharacteristicLinkController");
            Class<?> approval=Class.forName(topology+"request.InstalledCharacteristicLinkRequests$CharacteristicLinkApprovalCommand");
            final String linkBase="/api/v1/topology/installed-units/{unitId}/characteristic-links/{linkId}";
            Class<?> discovery=Class.forName(topology+"controller.EngineeringSourceDiscoveryController");
            Class<?> discoveryKind=Class.forName("dz.sh.hidra.modules.topology.application.port.in.EngineeringSourceDiscoveryUseCase$Kind");
            final String sourceBase="/api/v1/topology/engineering-sources";
            Class<?> baseline=Class.forName(topology+"controller.EngineeringBaselineDraftController");
            Class<?> baselineCreate=Class.forName(topology+"request.EngineeringBaselineDraftRequests$Create");
            Class<?> baselineUpdate=Class.forName(topology+"request.EngineeringBaselineDraftRequests$Update");
            Class<?> baselinePublication=Class.forName(topology+"controller.EngineeringBaselinePublicationController");
            Class<?> baselineReads=Class.forName(topology+"controller.EngineeringBaselineReadController");
            Class<?> submitBaseline=Class.forName(topology+"request.EngineeringBaselinePublicationRequests$Submit");
            Class<?> publishBaseline=Class.forName(topology+"request.EngineeringBaselinePublicationRequests$Publish");
            final String baselineBase="/api/v1/topology/engineering-baselines";
            Class<?> pipeline=Class.forName(topology+"controller.PipelineAuthoringController");
            Class<?> pipelineWrite=Class.forName(topology+"request.PipelineAuthoringRequests$Write");
            final String pipelineBase="/api/v1/topology/engineering-networks/{draftId}";
            Class<?> foundation=Class.forName(topology+"controller.TopologyFoundationController");
            Class<?> foundationCatalog=Class.forName("dz.sh.hidra.modules.topology.application.port.in.TopologyFoundationUseCase$Catalog");
            Class<?> foundationSystem=Class.forName(topology+"request.TopologyFoundationRequests$CreateSystem");
            Class<?> foundationFacility=Class.forName(topology+"request.TopologyFoundationRequests$RegisterFacility");
            Class<?> foundationActivation=Class.forName(topology+"request.TopologyFoundationRequests$ActivateFacility");
            Class<?> foundationPosition=Class.forName(topology+"request.TopologyFoundationRequests$RegisterPosition");
            String foundationBase="/api/v1/topology/foundation";
            Class<?> station=Class.forName(topology+"controller.StationGraphController");
            Class<?> stationAuthor=Class.forName(topology+"request.StationGraphRequests$Author");
            Class<?> stationPublish=Class.forName(topology+"request.StationGraphRequests$Publish");
            String stationBase="/api/v1/topology/facilities/{facilityId}/station-graph";
            Class<?> workflow=Class.forName("dz.sh.hidra.modules.workflow.api.rest.controller.WorkflowDefinitionDiscoveryController");
            final String telemetryBase="/api/v1/telemetry/commissioning";
            final String telemetryPkg="dz.sh.hidra.modules.telemetry.api.rest.";
            Class<?> telemetryCommissioning=Class.forName(telemetryPkg+"controller.TelemetryCommissioningController");
            Class<?> telemetryEnroll=Class.forName(telemetryPkg+"request.TelemetryCommissioningRequests$Enroll");
            Class<?> telemetryRebind=Class.forName(telemetryPkg+"request.TelemetryCommissioningRequests$Rebind");
            Class<?> telemetryQuality=Class.forName(telemetryPkg+"controller.TelemetryQualityController");
            Class<?> qualityCommand=Class.forName(telemetryPkg+"request.TelemetryQualityRequests$Command");
            String telemetryPointBase="/api/v1/telemetry/points/{pointId}";
            final String monitoring="dz.sh.hidra.modules.monitoring.api.rest.";
            Class<?> monitoringWrite=Class.forName(monitoring+"controller.SpringMonitoringController");
            Class<?> monitoringRead=Class.forName(monitoring+"controller.MonitoringQueryController");
            Class<?> monitoringCreate=Class.forName(monitoring+"request.CreateMonitoringRuleRequest");
            Class<?> monitoringDraft=Class.forName(monitoring+"request.MonitoringRuleRevisionRequests$Draft");
            Class<?> monitoringActivate=Class.forName(monitoring+"request.MonitoringRuleRevisionRequests$Activate");
            Class<?> monitoringEvaluation=Class.forName(monitoring+"controller.AuthoritativeDeviationController");
            Class<?> monitoringEvaluationRequest=Class.forName(monitoring+"request.AuthoritativeDeviationRequest");
            String monitoringBase="/api/v1/monitoring/rules";
            final String alarmRest="dz.sh.hidra.modules.alarm.api.rest.";
            Class<?> alarmEvaluation=Class.forName(alarmRest+"controller.EvidenceLinkedAlarmController");
            Class<?> alarmEvaluationRequest=Class.forName(alarmRest+"request.EvidenceLinkedAlarmRequest");
            String alarmBase="/api/v1/alarm/alarms";
            final String leakRest="dz.sh.hidra.modules.leakdetection.api.rest.";
            Class<?> leakEvidence=Class.forName(leakRest+"controller.EvidenceLinkedLeakCandidateController");
            Class<?> leakRequest=Class.forName(leakRest+"request.LeakCandidateFromEvaluationRequest");
            Class<?> leakWrite=Class.forName(leakRest+"controller.SpringLeakdetectionController");
            Class<?> leakLegacyRequest=Class.forName(leakRest+"request.CreateLeakCandidateRequest");
            Class<?> leakRead=Class.forName(leakRest+"controller.LeakDetectionQueryController");
            String leakBase="/api/v1/leakdetection/candidates";
            final String incidentRest="dz.sh.hidra.modules.incident.api.rest.";
            Class<?> incidentEvidence=Class.forName(incidentRest+"controller.EvidenceLinkedIncidentEscalationController");
            Class<?> incidentRequest=Class.forName(incidentRest+"request.EscalateEvidenceToIncidentRequest");
            Class<?> incidentRead=Class.forName(incidentRest+"controller.IncidentQueryController");
            String incidentBase="/api/v1/incident/incidents";
            Class<?> incidentInvestigation=Class.forName(incidentRest+"controller.IncidentInvestigationController");
            Class<?> ia=Class.forName(incidentRest+"request.IncidentInvestigationRequest$Assign");
            Class<?> ie=Class.forName(incidentRest+"request.IncidentInvestigationRequest$Evidence");
            Class<?> ix=Class.forName(incidentRest+"request.IncidentInvestigationRequest$InvestigationResponseAction");
            Class<?> is=Class.forName(incidentRest+"request.IncidentInvestigationRequest$Resolve");
            Class<?> ir=Class.forName(incidentRest+"request.IncidentInvestigationRequest$Review");
            Class<?> ic=Class.forName(incidentRest+"request.IncidentInvestigationRequest$Close");
            String incidentInvestigationBase=incidentBase+"/{id}/investigation";
            Class<?> leakInvestigation=Class.forName(leakRest+"controller.LeakInvestigationController");
            Class<?> lo=Class.forName(leakRest+"request.LeakInvestigationRequest$Open");
            Class<?> la=Class.forName(leakRest+"request.LeakInvestigationRequest$Assign");
            Class<?> le=Class.forName(leakRest+"request.LeakInvestigationRequest$Evidence");
            Class<?> lx=Class.forName(leakRest+"request.LeakInvestigationRequest$LeakVerificationAction");
            Class<?> ld=Class.forName(leakRest+"request.LeakInvestigationRequest$Disposition");
            Class<?> lr=Class.forName(leakRest+"request.LeakInvestigationRequest$Review");
            String leakInvestigationBase="/api/v1/leakdetection/cases/{id}/investigation";


            final String integrityMaintenanceRoot="dz.sh.hidra.modules.integrity.api.rest.";
            Class<?> integrityMaintenance=Class.forName(integrityMaintenanceRoot+"controller.IntegrityMaintenanceApprovalController");
            Class<?> integrityApproval=Class.forName(integrityMaintenanceRoot+"request.IntegrityMaintenanceApprovalRequest");
            String integrityMaintenanceBase="/api/v1/integrity/findings/{findingId}";
            final String assetsMaintenanceRoot="dz.sh.hidra.modules.assets.api.rest.";
            Class<?> assetsMaintenance=Class.forName(assetsMaintenanceRoot+"controller.IntegrityWorkOrderController");
            Class<?> maintenanceCreate=Class.forName(assetsMaintenanceRoot+"request.IntegrityWorkOrderRequest$Create");
            Class<?> maintenanceAssign=Class.forName(assetsMaintenanceRoot+"request.IntegrityWorkOrderRequest$Assign");
            Class<?> maintenanceStart=Class.forName(assetsMaintenanceRoot+"request.IntegrityWorkOrderRequest$Start");
            Class<?> maintenanceExecute=Class.forName(assetsMaintenanceRoot+"request.IntegrityWorkOrderRequest$Execute");
            Class<?> maintenanceVerify=Class.forName(assetsMaintenanceRoot+"request.IntegrityWorkOrderRequest$Verify");
            String assetsMaintenanceBase="/api/v1/assets/maintenance-work-orders";

            String nominationPackage = "dz.sh.hidra.modules.planning.api.rest.";
            Class<?> nominationController = Class.forName(nominationPackage + "controller.PlanningNominationCommandController");
            Class<?> nominationCreate = Class.forName(nominationPackage + "request.PlanningNominationCommandRequest$Create");
            Class<?> nominationDisposition = Class.forName(nominationPackage + "request.PlanningNominationCommandRequest$Disposition");
            Class<?> custodyController=Class.forName("dz.sh.hidra.modules.custody.api.rest.controller.CustodyMeasurementCaptureController");
            Class<?> custodyCapture=Class.forName("dz.sh.hidra.modules.custody.api.rest.request.CustodyMeasurementCaptureRequest");
            final String t21Root="dz.sh.hidra.modules.custody.api.rest.";
            Class<?> t21=Class.forName(t21Root+"controller.CustodyTransferLifecycleController");
            Class<?> t21Reconcile=Class.forName(t21Root+"request.CustodyTransferLifecycleRequests$Reconcile");
            Class<?> t21Finalize=Class.forName(t21Root+"request.CustodyTransferLifecycleRequests$Finalize");
            Class<?> t21Correct=Class.forName(t21Root+"request.CustodyTransferLifecycleRequests$Correct");
            Class<?> t21Disposition=Class.forName(t21Root+"request.CustodyTransferLifecycleRequests$Disposition");
            String t21Periods="/api/v1/custody/measurement-periods/{periodId}";
            String t21Reconciliations="/api/v1/custody/reconciliations/{reconciliationId}";
            String t21Tickets="/api/v1/custody/transfer-tickets/{ticketId}";
            String t21Discrepancies="/api/v1/custody/discrepancies/{discrepancyId}";
            Class<?> t22=Class.forName("dz.sh.hidra.modules.risk.api.rest.controller.GovernedRiskAssessmentController");
            final String t23Base="/api/v1/analytics/companies/{companyId}/facilities/{facilityId}/deviation-metric-runs";
            Class<?> t23=Class.forName("dz.sh.hidra.modules.analytics.api.rest.controller.DeviationMetricRunController");
            Class<?> t23Request=Class.forName("dz.sh.hidra.modules.analytics.api.rest.request.ExecuteDeviationMetricRunRequest");
            final String t24Pkg="dz.sh.hidra.modules.documents.api.rest.";
            final String t24Base="/api/v1/documents/companies/{companyId}/facilities/{facilityId}/controlled-documents";
            Class<?> t24=Class.forName(t24Pkg+"controller.ControlledDocumentsController");
            Class<?> t24Create=Class.forName(t24Pkg+"request.ControlledDocumentRequests$Create");
            Class<?> t24Accept=Class.forName(t24Pkg+"request.ControlledDocumentRequests$Accept");
            Class<?> t24Supersede=Class.forName(t24Pkg+"request.ControlledDocumentRequests$Supersede");
            Class<?> t24Binary=Class.forName(t24Pkg+"request.UploadDocumentBinaryVersionRequest");
            Class<?> t24Multipart=Class.forName("org.springframework.web.multipart.MultipartFile");
            final String t17Root="dz.sh.hidra.modules.hse.api.rest.";
            Class<?> t17=Class.forName(t17Root+"controller.HseCapaLifecycleController");
            Class<?> t17Enroll=Class.forName(t17Root+"request.HseCapaLifecycleRequest$Enroll");
            Class<?> t17Propose=Class.forName(t17Root+"request.HseCapaLifecycleRequest$Propose");
            Class<?> t17Action=Class.forName(t17Root+"request.HseCapaLifecycleRequest$ScopedHseCapaAction");
            String t25Package="dz.sh.hidra.modules.reporting.api.rest.";
            Class<?> t25=Class.forName(t25Package+"controller.ScopedReportsController");
            Class<?> t25Submit=Class.forName(t25Package+"request.ScopedReportRequests$T25SubmitReport");
            Class<?> t25Render=Class.forName(t25Package+"request.ScopedReportRequests$T25RenderReport");
            Class<?> t25Seal=Class.forName(t25Package+"request.ScopedReportRequests$T25SealReport");
            String t26Package="dz.sh.hidra.modules.integration.api.rest.";
            Class<?> t26=Class.forName(t26Package+"controller.SyntheticExchangesController");
            Class<?> t26Submit=Class.forName(t26Package+"request.SyntheticExchangeRequests$T26SubmitExchange");
            Class<?> t26Replay=Class.forName(t26Package+"request.SyntheticExchangeRequests$T26ReplayExchange");
            String t26Base="/api/v1/integration/companies/{companyId}/facilities/{facilityId}";
            String t25Base="/api/v1/reporting/companies/{companyId}/facilities/{facilityId}";
            String t17Base="/api/v1/hse/companies/{companyId}/facilities/{facilityId}/cases";
            Class<?> t27=Class.forName("dz.sh.hidra.modules.notification.api.rest.controller.WorkflowInboxController");
            String t27Base="/api/v1/notification/companies/{companyId}/facilities/{facilityId}/inbox";
            Class<?> t28=Class.forName(
                    "dz.sh.hidra.modules.audit.api.rest.controller.ScopedAuditEvidenceController");
            Class<?> t28Create=Class.forName(
                    "dz.sh.hidra.modules.audit.api.rest.request.ScopedAuditEvidenceRequests$T28CreateAuditExport");
            String t28Base="/api/v1/audit/companies/{companyId}/facilities/{facilityId}";
            return List.of(
                new Route(t28,t28.getDeclaredMethod("search",String.class,String.class,
                    String.class,java.time.Instant.class,java.time.Instant.class,
                    Integer.class,String.class),"GET",t28Base+"/events"),
                new Route(t28,t28.getDeclaredMethod("createExport",String.class,String.class,t28Create),
                    "POST",t28Base+"/exports"),
                new Route(t28,t28.getDeclaredMethod("exportManifest",String.class,String.class,
                    java.util.UUID.class),"GET",t28Base+"/exports/{exportId}"),
                new Route(t28,t28.getDeclaredMethod("download",String.class,String.class,
                    java.util.UUID.class),"GET",t28Base+"/exports/{exportId}/content"),
                new Route(t27,t27.getDeclaredMethod("list",String.class,String.class,Integer.class,String.class),
                    "GET",t27Base),
                new Route(t27,t27.getDeclaredMethod("detail",String.class,String.class,String.class),
                    "GET",t27Base+"/{notificationId}"),
                new Route(t27,t27.getDeclaredMethod("acknowledge",String.class,String.class,String.class),
                    "POST",t27Base+"/{notificationId}/acknowledgement"),
                new Route(t26,t26.getDeclaredMethod("mapping",String.class,String.class,String.class),
                    "GET",t26Base+"/synthetic-mappings/{profileId}"),
                new Route(t26,t26.getDeclaredMethod("submit",String.class,String.class,t26Submit),
                    "POST",t26Base+"/synthetic-exchanges"),
                new Route(t26,t26.getDeclaredMethod("detail",String.class,String.class,String.class),
                    "GET",t26Base+"/synthetic-exchanges/{exchangeId}"),
                new Route(t26,t26.getDeclaredMethod("attempts",String.class,String.class,String.class),
                    "GET",t26Base+"/synthetic-exchanges/{exchangeId}/attempts"),
                new Route(t26,t26.getDeclaredMethod("replay",String.class,String.class,String.class,t26Replay),
                    "POST",t26Base+"/synthetic-exchanges/{exchangeId}/replay"),
                new Route(t25,t25.getDeclaredMethod("submit",String.class,String.class,t25Submit),
                    "POST",t25Base+"/report-requests"),
                new Route(t25,t25.getDeclaredMethod("render",String.class,String.class,String.class,t25Render),
                    "POST",t25Base+"/report-requests/{requestId}/render"),
                new Route(t25,t25.getDeclaredMethod("read",String.class,String.class,String.class),
                    "GET",t25Base+"/report-runs/{runId}"),
                new Route(t25,t25.getDeclaredMethod("seal",String.class,String.class,String.class,t25Seal),
                    "POST",t25Base+"/report-runs/{runId}/seal"),
                new Route(t25,t25.getDeclaredMethod("content",String.class,String.class,String.class),
                    "GET",t25Base+"/report-runs/{runId}/content"),
                new Route(t17,t17.getDeclaredMethod("assessImpact",String.class,String.class,String.class,
                        Class.forName(t17Root+"request.HseCapaLifecycleRequest$HseCaseImpactAssessmentRequest")),
                    "POST",t17Base+"/{caseId}/impact-assessments"),
                new Route(t17,t17.getDeclaredMethod("enroll",String.class,String.class,t17Enroll),
                    "POST",t17Base+"/from-incident"),
                new Route(t17,t17.getDeclaredMethod("cases",String.class,String.class,int.class,int.class,String.class),
                    "GET",t17Base),
                new Route(t17,t17.getDeclaredMethod("caseDetail",String.class,String.class,String.class),
                    "GET",t17Base+"/{caseId}"),
                new Route(t17,t17.getDeclaredMethod("propose",String.class,String.class,String.class,t17Propose),
                    "POST",t17Base+"/{caseId}/capas"),
                new Route(t17,t17.getDeclaredMethod("capas",String.class,String.class,String.class,int.class,int.class,String.class),
                    "GET",t17Base+"/{caseId}/capas"),
                new Route(t17,t17.getDeclaredMethod("capaDetail",String.class,String.class,String.class,String.class),
                    "GET",t17Base+"/{caseId}/capas/{capaId}"),
                new Route(t17,t17.getDeclaredMethod("approve",String.class,String.class,String.class,String.class,t17Action),
                    "POST",t17Base+"/{caseId}/capas/{capaId}/approve"),
                new Route(t17,t17.getDeclaredMethod("assign",String.class,String.class,String.class,String.class,t17Action),
                    "POST",t17Base+"/{caseId}/capas/{capaId}/assign"),
                new Route(t17,t17.getDeclaredMethod("evidence",String.class,String.class,String.class,String.class,t17Action),
                    "POST",t17Base+"/{caseId}/capas/{capaId}/evidence"),
                new Route(t17,t17.getDeclaredMethod("complete",String.class,String.class,String.class,String.class,t17Action),
                    "POST",t17Base+"/{caseId}/capas/{capaId}/complete"),
                new Route(t17,t17.getDeclaredMethod("verify",String.class,String.class,String.class,String.class,t17Action),
                    "POST",t17Base+"/{caseId}/capas/{capaId}/verify"),
                new Route(t17,t17.getDeclaredMethod("close",String.class,String.class,String.class,t17Action),
                    "POST",t17Base+"/{caseId}/close"),
                new Route(t17,t17.getDeclaredMethod("history",String.class,String.class,String.class,int.class,int.class),
                    "GET",t17Base+"/{caseId}/history"),
                new Route(t24,t24.getDeclaredMethod("create",String.class,String.class,t24Create),
                    "POST",t24Base),
                new Route(t24,t24.getDeclaredMethod("discover",String.class,String.class,Integer.class,Integer.class,String.class),
                    "GET",t24Base),
                new Route(t24,t24.getDeclaredMethod("detail",String.class,String.class,String.class),
                    "GET",t24Base+"/{documentId}"),
                new Route(t24,t24.getDeclaredMethod("versions",String.class,String.class,String.class,Integer.class,Integer.class),
                    "GET",t24Base+"/{documentId}/versions"),
                new Route(t24,t24.getDeclaredMethod("links",String.class,String.class,String.class,Integer.class,Integer.class),
                    "GET",t24Base+"/{documentId}/links"),
                new Route(t24,t24.getDeclaredMethod("upload",String.class,String.class,String.class,t24Binary,t24Multipart),
                    "POST",t24Base+"/{documentId}/versions/upload"),
                new Route(t24,t24.getDeclaredMethod("content",String.class,String.class,String.class,String.class),
                    "GET",t24Base+"/{documentId}/versions/{versionId}/content"),
                new Route(t24,t24.getDeclaredMethod("accept",String.class,String.class,String.class,String.class,t24Accept),
                    "POST",t24Base+"/{documentId}/versions/{versionId}/accept"),
                new Route(t24,t24.getDeclaredMethod("supersede",String.class,String.class,String.class,String.class,t24Supersede),
                    "POST",t24Base+"/{documentId}/versions/{versionId}/supersede"),
                new Route(t23,t23.getDeclaredMethod("execute",String.class,String.class,t23Request),
                        "POST",t23Base),
                new Route(t23,t23.getDeclaredMethod("read",String.class,String.class,String.class),
                        "GET",t23Base+"/{runId}"),
                new Route(t22,t22.getDeclaredMethod("assessment",String.class),"GET",
                    "/api/v1/risk/assessments/{id}"),
                new Route(t22,t22.getDeclaredMethod("decisionHistory",String.class),"GET",
                    "/api/v1/risk/assessments/{id}/decision-history"),
                new Route(t21,t21.getDeclaredMethod("period",String.class),"GET",t21Periods),
                new Route(t21,t21.getDeclaredMethod("reconciliations",String.class),"GET",t21Periods+"/reconciliations"),
                new Route(t21,t21.getDeclaredMethod("reconcile",String.class,t21Reconcile),"POST",t21Periods+"/reconciliations"),
                new Route(t21,t21.getDeclaredMethod("reconciliation",String.class),"GET",t21Reconciliations),
                new Route(t21,t21.getDeclaredMethod("ticket",String.class),"GET",t21Tickets),
                new Route(t21,t21.getDeclaredMethod("history",String.class),"GET",t21Tickets+"/history"),
                new Route(t21,t21.getDeclaredMethod("finalizeTicket",String.class,t21Finalize),"POST",t21Tickets+"/finalizations"),
                new Route(t21,t21.getDeclaredMethod("correctTicket",String.class,t21Correct),"POST",t21Tickets+"/corrections"),
                new Route(t21,t21.getDeclaredMethod("discrepancies",String.class),"GET",t21Reconciliations+"/discrepancies"),
                new Route(t21,t21.getDeclaredMethod("discrepancy",String.class),"GET",t21Discrepancies),
                new Route(t21,t21.getDeclaredMethod("dispose",String.class,t21Disposition),"POST",t21Discrepancies+"/dispositions"),
                new Route(custodyController,custodyController.getDeclaredMethod("capture",String.class,custodyCapture),
                    "POST","/api/v1/custody/measurement-periods/{periodId}/measurements"),
                new Route(custodyController,custodyController.getDeclaredMethod("read",String.class,String.class),
                    "GET","/api/v1/custody/measurement-periods/{periodId}/measurements/{measurementId}"),
                new Route(nominationController, nominationController.getDeclaredMethod("create", String.class, nominationCreate),
                    "POST", "/api/v1/planning/revisions/{revisionId}/nominations"),
                new Route(nominationController, nominationController.getDeclaredMethod("dispose", String.class, nominationDisposition),
                    "POST", "/api/v1/planning/nominations/{id}/disposition"),
                new Route(nominationController, nominationController.getDeclaredMethod("history", String.class),
                    "GET", "/api/v1/planning/nominations/{id}/history"),
                new Route(integrityMaintenance,integrityMaintenance.getDeclaredMethod("approve",String.class,integrityApproval),
                    "POST",integrityMaintenanceBase+"/maintenance-approvals"),
                new Route(integrityMaintenance,integrityMaintenance.getDeclaredMethod("read",String.class,String.class),
                    "GET",integrityMaintenanceBase+"/maintenance-handoffs/{approvalId}"),
                new Route(assetsMaintenance,assetsMaintenance.getDeclaredMethod("handoff",maintenanceCreate),
                    "POST",assetsMaintenanceBase+"/from-integrity"),
                new Route(assetsMaintenance,assetsMaintenance.getDeclaredMethod("assign",String.class,maintenanceAssign),
                    "POST",assetsMaintenanceBase+"/{id}/assignment"),
                new Route(assetsMaintenance,assetsMaintenance.getDeclaredMethod("start",String.class,maintenanceStart),
                    "POST",assetsMaintenanceBase+"/{id}/start"),
                new Route(assetsMaintenance,assetsMaintenance.getDeclaredMethod("execution",String.class,maintenanceExecute),
                    "POST",assetsMaintenanceBase+"/{id}/execution"),
                new Route(assetsMaintenance,assetsMaintenance.getDeclaredMethod("verification",String.class,maintenanceVerify),
                    "POST",assetsMaintenanceBase+"/{id}/verification"),
                new Route(assetsMaintenance,assetsMaintenance.getDeclaredMethod("history",String.class),
                    "GET",assetsMaintenanceBase+"/{id}/integrity-history"),

                new Route(incidentInvestigation,incidentInvestigation.getDeclaredMethod("history",String.class),
                    "GET",incidentInvestigationBase),
                new Route(incidentInvestigation,incidentInvestigation.getDeclaredMethod("assign",String.class,ia),
                    "POST",incidentInvestigationBase+"/assign"),
                new Route(incidentInvestigation,incidentInvestigation.getDeclaredMethod("evidence",String.class,ie),
                    "POST",incidentInvestigationBase+"/evidence"),
                new Route(incidentInvestigation,incidentInvestigation.getDeclaredMethod("action",String.class,ix),
                    "POST",incidentInvestigationBase+"/actions"),
                new Route(incidentInvestigation,incidentInvestigation.getDeclaredMethod("resolution",String.class,is),
                    "POST",incidentInvestigationBase+"/resolution"),
                new Route(incidentInvestigation,incidentInvestigation.getDeclaredMethod("review",String.class,ir),
                    "POST",incidentInvestigationBase+"/review"),
                new Route(incidentInvestigation,incidentInvestigation.getDeclaredMethod("close",String.class,ic),
                    "POST",incidentInvestigationBase+"/close"),
                new Route(leakInvestigation,leakInvestigation.getDeclaredMethod("open",lo),
                    "POST","/api/v1/leakdetection/cases/from-candidate"),
                new Route(leakInvestigation,leakInvestigation.getDeclaredMethod("history",String.class),
                    "GET",leakInvestigationBase),
                new Route(leakInvestigation,leakInvestigation.getDeclaredMethod("assign",String.class,la),
                    "POST",leakInvestigationBase+"/assign"),
                new Route(leakInvestigation,leakInvestigation.getDeclaredMethod("evidence",String.class,le),
                    "POST",leakInvestigationBase+"/evidence"),
                new Route(leakInvestigation,leakInvestigation.getDeclaredMethod("action",String.class,lx),
                    "POST",leakInvestigationBase+"/actions"),
                new Route(leakInvestigation,leakInvestigation.getDeclaredMethod("disposition",String.class,ld),
                    "POST",leakInvestigationBase+"/disposition"),
                new Route(leakInvestigation,leakInvestigation.getDeclaredMethod("review",String.class,lr),
                    "POST",leakInvestigationBase+"/review"),

                new Route(incidentEvidence,incidentEvidence.getDeclaredMethod("escalate",incidentRequest),
                    "POST",incidentBase+"/from-evidence"),
                new Route(incidentEvidence,incidentEvidence.getDeclaredMethod("escalation",String.class),
                    "GET",incidentBase+"/{id}/escalation"),
                new Route(incidentEvidence,incidentEvidence.getDeclaredMethod("timeline",String.class),
                    "GET",incidentBase+"/{id}/timeline"),
                new Route(incidentRead,incidentRead.getDeclaredMethod("incidents",int.class,int.class),
                    "GET",incidentBase),
                new Route(incidentRead,incidentRead.getDeclaredMethod("incident",String.class),
                    "GET",incidentBase+"/{id}"),
                new Route(leakEvidence,leakEvidence.getDeclaredMethod("create",leakRequest),
                    "POST",leakBase+"/from-evaluation"),
                new Route(leakEvidence,leakEvidence.getDeclaredMethod("evidence",String.class),
                    "GET",leakBase+"/{id}/evidence"),
                new Route(leakWrite,leakWrite.getDeclaredMethod("createLeakCandidate",leakLegacyRequest),
                    "POST",leakBase),
                new Route(leakWrite,leakWrite.getDeclaredMethod("createLegacyLeakCandidate",leakLegacyRequest),
                    "POST","/api/v1/leakdetection/create-leak-candidate"),
                new Route(leakRead,leakRead.getDeclaredMethod("candidates",int.class,int.class),
                    "GET",leakBase),
                new Route(leakRead,leakRead.getDeclaredMethod("candidate",String.class),
                    "GET",leakBase+"/{id}"),
                new Route(alarmEvaluation,alarmEvaluation.getDeclaredMethod("create",alarmEvaluationRequest),
                    "POST",alarmBase+"/from-evaluation"),
                new Route(alarmEvaluation,alarmEvaluation.getDeclaredMethod("evidence",String.class),
                    "GET",alarmBase+"/{id}/evidence"),
                new Route(monitoringEvaluation,monitoringEvaluation.getDeclaredMethod("evaluate",
                    String.class,monitoringEvaluationRequest),"POST",monitoringBase+"/{id}/evaluations"),
                new Route(monitoringEvaluation,monitoringEvaluation.getDeclaredMethod("exact",
                    String.class,String.class),"GET",monitoringBase+"/{id}/evaluations/{evaluationId}"),
                new Route(monitoringEvaluation,monitoringEvaluation.getDeclaredMethod("history",
                    String.class,int.class),"GET",monitoringBase+"/{id}/evaluations"),
                new Route(monitoringWrite,monitoringWrite.getDeclaredMethod("createMonitoringRule",monitoringCreate),
                    "POST",monitoringBase),
                new Route(monitoringWrite,monitoringWrite.getDeclaredMethod("createLegacyMonitoringRule",monitoringCreate),
                    "POST","/api/v1/monitoring/create-monitoring-rule"),
                new Route(monitoringRead,monitoringRead.getDeclaredMethod("rules",
                    String.class,String.class,String.class,String.class,int.class,int.class),
                    "GET",monitoringBase),
                new Route(monitoringRead,monitoringRead.getDeclaredMethod("rule",String.class),
                    "GET",monitoringBase+"/{id}"),
                new Route(monitoringWrite,monitoringWrite.getDeclaredMethod("createRevision",
                    String.class,monitoringDraft),
                    "POST",monitoringBase+"/{id}/revisions"),
                new Route(monitoringWrite,monitoringWrite.getDeclaredMethod("activateRevision",
                    String.class,long.class,monitoringActivate),
                    "POST",monitoringBase+"/{id}/revisions/{revision}/activate"),
                new Route(monitoringWrite,monitoringWrite.getDeclaredMethod("exactRevision",
                    String.class,long.class),
                    "GET",monitoringBase+"/{id}/revisions/{revision}"),
                new Route(monitoringWrite,monitoringWrite.getDeclaredMethod("effectiveRevision",
                    String.class,java.time.Instant.class),
                    "GET",monitoringBase+"/{id}/effective"),
                new Route(monitoringWrite,monitoringWrite.getDeclaredMethod("ruleHistory",
                    String.class,int.class),
                    "GET",monitoringBase+"/{id}/history"),
                new Route(telemetryQuality,telemetryQuality.getDeclaredMethod("assess",
                    String.class,String.class,qualityCommand),
                    "POST",telemetryPointBase+"/readings/{readingId}/assessment"),
                new Route(telemetryQuality,telemetryQuality.getDeclaredMethod("assessment",
                    String.class,String.class,String.class),
                    "GET",telemetryPointBase+"/readings/{readingId}/assessment"),
                new Route(telemetryQuality,telemetryQuality.getDeclaredMethod("publish",
                    String.class,String.class,qualityCommand),
                    "POST",telemetryPointBase+"/readings/{readingId}/trust"),
                new Route(telemetryQuality,telemetryQuality.getDeclaredMethod("exact",
                    String.class,String.class,String.class),
                    "GET",telemetryPointBase+"/trusted-readings/{trustedId}"),
                new Route(telemetryQuality,telemetryQuality.getDeclaredMethod("history",
                    String.class,String.class,int.class),
                    "GET",telemetryPointBase+"/trusted-readings"),
                new Route(telemetryCommissioning,telemetryCommissioning.getDeclaredMethod("enroll",telemetryEnroll),
                    "POST",telemetryBase),
                new Route(telemetryCommissioning,telemetryCommissioning.getDeclaredMethod("rebind",String.class,telemetryRebind),
                    "POST",telemetryBase+"/points/{pointId}/bindings"),
                new Route(telemetryCommissioning,telemetryCommissioning.getDeclaredMethod("effective",String.class,String.class,java.time.Instant.class),
                    "GET",telemetryBase+"/points/{pointId}/effective"),
                new Route(telemetryCommissioning,telemetryCommissioning.getDeclaredMethod("history",String.class,String.class,int.class),
                    "GET",telemetryBase+"/points/{pointId}/history"),
                new Route(discovery,discovery.getDeclaredMethod("discover",String.class,String.class,discoveryKind,String.class,int.class,String.class),"GET",sourceBase),
                new Route(discovery,discovery.getDeclaredMethod("exact",discoveryKind,String.class,String.class,String.class,String.class,String.class),"GET",sourceBase+"/{kind}/{ownerId}/{sourceId}/revisions/{revisionId}"),
                new Route(workflow,workflow.getDeclaredMethod("installedCharacteristicLinkTarget",String.class,String.class,String.class,long.class),"GET","/api/v1/workflow/definitions/installed-characteristic-link-target"),
                new Route(foundation,foundation.getDeclaredMethod("choices",foundationCatalog,String.class,String.class,int.class),"GET",foundationBase+"/choices/{catalog}"),
                new Route(foundation,foundation.getDeclaredMethod("system",foundationSystem),"POST",foundationBase+"/pipeline-systems"),
                new Route(foundation,foundation.getDeclaredMethod("facility",foundationFacility),"POST",foundationBase+"/facilities"),
                new Route(foundation,foundation.getDeclaredMethod("activate",String.class,foundationActivation),"POST",foundationBase+"/facilities/{facilityId}/activation"),
                new Route(foundation,foundation.getDeclaredMethod("position",String.class,foundationPosition),"POST",foundationBase+"/facilities/{facilityId}/equipment-positions"),
                new Route(foundation,foundation.getDeclaredMethod("positions",String.class,String.class,String.class,int.class),"GET",foundationBase+"/facilities/{facilityId}/equipment-positions"),
                new Route(foundation,foundation.getDeclaredMethod("installations",String.class,String.class,String.class,int.class),"GET",foundationBase+"/facilities/{facilityId}/installations"),
                new Route(station,station.getDeclaredMethod("author",String.class,String.class,stationAuthor),"PUT",stationBase+"/revisions/{revisionId}"),
                new Route(station,station.getDeclaredMethod("publish",String.class,stationPublish),"POST",stationBase+"/publications"),
                new Route(station,station.getDeclaredMethod("current",String.class,String.class),"GET",stationBase+"/publications/current"),
                new Route(station,station.getDeclaredMethod("revision",String.class,String.class,String.class),"GET",stationBase+"/revisions/{revisionId}"),
                new Route(station,station.getDeclaredMethod("history",String.class,String.class,int.class),"GET",stationBase+"/publications/history"),
                new Route(pipeline,pipeline.getDeclaredMethod("write",String.class,String.class,String.class,pipelineWrite),"PUT",pipelineBase+"/authored-topology"),
                new Route(pipeline,pipeline.getDeclaredMethod("view",String.class,String.class,String.class,Long.class),"GET",pipelineBase+"/authored-topology"),
                new Route(pipeline,pipeline.getDeclaredMethod("choices",String.class,String.class,String.class,int.class,int.class,String.class),"GET",pipelineBase+"/authoring-choices"),
                new Route(baselinePublication,baselinePublication.getDeclaredMethod("submit",java.util.UUID.class,submitBaseline),"POST",baselineBase+"/{manifestId}/submissions"),
                new Route(baselinePublication,baselinePublication.getDeclaredMethod("publish",java.util.UUID.class,publishBaseline),"POST",baselineBase+"/submissions/{submissionId}/publications"),
                new Route(baselinePublication,baselinePublication.getDeclaredMethod("receipt",java.util.UUID.class,String.class,String.class),"GET",baselineBase+"/publication-receipts/{receiptId}"),
                new Route(baselineReads,baselineReads.getDeclaredMethod("exact",java.util.UUID.class,String.class,String.class),"GET",baselineBase+"/publications/{receiptId}"),
                new Route(baselineReads,baselineReads.getDeclaredMethod("current",String.class,String.class),"GET",baselineBase+"/publications/current"),
                new Route(baselineReads,baselineReads.getDeclaredMethod("effective",String.class,String.class,java.time.Instant.class),"GET",baselineBase+"/publications/effective"),
                new Route(baselineReads,baselineReads.getDeclaredMethod("history",String.class,String.class,java.time.Instant.class,int.class,String.class),"GET",baselineBase+"/publications/history"),
                new Route(baselineReads,baselineReads.getDeclaredMethod("compare",String.class,String.class,java.util.UUID.class,java.util.UUID.class),"GET",baselineBase+"/publications/compare"),
                new Route(baseline,baseline.getDeclaredMethod("create",baselineCreate),"POST",baselineBase),
                new Route(baseline,baseline.getDeclaredMethod("update",java.util.UUID.class,baselineUpdate),"PUT",baselineBase+"/{manifestId}"),
                new Route(baseline,baseline.getDeclaredMethod("current",java.util.UUID.class,String.class,String.class),"GET",baselineBase+"/{manifestId}"),
                new Route(baseline,baseline.getDeclaredMethod("revision",java.util.UUID.class,long.class,String.class,String.class),"GET",baselineBase+"/{manifestId}/revisions/{version}"),
                new Route(baseline,baseline.getDeclaredMethod("validate",java.util.UUID.class,String.class,String.class,Long.class),"POST",baselineBase+"/{manifestId}/validation"),
                new Route(t31,t31.getDeclaredMethod("create",String.class,String.class,t31Create),
                        "POST",t31Base),
                new Route(t31,t31.getDeclaredMethod("append",String.class,String.class,java.util.UUID.class,t31Append),
                        "POST",t31Base+"/{scenarioId}/revisions"),
                new Route(t31,t31.getDeclaredMethod("read",String.class,String.class,java.util.UUID.class,long.class),
                        "GET",t31Base+"/{scenarioId}/revisions/{revision}"),
                new Route(t31,t31.getDeclaredMethod("validate",String.class,String.class,java.util.UUID.class,long.class),
                        "GET",t31Base+"/{scenarioId}/revisions/{revision}/validation"),
                new Route(controller,controller.getDeclaredMethod("save",String.class,String.class,save),
                    "PUT",base+"/revisions/{revisionId}"),
                new Route(controller,controller.getDeclaredMethod("revision",String.class,String.class,String.class),
                    "GET",base+"/revisions/{revisionId}"),
                new Route(controller,controller.getDeclaredMethod("history",String.class,String.class,
                    Integer.class,String.class),"GET",base+"/history"),
                new Route(controller,controller.getDeclaredMethod("certificate",String.class,String.class,
                    String.class),"GET",base+"/revisions/{revisionId}/certificate"),
                new Route(controller,controller.getDeclaredMethod("qualify",String.class,String.class,qualify),
                    "POST",base+"/revisions/{revisionId}/qualification"),
                new Route(installed,installed.getDeclaredMethod("approve",String.class,String.class,long.class,approval),
                    "POST",linkBase+"/revisions/{revisionId}/approval"),
                new Route(installed,installed.getDeclaredMethod("approvedRevision",String.class,String.class,long.class,String.class),
                    "GET",linkBase+"/revisions/{revisionId}/approval"),
                new Route(installed,installed.getDeclaredMethod("approvalHistory",String.class,String.class,
                    String.class,int.class,Long.class),"GET",linkBase+"/approvals/history")
            );
        } catch (ClassNotFoundException | NoSuchMethodException contractDrift) {
            throw new IllegalStateException("Missing approved compiled native route contract",
                    contractDrift);
        }
    }

    List<Route> candidates() {
        return candidates;
    }

    // Only the Platform startup validator may activate successfully reconciled entries.
    void activate(List<Route> accepted) {
        if (!candidates.equals(accepted)) {
            throw new IllegalStateException("Unverified native route activation");
        }
        verified = Set.copyOf(accepted);
    }

    boolean isTrusted(HandlerMethod handler, String verb, String pattern) {
        if (handler == null || verb == null || pattern == null
                || !handler.getMethod().isAnnotationPresent(NativeProtectedOperation.class)) {
            return false;
        }
        return verified.stream().anyMatch(entry ->
                entry.controller().equals(handler.getBeanType())
                        && entry.method().equals(handler.getMethod())
                        && entry.verb().equals(verb)
                        && entry.pattern().equals(pattern));
    }

    /** Exact declared method identity, not a method-name-only or pattern-prefix trust. */
    record Route(Class<?> controller, Method method, String verb, String pattern) {
        Route {
            Objects.requireNonNull(controller, "controller");
            Objects.requireNonNull(method, "method");
            Objects.requireNonNull(verb, "verb");
            Objects.requireNonNull(pattern, "pattern");
            if (!controller.equals(method.getDeclaringClass())
                    || !Set.of("GET", "POST", "PUT", "PATCH", "DELETE").contains(verb)
                    || !pattern.startsWith("/api/v1/") || pattern.contains("*")
                    || pattern.contains("?") || pattern.contains("#")
                    || pattern.equals("/api/v1/security/oidc")
                    || pattern.equals("/api/v1/identity/authentication/login")
                    || pattern.equals("/api/v1/identity/me")
                    || pattern.equals("/api/v1/identity/me/permissions")
                    || pattern.startsWith("/api/v1/realtime/ws")) {
                throw new IllegalArgumentException("Invalid exact trusted native route declaration");
            }
        }
    }
}
