/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RequestReportUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.port.in
 *
 * @Description : Use case for requesting reports.
 *
 */
package dz.sh.hidra.modules.reporting.application.port.in;

import dz.sh.hidra.modules.reporting.application.command.RequestReportCommand;
import dz.sh.hidra.modules.reporting.application.dto.ReportRequestSummaryDto;

/**
 * Use case for requesting reports.
 */
public interface RequestReportUseCase {

    ReportRequestSummaryDto requestReport(RequestReportCommand command);
}
