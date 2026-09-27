/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityAssignmentApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Application service for responsibility assignments over registered scopes.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.command.AssignResponsibilityCommand;
import dz.sh.hidra.modules.organization.application.port.in.AssignResponsibilityUseCase;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeRegistryRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.ResponsibilityAssignmentRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.EmployeeStatus;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import dz.sh.hidra.modules.organization.domain.value.OrganizationId;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * Creates effective-dated responsibility assignments using canonical scope IDs.
 *
 * <p>For one assignee, responsibility type and scope, ACTIVE periods use half-open
 * interval semantics: [validFrom, validTo). Exact replay is idempotent and returns
 * the existing assignment ID; any other overlapping ACTIVE period is rejected.</p>
 */
@Service
public final class ResponsibilityAssignmentApplicationService implements AssignResponsibilityUseCase {

    private final OperationalScopeRegistryRepositoryPort operationalScopeRegistryRepositoryPort;
    private final OperationalScopeTargetResolverPort operationalScopeTargetResolverPort;
    private final EmployeeRepositoryPort employeeRepositoryPort;
    private final OrganizationUnitRepositoryPort organizationUnitRepositoryPort;
    private final ResponsibilityAssignmentRepositoryPort responsibilityAssignmentRepositoryPort;

    public ResponsibilityAssignmentApplicationService(
            OperationalScopeRegistryRepositoryPort operationalScopeRegistryRepositoryPort,
            OperationalScopeTargetResolverPort operationalScopeTargetResolverPort,
            EmployeeRepositoryPort employeeRepositoryPort,
            OrganizationUnitRepositoryPort organizationUnitRepositoryPort,
            ResponsibilityAssignmentRepositoryPort responsibilityAssignmentRepositoryPort
    ) {
        this.operationalScopeRegistryRepositoryPort = Objects.requireNonNull(
                operationalScopeRegistryRepositoryPort,
                "Operational scope registry repository port must not be null."
        );
        this.operationalScopeTargetResolverPort = Objects.requireNonNull(operationalScopeTargetResolverPort, "Operational scope target resolver port must not be null.");
        this.employeeRepositoryPort = Objects.requireNonNull(employeeRepositoryPort, "Employee repository port must not be null.");
        this.organizationUnitRepositoryPort = Objects.requireNonNull(organizationUnitRepositoryPort, "Organization unit repository port must not be null.");
        this.responsibilityAssignmentRepositoryPort = Objects.requireNonNull(
                responsibilityAssignmentRepositoryPort,
                "Responsibility assignment repository port must not be null."
        );
    }

    @Override
    public String assignResponsibility(AssignResponsibilityCommand command) {
        Objects.requireNonNull(command, "Assign responsibility command must not be null.");

        OperationalScope scope = operationalScopeRegistryRepositoryPort.findById(command.scopeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown operational scope registry ID: " + command.scopeId()
                ));

        validateAssignee(command.assigneeType(), command.assigneeId());
        validateScopeOwner(scope);
        if (scope.type() == OperationalScopeType.ORGANIZATION_UNIT
                && command.assigneeType() == ResponsibilityAssigneeType.ORGANIZATION_UNIT
                && Objects.equals(scope.targetId(), command.assigneeId())) {
            throw new IllegalArgumentException("An organization unit cannot hold a responsibility over itself.");
        }

        Instant now = Instant.now();
        Instant validFrom = command.validFrom() == null ? now : command.validFrom();
        Instant validTo = command.validTo();

        if (validTo != null && !validTo.isAfter(validFrom)) {
            throw new IllegalArgumentException("Responsibility validTo must be after validFrom.");
        }

        List<ResponsibilityAssignment> existingAssignments =
                responsibilityAssignmentRepositoryPort.findActiveByAssigneeAndResponsibilityAndScope(
                        command.assigneeType(),
                        command.assigneeId(),
                        command.responsibilityType(),
                        command.scopeId()
                );

        for (ResponsibilityAssignment existing : existingAssignments) {
            if (samePeriod(existing.validFrom(), existing.validTo(), validFrom, validTo)) {
                return existing.id();
            }

            if (overlaps(existing.validFrom(), existing.validTo(), validFrom, validTo)) {
                throw new IllegalStateException(
                        "Responsibility assignment overlaps an existing ACTIVE assignment: "
                                + existing.id()
                );
            }
        }

        ResponsibilityAssignment assignment = new ResponsibilityAssignment(
                OrganizationId.newId().value(),
                command.responsibilityType(),
                command.assigneeType(),
                command.assigneeId(),
                command.scopeId(),
                command.description(),
                validFrom,
                validTo,
                AssignmentStatus.ACTIVE,
                now,
                now
        );

        return responsibilityAssignmentRepositoryPort.save(assignment).id();
    }

    private void validateAssignee(ResponsibilityAssigneeType assigneeType, String assigneeId) {
        switch (assigneeType) {
            case EMPLOYEE -> {
                var employee = employeeRepositoryPort.findById(assigneeId).orElseThrow(() -> new IllegalArgumentException("Unknown employee assignee: " + assigneeId));
                if (employee.status() != EmployeeStatus.ACTIVE) throw new IllegalArgumentException("Employee assignee must be ACTIVE: " + assigneeId);
            }
            case ORGANIZATION_UNIT -> {
                var unit = organizationUnitRepositoryPort.findById(assigneeId).orElseThrow(() -> new IllegalArgumentException("Unknown organization-unit assignee: " + assigneeId));
                if (unit.status() != OrganizationUnitStatus.ACTIVE) throw new IllegalArgumentException("Organization-unit assignee must be ACTIVE: " + assigneeId);
            }
        }
    }

    private void validateScopeOwner(OperationalScope scope) {
        if (scope.type().isGlobal()) return;
        if (!operationalScopeTargetResolverPort.supports(scope.type())) throw new IllegalStateException("No authoritative owner resolver for scope type: " + scope.type());
        var target = operationalScopeTargetResolverPort.resolve(scope.type(), scope.targetId()).orElseThrow(() -> new IllegalArgumentException("Operational scope owner target no longer exists: " + scope.targetId()));
        if (target.type() != scope.type() || !Objects.equals(target.targetId(), scope.targetId())) throw new IllegalStateException("Operational scope owner resolver returned mismatched identity.");
        if (!target.assignable()) throw new IllegalArgumentException("Operational scope owner target is not assignable: " + scope.targetId());
    }

    private static boolean samePeriod(
            Instant existingFrom,
            Instant existingTo,
            Instant requestedFrom,
            Instant requestedTo
    ) {
        return Objects.equals(existingFrom, requestedFrom)
                && Objects.equals(existingTo, requestedTo);
    }

    /**
     * Tests overlap for half-open intervals [start, end).
     * A null end represents an open-ended interval.
     */
    private static boolean overlaps(
            Instant existingFrom,
            Instant existingTo,
            Instant requestedFrom,
            Instant requestedTo
    ) {
        boolean existingStartsBeforeRequestedEnds =
                requestedTo == null || existingFrom.isBefore(requestedTo);

        boolean requestedStartsBeforeExistingEnds =
                existingTo == null || requestedFrom.isBefore(existingTo);

        return existingStartsBeforeRequestedEnds && requestedStartsBeforeExistingEnds;
    }
}
