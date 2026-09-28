/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeApplicationServicePersonalDataTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Verifies employee registration carries canonical birth data through the application contract.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.modules.organization.application.command.RegisterEmployeeCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeSummaryDto;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.value.EmployeeType;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class EmployeeApplicationServicePersonalDataTest {

    @Test
    void registrationCarriesBirthDataIntoSavedEmployeeAndSummary() {
        CapturingEmployeeRepository repository = new CapturingEmployeeRepository();
        EmployeeApplicationService service = new EmployeeApplicationService(repository);

        LocalDate dateOfBirth = LocalDate.of(1990, 5, 3);
        RegisterEmployeeCommand command = new RegisterEmployeeCommand(
                "E-0001",
                "أمين",
                "مثال",
                "Amine",
                "Example",
                "أمين مثال",
                "Amine Example",
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

        EmployeeSummaryDto result = service.registerEmployee(command);

        Employee saved = repository.saved;
        assertThat(saved).isNotNull();
        assertThat(saved.dateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(saved.birthLocalityId()).isEqualTo("locality-16-001");
        assertThat(saved.birthPlaceAr()).isEqualTo("الجزائر");
        assertThat(saved.birthPlaceFr()).isEqualTo("Alger");
        assertThat(saved.birthPlaceEn()).isEqualTo("Algiers");

        assertThat(result.dateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(result.birthLocalityId()).isEqualTo("locality-16-001");
        assertThat(result.birthPlaceAr()).isEqualTo("الجزائر");
        assertThat(result.birthPlaceFr()).isEqualTo("Alger");
        assertThat(result.birthPlaceEn()).isEqualTo("Algiers");
    }

    private static final class CapturingEmployeeRepository implements EmployeeRepositoryPort {

        private Employee saved;

        @Override
        public Employee save(Employee model) {
            saved = model;
            return model;
        }

        @Override
        public Optional<Employee> findById(String id) {
            return Optional.empty();
        }
    }
}
