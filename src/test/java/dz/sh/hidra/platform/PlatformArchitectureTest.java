/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlatformArchitectureTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform
 *
 * @Description : Ensures platform code keeps technical boundaries and avoids business aggregate drift.
 *
 */
package dz.sh.hidra.platform;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.assertj.core.api.Assertions.assertThat;

class PlatformArchitectureTest {

    private static final JavaClasses PRODUCTION_CLASSES = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("dz.sh.hidra");

    private static final Set<String> FORBIDDEN_BUSINESS_AGGREGATE_NAMES = Set.of(
            "User",
            "Role",
            "Permission",
            "Employee",
            "Pipeline",
            "FlowReading",
            "Incident"
    );

    @Test
    void platformShouldNotDependOnBusinessAggregatePackages() {
        noClasses()
                .that()
                .resideInAPackage("dz.sh.hidra.platform..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "dz.sh.hidra.modules.*.domain.model..",
                        "dz.sh.hidra.modules.identityaccess.domain.model..",
                        "dz.sh.hidra.modules.organization.domain.model..",
                        "dz.sh.hidra.modules.topology.domain.model..",
                        "dz.sh.hidra.modules.telemetry.domain.model..",
                        "dz.sh.hidra.modules.workflow.domain.model..",
                        "dz.sh.hidra.modules.planning.domain.model..",
                        "dz.sh.hidra.modules.monitoring.domain.model..",
                        "dz.sh.hidra.modules.incidents.domain.model..",
                        "dz.sh.hidra.modules.audit.domain.model.."
                )
                .check(PRODUCTION_CLASSES);
    }

    @Test
    void platformShouldNotContainBusinessAggregateClasses() {
        Set<String> platformBusinessAggregateNames = PRODUCTION_CLASSES.stream()
                .filter(javaClass -> javaClass.getPackageName().startsWith("dz.sh.hidra.platform"))
                .map(JavaClass::getSimpleName)
                .filter(FORBIDDEN_BUSINESS_AGGREGATE_NAMES::contains)
                .collect(java.util.stream.Collectors.toSet());

        assertThat(platformBusinessAggregateNames).isEmpty();
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
