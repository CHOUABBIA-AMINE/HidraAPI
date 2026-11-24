/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityRegionalResponsibilityQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-24
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Provides T33 scoped regional resource access evidence.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.contract.identity.IdentityRegionalResponsibilityContract;
import dz.sh.hidra.modules.organization.application.contract.workflow.WorkflowOrganizationContract;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeRegistryRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.ResponsibilityAssignmentRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.EmployeeStatus;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.Objects;
import org.springframework.stereotype.Service;

/** Exports only directly evidenced employee or currently eligible unit-held responsibilities. */
@Service
public final class IdentityRegionalResponsibilityQueryService implements IdentityRegionalResponsibilityContract {
    private final EmployeeRepositoryPort employees;
    private final OperationalScopeRegistryRepositoryPort registry;
    private final ResponsibilityAssignmentRepositoryPort responsibilities;
    private final WorkflowOrganizationContract memberships;

    public IdentityRegionalResponsibilityQueryService(EmployeeRepositoryPort employees,
            OperationalScopeRegistryRepositoryPort registry,
            ResponsibilityAssignmentRepositoryPort responsibilities,
            WorkflowOrganizationContract memberships) {
        this.employees = Objects.requireNonNull(employees);
        this.registry = Objects.requireNonNull(registry);
        this.responsibilities = Objects.requireNonNull(responsibilities);
        this.memberships = Objects.requireNonNull(memberships);
    }

    @Override
    public List<Scope> eligibleScopes(String userId, String employeeId, List<Scope> ancestry, Instant at) {
        if (ancestry == null || !currentEmployee(userId, employeeId, at)) return List.of();
        var admitted = new LinkedHashSet<Scope>();
        for (Scope request : ancestry) {
            if (request == null) continue;
            final OperationalScopeType type;
            try {
                type = OperationalScopeType.valueOf(request.type());
            } catch (IllegalArgumentException invalid) {
                continue;
            }
            if (!type.isGoverned() || type.isGlobal() || type == OperationalScopeType.ORGANIZATION_UNIT) continue;
            var scope = registry.findByTypeAndTargetId(type, request.targetId());
            if (scope.isEmpty()) continue;
            for (ResponsibilityAssignment a : responsibilities.findByScopeId(scope.get().id())) {
                if (scope.get().id().equals(a.scopeId()) && currentAssignment(a, employeeId, at)) {
                    admitted.add(request);
                    break;
                }
            }
        }
        return new ArrayList<>(admitted);
    }

    @Override
    public List<Candidate> currentCandidates(String userId, String employeeId, Instant at) {
        if (!currentEmployee(userId, employeeId, at)) return List.of();
        var result = new TreeMap<Long, Candidate>();
        for (var assignment : responsibilities.findCurrentCandidatesForEmployee(employeeId, at)) {
            if (!currentAssignment(assignment, employeeId, at)) continue;
            var registered = registry.findById(assignment.scopeId());
            if (registered.isEmpty()) continue;
            var scope = registered.orElseThrow();
            if (!assignment.scopeId().equals(scope.id())) continue;
            if (scope.type() != OperationalScopeType.PIPELINE_SYSTEM
                    && scope.type() != OperationalScopeType.PIPELINE
                    && scope.type() != OperationalScopeType.FACILITY
                    && scope.type() != OperationalScopeType.EQUIPMENT) continue;
            result.put(scope.id(), new Candidate(scope.id(), scope.type().name(), scope.targetId()));
        }
        return List.copyOf(result.values());
    }

    private boolean currentEmployee(String userId, String employeeId, Instant at) {
        if (userId == null || userId.isBlank() || employeeId == null || employeeId.isBlank()
                || at == null) return false;
        var employee = employees.findById(employeeId);
        return employee.isPresent() && employee.get().status() == EmployeeStatus.ACTIVE
                && (employee.get().terminatedAt() == null || at.isBefore(employee.get().terminatedAt()))
                && (employee.get().identityUserReference() == null
                    || userId.equals(employee.get().identityUserReference()));
    }

    private boolean currentAssignment(ResponsibilityAssignment a, String employeeId, Instant at) {
        if (a.status() != AssignmentStatus.ACTIVE || at.isBefore(a.validFrom())
                || (a.validTo() != null && !at.isBefore(a.validTo()))
                || !qualifying(a.responsibilityType())) return false;
        return (a.assigneeType() == ResponsibilityAssigneeType.EMPLOYEE && employeeId.equals(a.assigneeId()))
                || (a.assigneeType() == ResponsibilityAssigneeType.ORGANIZATION_UNIT
                    && memberships.eligibleMember(employeeId, a.assigneeId(), at));
    }

    private static boolean qualifying(ResponsibilityType type) {
        return type == ResponsibilityType.OWNER || type == ResponsibilityType.ACCOUNTABLE
                || type == ResponsibilityType.RESPONSIBLE;
    }
}
