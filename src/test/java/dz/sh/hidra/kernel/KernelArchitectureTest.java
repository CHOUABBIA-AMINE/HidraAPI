/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : KernelArchitectureTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel
 *
 * @Description : Ensures kernel code has no forbidden dependencies or package drift.
 *
 */
package dz.sh.hidra.kernel;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class KernelArchitectureTest {

    private static final JavaClasses PRODUCTION_CLASSES = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("dz.sh.hidra");

    @Test
    void kernelShouldNotDependOnForbiddenPackages() {
        noClasses()
                .that()
                .resideInAPackage("dz.sh.hidra.kernel..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "dz.sh.hidra.platform..",
                        "dz.sh.hidra.modules..",
                        "org.springframework..",
                        "jakarta.persistence..",
                        "org.hibernate.."
                )
                .check(PRODUCTION_CLASSES);
    }

    @Test
    void forbiddenProductionPackageNamesShouldNotExist() {
        noClasses()
                .should()
                .resideInAnyPackage(
                        "dz.sh.hidra.shared..",
                        "dz.sh.hidra.sharedkernel..",
                        "dz.sh.hidra.common..",
                        "dz.sh.hidra.core..",
                        "dz.sh.hidra.utils.."
                )
                .check(PRODUCTION_CLASSES);
    }
}
