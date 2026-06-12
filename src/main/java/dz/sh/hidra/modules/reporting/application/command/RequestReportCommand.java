/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RequestReportCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.command
 *
 * @Description : Command to request a report.
 *
 */
package dz.sh.hidra.modules.reporting.application.command;

/**
 * Command to request a report.
 */
public record RequestReportCommand(
        String reportDefinitionId,
        String requestedByActorId,
        String requestedByUsernameSnapshot,
        String requestedByDisplayNameSnapshot,
        String requestedByRoleCodeSnapshot,
        String organizationUnitId,
        String organizationUnitNameSnapshot,
        String purpose,
        String correlationId,
        String workflowReferenceId
) {
}
