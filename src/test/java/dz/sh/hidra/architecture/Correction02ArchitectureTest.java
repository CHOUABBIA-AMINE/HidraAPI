/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Correction02ArchitectureTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Test
 * @Layer       : Architecture Test
 * @Module      : architecture
 * @Package     : dz.sh.hidra.architecture
 *
 * @Description : Correction 02 architecture guardrails for domain purity, module boundaries,
 *                deleted compatibility wrappers, and OpenAPI boundary policy.
 *
 */
package dz.sh.hidra.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;

/**
 * Correction 02 architecture guardrails.
 *
 * <p>Business role:
 * Protects the correction baseline from regressing into deprecated compatibility wrappers,
 * infrastructure leakage, cross-module aggregate coupling, or OpenAPI leakage into the domain.
 *
 * <p>Architecture role:
 * Enforces rules recorded in `docs/roadmap/correction_02.md`, `docs/ARCHITECTURE.md`, and
 * `CONTRIBUTING.md` without changing production behavior.
 *
 * <p>Validation:
 * Run with {@code mvn -q test -Dtest=Correction02ArchitectureTest} or the full test suite.
 */
class Correction02ArchitectureTest {

    private static final String ROOT_PACKAGE = "dz.sh.hidra";
    private static final Path PRODUCTION_SOURCE_ROOT = Path.of("src", "main", "java");

    private static final JavaClasses PRODUCTION_CLASSES = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(ROOT_PACKAGE);

    private static final Set<String> FORBIDDEN_PRODUCTION_PACKAGES = Set.of(
            ".identityaccess",
            ".shared",
            ".sharedkernel",
            ".common",
            ".core",
            ".utils",
            ".helper",
            ".helpers",
            ".misc");

    private static final List<String> DELETED_COMPATIBILITY_WRAPPER_PATHS = List.of(
            "src/main/java/dz/sh/hidra/modules/topology/domain/value/ProductType.java",
            "src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitType.java");

    @Test
    void moduleDomainPackagesMustNotDependOnSpringJpaPlatformOrOpenApi() {
        noClasses()
                .that()
                .resideInAPackage("..modules..domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "org.springframework..",
                        "jakarta.persistence..",
                        "javax.persistence..",
                        "org.hibernate..",
                        "dz.sh.hidra.platform..",
                        "io.swagger.v3.oas.annotations..")
                .check(PRODUCTION_CLASSES);
    }

    @Test
    void kernelDomainMustNotDependOnOpenApi() {
        noClasses()
                .that()
                .resideInAPackage("..kernel.domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("io.swagger.v3.oas.annotations..")
                .check(PRODUCTION_CLASSES);
    }

    @Test
    void organizationDomainMustNotImportIdentityDomainModels() {
        noClasses()
                .that()
                .resideInAPackage("..modules.organization.domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..modules.identity.domain.model..")
                .check(PRODUCTION_CLASSES);
    }

    @Test
    void topologyDomainMustNotImportOrganizationDomainModels() {
        noClasses()
                .that()
                .resideInAPackage("..modules.topology.domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..modules.organization.domain.model..")
                .check(PRODUCTION_CLASSES);
    }

    @Test
    void identityDomainMustNotImportOrganizationDomainModels() {
        noClasses()
                .that()
                .resideInAPackage("..modules.identity.domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..modules.organization.domain.model..")
                .check(PRODUCTION_CLASSES);
    }

    @Test
    void forbiddenProductionPackageNamesMustNotExist() {
        List<String> violations = PRODUCTION_CLASSES.stream()
                .map(JavaClass::getPackageName)
                .distinct()
                .filter(this::hasForbiddenPackageSegment)
                .sorted()
                .toList();

        assertTrue(
                violations.isEmpty(),
                "Forbidden production package names must not exist. Rename these packages: " + violations);
    }

    @Test
    void deletedCompatibilityWrappersMustNotBeReintroduced() {
        List<String> existingDeletedWrappers = DELETED_COMPATIBILITY_WRAPPER_PATHS.stream()
                .filter(path -> Files.exists(Path.of(path)))
                .toList();

        assertTrue(
                existingDeletedWrappers.isEmpty(),
                "Deleted compatibility wrappers must not be reintroduced: " + existingDeletedWrappers);
    }

    @Test
    void domainReferenceObjectsMustNotHardcodeMultilingualLabels() throws IOException {
        List<String> violations;
        try (Stream<Path> sourceFiles = Files.walk(PRODUCTION_SOURCE_ROOT)) {
            violations = sourceFiles
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().replace('\\', '/').contains("/domain/value/"))
                    .filter(path -> path.getFileName().toString().endsWith("Reference.java"))
                    .filter(this::containsHardcodedLabelSwitch)
                    .map(path -> path.toString().replace('\\', '/'))
                    .sorted()
                    .toList();
        }

        assertTrue(
                violations.isEmpty(),
                "Domain reference objects must carry id/code only and must not hardcode multilingual labels: " + violations);
    }

    private boolean hasForbiddenPackageSegment(String packageName) {
        return FORBIDDEN_PRODUCTION_PACKAGES.stream().anyMatch(packageName::contains);
    }

    private boolean containsHardcodedLabelSwitch(Path sourceFile) {
        try {
            String source = Files.readString(sourceFile);
            boolean hasSwitchLabelLogic = source.contains("switch")
                    && (source.contains("nameAr")
                    || source.contains("nameFr")
                    || source.contains("nameEn")
                    || source.contains("label")
                    || source.contains("arabic")
                    || source.contains("french")
                    || source.contains("english"));
            boolean hasLiteralTranslationHints = source.contains("\"ar\"")
                    || source.contains("\"fr\"")
                    || source.contains("\"en\"");

            return hasSwitchLabelLogic || hasLiteralTranslationHints;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to inspect source file: " + sourceFile, exception);
        }
    }
}
