/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RevokeResponsibilityCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
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

/**
 * Ends an existing responsibility assignment.
 *
 * @param assignmentId responsibility assignment identifier
 * @param effectiveAt effective end instant; null means application time
 */
public record RevokeResponsibilityCommand(
        String assignmentId,
        Instant effectiveAt
) {

    public RevokeResponsibilityCommand {
        if (assignmentId == null || assignmentId.isBlank()) {
            throw new IllegalArgumentException("Responsibility assignment ID must not be null or blank.");
        }
        assignmentId = assignmentId.trim();
    }
}
