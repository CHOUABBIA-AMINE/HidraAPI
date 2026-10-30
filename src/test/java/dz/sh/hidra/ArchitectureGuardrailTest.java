/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ArchitectureGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : bootstrap
 * @Package     : dz.sh.hidra
 *
 * @Description : Enforces repository-wide modular monolith layer and structural boundaries.
 *
 */
package dz.sh.hidra;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;

import dz.sh.hidra.modules.organization.application.service.ResponsibilityAssignmentApplicationService;
import dz.sh.hidra.modules.organization.application.service.ResponsibilityReconciliationApplicationService;
import dz.sh.hidra.modules.organization.application.service.ResponsibilityRevocationApplicationService;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.StreamSupport;

/**
 * Protects the architectural dependency direction defined by the Hidra coding policy.
 *
 * <p>Repository-wide layer rules are complemented by strict cross-module ownership rules.
 * A business module may not import another business module's domain, infrastructure, or
 * private application package unless an exact transitional dependency is recorded below
 * or the target package is explicitly exported as a cross-module contract.</p>
 *
 * <p>The transitional allowlist is intentionally narrow. HRA-090 and HRA-091 must remove
 * the corresponding entries when Planning/Workflow and Organization/Topology are moved to
 * deliberate exported contracts.</p>
 */
class ArchitectureGuardrailTest {

    private static final String MODULE_PREFIX = "dz.sh.hidra.modules.";
    private static final String PLATFORM_PREFIX = "dz.sh.hidra.platform.";
    private static final String REVIEWED_GENERIC_PERSISTENCE_READER =
            "dz.sh.hidra.platform.workbench.HidraOperationalWorkbenchService";
    private static final String WORKBENCH_EXPOSURE_POLICY =
            "dz.sh.hidra.platform.workbench.HidraOperationalWorkbenchExposurePolicy";

    private static final JavaClasses PRODUCTION_CLASSES =
            new ClassFileImporter()
                    .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                    .importPackages("dz.sh.hidra");

    /**
     * Deliberately exported package prefixes may be added here only together with the
     * architecture decision/roadmap task that creates that public module contract.
     */
    private static final Set<String> EXPORTED_CROSS_MODULE_PACKAGE_PREFIXES =
            Set.of(
                    "dz.sh.hidra.modules.workflow.application.contract.documents",
                    "dz.sh.hidra.modules.workflow.application.contract.target",
                    "dz.sh.hidra.modules.identity.application.contract.workflow",
                    "dz.sh.hidra.modules.identity.application.contract.topology",
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
                    "dz.sh.hidra.modules.assets.application.contract.hse");

    /**
     * Exact temporary exceptions present in the forensic baseline.
     *
     * <p>Values are root class names; nested types of the same contract are covered.
     * No wildcard package exception is allowed.</p>
     */
    private static final Map<String, Set<String>> TRANSITIONAL_CROSS_MODULE_DEPENDENCIES = Map.of();

    private static final Set<String> ORGANIZATION_OWNER_EXPORTS = Set.of(
            "dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract",
            "dz.sh.hidra.modules.party.application.contract.organization.OrganizationProtectedPartyEligibilityContract");

    private static boolean isOrganizationOwnerExport(String source, String target) {
        return source.startsWith("dz.sh.hidra.modules.organization.")
                && ORGANIZATION_OWNER_EXPORTS.stream().anyMatch(root -> target.equals(root) || target.startsWith(root + "$"));
    }

    private static boolean isCustodyProductOwnerExport(String source, String target) {
        if (isSuppliedFluidOwnerExport(source, target) || isProductPublicationOwnerExport(source,target)) return true;
        String root="dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract";
        String consumer="dz.sh.hidra.modules.custody.application.service.ProtectedProductDraftAdmissionCoordinator";
        return (source.equals(consumer) || source.startsWith(consumer+"$"))
                && (target.equals(root) || target.startsWith(root+"$"));
    }

    @Test void custodyProductExportKeepsPartyAndIdentityInternalsClosed() {
        String source="dz.sh.hidra.modules.custody.application.service.ProtectedProductDraftAdmissionCoordinator";
        String root="dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract";
        assertFalse(isForbiddenCrossModuleDependency(source,root));
        assertFalse(isForbiddenCrossModuleDependency(source,root+"$Admission"));
        assertFalse(isForbiddenCrossModuleDependency(source+"$Admission",root+"$Contribution"));
        assertTrue(isForbiddenCrossModuleDependency("dz.sh.hidra.modules.custody.application.service.Other",root));
        assertTrue(isForbiddenCrossModuleDependency(source,root+"Sibling"));
        assertTrue(isForbiddenCrossModuleDependency("dz.sh.hidra.modules.topology.application.service.Other",root));
        assertFalse(isForbiddenCrossModuleDependency(source,
                "dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductDraftAuthorizationContract"));
        assertTrue(isForbiddenCrossModuleDependency(source,
                "dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant$ProductClaimPair"));
        assertTrue(isForbiddenCrossModuleDependency(source,
                "dz.sh.hidra.modules.party.infrastructure.persistence.adapter.JdbcCustodyProtectedPartyEligibilityAdapter"));
    }

