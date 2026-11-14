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
            return List.of(
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
