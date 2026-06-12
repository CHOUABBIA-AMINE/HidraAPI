/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StartIntegrationJobRunUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.in
 *
 * @Description : Use case for starting integration job runs.
 *
 */
package dz.sh.hidra.modules.integration.application.port.in;

import dz.sh.hidra.modules.integration.application.command.StartIntegrationJobRunCommand;
import dz.sh.hidra.modules.integration.application.dto.IntegrationJobRunSummaryDto;

/**
 * Use case for starting integration job runs.
 */
public interface StartIntegrationJobRunUseCase {

    IntegrationJobRunSummaryDto startIntegrationJobRun(StartIntegrationJobRunCommand command);
}