    @Test void appointmentOwnerExportsAdmitOnlyTheirExactRootsAndNestedData() {
        String source = "dz.sh.hidra.modules.organization.application.service.AppointmentCoordinator";
        for (String root : ORGANIZATION_OWNER_EXPORTS) {
            assertFalse(isForbiddenCrossModuleDependency(source, root));
            assertFalse(isForbiddenCrossModuleDependency(source, root + "$Admission"));
            assertTrue(isForbiddenCrossModuleDependency(source, root + "Sibling"));
            assertTrue(isForbiddenCrossModuleDependency("dz.sh.hidra.modules.topology.application.service.Other", root));
        }
        assertTrue(isForbiddenCrossModuleDependency(source,
                "dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant$OrganizationClaimGroup"));
    }

    @Test
    void transactionalOrganizationResponsibilityServicesMustRemainProxyable() {
        assertFalse(
                Modifier.isFinal(ResponsibilityAssignmentApplicationService.class.getModifiers()),
                "Transactional responsibility assignment service must remain proxyable.");
        assertFalse(
                Modifier.isFinal(ResponsibilityRevocationApplicationService.class.getModifiers()),
                "Transactional responsibility revocation service must remain proxyable.");
        assertFalse(
                Modifier.isFinal(
                        ResponsibilityReconciliationApplicationService.class.getModifiers()),
                "Transactional responsibility reconciliation service must remain proxyable.");
    }

    @Test
    void kernelMustRemainFrameworkAndModuleIndependent() {
        noClasses()
                .that()
                .resideInAPackage("..kernel..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "org.springframework..",
                        "jakarta.persistence..",
                        "org.hibernate..",
                        "io.swagger.v3.oas.annotations..",
                        "dz.sh.hidra.platform..",
                        "dz.sh.hidra.modules..")
                .check(PRODUCTION_CLASSES);
    }

    @Test
    void businessDomainMustNotDependOnFrameworkOrPlatformInfrastructure() {
        noClasses()
                .that()
                .resideInAPackage("..modules..domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "org.springframework..",
                        "jakarta.persistence..",
                        "org.hibernate..",
                        "com.fasterxml.jackson..",
                        "io.swagger.v3.oas.annotations..",
                        "dz.sh.hidra.platform..")
                .check(PRODUCTION_CLASSES);
    }

    @Test
    void applicationMustNotDependOnApiOrInfrastructure() {
        noClasses()
                .that()
                .resideInAPackage("..modules..application..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "..modules..api..",
                        "..modules..infrastructure..",
                        "jakarta.persistence..",
                        "org.springframework.web..",
                        "org.springframework.data..")
                .check(PRODUCTION_CLASSES);
    }

    @Test
    void apiMustNotDependOnInfrastructure() {
        noClasses()
                .that()
                .resideInAPackage("..modules..api..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..modules..infrastructure..")
                .check(PRODUCTION_CLASSES);
    }

    @Test
    void controllersMustNotAccessOutboundOrPersistenceRepositoriesDirectly() {
        noClasses()
                .that()
                .resideInAPackage("..api.rest.controller..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "..application.port.out..",
                        "..infrastructure.persistence..",
                        "org.springframework.data..")
                .check(PRODUCTION_CLASSES);
    }

