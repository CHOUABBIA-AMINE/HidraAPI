/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationContactPointTargetValidatorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Verifies contact-point target existence validation through Organization repositories.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.value.ContactPointTargetReference;
import dz.sh.hidra.modules.organization.domain.value.ContactPointTargetType;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrganizationContactPointTargetValidatorTest {

    @Test
    void validatesEmployeeAgainstEmployeeRepositoryOnly() {
        EmployeeRepositoryPort employees = mock(EmployeeRepositoryPort.class);
        OrganizationUnitRepositoryPort units = mock(OrganizationUnitRepositoryPort.class);
        Employee employee = mock(Employee.class);
        when(employees.findById("emp-1")).thenReturn(Optional.of(employee));

        var validator = new OrganizationContactPointTargetValidator(employees, units);
        var target = new ContactPointTargetReference(ContactPointTargetType.EMPLOYEE, "emp-1");

        assertEquals(target, validator.validate(target));
        verify(employees).findById("emp-1");
        verify(units, never()).findById("emp-1");
    }

    @Test
    void validatesOrganizationUnitAgainstUnitRepositoryOnly() {
        EmployeeRepositoryPort employees = mock(EmployeeRepositoryPort.class);
        OrganizationUnitRepositoryPort units = mock(OrganizationUnitRepositoryPort.class);
        OrganizationUnit unit = mock(OrganizationUnit.class);
        when(units.findById("unit-1")).thenReturn(Optional.of(unit));

        var validator = new OrganizationContactPointTargetValidator(employees, units);
        var target = new ContactPointTargetReference(
                ContactPointTargetType.ORGANIZATION_UNIT,
                "unit-1"
        );

        assertEquals(target, validator.validate(target));
        verify(units).findById("unit-1");
        verify(employees, never()).findById("unit-1");
    }

    @Test
    void rejectsMissingTarget() {
        EmployeeRepositoryPort employees = mock(EmployeeRepositoryPort.class);
        OrganizationUnitRepositoryPort units = mock(OrganizationUnitRepositoryPort.class);
        when(employees.findById("missing")).thenReturn(Optional.empty());

        var validator = new OrganizationContactPointTargetValidator(employees, units);
        var target = new ContactPointTargetReference(ContactPointTargetType.EMPLOYEE, "missing");

        assertThrows(IllegalArgumentException.class, () -> validator.validate(target));
    }
}
