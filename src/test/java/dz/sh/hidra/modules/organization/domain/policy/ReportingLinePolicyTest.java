/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLinePolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.policy
 *
 * @Description : Unit tests for ReportingLinePolicy matrix reporting rules.
 *
 */
package dz.sh.hidra.modules.organization.domain.policy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.organization.domain.exception.ReportingLineException;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.ReportingLine;
import dz.sh.hidra.modules.organization.domain.value.EmployeeEmail;
import dz.sh.hidra.modules.organization.domain.value.EmployeeFullName;
import dz.sh.hidra.modules.organization.domain.value.EmployeeNumber;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineType;

/**
 * Tests matrix reporting line policy.
 *
 * <p>Business role:
 * Verifies primary LINE uniqueness, manager validation, self-reporting rejection, and matrix
 * reporting rules.
 *
 * <p>Architecture role:
 * This is a domain policy test with no persistence, API, identity, topology, or Spring dependency.
 */
class ReportingLinePolicyTest {

    private final ReportingLinePolicy policy = new ReportingLinePolicy();

    @Test
    void shouldAllowFunctionalReportingLineForActiveEmployeeAndManager() {
        Employee employee = activeEmployee("EMP-RPT-001");
        Employee manager = activeEmployee("EMP-RPT-002");
        ReportingLine reportingLine = ReportingLine.create(
                employee.id(),
                manager.id(),
                ReportingLineType.FUNCTIONAL,
                false,
                LocalDate.now(),
                "Functional reporting line");

        assertDoesNotThrow(() -> policy.ensureReportingLineAllowed(
                employee,
                manager,
                reportingLine,
                List.of(),
                LocalDate.now()));
    }

    @Test
    void shouldRejectSelfReporting() {
        Employee employee = activeEmployee("EMP-RPT-SELF-001");

        assertThrows(
                ReportingLineException.class,
                () -> policy.ensureNoSelfReporting(employee.id(), employee.id()));
    }

    @Test
    void shouldRejectMultipleActivePrimaryLineReportingLines() {
        Employee employee = activeEmployee("EMP-RPT-003");
        Employee firstManager = activeEmployee("EMP-RPT-004");
        Employee secondManager = activeEmployee("EMP-RPT-005");

        ReportingLine existingPrimaryLine = ReportingLine.create(
                employee.id(),
                firstManager.id(),
                ReportingLineType.LINE,
                true,
                LocalDate.now(),
                "Primary line");

        ReportingLine newPrimaryLine = ReportingLine.create(
                employee.id(),
                secondManager.id(),
                ReportingLineType.LINE,
                true,
                LocalDate.now(),
                "Second primary line");

        assertThrows(
                ReportingLineException.class,
                () -> policy.ensureReportingLineAllowed(
                        employee,
                        secondManager,
                        newPrimaryLine,
                        List.of(existingPrimaryLine),
                        LocalDate.now()));
    }

    @Test
    void shouldRejectReportingLineWhenManagerDoesNotMatch() {
        Employee employee = activeEmployee("EMP-RPT-006");
        Employee expectedManager = activeEmployee("EMP-RPT-007");
        Employee actualDifferentManager = activeEmployee("EMP-RPT-008");

        ReportingLine reportingLine = ReportingLine.create(
                employee.id(),
                expectedManager.id(),
                ReportingLineType.OPERATIONAL,
                false,
                LocalDate.now(),
                "Operational line");

        assertThrows(
                ReportingLineException.class,
                () -> policy.ensureReportingLineAllowed(
                        employee,
                        actualDifferentManager,
                        reportingLine,
                        List.of(),
                        LocalDate.now()));
    }

    @Test
    void shouldRejectReportingLineForRegisteredManager() {
        Employee employee = activeEmployee("EMP-RPT-009");
        Employee registeredManager = registeredEmployee("EMP-RPT-010");

        ReportingLine reportingLine = ReportingLine.create(
                employee.id(),
                registeredManager.id(),
                ReportingLineType.TECHNICAL,
                false,
                LocalDate.now(),
                "Technical line");

        assertThrows(
                ReportingLineException.class,
                () -> policy.ensureReportingLineAllowed(
                        employee,
                        registeredManager,
                        reportingLine,
                        List.of(),
                        LocalDate.now()));
    }

    private static Employee activeEmployee(String employeeNumber) {
        return registeredEmployee(employeeNumber).activate();
    }

    private static Employee registeredEmployee(String employeeNumber) {
        return Employee.register(
                EmployeeNumber.of(employeeNumber),
                EmployeeFullName.of("Abir MEDJERAB"),
                EmployeeEmail.of("abir.medjerab@example.com"),
                null);
    }
}
