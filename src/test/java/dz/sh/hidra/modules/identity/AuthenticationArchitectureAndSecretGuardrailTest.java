/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticationArchitectureAndSecretGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity
 *
 * @Description : Protects authentication architecture ownership and production secret externalization boundaries.
 *
 */
package dz.sh.hidra.modules.identity;

import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.StreamSupport;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Authentication-specific guardrails supplement the repository-wide architecture rules.
 */
class AuthenticationArchitectureAndSecretGuardrailTest {

    private static final JavaClasses PRODUCTION_CLASSES = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("dz.sh.hidra");

    private static final Set<String> EXTERNAL_AUTHENTICATION_NORMALIZERS = Set.of(
            "dz.sh.hidra.modules.identity.infrastructure.security.LdapAuthenticationProvider",
            "dz.sh.hidra.modules.identity.infrastructure.security.IdentityOidcJwtAuthenticationConverter"
    );

    private static final Set<String> IDENTITY_BUSINESS_TYPES = Set.of("User", "Role", "Permission");

    private static final Set<String> EXTERNAL_AUTHORIZATION_MAPPING_TYPES = Set.of(
            "ExternalGroupMapping",
            "ExternalRoleMapping",
            "ExternalPermissionMapping"
    );

    private static final Set<String> AUTHENTICATION_SECRET_KEYS = Set.of(
            "hidra.security.bootstrap.password",
            "hidra.platform.security.jwt.hmac-secret",
            "hidra.platform.security.ldap.bind-password"
    );

    @Test
    void identityDomainMustNotDependOnSpringLdapImplementationClasses() {
        noClasses()
                .that()
                .resideInAPackage("dz.sh.hidra.modules.identity.domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "org.springframework.ldap..",
                        "org.springframework.security.ldap.."
                )
                .check(PRODUCTION_CLASSES);
    }

    @Test
    void identityControllersMustNotQueryPersistenceRepositoriesDirectly() {
        noClasses()
                .that()
                .resideInAPackage("dz.sh.hidra.modules.identity.api.rest.controller..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "dz.sh.hidra.modules.identity.infrastructure.persistence..",
                        "org.springframework.data.."
                )
                .check(PRODUCTION_CLASSES);
    }

    @Test
    void externalAuthenticationNormalizersMustNotDependOnExternalRolePermissionMappings() {
        for (String className : EXTERNAL_AUTHENTICATION_NORMALIZERS) {
            JavaClass normalizer = productionClass(className);
            List<String> forbiddenDependencies = normalizer.getDirectDependenciesFromSelf().stream()
                    .map(Dependency::getTargetClass)
                    .filter(AuthenticationArchitectureAndSecretGuardrailTest::isExternalAuthorizationMappingType)
                    .map(JavaClass::getName)
                    .sorted()
                    .toList();

            assertTrue(
                    forbiddenDependencies.isEmpty(),
                    () -> className + " must not assign Hidra authorization from external role/group/permission mappings: "
                            + forbiddenDependencies
            );
        }
    }

    @Test
    void platformMustNotOwnIdentityUserRolePermissionBusinessMeaning() {
        List<String> violations = StreamSupport.stream(PRODUCTION_CLASSES.spliterator(), false)
                .filter(javaClass -> javaClass.getPackageName().startsWith("dz.sh.hidra.platform."))
                .flatMap(javaClass -> javaClass.getDirectDependenciesFromSelf().stream()
                        .map(Dependency::getTargetClass)
                        .filter(AuthenticationArchitectureAndSecretGuardrailTest::isIdentityBusinessType)
                        .map(target -> javaClass.getName() + " -> " + target.getName()))
                .sorted()
                .toList();

        assertTrue(
                violations.isEmpty(),
                () -> "Platform must remain technical security plumbing and must not depend on Identity User/Role/Permission: "
                        + violations
        );
    }

    @Test
    void ordinaryIdentityApiDtosMustNotExposePasswordHashes() throws IOException {
        Path apiRoot = Path.of("src/main/java/dz/sh/hidra/modules/identity/api");
        assertTrue(Files.isDirectory(apiRoot), "Identity API source root must exist.");

        List<Path> violations;
        try (var paths = Files.walk(apiRoot)) {
            violations = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .filter(AuthenticationArchitectureAndSecretGuardrailTest::containsPasswordHashTerm)
                    .sorted()
                    .toList();
        }

        assertTrue(
                violations.isEmpty(),
                () -> "Password hashes must never appear in ordinary Identity API request/response DTOs: " + violations
        );
    }

