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
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
                    "dz.sh.hidra.modules.simulation.application.contract.topology",
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
                    "dz.sh.hidra.modules.planning.application.contract.workflow",
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
                    "dz.sh.hidra.modules.topology.application.contract.integration",
                    "dz.sh.hidra.modules.documents.application.contract.integration",
                    "dz.sh.hidra.modules.documents.application.contract.target",
                    "dz.sh.hidra.modules.documents.application.contract.audit",
                    "dz.sh.hidra.modules.documents.application.contract.reporting",
                    "dz.sh.hidra.modules.workflow.application.contract.audit",
                    "dz.sh.hidra.modules.identity.application.contract.planning",
                    "dz.sh.hidra.modules.organization.application.contract.planning",
                    "dz.sh.hidra.modules.topology.application.contract.risk",
                    "dz.sh.hidra.modules.audit.application.contract.risk",
                    "dz.sh.hidra.modules.risk.application.contract.evidence",
                    "dz.sh.hidra.modules.risk.application.contract.workflow",
                    "dz.sh.hidra.modules.alarm.application.contract.risk",
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

    /** HAPI-925: permit only the reviewed Identity eligibility contract to one Organization adapter. */
    private static boolean isOrganizationFoundationIdentityEligibilityExport(String source, String target) {
        String consumer = "dz.sh.hidra.modules.organization.infrastructure.security.OrganizationFoundationAuthorizationAdapter";
        String contract = "dz.sh.hidra.modules.identity.application.contract.organization.OrganizationIdentityUserEligibility";
        return source.equals(consumer)
                && (target.equals(contract) || target.equals(contract + "$Eligibility"));
    }

    private static boolean isCharacteristicManufacturerPartyExport(String source,String target) {
        String producer="dz.sh.hidra.modules.simulation.infrastructure.integration.TopologyManufacturerCharacteristicReferenceAdapter";
        String contract="dz.sh.hidra.modules.party.application.contract.simulation.SimulationManufacturerEvidenceContract";
        return (source.equals(producer)||source.startsWith(producer+"$"))
                && (target.equals(contract)||target.startsWith(contract+"$"));
    }
    private static boolean isManufacturerOwnerExport(String source,String target) {
        return (source.equals("dz.sh.hidra.modules.simulation.application.service.ProtectedManufacturerCharacteristicAdmissionCoordinator")
                || source.startsWith("dz.sh.hidra.modules.simulation.application.service.ProtectedManufacturerCharacteristicAdmissionCoordinator$"))
                && MANUFACTURER_OWNER_ROOTS.stream().anyMatch(root->target.equals(root)||target.startsWith(root+"$"));
    }
    private static boolean isCustodyProductOwnerExport(String source, String target) {
        if (isSuppliedFluidOwnerExport(source, target) || isProductPublicationOwnerExport(source,target)) return true;
        String root="dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract";
        String consumer="dz.sh.hidra.modules.custody.application.service.ProtectedProductDraftAdmissionCoordinator";
        return (source.equals(consumer) || source.startsWith(consumer+"$"))
                && (target.equals(root) || target.startsWith(root+"$"));
    }

    // T20: exact owner contracts, no public-package exemption for sibling types.
    private static final Set<String> T20_MEASUREMENT_OWNER_ROOTS = Set.of(
            "dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyIntervalEvidenceContract",
            "dz.sh.hidra.modules.topology.application.contract.custody.CustodyMeasurementAssetOwnershipContract",
            "dz.sh.hidra.modules.party.application.contract.custody.CustodyMeasurementPartyReferenceContract",
            "dz.sh.hidra.modules.identity.application.contract.custody.CustodyMeasurementScopedAuthorizationContract");
    private static boolean isT20MeasurementOwnerType(String target) {
        return T20_MEASUREMENT_OWNER_ROOTS.stream().anyMatch(root -> isTargetRoot(target, root));
    }
    private static boolean hasT20MeasurementOwnerNamePrefix(String target) {
        // A sibling sharing an export prefix is not a registered owner contract.
        return T20_MEASUREMENT_OWNER_ROOTS.stream().anyMatch(target::startsWith);
    }
    private static boolean isT20MeasurementOwnerExport(String source, String target) {
        String coordinator="dz.sh.hidra.modules.custody.application.service.CustodyMeasurementCaptureApplicationService";
        String lifecycle="dz.sh.hidra.modules.custody.application.service.CustodyTransferLifecycleApplicationService";
        String jdbc="dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.JdbcCustodyTransferLifecycleAdapter";
        String ownerContract="dz.sh.hidra.modules.identity.application.contract.custody.CustodyMeasurementScopedAuthorizationContract";
        String topologyContract="dz.sh.hidra.modules.topology.application.contract.custody.CustodyMeasurementAssetOwnershipContract";
        return ((source.equals(coordinator) || source.startsWith(coordinator + "$"))
                && isT20MeasurementOwnerType(target))
            || ((source.equals(lifecycle) || source.startsWith(lifecycle+"$")
                ||source.equals(jdbc)||source.startsWith(jdbc+"$"))
                && (isTargetRoot(target,ownerContract)||isTargetRoot(target,topologyContract)));
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
        String t20="dz.sh.hidra.modules.custody.application.service.CustodyMeasurementCaptureApplicationService";
        for (String exported : T20_MEASUREMENT_OWNER_ROOTS) {
            assertFalse(isForbiddenCrossModuleDependency(t20,exported));
            assertFalse(isForbiddenCrossModuleDependency(t20,exported+"$Evidence"));
            assertTrue(isForbiddenCrossModuleDependency(t20+"Sibling",exported));
            assertTrue(isForbiddenCrossModuleDependency(t20,exported+"Sibling"));
            assertTrue(isForbiddenCrossModuleDependency(
                    "dz.sh.hidra.modules.planning.application.service.Other",exported));
        }
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
        String consumer = "dz.sh.hidra.modules.organization.infrastructure.security.OrganizationFoundationAuthorizationAdapter";
        String eligibility = "dz.sh.hidra.modules.identity.application.contract.organization.OrganizationIdentityUserEligibility";
        assertFalse(isForbiddenCrossModuleDependency(consumer, eligibility));
        assertFalse(isForbiddenCrossModuleDependency(consumer, eligibility + "$Eligibility"));
        assertTrue(isForbiddenCrossModuleDependency(consumer, eligibility + "$Other"));
        assertTrue(isForbiddenCrossModuleDependency(consumer, eligibility + "Sibling"));
        assertTrue(isForbiddenCrossModuleDependency(
                "dz.sh.hidra.modules.organization.application.service.Other", eligibility));
        assertTrue(isForbiddenCrossModuleDependency(
                "dz.sh.hidra.modules.topology.application.service.Other", eligibility));
        assertTrue(isForbiddenCrossModuleDependency(consumer,
                "dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase"));
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
                if (hasT20MeasurementOwnerNamePrefix(target.getName())) {
                    if (!isT20MeasurementOwnerExport(source.getName(), target.getName()))
                        violations.add(source.getName() + " -> " + target.getName());
                    continue;
                }
                if (t31OwnerRoot(target.getName())) {
                    if (!t31OwnerExport(source.getName(),target.getName()))
                        violations.add(source.getName()+" -> unauthorized T31 owner "+target.getName());
                    continue;
                }
                if (t28OwnerRoot(target.getName())) {
                    if (!t28Export(source.getName(),target.getName()))
                        violations.add(source.getName()+" -> "+target.getName());
                    continue;
                }
                if (t27OwnerRoot(target.getName())) {
                    if (!t27Export(source.getName(),target.getName()))
                        violations.add(source.getName()+" -> "+target.getName());
                    continue;
                }
                if (T26_OWNER_ROOTS.stream().anyMatch(target.getName()::startsWith)) {
                    if(!t26Export(source.getName(),target.getName()))
                        violations.add(source.getName()+" -> "+target.getName());
                    continue;
                }
                if (T25_OWNER_ROOTS.stream().anyMatch(target.getName()::startsWith)) {
                    if (!t25Export(source.getName(),target.getName()))
                        violations.add(source.getName()+" -> "+target.getName());
                    continue;
                }
                if (t26Export(source.getName(),target.getName())) continue;
                if (t25Export(source.getName(),target.getName())) continue;
                if (isExplicitlyExported(target.getPackageName())) {
                    continue;
                }
                if (isTransitionallyAllowed(source.getName(), target.getName())) {
                    continue;
                }
                if (isBaselineOwnerExport(source.getName(), target.getName())) continue;
                if (isOrganizationOwnerExport(source.getName(), target.getName())) continue;
                if (isOrganizationFoundationIdentityEligibilityExport(source.getName(), target.getName())) continue;
                if (isCustodyProductOwnerExport(source.getName(), target.getName())) continue;
                if (isManufacturerOwnerExport(source.getName(), target.getName())) continue;
                if (isCharacteristicManufacturerPartyExport(source.getName(),target.getName())) continue;
                if (isAlarmOwnerExport(source.getName(),target.getName())) continue;
                if (isAlarmMonitoringEvidenceExport(source.getName(),target.getName())) continue;
                if (isLeakT13OwnerExport(source.getName(),target.getName())) continue;
                if (isT14OwnerExport(source.getName(),target.getName())) continue;
                if (isTelemetryNativeOwnerExport(source.getName(),target.getName())) continue;
                if (isMonitoringRuleOwnerExport(source.getName(),target.getName())) continue;
                // Only the two T24 Documents owner contracts may be consumed by this exact adapter.
                if (t24DocumentOwnerExport(source.getName(),target.getName())) continue;
                if (isT17OwnerExport(source.getName(),target.getName())) continue;

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

        assertFalse(
                isForbiddenCrossModuleDependency(
                        "dz.sh.hidra.modules.workflow.application.service.WorkflowTransitionApplicationService",
                        "dz.sh.hidra.modules.planning.application.contract.workflow.PlanningRevisionWorkflowAuthorityContract"),
                "Only the explicit Planning reviewer authority contract may cross into Workflow.");

        assertTrue(
                isForbiddenCrossModuleDependency(
                        "dz.sh.hidra.modules.workflow.application.service.WorkflowTransitionApplicationService",
                        "dz.sh.hidra.modules.planning.infrastructure.persistence.repository.PlanRevisionJpaRepository"),
                "Workflow must not access Planning persistence or private application objects.");

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
        String t12Consumer = "dz.sh.hidra.modules.alarm.infrastructure.integration.MonitoringEvaluationEvidenceAdapter";
        String t12Export = "dz.sh.hidra.modules.monitoring.application.contract.alarm.AlarmMonitoringEvaluationContract";
        assertFalse(isForbiddenCrossModuleDependency(t12Consumer,t12Export));
        assertFalse(isForbiddenCrossModuleDependency(t12Consumer,t12Export + "$Evaluation"));
        assertTrue(isForbiddenCrossModuleDependency(t12Consumer + "Sibling",t12Export));
        assertTrue(isForbiddenCrossModuleDependency(t12Consumer,t12Export + "Sibling"));
        // T13: exact consumer and nested owner projection, never any sibling or private adapter.
        String t13Consumer="dz.sh.hidra.modules.leakdetection.infrastructure.integration.LeakMonitoringEvidenceAdapter";
        String t13Monitoring="dz.sh.hidra.modules.monitoring.application.contract.leakdetection.LeakDetectionMonitoringEvaluationContract";
        String t13Authorization="dz.sh.hidra.modules.leakdetection.infrastructure.security.LeakCandidateResourceAuthorizationAdapter";
        String t13Identity="dz.sh.hidra.modules.identity.application.contract.leakdetection.LeakDetectionScopedAuthorizationContract";
        assertFalse(isForbiddenCrossModuleDependency(t13Consumer,t13Monitoring));
        assertFalse(isForbiddenCrossModuleDependency(t13Consumer,t13Monitoring+"$Evaluation"));
        assertFalse(isForbiddenCrossModuleDependency(t13Authorization,t13Identity));
        assertTrue(isForbiddenCrossModuleDependency(t13Consumer+"Sibling",t13Monitoring));
        assertTrue(isForbiddenCrossModuleDependency(t13Consumer,t13Monitoring+"Sibling"));
        assertTrue(isForbiddenCrossModuleDependency(t13Authorization+"Sibling",t13Identity));
        // HAPI-930 exact root and nested type, with negative sibling/private/foreign cases.
        String alarmConsumer = "dz.sh.hidra.modules.alarm.infrastructure.security.AlarmResourceAuthorizationAdapter";
        String ownerRoot = "dz.sh.hidra.modules.topology.application.contract.alarm.AlarmTopologyOwnershipContract";
        String identityRoot = "dz.sh.hidra.modules.identity.application.contract.alarm.AlarmScopedAuthorizationContract";
        assertFalse(isForbiddenCrossModuleDependency(alarmConsumer, ownerRoot));
        assertFalse(isForbiddenCrossModuleDependency(alarmConsumer, ownerRoot + "$OwnedAsset"));
        assertFalse(isForbiddenCrossModuleDependency(alarmConsumer, identityRoot));
        assertTrue(isForbiddenCrossModuleDependency(alarmConsumer + "Sibling", ownerRoot));
        assertTrue(isForbiddenCrossModuleDependency(alarmConsumer, identityRoot + "Sibling"));
        assertTrue(isForbiddenCrossModuleDependency(alarmConsumer,
                "dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity"));
        assertTrue(isForbiddenCrossModuleDependency(
                "dz.sh.hidra.modules.organization.application.service.Other", identityRoot));
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
    private static final String MANUFACTURER_COORDINATOR =
            "dz.sh.hidra.modules.simulation.application.service.ProtectedManufacturerCharacteristicAdmissionCoordinator";
    private static final Set<String> MANUFACTURER_OWNER_ROOTS=Set.of(
            "dz.sh.hidra.modules.party.application.contract.simulation.SimulationManufacturerEvidenceContract",
            "dz.sh.hidra.modules.identity.application.contract.simulation.SimulationManufacturerCharacteristicAuthorizationContract");
    @Test void characteristicSourceExportRemainsExactAndOwnerPrivate() {
        String producer="dz.sh.hidra.modules.simulation.infrastructure.integration.TopologyManufacturerCharacteristicReferenceAdapter";
        String party="dz.sh.hidra.modules.party.application.contract.simulation.SimulationManufacturerEvidenceContract";
        String topology="dz.sh.hidra.modules.simulation.application.contract.topology.TopologyManufacturerCharacteristicReferenceContract";
        assertFalse(isForbiddenCrossModuleDependency(producer,party));
        assertFalse(isForbiddenCrossModuleDependency(producer+"$Ticket",party+"$Admission"));
        assertTrue(isForbiddenCrossModuleDependency(producer+"Sibling",party));
        assertTrue(isForbiddenCrossModuleDependency("dz.sh.hidra.modules.topology.application.service.Other",party));
        assertFalse(isForbiddenCrossModuleDependency("dz.sh.hidra.modules.topology.application.service.ProtectedInstalledCharacteristicLinkAdmissionCoordinator",topology));
        assertTrue(isForbiddenCrossModuleDependency("dz.sh.hidra.modules.topology.application.service.Other",
                "dz.sh.hidra.modules.simulation.infrastructure.integration.TopologyManufacturerCharacteristicReferenceAdapter"));
    }
    @Test void manufacturerNativeAndPartyOwnerExportsRemainExact() {
        for(String root:MANUFACTURER_OWNER_ROOTS){
            assertFalse(isForbiddenCrossModuleDependency(MANUFACTURER_COORDINATOR,root));
            assertTrue(isForbiddenCrossModuleDependency(MANUFACTURER_COORDINATOR+"Other",root));
            assertTrue(isForbiddenCrossModuleDependency("dz.sh.hidra.modules.simulation.application.service.Other",root));
            assertTrue(isForbiddenCrossModuleDependency(MANUFACTURER_COORDINATOR,
                root.replace(".application.contract.simulation.",".infrastructure.persistence.")));
        }
    }
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

    private static boolean isBaselineOwnerExport(String source,String target){
        String coordinator="dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringBaselineAdmissionCoordinator";
        String publication="dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringBaselinePublicationCoordinator";
        String reader="dz.sh.hidra.modules.topology.infrastructure.integration.EngineeringBaselineSourceAdapter";
        String identity="dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract";
        String product="dz.sh.hidra.modules.custody.application.contract.topology.TopologyBaselineProductSourceContract";
        String manufacturer="dz.sh.hidra.modules.simulation.application.contract.topology.TopologyManufacturerCharacteristicReferenceContract";
        return ((source.equals(coordinator)||source.startsWith(coordinator+"$")
                ||source.equals(publication)||source.startsWith(publication+"$"))&&(target.equals(identity)||target.startsWith(identity+"$")))
            || ((source.equals(reader)||source.startsWith(reader+"$"))&&(target.equals(product)||target.startsWith(product+"$")||target.equals(manufacturer)||target.startsWith(manufacturer+"$")));
    }
    @Test void baselineOwnerExportsRemainExactAndOpaque(){
        String coordinator="dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringBaselineAdmissionCoordinator";
        String publication="dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringBaselinePublicationCoordinator";
        String reader="dz.sh.hidra.modules.topology.infrastructure.integration.EngineeringBaselineSourceAdapter";
        for(String root:List.of("dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract",
                "dz.sh.hidra.modules.custody.application.contract.topology.TopologyBaselineProductSourceContract")){
            String owner=root.contains(".identity.")?coordinator:reader;
            assertFalse(isForbiddenCrossModuleDependency(owner,root));
            assertFalse(isForbiddenCrossModuleDependency(owner+"$Ticket",root+"$Admission"));
            assertTrue(isForbiddenCrossModuleDependency(owner+"Sibling",root));
            assertTrue(isForbiddenCrossModuleDependency(owner,root+"Sibling"));
            assertTrue(isForbiddenCrossModuleDependency("dz.sh.hidra.modules.planning.application.service.Other",root));
        }
        assertFalse(isForbiddenCrossModuleDependency(publication, "dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract"));
        assertTrue(isForbiddenCrossModuleDependency(publication+"Sibling", "dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract"));
        assertTrue(isForbiddenCrossModuleDependency(coordinator,"dz.sh.hidra.modules.identity.infrastructure.security.ProtectedEngineeringBaselineAuthorizationParticipant"));
        assertTrue(isForbiddenCrossModuleDependency(reader,"dz.sh.hidra.modules.custody.infrastructure.integration.TopologyBaselineProductSourceAdapter"));
    }
    /**
     * HAPI-930: only Alarm's reviewed infrastructure coordinator can import exactly the
     * two owner-exported Alarm contracts and their nested scalar projection types.
     * No other consumer, sibling type or owner infrastructure is made public.
     */

    /** Only the exact T14 adapter may consume the named scalar owner projection. */
    @Test void t14OwnerExportsAreExactAndExcludeSiblings() {
        String reader="dz.sh.hidra.modules.incident.infrastructure.integration.IncidentEscalationSourceAdapter";
        String owner="dz.sh.hidra.modules.alarm.application.contract.incident.IncidentAlarmEvidenceContract";
        assertFalse(isForbiddenCrossModuleDependency(reader,owner));
        assertFalse(isForbiddenCrossModuleDependency(reader,owner+"$Evidence"));
        assertTrue(isForbiddenCrossModuleDependency(reader+"Sibling",owner));
        assertTrue(isForbiddenCrossModuleDependency(reader,owner+"Sibling"));
        String auth="dz.sh.hidra.modules.incident.infrastructure.security.IncidentEscalationAuthorizationAdapter";
        String identity="dz.sh.hidra.modules.identity.application.contract.incident.IncidentScopedAuthorizationContract";
        assertFalse(isForbiddenCrossModuleDependency(auth,identity));
        assertTrue(isForbiddenCrossModuleDependency(auth+"Sibling",identity));
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
        return consumer.equals(source) && (owner.equals(target) || target.startsWith(owner + "$"));
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

    /** HAPI-933: only the reviewed Telemetry scope adapter may import these exact two owner contracts. */
    private static boolean isTelemetryNativeOwnerExport(String source, String target) {
        String consumer="dz.sh.hidra.modules.telemetry.infrastructure.security.TelemetryReadingNativeScopeAdapter";
        if(!consumer.equals(source))return false;
        return java.util.Set.of(
                "dz.sh.hidra.modules.identity.application.contract.telemetry.TelemetryScopedAuthorizationContract",
                "dz.sh.hidra.modules.topology.application.contract.telemetry.TelemetryTopologyOwnershipContract")
                .stream().anyMatch(root->target.equals(root)||target.startsWith(root+"$"));
    }

    /** T10: admit only the reviewed Monitoring adapter's two exact owner contract roots. */
    private static boolean isMonitoringRuleOwnerExport(String source,String target) {
        String consumer="dz.sh.hidra.modules.monitoring.infrastructure.security.MonitoringRuleNativeScopeAdapter";
        if(!consumer.equals(source))return false;
        return java.util.Set.of(
                "dz.sh.hidra.modules.identity.application.contract.monitoring.MonitoringRuleScopedAuthorizationContract",
                "dz.sh.hidra.modules.topology.application.contract.monitoring.MonitoringRuleAssetOwnershipContract")
                .stream().anyMatch(exported->target.equals(exported)||target.startsWith(exported+"$"));
    }

    @Test void t21ScopedReadsUseLockCapableTransactionsForLiveTopologyOwnership() throws Exception {
        var type=Class.forName(
            "dz.sh.hidra.modules.custody.application.service.CustodyTransferLifecycleApplicationService");
        for(String name:java.util.List.of("period","reconciliations","reconciliation",
                "ticket","history","discrepancies","discrepancy")){
            var method=java.util.Arrays.stream(type.getDeclaredMethods())
                .filter(m->m.getName().equals(name)).findFirst().orElseThrow();
            var transaction=method.getAnnotation(org.springframework.transaction.annotation.Transactional.class);
            assertNotNull(transaction,()->"Missing T21 transaction for "+name);
            assertFalse(transaction.readOnly(),
                ()->"T21 live Topology FOR SHARE ownership requires a lock-capable transaction: "+name);
        }
    }

    @Test void t21CustodyLifecycleTransactionalComponentsRemainSpringProxyable() throws ClassNotFoundException {
        for(String component: java.util.List.of(
                "dz.sh.hidra.modules.custody.application.service.CustodyTransferLifecycleApplicationService",
                "dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.JdbcCustodyTransferLifecycleAdapter",
                "dz.sh.hidra.modules.workflow.application.service.CustodyTransferTicketApprovalEvidenceQueryService")) {
            Class<?> type=Class.forName(component);
            assertFalse(java.lang.reflect.Modifier.isFinal(type.getModifiers()),
                    ()->"T21 transactional component cannot be proxied: "+component);
        }
    }

    @Test void t10TransactionalOwnerServicesStayProxyable() throws ClassNotFoundException {
        for(String component: java.util.List.of(
                "dz.sh.hidra.modules.identity.application.service.MonitoringRuleScopedAuthorizationQueryService",
                "dz.sh.hidra.modules.monitoring.application.service.MonitoringRuleApplicationService",
                "dz.sh.hidra.modules.monitoring.application.service.MonitoringRuleLifecycleApplicationService",
                "dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter.JdbcMonitoringRuleRevisionAdapter",
                "dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter.JpaMonitoringRulePointEligibilityAdapter")) {
            var type=Class.forName(component);
            assertFalse(java.lang.reflect.Modifier.isFinal(type.getModifiers()),
                    ()->"T10 transactional component cannot be proxied: "+component);
        }
    }

    @Test void monitoringRuleOwnerExportsStayExactAndPrivate() {
        String source="dz.sh.hidra.modules.monitoring.infrastructure.security.MonitoringRuleNativeScopeAdapter";
        String identity="dz.sh.hidra.modules.identity.application.contract.monitoring.MonitoringRuleScopedAuthorizationContract";
        String topology="dz.sh.hidra.modules.topology.application.contract.monitoring.MonitoringRuleAssetOwnershipContract";
        assertFalse(isForbiddenCrossModuleDependency(source,identity));
        assertFalse(isForbiddenCrossModuleDependency(source,identity+"$Operation"));
        assertFalse(isForbiddenCrossModuleDependency(source,topology));
        assertFalse(isForbiddenCrossModuleDependency(source,topology+"$OwnedAsset"));
        assertTrue(isForbiddenCrossModuleDependency(source+"Sibling",identity));
        assertTrue(isForbiddenCrossModuleDependency(source,topology+"Sibling"));
        assertTrue(isForbiddenCrossModuleDependency(source,
                "dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity"));
    }

    @Test
    void telemetryNativeAuthorizationContractsRemainExactAndOwnerPrivate() {
        String consumer="dz.sh.hidra.modules.telemetry.infrastructure.security.TelemetryReadingNativeScopeAdapter";
        String owner="dz.sh.hidra.modules.topology.application.contract.telemetry.TelemetryTopologyOwnershipContract";
        String rights="dz.sh.hidra.modules.identity.application.contract.telemetry.TelemetryScopedAuthorizationContract";
        assertFalse(isForbiddenCrossModuleDependency(consumer,owner));
        assertFalse(isForbiddenCrossModuleDependency(consumer,owner+"$OwnedAsset"));
        assertFalse(isForbiddenCrossModuleDependency(consumer,rights));
        assertTrue(isForbiddenCrossModuleDependency(consumer+"Sibling",rights));
        assertTrue(isForbiddenCrossModuleDependency(consumer,owner+"Sibling"));
        assertTrue(isForbiddenCrossModuleDependency("dz.sh.hidra.modules.alarm.infrastructure.security.Other",rights));
        assertTrue(isForbiddenCrossModuleDependency(consumer,
                "dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity"));
        assertTrue(isForbiddenCrossModuleDependency(consumer,
                "dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JpaTelemetryExplicitGrantEvidenceAdapter"));
    }

    private static boolean isT18MaintenanceContract(String target) {
        return java.util.Set.of(
                "dz.sh.hidra.modules.integrity.application.contract.assets.ApprovedMaintenanceHandoffContract",
                "dz.sh.hidra.modules.identity.application.contract.integrity.IntegrityMaintenanceScopedAuthorizationContract",
                "dz.sh.hidra.modules.identity.application.contract.assets.AssetsMaintenanceScopedAuthorizationContract",
                "dz.sh.hidra.modules.topology.application.contract.integrity.IntegrityMaintenanceTopologyOwnershipContract")
                .stream().anyMatch(root->target.startsWith(root));
    }
    private static boolean isT18MaintenanceConsumer(String source,String target) {
        String owner="dz.sh.hidra.modules.integrity.application.service.IntegrityMaintenanceApprovalApplicationService";
        String assets="dz.sh.hidra.modules.assets.application.service.IntegrityWorkOrderApplicationService";
        String validation="dz.sh.hidra.modules.assets.infrastructure.persistence.adapter.MaintenanceWorkOrderReferenceValidation";
        String approval="dz.sh.hidra.modules.integrity.application.contract.assets.ApprovedMaintenanceHandoffContract";
        String integrityAuth="dz.sh.hidra.modules.identity.application.contract.integrity.IntegrityMaintenanceScopedAuthorizationContract";
        String assetsAuth="dz.sh.hidra.modules.identity.application.contract.assets.AssetsMaintenanceScopedAuthorizationContract";
        String topology="dz.sh.hidra.modules.topology.application.contract.integrity.IntegrityMaintenanceTopologyOwnershipContract";
        boolean match=(target.equals(approval)||target.startsWith(approval+"$"))
                    &&(source.equals(assets)||source.startsWith(assets+"$")
                        ||source.equals(validation)||source.startsWith(validation+"$"));
        match|=(target.equals(integrityAuth)||target.startsWith(integrityAuth+"$"))
                    &&(source.equals(owner)||source.startsWith(owner+"$"));
        match|=(target.equals(assetsAuth)||target.startsWith(assetsAuth+"$"))
                    &&(source.equals(assets)||source.startsWith(assets+"$"));
        match|=(target.equals(topology)||target.startsWith(topology+"$"))
                    &&(source.equals(owner)||source.startsWith(owner+"$"));
        return match;
    }
    @Test void t18CrossOwnerCapabilitiesDoNotBecomeGeneralExports() {
        String source="dz.sh.hidra.modules.assets.application.service.IntegrityWorkOrderApplicationService";
        String target="dz.sh.hidra.modules.integrity.application.contract.assets.ApprovedMaintenanceHandoffContract";
        org.junit.jupiter.api.Assertions.assertFalse(isForbiddenCrossModuleDependency(source,target));
        org.junit.jupiter.api.Assertions.assertFalse(isForbiddenCrossModuleDependency(
                "dz.sh.hidra.modules.assets.infrastructure.persistence.adapter.MaintenanceWorkOrderReferenceValidation",target));
        org.junit.jupiter.api.Assertions.assertTrue(isForbiddenCrossModuleDependency(source+"Sibling",target));
        org.junit.jupiter.api.Assertions.assertTrue(isForbiddenCrossModuleDependency(source,target+"Sibling"));
        org.junit.jupiter.api.Assertions.assertTrue(isForbiddenCrossModuleDependency(
                "dz.sh.hidra.modules.assets.application.service.Any", 
                "dz.sh.hidra.modules.identity.application.contract.integrity.IntegrityMaintenanceScopedAuthorizationContract"));
        // Keep T18 transactional participants suitable for Spring class-based AOP.
        for (Class<?> bean : java.util.List.of(
                dz.sh.hidra.modules.integrity.application.service.IntegrityMaintenanceApprovalApplicationService.class,
                dz.sh.hidra.modules.assets.application.service.IntegrityWorkOrderApplicationService.class,
                dz.sh.hidra.modules.assets.infrastructure.persistence.adapter.JdbcIntegrityWorkOrderAdapter.class,
                dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter.JdbcIntegrityMaintenanceApprovalAdapter.class,
                dz.sh.hidra.modules.identity.application.service.MaintenanceScopedAuthorizationQueryService.class,
                dz.sh.hidra.modules.topology.infrastructure.reference.JpaIntegrityMaintenanceTopologyOwnershipAdapter.class)) {
            org.junit.jupiter.api.Assertions.assertFalse(
                    java.lang.reflect.Modifier.isFinal(bean.getModifiers()),
                    "T18 transactional bean must be proxyable: " + bean.getName());
        }
    }

    private static boolean isT23OwnerExport(String source,String target) {
        final String consumer="dz.sh.hidra.modules.analytics.infrastructure.integration.DeviationMetricOwnerAccessAdapter";
        return source.equals(consumer) && java.util.Set.of(
                "dz.sh.hidra.modules.identity.application.contract.analytics.AnalyticsMetricScopedAuthorizationContract",
                "dz.sh.hidra.modules.monitoring.application.contract.analytics.AnalyticsDeviationSourceContract")
                .stream().anyMatch(root->target.equals(root)||target.startsWith(root+"$"));
    }
    @Test void t23OwnerExportsAreExclusiveToAnalyticsAdapter() {
        String consumer="dz.sh.hidra.modules.analytics.infrastructure.integration.DeviationMetricOwnerAccessAdapter";
        for(String root:java.util.Set.of(
                "dz.sh.hidra.modules.identity.application.contract.analytics.AnalyticsMetricScopedAuthorizationContract",
                "dz.sh.hidra.modules.monitoring.application.contract.analytics.AnalyticsDeviationSourceContract")) {
            assertFalse(isForbiddenCrossModuleDependency(consumer,root));
            assertFalse(isForbiddenCrossModuleDependency(consumer,root+"$Evaluation"));
            assertTrue(isForbiddenCrossModuleDependency(consumer+"Sibling",root));
            assertTrue(isForbiddenCrossModuleDependency(consumer,root+"Sibling"));
            assertTrue(isForbiddenCrossModuleDependency(consumer,root.replace(".application.contract.analytics.",".infrastructure.persistence.")));
        }
    }
    private static final String T26_BRIDGE =
            "dz.sh.hidra.modules.integration.infrastructure.integration.SyntheticExchangeOwnerAccessAdapter";
    private static final Set<String> T26_OWNER_ROOTS = Set.of(
            "dz.sh.hidra.modules.identity.application.contract.integration.IntegrationSyntheticExchangeAuthorizationContract",
            "dz.sh.hidra.modules.topology.application.contract.integration.IntegrationSyntheticFacilityOwnershipContract",
            "dz.sh.hidra.modules.documents.application.contract.integration.IntegrationSyntheticDraftReceiptContract");
    private static boolean t26Export(String source,String target) {
        return T26_BRIDGE.equals(source) && T26_OWNER_ROOTS.stream()
                .anyMatch(root->target.equals(root)||target.startsWith(root+"$"));
    }
    @Test void t26ExactScalarOwnerImportsExcludeSiblingsAndForeignPersistence() {
        for(String root:T26_OWNER_ROOTS) {
            assertTrue(t26Export(T26_BRIDGE,root));
            assertTrue(t26Export(T26_BRIDGE,root+"$Facility"));
            assertFalse(t26Export(T26_BRIDGE+"Sibling",root));
            assertFalse(t26Export(T26_BRIDGE,root+"Sibling"));
            assertTrue(isForbiddenCrossModuleDependency(T26_BRIDGE+"Sibling",root));
            assertTrue(isForbiddenCrossModuleDependency(T26_BRIDGE,root+"Sibling"));
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
            return target.equals(T25_TARGET_CONTRACT)||target.startsWith(T25_TARGET_CONTRACT+"$");
        return T25_OWNER_ROOTS.stream().filter(root->target.equals(root)||target.startsWith(root+"$"))
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
        // T24 methods use class-based Spring transaction proxies.
        for(Class<?> service:java.util.List.of(
                dz.sh.hidra.modules.documents.application.service.AuditDocumentReferenceQueryService.class,
                dz.sh.hidra.modules.documents.application.service.ReportingDocumentReferenceQueryService.class,
                dz.sh.hidra.modules.documents.application.service.ControlledDocumentAccessService.class,
                dz.sh.hidra.modules.documents.application.service.ControlledDocumentsApplicationService.class,
                dz.sh.hidra.modules.identity.application.service.ControlledDocumentScopedAuthorizationQueryService.class,
                dz.sh.hidra.modules.documents.infrastructure.persistence.adapter.JdbcControlledDocumentRepositoryAdapter.class)){
            assertFalse(java.lang.reflect.Modifier.isFinal(service.getModifiers()),
                    "T24 transactional bean must be proxyable: "+service.getName());
        }
    }

    private static final String T17_HSE_ADAPTER =
            "dz.sh.hidra.modules.hse.infrastructure.integration.HseCapaOwnerAccessAdapter";
    private static final Set<String> T17_OWNER_ROOTS = Set.of(
            "dz.sh.hidra.modules.identity.application.contract.hse.HseCapaScopedAuthorizationContract",
            "dz.sh.hidra.modules.topology.application.contract.hse.HseFacilityOwnershipContract",
            "dz.sh.hidra.modules.incident.application.contract.hse.HseIncidentSourceContract",
            "dz.sh.hidra.modules.documents.application.contract.hse.HseControlledDocumentEvidenceContract",
            "dz.sh.hidra.modules.workflow.application.contract.hse.HseCapaDecisionContract");
    private static boolean isT17OwnerContract(String target) {
        return T17_OWNER_ROOTS.stream().anyMatch(root ->
                target.equals(root)||target.startsWith(root+"$"));
    }
    private static boolean isT17OwnerExport(String source,String target) {
        return T17_HSE_ADAPTER.equals(source) && isT17OwnerContract(target);
    }
    @Test void t17OwnerEvidenceImportsAreExactAndProxyable() {
        for(String root:T17_OWNER_ROOTS) {
            assertTrue(isT17OwnerExport(T17_HSE_ADAPTER,root));
            assertTrue(isT17OwnerExport(T17_HSE_ADAPTER,root+"$Evidence"));
            assertFalse(isT17OwnerExport(T17_HSE_ADAPTER+"Sibling",root));
            assertFalse(isT17OwnerExport(T17_HSE_ADAPTER,root+"Sibling"));
            assertTrue(isForbiddenCrossModuleDependency(T17_HSE_ADAPTER+"Sibling",root));
            assertTrue(isForbiddenCrossModuleDependency(T17_HSE_ADAPTER,root+"Sibling"));
            assertFalse(isForbiddenCrossModuleDependency(T17_HSE_ADAPTER,root));
        }
        for(Class<?> type:java.util.List.of(
                dz.sh.hidra.modules.hse.application.service.HseCapaLifecycleApplicationService.class,
                dz.sh.hidra.modules.hse.infrastructure.persistence.adapter.JdbcHseCapaLifecycleStoreAdapter.class,
                dz.sh.hidra.modules.identity.application.service.HseCapaScopedAuthorizationQueryService.class,
                dz.sh.hidra.modules.topology.application.service.HseFacilityOwnershipQueryService.class,
                dz.sh.hidra.modules.incident.application.service.HseIncidentSourceQueryService.class,
                dz.sh.hidra.modules.documents.application.service.HseControlledDocumentEvidenceQueryService.class,
                dz.sh.hidra.modules.workflow.application.service.HseCapaDecisionQueryService.class))
            assertFalse(java.lang.reflect.Modifier.isFinal(type.getModifiers()),
                    "T17 transactional bean must be Spring proxyable: "+type.getName());
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
        if (t31OwnerRoot(targetClassName))
            return !t31OwnerExport(sourceClassName,targetClassName);
        if (t28OwnerRoot(targetClassName))
            return !t28Export(sourceClassName,targetClassName);
        if (t27OwnerRoot(targetClassName))
            return !t27Export(sourceClassName,targetClassName);
        if (T26_OWNER_ROOTS.stream().anyMatch(targetClassName::startsWith))
            return !t26Export(sourceClassName,targetClassName);
        if (t26Export(sourceClassName,targetClassName)) return false;
        if (T25_OWNER_ROOTS.stream().anyMatch(targetClassName::startsWith))
            return !t25Export(sourceClassName,targetClassName);
        if (t25Export(sourceClassName,targetClassName)) return false;
        // Deny lookalike siblings too: only exact owner root and nested exports
        // may cross into HSE's single approved adapter. Generic package exports
        // must not grant access to a similarly prefixed foreign type.
        for(String root:T17_OWNER_ROOTS)
            if(targetClassName.startsWith(root))
                return !isT17OwnerExport(sourceClassName,targetClassName);
        for(String root:java.util.Set.of(
                "dz.sh.hidra.modules.identity.application.contract.documents.ControlledDocumentScopedAuthorizationContract",
                "dz.sh.hidra.modules.topology.application.contract.documents.ControlledDocumentFacilityOwnershipContract"))
            if(targetClassName.startsWith(root))
                return !t24DocumentOwnerExport(sourceClassName,targetClassName);
        for(String root:java.util.Set.of(
                "dz.sh.hidra.modules.identity.application.contract.analytics.AnalyticsMetricScopedAuthorizationContract",
                "dz.sh.hidra.modules.monitoring.application.contract.analytics.AnalyticsDeviationSourceContract"))
            if(targetClassName.startsWith(root))return !isT23OwnerExport(sourceClassName,targetClassName);
        if (isT18MaintenanceContract(targetClassName)) {
            return !isT18MaintenanceConsumer(sourceClassName,targetClassName);
        }
        if(isBaselineOwnerExport(sourceClassName,targetClassName))return false;
        if(isAlarmOwnerExport(sourceClassName,targetClassName))return false;
        if(isAlarmMonitoringEvidenceExport(sourceClassName,targetClassName))return false;
        if(isLeakT13OwnerExport(sourceClassName,targetClassName))return false;
        if(isT14ProtectedOwnerType(targetClassName))return !isT14OwnerExport(sourceClassName,targetClassName);
        if(isT14OwnerExport(sourceClassName,targetClassName))return false;
        if(isTelemetryNativeOwnerExport(sourceClassName,targetClassName))return false;
        if(isMonitoringRuleOwnerExport(sourceClassName,targetClassName))return false;
        // HAPI-605: generic exported-package allowances must not admit unauthorized
        // consumers of the three new source-owner contracts (or nested credentials).
        if(List.of(
                "dz.sh.hidra.modules.identity.application.contract.topology.TopologyEngineeringBaselineAuthorizationContract",
                "dz.sh.hidra.modules.custody.application.contract.topology.TopologyBaselineProductSourceContract",
                "dz.sh.hidra.modules.simulation.application.contract.topology.TopologyManufacturerCharacteristicReferenceContract")
                .stream().anyMatch(targetClassName::startsWith)) {
            // HAPI-602 already authorized this genuine characteristic-link consumer.
            // HAPI-605 must not revoke that existing exact owner contract while
            // rejecting unrelated consumers and lookalike sibling classes.
            String manufacturer="dz.sh.hidra.modules.simulation.application.contract.topology.TopologyManufacturerCharacteristicReferenceContract";
            String existing="dz.sh.hidra.modules.topology.application.service.ProtectedInstalledCharacteristicLinkAdmissionCoordinator";
            if ((sourceClassName.equals(existing)||sourceClassName.startsWith(existing+"$"))
                    && (targetClassName.equals(manufacturer)||targetClassName.startsWith(manufacturer+"$")))
                return false;
            return true;
        }
        // Simulation's exact Topology source reader may use its existing Party owner contract.
        // No sibling, general Simulation class or Topology port receives that private export.
        String partyRoot="dz.sh.hidra.modules.party.application.contract.simulation.SimulationManufacturerEvidenceContract";
        String simulationReader="dz.sh.hidra.modules.simulation.infrastructure.integration.TopologyManufacturerCharacteristicReferenceAdapter";
        if(targetClassName.equals(partyRoot)||targetClassName.startsWith(partyRoot+"$"))
            return !(sourceClassName.equals(simulationReader)
                    || sourceClassName.startsWith(simulationReader+"$")
                    || isManufacturerOwnerExport(sourceClassName,targetClassName)
                    || isCharacteristicManufacturerPartyExport(sourceClassName,targetClassName));
        for(String root:MANUFACTURER_OWNER_ROOTS)if(targetClassName.equals(root)
                || targetClassName.startsWith(root+"$"))
            return !(sourceClassName.equals(MANUFACTURER_COORDINATOR)
                || sourceClassName.startsWith(MANUFACTURER_COORDINATOR+"$"));
        for(String root:INSTALLED_UNIT_OWNER_ROOTS)if(targetClassName.startsWith(root))
            return !(targetClassName.equals(root) || targetClassName.startsWith(root+"$"))
                || !sourceClassName.equals(INSTALLED_UNIT_COORDINATOR) && !sourceClassName.startsWith(INSTALLED_UNIT_COORDINATOR+"$");
        // B3-S2 exports ONLY the native Identity contract root/nested records to its
        // exact compiled Topology coordinator. The repository port/adapter, siblings
        // and all private Identity participants remain forbidden.
        String configurationRoot="dz.sh.hidra.modules.identity.application.contract.topology.TopologyStationConfigurationAuthorizationContract";
        if(targetClassName.startsWith(configurationRoot)) {
            String reviewed="dz.sh.hidra.modules.topology.application.service.ProtectedStationConfigurationAdmissionCoordinator";
            return !(targetClassName.equals(configurationRoot) || targetClassName.startsWith(configurationRoot+"$"))
                || !(sourceClassName.equals(reviewed) || sourceClassName.startsWith(reviewed+"$"));
        }
        if(isProductPublicationSourceType(targetClassName))return true;
        if(isPublicationSpecific(targetClassName))return !isPublicationExportAllowed(sourceClassName,targetClassName);
        if (hasT20MeasurementOwnerNamePrefix(targetClassName))
            return !isT20MeasurementOwnerExport(sourceClassName,targetClassName);
        if (!isPrivateCrossModulePackage(targetPackage)) {
            return false;
        }
        if (isExplicitlyExported(targetPackage)) {
            return false;
        }
        if (isOrganizationOwnerExport(sourceClassName, targetClassName)) return false;
        if (isOrganizationFoundationIdentityEligibilityExport(sourceClassName, targetClassName)) return false;
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
        String approved="dz.sh.hidra.modules.topology.application.service.ProtectedStationConfigurationAdmissionCoordinator";
        assertFalse(isForbiddenCrossModuleDependency(approved,root));
        assertFalse(isForbiddenCrossModuleDependency(approved+"$Admission",root+"$Contribution"));
        assertTrue(isForbiddenCrossModuleDependency(approved+"Sibling",root));
        assertTrue(isForbiddenCrossModuleDependency("dz.sh.hidra.modules.topology.application.port.out.StationInstalledConfigurationRepositoryPort",root));
        assertTrue(isForbiddenCrossModuleDependency(approved,
            "dz.sh.hidra.modules.identity.infrastructure.security.ProtectedTopologyStationConfigurationAuthorizationParticipant"));
        var group=dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant.StationConfigurationClaimGroup.class;
        assertEquals(0,group.getConstructors().length);
        for(var constructor:group.getDeclaredConstructors())assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        for(var field:group.getDeclaredFields())assertTrue(java.lang.reflect.Modifier.isPrivate(field.getModifiers()));
        assertFalse(dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant.InstalledUnitClaimGroup.class.isAssignableFrom(group));
    }

    @Test void trustedNativeRoutesCannotBeSelfRegisteredByBusinessModules() {
        String prefix="dz.sh.hidra.platform.permissions.";
        for(String name:Set.of("NativeProtectedOperation",
                "HidraTrustedNativeProtectedRouteRegistry",
                "HidraTrustedNativeProtectedRouteStartupValidator")) {
            var actual=PRODUCTION_CLASSES.get(prefix+name);
            assertTrue(actual.getDirectDependenciesFromSelf().stream()
                    .noneMatch(d -> d.getTargetClass().getName().startsWith(MODULE_PREFIX)));
        }
        var registry=dz.sh.hidra.platform.permissions.HidraTrustedNativeProtectedRouteRegistry.class;
        assertEquals(1,registry.getConstructors().length);
        assertEquals(0,registry.getConstructors()[0].getParameterCount());
        assertTrue(java.util.Arrays.stream(registry.getMethods())
                .noneMatch(m -> Set.of("register","authorize","grant","addTrusted").contains(m.getName())));
        var marker=dz.sh.hidra.platform.permissions.NativeProtectedOperation.class;
        assertTrue(marker.isAnnotationPresent(java.lang.annotation.Retention.class));
        assertEquals(java.lang.annotation.RetentionPolicy.RUNTIME,
                marker.getAnnotation(java.lang.annotation.Retention.class).value());
        assertEquals(java.util.Set.of(java.lang.annotation.ElementType.METHOD),
                java.util.Set.of(marker.getAnnotation(java.lang.annotation.Target.class).value()));
    }

}
