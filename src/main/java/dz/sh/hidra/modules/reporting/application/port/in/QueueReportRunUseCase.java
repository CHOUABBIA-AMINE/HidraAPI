/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QueueReportRunUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.port.in
 *
 * @Description : Use case for queuing report runs.
 *
 */
package dz.sh.hidra.modules.reporting.application.port.in;

import dz.sh.hidra.modules.reporting.application.command.QueueReportRunCommand;
import dz.sh.hidra.modules.reporting.application.dto.ReportRunSummaryDto;

/**
 * Use case for queuing report runs.
 */
public interface QueueReportRunUseCase {

    ReportRunSummaryDto queueReportRun(QueueReportRunCommand command);
}