    @Test
    void platformJpaAccessMustRemainRestrictedToReviewedWorkbenchBoundary() {
        List<String> violations = new ArrayList<>();

        for (JavaClass source : PRODUCTION_CLASSES) {
            if (!source.getPackageName().startsWith(PLATFORM_PREFIX)) {
                continue;
            }

            for (Dependency dependency : source.getDirectDependenciesFromSelf()) {
                JavaClass target = dependency.getTargetClass();
                String targetPackage = target.getPackageName();

                boolean dependsOnJpa =
                        targetPackage.equals("jakarta.persistence")
                                || targetPackage.startsWith("jakarta.persistence.");
                boolean dependsOnModulePersistence =
                        targetPackage.startsWith(MODULE_PREFIX)
                                && targetPackage.contains(".infrastructure.persistence");

                if (!dependsOnJpa && !dependsOnModulePersistence) {
                    continue;
                }

                boolean reviewedWorkbenchJpaDependency =
                        reviewedPlatformJpa(
                                source.getName(), dependsOnJpa, dependsOnModulePersistence);

                if (!reviewedWorkbenchJpaDependency) {
                    violations.add(source.getName() + " -> " + target.getName());
                }
            }
        }

        assertTrue(
                violations.isEmpty(),
                () ->
                        "Platform code must not introduce generic JPA/module-persistence access"
                                + " outside the exact reviewed Workbench and technical transaction"
                                + " boundaries. Violations: "
                                + violations);

        JavaClass reviewedReader = PRODUCTION_CLASSES.get(REVIEWED_GENERIC_PERSISTENCE_READER);
        boolean dependsOnExposurePolicy =
                reviewedReader.getDirectDependenciesFromSelf().stream()
                        .map(Dependency::getTargetClass)
                        .map(JavaClass::getName)
                        .anyMatch(WORKBENCH_EXPOSURE_POLICY::equals);

        assertTrue(
                dependsOnExposurePolicy,
                "The reviewed Workbench persistence reader must retain the fail-closed exposure"
                        + " policy dependency.");
    }

    @Test
    void businessModulesMustNotReachIntoOtherModuleInternals() {
        List<String> violations = new ArrayList<>();

        for (JavaClass source : PRODUCTION_CLASSES) {
            String sourceModule = moduleName(source.getPackageName());
            if (sourceModule == null) {
                continue;
            }

            for (Dependency dependency : source.getDirectDependenciesFromSelf()) {
                JavaClass target = dependency.getTargetClass();
                String targetModule = moduleName(target.getPackageName());

                if (targetModule == null || sourceModule.equals(targetModule)) {
                    continue;
                }
                if (!isPrivateCrossModulePackage(target.getPackageName())) {
                    continue;
                }
                if (isPublicationSpecific(target.getName())) {
                    if(!isPublicationExportAllowed(source.getName(),target.getName()))violations.add(source.getName()+" -> "+target.getName());
                    continue;
                }
                if (isExplicitlyExported(target.getPackageName())) {
                    continue;
                }
                if (isTransitionallyAllowed(source.getName(), target.getName())) {
                    continue;
                }
                if (isOrganizationOwnerExport(source.getName(), target.getName())) continue;
                if (isCustodyProductOwnerExport(source.getName(), target.getName())) continue;

                violations.add(source.getName() + " -> " + target.getName());
            }
        }

        assertTrue(
                violations.isEmpty(),
                () ->
                        "Business modules must not depend on another module's private "
                                + "domain/infrastructure/application packages. Violations: "
                                + violations);
    }

    @Test
    void transitionalCrossModuleAllowlistMustRemainExactAndInUse() {
        List<String> missingAllowlistedDependencies = new ArrayList<>();

        TRANSITIONAL_CROSS_MODULE_DEPENDENCIES.forEach(
                (sourceName, targetRoots) -> {
                    JavaClass source = PRODUCTION_CLASSES.get(sourceName);
                    Set<String> actualTargets = new HashSet<>();
                    for (Dependency dependency : source.getDirectDependenciesFromSelf()) {
                        actualTargets.add(dependency.getTargetClass().getName());
                    }

                    for (String targetRoot : targetRoots) {
                        boolean present =
                                actualTargets.stream()
                                        .anyMatch(
                                                targetName -> isTargetRoot(targetName, targetRoot));
                        if (!present) {
                            missingAllowlistedDependencies.add(sourceName + " -> " + targetRoot);
                        }
                    }
                });

        assertTrue(
                missingAllowlistedDependencies.isEmpty(),
                () ->
                        "Remove stale transitional dependency exceptions as soon as their imports "
                                + "are refactored. Missing dependencies: "
                                + missingAllowlistedDependencies);
    }

