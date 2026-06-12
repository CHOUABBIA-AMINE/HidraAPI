/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CloseHseCaseCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.command
 *
 * @Description : Command to close HSE case.
 *
 */
package dz.sh.hidra.modules.hse.application.command;

/**
 * Command to close HSE case.
 */
public record CloseHseCaseCommand(
        String hseCaseId,
        String closureSummary,
        boolean impactAssessed,
        boolean capaCompleted,
        boolean evidenceReviewed,
        boolean regulatoryReviewed,
        String closedByActorId,
        String closedByDisplayNameSnapshot,
        String workflowInstanceId
) {
}
