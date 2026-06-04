/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Unit tests for Employee aggregate lifecycle, assignments, and reporting lines.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.organization.domain.value.EmployeeEmail;
import dz.sh.hidra.modules.organization.domain.value.EmployeeFullName;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.EmployeeNumber;
import dz.sh.hidra.modules.organization.domain.value.EmploymentStatus;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.PositionId;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineType;

/**
 * Tests the employee aggregate.
 *
 * <p>Business role:
 * Verifies employee lifecycle, operational assignments, and matrix reporting-line invariants.
 *
 * <p>Architecture role:
 * This is a domain aggregate test and does not load Spring, JPA, API, identity, topology, or
 * platform infrastructure.
 */
class EmployeeTest {

    @Test
    void shouldRegisterEmployeeInRegisteredStatus() {
        Employee employee = registeredEmployee("EMP-000123");

        assertEquals(EmploymentStatus.REGISTERED, employee.status());
        assertEquals("EMP-000123", employee.employeeNumber().value());
        assertTrue(employee.assignments().isEmpty());
        assertTrue(employee.reportingLines().isEmpty());
    }

    @Test
    void shouldActivateAndSuspendEmployee() {
        Employee activeEmployee = registeredEmployee("EMP-000124").activate();

        assertEquals(EmploymentStatus.ACTIVE, activeEmployee.status());
        assertTrue(activeEmployee.activatedAt().isPresent());

        Employee suspendedEmployee = activeEmployee.suspend();

        assertEquals(EmploymentStatus.SUSPENDED, suspendedEmployee.status());
        assertTrue(suspendedEmployee.suspendedAt().isPresent());
    }

    @Test
    void shouldRejectSuspendingRegisteredEmployee() {
        Employee employee = registeredEmployee("EMP-000125");

        assertThrows(BusinessRuleViolationException.class, employee::suspend);
    }

    @Test
    void shouldAddAssignmentToActiveEmployee() {
        Employee employee = registeredEmployee("EMP-000126").activate();
        EmployeeAssignment assignment = assignmentFor(employee.id());

        Employee updatedEmployee = employee.addAssignment(assignment);

        assertEquals(1, updatedEmployee.assignments().size());
        assertTrue(updatedEmployee.assignments().get(0).isActiveOn(LocalDate.now()));
    }

    @Test
    void shouldRejectDuplicateActiveAssignmentToSameUnitAndPosition() {
        Employee employee = registeredEmployee("EMP-000127").activate();
        EmployeeAssignment firstAssignment = assignmentFor(employee.id());
        EmployeeAssignment secondAssignment = EmployeeAssignment.create(
                employee.id(),
                firstAssignment.organizationUnitId(),
                firstAssignment.positionId(),
                null,
                LocalDate.now());

        Employee updatedEmployee = employee.addAssignment(firstAssignment);

        assertThrows(BusinessRuleViolationException.class, () -> updatedEmployee.addAssignment(secondAssignment));
    }

    @Test
    void shouldAddReportingLineToActiveEmployee() {
        Employee employee = registeredEmployee("EMP-000128").activate();
        ReportingLine reportingLine = ReportingLine.create(
                employee.id(),
                EmployeeId.newId(),
                ReportingLineType.LINE,
                true,
                LocalDate.now(),
                "Primary reporting line");

        Employee updatedEmployee = employee.addReportingLine(reportingLine);

        assertEquals(1, updatedEmployee.reportingLines().size());
        assertTrue(updatedEmployee.reportingLines().get(0).isActivePrimaryLineOn(LocalDate.now()));
    }

    @Test
    void shouldRejectMoreThanOneActivePrimaryLineReportingLine() {
        Employee employee = registeredEmployee("EMP-000129").activate();
        ReportingLine firstReportingLine = ReportingLine.create(
                employee.id(),
                EmployeeId.newId(),
                ReportingLineType.LINE,
                true,
                LocalDate.now(),
                "Primary reporting line");
        ReportingLine secondReportingLine = ReportingLine.create(
                employee.id(),
                EmployeeId.newId(),
                ReportingLineType.LINE,
                true,
                LocalDate.now(),
                "Second primary reporting line");

        Employee updatedEmployee = employee.addReportingLine(firstReportingLine);

        assertThrows(BusinessRuleViolationException.class, () -> updatedEmployee.addReportingLine(secondReportingLine));
    }

    @Test
    void shouldDisableEmployeeAndRejectNewResponsibility() {
        Employee disabledEmployee = registeredEmployee("EMP-000130").activate().disable();

        assertEquals(EmploymentStatus.DISABLED, disabledEmployee.status());
        assertTrue(disabledEmployee.disabledAt().isPresent());
        assertThrows(BusinessRuleViolationException.class, disabledEmployee::ensureCanReceiveOperationalResponsibility);
    }

    private static Employee registeredEmployee(String employeeNumber) {
        return Employee.register(
                EmployeeNumber.of(employeeNumber),
                EmployeeFullName.of("Abir MEDJERAB"),
                EmployeeEmail.of("abir.medjerab@example.com"),
                null);
    }

    private static EmployeeAssignment assignmentFor(EmployeeId employeeId) {
        return EmployeeAssignment.create(
                employeeId,
                OrganizationUnitId.newId(),
                PositionId.newId(),
                null,
                LocalDate.now());
    }
}