    @Test
    void productionAuthenticationSecretsMustRemainExternalized() throws IOException {
        Path resourcesRoot = Path.of("src/main/resources");
        assertTrue(Files.isDirectory(resourcesRoot), "Production resources root must exist.");

        List<String> violations;
        try (var paths = Files.walk(resourcesRoot)) {
            violations = paths
                    .filter(Files::isRegularFile)
                    .filter(AuthenticationArchitectureAndSecretGuardrailTest::isTextConfigurationFile)
                    .flatMap(path -> secretViolations(path).stream())
                    .sorted()
                    .toList();
        }

        assertTrue(
                violations.isEmpty(),
                () -> "Authentication credentials/private keys must not be committed in production resources: " + violations
        );
    }

    @Test
    void productionSourceMustNotContainEmbeddedPrivateKeys() throws IOException {
        Path sourceRoot = Path.of("src/main");
        List<Path> violations;
        try (var paths = Files.walk(sourceRoot)) {
            violations = paths
                    .filter(Files::isRegularFile)
                    .filter(AuthenticationArchitectureAndSecretGuardrailTest::isTextSourceFile)
                    .filter(AuthenticationArchitectureAndSecretGuardrailTest::containsPrivateKeyMarker)
                    .sorted()
                    .toList();
        }

        assertFalse(
                violations.size() > 0,
                () -> "Private-key PEM material must never be embedded under src/main: " + violations
        );
    }

    private static JavaClass productionClass(String className) {
        return StreamSupport.stream(PRODUCTION_CLASSES.spliterator(), false)
                .filter(javaClass -> javaClass.getName().equals(className))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected production class not found: " + className));
    }

    private static boolean isExternalAuthorizationMappingType(JavaClass target) {
        String simpleName = target.getSimpleName();
        if (EXTERNAL_AUTHORIZATION_MAPPING_TYPES.contains(simpleName)) {
            return true;
        }
        if (!target.getPackageName().startsWith("dz.sh.hidra.modules.identity.")) {
            return false;
        }
        return simpleName.contains("RoleMapping")
                || simpleName.contains("PermissionMapping")
                || simpleName.contains("GroupMapping");
    }

    private static boolean isIdentityBusinessType(JavaClass target) {
        return target.getPackageName().equals("dz.sh.hidra.modules.identity.domain.model")
                && IDENTITY_BUSINESS_TYPES.contains(target.getSimpleName());
    }

    private static boolean containsPasswordHashTerm(Path path) {
        try {
            String content = Files.readString(path).toLowerCase(Locale.ROOT);
            return content.contains("passwordhash") || content.contains("password_hash");
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to inspect " + path, exception);
        }
    }

    private static boolean isTextConfigurationFile(Path path) {
        String name = path.getFileName().toString().toLowerCase(Locale.ROOT);
        return name.endsWith(".properties") || name.endsWith(".yml") || name.endsWith(".yaml");
    }

    private static boolean isTextSourceFile(Path path) {
        String name = path.getFileName().toString().toLowerCase(Locale.ROOT);
        return name.endsWith(".java")
                || name.endsWith(".properties")
                || name.endsWith(".yml")
                || name.endsWith(".yaml")
                || name.endsWith(".xml")
                || name.endsWith(".sql");
    }

    private static List<String> secretViolations(Path path) {
        try {
            return Files.readAllLines(path).stream()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                    .filter(line -> line.contains("="))
                    .map(line -> line.split("=", 2))
                    .filter(parts -> AUTHENTICATION_SECRET_KEYS.contains(parts[0].trim()))
                    .filter(parts -> !isExternalizedPlaceholder(parts[1].trim()))
                    .map(parts -> path + ":" + parts[0].trim())
                    .toList();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to inspect " + path, exception);
        }
    }

    private static boolean isExternalizedPlaceholder(String value) {
        return value.isEmpty() || (value.startsWith("${") && value.endsWith("}"));
    }

    private static boolean containsPrivateKeyMarker(Path path) {
        try {
            String content = Files.readString(path);
            return content.contains("-----BEGIN PRIVATE KEY-----")
                    || content.contains("-----BEGIN RSA PRIVATE KEY-----")
                    || content.contains("-----BEGIN EC PRIVATE KEY-----");
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to inspect " + path, exception);
        }
    }
}
