/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ForensicRemediationClosureTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
 *
 * @Type        : Test
 * @Layer       : Architecture Test
 * @Module      : repository
 * @Package     : dz.sh.hidra
 *
 * @Description : Replays HRA-120 closure-critical checks and rejects REST/domain representation leakage.
 *
 */
package dz.sh.hidra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ForensicRemediationClosureTest {

    private static final Path ROOT = Path.of("src/main/java/dz/sh/hidra");
    private static final Path MODULES = ROOT.resolve("modules");
    private static final Pattern PACKAGE =
            Pattern.compile("(?m)^\\s*package\\s+([\\w.]+)\\s*;");
    private static final Pattern IMPORT =
            Pattern.compile("(?m)^\\s*import\\s+(?:static\\s+)?([\\w.]+)\\s*;");
    private static final Pattern TYPE = Pattern.compile(
            "(?m)^\\s*(?:public\\s+)?(?:abstract\\s+|final\\s+|sealed\\s+|non-sealed\\s+)*"
                    + "(class|record|enum|interface)\\s+(\\w+)"
    );

    private static final Set<String> EXPORTED_PACKAGES = Set.of(
            "dz.sh.hidra.modules.workflow.application.contract.documents",
            "dz.sh.hidra.modules.workflow.application.contract.target",
            "dz.sh.hidra.modules.identity.application.contract.workflow",
            "dz.sh.hidra.modules.organization.application.contract.workflow",
            "dz.sh.hidra.modules.workflow.application.contract.planning",
            "dz.sh.hidra.modules.workflow.application.contract.organization",
            "dz.sh.hidra.modules.workflow.application.contract.alarm",
            "dz.sh.hidra.modules.topology.application.contract.organization",
            "dz.sh.hidra.modules.topology.application.contract.simulation",
            "dz.sh.hidra.modules.topology.application.contract.leakdetection",
            "dz.sh.hidra.modules.topology.application.contract.analytics",
            "dz.sh.hidra.modules.organization.application.contract.analytics",
            "dz.sh.hidra.modules.audit.application.contract.organization",
            "dz.sh.hidra.modules.audit.application.contract.alarm",
            "dz.sh.hidra.modules.party.application.contract.topology",
            "dz.sh.hidra.modules.party.application.contract.planning",
            "dz.sh.hidra.modules.custody.application.contract.planning",
            "dz.sh.hidra.modules.custody.application.contract.simulation",
            "dz.sh.hidra.modules.telemetry.application.contract.monitoring",
            "dz.sh.hidra.modules.telemetry.application.contract.simulation",
            "dz.sh.hidra.modules.telemetry.application.contract.planning",
            "dz.sh.hidra.modules.planning.application.contract.monitoring",
            "dz.sh.hidra.modules.topology.application.contract.assets",
            "dz.sh.hidra.modules.organization.application.contract.assets",
            "dz.sh.hidra.modules.party.application.contract.assets",
            "dz.sh.hidra.modules.organization.application.contract.integration",
            "dz.sh.hidra.modules.identity.application.contract.reporting",
            "dz.sh.hidra.modules.workflow.application.contract.reporting",
            "dz.sh.hidra.modules.organization.application.contract.reporting",
            "dz.sh.hidra.modules.organization.application.contract.risk",
            "dz.sh.hidra.modules.organization.application.contract.integrity",
            "dz.sh.hidra.modules.topology.application.contract.integrity",
            "dz.sh.hidra.modules.organization.application.contract.leakdetection",
            "dz.sh.hidra.modules.organization.application.contract.identity",
            "dz.sh.hidra.modules.topology.application.contract.planning",
            "dz.sh.hidra.modules.identity.application.contract.documents",
            "dz.sh.hidra.modules.identity.application.contract.integration",
            "dz.sh.hidra.modules.documents.application.contract.target",
            "dz.sh.hidra.modules.documents.application.contract.audit",
            "dz.sh.hidra.modules.documents.application.contract.reporting",
            "dz.sh.hidra.modules.workflow.application.contract.audit",
            "dz.sh.hidra.modules.identity.application.contract.planning",
            "dz.sh.hidra.modules.organization.application.contract.planning",
            "dz.sh.hidra.modules.topology.application.contract.risk",
            "dz.sh.hidra.modules.audit.application.contract.risk",
            "dz.sh.hidra.modules.risk.application.contract.evidence",
            "dz.sh.hidra.modules.identity.application.contract.risk",
            "dz.sh.hidra.modules.identity.application.contract.analytics",
            "dz.sh.hidra.modules.monitoring.application.contract.analytics",
            "dz.sh.hidra.modules.workflow.application.contract.risk",
            "dz.sh.hidra.modules.audit.application.contract.simulation",
            "dz.sh.hidra.modules.identity.application.contract.incident",
            "dz.sh.hidra.modules.organization.application.contract.incident",
            "dz.sh.hidra.modules.topology.application.contract.incident",
            "dz.sh.hidra.modules.workflow.application.contract.incident",
            "dz.sh.hidra.modules.workflow.application.contract.integrity",
            "dz.sh.hidra.modules.identity.application.contract.integrity",
            "dz.sh.hidra.modules.audit.application.contract.custody",
            "dz.sh.hidra.modules.workflow.application.contract.custody",
            "dz.sh.hidra.modules.identity.application.contract.custody",
            "dz.sh.hidra.modules.integrity.application.contract.assets",
            "dz.sh.hidra.modules.workflow.application.contract.assets",
            "dz.sh.hidra.modules.identity.application.contract.assets",
            "dz.sh.hidra.modules.identity.application.contract.hse",
            "dz.sh.hidra.modules.organization.application.contract.hse",
            "dz.sh.hidra.modules.workflow.application.contract.hse",
            "dz.sh.hidra.modules.assets.application.contract.hse"
    );

    private static final String T17_HSE_ADAPTER =
            "dz.sh.hidra.modules.hse.infrastructure.integration.HseCapaOwnerAccessAdapter";
    private static final Set<String> T17_OWNER_ROOTS = Set.of(
            "dz.sh.hidra.modules.identity.application.contract.hse.HseCapaScopedAuthorizationContract",
            "dz.sh.hidra.modules.topology.application.contract.hse.HseFacilityOwnershipContract",
            "dz.sh.hidra.modules.incident.application.contract.hse.HseIncidentSourceContract",
            "dz.sh.hidra.modules.documents.application.contract.hse.HseControlledDocumentEvidenceContract",
            "dz.sh.hidra.modules.workflow.application.contract.hse.HseCapaDecisionContract");
    private static boolean isT17OwnerContract(String imported){
        return T17_OWNER_ROOTS.stream().anyMatch(root->
                imported.equals(root)||imported.startsWith(root+"$"));
    }
    private static boolean isT17OwnerExport(String source,String imported){
        return T17_HSE_ADAPTER.equals(source)&&isT17OwnerContract(imported);
    }
    @Test void t17SoleOwnerConsumerAdmitsOnlyRegisteredScalarExports(){
        for(String root:T17_OWNER_ROOTS) {
            assertTrue(isT17OwnerExport(T17_HSE_ADAPTER,root));
            assertTrue(isT17OwnerExport(T17_HSE_ADAPTER,root+"$Evidence"));
            assertFalse(isT17OwnerExport(T17_HSE_ADAPTER+"Sibling",root));
            assertFalse(isT17OwnerExport(T17_HSE_ADAPTER,root+"Sibling"));
            assertFalse(isT17OwnerExport(T17_HSE_ADAPTER,
                    root.replace(".application.contract.hse.",".infrastructure.persistence.")));
        }
    }

    private static final String T26_BRIDGE =
            "dz.sh.hidra.modules.integration.infrastructure.integration.SyntheticExchangeOwnerAccessAdapter";
    private static final Set<String> T26_OWNER_ROOTS=Set.of(
            "dz.sh.hidra.modules.identity.application.contract.integration.IntegrationSyntheticExchangeAuthorizationContract",
            "dz.sh.hidra.modules.topology.application.contract.integration.IntegrationSyntheticFacilityOwnershipContract",
            "dz.sh.hidra.modules.documents.application.contract.integration.IntegrationSyntheticDraftReceiptContract");
    private static boolean t26Export(String consumer,String imported) {
        return consumer.equals(T26_BRIDGE) && T26_OWNER_ROOTS.stream()
                .anyMatch(root->imported.equals(root)||imported.startsWith(root+"$"));
    }
    @Test void t26OnlyTheRegisteredConsumerMayImportExactScalarContracts() {
        for (String root:T26_OWNER_ROOTS) {
            assertTrue(t26Export(T26_BRIDGE,root));
            assertTrue(t26Export(T26_BRIDGE,root+"$Facility"));
            assertFalse(t26Export(T26_BRIDGE+"Sibling",root));
            assertFalse(t26Export(T26_BRIDGE,root+"Sibling"));
        }
    }


    // T31 contracts: no generic cross-module scalar export and no adapter sibling.
    private static final String T31_ADAPTER =
            "dz.sh.hidra.modules.simulation.infrastructure.integration.ScenarioInputOwnerAccessAdapter";
    private static final Set<String> T31_OWNER_CONTRACTS=Set.of(
            "dz.sh.hidra.modules.identity.application.contract.simulation.ScenarioInputAuthorizationContract",
            "dz.sh.hidra.modules.topology.application.contract.simulation.ScenarioBaselineReceiptContract");
    private static boolean t31OwnerRoot(String type) {
        return T31_OWNER_CONTRACTS.stream().anyMatch(type::startsWith);
    }
    private static boolean t31OwnerExport(String consumer,String type) {
        return T31_ADAPTER.equals(consumer) && T31_OWNER_CONTRACTS.stream()
            .anyMatch(root->type.equals(root)||type.startsWith(root+"$"));
    }
    @Test void t31OwnerExportsAreExactAndSolverFree() {
        for(String root:T31_OWNER_CONTRACTS) {
            assertTrue(t31OwnerExport(T31_ADAPTER,root));
            assertTrue(t31OwnerExport(T31_ADAPTER,root+"$Exact"));
            assertFalse(t31OwnerExport(T31_ADAPTER+"Sibling",root));
            assertFalse(t31OwnerExport(T31_ADAPTER,root+"Sibling"));
            assertTrue(t31OwnerRoot(root+"Sibling"));
        }
        assertFalse(t31OwnerExport(T31_ADAPTER,
                "dz.sh.hidra.modules.simulation.application.port.out.SimulationSolverPort"));
    }

    // T28 alone admits these two exact, owner-owned scalar contracts.
    private static final String T28_CONSUMER =
            "dz.sh.hidra.modules.audit.infrastructure.integration.ScopedAuditEvidenceAccessAdapter";
    private static final Set<String> T28_OWNER_EXPORTS=Set.of(
            "dz.sh.hidra.modules.identity.application.contract.audit.ScopedAuditEvidenceAuthorizationContract",
            "dz.sh.hidra.modules.topology.application.contract.audit.AuditFacilityOwnershipContract");
    private static boolean t28Export(String consumer,String imported) {
        return consumer.equals(T28_CONSUMER) &&
                T28_OWNER_EXPORTS.stream().anyMatch(root->
                    imported.equals(root)||imported.startsWith(root+"$"));
    }
    private static boolean t28OwnerRoot(String imported) {
        return T28_OWNER_EXPORTS.stream().anyMatch(imported::startsWith);
    }
    @Test void t28OnlyTheAuditBridgeImportsExactOwnerContracts() {
        for(String root:T28_OWNER_EXPORTS) {
            assertTrue(t28Export(T28_CONSUMER,root));
            assertTrue(t28Export(T28_CONSUMER,root+"$Facility"));
            assertFalse(t28Export(T28_CONSUMER+"Sibling",root));
            assertFalse(t28Export(T28_CONSUMER,root+"Sibling"));
            assertTrue(t28OwnerRoot(root+"Sibling"));
        }
    }

    // T27 has three exact scalar owner exports and no package-wide import exemption.
    private static final String T27_REPORT_CONSUMER =
            "dz.sh.hidra.modules.reporting.infrastructure.integration.ScopedReportWorkflowTargetLookup";
    private static final String T27_ACCESS_CONSUMER =
            "dz.sh.hidra.modules.notification.infrastructure.integration.WorkflowInboxAccessAdapter";
    private static final String T27_REPORT_EXPORT =
            "dz.sh.hidra.modules.notification.application.contract.reporting.ReportReviewTaskInboxContract";
    private static final Set<String> T27_ACCESS_EXPORTS=Set.of(
            "dz.sh.hidra.modules.identity.application.contract.notification.NotificationInboxAuthorizationContract",
            "dz.sh.hidra.modules.topology.application.contract.notification.NotificationInboxFacilityOwnershipContract");
    private static boolean t27Export(String consumer,String imported) {
        if (consumer.equals(T27_REPORT_CONSUMER)
                && (imported.equals(T27_REPORT_EXPORT) || imported.startsWith(T27_REPORT_EXPORT+"$")))
            return true;
        return consumer.equals(T27_ACCESS_CONSUMER)
                && T27_ACCESS_EXPORTS.stream()
                    .anyMatch(root->imported.equals(root)||imported.startsWith(root+"$"));
    }
    private static boolean t27OwnerRoot(String imported) {
        return imported.startsWith(T27_REPORT_EXPORT)
                || T27_ACCESS_EXPORTS.stream().anyMatch(imported::startsWith);
    }
    @Test void t27OnlyExactNamedConsumersMayUseExactOwnerContracts() {
        assertTrue(t27Export(T27_REPORT_CONSUMER,T27_REPORT_EXPORT));
        assertTrue(t27Export(T27_REPORT_CONSUMER,T27_REPORT_EXPORT+"$Assignment"));
        assertFalse(t27Export(T27_REPORT_CONSUMER+"Sibling",T27_REPORT_EXPORT));
        assertFalse(t27Export(T27_REPORT_CONSUMER,T27_REPORT_EXPORT+"Sibling"));
        for (String root:T27_ACCESS_EXPORTS) {
            assertTrue(t27Export(T27_ACCESS_CONSUMER,root));
            assertFalse(t27Export(T27_ACCESS_CONSUMER+"Sibling",root));
            assertFalse(t27Export(T27_ACCESS_CONSUMER,root+"Sibling"));
            assertTrue(t27OwnerRoot(root+"Sibling"));
        }
    }

    private static final String T25_BRIDGE = "dz.sh.hidra.modules.reporting.infrastructure.integration.ScopedReportOwnerAccessAdapter";
    private static final String T25_TARGET_RESOLVER = "dz.sh.hidra.modules.reporting.infrastructure.integration.ScopedReportWorkflowTargetLookup";
    private static final String T25_TARGET_CONTRACT = "dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup";
    private static final String T25_DOCS_ADAPTER = "dz.sh.hidra.modules.documents.infrastructure.integration.ControlledDocumentOwnerAccessAdapter";
    private static final String T25_IDENTITY_CONTRACT = "dz.sh.hidra.modules.identity.application.contract.reporting.ScopedReportAuthorizationContract";
    private static final Set<String> T25_OWNER_ROOTS = Set.of(
            "dz.sh.hidra.modules.analytics.application.contract.reporting.ReportingDeviationMetricContract",
            "dz.sh.hidra.modules.documents.application.contract.reporting.ScopedReportDocumentContract",
            "dz.sh.hidra.modules.identity.application.contract.reporting.ScopedReportAuthorizationContract",
            "dz.sh.hidra.modules.topology.application.contract.reporting.ReportingFacilityOwnershipContract",
            "dz.sh.hidra.modules.workflow.application.contract.reporting.ScopedReportApprovalContract");
    private static boolean t25Export(String source,String target) {
        if (T25_TARGET_RESOLVER.equals(source))
            return target.equals(T25_TARGET_CONTRACT)||target.startsWith(T25_TARGET_CONTRACT+".");
        return T25_OWNER_ROOTS.stream().filter(root->target.equals(root)||target.startsWith(root+"."))
                .anyMatch(root->T25_BRIDGE.equals(source)
                        || T25_DOCS_ADAPTER.equals(source)&&T25_IDENTITY_CONTRACT.equals(root));
    }
    @Test void t25OwnerExportsAreExact() {
        for (String owner:T25_OWNER_ROOTS) {
            assertTrue(t25Export(T25_BRIDGE,owner));
            assertFalse(t25Export(T25_BRIDGE+"Sibling",owner));
            assertFalse(t25Export(T25_BRIDGE,owner+"Sibling"));
        }
        assertTrue(t25Export(T25_TARGET_RESOLVER,T25_TARGET_CONTRACT));
        assertFalse(t25Export(T25_TARGET_RESOLVER+"Sibling",T25_TARGET_CONTRACT));
        assertTrue(t25Export(T25_DOCS_ADAPTER,T25_IDENTITY_CONTRACT));
    }

    private static boolean t24DocumentOwnerExport(String source,String target) {
        final String owner="dz.sh.hidra.modules.documents.infrastructure.integration.ControlledDocumentOwnerAccessAdapter";
        return source.equals(owner) && java.util.Set.of(
                "dz.sh.hidra.modules.identity.application.contract.documents.ControlledDocumentScopedAuthorizationContract",
                "dz.sh.hidra.modules.topology.application.contract.documents.ControlledDocumentFacilityOwnershipContract")
                .stream().anyMatch(contract->target.equals(contract)||target.startsWith(contract+"$"));
    }
    @Test void t24DocumentsOwnerExportsRemainExactAndPrivate() {
        String adapter="dz.sh.hidra.modules.documents.infrastructure.integration.ControlledDocumentOwnerAccessAdapter";
        for(String root:java.util.Set.of(
                "dz.sh.hidra.modules.identity.application.contract.documents.ControlledDocumentScopedAuthorizationContract",
                "dz.sh.hidra.modules.topology.application.contract.documents.ControlledDocumentFacilityOwnershipContract")) {
            org.junit.jupiter.api.Assertions.assertTrue(t24DocumentOwnerExport(adapter,root));
            org.junit.jupiter.api.Assertions.assertTrue(t24DocumentOwnerExport(adapter,root+"$Facility"));
            org.junit.jupiter.api.Assertions.assertFalse(t24DocumentOwnerExport(adapter+"Sibling",root));
            org.junit.jupiter.api.Assertions.assertFalse(t24DocumentOwnerExport(adapter,root+"Sibling"));
        }
    }

    private static boolean t23AnalyticsOwnerExport(String consumer,String imported) {
        final String approved="dz.sh.hidra.modules.analytics.infrastructure.integration.DeviationMetricOwnerAccessAdapter";
        return approved.equals(consumer) && java.util.Set.of(
                "dz.sh.hidra.modules.monitoring.application.contract.analytics.AnalyticsDeviationSourceContract",
                "dz.sh.hidra.modules.identity.application.contract.analytics.AnalyticsMetricScopedAuthorizationContract")
                .stream().anyMatch(root->imported.equals(root)||imported.startsWith(root+"$"));
    }
    @Test void t23SourceImportsAreExclusive() {
        String consumer="dz.sh.hidra.modules.analytics.infrastructure.integration.DeviationMetricOwnerAccessAdapter";
        String source="dz.sh.hidra.modules.monitoring.application.contract.analytics.AnalyticsDeviationSourceContract";
        assertTrue(t23AnalyticsOwnerExport(consumer,source));
        assertTrue(t23AnalyticsOwnerExport(consumer,source+"$Evaluation"));
        assertFalse(t23AnalyticsOwnerExport(consumer+"Sibling",source));
        assertFalse(t23AnalyticsOwnerExport(consumer,source+"Sibling"));
    }

    /** Only the T22 consumers named in the registered boundary may call these new owner exports. */
    private static boolean t22RiskOwnerExport(String consumer, String imported) {
        if (imported.equals("dz.sh.hidra.modules.risk.application.contract.workflow.RiskAssessmentWorkflowAuthorityContract")) {
            return consumer.equals("dz.sh.hidra.modules.workflow.application.service.RiskAssessmentApprovalService");
        }
        if (imported.equals("dz.sh.hidra.modules.alarm.application.contract.risk.RiskAlarmEvidenceContract")) {
            return Set.of(
                    "dz.sh.hidra.modules.risk.application.service.RiskApplicationService",
                    "dz.sh.hidra.modules.risk.infrastructure.persistence.adapter.JpaRiskAssessmentRepositoryAdapter")
                    .contains(consumer);
        }
        return false;
    }

    private static boolean isPlanningReviewerExport(String consumer, String imported) {
        return consumer.equals("dz.sh.hidra.modules.workflow.application.service.WorkflowTransitionApplicationService")
                && imported.equals("dz.sh.hidra.modules.planning.application.contract.workflow.PlanningRevisionWorkflowAuthorityContract");
    }

    private static final Set<String> EXPECTED_WIRE_BLOCKER = Set.of();

    @Test
    void fictionalModuleEventScaffoldingRemainsAbsent() throws IOException {
        List<String> violations = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(MODULES)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        String normalized = path.toString().replace('\\', '/');
                        String name = path.getFileName().toString();
                        if (normalized.contains("/domain/event/")) {
                            violations.add(normalized);
                        }
                        if (name.contains("DomainEventPublisher")
                                || name.matches(".*EventPublisher.*\\.java")) {
                            violations.add(normalized);
                        }
                    });
        }
        assertTrue(violations.isEmpty(), () -> "Fictional event scaffolding returned: " + violations);
    }


    /** T34 exact Topology consumer of Identity's current regional resource decision. */
    @Test
    void t36ImmutablePressureReadDoesNotDependOnForeignPersistence() throws Exception {
        String file = java.nio.file.Files.readString(java.nio.file.Path.of(
                "src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/"
                + "JdbcQualifiedOperatingConstraintReadAdapter.java"));
        org.junit.jupiter.api.Assertions.assertFalse(file.contains("hidra_workflow_")
                || file.contains("hidra_audit_") || file.contains("hidra_integrity_"),
                "T36 JDBC adapter must access only Topology-owned projection and canonical target");
        org.junit.jupiter.api.Assertions.assertFalse(file.contains("INSERT INTO")
                || file.contains("UPDATE public.") || file.contains("DELETE FROM"),
                "T36 does not provide an application writer");
    }

    private static boolean t34RegionalIdentityOwnerExport(String source, String imported) {
        String root = "dz.sh.hidra.modules.identity.application.contract.topology.TopologyRegionalAccessContract";
        if (!"dz.sh.hidra.modules.topology.application.service.RegionalTopologyMapApplicationService".equals(source)
                && !"dz.sh.hidra.modules.topology.application.service.PipelineDossierReadApplicationService".equals(source)
                && !"dz.sh.hidra.modules.telemetry.infrastructure.security.TelemetryReadingNativeScopeAdapter".equals(source))
            return false;
        return Set.of(root, root + ".Request", root + ".ResourceKind",
                root + "$Request", root + "$ResourceKind").contains(imported);
    }

    @Test void t34RegionalIdentityOwnerExportRejectsSiblingsAndPrivateTypes() {
        String owner = "dz.sh.hidra.modules.topology.application.service.RegionalTopologyMapApplicationService";
        String root = "dz.sh.hidra.modules.identity.application.contract.topology.TopologyRegionalAccessContract";
        assertTrue(t34RegionalIdentityOwnerExport(owner, root));
        assertTrue(t34RegionalIdentityOwnerExport(owner, root + ".Request"));
        assertTrue(t34RegionalIdentityOwnerExport(owner, root + "$ResourceKind"));
        String dossier = "dz.sh.hidra.modules.topology.application.service.PipelineDossierReadApplicationService";
        assertTrue(t34RegionalIdentityOwnerExport(dossier, root));
        assertTrue(t34RegionalIdentityOwnerExport(dossier, root + ".Request"));
        assertTrue(t34RegionalIdentityOwnerExport(dossier, root + "$ResourceKind"));
        String telemetry = "dz.sh.hidra.modules.telemetry.infrastructure.security.TelemetryReadingNativeScopeAdapter";
        assertTrue(t34RegionalIdentityOwnerExport(telemetry, root));
        assertTrue(t34RegionalIdentityOwnerExport(telemetry, root + ".Request"));
        assertTrue(t34RegionalIdentityOwnerExport(telemetry, root + "$ResourceKind"));
        assertFalse(t34RegionalIdentityOwnerExport(telemetry + "Sibling", root));
        assertFalse(t34RegionalIdentityOwnerExport(telemetry, root + ".Other"));
        assertFalse(t34RegionalIdentityOwnerExport(
                "dz.sh.hidra.modules.telemetry.application.service.AssetScopedTrustedObservationReadApplicationService", root));
        assertFalse(t34RegionalIdentityOwnerExport(dossier + "Sibling", root));
        assertFalse(t34RegionalIdentityOwnerExport(dossier, root + "$Unregistered"));
        assertFalse(t34RegionalIdentityOwnerExport("dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JpaPipelineDossierReadAdapter",root));
        assertFalse(t34RegionalIdentityOwnerExport(owner + "Sibling", root));
        assertFalse(t34RegionalIdentityOwnerExport(owner, root + ".Other"));
        assertFalse(t34RegionalIdentityOwnerExport(owner, root + "Sibling"));
        assertFalse(t34RegionalIdentityOwnerExport(owner,
                "dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant"));
    }

    /** T33: only the registered Identity evaluator may see the exact Topology scalar lineage export. */
    private static boolean t33RegionalTopologyOwnerExport(String source, String imported) {
        String owner = "dz.sh.hidra.modules.topology.application.contract.identity.IdentityRegionalTopologyContract";
        if (!"dz.sh.hidra.modules.identity.application.service.RegionalAccessApplicationService".equals(source)) return false;
        return Set.of(owner, owner + ".Kind", owner + ".Scope", owner + ".Resolution",
                owner + "$Kind", owner + "$Scope", owner + "$Resolution").contains(imported);
    }

    @Test void t33RegionalTopologyOwnerExportIsExact() {
        String consumer = "dz.sh.hidra.modules.identity.application.service.RegionalAccessApplicationService";
        String root = "dz.sh.hidra.modules.topology.application.contract.identity.IdentityRegionalTopologyContract";
        assertTrue(t33RegionalTopologyOwnerExport(consumer, root));
        assertTrue(t33RegionalTopologyOwnerExport(consumer, root + ".Scope"));
        assertTrue(t33RegionalTopologyOwnerExport(consumer, root + "$Resolution"));
        assertFalse(t33RegionalTopologyOwnerExport(consumer + "Sibling", root));
        assertFalse(t33RegionalTopologyOwnerExport(consumer, root + "$Unregistered"));
        assertFalse(t33RegionalTopologyOwnerExport(consumer,
                "dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JpaRegionalTopologyRelationshipAdapter"));
    }

    @Test
    void crossModulePrivateImportsRemainClosed() throws IOException {
        String reviewConsumer = "dz.sh.hidra.modules.workflow.application.service.WorkflowTransitionApplicationService";
        String reviewContract = "dz.sh.hidra.modules.planning.application.contract.workflow.PlanningRevisionWorkflowAuthorityContract";
        assertTrue(isPlanningReviewerExport(reviewConsumer, reviewContract));
        assertFalse(isPlanningReviewerExport(reviewConsumer + "Sibling", reviewContract));
        assertFalse(isPlanningReviewerExport(reviewConsumer, reviewContract + "Sibling"));
        assertFalse(isPlanningReviewerExport(reviewConsumer,
                "dz.sh.hidra.modules.planning.infrastructure.persistence.adapter.JpaPlanRevisionRepositoryAdapter"));
        String t12Consumer = "dz.sh.hidra.modules.alarm.infrastructure.integration.MonitoringEvaluationEvidenceAdapter";
        String t12Root = "dz.sh.hidra.modules.monitoring.application.contract.alarm.AlarmMonitoringEvaluationContract";
        assertTrue(isAlarmMonitoringEvidenceExport(t12Consumer, t12Root));
        assertTrue(isAlarmMonitoringEvidenceExport(t12Consumer, t12Root + "$Evaluation"));
        assertFalse(isAlarmMonitoringEvidenceExport(t12Consumer + "Sibling", t12Root));
        assertFalse(isAlarmMonitoringEvidenceExport(t12Consumer, t12Root + "Sibling"));
        String alarmConsumer = "dz.sh.hidra.modules.alarm.infrastructure.security.AlarmResourceAuthorizationAdapter";
        String alarmIdentity = "dz.sh.hidra.modules.identity.application.contract.alarm.AlarmScopedAuthorizationContract";
        String alarmTopology = "dz.sh.hidra.modules.topology.application.contract.alarm.AlarmTopologyOwnershipContract";
        assertTrue(isAlarmOwnerExport(alarmConsumer, alarmIdentity));
        assertTrue(isAlarmOwnerExport(alarmConsumer, alarmTopology));
        assertTrue(isAlarmOwnerExport(alarmConsumer, alarmTopology + ".OwnedAsset"));
        assertFalse(isAlarmOwnerExport(alarmConsumer + "Sibling", alarmTopology));
        assertFalse(isAlarmOwnerExport(alarmConsumer, alarmIdentity + "Sibling"));
        assertFalse(isAlarmOwnerExport(alarmConsumer,
                "dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant"));
        String telemetryConsumer="dz.sh.hidra.modules.telemetry.infrastructure.security.TelemetryReadingNativeScopeAdapter";
        String telemetryIdentity="dz.sh.hidra.modules.identity.application.contract.telemetry.TelemetryScopedAuthorizationContract";
        String telemetryTopology="dz.sh.hidra.modules.topology.application.contract.telemetry.TelemetryTopologyOwnershipContract";
        assertTrue(isTelemetryNativeOwnerExport(telemetryConsumer,telemetryIdentity));
        assertTrue(isTelemetryNativeOwnerExport(telemetryConsumer,telemetryIdentity+".Operation"));
        assertTrue(isTelemetryNativeOwnerExport(telemetryConsumer,telemetryTopology));
        assertTrue(isTelemetryNativeOwnerExport(telemetryConsumer,telemetryTopology+".OwnedAsset"));
        assertFalse(isTelemetryNativeOwnerExport(telemetryConsumer+"Sibling",telemetryTopology));
        assertFalse(isTelemetryNativeOwnerExport(telemetryConsumer,telemetryTopology+"Sibling"));
        assertFalse(isTelemetryNativeOwnerExport(telemetryConsumer,
                "dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity"));
        String monitoringConsumer="dz.sh.hidra.modules.monitoring.infrastructure.security.MonitoringRuleNativeScopeAdapter";
        String monitoringIdentity="dz.sh.hidra.modules.identity.application.contract.monitoring.MonitoringRuleScopedAuthorizationContract";
        String monitoringTopology="dz.sh.hidra.modules.topology.application.contract.monitoring.MonitoringRuleAssetOwnershipContract";
        assertTrue(isMonitoringRuleOwnerExport(monitoringConsumer,monitoringIdentity));
        assertTrue(isMonitoringRuleOwnerExport(monitoringConsumer,monitoringIdentity+".Operation"));
        assertTrue(isMonitoringRuleOwnerExport(monitoringConsumer,monitoringTopology+".OwnedAsset"));
        assertFalse(isMonitoringRuleOwnerExport(monitoringConsumer+"Sibling",monitoringIdentity));
        assertFalse(isMonitoringRuleOwnerExport(monitoringConsumer,monitoringIdentity+"Sibling"));
        assertFalse(isMonitoringRuleOwnerExport(monitoringConsumer,
                "dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JpaTelemetryExplicitGrantEvidenceAdapter"));
        String productConsumer="dz.sh.hidra.modules.custody.application.service.ProtectedProductDraftAdmissionCoordinator";
        String productRoot="dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract";
        assertTrue(custodyProductOwnerExport(productConsumer,productRoot));
        assertTrue(custodyProductOwnerExport(productConsumer,productRoot+".Admission"));
        assertFalse(custodyProductOwnerExport(productConsumer,productRoot+"Sibling"));
        assertFalse(custodyProductOwnerExport("dz.sh.hidra.modules.custody.application.service.Other",productRoot));
        assertFalse(custodyProductOwnerExport(productConsumer,"dz.sh.hidra.modules.party.infrastructure.persistence.adapter.JdbcCustodyProtectedPartyEligibilityAdapter"));
        String installedConsumer="dz.sh.hidra.modules.topology.application.service.ProtectedInstalledUnitAdmissionCoordinator";
        String installedRoot="dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract";
        assertTrue(installedUnitOwnerExport(installedConsumer,installedRoot));
        assertTrue(installedUnitOwnerExport(installedConsumer,installedRoot+".Admission"));
        assertFalse(installedUnitOwnerExport(installedConsumer,installedRoot+"Sibling"));
        assertFalse(installedUnitOwnerExport(installedConsumer+"Sibling",installedRoot));
        assertFalse(installedUnitOwnerExport(installedConsumer,"dz.sh.hidra.modules.identity.infrastructure.security.ProtectedTopologyInstalledUnitAuthorizationParticipant"));
        // HAPI-600 B3-S2 admits only the already-reviewed Identity export
        // to its exact Topology coordinator, never a sibling or concrete adapter.
        String configConsumer="dz.sh.hidra.modules.topology.application.service.ProtectedStationConfigurationAdmissionCoordinator";
        String configRoot="dz.sh.hidra.modules.identity.application.contract.topology.TopologyStationConfigurationAuthorizationContract";
        assertTrue(stationConfigurationOwnerExport(configConsumer,configRoot));
        assertTrue(stationConfigurationOwnerExport(configConsumer,configRoot+".Admission"));
        assertFalse(stationConfigurationOwnerExport(configConsumer+"Sibling",configRoot));
        assertFalse(stationConfigurationOwnerExport(configConsumer,configRoot+"Sibling"));
        assertFalse(stationConfigurationOwnerExport(configConsumer,
            "dz.sh.hidra.modules.identity.infrastructure.security.ProtectedTopologyStationConfigurationAuthorizationParticipant"));
        String manufacturerConsumer = "dz.sh.hidra.modules.simulation.application.service.ProtectedManufacturerCharacteristicAdmissionCoordinator";
        for (String root : Set.of(
                "dz.sh.hidra.modules.identity.application.contract.simulation.SimulationManufacturerCharacteristicAuthorizationContract",
                "dz.sh.hidra.modules.party.application.contract.simulation.SimulationManufacturerEvidenceContract")) {
            assertTrue(manufacturerCharacteristicOwnerExport(manufacturerConsumer, root));
            assertFalse(manufacturerCharacteristicOwnerExport(manufacturerConsumer + "Sibling", root));
            assertFalse(manufacturerCharacteristicOwnerExport(manufacturerConsumer, root + ".Sibling"));
        }
        assertFalse(manufacturerCharacteristicOwnerExport(manufacturerConsumer,
                "dz.sh.hidra.modules.identity.infrastructure.security.ProtectedSimulationManufacturerCharacteristicAuthorizationParticipant"));
        // HAPI-605: only exact Topology callers may see these three public owner contracts.
        String baselineCoordinator="dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringBaselineAdmissionCoordinator";
        String baselineReader="dz.sh.hidra.modules.topology.infrastructure.integration.EngineeringBaselineSourceAdapter";
        String baselineIdentity="dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract";
        String baselineCustody="dz.sh.hidra.modules.custody.application.contract.topology.TopologyBaselineProductSourceContract";
        String baselineManufacturer="dz.sh.hidra.modules.simulation.application.contract.topology.TopologyManufacturerCharacteristicReferenceContract";
        assertTrue(baselineOwnerExport(baselineCoordinator,baselineIdentity));
        assertTrue(baselineOwnerExport(baselineReader,baselineCustody));
        assertTrue(baselineOwnerExport(baselineReader,baselineManufacturer));
        assertFalse(baselineOwnerExport(baselineCoordinator+"Sibling",baselineIdentity));
        assertFalse(baselineOwnerExport(baselineReader,baselineManufacturer+"Sibling"));
        assertFalse(baselineOwnerExport(baselineReader,"dz.sh.hidra.modules.custody.infrastructure.integration.TopologyBaselineProductSourceAdapter"));
        String t20Consumer="dz.sh.hidra.modules.custody.application.service.CustodyMeasurementCaptureApplicationService";
        for (String t20Contract : Set.of(
                "dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyIntervalEvidenceContract",
                "dz.sh.hidra.modules.topology.application.contract.custody.CustodyMeasurementAssetOwnershipContract",
                "dz.sh.hidra.modules.party.application.contract.custody.CustodyMeasurementPartyReferenceContract",
                "dz.sh.hidra.modules.identity.application.contract.custody.CustodyMeasurementScopedAuthorizationContract")) {
            assertTrue(t20MeasurementOwnerExport(t20Consumer,t20Contract));
            assertFalse(t20MeasurementOwnerExport(t20Consumer+"Sibling",t20Contract));
            assertFalse(t20MeasurementOwnerExport(t20Consumer,t20Contract+"Sibling"));
            assertFalse(t20MeasurementOwnerExport(t20Consumer,
                    "dz.sh.hidra.modules.party.infrastructure.persistence.adapter.JpaCustodyMeasurementPartyReferenceAdapter"));
        }
        String t21Owner="dz.sh.hidra.modules.topology.application.contract.custody.CustodyMeasurementAssetOwnershipContract";
        for(String exactConsumer:Set.of(
            "dz.sh.hidra.modules.custody.application.service.CustodyTransferLifecycleApplicationService",
            "dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.JdbcCustodyTransferLifecycleAdapter")){
            assertTrue(t21CustodyOwnershipExport(exactConsumer,t21Owner));
            assertFalse(t21CustodyOwnershipExport(exactConsumer+"Sibling",t21Owner));
            assertFalse(t21CustodyOwnershipExport(exactConsumer,t21Owner+"Sibling"));
            assertFalse(t21CustodyOwnershipExport(exactConsumer,
                "dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcCustodyMeasurementAssetOwnershipAdapter"));
        }
        assertFalse(t21CustodyOwnershipExport(
            "dz.sh.hidra.modules.planning.application.service.Other",t21Owner));
        List<String> violations = new ArrayList<>();
        for (SourceType source : sources().values()) {
            String sourceModule = module(source.packageName());
            if (sourceModule == null) {
                continue;
            }
            Matcher imports = IMPORT.matcher(source.source());
            while (imports.find()) {
                String imported = imports.group(1);
                String importedPackage = packageName(imported);
                String targetModule = module(importedPackage);
                if (targetModule == null || sourceModule.equals(targetModule)) {
                    continue;
                }
                if (t31OwnerRoot(imported)) {
                    if (!t31OwnerExport(source.fqcn(),imported))
                        violations.add(source.fqcn()+" -> unauthorized T31 owner "+imported);
                    continue;
                }
                if (t28OwnerRoot(imported)) {
                    if (!t28Export(source.fqcn(),imported))
                        violations.add(source.fqcn()+" -> unauthorized T28 owner "+imported);
                    continue;
                }
                if (t27OwnerRoot(imported)) {
                    if (!t27Export(source.fqcn(),imported))
                        violations.add(source.fqcn()+" -> unauthorized T27 owner "+imported);
                    continue;
                }
                if (T26_OWNER_ROOTS.stream().anyMatch(imported::startsWith)) {
                    if (!t26Export(source.fqcn(),imported))
                        violations.add(source.fqcn()+" -> unauthorized T26 owner "+imported);
                    continue;
                }
                if (T25_OWNER_ROOTS.stream().anyMatch(imported::startsWith)) {
                    if (!t25Export(source.fqcn(),imported))
                        violations.add(source.fqcn()+" -> unauthorized T25 owner "+imported);
                    continue;
                }
                if (t26Export(source.fqcn(),imported)) continue;
                if (t25Export(source.fqcn(),imported)) continue;
                if (!isPrivate(importedPackage)) {
                    continue;
                }
                if(isT17OwnerContract(imported)) {
                    if(!isT17OwnerExport(source.fqcn(),imported))
                        violations.add(source.fqcn()+" -> unauthorized T17 owner "+imported);
                    continue;
                }
                if (isT14ProtectedOwnerType(imported) && !isT14OwnerExport(source.fqcn(),imported)) {
                    violations.add(source.fqcn()+" -> "+imported);
                    continue;
                }
                // The existing Identity documents export package remains public for historical contracts.
                // The new T24 scalar is an exact-consumer exception, not package-wide authority.
                if (imported.startsWith("dz.sh.hidra.modules.identity.application.contract.documents.ControlledDocumentScopedAuthorizationContract")
                    || imported.startsWith("dz.sh.hidra.modules.topology.application.contract.documents.ControlledDocumentFacilityOwnershipContract")) {
                    if (!t24DocumentOwnerExport(source.fqcn(),imported))
                        violations.add(source.fqcn()+" -> unauthorized T24 owner "+imported);
                    continue;
                }
                if (imported.startsWith(
                        "dz.sh.hidra.modules.identity.application.contract.topology.TopologyRegionalAccessContract")) {
                    if (!t34RegionalIdentityOwnerExport(source.fqcn(), imported))
                        violations.add(source.fqcn() + " -> forbidden T34 export " + imported);
                    continue;
                }
                if (EXPORTED_PACKAGES.stream().anyMatch(imported::startsWith)) {
                    continue;
                }
                // HAPI-583: only the registered coordinator's three exact Identity exports.
                if (source.fqcn().equals("dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator")
                        && Set.of(
                                "dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedAuthorizationContract",
                                "dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Request",
                                "dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Operation")
                                .contains(imported)) {
                    continue;
                }
                // HAPI-584 permits only these two exact public Identity operation exports.
                if (source.fqcn().equals("dz.sh.hidra.modules.topology.application.service.ProtectedStationGraphOperationService")
                        && Set.of(
                                "dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Operation",
                                "dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Request")
                                .contains(imported)) {
                    continue;
                }
                // HAPI-587: one compiled coordinator consumes the distinct public company/network contract.
                // This is not a package-wide exemption and admits no foreign persistence type.
                if ((source.fqcn().equals("dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringDraftAdmissionCoordinator")
                        || source.fqcn().equals("dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringPublicationAdmissionCoordinator"))
                        && imported.equals("dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract")) {
                    continue;
                }
                if (manufacturerCharacteristicOwnerExport(source.fqcn(), imported)) continue;
                if (stationConfigurationOwnerExport(source.fqcn(), imported)) continue;
                if (characteristicLinkOwnerExport(source.fqcn(), imported)) continue;
                if (installedUnitOwnerExport(source.fqcn(), imported)) continue;
                if (organizationOwnerExport(source.fqcn(), imported)) continue;
                if (custodyProductOwnerExport(source.fqcn(), imported)) continue;
                if (t20MeasurementOwnerExport(source.fqcn(), imported)) continue;
                if (t21CustodyOwnershipExport(source.fqcn(), imported)) continue;
                if (baselineOwnerExport(source.fqcn(), imported)) continue;
                if (isAlarmOwnerExport(source.fqcn(), imported)) continue;
                if (isAlarmMonitoringEvidenceExport(source.fqcn(), imported)) continue;
                if (isLeakT13OwnerExport(source.fqcn(), imported)) continue;
                if (isT14OwnerExport(source.fqcn(), imported)) continue;
                if (isTelemetryNativeOwnerExport(source.fqcn(), imported)) continue;
                if (isMonitoringRuleOwnerExport(source.fqcn(), imported)) continue;
                if (isPlanningReviewerExport(source.fqcn(), imported)) continue;
                if (t22RiskOwnerExport(source.fqcn(), imported)) continue;
                if (t24DocumentOwnerExport(source.fqcn(), imported)) continue;
                if (isT17OwnerExport(source.fqcn(), imported)) continue;
                if (t23AnalyticsOwnerExport(source.fqcn(), imported)) continue;
                if (t33RegionalTopologyOwnerExport(source.fqcn(), imported)) continue;
                violations.add(source.fqcn() + " -> " + imported);
            }
        }
        assertTrue(violations.isEmpty(), () -> "Unapproved cross-module imports: " + violations);
    }

    /** HAPI-605: exact imported types and consumers; no cross-module package-wide bypass. */
    /**
     * HAPI-930: only Alarm's reviewed infrastructure coordinator can import exactly the
     * two owner-exported Alarm contracts and their nested scalar projection types.
     * No other consumer, sibling type or owner infrastructure is made public.
     */

    /** Only the exact T14 adapter may consume the named scalar owner projection. */
    @Test void t14IncidentExportsExcludeSiblings() {
        String consumer="dz.sh.hidra.modules.incident.infrastructure.integration.IncidentEscalationSourceAdapter";
        String owner="dz.sh.hidra.modules.leakdetection.application.contract.incident.IncidentLeakCandidateEvidenceContract";
        assertTrue(isT14OwnerExport(consumer,owner));
        assertTrue(isT14OwnerExport(consumer,owner+"$Evidence"));
        assertFalse(isT14OwnerExport(consumer+"Sibling",owner));
        assertFalse(isT14OwnerExport(consumer,owner+"Sibling"));
    }

    private static boolean isT14OwnerExport(String source,String target) {
        String[][] allowed={
          {"dz.sh.hidra.modules.incident.infrastructure.integration.IncidentEscalationSourceAdapter",
           "dz.sh.hidra.modules.alarm.application.contract.incident.IncidentAlarmEvidenceContract"},
          {"dz.sh.hidra.modules.incident.infrastructure.integration.IncidentEscalationSourceAdapter",
           "dz.sh.hidra.modules.leakdetection.application.contract.incident.IncidentLeakCandidateEvidenceContract"},
          {"dz.sh.hidra.modules.incident.infrastructure.security.IncidentEscalationAuthorizationAdapter",
           "dz.sh.hidra.modules.identity.application.contract.incident.IncidentScopedAuthorizationContract"},
          {"dz.sh.hidra.modules.incident.infrastructure.security.IncidentEscalationAuthorizationAdapter",
           "dz.sh.hidra.modules.topology.application.contract.incident.IncidentTopologyOwnershipContract"},
          {"dz.sh.hidra.modules.alarm.application.service.IncidentAlarmEvidenceQueryService",
           "dz.sh.hidra.modules.topology.application.contract.alarm.AlarmTopologyOwnershipContract"}};
        for(String[] pair:allowed)if(source.equals(pair[0])
            &&(target.equals(pair[1])||target.startsWith(pair[1]+"$")))return true;
        return false;
    }
    private static boolean isT14ProtectedOwnerType(String target) {
        return target.equals("dz.sh.hidra.modules.alarm.application.contract.incident.IncidentAlarmEvidenceContract")
            ||target.startsWith("dz.sh.hidra.modules.alarm.application.contract.incident.IncidentAlarmEvidenceContract$")
            ||target.equals("dz.sh.hidra.modules.leakdetection.application.contract.incident.IncidentLeakCandidateEvidenceContract")
            ||target.startsWith("dz.sh.hidra.modules.leakdetection.application.contract.incident.IncidentLeakCandidateEvidenceContract$")
            ||target.equals("dz.sh.hidra.modules.identity.application.contract.incident.IncidentScopedAuthorizationContract")
            ||target.startsWith("dz.sh.hidra.modules.identity.application.contract.incident.IncidentScopedAuthorizationContract$")
            ||target.equals("dz.sh.hidra.modules.topology.application.contract.incident.IncidentTopologyOwnershipContract")
            ||target.startsWith("dz.sh.hidra.modules.topology.application.contract.incident.IncidentTopologyOwnershipContract$");
    }

    private static boolean isLeakT13OwnerExport(String source,String target) {
        String monitoring="dz.sh.hidra.modules.monitoring.application.contract.leakdetection.LeakDetectionMonitoringEvaluationContract";
        String identity="dz.sh.hidra.modules.identity.application.contract.leakdetection.LeakDetectionScopedAuthorizationContract";
        String topology="dz.sh.hidra.modules.topology.application.contract.leakdetection.LeakDetectionTopologyOwnershipContract";
        if(source.equals("dz.sh.hidra.modules.leakdetection.infrastructure.integration.LeakMonitoringEvidenceAdapter"))
            return target.equals(monitoring)||target.startsWith(monitoring+"$");
        if(source.equals("dz.sh.hidra.modules.leakdetection.infrastructure.security.LeakCandidateResourceAuthorizationAdapter"))
            return target.equals(identity)||target.startsWith(identity+"$")
                ||target.equals(topology)||target.startsWith(topology+"$");
        return false;
    }
    private static boolean isAlarmMonitoringEvidenceExport(String source, String target) {
        String consumer = "dz.sh.hidra.modules.alarm.infrastructure.integration.MonitoringEvaluationEvidenceAdapter";
        String owner = "dz.sh.hidra.modules.monitoring.application.contract.alarm.AlarmMonitoringEvaluationContract";
        return consumer.equals(source) && (target.equals(owner) || target.startsWith(owner + "$"));
    }

    private static boolean isAlarmOwnerExport(String source, String target) {
        String consumer = "dz.sh.hidra.modules.alarm.infrastructure.security.AlarmResourceAuthorizationAdapter";
        if (!consumer.equals(source)) return false;
        return java.util.Set.of(
                "dz.sh.hidra.modules.identity.application.contract.alarm.AlarmScopedAuthorizationContract",
                "dz.sh.hidra.modules.topology.application.contract.alarm.AlarmTopologyOwnershipContract")
                .stream().anyMatch(root -> target.equals(root) || target.startsWith(root + "$")
                        || target.startsWith(root + "."));
    }

    /** HAPI-933: only one exact Telemetry infrastructure adapter may consume these two contract roots. */
    private static boolean isTelemetryNativeOwnerExport(String source, String imported) {
        String consumer="dz.sh.hidra.modules.telemetry.infrastructure.security.TelemetryReadingNativeScopeAdapter";
        if (!consumer.equals(source)) return false;
        return Set.of(
                "dz.sh.hidra.modules.identity.application.contract.telemetry.TelemetryScopedAuthorizationContract",
                "dz.sh.hidra.modules.topology.application.contract.telemetry.TelemetryTopologyOwnershipContract")
                .stream().anyMatch(root -> imported.equals(root) || imported.startsWith(root + "."));
    }

    /** T10: no package-wide exposure for Monitoring's exact Identity/Topology imports. */
    private static boolean isMonitoringRuleOwnerExport(String source,String imported) {
        if(!"dz.sh.hidra.modules.monitoring.infrastructure.security.MonitoringRuleNativeScopeAdapter".equals(source))return false;
        return Set.of(
                "dz.sh.hidra.modules.identity.application.contract.monitoring.MonitoringRuleScopedAuthorizationContract",
                "dz.sh.hidra.modules.topology.application.contract.monitoring.MonitoringRuleAssetOwnershipContract").stream().anyMatch(root->
                imported.equals(root)||imported.startsWith(root+"."));
    }

    private static boolean baselineOwnerExport(String source,String imported) {
        String coordinator="dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringBaselineAdmissionCoordinator";
        String publicationCoordinator="dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringBaselinePublicationCoordinator";
        String reader="dz.sh.hidra.modules.topology.infrastructure.integration.EngineeringBaselineSourceAdapter";
        String identity="dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract";
        String custody="dz.sh.hidra.modules.custody.application.contract.topology.TopologyBaselineProductSourceContract";
        String manufacturer="dz.sh.hidra.modules.simulation.application.contract.topology.TopologyManufacturerCharacteristicReferenceContract";
        return ((source.equals(coordinator)||source.equals(publicationCoordinator))
                && (imported.equals(identity)||imported.startsWith(identity+".")))
            || (source.equals(reader) && List.of(custody,manufacturer).stream()
                .anyMatch(root -> imported.equals(root)||imported.startsWith(root+".")));
    }

    /** Only the reviewed Simulation coordinator may consume the two named owner exports. */
    private static boolean manufacturerCharacteristicOwnerExport(String source, String imported) {
        return source.equals("dz.sh.hidra.modules.simulation.application.service.ProtectedManufacturerCharacteristicAdmissionCoordinator")
                && Set.of(
                        "dz.sh.hidra.modules.identity.application.contract.simulation.SimulationManufacturerCharacteristicAuthorizationContract",
                        "dz.sh.hidra.modules.party.application.contract.simulation.SimulationManufacturerEvidenceContract")
                        .contains(imported);
    }

    /** Precisely five reviewed consumers of two public owner contracts; no package-wide export. */
    private static boolean characteristicLinkOwnerExport(String source,String imported) {
        String owner="dz.sh.hidra.modules.topology.application.service.ProtectedInstalledCharacteristicLinkAdmissionCoordinator";
        String storage="dz.sh.hidra.modules.topology.application.port.out.InstalledCharacteristicLinkStoragePort";
        String writer="dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcInstalledCharacteristicLinkRepositoryAdapter";
        String simulation="dz.sh.hidra.modules.simulation.infrastructure.integration.TopologyManufacturerCharacteristicReferenceAdapter";
        String identityContract="dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledCharacteristicLinkAuthorizationContract";
        String simulationContract="dz.sh.hidra.modules.simulation.application.contract.topology.TopologyManufacturerCharacteristicReferenceContract";
        String partyContract="dz.sh.hidra.modules.party.application.contract.simulation.SimulationManufacturerEvidenceContract";
        return (source.equals(owner)
                    && (imported.equals(identityContract)||imported.startsWith(identityContract+".")
                        || imported.equals(simulationContract)||imported.startsWith(simulationContract+".")))
                || ((source.equals(storage)||source.equals(writer))
                    && imported.equals(identityContract+".Origin"))
                || (source.equals(simulation) && (imported.equals(partyContract)
                    || imported.startsWith(partyContract+".")));
    }

    @Test void exactCharacteristicLinkContractsAreNarrowerThanAnySiblings() {
        String owner="dz.sh.hidra.modules.topology.application.service.ProtectedInstalledCharacteristicLinkAdmissionCoordinator";
        String storage="dz.sh.hidra.modules.topology.application.port.out.InstalledCharacteristicLinkStoragePort";
        String writer="dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcInstalledCharacteristicLinkRepositoryAdapter";
        String reader="dz.sh.hidra.modules.simulation.infrastructure.integration.TopologyManufacturerCharacteristicReferenceAdapter";
        String identity="dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledCharacteristicLinkAuthorizationContract";
        String manufacturer="dz.sh.hidra.modules.simulation.application.contract.topology.TopologyManufacturerCharacteristicReferenceContract";
        String party="dz.sh.hidra.modules.party.application.contract.simulation.SimulationManufacturerEvidenceContract";
        assertTrue(characteristicLinkOwnerExport(owner,identity));
        assertTrue(characteristicLinkOwnerExport(owner,identity+".Origin"));
        assertTrue(characteristicLinkOwnerExport(owner,manufacturer));
        assertTrue(characteristicLinkOwnerExport(storage,identity+".Origin"));
        assertTrue(characteristicLinkOwnerExport(writer,identity+".Origin"));
        assertTrue(characteristicLinkOwnerExport(reader,party));
        assertFalse(characteristicLinkOwnerExport(owner+"Sibling",identity));
        assertFalse(characteristicLinkOwnerExport(owner,identity+"Sibling"));
        assertFalse(characteristicLinkOwnerExport(writer,identity));
        assertFalse(characteristicLinkOwnerExport(storage,manufacturer));
        assertFalse(characteristicLinkOwnerExport(reader+"Sibling",party));
        assertFalse(characteristicLinkOwnerExport(reader,party+"Sibling"));
        assertFalse(characteristicLinkOwnerExport(owner,"dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant"));
    }

    private static boolean stationConfigurationOwnerExport(String source,String imported) {
        String consumer="dz.sh.hidra.modules.topology.application.service.ProtectedStationConfigurationAdmissionCoordinator";
        String root="dz.sh.hidra.modules.identity.application.contract.topology.TopologyStationConfigurationAuthorizationContract";
        return source.equals(consumer) && (imported.equals(root) || imported.startsWith(root+"."));
    }
    private static boolean installedUnitOwnerExport(String source,String imported) {
        String consumer="dz.sh.hidra.modules.topology.application.service.ProtectedInstalledUnitAdmissionCoordinator";
        String root="dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract";
        return source.equals(consumer) && (imported.equals(root) || imported.startsWith(root+"."));
    }

    // T21: only the exact custody lifecycle coordinator and persistence adapter may
    // use the pre-existing Topology-owned live facility/company lookup. This is
    // not a package-wide exemption and never exports Topology persistence internals.
    private static boolean t21CustodyOwnershipExport(String source, String imported) {
        String root="dz.sh.hidra.modules.topology.application.contract.custody.CustodyMeasurementAssetOwnershipContract";
        return Set.of(
            "dz.sh.hidra.modules.custody.application.service.CustodyTransferLifecycleApplicationService",
            "dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.JdbcCustodyTransferLifecycleAdapter")
            .contains(source) && imported.equals(root);
    }

    // T20: the capture service consumes only the four reviewed owner contract roots.
    private static boolean t20MeasurementOwnerExport(String source, String imported) {
        if (!source.equals("dz.sh.hidra.modules.custody.application.service.CustodyMeasurementCaptureApplicationService")) return false;
        return Set.of(
                "dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyIntervalEvidenceContract",
                "dz.sh.hidra.modules.topology.application.contract.custody.CustodyMeasurementAssetOwnershipContract",
                "dz.sh.hidra.modules.party.application.contract.custody.CustodyMeasurementPartyReferenceContract",
                "dz.sh.hidra.modules.identity.application.contract.custody.CustodyMeasurementScopedAuthorizationContract")
                .stream().anyMatch(root -> imported.equals(root) || imported.startsWith(root + "."));
    }

    private static boolean custodyProductOwnerExport(String source,String imported) {
        if (suppliedFluidOwnerExport(source, imported) || productPublicationOwnerExport(source,imported)) return true;
        String consumer="dz.sh.hidra.modules.custody.application.service.ProtectedProductDraftAdmissionCoordinator";
        String root="dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract";
        return source.equals(consumer) && (imported.equals(root) || imported.startsWith(root+"."));
    }

    private static boolean organizationOwnerExport(String source, String imported) {
        // HAPI-925: exact Organization consumer and the two scalar Identity contract types;
        // no new package-wide export, private Identity query, sibling or other caller.
        String consumer = "dz.sh.hidra.modules.organization.infrastructure.security.OrganizationFoundationAuthorizationAdapter";
        String contract = "dz.sh.hidra.modules.identity.application.contract.organization.OrganizationIdentityUserEligibility";
        if (source.equals(consumer) && (imported.equals(contract) || imported.equals(contract + ".Eligibility"))) {
            return true;
        }
        return source.startsWith("dz.sh.hidra.modules.organization.") && Set.of(
                "dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract",
                "dz.sh.hidra.modules.party.application.contract.organization.OrganizationProtectedPartyEligibilityContract")
                .stream().anyMatch(root -> imported.equals(root) || imported.startsWith(root + "."));
    }

    @Test void appointmentImportsDoNotOpenPrivateSiblingsOrOtherConsumers() {
        String source = "dz.sh.hidra.modules.organization.application.service.AppointmentCoordinator";
        String root = "dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract";
        assertTrue(organizationOwnerExport(source, root));
        assertTrue(organizationOwnerExport(source, root + ".Admission"));
        assertFalse(organizationOwnerExport(source, root + "Sibling"));
        assertFalse(organizationOwnerExport(source, "dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant.OrganizationClaimGroup"));
        assertFalse(organizationOwnerExport("dz.sh.hidra.modules.topology.application.service.Other", root));
        String consumer = "dz.sh.hidra.modules.organization.infrastructure.security.OrganizationFoundationAuthorizationAdapter";
        String eligibility = "dz.sh.hidra.modules.identity.application.contract.organization.OrganizationIdentityUserEligibility";
        assertTrue(organizationOwnerExport(consumer, eligibility));
        assertTrue(organizationOwnerExport(consumer, eligibility + ".Eligibility"));
        assertFalse(organizationOwnerExport(consumer, eligibility + "Sibling"));
        assertFalse(organizationOwnerExport(consumer, eligibility + ".Other"));
        assertFalse(organizationOwnerExport("dz.sh.hidra.modules.organization.application.service.Other", eligibility));
        assertFalse(organizationOwnerExport("dz.sh.hidra.modules.topology.application.service.Other", eligibility));
        assertFalse(organizationOwnerExport(consumer,
                "dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase"));
    }

    @Test
    void directWireDomainRepresentationLeakageRemainsAbsent() throws IOException {
        Map<Path, SourceType> sources = sources();
        Map<String, String> kinds = new HashMap<>();
        for (SourceType source : sources.values()) {
            kinds.put(source.fqcn(), source.kind());
        }

        Set<String> blockers = new HashSet<>();
        for (SourceType source : sources.values()) {
            String path = source.path().toString().replace('\\', '/');
            if (!path.contains("/api/rest/request/") && !path.contains("/api/rest/response/")) {
                continue;
            }
            Matcher imports = IMPORT.matcher(source.source());
            while (imports.find()) {
                String imported = imports.group(1);
                if (!imported.contains(".domain.") || "enum".equals(kinds.get(imported))) {
                    continue;
                }
                blockers.add(source.fqcn() + " -> " + imported);
            }
        }

        assertEquals(
                EXPECTED_WIRE_BLOCKER,
                blockers,
                "HRA-102 forbids REST request/response exposure of non-enum domain representation."
        );
    }

    @Test
    void residualProjectionVocabularyRemainsRemovedAndDocumented() throws IOException {
        Path projectionType = Path.of(
                "src/main/java/dz/sh/hidra/modules/topology/domain/value/ProjectionType.java"
        );
        String classification =
                Files.readString(Path.of("docs/architecture/static-orphan-classification.md"));
        String closure =
                Files.readString(Path.of("docs/architecture/forensic-remediation-closure.md"));

        assertTrue(
                Files.notExists(projectionType),
                "HRA-122 removed the zero-incoming Topology ProjectionType residual."
        );
        assertTrue(
                classification.contains("topology.ProjectionType")
                        && classification.contains("DELETE_RESIDUAL")
                        && classification.contains("REMOVED_HRA_122"),
                "ProjectionType removal must retain its evidence-backed orphan disposition."
        );
        assertTrue(
                closure.contains("## HRA-122 corrective follow-up"),
                "Forensic closure evidence must record the HRA-122 corrective removal."
        );
    }

    @Test
    void finalClosureEvidenceRecordsCompletedRerun() throws IOException {
        String closure =
                Files.readString(Path.of("docs/architecture/forensic-remediation-closure.md"));
        String roadmap =
                Files.readString(Path.of("docs/roadmap/repository-remediation.md"));

        assertTrue(
                closure.contains("**HRA-120 status: COMPLETED.**")
                        && closure.contains("d6d1454b816b819bc90c2c4ef3e382a59cd6d4c0"),
                "Final HRA-120 evidence must record the successful exact-head rerun."
        );
        assertTrue(
                roadmap.contains("| `HRA-120`")
                        && roadmap.contains("**Completed** — final rerun on exact head"),
                "Repository remediation roadmap must keep HRA-120 closed after the successful rerun."
        );
    }

    private static Map<Path, SourceType> sources() throws IOException {
        Map<Path, SourceType> result = new HashMap<>();
        try (Stream<Path> paths = Files.walk(ROOT)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        String source = read(path);
                        Matcher packageMatcher = PACKAGE.matcher(source);
                        Matcher typeMatcher = TYPE.matcher(source);
                        if (!packageMatcher.find() || !typeMatcher.find()) {
                            return;
                        }
                        String packageName = packageMatcher.group(1);
                        String simpleName = typeMatcher.group(2);
                        result.put(path, new SourceType(
                                path, source, packageName, typeMatcher.group(1),
                                packageName + "." + simpleName
                        ));
                    });
        }
        return Map.copyOf(result);
    }

    private static String read(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot read source: " + path, exception);
        }
    }

    private static boolean isPrivate(String packageName) {
        return packageName.contains(".domain.")
                || packageName.contains(".infrastructure.")
                || packageName.contains(".application.");
    }

    private static String module(String packageName) {
        String prefix = "dz.sh.hidra.modules.";
        if (!packageName.startsWith(prefix)) {
            return null;
        }
        String remainder = packageName.substring(prefix.length());
        int separator = remainder.indexOf('.');
        return separator < 0 ? remainder : remainder.substring(0, separator);
    }

    private static String packageName(String className) {
        int separator = className.lastIndexOf('.');
        return separator < 0 ? "" : className.substring(0, separator);
    }

    private record SourceType(
            Path path,
            String source,
            String packageName,
            String kind,
            String fqcn
    ) { }

    @Test void publicationCoordinatorAddsOnlyTheExactReviewedIdentityContract() throws Exception {
        var path=Path.of("src/main/java/dz/sh/hidra/modules/topology/application/service/ProtectedEngineeringPublicationAdmissionCoordinator.java");
        var imported=new HashSet<String>();
        var matcher=IMPORT.matcher(Files.readString(path));
        while(matcher.find())if(matcher.group(1).contains(".modules.identity."))imported.add(matcher.group(1));
        assertEquals(Set.of("dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract"),imported);
    }
    private static boolean suppliedFluidOwnerExport(String source,String imported) {
        String consumer="dz.sh.hidra.modules.custody.application.service.ProtectedSuppliedFluidPropertyAdmissionCoordinator";
        return (source.equals(consumer))
            && Set.of("dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract","dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyProtectedFluidUnitContract","dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedFluidPropertyAuthorizationContract").stream()
                .anyMatch(root -> imported.equals(root) || imported.startsWith(root+"."));
    }
    @Test void suppliedFluidExportsStayExactToTheirCoordinator() {
        String consumer="dz.sh.hidra.modules.custody.application.service.ProtectedSuppliedFluidPropertyAdmissionCoordinator";
        for(String root:Set.of("dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract","dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyProtectedFluidUnitContract","dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedFluidPropertyAuthorizationContract")) {
            assertTrue(suppliedFluidOwnerExport(consumer,root));
            assertTrue(suppliedFluidOwnerExport(consumer,root+".Admission"));
            assertFalse(suppliedFluidOwnerExport(consumer,root+"Sibling"));
            assertFalse(suppliedFluidOwnerExport(consumer+"Sibling",root));
            assertFalse(suppliedFluidOwnerExport("dz.sh.hidra.modules.topology.application.service.Other",root));
        }
        assertFalse(suppliedFluidOwnerExport(consumer,"dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter.JdbcCustodyProtectedFluidUnitAdapter"));
        assertFalse(suppliedFluidOwnerExport(consumer,"dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant"));
    }
    private static boolean productPublicationOwnerExport(String source,String imported){
        return source.equals("dz.sh.hidra.modules.custody.application.service.ProtectedProductPublicationAdmissionCoordinator") && Set.of("dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductPublicationAuthorizationContract","dz.sh.hidra.modules.workflow.application.contract.custody.CustodyProtectedProductPublicationApprovalContract","dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract","dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyProtectedFluidUnitContract").stream().anyMatch(root->imported.equals(root) || imported.startsWith(root+"."));
    }
    @Test void productPublicationImportsHaveOnlyExactOwnerRoots(){
        String source="dz.sh.hidra.modules.custody.application.service.ProtectedProductPublicationAdmissionCoordinator";
        for(String root:Set.of("dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductPublicationAuthorizationContract","dz.sh.hidra.modules.workflow.application.contract.custody.CustodyProtectedProductPublicationApprovalContract","dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract","dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyProtectedFluidUnitContract")){assertTrue(productPublicationOwnerExport(source,root));assertTrue(productPublicationOwnerExport(source,root+".Admission"));assertFalse(productPublicationOwnerExport(source,root+"Sibling"));assertFalse(productPublicationOwnerExport(source+"Sibling",root));}
        assertFalse(productPublicationOwnerExport(source,"dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter.JdbcCustodyProtectedProductPublicationApprovalAdapter"));
    }
}
