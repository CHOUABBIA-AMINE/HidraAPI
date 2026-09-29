/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityReconciliationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
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

import dz.sh.hidra.modules.audit.application.contract.organization.OrganizationResponsibilityAuditContract;
import dz.sh.hidra.modules.audit.application.contract.organization.OrganizationResponsibilityAuditContract.Event;
import dz.sh.hidra.modules.audit.application.contract.organization.OrganizationResponsibilityAuditContract.Operation;
import dz.sh.hidra.modules.organization.application.command.ResponsibilityOperationContext;
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
import org.springframework.transaction.annotation.Transactional;

/**
 * Reconciles current responsibility references without changing persisted state.
 *
 * <p>Historical assignments remain historical facts. This service only reports
 * inconsistencies such as absent/inactive assignees, unknown registered scopes,
 * retired/unresolvable owners, mismatched resolver identity, or direct unit self-targets.</p>
 *
 * <p>Resolver failures remain fail-closed: an unsupported owner type is reported and
 * no target ID is guessed or coerced. GLOBAL scopes require no owner resolution.</p>
 */
@Service
public class ResponsibilityReconciliationApplicationService
        implements ReconcileResponsibilitiesUseCase {

    private final ResponsibilityAssignmentRepositoryPort assignments;
    private final OperationalScopeRegistryRepositoryPort scopes;
    private final OperationalScopeTargetResolverPort targets;
    private final EmployeeRepositoryPort employees;
    public static final String RECONCILE_PERMISSION = "organization:responsibility:reconcile";
    private static final String EVENT_TYPE = "ORGANIZATION_RESPONSIBILITY_RECONCILED";
    private static final String EVENT_CATEGORY = "BUSINESS";

    private final OrganizationUnitRepositoryPort units;
    private final OrganizationResponsibilityAuditContract audit;

    public ResponsibilityReconciliationApplicationService(
            ResponsibilityAssignmentRepositoryPort assignments,
            OperationalScopeRegistryRepositoryPort scopes,
            OperationalScopeTargetResolverPort targets,
            EmployeeRepositoryPort employees,
            OrganizationUnitRepositoryPort units,
            OrganizationResponsibilityAuditContract audit
    ) {
        this.assignments = Objects.requireNonNull(assignments, "Responsibility repository must not be null.");
        this.scopes = Objects.requireNonNull(scopes, "Operational scope registry must not be null.");
        this.targets = Objects.requireNonNull(targets, "Operational scope target resolver must not be null.");
        this.employees = Objects.requireNonNull(employees, "Employee repository must not be null.");
        this.units = Objects.requireNonNull(units, "Organization unit repository must not be null.");
        this.audit = Objects.requireNonNull(audit, "Organization audit contract must not be null.");
    }

    @Override
    @Transactional
    public ResponsibilityReconciliationResult reconcileResponsibilities(
            ResponsibilityOperationContext context
    ) {
        Objects.requireNonNull(context, "Responsibility operation context must not be null.");
        if (!context.hasPermission(RECONCILE_PERMISSION)) {
            throw new SecurityException("Missing required permission: " + RECONCILE_PERMISSION);
        }

        List<ResponsibilityAssignment> all = List.copyOf(assignments.findAll());
        List<Issue> issues = new ArrayList<>();

        for (ResponsibilityAssignment assignment : all) {
            reconcileAssignee(assignment, issues);
            reconcileScope(assignment, issues);
        }

        ResponsibilityReconciliationResult result =
                new ResponsibilityReconciliationResult(all.size(), issues);
        audit.append(new Event(
                EVENT_TYPE,
                EVENT_CATEGORY,
                getClass().getSimpleName(),
                "RECONCILE_RESPONSIBILITIES",
                context.actorId(),
                context.actorUsername(),
                context.actorDisplayName(),
                "RESPONSIBILITY_RECONCILIATION",
                context.operationReference(),
                Operation.READ,
                "COMPLETED",
                "scanned=" + result.scannedAssignments() + ",issues=" + result.issues().size(),
                null,
                null,
                null,
                context.requestId(),
                context.correlationId(),
                java.time.Instant.now()
        ));
        return result;
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