    @Test
    void crossModuleClassifierMustRejectRepresentativePrivateDependency() {
        assertTrue(
                isForbiddenCrossModuleDependency(
                        "dz.sh.hidra.modules.planning.application.service.ExampleService",
                        "dz.sh.hidra.modules.identity.domain.model.User"),
                "An unlisted cross-module dependency on another module's domain must be rejected.");

        assertTrue(
                isForbiddenCrossModuleDependency(
                        "dz.sh.hidra.modules.planning.application.service.PlanningApprovalApplicationService",
                        "dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase"),
                "Planning must no longer depend on Workflow's private application ports.");

        assertFalse(
                isForbiddenCrossModuleDependency(
                        "dz.sh.hidra.modules.planning.application.service.PlanningApprovalApplicationService",
                        "dz.sh.hidra.modules.workflow.application.contract.planning.PlanningWorkflowContract"),
                "The deliberate Workflow contract exported to Planning must remain allowed.");

        assertTrue(
                isForbiddenCrossModuleDependency(
                        "dz.sh.hidra.modules.organization.infrastructure.adapter.AuthoritativeOperationalScopeTargetResolverAdapter",
                        "dz.sh.hidra.modules.topology.application.port.in.ResolveTopologyOperationalScopeTargetUseCase"),
                "Organization must no longer depend on Topology's private application ports.");

        assertFalse(
                isForbiddenCrossModuleDependency(
                        "dz.sh.hidra.modules.organization.infrastructure.adapter.AuthoritativeOperationalScopeTargetResolverAdapter",
                        "dz.sh.hidra.modules.topology.application.contract.organization.TopologyOperationalScopeTargetContract"),
                "The deliberate Topology contract exported to Organization must remain allowed.");

        assertFalse(
                isForbiddenCrossModuleDependency(
                        "dz.sh.hidra.modules.organization.application.service.ResponsibilityAssignmentApplicationService",
                        "dz.sh.hidra.modules.workflow.application.contract.organization.OrganizationResponsibilityWorkflowContract"),
                "The deliberate Workflow responsibility contract exported to Organization must"
                        + " remain allowed.");

        assertFalse(
                isForbiddenCrossModuleDependency(
                        "dz.sh.hidra.modules.organization.application.service.ResponsibilityAssignmentApplicationService",
                        "dz.sh.hidra.modules.audit.application.contract.organization.OrganizationResponsibilityAuditContract"),
                "The deliberate Audit responsibility contract exported to Organization must remain"
                        + " allowed.");

        assertFalse(
                isForbiddenCrossModuleDependency(
                        "dz.sh.hidra.modules.alarm.application.service.AlarmSuppressionExpiryOrchestrator",
                        "dz.sh.hidra.modules.audit.application.contract.alarm.AlarmSuppressionAuditContract"),
                "The deliberate Audit suppression contract exported to Alarm must remain allowed.");
    }

    @Test
    void simulationTelemetryEvidenceExportKeepsOwnerInternalsPrivate() {
        String adapter =
                "dz.sh.hidra.modules.simulation.infrastructure.integration.TelemetryTrustedReadingEvidenceQueryAdapter";
        assertFalse(
                isForbiddenCrossModuleDependency(
                        adapter,
                        "dz.sh.hidra.modules.telemetry.application.contract.simulation.SimulationTrustedReadingContract"));
        assertFalse(
                isForbiddenCrossModuleDependency(
                        adapter,
                        "dz.sh.hidra.modules.telemetry.application.contract.simulation.SimulationTrustedReadingContract$ReadingEvidence"));
        assertTrue(
                isForbiddenCrossModuleDependency(
                        adapter,
                        "dz.sh.hidra.modules.telemetry.domain.model.TrustedTelemetryReading"));
        assertTrue(
                isForbiddenCrossModuleDependency(
                        adapter,
                        "dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TrustedTelemetryReadingJpaEntity"));
        assertTrue(
                isForbiddenCrossModuleDependency(
                        adapter,
                        "dz.sh.hidra.modules.telemetry.application.port.out.TrustedTelemetryReadingRepositoryPort"));
    }

    @Test
    void simulationOwnerEvidenceExportsKeepPrivateTypesClosed() {
        String custodyAdapter =
                "dz.sh.hidra.modules.simulation.infrastructure.integration.CustodyProductCandidateEvidenceQueryAdapter";
        String topologyAdapter =
                "dz.sh.hidra.modules.simulation.infrastructure.integration.TopologyScopeEvidenceQueryAdapter";
        assertFalse(
                isForbiddenCrossModuleDependency(
                        custodyAdapter,
                        "dz.sh.hidra.modules.custody.application.contract.simulation.SimulationProductCandidateContract"));
        assertFalse(
                isForbiddenCrossModuleDependency(
                        custodyAdapter,
                        "dz.sh.hidra.modules.custody.application.contract.simulation.SimulationProductCandidateContract$Candidate"));
        assertFalse(
                isForbiddenCrossModuleDependency(
                        topologyAdapter,
                        "dz.sh.hidra.modules.topology.application.contract.simulation.SimulationTopologyScopeContract"));
        assertFalse(
                isForbiddenCrossModuleDependency(
                        topologyAdapter,
                        "dz.sh.hidra.modules.topology.application.contract.simulation.SimulationTopologyScopeContract$ScopeResolution"));
        assertTrue(
                isForbiddenCrossModuleDependency(
                        custodyAdapter,
                        "dz.sh.hidra.modules.custody.infrastructure.persistence.entity.CustodyCatalogEntryJpaEntity"));
        assertTrue(
                isForbiddenCrossModuleDependency(
                        custodyAdapter, "dz.sh.hidra.modules.custody.domain.model.CustodyBatch"));
        assertTrue(
                isForbiddenCrossModuleDependency(
                        custodyAdapter,
                        "dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyCatalogEntryJpaRepository"));
        assertTrue(
                isForbiddenCrossModuleDependency(
                        topologyAdapter, "dz.sh.hidra.modules.topology.domain.model.Pipeline"));
        assertTrue(
                isForbiddenCrossModuleDependency(
                        topologyAdapter,
                        "dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineJpaEntity"));
        assertTrue(
                isForbiddenCrossModuleDependency(
                        topologyAdapter,
                        "dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort"));
    }

