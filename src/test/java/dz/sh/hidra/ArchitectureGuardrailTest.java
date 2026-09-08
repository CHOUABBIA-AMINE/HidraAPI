/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ArchitectureGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : bootstrap
 * @Package     : dz.sh.hidra
 *
 * @Description : Enforces repository-wide modular monolith layer boundaries with ArchUnit.
 *
 */
package dz.sh.hidra;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import java.util.stream.StreamSupport;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Protects the architectural dependency direction defined by the Hidra coding policy.
 *
 * <p>The rules intentionally start with high-confidence repository-wide boundaries.
 * Module-specific cross-context rules can be added under each module roadmap after the
 * baseline is green.</p>
 */
class ArchitectureGuardrailTest {

    private static final JavaClasses PRODUCTION_CLASSES = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("dz.sh.hidra");

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
}
