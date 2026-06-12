/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QueueReportRunCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.command
 *
 * @Description : Command to queue a report run.
 *
 */
package dz.sh.hidra.modules.reporting.application.command;

import dz.sh.hidra.modules.reporting.domain.value.ReportRunMode;

/**
 * Command to queue a report run.
 */
public record QueueReportRunCommand(
        String reportRequestId,
        String reportDefinitionId,
        String templateVersionId,
        ReportRunMode runMode,
        String correlationId
) {
}