    @Test
    void jpaTableNamesMustBeUnique() {
        Map<String, List<String>> ownersByTable = new HashMap<>();

        for (JavaClass type : PRODUCTION_CLASSES) {
            if (!type.isAnnotatedWith(Entity.class)) {
                continue;
            }

            Table table = type.getAnnotationOfType(Table.class);
            String tableName =
                    table == null || table.name().isBlank()
                            ? type.getSimpleName()
                            : table.name().trim();

            ownersByTable
                    .computeIfAbsent(tableName, ignored -> new ArrayList<>())
                    .add(type.getName());
        }

        Map<String, List<String>> duplicates = new HashMap<>();
        ownersByTable.forEach(
                (tableName, owners) -> {
                    if (owners.size() > 1) {
                        duplicates.put(tableName, owners);
                    }
                });

        assertTrue(
                duplicates.isEmpty(),
                () ->
                        "JPA table names must be unique across production entities. Duplicates: "
                                + duplicates);
    }

    @Test
    void jpaColumnNamesMustBeUniqueInsideEachEntity() {
        List<String> violations = new ArrayList<>();

        for (JavaClass type : PRODUCTION_CLASSES) {
            if (!type.isAnnotatedWith(Entity.class)) {
                continue;
            }

            Map<String, List<String>> fieldsByColumn = new HashMap<>();
            for (JavaField field : type.getAllFields()) {
                if (!field.isAnnotatedWith(Column.class)) {
                    continue;
                }

                Column column = field.getAnnotationOfType(Column.class);
                String columnName =
                        column.name().isBlank() ? field.getName() : column.name().trim();

                fieldsByColumn
                        .computeIfAbsent(columnName, ignored -> new ArrayList<>())
                        .add(field.getFullName());
            }

            fieldsByColumn.forEach(
                    (columnName, fields) -> {
                        if (fields.size() > 1) {
                            violations.add(
                                    type.getName()
                                            + " maps column "
                                            + columnName
                                            + " from "
                                            + fields);
                        }
                    });
        }

        assertTrue(
                violations.isEmpty(),
                () ->
                        "A JPA entity must not map the same column name more than once. Violations:"
                                + " "
                                + violations);
    }

    @Test
    void identityAccessPackageMustNotExist() {
        boolean identityAccessExists =
                StreamSupport.stream(PRODUCTION_CLASSES.spliterator(), false)
                        .map(JavaClass::getPackageName)
                        .anyMatch(
                                packageName ->
                                        packageName.equals("dz.sh.hidra.modules.identityaccess")
                                                || packageName.startsWith(
                                                        "dz.sh.hidra.modules.identityaccess."));

        assertFalse(
                identityAccessExists,
                "Use dz.sh.hidra.modules.identity; identityaccess is a forbidden module/package.");
    }

    private static final String INSTALLED_UNIT_COORDINATOR="dz.sh.hidra.modules.topology.application.service.ProtectedInstalledUnitAdmissionCoordinator";
    private static final Set<String> INSTALLED_UNIT_OWNER_ROOTS=Set.of(
        "dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract",
        "dz.sh.hidra.modules.party.application.contract.topology.TopologyInstalledUnitCompanyEvidenceContract",
        "dz.sh.hidra.modules.party.application.contract.topology.TopologyInstalledUnitManufacturerEvidenceContract");
    @Test void installedUnitOwnerExportsRemainExactToReviewedCoordinator() {
        for(String root:INSTALLED_UNIT_OWNER_ROOTS) {
            assertFalse(isForbiddenCrossModuleDependency(INSTALLED_UNIT_COORDINATOR,root));
            assertFalse(isForbiddenCrossModuleDependency(INSTALLED_UNIT_COORDINATOR+"$Admission",root+"$Admission"));
            assertTrue(isForbiddenCrossModuleDependency(INSTALLED_UNIT_COORDINATOR+"Sibling",root));
            assertTrue(isForbiddenCrossModuleDependency(INSTALLED_UNIT_COORDINATOR,root+"Sibling"));
            assertTrue(isForbiddenCrossModuleDependency("dz.sh.hidra.modules.custody.application.service.Unregistered",root));
        }
        assertTrue(isForbiddenCrossModuleDependency(INSTALLED_UNIT_COORDINATOR,"dz.sh.hidra.modules.identity.infrastructure.security.ProtectedTopologyInstalledUnitAuthorizationParticipant"));
        assertTrue(isForbiddenCrossModuleDependency(INSTALLED_UNIT_COORDINATOR,"dz.sh.hidra.modules.party.infrastructure.persistence.adapter.JdbcTopologyInstalledUnitCompanyEvidenceAdapter"));
    }

