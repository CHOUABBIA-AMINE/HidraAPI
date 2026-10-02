/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RevokeResponsibilityCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.command
 *
 * @Description : Command to end an existing responsibility assignment without deleting history.
 *
 */
package dz.sh.hidra.modules.organization.application.command;

import java.time.Instant;
import java.util.Objects;

/**
 * Ends an existing responsibility assignment.
 */
public record RevokeResponsibilityCommand(
        String assignmentId,
        Instant effectiveAt,
        ResponsibilityOperationContext context
) {

    public RevokeResponsibilityCommand {
        if (assignmentId == null || assignmentId.isBlank()) {
            throw new IllegalArgumentException("Responsibility assignment ID must not be null or blank.");
        }
        assignmentId = assignmentId.trim();
        context = Objects.requireNonNull(context, "Responsibility operation context must not be null.");
    }
}
