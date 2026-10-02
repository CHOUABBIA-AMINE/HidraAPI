/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateReportDefinitionUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.port.in
 *
 * @Description : Use case for creating report definitions.
 *
 */
package dz.sh.hidra.modules.reporting.application.port.in;

import dz.sh.hidra.modules.reporting.application.command.CreateReportDefinitionCommand;
import dz.sh.hidra.modules.reporting.application.dto.ReportDefinitionSummaryDto;

/**
 * Use case for creating report definitions.
 */
public interface CreateReportDefinitionUseCase {

    ReportDefinitionSummaryDto createReportDefinition(CreateReportDefinitionCommand command);
}
