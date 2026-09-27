/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityReconciliationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Read-only reconciliation service for responsibility assignment integrity.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.dto.ResponsibilityReconciliationResult;
import dz.sh.hidra.modules.organization.application.dto.ResponsibilityReconciliationResult.Issue;
import dz.sh.hidra.modules.organization.application.dto.ResponsibilityReconciliationResult.IssueCode;
import dz.sh.hidra.modules.organization.application.port.in.ReconcileResponsibilitiesUseCase;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeRegistryRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.ResponsibilityAssignmentRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.EmployeeStatus;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

/**
 * Reconciles current responsibility references without changing persisted state.
 *
 * <p>Historical assignments remain historical facts. This service only reports
 * inconsistencies such as absent/inactive assignees, missing registry references,
 * retired/unresolvable owners, mismatched resolver identity, or direct unit self-targets.</p>
 *
 * <p>Resolver failures remain fail-closed: an unsupported owner type is reported and
 * no target ID is guessed or coerced. GLOBAL scopes require no owner resolution.</p>
 */
@Service
public final class ResponsibilityReconciliationApplicationService
        implements ReconcileResponsibilitiesUseCase {

    private final ResponsibilityAssignmentRepositoryPort assignments;
    private final OperationalScopeRegistryRepositoryPort scopes;
    private final OperationalScopeTargetResolverPort targets;
    private final EmployeeRepositoryPort employees;
    private final OrganizationUnitRepositoryPort units;

    public ResponsibilityReconciliationApplicationService(
            ResponsibilityAssignmentRepositoryPort assignments,
            OperationalScopeRegistryRepositoryPort scopes,
            OperationalScopeTargetResolverPort targets,
            EmployeeRepositoryPort employees,
            OrganizationUnitRepositoryPort units
    ) {
        this.assignments = Objects.requireNonNull(assignments, "Responsibility repository must not be null.");
        this.scopes = Objects.requireNonNull(scopes, "Operational scope registry must not be null.");
        this.targets = Objects.requireNonNull(targets, "Operational scope target resolver must not be null.");
        this.employees = Objects.requireNonNull(employees, "Employee repository must not be null.");
        this.units = Objects.requireNonNull(units, "Organization unit repository must not be null.");
    }

    @Override
    public ResponsibilityReconciliationResult reconcileResponsibilities() {
        List<ResponsibilityAssignment> all = List.copyOf(assignments.findAll());
        List<Issue> issues = new ArrayList<>();

        for (ResponsibilityAssignment assignment : all) {
            reconcileAssignee(assignment, issues);
            reconcileScope(assignment, issues);
        }

        return new ResponsibilityReconciliationResult(all.size(), issues);
    }

    private void reconcileAssignee(ResponsibilityAssignment assignment, List<Issue> issues) {
        if (assignment.assigneeType() == ResponsibilityAssigneeType.EMPLOYEE) {
            employees.findById(assignment.assigneeId()).ifPresentOrElse(
                    employee -> {
                        if (employee.status() != EmployeeStatus.ACTIVE) {
                            issues.add(issue(assignment, IssueCode.ASSIGNEE_NOT_ACTIVE, employee.id()));
                        }
                    },
                    () -> issues.add(issue(
                            assignment,
                            IssueCode.MISSING_ASSIGNEE,
                            assignment.assigneeId()
                    ))
            );
            return;
        }

        units.findById(assignment.assigneeId()).ifPresentOrElse(
                unit -> {
                    if (unit.status() != OrganizationUnitStatus.ACTIVE) {
                        issues.add(issue(assignment, IssueCode.ASSIGNEE_NOT_ACTIVE, unit.id()));
                    }
                },
                () -> issues.add(issue(
                        assignment,
                        IssueCode.MISSING_ASSIGNEE,
                        assignment.assigneeId()
                ))
        );
    }

    private void reconcileScope(ResponsibilityAssignment assignment, List<Issue> issues) {
        Long scopeId = assignment.scopeId();
        if (scopeId == null) {
            issues.add(issue(assignment, IssueCode.MISSING_SCOPE_REFERENCE, null));
            return;
        }

        OperationalScope scope = scopes.findById(scopeId).orElse(null);
        if (scope == null) {
            issues.add(issue(assignment, IssueCode.UNKNOWN_SCOPE, scopeId.toString()));
            return;
        }

        if (scope.type() == OperationalScopeType.GLOBAL) {
            return;
        }

        if (!targets.supports(scope.type())) {
            issues.add(issue(
                    assignment,
                    IssueCode.UNSUPPORTED_SCOPE_RESOLVER,
                    scope.targetId()
            ));
            return;
        }

        OperationalScopeTargetResolverPort.ResolvedTarget resolved =
                targets.resolve(scope.type(), scope.targetId()).orElse(null);
        if (resolved == null) {
            issues.add(issue(assignment, IssueCode.MISSING_SCOPE_OWNER, scope.targetId()));
            return;
        }

        if (resolved.type() != scope.type()
                || !Objects.equals(resolved.targetId(), scope.targetId())) {
            issues.add(issue(
                    assignment,
                    IssueCode.SCOPE_OWNER_IDENTITY_MISMATCH,
                    scope.targetId()
            ));
            return;
        }

        if (!resolved.assignable()) {
            issues.add(issue(
                    assignment,
                    IssueCode.SCOPE_OWNER_NOT_ASSIGNABLE,
                    scope.targetId()
            ));
        }

        if (scope.type() == OperationalScopeType.ORGANIZATION_UNIT
                && assignment.assigneeType() == ResponsibilityAssigneeType.ORGANIZATION_UNIT
                && Objects.equals(scope.targetId(), assignment.assigneeId())) {
            issues.add(issue(
                    assignment,
                    IssueCode.SELF_TARGET_RESPONSIBILITY,
                    scope.targetId()
            ));
        }
    }

    private static Issue issue(
            ResponsibilityAssignment assignment,
            IssueCode code,
            String referenceId
    ) {
        return new Issue(assignment.id(), code, referenceId);
    }
}
