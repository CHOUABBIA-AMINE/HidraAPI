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
import java.util.Objects;

/**
 * Creates responsibility assignments using only canonical scope registry IDs.
 *
 * <p>This service verifies that the scope registry row exists before persistence.
 * Assignee authorization, overlap/idempotency rules, revocation, and owner lifecycle
 * rechecks remain separate responsibilities for subsequent application policies.</p>
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

        if (command.validTo() != null && !command.validTo().isAfter(validFrom)) {
            throw new IllegalArgumentException("Responsibility validTo must be after validFrom.");
        }

        ResponsibilityAssignment assignment = new ResponsibilityAssignment(
                OrganizationId.newId().value(),
                command.responsibilityType(),
                command.assigneeType(),
                command.assigneeId(),
                command.scopeId(),
                command.description(),
                validFrom,
                command.validTo(),
                AssignmentStatus.ACTIVE,
                now,
                now
        );

        return responsibilityAssignmentRepositoryPort.save(assignment).id();
    }
}
