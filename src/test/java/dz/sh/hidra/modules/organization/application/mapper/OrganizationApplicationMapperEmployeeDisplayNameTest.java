/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationApplicationMapperEmployeeDisplayNameTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.mapper
 *
 * @Description : Verifies Employee display names are derived from structured names at application boundaries.
 *
 */
package dz.sh.hidra.modules.organization.application.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.modules.organization.application.dto.EmployeeSummaryDto;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.value.EmployeeStatus;
import dz.sh.hidra.modules.organization.domain.value.EmployeeType;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class OrganizationApplicationMapperEmployeeDisplayNameTest {

    @Test
    void ignoresHistoricalDisplayCompatibilityStateAndDerivesBoundaryNames() {
        Employee employee = new Employee(
                "employee-1",
                "E-0001",
                "  أمين  ",
                "  مثال ",
                " Amine ",
                " Example ",
                "CALLER CONTROLLED AR",
                "CALLER CONTROLLED LT",
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
                null,
                null,
                Instant.parse("2026-09-28T08:00:00Z"),
                Instant.parse("2026-09-28T09:00:00Z")
        );

        EmployeeSummaryDto summary = OrganizationApplicationMapper.toSummary(employee);

        assertThat(summary.displayNameAr()).isEqualTo("أمين مثال");
        assertThat(summary.displayNameLt()).isEqualTo("Amine Example");
        assertThat(summary.displayNameAr()).isNotEqualTo(employee.displayNameAr());
        assertThat(summary.displayNameLt()).isNotEqualTo(employee.displayNameLt());
    }

    @Test
    void derivesPartialNamesDeterministicallyWhenOneStructuredPartIsAbsent() {
        Employee employee = new Employee(
                "employee-2",
                "E-0002",
                null,
                "مثال",
                "Amine",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                EmployeeType.PERMANENT,
                EmployeeStatus.REGISTERED,
                null,
                null,
                null,
                Instant.EPOCH,
                Instant.EPOCH
        );

        EmployeeSummaryDto summary = OrganizationApplicationMapper.toSummary(employee);

        assertThat(summary.displayNameAr()).isEqualTo("مثال");
        assertThat(summary.displayNameLt()).isEqualTo("Amine");
    }
}
