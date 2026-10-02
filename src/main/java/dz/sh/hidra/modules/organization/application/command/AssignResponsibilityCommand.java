/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignResponsibilityCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.command
 *
 * @Description : Command to assign an effective-dated responsibility to a registered operational scope.
 *
 */
package dz.sh.hidra.modules.organization.application.command;

import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import java.time.Instant;
import java.util.Objects;

/**
 * Application command for one responsibility assignment.
 *
 * <p>{@code scopeId} is the generated OperationalScope registry ID. The command
 * never accepts independent scope type, target ID, code, or name.</p>
 */
public record AssignResponsibilityCommand(
        ResponsibilityType responsibilityType,
        ResponsibilityAssigneeType assigneeType,
        String assigneeId,
        Long scopeId,
        String description,
        Instant validFrom,
        Instant validTo,
        ResponsibilityOperationContext context
) {

    public AssignResponsibilityCommand {
        Objects.requireNonNull(responsibilityType, "Responsibility type must not be null.");
        Objects.requireNonNull(assigneeType, "Assignee type must not be null.");
        assigneeId = requireText(assigneeId, "Assignee ID must not be null or blank.");
        description = normalize(description);
        context = Objects.requireNonNull(context, "Responsibility operation context must not be null.");

        if (scopeId == null || scopeId <= 0) {
            throw new IllegalArgumentException("Operational scope registry ID must be positive.");
        }
        if (validFrom != null && validTo != null && !validTo.isAfter(validFrom)) {
            throw new IllegalArgumentException("Responsibility validTo must be after validFrom.");
        }
    }

    @Deprecated(forRemoval = true)
    public AssignResponsibilityCommand(
            ResponsibilityType responsibilityType,
            String assigneeType,
            String assigneeId,
            Long scopeId,
            String description,
            Instant validFrom,
            Instant validTo,
            ResponsibilityOperationContext context
    ) {
        this(
                responsibilityType,
                ResponsibilityAssigneeType.from(assigneeType),
                assigneeId,
                scopeId,
                description,
                validFrom,
                validTo,
                context
        );
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
