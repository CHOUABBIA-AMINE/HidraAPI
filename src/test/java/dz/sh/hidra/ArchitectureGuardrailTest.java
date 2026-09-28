/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ArchitectureGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.StreamSupport;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    private static final JavaClasses PRODUCTION_CLASSES = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("dz.sh.hidra");

    /**
     * Deliberately exported package prefixes may be added here only together with the
     * architecture decision/roadmap task that creates that public module contract.
     */
    private static final Set<String> EXPORTED_CROSS_MODULE_PACKAGE_PREFIXES = Set.of(
            "dz.sh.hidra.modules.workflow.application.contract.planning",
            "dz.sh.hidra.modules.topology.application.contract.organization"
    );

    /**
     * Exact temporary exceptions present in the forensic baseline.
     *
     * <p>Values are root class names; nested types of the same contract are covered.
     * No wildcard package exception is allowed.</p>
     */
    private static final Map<String, Set<String>> TRANSITIONAL_CROSS_MODULE_DEPENDENCIES =
            Map.of();

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
                        "dz.sh.hidra.modules.."
                )
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
                        "dz.sh.hidra.platform.."
                )
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
                        "org.springframework.data.."
                )
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
                        "org.springframework.data.."
                )
                .check(PRODUCTION_CLASSES);
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
                if (isExplicitlyExported(target.getPackageName())) {
                    continue;
                }
                if (isTransitionallyAllowed(source.getName(), target.getName())) {
                    continue;
                }

                violations.add(source.getName() + " -> " + target.getName());
            }
        }

        assertTrue(
                violations.isEmpty(),
                () -> "Business modules must not depend on another module's private "
                        + "domain/infrastructure/application packages. Violations: " + violations
        );
    }

    @Test
    void transitionalCrossModuleAllowlistMustRemainExactAndInUse() {
        List<String> missingAllowlistedDependencies = new ArrayList<>();

        TRANSITIONAL_CROSS_MODULE_DEPENDENCIES.forEach((sourceName, targetRoots) -> {
            JavaClass source = PRODUCTION_CLASSES.get(sourceName);
            Set<String> actualTargets = new HashSet<>();
            for (Dependency dependency : source.getDirectDependenciesFromSelf()) {
                actualTargets.add(dependency.getTargetClass().getName());
            }

            for (String targetRoot : targetRoots) {
                boolean present = actualTargets.stream()
                        .anyMatch(targetName -> isTargetRoot(targetName, targetRoot));
                if (!present) {
                    missingAllowlistedDependencies.add(sourceName + " -> " + targetRoot);
                }
            }
        });

        assertTrue(
                missingAllowlistedDependencies.isEmpty(),
                () -> "Remove stale transitional dependency exceptions as soon as their imports "
                        + "are refactored. Missing dependencies: " + missingAllowlistedDependencies
        );
    }

    @Test
    void crossModuleClassifierMustRejectRepresentativePrivateDependency() {
        assertTrue(
                isForbiddenCrossModuleDependency(
                        "dz.sh.hidra.modules.planning.application.service.ExampleService",
                        "dz.sh.hidra.modules.identity.domain.model.User"
                ),
                "An unlisted cross-module dependency on another module's domain must be rejected."
        );

        assertTrue(
                isForbiddenCrossModuleDependency(
                        "dz.sh.hidra.modules.planning.application.service.PlanningApprovalApplicationService",
                        "dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase"
                ),
                "Planning must no longer depend on Workflow's private application ports."
        );

        assertFalse(
                isForbiddenCrossModuleDependency(
                        "dz.sh.hidra.modules.planning.application.service.PlanningApprovalApplicationService",
                        "dz.sh.hidra.modules.workflow.application.contract.planning.PlanningWorkflowContract"
                ),
                "The deliberate Workflow contract exported to Planning must remain allowed."
        );

        assertTrue(
                isForbiddenCrossModuleDependency(
                        "dz.sh.hidra.modules.organization.infrastructure.adapter.AuthoritativeOperationalScopeTargetResolverAdapter",
                        "dz.sh.hidra.modules.topology.application.port.in.ResolveTopologyOperationalScopeTargetUseCase"
                ),
                "Organization must no longer depend on Topology's private application ports."
        );

        assertFalse(
                isForbiddenCrossModuleDependency(
                        "dz.sh.hidra.modules.organization.infrastructure.adapter.AuthoritativeOperationalScopeTargetResolverAdapter",
                        "dz.sh.hidra.modules.topology.application.contract.organization.TopologyOperationalScopeTargetContract"
                ),
                "The deliberate Topology contract exported to Organization must remain allowed."
        );
    }

    @Test
    void jpaTableNamesMustBeUnique() {
        Map<String, List<String>> ownersByTable = new HashMap<>();

        for (JavaClass type : PRODUCTION_CLASSES) {
            if (!type.isAnnotatedWith(Entity.class)) {
                continue;
            }

            Table table = type.getAnnotationOfType(Table.class);
            String tableName = table == null || table.name().isBlank()
                    ? type.getSimpleName()
                    : table.name().trim();

            ownersByTable.computeIfAbsent(tableName, ignored -> new ArrayList<>())
                    .add(type.getName());
        }

        Map<String, List<String>> duplicates = new HashMap<>();
        ownersByTable.forEach((tableName, owners) -> {
            if (owners.size() > 1) {
                duplicates.put(tableName, owners);
            }
        });

        assertTrue(
                duplicates.isEmpty(),
                () -> "JPA table names must be unique across production entities. Duplicates: "
                        + duplicates
        );
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
                String columnName = column.name().isBlank()
                        ? field.getName()
                        : column.name().trim();

                fieldsByColumn.computeIfAbsent(columnName, ignored -> new ArrayList<>())
                        .add(field.getFullName());
            }

            fieldsByColumn.forEach((columnName, fields) -> {
                if (fields.size() > 1) {
                    violations.add(
                            type.getName() + " maps column " + columnName + " from " + fields
                    );
                }
            });
        }

        assertTrue(
                violations.isEmpty(),
                () -> "A JPA entity must not map the same column name more than once. Violations: "
                        + violations
        );
    }

    @Test
    void identityAccessPackageMustNotExist() {
        boolean identityAccessExists = StreamSupport.stream(
                        PRODUCTION_CLASSES.spliterator(),
                        false
                )
                .map(JavaClass::getPackageName)
                .anyMatch(packageName ->
                        packageName.equals("dz.sh.hidra.modules.identityaccess")
                                || packageName.startsWith("dz.sh.hidra.modules.identityaccess.")
                );

        assertFalse(
                identityAccessExists,
                "Use dz.sh.hidra.modules.identity; identityaccess is a forbidden module/package."
        );
    }

    private static boolean isForbiddenCrossModuleDependency(
            String sourceClassName,
            String targetClassName
    ) {
        String sourcePackage = packageName(sourceClassName);
        String targetPackage = packageName(targetClassName);
        String sourceModule = moduleName(sourcePackage);
        String targetModule = moduleName(targetPackage);

        if (sourceModule == null || targetModule == null || sourceModule.equals(targetModule)) {
            return false;
        }
        if (!isPrivateCrossModulePackage(targetPackage)) {
            return false;
        }
        if (isExplicitlyExported(targetPackage)) {
            return false;
        }
        return !isTransitionallyAllowed(sourceClassName, targetClassName);
    }

    private static boolean isPrivateCrossModulePackage(String packageName) {
        return packageName.contains(".domain.")
                || packageName.contains(".infrastructure.")
                || packageName.contains(".application.");
    }

    private static boolean isExplicitlyExported(String packageName) {
        return EXPORTED_CROSS_MODULE_PACKAGE_PREFIXES.stream()
                .anyMatch(packageName::startsWith);
    }

    private static boolean isTransitionallyAllowed(
            String sourceClassName,
            String targetClassName
    ) {
        return TRANSITIONAL_CROSS_MODULE_DEPENDENCIES
                .getOrDefault(sourceClassName, Set.of())
                .stream()
                .anyMatch(targetRoot -> isTargetRoot(targetClassName, targetRoot));
    }

    private static boolean isTargetRoot(String targetClassName, String targetRoot) {
        return targetClassName.equals(targetRoot)
                || targetClassName.startsWith(targetRoot + "$");
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
}