    private static boolean isForbiddenCrossModuleDependency(
            String sourceClassName, String targetClassName) {
        String sourcePackage = packageName(sourceClassName);
        String targetPackage = packageName(targetClassName);
        String sourceModule = moduleName(sourcePackage);
        String targetModule = moduleName(targetPackage);

        if (sourceModule == null || targetModule == null || sourceModule.equals(targetModule)) {
            return false;
        }
        for(String root:INSTALLED_UNIT_OWNER_ROOTS)if(targetClassName.startsWith(root))
            return !(targetClassName.equals(root) || targetClassName.startsWith(root+"$"))
                || !sourceClassName.equals(INSTALLED_UNIT_COORDINATOR) && !sourceClassName.startsWith(INSTALLED_UNIT_COORDINATOR+"$");
        // B2 exports no configuration authority to Topology; B3 must admit its exact coordinator.
        if(targetClassName.startsWith("dz.sh.hidra.modules.identity.application.contract.topology.TopologyStationConfigurationAuthorizationContract"))return true;
        if(isProductPublicationSourceType(targetClassName))return true;
        if(isPublicationSpecific(targetClassName))return !isPublicationExportAllowed(sourceClassName,targetClassName);
        if (!isPrivateCrossModulePackage(targetPackage)) {
            return false;
        }
        if (isExplicitlyExported(targetPackage)) {
            return false;
        }
        if (isOrganizationOwnerExport(sourceClassName, targetClassName)) return false;
        if (isCustodyProductOwnerExport(sourceClassName, targetClassName)) return false;
        return !isTransitionallyAllowed(sourceClassName, targetClassName);
    }

    private static boolean isPrivateCrossModulePackage(String packageName) {
        return packageName.contains(".domain.")
                || packageName.contains(".infrastructure.")
                || packageName.contains(".application.");
    }

    private static boolean isExplicitlyExported(String packageName) {
        return EXPORTED_CROSS_MODULE_PACKAGE_PREFIXES.stream().anyMatch(packageName::startsWith);
    }

    private static boolean isTransitionallyAllowed(String sourceClassName, String targetClassName) {
        return TRANSITIONAL_CROSS_MODULE_DEPENDENCIES
                .getOrDefault(sourceClassName, Set.of())
                .stream()
                .anyMatch(targetRoot -> isTargetRoot(targetClassName, targetRoot));
    }

    private static boolean isTargetRoot(String targetClassName, String targetRoot) {
        return targetClassName.equals(targetRoot) || targetClassName.startsWith(targetRoot + "$");
    }

    private static String moduleName(String packageName) {
        if (!packageName.startsWith(MODULE_PREFIX)) {
            return null;
        }

        String remainder = packageName.substring(MODULE_PREFIX.length());
        int separator = remainder.indexOf('.');
        return separator < 0 ? remainder : remainder.substring(0, separator);
    }

    private static String packageName(String className) {
        int separator = className.lastIndexOf('.');
        return separator < 0 ? "" : className.substring(0, separator);
    }

    private static boolean reviewedPlatformJpa(
            String source, boolean jpa, boolean modulePersistence) {
        return jpa
                && !modulePersistence
                && Set.of(
                                REVIEWED_GENERIC_PERSISTENCE_READER,
                                "dz.sh.hidra.platform.persistence.ProtectedJpaTransactionManager",
                                "dz.sh.hidra.platform.configuration.HidraTransactionConfiguration")
                        .contains(source);
    }

