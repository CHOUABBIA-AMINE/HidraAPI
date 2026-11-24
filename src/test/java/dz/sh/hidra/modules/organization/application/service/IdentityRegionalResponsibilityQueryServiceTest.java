/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityRegionalResponsibilityQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-24
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Verifies T33 regional read scope, deny precedence and owner evidence.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.contract.identity.IdentityRegionalResponsibilityContract;
import dz.sh.hidra.modules.organization.application.contract.workflow.WorkflowOrganizationContract;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeRegistryRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.ResponsibilityAssignmentRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.*;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class IdentityRegionalResponsibilityQueryServiceTest {
    private static final Instant NOW = Instant.parse("2026-11-20T12:00:00Z");
    private final EmployeeRepositoryPort employees = mock(EmployeeRepositoryPort.class);
    private final OperationalScopeRegistryRepositoryPort registry = mock(OperationalScopeRegistryRepositoryPort.class);
    private final ResponsibilityAssignmentRepositoryPort assignments = mock(ResponsibilityAssignmentRepositoryPort.class);
    private final WorkflowOrganizationContract members = mock(WorkflowOrganizationContract.class);
    private final IdentityRegionalResponsibilityQueryService service =
            new IdentityRegionalResponsibilityQueryService(employees, registry, assignments, members);
    private final IdentityRegionalResponsibilityContract.Scope request =
            new IdentityRegionalResponsibilityContract.Scope("PIPELINE_SYSTEM", "sys");

    private ResponsibilityAssignment responsibility(ResponsibilityAssigneeType type,
            String assigneeId, AssignmentStatus status, Instant until) {
        return new ResponsibilityAssignment("r", ResponsibilityType.RESPONSIBLE, type,
                assigneeId, 17L, null, NOW.minusSeconds(90), until, status, NOW, NOW);
    }

    @BeforeEach void currentCanonicalRegistryAndActiveEmployee() {
        Employee employee = mock(Employee.class);
        when(employee.status()).thenReturn(EmployeeStatus.ACTIVE);
        when(employee.identityUserReference()).thenReturn("u");
        when(employees.findById("emp")).thenReturn(Optional.of(employee));
        when(registry.findByTypeAndTargetId(OperationalScopeType.PIPELINE_SYSTEM, "sys"))
                .thenReturn(Optional.of(new OperationalScope(17L, OperationalScopeType.PIPELINE_SYSTEM, "sys")));
        when(assignments.findByScopeId(17L)).thenReturn(
                List.of(responsibility(ResponsibilityAssigneeType.EMPLOYEE, "emp",
                        AssignmentStatus.ACTIVE, NOW.plusSeconds(10))));
    }

    @Test void directlyHeldResponsibilityQualifiesOnlyTheExactRegisteredScope() {
        assertEquals(List.of(request), service.eligibleScopes("u", "emp", List.of(request), NOW));
        verify(registry).findByTypeAndTargetId(OperationalScopeType.PIPELINE_SYSTEM, "sys");
    }

    @Test void wrongReciprocalEmployeeBindingAndExpiredResponsibilityDeny() {
        assertTrue(service.eligibleScopes("different", "emp", List.of(request), NOW).isEmpty());
        when(assignments.findByScopeId(17L)).thenReturn(List.of(responsibility(
                ResponsibilityAssigneeType.EMPLOYEE, "emp", AssignmentStatus.ACTIVE, NOW)));
        assertTrue(service.eligibleScopes("u", "emp", List.of(request), NOW).isEmpty());
    }

    @Test void unitHeldResponsibilityRequiresIndependentlyActiveMembership() {
        when(assignments.findByScopeId(17L)).thenReturn(List.of(responsibility(
                ResponsibilityAssigneeType.ORGANIZATION_UNIT, "unit", AssignmentStatus.ACTIVE, NOW.plusSeconds(10))));
        assertTrue(service.eligibleScopes("u", "emp", List.of(request), NOW).isEmpty());
        when(members.eligibleMember("emp", "unit", NOW)).thenReturn(true);
        assertEquals(List.of(request), service.eligibleScopes("u", "emp", List.of(request), NOW));
    }

    @Test void currentDiscoveryUsesOnlyBoundEmployeeAndEffectivePhysicalAssignments() {
        var direct = responsibility(ResponsibilityAssigneeType.EMPLOYEE, "emp", AssignmentStatus.ACTIVE, NOW.plusSeconds(10));
        var unit = responsibility(ResponsibilityAssigneeType.ORGANIZATION_UNIT, "unit", AssignmentStatus.ACTIVE, NOW.plusSeconds(10));
        var expired = responsibility(ResponsibilityAssigneeType.EMPLOYEE, "emp", AssignmentStatus.ACTIVE, NOW);
        var ended = responsibility(ResponsibilityAssigneeType.EMPLOYEE, "emp", AssignmentStatus.ENDED, NOW.plusSeconds(10));
        var foreign = responsibility(ResponsibilityAssigneeType.EMPLOYEE, "other", AssignmentStatus.ACTIVE, NOW.plusSeconds(10));
        var expected = new IdentityRegionalResponsibilityContract.Candidate(17L, "PIPELINE_SYSTEM", "sys");
        when(registry.findById(17L)).thenReturn(Optional.of(new OperationalScope(17L, OperationalScopeType.PIPELINE_SYSTEM, "sys")));
        when(assignments.findCurrentCandidatesForEmployee("emp", NOW)).thenReturn(List.of(expired, ended, foreign, unit));
        assertTrue(service.currentCandidates("u", "emp", NOW).isEmpty());
        when(members.eligibleMember("emp", "unit", NOW)).thenReturn(true);
        assertEquals(List.of(expected), service.currentCandidates("u", "emp", NOW));
        when(assignments.findCurrentCandidatesForEmployee("emp", NOW)).thenReturn(List.of(unit, direct));
        assertEquals(List.of(expected), service.currentCandidates("u", "emp", NOW));
        assertTrue(service.currentCandidates("other", "emp", NOW).isEmpty());
        when(registry.findById(17L)).thenReturn(Optional.of(new OperationalScope(17L, OperationalScopeType.GLOBAL, null)));
        assertTrue(service.currentCandidates("u", "emp", NOW).isEmpty());
    }

    @Test void suspendedOrUnsupportedResponsibilityDoesNotGrantRegionalRead() {
        when(assignments.findByScopeId(17L)).thenReturn(List.of(responsibility(
                ResponsibilityAssigneeType.EMPLOYEE, "emp", AssignmentStatus.SUSPENDED, NOW.plusSeconds(10))));
        assertTrue(service.eligibleScopes("u", "emp", List.of(request), NOW).isEmpty());
        assertTrue(service.eligibleScopes("u", "emp", List.of(
                new IdentityRegionalResponsibilityContract.Scope("CUSTOM", "forged")), NOW).isEmpty());
    }
}
