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
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeRegistryRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.ResponsibilityAssignmentRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
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
    private final ResponsibilityAssignmentRepositoryPort responsibilityAssignmentRepositoryPort;

    public ResponsibilityAssignmentApplicationService(
            OperationalScopeRegistryRepositoryPort operationalScopeRegistryRepositoryPort,
            ResponsibilityAssignmentRepositoryPort responsibilityAssignmentRepositoryPort
    ) {
        this.operationalScopeRegistryRepositoryPort = Objects.requireNonNull(
                operationalScopeRegistryRepositoryPort,
                "Operational scope registry repository port must not be null."
        );
        this.responsibilityAssignmentRepositoryPort = Objects.requireNonNull(
                responsibilityAssignmentRepositoryPort,
                "Responsibility assignment repository port must not be null."
        );
    }

    @Override
    public String assignResponsibility(AssignResponsibilityCommand command) {
        Objects.requireNonNull(command, "Assign responsibility command must not be null.");

        operationalScopeRegistryRepositoryPort.findById(command.scopeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown operational scope registry ID: " + command.scopeId()
                ));

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