    @Test
    void protectedTransactionJpaExceptionsRemainExactAndTechnical() {
        for (String name :
                Set.of(
                        "dz.sh.hidra.platform.persistence.ProtectedJpaTransactionManager",
                        "dz.sh.hidra.platform.configuration.HidraTransactionConfiguration")) {
            assertTrue(reviewedPlatformJpa(name, true, false));
            assertFalse(reviewedPlatformJpa(name, true, true));
            assertTrue(
                    PRODUCTION_CLASSES.get(name).getDirectDependenciesFromSelf().stream()
                            .noneMatch(
                                    d ->
                                            d.getTargetClass()
                                                    .getPackageName()
                                                    .startsWith(MODULE_PREFIX)));
        }
        assertFalse(
                reviewedPlatformJpa("dz.sh.hidra.platform.persistence.OtherReader", true, false));
        assertFalse(
                reviewedPlatformJpa(
                        "dz.sh.hidra.platform.persistence.ProtectedOperationDataSource",
                        true,
                        false));
        assertFalse(
                reviewedPlatformJpa(
                        "dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor",
                        true,
                        false));
    }
    private static boolean isSuppliedFluidOwnerExport(String source,String target) {
        String consumer="dz.sh.hidra.modules.custody.application.service.ProtectedSuppliedFluidPropertyAdmissionCoordinator";
        return (source.equals(consumer) || source.startsWith(consumer+"$"))
            && Set.of("dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract","dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyProtectedFluidUnitContract","dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedFluidPropertyAuthorizationContract").stream()
                .anyMatch(root -> target.equals(root) || target.startsWith(root+"$"));
    }
    @Test void suppliedFluidExportsStayExactToTheirCoordinator() {
        String consumer="dz.sh.hidra.modules.custody.application.service.ProtectedSuppliedFluidPropertyAdmissionCoordinator";
        for(String root:Set.of("dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract","dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyProtectedFluidUnitContract","dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedFluidPropertyAuthorizationContract")) {
            assertTrue(isSuppliedFluidOwnerExport(consumer,root));
            assertTrue(isSuppliedFluidOwnerExport(consumer,root+"$Admission"));
            assertFalse(isSuppliedFluidOwnerExport(consumer,root+"Sibling"));
            assertFalse(isSuppliedFluidOwnerExport(consumer+"Sibling",root));
            assertFalse(isSuppliedFluidOwnerExport("dz.sh.hidra.modules.topology.application.service.Other",root));
        }
        assertFalse(isSuppliedFluidOwnerExport(consumer,"dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter.JdbcCustodyProtectedFluidUnitAdapter"));
        assertFalse(isSuppliedFluidOwnerExport(consumer,"dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant"));
    }
    private static final String PUBLICATION_HUMAN="dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowHumanActorContract";
    private static final Set<String> PUBLICATION_ROOTS=Set.of(PUBLICATION_HUMAN,
        "dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductPublicationAuthorizationContract",
        "dz.sh.hidra.modules.workflow.application.contract.custody.CustodyProtectedProductPublicationApprovalContract");
    private static boolean isPublicationSpecific(String target){return PUBLICATION_ROOTS.stream().anyMatch(target::startsWith);}
    private static boolean isPublicationExportAllowed(String source,String target){
        if(isProductPublicationOwnerExport(source,target))return true;
        String owner="dz.sh.hidra.modules.workflow.application.service.WorkflowTransitionApplicationService";
        return (source.equals(owner) || source.startsWith(owner+"$")) && (target.equals(PUBLICATION_HUMAN) || target.startsWith(PUBLICATION_HUMAN+"$"));
    }
    @Test void publicationExportsAdmitOnlyRegisteredHumanTransitionConsumer() {
        String owner="dz.sh.hidra.modules.workflow.application.service.WorkflowTransitionApplicationService";
        assertFalse(isForbiddenCrossModuleDependency(owner,PUBLICATION_HUMAN));assertFalse(isForbiddenCrossModuleDependency(owner,PUBLICATION_HUMAN+"$Human"));
        assertTrue(isForbiddenCrossModuleDependency(owner+"Sibling",PUBLICATION_HUMAN));assertTrue(isForbiddenCrossModuleDependency(owner,PUBLICATION_HUMAN+"Sibling"));
        for(String root:PUBLICATION_ROOTS){
            assertTrue(isForbiddenCrossModuleDependency("dz.sh.hidra.modules.topology.application.service.Other",root));
            assertTrue(isForbiddenCrossModuleDependency("dz.sh.hidra.modules.custody.application.service.Other",root+"$Admission"));
        }
        assertTrue(isForbiddenCrossModuleDependency(owner,"dz.sh.hidra.modules.identity.infrastructure.security.ProtectedProductPublicationAuthorizationParticipant"));
    }
    private static boolean isProductPublicationOwnerExport(String source,String target){
        String consumer="dz.sh.hidra.modules.custody.application.service.ProtectedProductPublicationAdmissionCoordinator";
        return (source.equals(consumer) || source.startsWith(consumer+"$")) && Set.of("dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductPublicationAuthorizationContract","dz.sh.hidra.modules.workflow.application.contract.custody.CustodyProtectedProductPublicationApprovalContract","dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract","dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyProtectedFluidUnitContract").stream().anyMatch(root->target.equals(root) || target.startsWith(root+"$"));
    }
    @Test void productPublicationOwnerExportsRemainExactAndPrivate(){
        String consumer="dz.sh.hidra.modules.custody.application.service.ProtectedProductPublicationAdmissionCoordinator";
        for(String root:Set.of("dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductPublicationAuthorizationContract","dz.sh.hidra.modules.workflow.application.contract.custody.CustodyProtectedProductPublicationApprovalContract","dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract","dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyProtectedFluidUnitContract")){
            assertFalse(isForbiddenCrossModuleDependency(consumer,root));assertFalse(isForbiddenCrossModuleDependency(consumer,root+"$Admission"));
            assertTrue(isForbiddenCrossModuleDependency(consumer+"Sibling",root));assertTrue(isForbiddenCrossModuleDependency(consumer,root+"Sibling"));
        }
        assertTrue(isForbiddenCrossModuleDependency(consumer,"dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter.JdbcCustodyProtectedProductPublicationApprovalAdapter"));
        assertTrue(isForbiddenCrossModuleDependency(consumer,"dz.sh.hidra.modules.identity.infrastructure.security.ProtectedProductPublicationAuthorizationParticipant"));
        String repository="dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.JdbcProductRevisionPublicationRepositoryAdapter";
        assertTrue(isForbiddenCrossModuleDependency(repository,"dz.sh.hidra.modules.identity.application.contract.custody.CustodyProtectedProductPublicationAuthorizationContract$Request"));
        var scope=java.util.Arrays.stream(dz.sh.hidra.modules.custody.application.service.ProtectedProductPublicationAdmissionCoordinator.Contribution.class.getRecordComponents()).filter(c->c.getName().equals("scope")).findFirst().orElseThrow();
        assertEquals(dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Scope.class,scope.getType());

    }

