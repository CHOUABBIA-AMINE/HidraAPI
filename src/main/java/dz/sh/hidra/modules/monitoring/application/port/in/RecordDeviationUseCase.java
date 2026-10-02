/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordDeviationUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.port.in
 *
 * @Description : Use case for recording monitoring deviations.
 *
 */
package dz.sh.hidra.modules.monitoring.application.port.in;

import dz.sh.hidra.modules.monitoring.application.command.RecordDeviationCommand;
import dz.sh.hidra.modules.monitoring.application.dto.DeviationSummaryDto;

/**
 * Use case for recording monitoring deviations.
 */
public interface RecordDeviationUseCase {

    DeviationSummaryDto recordDeviation(RecordDeviationCommand command);
}
