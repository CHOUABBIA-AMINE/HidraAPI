/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationRestMapperEmployeePersonalDataTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.mapper
 *
 * @Description : Verifies employee REST contracts preserve canonical birth data.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.modules.organization.api.rest.request.RegisterEmployeeRequest;
import dz.sh.hidra.modules.organization.api.rest.response.EmployeeResponse;
import dz.sh.hidra.modules.organization.application.command.RegisterEmployeeCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeSummaryDto;
import dz.sh.hidra.modules.organization.domain.value.EmployeeStatus;
import dz.sh.hidra.modules.organization.domain.value.EmployeeType;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class OrganizationRestMapperEmployeePersonalDataTest {

    @Test
    void registrationRequestCarriesBirthDataToApplicationCommand() {
        LocalDate dateOfBirth = LocalDate.of(1990, 5, 3);
        RegisterEmployeeRequest request = new RegisterEmployeeRequest(
                "E-0001",
                "أمين",
                "مثال",
                "Amine",
                "Example",
                dateOfBirth,
                "locality-16-001",
                "الجزائر",
                "Alger",
                "Algiers",
                "amine@example.test",
                "+213555000001",
                EmployeeType.PERMANENT,
                "identity-user-1"
        );

        RegisterEmployeeCommand command = OrganizationRestMapper.toCommand(request);

        assertThat(command.firstNameAr()).isEqualTo("أمين");
        assertThat(command.lastNameAr()).isEqualTo("مثال");
        assertThat(command.firstNameLt()).isEqualTo("Amine");
        assertThat(command.lastNameLt()).isEqualTo("Example");
        assertThat(command.dateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(command.birthLocalityId()).isEqualTo("locality-16-001");
        assertThat(command.birthPlaceAr()).isEqualTo("الجزائر");
        assertThat(command.birthPlaceFr()).isEqualTo("Alger");
        assertThat(command.birthPlaceEn()).isEqualTo("Algiers");
    }

    @Test
    void employeeSummaryCarriesBirthDataToRestResponse() {
        LocalDate dateOfBirth = LocalDate.of(1990, 5, 3);
        EmployeeSummaryDto summary = new EmployeeSummaryDto(
                "employee-1",
                "E-0001",
                "أمين مثال",
                "Amine Example",
                dateOfBirth,
                "locality-16-001",
                "الجزائر",
                "Alger",
                "Algiers",
                "amine@example.test",
                EmployeeType.PERMANENT,
                EmployeeStatus.REGISTERED,
                "identity-user-1"
        );

        EmployeeResponse response = OrganizationRestMapper.toResponse(summary);

        assertThat(response.dateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(response.birthLocalityId()).isEqualTo("locality-16-001");
        assertThat(response.birthPlaceAr()).isEqualTo("الجزائر");
        assertThat(response.birthPlaceFr()).isEqualTo("Alger");
        assertThat(response.birthPlaceEn()).isEqualTo("Algiers");
    }
}
