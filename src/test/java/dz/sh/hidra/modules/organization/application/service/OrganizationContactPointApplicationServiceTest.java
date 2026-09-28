/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationContactPointApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Verifies canonical Organization contact-point creation and target validation.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dz.sh.hidra.modules.organization.application.command.CreateOrganizationContactPointCommand;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationContactPointRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.OrganizationContactPoint;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.value.ContactPointTargetReference;
import dz.sh.hidra.modules.organization.domain.value.ContactPointTargetType;
import dz.sh.hidra.modules.organization.domain.value.ContactPointType;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class OrganizationContactPointApplicationServiceTest {

    @Test
    void createsContactPointForExistingEmployee() {
        CapturingContactPointRepository contacts = new CapturingContactPointRepository();
        EmployeeRepositoryPort employees = new StubEmployeeRepository(true);
        OrganizationUnitRepositoryPort units = new StubOrganizationUnitRepository(false);
        OrganizationContactPointApplicationService service =
                new OrganizationContactPointApplicationService(
                        contacts,
                        new OrganizationContactPointTargetValidator(employees, units)
                );

        String id = service.createContactPoint(
                new CreateOrganizationContactPointCommand(
                        ContactPointType.EMAIL,
                        new ContactPointTargetReference(
                                ContactPointTargetType.EMPLOYEE,
                                "employee-1"
                        ),
                        null,
                        "ops@example.test",
                        false,
                        false,
                        true
                )
        );

        assertThat(id).isEqualTo(contacts.saved.id());
        assertThat(contacts.saved.contactPointType()).isEqualTo(ContactPointType.EMAIL);
        assertThat(contacts.saved.target().type()).isEqualTo(ContactPointTargetType.EMPLOYEE);
        assertThat(contacts.saved.target().targetId()).isEqualTo("employee-1");
        assertThat(contacts.saved.value()).isEqualTo("ops@example.test");
        assertThat(contacts.saved.active()).isTrue();
    }

    @Test
    void rejectsMissingEmployeeTargetBeforeSave() {
        CapturingContactPointRepository contacts = new CapturingContactPointRepository();
        OrganizationContactPointApplicationService service =
                new OrganizationContactPointApplicationService(
                        contacts,
                        new OrganizationContactPointTargetValidator(
                                new StubEmployeeRepository(false),
                                new StubOrganizationUnitRepository(false)
                        )
                );

        assertThatThrownBy(() -> service.createContactPoint(
                new CreateOrganizationContactPointCommand(
                        ContactPointType.MOBILE,
                        new ContactPointTargetReference(
                                ContactPointTargetType.EMPLOYEE,
                                "missing-employee"
                        ),
                        null,
                        "+213555000001",
                        false,
                        false,
                        true
                )
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("EMPLOYEE/missing-employee");

        assertThat(contacts.saved).isNull();
    }

    private static final class CapturingContactPointRepository
            implements OrganizationContactPointRepositoryPort {

        private OrganizationContactPoint saved;

        @Override
        public OrganizationContactPoint save(OrganizationContactPoint model) {
            saved = model;
            return model;
        }

        @Override
        public Optional<OrganizationContactPoint> findById(String id) {
            return Optional.empty();
        }
    }

    private record StubEmployeeRepository(boolean exists) implements EmployeeRepositoryPort {

        @Override
        public Employee save(Employee model) {
            return model;
        }

        @Override
        public Optional<Employee> findById(String id) {
            if (!exists) {
                return Optional.empty();
            }
            return Optional.of(new Employee(
                    id,
                    "E-1",
                    null,
                    null,
                    "Employee",
                    "One",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    dz.sh.hidra.modules.organization.domain.value.EmployeeType.PERMANENT,
                    dz.sh.hidra.modules.organization.domain.value.EmployeeStatus.ACTIVE,
                    null,
                    null,
                    null,
                    java.time.Instant.EPOCH,
                    java.time.Instant.EPOCH
            ));
        }
    }

    private record StubOrganizationUnitRepository(boolean exists)
            implements OrganizationUnitRepositoryPort {

        @Override
        public OrganizationUnit save(OrganizationUnit model) {
            return model;
        }

        @Override
        public Optional<OrganizationUnit> findById(String id) {
            return Optional.empty();
        }
    }
}
