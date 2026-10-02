/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeDisplayNameSemanticsTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Verifies canonical derived employee display-name semantics.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.modules.organization.domain.value.EmployeeStatus;
import dz.sh.hidra.modules.organization.domain.value.EmployeeType;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class EmployeeDisplayNameSemanticsTest {

    @Test
    void derivesCanonicalArabicAndLatinDisplayNamesFromStructuredNames() {
        Employee employee = employee(
                " أمين ",
                " شواعبية ",
                " Amine ",
                " Chouabbia ",
                "legacy-ar",
                "legacy-lt"
        );

        assertThat(employee.arabicDisplayName()).isEqualTo("أمين شواعبية");
        assertThat(employee.latinDisplayName()).isEqualTo("Amine Chouabbia");
    }

    @Test
    void derivesDisplayNameWhenOnlyOneStructuredNamePartExists() {
        Employee employee = employee(
                null,
                " شواعبية ",
                " Amine ",
                null,
                null,
                null
        );

        assertThat(employee.arabicDisplayName()).isEqualTo("شواعبية");
        assertThat(employee.latinDisplayName()).isEqualTo("Amine");
    }

    @Test
    void returnsNullWhenStructuredNameIsAbsent() {
        Employee employee = employee(null, null, null, null, "legacy-ar", "legacy-lt");

        assertThat(employee.arabicDisplayName()).isNull();
        assertThat(employee.latinDisplayName()).isNull();
    }

    @Test
    void historicalDisplayAccessorsRemainDeprecatedCompatibilityOnly() throws Exception {
        assertThat(Employee.class.getMethod("displayNameAr").isAnnotationPresent(Deprecated.class))
                .isTrue();
        assertThat(Employee.class.getMethod("displayNameLt").isAnnotationPresent(Deprecated.class))
                .isTrue();
    }

    private static Employee employee(
            String firstNameAr,
            String lastNameAr,
            String firstNameLt,
            String lastNameLt,
            String legacyDisplayNameAr,
            String legacyDisplayNameLt
    ) {
        return new Employee(
                "emp-1",
                "EMP-1",
                firstNameAr,
                lastNameAr,
                firstNameLt,
                lastNameLt,
                legacyDisplayNameAr,
                legacyDisplayNameLt,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                EmployeeType.PERMANENT,
                EmployeeStatus.ACTIVE,
                null,
                Instant.parse("2020-01-01T00:00:00Z"),
                null,
                Instant.parse("2026-09-28T00:00:00Z"),
                Instant.parse("2026-09-28T00:00:00Z")
        );
    }
}