    private static boolean isProductPublicationSourceType(String type){return isTargetRoot(type,"dz.sh.hidra.modules.custody.application.contract.publication.ProductPublicationSourceContract");}
    @Test void publicationScalarExportRemainsExactAndHasNoUnregisteredConsumer(){
        String root="dz.sh.hidra.modules.custody.application.contract.publication.ProductPublicationSourceContract";
        assertTrue(isProductPublicationSourceType(root));assertTrue(isProductPublicationSourceType(root+"$Source"));assertFalse(isProductPublicationSourceType(root+"Sibling"));assertFalse(isProductPublicationSourceType("dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication"));
        assertFalse(isForbiddenCrossModuleDependency("dz.sh.hidra.modules.custody.infrastructure.integration.ProductPublicationSourceQueryAdapter",root));
        for(String consumer:java.util.List.of("dz.sh.hidra.modules.planning.application.service.Unregistered","dz.sh.hidra.modules.simulation.infrastructure.integration.Unregistered")){
            assertTrue(isForbiddenCrossModuleDependency(consumer,root));assertTrue(isForbiddenCrossModuleDependency(consumer,root+"$Source"));assertTrue(isForbiddenCrossModuleDependency(consumer,root+"Sibling"));assertTrue(isForbiddenCrossModuleDependency(consumer,"dz.sh.hidra.modules.custody.infrastructure.integration.ProductPublicationSourceQueryAdapter"));
        }
        for(var type:dz.sh.hidra.modules.custody.application.contract.publication.ProductPublicationSourceContract.class.getDeclaredClasses())if(type.isRecord())for(var component:type.getRecordComponents())assertFalse(component.getGenericType().getTypeName().contains(".domain."));
    }

    @Test
    void stationConfigurationNativeAuthorityStaysPrivateUntilReviewedTopologyIntegration() {
        String root="dz.sh.hidra.modules.identity.application.contract.topology.TopologyStationConfigurationAuthorizationContract";
        assertTrue(isForbiddenCrossModuleDependency("dz.sh.hidra.modules.topology.application.service.Other",root));
        assertTrue(isForbiddenCrossModuleDependency("dz.sh.hidra.modules.topology.application.service.Other",root+"$Contribution"));
        assertTrue(isForbiddenCrossModuleDependency("dz.sh.hidra.modules.topology.application.service.Other",root+"Sibling"));
        var group=dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant.StationConfigurationClaimGroup.class;
        assertEquals(0,group.getConstructors().length);
        for(var constructor:group.getDeclaredConstructors())assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        for(var field:group.getDeclaredFields())assertTrue(java.lang.reflect.Modifier.isPrivate(field.getModifiers()));
        assertFalse(dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant.InstalledUnitClaimGroup.class.isAssignableFrom(group));
    }
}
