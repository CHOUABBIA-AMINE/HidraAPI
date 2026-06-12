/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CloseIncidentCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.command
 *
 * @Description : Command to close an incident.
 *
 */
package dz.sh.hidra.modules.incident.application.command;

/**
 * Command to close an incident.
 */
public record CloseIncidentCommand(
        String incidentId,
        String closureSummary,
        boolean resolutionVerified,
        boolean evidenceReviewed,
        boolean rootCauseReviewed,
        boolean followUpActionsCreated,
        String closedByActorId,
        String closedByActorNameSnapshot,
        String workflowInstanceId
) {
}
