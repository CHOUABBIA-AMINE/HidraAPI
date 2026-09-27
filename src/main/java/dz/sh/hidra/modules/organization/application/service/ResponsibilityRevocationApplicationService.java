/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityRevocationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Ends responsibility assignments without deleting their historical records.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.command.RevokeResponsibilityCommand;
import dz.sh.hidra.modules.organization.application.port.in.RevokeResponsibilityUseCase;
import dz.sh.hidra.modules.organization.application.port.out.ResponsibilityAssignmentRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

/**
 * Applies a historical end to an existing responsibility assignment.
 *
 * <p>Revocation never deletes a row. ACTIVE and SUSPENDED assignments transition
 * to ENDED and receive an effective {@code validTo}. Repeating a revocation for an
 * already ENDED assignment is idempotent.</p>
 */
@Service
public final class ResponsibilityRevocationApplicationService implements RevokeResponsibilityUseCase {

    private final ResponsibilityAssignmentRepositoryPort responsibilityAssignmentRepositoryPort;

    public ResponsibilityRevocationApplicationService(
            ResponsibilityAssignmentRepositoryPort responsibilityAssignmentRepositoryPort
    ) {
        this.responsibilityAssignmentRepositoryPort = Objects.requireNonNull(
                responsibilityAssignmentRepositoryPort,
                "Responsibility assignment repository port must not be null."
        );
    }

    @Override
    public String revokeResponsibility(RevokeResponsibilityCommand command) {
        Objects.requireNonNull(command, "Revoke responsibility command must not be null.");

        ResponsibilityAssignment existing = responsibilityAssignmentRepositoryPort
                .findById(command.assignmentId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown responsibility assignment: " + command.assignmentId()
                ));

        if (existing.status() == AssignmentStatus.ENDED) {
            return existing.id();
        }

        if (existing.status() == AssignmentStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Cancelled responsibility assignment cannot be revoked: " + existing.id()
            );
        }

        Instant now = Instant.now();
        Instant effectiveAt = command.effectiveAt() == null ? now : command.effectiveAt();

        if (existing.validFrom() == null || !effectiveAt.isAfter(existing.validFrom())) {
            throw new IllegalArgumentException(
                    "Responsibility revocation time must be after validFrom."
            );
        }

        if (existing.validTo() != null && effectiveAt.isAfter(existing.validTo())) {
            throw new IllegalArgumentException(
                    "Responsibility revocation cannot extend the existing validity period."
            );
        }

        ResponsibilityAssignment ended = new ResponsibilityAssignment(
                existing.id(),
                existing.responsibilityType(),
                existing.assigneeType(),
                existing.assigneeId(),
                existing.scopeId(),
                existing.description(),
                existing.validFrom(),
                effectiveAt,
                AssignmentStatus.ENDED,
                existing.createdAt(),
                now
        );

        return responsibilityAssignmentRepositoryPort.save(ended).id();
    }
}
