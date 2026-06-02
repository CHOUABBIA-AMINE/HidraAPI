/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityArchitectureTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Architecture Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity
 *
 * @Description : Architecture guardrail tests for identity module boundaries.
 *
 */
package dz.sh.hidra.modules.identity;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import dz.sh.hidra.modules.identity.application.port.out.PermissionRepository;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepository;
import dz.sh.hidra.modules.identity.application.port.out.UserRepository;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.PermissionRepositoryAdapter;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.RoleRepositoryAdapter;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserRepositoryAdapter;
import org.junit.jupiter.api.Test;

import java.util.stream.StreamSupport;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Architecture guardrail tests for identity module boundaries.
 *
 * <p>Business role: protects the identity module as the owner of users, roles,
 * permissions, and access policies while preventing accidental coupling to organization,
 * platform security plumbing, or unrelated module names.</p>
 *
 * <p>Architecture role: ArchUnit-based test suite that enforces the identity layered
 * architecture and persistence adapter contracts. It intentionally lives in tests and
 * does not change production behavior.</p>
 *
 * <p>Validation responsibility: verifies forbidden dependencies, prevents creation of an
 * identityaccess package, prevents controllers from accessing repositories directly, and
 * verifies persistence adapters implement application outbound ports.</p>
 *
 * <p>Usage: run with {@code mvn -q test -Dtest=IdentityArchitectureTest}.</p>
 */
class IdentityArchitectureTest {

    private static final String IDENTITY_PACKAGE = "dz.sh.hidra.modules.identity";

    private final JavaClasses identityClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(IDENTITY_PACKAGE);

    @Test
    void identityDomainMustNotDependOnSpring() {
        noClasses()
                .that()
                .resideInAPackage("..modules.identity.domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("org.springframework..")
                .check(identityClasses);
    }

    @Test
    void identityDomainMustNotDependOnJpa() {
        noClasses()
                .that()
                .resideInAPackage("..modules.identity.domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "jakarta.persistence..",
                        "javax.persistence..",
                        "org.hibernate.."
                )
                .check(identityClasses);
    }

    @Test
    void identityDomainMustNotDependOnInfrastructure() {
        noClasses()
                .that()
                .resideInAPackage("..modules.identity.domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..modules.identity.infrastructure..")
                .check(identityClasses);
    }

    @Test
    void identityDomainMustNotDependOnApi() {
        noClasses()
                .that()
                .resideInAPackage("..modules.identity.domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..modules.identity.api..")
                .check(identityClasses);
    }

    @Test
    void identityApplicationMustNotDependOnApi() {
        noClasses()
                .that()
                .resideInAPackage("..modules.identity.application..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..modules.identity.api..")
                .check(identityClasses);
    }

    @Test
    void identityApplicationMustNotDependOnInfrastructure() {
        noClasses()
                .that()
                .resideInAPackage("..modules.identity.application..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..modules.identity.infrastructure..")
                .check(identityClasses);
    }

    @Test
    void identityApiMustNotDependOnInfrastructure() {
        noClasses()
                .that()
                .resideInAPackage("..modules.identity.api..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..modules.identity.infrastructure..")
                .check(identityClasses);
    }

    @Test
    void identityMustNotImportOrganizationDomainModel() {
        noClasses()
                .that()
                .resideInAPackage("..modules.identity..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..modules.organization.domain..")
                .check(identityClasses);
    }

    @Test
    void identityMustNotCreateIdentityAccessPackage() {
        boolean containsIdentityAccessPackage = StreamSupport.stream(identityClasses.spliterator(), false)
                .map(JavaClass::getPackageName)
                .anyMatch(packageName -> packageName.contains(".identityaccess"));

        assertTrue(
                !containsIdentityAccessPackage,
                "The identity module must use dz.sh.hidra.modules.identity and must not create identityaccess packages."
        );
    }

    @Test
    void controllersMustNotAccessRepositoriesDirectly() {
        noClasses()
                .that()
                .resideInAPackage("..modules.identity.api.rest.controller..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "..modules.identity.application.port.out..",
                        "..modules.identity.infrastructure.persistence..",
                        "org.springframework.data.."
                )
                .check(identityClasses);
    }

    @Test
    void userPersistenceAdapterMustImplementUserRepositoryPort() {
        assertTrue(
                UserRepository.class.isAssignableFrom(UserRepositoryAdapter.class),
                "UserRepositoryAdapter must implement the identity application UserRepository outbound port."
        );
    }

    @Test
    void rolePersistenceAdapterMustImplementRoleRepositoryPort() {
        assertTrue(
                RoleRepository.class.isAssignableFrom(RoleRepositoryAdapter.class),
                "RoleRepositoryAdapter must implement the identity application RoleRepository outbound port."
        );
    }

    @Test
    void permissionPersistenceAdapterMustImplementPermissionRepositoryPort() {
        assertTrue(
                PermissionRepository.class.isAssignableFrom(PermissionRepositoryAdapter.class),
                "PermissionRepositoryAdapter must implement the identity application PermissionRepository outbound port."
        );
    }
}
