/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignmentPolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.policy
 *
 * @Description : Unit tests for EmployeeAssignmentPolicy rules.
 *
 */
package dz.sh.hidra.modules.organization.domain.policy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.organization.domain.exception.EmployeeAssignmentNotAllowedException;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAssignment;
import dz.sh.hidra.modules.organization.domain.model.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.model.Position;
import dz.sh.hidra.modules.organization.domain.value.EmployeeEmail;
import dz.sh.hidra.modules.organization.domain.value.EmployeeFullName;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.EmployeeNumber;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitCode;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitName;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitType;
import dz.sh.hidra.modules.organization.domain.value.PositionCode;
import dz.sh.hidra.modules.organization.domain.value.PositionTitle;

/**
 * Tests employee assignment domain policy.
 *
 * <p>Business role:
 * Verifies that employees can only be assigned to valid organization units and positions.
 *
 * <p>Architecture role:
 * This policy test does not load persistence, API, identity, topology, or Spring infrastructure.
 */
class EmployeeAssignmentPolicyTest {

    private final EmployeeAssignmentPolicy policy = new EmployeeAssignmentPolicy();

    @Test
    void shouldAllowAssignmentForActiveEmployeeActiveUnitAndActivePosition() {
        Employee employee = activeEmployee("EMP-ASSIGN-001");
        OrganizationUnit organizationUnit = organizationUnit();
        Position position = position();
        EmployeeAssignment assignment = EmployeeAssignment.create(
                employee.id(),
                organizationUnit.id(),
                position.id(),
                null,
                LocalDate.now());

        assertDoesNotThrow(() -> policy.ensureAssignmentAllowed(employee, organizationUnit, position, assignment));
    }

    @Test
    void shouldRejectAssignmentForRegisteredEmployee() {
        Employee employee = registeredEmployee("EMP-ASSIGN-002");
        OrganizationUnit organizationUnit = organizationUnit();
        Position position = position();
        EmployeeAssignment assignment = EmployeeAssignment.create(
                employee.id(),
                organizationUnit.id(),
                position.id(),
                null,
                LocalDate.now());

        assertThrows(
                EmployeeAssignmentNotAllowedException.class,
                () -> policy.ensureAssignmentAllowed(employee, organizationUnit, position, assignment));
    }

    @Test
    void shouldRejectAssignmentWhenEmployeeDoesNotMatchAggregate() {
        Employee employee = activeEmployee("EMP-ASSIGN-003");
        OrganizationUnit organizationUnit = organizationUnit();
        Position position = position();
        EmployeeAssignment assignment = EmployeeAssignment.create(
                EmployeeId.newId(),
                organizationUnit.id(),
                position.id(),
                null,
                LocalDate.now());

        assertThrows(
                EmployeeAssignmentNotAllowedException.class,
                () -> policy.ensureAssignmentAllowed(employee, organizationUnit, position, assignment));
    }

    @Test
    void shouldRejectStationAssignmentWithNonStationScope() {
        Employee employee = activeEmployee("EMP-ASSIGN-004");
        OperationalScopeReference stationScope = new OperationalScopeReference(
                OperationalScopeType.TOPOLOGY_COMPRESSION_STATION,
                "station-001",
                "CS-EAST-01",
                "Compression Station East 01");
        OrganizationUnit stationUnit = OrganizationUnit.createStation(
                OrganizationUnitCode.of("CS_EAST_01"),
                OrganizationUnitName.of("Compression Station East 01"),
                null,
                stationScope);
        Position position = position();
        OperationalScopeReference pipelineScope = new OperationalScopeReference(
                OperationalScopeType.TOPOLOGY_PIPELINE,
                "pipeline-001",
                "PIPE-01",
                "Pipeline 01");
        EmployeeAssignment assignment = EmployeeAssignment.create(
                employee.id(),
                stationUnit.id(),
                position.id(),
                pipelineScope,
                LocalDate.now());

        assertThrows(
                EmployeeAssignmentNotAllowedException.class,
                () -> policy.ensureAssignmentAllowed(employee, stationUnit, position, assignment));
    }

    private static Employee registeredEmployee(String employeeNumber) {
        return Employee.register(
                EmployeeNumber.of(employeeNumber),
                EmployeeFullName.of("Abir MEDJERAB"),
                EmployeeEmail.of("abir.medjerab@example.com"),
                null);
    }

    private static Employee activeEmployee(String employeeNumber) {
        return registeredEmployee(employeeNumber).activate();
    }

    private static OrganizationUnit organizationUnit() {
        return OrganizationUnit.create(
                OrganizationUnitCode.of("TEAM_ASSIGN"),
                OrganizationUnitName.of("Assignment Team"),
                OrganizationUnitType.TEAM,
                null,
                null);
    }

    private static Position position() {
        return Position.create(
                PositionCode.of("TEAM_LEADER"),
                PositionTitle.of("Team Leader"),
                "Leads a team");
    }
}
