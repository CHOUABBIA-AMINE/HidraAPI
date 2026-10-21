/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowOrganizationAppointmentMembershipTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.query
 *
 * @Description : Preserves Workflow membership semantics while prohibiting all-assignment materialization.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.query;

import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.value.EmployeeStatus;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.EmployeeAssignmentJpaRepository;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.Query;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkflowOrganizationAppointmentMembershipTest {
    private static final Instant FROM = Instant.parse("2026-10-21T09:00:00Z");
    private static final Instant AT = Instant.parse("2026-10-21T10:00:00Z");
    private static final Instant TO = Instant.parse("2026-10-21T11:00:00Z");

    private final OrganizationUnitRepositoryPort units = mock(OrganizationUnitRepositoryPort.class);
    private final EmployeeRepositoryPort employees = mock(EmployeeRepositoryPort.class);
    private final EmployeeAssignmentJpaRepository assignments = mock(EmployeeAssignmentJpaRepository.class);
    private final WorkflowOrganizationQueryAdapter subject = new WorkflowOrganizationQueryAdapter(
            units, employees, assignments);

    private void eligibleOwners() {
        OrganizationUnit unit = mock(OrganizationUnit.class);
        when(unit.status()).thenReturn(OrganizationUnitStatus.ACTIVE);
        when(unit.validFrom()).thenReturn(FROM);
        when(unit.validTo()).thenReturn(TO);
        when(units.findById("unit")).thenReturn(Optional.of(unit));
        Employee employee = mock(Employee.class);
        when(employee.status()).thenReturn(EmployeeStatus.ACTIVE);
        when(employees.findById("employee")).thenReturn(Optional.of(employee));
    }

    @Test
    void activeMembershipUsesBoundedExactPredicateInsteadOfFindAll() {
        eligibleOwners();
        when(assignments.existsActiveMembershipAt("employee", "unit", AT)).thenReturn(true);
        assertTrue(subject.eligibleMember("employee", "unit", AT));
        verify(assignments).existsActiveMembershipAt("employee", "unit", AT);
        verify(assignments, never()).findAll();
    }

    @Test
    void absentMembershipReturnsFalseWithoutForeignMaterialization() {
        eligibleOwners();
        assertFalse(subject.eligibleMember("employee", "unit", AT));
        verify(assignments).existsActiveMembershipAt("employee", "unit", AT);
        verify(assignments, never()).findAll();
    }

    @Test
    void nonexistentUnitIsRejectedBeforeAnyEmployeeOrAssignmentLookup() {
        assertFalse(subject.eligibleMember("employee", "missing", AT));
        verifyNoInteractions(employees, assignments);
    }

    @Test
    void inactiveEmployeeIsRejectedBeforeAssignmentLookup() {
        OrganizationUnit unit = mock(OrganizationUnit.class);
        when(unit.status()).thenReturn(OrganizationUnitStatus.ACTIVE);
        when(unit.validFrom()).thenReturn(FROM);
        when(unit.validTo()).thenReturn(TO);
        when(units.findById("unit")).thenReturn(Optional.of(unit));
        Employee employee = mock(Employee.class);
        when(employee.status()).thenReturn(EmployeeStatus.SUSPENDED);
        when(employees.findById("employee")).thenReturn(Optional.of(employee));
        assertFalse(subject.eligibleMember("employee", "unit", AT));
        verifyNoInteractions(assignments);
    }

    @Test
    void expiredUnitAndNullSampleFailClosed() {
        OrganizationUnit unit = mock(OrganizationUnit.class);
        when(unit.status()).thenReturn(OrganizationUnitStatus.ACTIVE);
        when(unit.validFrom()).thenReturn(FROM);
        when(unit.validTo()).thenReturn(AT);
        when(units.findById("unit")).thenReturn(Optional.of(unit));
        assertFalse(subject.eligibleMember("employee", "unit", AT));
        assertFalse(subject.eligibleMember("employee", "unit", null));
        verifyNoInteractions(employees, assignments);
    }

    @Test
    void nativeSqlPredicateIsExactAndHalfOpenWithoutGlobalScan() throws Exception {
        var method = EmployeeAssignmentJpaRepository.class.getMethod(
                "existsActiveMembershipAt", String.class, String.class, Instant.class);
        Query query = method.getAnnotation(Query.class);
        assertNotNull(query);
        assertTrue(query.nativeQuery());
        String sql = query.value().replaceAll("\\s+", " ").trim();
        assertTrue(sql.contains("SELECT EXISTS ("));
        assertTrue(sql.contains("FROM public.hidra_org_employee_assignment a"));
        assertTrue(sql.contains("a.employee_id = :employeeId"));
        assertTrue(sql.contains("a.organization_unit_id = :unitId"));
        assertTrue(sql.contains("a.status = 'ACTIVE'"));
        assertTrue(sql.contains("a.valid_from <= :at"));
        assertTrue(sql.contains("a.valid_to IS NULL OR a.valid_to > :at"));
        assertFalse(sql.contains("SELECT *"));
        assertFalse(sql.contains("LIMIT 0"));
    }
}
